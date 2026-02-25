package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.entity.projectile.EnergySpark;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.LongJumpUtil;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StarlightGolemSmashPhase extends BehaviorPhase<StarlightGolem> {
	public static final int ID = 3;

	private static final ObjectArrayList<Integer> ALLOWED_ANGLES = new ObjectArrayList<>(Lists.newArrayList(15, 20, 25, 30, 35, 40));

	private BlockPos shockwavePos = BlockPos.ZERO;
	private float pitch, yaw;
	private final List<BlockPos> visited = new ArrayList<>();
	private final List<BlockPos> lavaVisited = new ArrayList<>();

	public StarlightGolemSmashPhase() {
		super(ID, 2, 70, 250);
	}

	@Override
	public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
		return (cooldownOver || (entity.getPhase() == 1 && entity.onGround() && entity.getBehaviorManager().getCooldowns().getOrDefault(ID, 0) < 150)) && entity.getTarget() != null && (entity.getPhase() == 1 || entity.getAttackEnergy() >= 0);
	}

	@Override
	public void onStart(StarlightGolem entity) {
		entity.setAttackEnergy(Math.max(entity.getAttackEnergy() - 16, 0));
		visited.clear();
		lavaVisited.clear();
	}

	@Override
	public void tick(StarlightGolem entity) {
		LivingEntity target = entity.getTarget();
		if (entity.getBehaviorTicks() == 30) {
			pitch = target != null ? ESMathUtil.positionToPitch(entity.position(), target.position()) : 0;
			yaw = entity.getYHeadRot() + 90f;
			if (target != null) {
				for (int i = 0; i < 5; i++) {
					EnergySpark spark = new EnergySpark(entity.level(), entity);
					spark.setPos(entity.position().add(0, entity.getBbHeight() / 3f, 0));
					spark.setTarget(target);
					Vec3 movement = ESMathUtil.rotationToPosition(1, pitch, yaw - 30 + 15 * i);
					spark.shoot(movement.x, movement.y, movement.z, 0.1f, 0);
					entity.level().addFreshEntity(spark);
				}
			}
			shockwavePos = entity.blockPosition();
		}
		if (entity.getBehaviorTicks() == 37 && entity.getPhase() == 1 && target != null) {
			for (int i : Util.shuffledCopy(ALLOWED_ANGLES, entity.getRandom())) {
				Optional<Vec3> optional = LongJumpUtil.calculateJumpVectorForAngle(entity, ESMathUtil.rotationToPosition(entity.position(), 1, pitch, yaw), 0.75F, i, false);
				if (optional.isPresent()) {
					entity.hurtMarked = true;
					entity.addDeltaMovement(optional.get());
				}
			}
		}
		if (entity.getBehaviorTicks() == 40 && entity.level() instanceof ServerLevel serverLevel) {
			ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 40, 50, 0.24f, 0.5f, 3, 5.5f).send(serverLevel);
		}
		if (entity.getBehaviorTicks() >= 30) {
			ESEntityUtil.instantLook(entity, ESMathUtil.rotationToPosition(entity.position(), entity.getBbWidth() * 10, pitch, yaw));
		}
		if (entity.getBehaviorTicks() >= 40) {
			int radius = (int) ((entity.getBehaviorTicks() - 30f) / 3f);
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					for (int y = -radius; y <= radius; y++) {
						BlockPos pos = shockwavePos.offset(x, y, z);
						if (entity.level().getBlockState(pos).is(Blocks.LAVA)) {
							if (!lavaVisited.contains(pos)) {
								lavaVisited.add(pos);
								if (entity.getRandom().nextInt(25) == 0 && ESPlatform.INSTANCE.postEntityDestroyBlockEvent(entity.level(), pos, entity)) {
									entity.level().setBlockAndUpdate(pos, Blocks.MAGMA_BLOCK.defaultBlockState());
									if (!entity.level().isClientSide) {
										((ServerLevel) entity.level()).sendParticles(ESExplosionParticleOptions.LAVA, pos.getCenter().x, pos.getCenter().y + 0.6, pos.getCenter().z, 1, 0, 0, 0, 0);
									}
								}
							}
						} else {
							float blockPitch = ESMathUtil.positionToPitch(shockwavePos.getBottomCenter(), pos.getCenter());
							float blockYaw = ESMathUtil.positionToYaw(shockwavePos.getBottomCenter(), pos.getCenter());
							if (!visited.contains(pos) && !entity.level().getBlockState(pos).isAir() && Math.abs(Mth.wrapDegrees(pitch - blockPitch)) < 75 && Math.abs(Mth.wrapDegrees(yaw - blockYaw)) < 30 && pos.getCenter().distanceTo(shockwavePos.getBottomCenter()) <= radius && pos.getCenter().distanceTo(shockwavePos.getBottomCenter()) >= radius - 1) {
								boolean above = entity.level().getBlockState(pos.above()).isAir();
								boolean below = entity.level().getBlockState(pos.below()).isAir();
								if (above || below) {
									visited.add(pos);
									boolean flag = true;
									if (!above) {
										flag = entity.getRandom().nextInt(6) == 0;
									}
									if (flag) {
										ESFallingBlock fallingBlock = new ESFallingBlock(entity.level(), pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, entity.level().getBlockState(pos), 100);
										fallingBlock.push(0, (above ? 1 : -1) * entity.getRandom().nextDouble() / 6 + 0.25, 0);
										entity.level().addFreshEntity(fallingBlock);
										for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(1))) {
											if (living != entity) {
												living.hurt(ESDamageTypes.getDamageSource(entity.level(), ESDamageTypes.GROUND_SMASH), 4);
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
}
