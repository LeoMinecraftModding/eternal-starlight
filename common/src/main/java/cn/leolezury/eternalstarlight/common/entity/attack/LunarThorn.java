package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LunarThorn extends AttackEffect {
	public float oldClientScale = 0f;
	public float clientScale = 0f;

	public LunarThorn(EntityType<? extends AttackEffect> type, Level level) {
		super(type, level);
	}

	@Override
	public boolean shouldContinueToTick() {
		return true;
	}

	@Override
	public void setDeltaMovement(Vec3 vec3) {
		super.setDeltaMovement(Vec3.ZERO);
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			if (getSpawnedTicks() >= 200) {
				discard();
			}
			if (getSpawnedTicks() == 10) {
				CameraShake.createCameraShake(level(), position(), 30, 0.001f, 20, 10);
			}
			if (getSpawnedTicks() > 40 && getOwner() != null) {
				if (getAttackMode() == 0) {
					for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(0.5))) {
						if (!livingEntity.getUUID().equals(getOwner().getUUID())) {
							livingEntity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.POISON, this, getOwner()), 4);
						}
					}
				}
				if (getAttackMode() == 1) {
					for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(0.5))) {
						if (!livingEntity.getUUID().equals(getOwner().getUUID())) {
							livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 4));
							livingEntity.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.POISON, this, getOwner()), 3);
						}
					}
				}
			}
		} else {
			float scale = getSpawnedTicks() <= 40 ? getSpawnedTicks() / 40f : 1;
			if (getSpawnedTicks() >= 160) {
				scale = (200 - getSpawnedTicks()) / 40f;
			}
			oldClientScale = clientScale;
			clientScale = scale;
			if (random.nextInt(20) == 0) {
				level().addParticle(ESSmokeParticleOptions.LUNAR_SHORT, getRandomX(1.5), getRandomY(), getRandomZ(1.5), 0, 0, 0);
			}
		}
	}
}
