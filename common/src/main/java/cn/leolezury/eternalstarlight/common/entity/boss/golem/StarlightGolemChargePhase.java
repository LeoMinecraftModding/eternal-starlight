package cn.leolezury.eternalstarlight.common.entity.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.boss.AttackPhase;

public class StarlightGolemChargePhase extends AttackPhase<StarlightGolem> {
    public static final int ID = 6;

    public StarlightGolemChargePhase() {
        super(ID, 1, 600, 0, StarlightGolemChargeEndPhase.ID);
    }

    @Override
    public boolean canStart(StarlightGolem entity, boolean coolDownOver) {
        return false;
    }

    @Override
    public void onStart(StarlightGolem entity) {

    }

    @Override
    public void tick(StarlightGolem entity) {
        if (!entity.canHurt()) {
            entity.heal(0.04f);
        }
    }

    @Override
    public boolean canContinue(StarlightGolem entity) {
        return entity.getHurtCount() < 5;
    }

    @Override
    public void onStop(StarlightGolem entity) {

    }
}
