package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviourPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class GatekeeperSwingSwordPhase extends BehaviourPhase<TheGatekeeper> {
	public static final int ID = 6;

	public GatekeeperSwingSwordPhase() {
		super(ID, 1, 61, 100);
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
		if (entity.getBehaviourTicks() < 40 && entity.level() instanceof ServerLevel serverLevel) {
			Vec3 pos = entity.position().add(0, entity.getBbHeight() * 0.5, 0).offsetRandom(entity.getRandom(), 6);
			Vec3 delta = entity.position().add(0, entity.getBbHeight() * 0.5, 0).subtract(pos).normalize();
			ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ParticleTypes.CRIT, pos.x, pos.y, pos.z, delta.x, delta.y, delta.z));
		}
		if (entity.getTarget() != null) {
			LivingEntity target = entity.getTarget();
			if (entity.getBehaviourTicks() == 40) {
				for (int x = -4; x <= 4; x++) {
					for (int y = -2; y <= 2; y++) {
						for (int z = -4; z <= 4; z++) {
							BlockPos pos = entity.blockPosition().offset(x, y, z);
							if (entity.level().getBlockState(pos.above()).isAir() && pos.getCenter().distanceTo(entity.position()) <= 4) {
								ESFallingBlock fallingBlock = new ESFallingBlock(entity.level(), pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, entity.level().getBlockState(pos), 100, false);
								fallingBlock.push(0, entity.getRandom().nextDouble() / 12 + 0.2, 0);
								entity.level().addFreshEntity(fallingBlock);
								if (fallingBlock.getBoundingBox().intersects(target.getBoundingBox()) && !canReachTarget(entity, 2)) {
									target.hurtMarked = true;
									target.setDeltaMovement(target.getDeltaMovement().add(entity.position().subtract(target.position()).normalize().scale(0.5)));
								}
							}
						}
					}
				}
			}
			if (entity.getBehaviourTicks() >= 47 && entity.getBehaviourTicks() <= 51 && performMeleeAttack(entity, 3)) {
				target.hurtMarked = true;
				target.setDeltaMovement(target.getDeltaMovement().add(entity.position().subtract(target.position()).normalize().scale(-3).add(0, 0.2, 0)));
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
