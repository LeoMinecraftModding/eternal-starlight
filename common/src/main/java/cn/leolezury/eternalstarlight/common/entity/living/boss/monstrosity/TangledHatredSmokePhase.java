package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviourPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class TangledHatredSmokePhase extends BehaviourPhase<TangledHatred> {
	public static final int ID = 1;

	public TangledHatredSmokePhase() {
		super(ID, 1, 100, 700);
	}

	@Override
	public boolean canStart(TangledHatred entity, boolean cooldownOver) {
		return cooldownOver && entity.getTarget() != null;
	}

	@Override
	public void onStart(TangledHatred entity) {
		CameraShake.createCameraShake(entity.level(), entity.position(), 45, 0.005f, 100, 20);
	}

	@Override
	public void tick(TangledHatred entity) {
		if (entity.level() instanceof ServerLevel serverLevel) {
			int radius = entity.getBehaviourTicks() / 5;
			for (int angle = 0; angle <= 360; angle += 15) {
				Vec3 vec3 = ESMathUtil.rotationToPosition(entity.position(), radius, 0, angle).offsetRandom(entity.getRandom(), 2);
				for (int m = 0; m < serverLevel.players().size(); ++m) {
					ServerPlayer serverPlayer = serverLevel.players().get(m);
					serverLevel.sendParticles(serverPlayer, ESExplosionParticleOptions.LUNAR, true, vec3.x, vec3.y, vec3.z, 3, 0, 0, 0, 0);
				}
			}
			for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius))) {
				if (!living.getUUID().equals(entity.getUUID()) && living.distanceTo(entity) - living.getBbWidth() / 2 < radius) {
					living.hurt(ESDamageTypes.getEntityDamageSource(entity.level(), ESDamageTypes.POISON, entity), 5);
					living.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
				}
			}
		}
	}

	@Override
	public boolean canContinue(TangledHatred entity) {
		return true;
	}

	@Override
	public void onStop(TangledHatred entity) {

	}
}
