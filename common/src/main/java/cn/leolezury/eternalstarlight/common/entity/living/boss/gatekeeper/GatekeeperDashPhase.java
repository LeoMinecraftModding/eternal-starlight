package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.world.entity.LivingEntity;

public class GatekeeperDashPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 3;
	private boolean attacked = false;

	public GatekeeperDashPhase() {
		super(ID, 2, 25, 80);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver && canReachTarget(entity, 10) && !canReachTarget(entity, 3);
	}

	@Override
	public void onStart(TheGatekeeper entity) {
		attacked = false;
	}

	@Override
	public void tick(TheGatekeeper entity) {
		entity.getNavigation().stop();
		LivingEntity target = entity.getTarget();
		if (!attacked && entity.getBehaviorTicks() > 8 && canReachTarget(entity, 2)) {
			performMeleeAttack(entity, 2);
			attacked = true;
		}
		if (target != null) {
			if (entity.getBehaviorTicks() == 8) {
				entity.hurtMarked = true;
				entity.setDeltaMovement(entity.getDeltaMovement().add(target.position().subtract(entity.position()).normalize().scale(3)));
				float yRot = ESMathUtil.positionToYaw(entity.position(), target.position()) - 90;
				entity.setFixedYRot(yRot);
				entity.getNavigation().stop();
			}
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
