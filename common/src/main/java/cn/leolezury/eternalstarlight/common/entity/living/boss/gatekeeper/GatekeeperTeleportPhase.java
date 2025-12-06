package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import net.minecraft.world.entity.LivingEntity;

public class GatekeeperTeleportPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 8;

	public GatekeeperTeleportPhase() {
		super(ID, 1, 60, 120);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver && entity.getTarget() != null && (entity.viewBlockedTime > 60 || entity.distanceTo(entity.getTarget()) > 25);
	}

	@Override
	public void onStart(TheGatekeeper entity) {

	}

	@Override
	public void tick(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		if (entity.getBehaviorTicks() == 25 && target != null) {
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
