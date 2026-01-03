package cn.leolezury.eternalstarlight.common.entity.projectile;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class ThrownSpear extends AbstractArrow {
	private static final String TAG_DEALT_DAMAGE = "dealt_damage";

	private static final EntityDataAccessor<Boolean> FOIL = SynchedEntityData.defineId(ThrownSpear.class, EntityDataSerializers.BOOLEAN);
	private boolean dealtDamage;

	public ThrownSpear(EntityType<? extends ThrownSpear> type, Level level) {
		super(type, level);
	}

	public ThrownSpear(EntityType<? extends ThrownSpear> type, Level level, @Nullable LivingEntity owner, double x, double y, double z, ItemStack pickupItemStack) {
		super(type, x, y, z, level, pickupItemStack, pickupItemStack);
		this.setOwner(owner);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(FOIL, false);
	}

	@Override
	public void tick() {
		if (this.inGroundTime > 4) {
			this.dealtDamage = true;
		}
		super.tick();
	}

	@Override
	protected void setPickupItemStack(ItemStack stack) {
		super.setPickupItemStack(stack);
		this.entityData.set(FOIL, stack.hasFoil());
	}

	public boolean isFoil() {
		return this.entityData.get(FOIL);
	}

	@Nullable
	@Override
	protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
		return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
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
	protected void onHitEntity(EntityHitResult result) {
		Entity target = result.getEntity();
		Entity owner = this.getOwner();
		float damage = getDamageScale(target) * (float) getItemDamage(owner instanceof LivingEntity living ? living.getAttribute(Attributes.ATTACK_DAMAGE) : null);
		DamageSource damageSource = this.damageSources().thrown(this, owner == null ? this : owner);
		if (this.level() instanceof ServerLevel serverlevel) {
			damage = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), target, damageSource, damage);
		}

		this.dealtDamage = true;
		if (target.hurt(damageSource, damage)) {
			if (target.getType() == EntityType.ENDERMAN) {
				return;
			}

			if (this.level() instanceof ServerLevel serverLevel) {
				EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, target, damageSource, this.getWeaponItem());
			}

			if (target instanceof LivingEntity living) {
				this.doKnockback(living, damageSource);
				this.doPostHurtEffects(living);
			}
		}

		this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
		this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.0F, 1.0F);
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.PLAYER_ATTACK_CRIT;
	}

	protected float getDamageScale(Entity target) {
		return 2.0F;
	}

	@Override
	protected void hitBlockEnchantmentEffects(ServerLevel level, BlockHitResult hitResult, ItemStack stack) {
		Vec3 location = hitResult.getBlockPos().clampLocationWithin(hitResult.getLocation());
		EnchantmentHelper.onHitBlock(level, stack, this.getOwner() instanceof LivingEntity livingentity ? livingentity : null, this, null, location, level.getBlockState(hitResult.getBlockPos()), item -> this.kill());
	}

	@Override
	protected boolean tryPickup(Player player) {
		return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
	}

	@Override
	public void playerTouch(Player entity) {
		if (this.ownedBy(entity) || this.getOwner() == null) {
			super.playerTouch(entity);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.dealtDamage = compound.getBoolean(TAG_DEALT_DAMAGE);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean(TAG_DEALT_DAMAGE, this.dealtDamage);
	}

	@Override
	public boolean shouldRender(double x, double y, double z) {
		return true;
	}
}
