package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherAmmoType;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ShotSeeds extends ThrowableItemProjectile {
	private ItemStack firedFromWeapon;

	public ShotSeeds(EntityType<? extends ShotSeeds> entityType, Level level) {
		super(entityType, level);
	}

	public ShotSeeds(Level level, LivingEntity livingEntity, ItemStack stack, ItemStack weapon) {
		super(ESEntities.SHOT_SEEDS.get(), livingEntity, level);
		setItem(stack);
		if (weapon != null) {
			firedFromWeapon = weapon.copy();
		}
		if (level instanceof ServerLevel serverLevel && weapon != null) {
			onProjectileSpawned(serverLevel, weapon, this, (item) -> this.firedFromWeapon = null);
		}
	}

	public ShotSeeds(Level level, double x, double y, double z) {
		super(ESEntities.SHOT_SEEDS.get(), x, y, z, level);
	}

	// copied from EnchantmentHelper
	private static void onProjectileSpawned(ServerLevel serverLevel, ItemStack itemStack, ShotSeeds seeds, Consumer<Item> consumer) {
		Entity owner = seeds.getOwner();
		LivingEntity living;
		if (owner instanceof LivingEntity livingEntity) {
			living = livingEntity;
		} else {
			living = null;
		}

		EnchantedItemInUse enchantedItemInUse = new EnchantedItemInUse(itemStack, null, living, consumer);
		EnchantmentHelper.runIterationOnItem(itemStack, (holder, i) -> holder.value().onProjectileSpawned(serverLevel, i, enchantedItemInUse, seeds));
	}

	@Override
	public void tick() {
		super.tick();
		if (tickCount > (isOnFire() ? 40 : 80)) {
			discard();
		}
		if (this.isInWaterOrRain() || level().getBlockState(blockPosition()).is(Blocks.POWDER_SNOW)) {
			this.clearFire();
		}
	}

	@Override
	public @Nullable ItemStack getWeaponItem() {
		return firedFromWeapon;
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		discard();
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		Entity entity = hitResult.getEntity();
		Entity owner = this.getOwner();
		float damage = 0.5f;
		DamageSource source = ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.SEEDS, this, owner);
		if (this.getWeaponItem() != null) {
			if (level() instanceof ServerLevel serverLevel) {
				damage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, source, damage);
			}
		}
		damage += (float) (getDeltaMovement().length() * 1.25);
		SeedsLauncherAmmoType type = SeedsLauncherAmmoType.getAmmoType(level().registryAccess(), getItem().getItem()).value();
		damage *= type.damageMultiplier();
		entity.invulnerableTime = 0;
		if (entity.hurt(source, damage)) {
			if (entity instanceof LivingEntity living) {
				if (level() instanceof ServerLevel serverLevel) {
					EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, living, source, this.getWeaponItem());
				}
			}
			if (isOnFire()) {
				entity.igniteForSeconds((getRemainingFireTicks() / 20f) / 15f);
			}
		}
	}

	@Override
	protected Item getDefaultItem() {
		return Items.WHEAT_SEEDS;
	}
}
