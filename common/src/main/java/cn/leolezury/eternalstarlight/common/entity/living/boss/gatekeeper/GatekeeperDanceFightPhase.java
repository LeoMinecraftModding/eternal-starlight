package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import net.minecraft.core.BlockPos;

public class GatekeeperDanceFightPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 5;

	public GatekeeperDanceFightPhase() {
		super(ID, 1, 110, 200);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver && canReachTarget(entity, 3);
	}

	@Override
	public void onStart(TheGatekeeper entity) {

	}

	@Override
	public void tick(TheGatekeeper entity) {
		if (entity.getBehaviorTicks() >= 30 && entity.getBehaviorTicks() <= 50) {
			performMeleeAttack(entity, 2);
		}
		if (entity.getBehaviorTicks() == 73) {
			performMeleeAttack(entity, 5);
			for (int x = -5; x <= 5; x++) {
				for (int y = -2; y <= 2; y++) {
					for (int z = -5; z <= 5; z++) {
						BlockPos pos = entity.blockPosition().offset(x, y, z);
						if (entity.level().getBlockState(pos.above()).isAir() && pos.getCenter().distanceTo(entity.position()) <= 5) {
							ESFallingBlock fallingBlock = new ESFallingBlock(entity.level(), pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, entity.level().getBlockState(pos), 100, false);
							fallingBlock.push(0, entity.getRandom().nextDouble() / 6 + 0.25, 0);
							entity.level().addFreshEntity(fallingBlock);
						}
					}
				}
			}
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
