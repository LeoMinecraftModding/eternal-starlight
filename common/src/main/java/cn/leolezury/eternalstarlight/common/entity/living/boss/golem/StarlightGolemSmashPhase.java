package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class StarlightGolemSmashPhase extends BehaviorPhase<StarlightGolem> {
	public static final int ID = 3;

	private float pitch, yaw;
	private final List<BlockPos> visited = new ArrayList<>();
	private final List<BlockPos> lavaVisited = new ArrayList<>();

	public StarlightGolemSmashPhase() {
		super(ID, 2, 100, 250);
	}

	@Override
	public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
		return cooldownOver && entity.getTarget() != null;
	}

	@Override
	public void onStart(StarlightGolem entity) {
		visited.clear();
		lavaVisited.clear();
	}

	@Override
	public void tick(StarlightGolem entity) {
		if (entity.getBehaviorTicks() == 30) {
			pitch = entity.getTarget() != null ? ESMathUtil.positionToPitch(entity.position(), entity.getTarget().position()) : 0;
			yaw = entity.getYHeadRot() + 90f;
		}
		if (entity.getBehaviorTicks() == 40 && entity.level() instanceof ServerLevel serverLevel) {
			ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 40, 50, 0.24f, 0.5f, 3, 5.5f).send(serverLevel);
		}
		if (entity.getBehaviorTicks() >= 30) {
			int radius = (int) ((entity.getBehaviorTicks() - 30f) / 3.5f);
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					for (int y = -radius; y <= radius; y++) {
						BlockPos pos = entity.blockPosition().offset(x, y, z);
						if (entity.level().getBlockState(pos).getFluidState().is(Fluids.LAVA)) {
							if (!lavaVisited.contains(pos)) {
								lavaVisited.add(pos);
								if (entity.getRandom().nextInt(25) == 0) {
									entity.level().setBlockAndUpdate(pos, Blocks.MAGMA_BLOCK.defaultBlockState());
									if (!entity.level().isClientSide) {
										((ServerLevel) entity.level()).sendParticles(ESExplosionParticleOptions.LAVA, pos.getCenter().x, pos.getCenter().y + 0.6, pos.getCenter().z, 1, 0, 0, 0, 0);
									}
								}
							}
						} else {
							float blockPitch = ESMathUtil.positionToPitch(entity.position(), pos.getCenter());
							float blockYaw = ESMathUtil.positionToYaw(entity.position(), pos.getCenter());
							if (!visited.contains(pos) && !entity.level().getBlockState(pos).isAir() && Math.abs(Mth.wrapDegrees(pitch - blockPitch)) < 75 && Math.abs(Mth.wrapDegrees(yaw - blockYaw)) < 30 && pos.getCenter().distanceTo(entity.position()) <= radius && pos.getCenter().distanceTo(entity.position()) >= radius - 1) {
								boolean above = entity.level().getBlockState(pos.above()).isAir();
								boolean below = entity.level().getBlockState(pos.below()).isAir();
								if (above || below) {
									visited.add(pos);
									boolean flag = true;
									if (!above) {
										flag = entity.getRandom().nextInt(6) == 0;
									}
									if (flag) {
										ESFallingBlock fallingBlock = new ESFallingBlock(entity.level(), pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, entity.level().getBlockState(pos), 100);
										fallingBlock.push(0, (above ? 1 : -1) * entity.getRandom().nextDouble() / 6 + 0.25, 0);
										entity.level().addFreshEntity(fallingBlock);
										for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(1))) {
											if (!living.getUUID().equals(entity.getUUID())) {
												living.hurt(ESDamageTypes.getDamageSource(entity.level(), ESDamageTypes.GROUND_SMASH), 4 * (float) ESConfig.INSTANCE.mobsConfig.starlightGolem.attackDamageScale());
											}
										}
										if (!entity.level().isClientSide) {
											((ServerLevel) entity.level()).sendParticles(ESExplosionParticleOptions.ENERGY, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, 1, 0, 0, 0, 0);
											if (entity.getRandom().nextInt(5) == 0) {
												((ServerLevel) entity.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getCenter().x, pos.getCenter().y + 0.5, pos.getCenter().z, 1, 0, 0, 0, 0);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canContinue(StarlightGolem entity) {
		return true;
	}

	@Override
	public void onStop(StarlightGolem entity) {

	}
}
