package cn.leolezury.eternalstarlight.common.entity.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.boss.AttackPhase;
import net.minecraft.world.entity.LivingEntity;

public class GatekeeperDashPhase extends AttackPhase<TheGatekeeper> {
    public static final int ID = 3;
    private boolean attacked = false;

    public GatekeeperDashPhase() {
        super(ID, 1, 20, 80);
    }

    @Override
    public boolean canStart(TheGatekeeper entity, boolean coolDownOver) {
        return coolDownOver && entity.canReachTarget(10) && !entity.canReachTarget(3);
    }

    @Override
    public void onStart(TheGatekeeper entity) {
        attacked = false;
        LivingEntity target = entity.getTarget();
        if (target != null) {
            entity.hurtMarked = true;
            entity.setDeltaMovement(entity.getDeltaMovement().add(target.position().subtract(entity.position()).normalize().scale(3)));
            entity.getNavigation().stop();
        }
    }

    @Override
    public void tick(TheGatekeeper entity) {
        if (!attacked) {
            entity.performMeleeAttack(2);
            attacked = true;
        }
        entity.getNavigation().stop();
        LivingEntity target = entity.getTarget();
        if (target != null) {
            entity.getLookControl().setLookAt(target, 360f, 360f);
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
