package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.boss.AttackPhase;

public class GatekeeperComboPhase extends AttackPhase<TheGatekeeper> {
    public static final int ID = 7;

    public GatekeeperComboPhase() {
        super(ID, 1, 75, 50);
    }

    @Override
    public boolean canStart(TheGatekeeper entity, boolean coolDownOver) {
        return coolDownOver && entity.canReachTarget(2);
    }

    @Override
    public void onStart(TheGatekeeper entity) {

    }

    @Override
    public void tick(TheGatekeeper entity) {
        int ticks = entity.getAttackTicks();
        if ((ticks >= 30 && ticks <= 33) || (ticks >= 35 && ticks <= 40)|| (ticks >= 44 && ticks <= 47)|| (ticks >= 60 && ticks <= 65)) {
            entity.performMeleeAttack(2, false);
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
