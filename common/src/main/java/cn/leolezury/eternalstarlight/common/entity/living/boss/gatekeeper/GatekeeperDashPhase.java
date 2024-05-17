package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.AttackPhase;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.world.entity.LivingEntity;

public class GatekeeperDashPhase extends AttackPhase<TheGatekeeper> {
    public static final int ID = 3;
    private boolean attacked = false;

    public GatekeeperDashPhase() {
        super(ID, 1, 20, 80);
    }

    @Override
    public boolean canStart(TheGatekeeper entity, boolean coolDownOver) {
        return coolDownOver && canReachTarget(entity, 10) && !canReachTarget(entity, 3);
    }

    @Override
    public void onStart(TheGatekeeper entity) {
        attacked = false;
        LivingEntity target = entity.getTarget();
        if (target != null) {
            entity.hurtMarked = true;
            entity.setDeltaMovement(entity.getDeltaMovement().add(target.position().subtract(entity.position()).normalize().scale(3)));
            float yRot = ESMathUtil.positionToYaw(entity.position(), target.position()) - 90;
            entity.setFixedYRot(yRot);
            entity.getNavigation().stop();
        }
    }

    @Override
    public void tick(TheGatekeeper entity) {
        if (!attacked && canReachTarget(entity, 2)) {
            performMeleeAttack(entity, 2);
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
