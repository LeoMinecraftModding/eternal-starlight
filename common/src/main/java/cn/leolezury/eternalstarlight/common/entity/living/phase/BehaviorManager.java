package cn.leolezury.eternalstarlight.common.entity.living.phase;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BehaviorManager<T extends LivingEntity & MultiBehaviorUser> {
	private final T entity;
	private final List<BehaviorPhase<T>> phaseList = new ArrayList<>();
	private final Int2ObjectArrayMap<List<BehaviorPhase<T>>> phases = new Int2ObjectArrayMap<>();
	private final IntArrayList priorities = new IntArrayList();
	private final Int2IntArrayMap cooldowns = new Int2IntArrayMap();

	public Int2IntArrayMap getCooldowns() {
		return cooldowns;
	}

	public BehaviorManager(T entity, List<BehaviorPhase<T>> phaseList) {
		this.entity = entity;
		this.phaseList.addAll(phaseList);
		for (BehaviorPhase<T> phase : phaseList) {
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
		if (entity.getBehaviorState() == 0) {
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
					entity.setBehaviorTicks(entity.getBehaviorTicks() + 1);
				}
			});
		}
		for (int id : cooldowns.keySet()) {
			cooldowns.put(id, Math.max(0, cooldowns.get(id) - 1));
		}
	}

	private Optional<BehaviorPhase<T>> selectPhase() {
		for (int priority : priorities) {
			List<BehaviorPhase<T>> phasesForPriority = phases.get(priority).stream().filter(p -> p.canStart(entity, cooldowns.getOrDefault(p.getId(), 0) <= 0)).toList();
			if (!phasesForPriority.isEmpty()) {
				return Optional.ofNullable(phasesForPriority.get(entity.getRandom().nextInt(phasesForPriority.size())));
			}
		}
		return Optional.empty();
	}

	private Optional<BehaviorPhase<T>> getActivePhase() {
		return phaseList.stream().filter(p -> entity.getBehaviorState() == p.getId()).findFirst();
	}

	private boolean canContinue(BehaviorPhase<T> phase) {
		return phase.canContinue(entity) && entity.getBehaviorTicks() <= phase.getDuration();
	}
}
