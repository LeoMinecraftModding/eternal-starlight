package cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class EctostoneSmashPhase extends BehaviorPhase<Ectostone> {
	public static final int ID = 2;

	public EctostoneSmashPhase() {
		super(ID, 1, 20, 100, EctostoneSmashTransitionPhase.ID);
	}

	@Override
	public boolean canStart(Ectostone entity, boolean cooldownOver) {
		return cooldownOver && !entity.isDormant() && canReachTarget(entity, 4) && entity.hasGroundBelow(Ectostone.GROUND_CHECK_DISTANCE);
	}

	@Override
	public void tick(Ectostone entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}
		entity.hurtMarked = true;
		entity.addDeltaMovement(new Vec3(0, entity.getBehaviorTicks() <= 7 ? 0.12 : -0.1, 0));
	}
}
