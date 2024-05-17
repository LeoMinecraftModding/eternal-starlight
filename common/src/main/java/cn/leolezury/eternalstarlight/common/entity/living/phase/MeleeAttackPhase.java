package cn.leolezury.eternalstarlight.common.entity.living.phase;

import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MeleeAttackPhase<T extends LivingEntity & MultiPhaseAttacker> extends AttackPhase<T> {
    private final List<Entry> entries = new ArrayList<>();

    public MeleeAttackPhase(int id, int priority, int duration, int coolDown) {
        super(id, priority, duration, coolDown);
    }

    @Override
    public boolean canStart(T entity, boolean coolDownOver) {
        return coolDownOver && !entries.isEmpty() && canReachTarget(entity, entries.getFirst().range());
    }

    @Override
    public void onStart(T entity) {

    }

    @Override
    public void tick(T entity) {
        for (Entry entry : entries) {
            if (entity.getAttackTicks() == entry.tick()) {
                performMeleeAttack(entity, entry.range());
            }
        }
    }

    @Override
    public boolean canContinue(T entity) {
        return true;
    }

    @Override
    public void onStop(T entity) {

    }

    private record Entry(int range, int tick) {

    }

    public MeleeAttackPhase<T> with(int range, int tick) {
        entries.add(new Entry(range, tick));
        entries.sort(Comparator.comparingInt(Entry::range));
        return this;
    }
}
