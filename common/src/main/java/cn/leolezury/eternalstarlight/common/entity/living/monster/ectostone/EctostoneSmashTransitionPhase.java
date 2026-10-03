package cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EctostoneSmashTransitionPhase extends BehaviorPhase<Ectostone> {
	public static final int ID = 3;

	public EctostoneSmashTransitionPhase() {
		super(ID, 1, 200, 0, EctostoneSmashEndPhase.ID);
	}

	@Override
	public boolean canStart(Ectostone entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void tick(Ectostone entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}
		entity.hurtMarked = true;
		entity.addDeltaMovement(new Vec3(0, -0.7, 0));
	}

	@Override
	public boolean canContinue(Ectostone entity) {
		BlockHitResult ground = entity.level().clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 0.5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, entity));
		// stop diving when the floor is gone, so sky island variants do not drop into the void
		return ground.getType() == HitResult.Type.MISS && entity.hasGroundBelow(Ectostone.GROUND_CHECK_DISTANCE);
	}
}
