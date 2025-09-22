package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ThrownShatteredBlade extends AbstractArrow {
	private static final String TAG_DEALT_DAMAGE = "dealt_damage";

	private boolean dealtDamage;

	public ThrownShatteredBlade(EntityType<? extends ThrownShatteredBlade> entityType, Level level) {
		super(entityType, level);
	}

	public ThrownShatteredBlade(Level level, LivingEntity livingEntity, @Nullable ItemStack weapon) {
		super(ESEntities.SHATTERED_BLADE.get(), livingEntity, level, new ItemStack(ESItems.SHATTERED_SWORD_BLADE.get()), weapon);
	}

	@Override
	public void tick() {
		if (this.inGroundTime > 4) {
			this.dealtDamage = true;
		}
		Entity entity = this.getOwner();
		int loyaltyLevel = 3;
		if ((this.dealtDamage || this.isNoPhysics()) && entity != null) {
			if (!(entity instanceof Player) && !level().isClientSide) {
				this.discard();
			}
			if (!this.isAcceptableReturnOwner()) {
				if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
					this.spawnAtLocation(this.getPickupItem(), 0.1F);
				}

				this.discard();
			} else {
				this.setNoPhysics(true);
				Vec3 vec3 = entity.getEyePosition().subtract(this.position());
				this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * (double) loyaltyLevel, this.getZ());
				if (this.level().isClientSide) {
					this.yOld = this.getY();
				}

				double d = 0.05 * (double) loyaltyLevel;
				this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(d)));
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
	protected EntityHitResult findHitEntity(Vec3 vec3, Vec3 vec32) {
		return this.dealtDamage ? null : super.findHitEntity(vec3, vec32);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		Entity owner = this.getOwner();
		float damage = owner instanceof LivingEntity living && living.getAttribute(Attributes.ATTACK_DAMAGE) != null ? (float) living.getAttributeValue(Attributes.ATTACK_DAMAGE) : 5;
		DamageSource damageSource = ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.SHATTERED_BLADE, this, owner == null ? this : owner);

		if (level() instanceof ServerLevel serverLevel && this.getWeaponItem() != null) {
			damage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, damage);
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
		}

		this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
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
	protected ItemStack getDefaultPickupItem() {
		return ESItems.SHATTERED_SWORD_BLADE.get().getDefaultInstance();
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