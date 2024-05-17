package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.MeleeAttackPhase;

public class GatekeeperMeleePhase extends MeleeAttackPhase<TheGatekeeper> {
    public static final int ID = 1;

    public GatekeeperMeleePhase() {
        super(ID, 1, 40, 50);
        this.with(3, 30);
    }

    @Override
    public boolean performMeleeAttack(TheGatekeeper entity, double range) {
        boolean flag = super.performMeleeAttack(entity, range);
        if (flag) {
            entity.spawnMeleeAttackParticles();
        }
        return flag;
    }
}
