package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SolarCreeperJumpStartPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 4;

	public SolarCreeperJumpStartPhase() {
		super(ID, 1, 40, 80, SolarCreeperJumpTransitionPhase.ID);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && !canReachTargetXZ(entity, 8) && entity.getTarget() != null;
	}

	@Override
	public void tick(SolarCreeper entity) {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}
		if (entity.getBehaviorTicks() == 15) {
			entity.hurtMarked = true;
			entity.addDeltaMovement(new Vec3(0, 1.5, 0));
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
