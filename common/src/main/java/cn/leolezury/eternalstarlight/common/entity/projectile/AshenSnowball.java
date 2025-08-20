package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class AshenSnowball extends ThrowableItemProjectile {
	public AshenSnowball(EntityType<? extends AshenSnowball> entityType, Level level) {
		super(entityType, level);
	}

	public AshenSnowball(Level level, LivingEntity livingEntity) {
		super(ESEntities.ASHEN_SNOWBALL.get(), livingEntity, level);
	}

	public AshenSnowball(Level level, double x, double y, double z) {
		super(ESEntities.ASHEN_SNOWBALL.get(), x, y, z, level);
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (hitResult.getType() != HitResult.Type.MISS && level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, getItem()), this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 5, 0.2, 0.2, 0.2, 0.0);
			serverLevel.sendParticles(ESParticles.ASHEN_SNOW.get(), this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 5, 0.2, 0.2, 0.2, 0.0);
			for (int i = 0; i <= 5; i++) {
				serverLevel.sendParticles(ESParticles.ORBITAL_ASHEN_SNOW.get(), this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + (random.nextFloat() - 0.5f) * getBbHeight() * 8, this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 5, 0.2, 1.5, 0.2, 0.0);
			}
			for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(3))) {
				if (ESEntityUtil.shouldHarm(getOwner(), entity)) {
					entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60));
				}
			}
			discard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		Entity entity = hitResult.getEntity();
		float i = entity instanceof Blaze ? 5 : 1.5f;
		if (entity.hurt(this.damageSources().thrown(this, this.getOwner()), i) && entity instanceof LivingEntity living) {
			living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50));
		}
	}

	@Override
	protected @NotNull Item getDefaultItem() {
		return ESItems.ASHEN_SNOWBALL.get();
	}
}
