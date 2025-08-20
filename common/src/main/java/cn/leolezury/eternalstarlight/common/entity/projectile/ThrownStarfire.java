package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.*;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ThrownStarfire extends ThrowableItemProjectile {
	public ThrownStarfire(EntityType<? extends ThrownStarfire> entityType, Level level) {
		super(entityType, level);
	}

	public ThrownStarfire(Level level, LivingEntity livingEntity) {
		super(ESEntities.STARFIRE.get(), livingEntity, level);
	}

	public ThrownStarfire(Level level, double x, double y, double z) {
		super(ESEntities.STARFIRE.get(), x, y, z, level);
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (hitResult.getType() != HitResult.Type.MISS) {
			if (level() instanceof ServerLevel serverLevel) {
				createExplosionParticles(serverLevel, position().add(0, getBbHeight() / 2, 0), 12, 1);
				for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(3))) {
					if (ESEntityUtil.shouldHarm(getOwner(), entity)) {
						entity.addEffect(new MobEffectInstance(ESMobEffects.STARFIRE.asHolder(), 200));
						createExplosionParticles(serverLevel, entity.position().add(0, entity.getBbHeight() / 2, 0), 10, 0.25);
					}
				}
			}
			playSound(ESSoundEvents.STARFIRE_WHOOSH.get());
			discard();
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		if (hitResult.getType() != HitResult.Type.MISS && level().getBlockState(hitResult.getBlockPos()).is(BlockTags.SAND)) {
			level().setBlockAndUpdate(hitResult.getBlockPos(), ESBlocks.RAW_FLOWGLAZE.get().defaultBlockState());
		}
	}

	public static void createExplosionParticles(ServerLevel serverLevel, Vec3 vec3, int count, double scale) {
		for (int i = 0; i < count; i++) {
			serverLevel.sendParticles(ESParticles.STARFIRE_EXPLOSION.get(), vec3.x, vec3.y, vec3.z, 5, 0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale, 0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale, 0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale, 0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale);
			serverLevel.sendParticles(ESParticles.STARFIRE_EXPLOSION_SMALL.get(), vec3.x, vec3.y, vec3.z, 5, 0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale, 0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale, 0.2 * (serverLevel.getRandom().nextFloat() - 0.5) * scale, 0.4 * (serverLevel.getRandom().nextFloat() - 0.5) * scale);
		}
	}

	@Override
	protected @NotNull Item getDefaultItem() {
		return ESItems.STARFIRE.get();
	}
}
