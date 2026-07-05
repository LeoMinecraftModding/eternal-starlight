package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.Vec3;

public class SolarCreeperSolarWindPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 8;

	public SolarCreeperSolarWindPhase() {
		super(ID, 1, 70, 250);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && canReachTarget(entity, 5);
	}

	@Override
	public void tick(SolarCreeper entity) {
		LivingEntity target = entity.getTarget();
		RandomSource random = entity.getRandom();
		int ticks = entity.getBehaviorTicks();
		if (target == null) {
			return;
		}
		if (ticks > 10 && ticks < 60 && entity.level() instanceof ServerLevel serverLevel) {
			for (LivingEntity livingEntity : entity.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, entity, entity.getBoundingBox().inflate(5))) {
				if ((livingEntity == target || livingEntity instanceof Targeting t && t.getTarget() == entity)
					&& (isFacingEntity(entity, livingEntity, 45, entity.getYRot())
					|| isFacingEntity(entity, livingEntity, 45, entity.yBodyRot))) {
					entity.doHurtTarget(livingEntity);
					livingEntity.hurtMarked = true;
					livingEntity.addDeltaMovement(livingEntity.position().subtract(entity.position()).normalize().multiply(1.5, 0.5, 1.5));
				}
			}
			Vec3 pos = entity.getEyePosition().offsetRandom(random, (entity.getBbHeight() - entity.getEyeHeight()) * 2);
			Vec3 speed = ESMathUtil.rotationToPosition(1, -entity.getXRot(), entity.yBodyRot + 90).offsetRandom(random, 0.1f).normalize();
			ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.FLARE_LONG, pos.x, pos.y, pos.z, speed.x, speed.y, speed.z));
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
