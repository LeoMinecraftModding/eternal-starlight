package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public class GatekeeperTeleportPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 12;

	public GatekeeperTeleportPhase() {
		super(ID, 1, 37, 120);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver && entity.getTarget() != null && (entity.isGatekeeperStuck() || !canReachTarget(entity, 40));
	}
	@Override
	public void tick(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		if (entity.getBehaviorTicks() == 17 && target != null) {
			BlockPos oldPos = entity.blockPosition();
			entity.randomTeleport(target.getX(), target.getY(), target.getZ(), false);
			BlockPos newPos = entity.blockPosition();
			if (oldPos.equals(newPos)) {
				entity.setPos(target.position());
			}
		}
	}

	@Override
	public boolean canContinue(TheGatekeeper entity) {
		return true;
	}
}
