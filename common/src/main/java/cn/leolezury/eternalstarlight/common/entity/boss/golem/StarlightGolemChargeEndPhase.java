package cn.leolezury.eternalstarlight.common.entity.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.boss.AttackPhase;

public class StarlightGolemChargeEndPhase extends AttackPhase<StarlightGolem> {
    public static final int ID = 7;

    public StarlightGolemChargeEndPhase() {
        super(ID, 1, 30, 0);
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
        return true;
    }

    @Override
    public void onStop(StarlightGolem entity) {

    }
}
