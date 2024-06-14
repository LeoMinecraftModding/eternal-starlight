package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviourPhase;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class GatekeeperDodgePhase extends BehaviourPhase<TheGatekeeper> {
    public static final int ID = 2;

    public GatekeeperDodgePhase() {
        super(ID, 1, 20, 40);
    }

    private boolean isLookedAt(TheGatekeeper gatekeeper) {
        LivingEntity target = gatekeeper.getTarget();
        if (target == null) {
            return false;
        }
        Vec3 vec3 = target.getViewVector(1.0F).normalize();
        Vec3 vec32 = new Vec3(gatekeeper.getX() - target.getX(), gatekeeper.getEyeY() - target.getEyeY(), gatekeeper.getZ() - target.getZ());
        double d = vec32.length();
        vec32 = vec32.normalize();
        double e = vec3.dot(vec32);
        return e > 1.0 - 0.025 / d && target.hasLineOfSight(gatekeeper);
    }

    @Override
    public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
        return cooldownOver && isLookedAt(entity);
    }

    @Override
    public void onStart(TheGatekeeper entity) {
        LivingEntity target = entity.getTarget();
        if (target != null) {
            float yaw = ESMathUtil.positionToYaw(target.position(), entity.position());
            float pitch = ESMathUtil.positionToPitch(target.position(), entity.position());
            yaw += entity.getRandom().nextBoolean() ? 10f : -10f;
            Vec3 newPos = ESMathUtil.rotationToPosition(target.position(), entity.distanceTo(target), pitch, yaw);
            entity.hurtMarked = true;
            entity.setDeltaMovement(entity.getDeltaMovement().add(newPos.subtract(entity.position()).normalize()));
        }
    }

    @Override
    public void tick(TheGatekeeper entity) {

    }

    @Override
    public boolean canContinue(TheGatekeeper entity) {
        return true;
    }

    @Override
    public void onStop(TheGatekeeper entity) {

    }
}
