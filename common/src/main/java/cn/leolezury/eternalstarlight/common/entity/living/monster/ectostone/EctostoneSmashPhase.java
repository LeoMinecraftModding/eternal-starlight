package cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ParticleFacing;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.EasingCurve;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class EctostoneSmashPhase extends BehaviorPhase<Ectostone> {
	public static final int ID = 2;

	private static final int JUMP_DURATION = 20;
	private static final int LAND_MAX_DURATION = 30;

	public EctostoneSmashPhase() {
		super(ID, 1, 100, 100);
	}

	@Override
	public boolean canStart(Ectostone entity, boolean cooldownOver) {
		boolean canReachGround = false;
		LivingEntity target = entity.getTarget();
		if (target != null) {
			BlockHitResult result = entity.level().clip(new ClipContext(entity.position(), new Vec3(entity.getX(), target.getY() - 5, entity.getZ()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			canReachGround = result.getType() != HitResult.Type.MISS;
		}
		return cooldownOver && canReachTarget(entity, 4) && canReachGround;
	}

	@Override
	public void tick(Ectostone entity) {
		Level level = entity.level();
		LivingEntity target = entity.getTarget();

		if (entity.getBehaviorTicks() <= JUMP_DURATION) {
			if (target != null) {
				ESEntityUtil.instantLook(entity, target.getEyePosition());
			}
			entity.hurtMarked = true;
			entity.addDeltaMovement(new Vec3(0, entity.getBehaviorTicks() <= 7 ? 0.12 : -0.1, 0));
			return;
		}

		if (entity.getBehaviorTicks() - JUMP_DURATION <= LAND_MAX_DURATION) {
			if (target != null) {
				ESEntityUtil.instantLook(entity, target.getEyePosition());
			}
			entity.hurtMarked = true;
			entity.addDeltaMovement(new Vec3(0, -0.7, 0));

			BlockHitResult ground = level.clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 0.5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, entity));
			if (ground.getType() != HitResult.Type.MISS) {
				entity.setBehaviorTicks(JUMP_DURATION + LAND_MAX_DURATION);
			}
			return;
		}

		int endTick = entity.getBehaviorTicks() - JUMP_DURATION - LAND_MAX_DURATION;

		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}

		BlockHitResult result = level.clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
		if (result.getType() != HitResult.Type.MISS) {
			entity.setPos(result.getLocation());
		}

		if (endTick == 3 || (endTick >= 10 && endTick <= 25)) {
			if (endTick == 3) {
				BlockHitResult toGround = level.clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 0.5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
				if (toGround.getType() != HitResult.Type.MISS && level instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 5; i++) {
						ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, new ParticlePacket(
							ExplosionShockParticleOptions.fromIntColor(
								new Vector3f(255, 71, 180),
								new Vector3f(151, 63, 115),
								0.5f, 0.1f, 0.6f),
							entity.getX(), entity.getY() + 0.25, entity.getZ(),
							(entity.getRandom().nextDouble() - 0.5) * 0.2,
							(entity.getRandom().nextDouble() - 0.5) * 0.2,
							(entity.getRandom().nextDouble() - 0.5) * 0.2));
					}
					entity.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 0.7f, 1.2f);
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.fromIntColor(new Vector3f(255, 71, 180), new Vector3f(151, 63, 115), 2), entity.getX(), entity.getY(), entity.getZ(), 0, 0.1, 0));
					serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(entity.getOnPos())), entity.getX(), entity.getY(), entity.getZ(), 150, 0.5, 0.5, 0.5, 0.15);
					ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 40, 20, 0.2f, 0.3f, 3, 5.5f).send(serverLevel);
				}
			}
			performDefaultMeleeAttack(entity, endTick == 3 ? 2.5 : 1.5, true, 180, e -> {
				e.hurtMarked = true;
				e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(0.75, 0.5, 0.75));
			});
		}
	}

	@Override
	public boolean canContinue(Ectostone entity) {
		return true;
	}
}