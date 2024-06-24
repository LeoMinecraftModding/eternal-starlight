package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.entity.living.monster.Gleech;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class GleechEgg extends ThrowableItemProjectile {
	public GleechEgg(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
		super(entityType, level);
	}

	public GleechEgg(Level level, LivingEntity livingEntity) {
		super(ESEntities.GLEECH_EGG.get(), livingEntity, level);
	}

	public GleechEgg(Level level, double x, double y, double z) {
		super(ESEntities.GLEECH_EGG.get(), x, y, z, level);
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		discard();
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
		if (!level().isClientSide && entityHitResult.getEntity() instanceof LivingEntity livingEntity && !livingEntity.getType().is(ESTags.EntityTypes.GLEECH_IMMUNE)) {
			if (getOwner() instanceof ServerPlayer serverPlayer) {
				ESCriteriaTriggers.THROW_GLEECH_EGG.get().trigger(serverPlayer);
			}
			Gleech gleech = ESEntities.GLEECH.get().create(level());
			if (gleech != null) {
				gleech.setPos(position());
				gleech.setLarval(true);
				gleech.attachTo(livingEntity);
				level().addFreshEntity(gleech);
				// to set the target in a hacky way
				gleech.setTarget(livingEntity);
				gleech.hurt(damageSources().mobAttack(livingEntity), 0);
			}
		}
	}

	@Override
	protected @NotNull Item getDefaultItem() {
		return ESItems.GLEECH_EGG.get();
	}
}
