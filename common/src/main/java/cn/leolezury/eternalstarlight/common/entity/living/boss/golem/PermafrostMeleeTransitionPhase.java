package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PermafrostMeleeTransitionPhase extends BehaviorPhase<Permafrost> {
	public static final int ID = 2;

	public PermafrostMeleeTransitionPhase() {
		super(ID, 1, 200, 0, PermafrostMeleeEndPhase.ID);
	}

	@Override
	public boolean canStart(Permafrost entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void tick(Permafrost entity) {
		if (entity.getTarget() != null) {
			LivingEntity target = entity.getTarget();
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}
		entity.hurtMarked = true;
		entity.addDeltaMovement(new Vec3(0, -0.7, 0));
	}

	@Override
	public boolean canContinue(Permafrost entity) {
		BlockHitResult result = entity.level().clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 0.5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, entity));
		return result.getType() == HitResult.Type.MISS;
	}
}
