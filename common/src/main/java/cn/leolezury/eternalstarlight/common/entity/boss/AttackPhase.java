package cn.leolezury.eternalstarlight.common.entity.boss;

import net.minecraft.world.entity.LivingEntity;

public abstract class AttackPhase<T extends LivingEntity & MultiPhaseAttacker> {
    private final int id;
    private final int priority;
    private final int duration;
    private final int coolDown;
    private final int turnsInto;

    public AttackPhase(int id, int priority, int duration, int coolDown) {
        this(id, priority, duration, coolDown, 0);
    }

    public AttackPhase(int id, int priority, int duration, int coolDown, int turnsInto) {
        this.id = id;
        this.priority = priority;
        this.duration = duration;
        this.coolDown = coolDown;
        this.turnsInto = turnsInto;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public int getDuration() {
        return duration;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public abstract boolean canStart(T entity, boolean coolDownOver);
    public abstract void onStart(T entity);
    public abstract void tick(T entity);
    public abstract boolean canContinue(T entity);
    public abstract void onStop(T entity);

    public void start(T entity) {
        entity.setAttackState(getId());
        entity.setAttackTicks(0);
        onStart(entity);
    }

    public void stop(T entity) {
        entity.setAttackState(turnsInto);
        onStop(entity);
        entity.setAttackTicks(0);
    }
}
