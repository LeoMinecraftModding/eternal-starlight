package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SolarCreeperJumpTransitionPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 5;

	public SolarCreeperJumpTransitionPhase() {
		super(ID, 1, 200, 0, SolarCreeperJumpEndPhase.ID);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void onStart(SolarCreeper entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			entity.hurtMarked = true;
			entity.addDeltaMovement(target.position().subtract(entity.position()).normalize().scale(1.5));
		}
	}

	@Override
	public void tick(SolarCreeper entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}
		entity.hurtMarked = true;
		entity.addDeltaMovement(new Vec3(0, -0.5, 0));
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		BlockHitResult result = entity.level().clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 0.5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, entity));
		return result.getType() == HitResult.Type.MISS;
	}
}
