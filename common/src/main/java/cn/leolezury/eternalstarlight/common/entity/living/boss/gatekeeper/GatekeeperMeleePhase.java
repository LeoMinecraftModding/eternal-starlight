package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.boss.AttackPhase;

public class GatekeeperMeleePhase extends AttackPhase<TheGatekeeper> {
    public static final int ID = 1;

    public GatekeeperMeleePhase() {
        super(ID, 1, 40, 50);
    }

    @Override
    public boolean canStart(TheGatekeeper entity, boolean coolDownOver) {
        return coolDownOver && entity.canReachTarget(3);
    }

    @Override
    public void onStart(TheGatekeeper entity) {

    }

    @Override
    public void tick(TheGatekeeper entity) {
        if (entity.getAttackTicks() == 30) {
            entity.performMeleeAttack(3);
        }
    }

    @Override
    public boolean canContinue(TheGatekeeper entity) {
        return true;
    }

    @Override
    public void onStop(TheGatekeeper entity) {

    }
}
