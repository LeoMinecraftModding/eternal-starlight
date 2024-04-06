package cn.leolezury.eternalstarlight.common.entity.living.boss;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttackManager<T extends LivingEntity & MultiPhaseAttacker> {
    private final T entity;
    private final List<AttackPhase<T>> phaseList = new ArrayList<>();
    private final Int2ObjectArrayMap<List<AttackPhase<T>>> phases = new Int2ObjectArrayMap<>();
    private final IntArrayList priorities = new IntArrayList();
    private final Int2IntArrayMap coolDowns = new Int2IntArrayMap();

    public Int2IntArrayMap getCoolDowns() {
        return coolDowns;
    }

    public AttackManager(T entity, List<AttackPhase<T>> phaseList) {
        this.entity = entity;
        this.phaseList.addAll(phaseList);
        for (AttackPhase<T> phase : phaseList) {
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
        if (entity.getAttackState() == 0) {
            selectPhase().ifPresent(p -> {
                p.start(entity);
                coolDowns.put(p.getId(), p.getCoolDown());
            });
        } else {
            getActivePhase().ifPresent(p -> {
                if (!canContinue(p)) {
                    p.stop(entity);
                } else {
                    p.tick(entity);
                    entity.setAttackTicks(entity.getAttackTicks() + 1);
                }
            });
        }
        for (int id : coolDowns.keySet()) {
            coolDowns.put(id, Math.max(0, coolDowns.get(id) - 1));
        }
    }
    
    private Optional<AttackPhase<T>> selectPhase() {
        for (int priority : priorities) {
            List<AttackPhase<T>> phasesForPriority = phases.get(priority).stream().filter(p -> p.canStart(entity, coolDowns.getOrDefault(p.getId(), 0) <= 0)).toList();
            if (!phasesForPriority.isEmpty()) {
                return Optional.ofNullable(phasesForPriority.get(entity.getRandom().nextInt(phasesForPriority.size())));
            }
        }
        return Optional.empty();
    }
    
    private Optional<AttackPhase<T>> getActivePhase() {
        return phaseList.stream().filter(p -> entity.getAttackState() == p.getId()).findFirst();
    }

    private boolean canContinue(AttackPhase<T> phase) {
        return phase.canContinue(entity) && entity.getAttackTicks() <= phase.getDuration();
    }
}
