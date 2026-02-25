package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class GatekeeperJumpTransitionPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 3;

	public GatekeeperJumpTransitionPhase() {
		super(ID, 1, 200, 0, GatekeeperJumpEndPhase.ID);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void onStart(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			entity.hurtMarked = true;
			entity.addDeltaMovement(target.position().subtract(entity.position()).normalize().scale(1.5));
		}
	}

	@Override
	public void tick(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}
		entity.hurtMarked = true;
		entity.addDeltaMovement(new Vec3(0, -0.5, 0));
	}

	@Override
	public boolean canContinue(TheGatekeeper entity) {
		BlockHitResult result = entity.level().clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 0.5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, entity));
		return result.getType() == HitResult.Type.MISS;
	}
}
