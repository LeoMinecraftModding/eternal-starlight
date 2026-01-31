package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class FrozenBomb extends ThrowableItemProjectile {
	public FrozenBomb(EntityType<? extends FrozenBomb> entityType, Level level) {
		super(entityType, level);
	}

	public FrozenBomb(Level level, LivingEntity livingEntity) {
		super(ESEntities.FROZEN_BOMB.get(), livingEntity, level);
	}

	public FrozenBomb(Level level, double x, double y, double z) {
		super(ESEntities.FROZEN_BOMB.get(), x, y, z, level);
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (hitResult.getType() != HitResult.Type.MISS && level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, getItem()), this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 5, 0.2, 0.2, 0.2, 0.0);
			serverLevel.sendParticles(ESParticles.ASHEN_SNOW.get(), this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 5, 0.2, 0.2, 0.2, 0.0);
			for (int i = 0; i <= 10; i++) {
				serverLevel.sendParticles(ESParticles.ORBITAL_ASHEN_SNOW.get(), this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + (random.nextFloat() - 0.5f) * getBbHeight() * 8, this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 5, 0.2, 1.5, 0.2, 0.0);
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.FROZEN, getX() + (random.nextFloat() - 0.5f) * getBbWidth() * 5, getY(), getZ() + (random.nextFloat() - 0.5f) * getBbWidth() * 5, 0, 1, 0));
			}
			serverLevel.sendParticles(ESExplosionParticleOptions.FROZEN, this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 10, 1.5, 1.5, 1.5, 0);
			serverLevel.sendParticles(ESExplosionParticleOptions.FROZEN_BLAST, this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 3, 0.2, 0.2, 0.2, 0.0);
			level().explode(this, null, null, this.getX(), this.getY(), this.getZ(), 3, false, Level.ExplosionInteraction.TNT, ESExplosionParticleOptions.FROZEN, ESExplosionParticleOptions.FROZEN, SoundEvents.GENERIC_EXPLODE);
			for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(3))) {
				if (ESEntityUtil.shouldHarm(getOwner(), entity)) {
					if (entity.canFreeze()) {
						entity.setTicksFrozen(Math.min(entity.getTicksFrozen() + 100, 300));
					}
					entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60));
				}
			}
			ScreenShakeVfx.createInstance(level().dimension(), position(), 30, 20, 0.2f, 0.24f, 4, 5).send(serverLevel);
			discard();
		}
	}

	@Override
	protected @NotNull Item getDefaultItem() {
		return ESItems.FROZEN_BOMB.get();
	}
}
