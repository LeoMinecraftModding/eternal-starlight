package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import net.minecraft.world.entity.LivingEntity;

public class GatekeeperTeleportPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 12;

	public GatekeeperTeleportPhase() {
		super(ID, 1, 37, 120);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver && entity.getTarget() != null && (entity.viewBlockedTime > 100 || !canReachTarget(entity, 40));
	}

	@Override
	public void onStart(TheGatekeeper entity) {
	}

	@Override
	public void tick(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		if (entity.getBehaviorTicks() == 17 && target != null) {
			entity.randomTeleport(target.getX(), target.getY(), target.getZ(), false);
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
