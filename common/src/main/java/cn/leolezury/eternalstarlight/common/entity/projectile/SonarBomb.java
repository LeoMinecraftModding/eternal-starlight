package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SonarBomb extends ThrowableItemProjectile {
	public SonarBomb(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
		super(entityType, level);
	}

	public SonarBomb(Level level, LivingEntity livingEntity) {
		super(ESEntities.SONAR_BOMB.get(), livingEntity, level);
	}

	public SonarBomb(Level level, double x, double y, double z) {
		super(ESEntities.SONAR_BOMB.get(), x, y, z, level);
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (hitResult.getType() != HitResult.Type.MISS && level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, getItem()), this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 5, 0.2, 0.2, 0.2, 0.0);
			serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 3, 0.2, 0.2, 0.2, 0.0);
			serverLevel.sendParticles(ParticleTypes.SMOKE, this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 2, 0.2, 0.2, 0.2, 0.0);
			serverLevel.sendParticles(ParticleTypes.WHITE_SMOKE, this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 2, 0.2, 0.2, 0.2, 0.0);
			for (LivingEntity livingEntity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(20))) {
				if (!ownedBy(livingEntity)) {
					livingEntity.hurt(level().damageSources().mobProjectile(this, getOwner() instanceof LivingEntity owner ? owner : null), livingEntity.getType().is(ESTags.EntityTypes.VULNERABLE_TO_SONAR_BOMB) ? 20 : 5);
					Vec3 delta = livingEntity.position().add(0, livingEntity.getBbHeight() / 2, 0).subtract(position()).normalize().scale(1.1);
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), getX(), getY(), getZ(), delta.x, delta.y, delta.z));
				}
			}
			playSound(SoundEvents.GENERIC_EXPLODE.value());
			playSound(ESSoundEvents.SONAR_BOMB_EXPLODE.get());
			ScreenShakeVfx.createInstance(level().dimension(), position(), 30, 30, 0.15f, 0.24f, 4, 5).send(serverLevel);
			discard();
		}
	}

	@Override
	protected @NotNull Item getDefaultItem() {
		return ESItems.SONAR_BOMB.get();
	}
}
