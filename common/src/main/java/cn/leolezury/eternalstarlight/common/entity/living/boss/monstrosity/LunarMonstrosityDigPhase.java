package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviourPhase;

public class LunarMonstrosityDigPhase extends BehaviourPhase<LunarMonstrosity> {
    public static final int ID = 5;

    public LunarMonstrosityDigPhase() {
        super(ID, 1, 30, 300, LunarMonstrositySneakPhase.ID);
    }

    @Override
    public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
        return cooldownOver && entity.getTarget() != null && !canReachTarget(entity, 10);
    }

    @Override
    public void onStart(LunarMonstrosity entity) {

    }

    @Override
    public void tick(LunarMonstrosity entity) {

    }

    @Override
    public boolean canContinue(LunarMonstrosity entity) {
        return true;
    }

    @Override
    public void onStop(LunarMonstrosity entity) {

    }
}
