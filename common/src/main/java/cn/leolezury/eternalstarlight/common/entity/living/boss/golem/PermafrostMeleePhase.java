package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PermafrostMeleePhase extends BehaviorPhase<Permafrost> {
	public static final int ID = 1;

	public PermafrostMeleePhase() {
		super(ID, 1, 20, 100, PermafrostMeleeTransitionPhase.ID);
	}

	@Override
	public boolean canStart(Permafrost entity, boolean cooldownOver) {
		boolean canReachGround = false;
		LivingEntity target = entity.getTarget();
		if (target != null) {
			BlockHitResult result = entity.level().clip(new ClipContext(entity.position(), new Vec3(entity.getX(), target.getY() - 5, entity.getZ()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			canReachGround = result.getType() != HitResult.Type.MISS;
		}
		return cooldownOver && canReachTarget(entity, 4) && canReachGround;
	}

	@Override
	public void tick(Permafrost entity) {
		if (entity.getTarget() != null) {
			LivingEntity target = entity.getTarget();
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}
		entity.hurtMarked = true;
		entity.addDeltaMovement(new Vec3(0, entity.getBehaviorTicks() <= 7 ? 0.12 : -0.1, 0));
	}

	@Override
	public boolean canContinue(Permafrost entity) {
		return true;
	}
}
