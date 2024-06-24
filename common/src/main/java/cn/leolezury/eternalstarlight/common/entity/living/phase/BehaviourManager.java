package cn.leolezury.eternalstarlight.common.entity.living.phase;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BehaviourManager<T extends LivingEntity & MultiBehaviourUser> {
	private final T entity;
	private final List<BehaviourPhase<T>> phaseList = new ArrayList<>();
	private final Int2ObjectArrayMap<List<BehaviourPhase<T>>> phases = new Int2ObjectArrayMap<>();
	private final IntArrayList priorities = new IntArrayList();
	private final Int2IntArrayMap cooldowns = new Int2IntArrayMap();

	public Int2IntArrayMap getCooldowns() {
		return cooldowns;
	}

	public BehaviourManager(T entity, List<BehaviourPhase<T>> phaseList) {
		this.entity = entity;
		this.phaseList.addAll(phaseList);
		for (BehaviourPhase<T> phase : phaseList) {
			if (!phases.containsKey(phase.getPriority())) {
				phases.put(phase.getPriority(), new ArrayList<>());
			}
			if (!priorities.contains(phase.getPriority())) {
				priorities.add(phase.getPriority());
			}
			phases.get(phase.getPriority()).add(phase);
		}
		priorities.sort((i1, i2) -> i1 - i2);
	}

	public void tick() {
		if (entity.getBehaviourState() == 0) {
			selectPhase().ifPresent(p -> {
				p.start(entity);
				cooldowns.put(p.getId(), p.getCooldown());
			});
		} else {
			getActivePhase().ifPresent(p -> {
				if (!canContinue(p)) {
					p.stop(entity);
				} else {
					p.tick(entity);
					entity.setBehaviourTicks(entity.getBehaviourTicks() + 1);
				}
			});
		}
		for (int id : cooldowns.keySet()) {
			cooldowns.put(id, Math.max(0, cooldowns.get(id) - 1));
		}
	}

	private Optional<BehaviourPhase<T>> selectPhase() {
		for (int priority : priorities) {
			List<BehaviourPhase<T>> phasesForPriority = phases.get(priority).stream().filter(p -> p.canStart(entity, cooldowns.getOrDefault(p.getId(), 0) <= 0)).toList();
			if (!phasesForPriority.isEmpty()) {
				return Optional.ofNullable(phasesForPriority.get(entity.getRandom().nextInt(phasesForPriority.size())));
			}
		}
		return Optional.empty();
	}

	private Optional<BehaviourPhase<T>> getActivePhase() {
		return phaseList.stream().filter(p -> entity.getBehaviourState() == p.getId()).findFirst();
	}

	private boolean canContinue(BehaviourPhase<T> phase) {
		return phase.canContinue(entity) && entity.getBehaviourTicks() <= phase.getDuration();
	}
}
