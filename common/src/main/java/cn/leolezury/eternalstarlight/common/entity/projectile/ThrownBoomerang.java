package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESEnchantments;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class ThrownBoomerang extends AbstractArrow {
	private static final String TAG_DEALT_DAMAGE = "dealt_damage";

	private boolean dealtDamage;

	public ThrownBoomerang(EntityType<? extends ThrownBoomerang> type, Level level) {
		super(type, level);
	}

	public ThrownBoomerang(EntityType<? extends ThrownBoomerang> type, Level level, @Nullable LivingEntity owner, double x, double y, double z, ItemStack pickupItemStack) {
		super(type, x, y, z, level, pickupItemStack, pickupItemStack);
		this.setOwner(owner);
	}

	@Override
	public void tick() {
		Entity owner = this.getOwner();
		if (this.inGround || (owner == null && tickCount > 40) || (owner != null && distanceTo(owner) > 15 + owner.getBbWidth() / 2)) {
			this.dealtDamage = true;
		}
		if (!level().isClientSide && !this.dealtDamage) {
			float homing = 0;
			if (level() instanceof ServerLevel serverLevel && this.getWeaponItem() != null) {
				homing = ESEnchantments.modifyBoomerangHomingStrength(serverLevel, this.getWeaponItem(), homing);
			}
			homing = Mth.clamp(homing, 0, 1);
			Vec3 homingTarget = null;
			for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(3)).stream().sorted(Comparator.comparingDouble(i -> i.distanceTo(this))).toList()) {
				if (ESEntityUtil.shouldHarm(owner, living)) {
					homingTarget = living.position().add(0, living.getBbHeight() / 2, 0);
					break;
				}
			}
			if (homingTarget != null && homing > 0 && getDeltaMovement().normalize().dot(homingTarget.subtract(position()).normalize()) > 0.5) {
				this.setDeltaMovement(this.getDeltaMovement().normalize().scale(1 - homing).add(homingTarget.subtract(position()).normalize().scale(homing)).normalize().scale(getDeltaMovement().length()));
			}
		}
		if (owner instanceof Player player) {
			float pickupRadius = 0;
			if (level() instanceof ServerLevel serverLevel && this.getWeaponItem() != null) {
				pickupRadius = ESEnchantments.modifyBoomerangPickupRadius(serverLevel, this.getWeaponItem(), pickupRadius);
			}
			if (pickupRadius > 0) {
				for (ItemEntity item : level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(pickupRadius))) {
					item.playerTouch(player);
				}
			}
		}
		if ((this.dealtDamage || this.isNoPhysics()) && owner != null) {
			this.inGround = false;
			if (!(owner instanceof Player) && !level().isClientSide) {
				this.discard();
			}
			if (!this.isAcceptableReturnOwner()) {
				if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
					this.spawnAtLocation(this.getPickupItem(), 0.1F);
				}

				this.discard();
			} else {
				this.setNoPhysics(true);
				Vec3 diff = owner.getEyePosition().subtract(this.position());
				double speed = Math.max(getDeltaMovement().length(), 1.5);
				if (speed > diff.length()) {
					speed = diff.length();
				}
				this.setDeltaMovement(diff.normalize().scale(speed));
			}
		}

		super.tick();
	}

	private boolean isAcceptableReturnOwner() {
		Entity entity = this.getOwner();
		if (entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayer) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	@Nullable
	@Override
	protected EntityHitResult findHitEntity(Vec3 vec3, Vec3 vec32) {
		return this.dealtDamage ? null : super.findHitEntity(vec3, vec32);
	}

	private double getItemDamage(AttributeInstance instance) {
		double baseValue = instance != null ? instance.getBaseValue() : 1;
		ItemStack weapon = getWeaponItem();
		if (weapon == null) {
			return baseValue;
		}
		double result = baseValue;
		List<AttributeModifier> allModifiers = new ArrayList<>();
		List<AttributeModifier> originalModifiers = instance != null ? new ArrayList<>(instance.getModifiers()) : new ArrayList<>();
		ItemAttributeModifiers modifiers = weapon.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
		originalModifiers.removeIf(o -> modifiers.modifiers().stream().anyMatch(modifier -> modifier.attribute().is(Attributes.ATTACK_DAMAGE) && modifier.modifier().id().equals(o.id())));
		for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
			if (entry.attribute().is(Attributes.ATTACK_DAMAGE)) {
				allModifiers.addFirst(entry.modifier());
			}
		}
		allModifiers.addAll(originalModifiers);
		for (AttributeModifier modifier : allModifiers) {
			double amount = modifier.amount();
			double addition;
			switch (modifier.operation()) {
				case ADD_VALUE -> addition = amount;
				case ADD_MULTIPLIED_BASE -> addition = amount * baseValue;
				case ADD_MULTIPLIED_TOTAL -> addition = amount * result;
				default -> throw new MatchException(null, null);
			}
			result += addition;
		}
		return result;
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		Entity owner = this.getOwner();
		float damage = (float) getItemDamage(owner instanceof LivingEntity living ? living.getAttribute(Attributes.ATTACK_DAMAGE) : null);
		DamageSource damageSource = damageSources().thrown(this, getOwner());
		float critChance = 0;
		boolean critSuccess = false;
		if (level() instanceof ServerLevel serverLevel && this.getWeaponItem() != null) {
			damage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, damage);
			critChance = ESEnchantments.modifyBoomerangCritChance(serverLevel, this.getWeaponItem(), entity, damageSource, critChance);
		}

		if (getRandom().nextFloat() < critChance) {
			damage *= 1.5f;
			critSuccess = true;
		}

		this.dealtDamage = true;
		if (entity.hurt(damageSource, damage)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}
			if (level() instanceof ServerLevel serverLevel) {
				// so that we can trigger melee-only effects
				DamageSource directSource = getOwner() instanceof Player player ? this.damageSources().playerAttack(player) : (getOwner() instanceof LivingEntity living ? damageSources().mobAttack(living) : damageSource);
				EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, directSource, this.getWeaponItem());
			}
			if (entity instanceof LivingEntity livingEntity) {
				this.doKnockback(livingEntity, damageSource);
				this.doPostHurtEffects(livingEntity);
			}
			if (critSuccess && getOwner() instanceof Player player) {
				player.crit(entity);
			}
		}

		//this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
		this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.0F, 1.0F);
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.PLAYER_ATTACK_CRIT;
	}

	@Override
	protected boolean tryPickup(Player player) {
		return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
	}

	@Override
	public void playerTouch(Player player) {
		if (this.ownedBy(player) || this.getOwner() == null) {
			super.playerTouch(player);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.dealtDamage = compoundTag.getBoolean(TAG_DEALT_DAMAGE);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_DEALT_DAMAGE, this.dealtDamage);
	}

	@Override
	protected float getWaterInertia() {
		return 0.99F;
	}

	@Override
	public boolean shouldRender(double d, double e, double f) {
		return true;
	}
}