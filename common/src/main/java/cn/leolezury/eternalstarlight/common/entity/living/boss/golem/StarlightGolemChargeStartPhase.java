package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviourPhase;

public class StarlightGolemChargeStartPhase extends BehaviourPhase<StarlightGolem> {
    public static final int ID = 4;

    public StarlightGolemChargeStartPhase() {
        super(ID, 1, 20, 1800, StarlightGolemChargePhase.ID);
    }

    @Override
    public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
        return cooldownOver && entity.getTarget() != null;
    }

    @Override
    public void onStart(StarlightGolem entity) {
        entity.litAllEnergyBlocks();
        entity.clearHurtCount();
    }

    @Override
    public void tick(StarlightGolem entity) {
        if (!entity.canHurt()) {
            entity.heal(0.04f);
        }
    }

    @Override
    public boolean canContinue(StarlightGolem entity) {
        return true;
    }

    @Override
    public void onStop(StarlightGolem entity) {

    }
}
