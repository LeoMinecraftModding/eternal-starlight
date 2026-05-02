package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.entity.interfaces.Grappling;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin implements SpellCaster {
	@Shadow
	@NotNull
	public abstract ItemStack getWeaponItem();

	@Unique
	private boolean originalEnoughAttackStrength;

	@Inject(method = "createAttributes", at = @At("RETURN"))
	private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.getReturnValue()
			.add(ESAttributes.FOG_VISION.asHolder());
	}

	@Inject(method = "hurtCurrentlyUsedShield", at = @At("HEAD"))
	private void damageShield(float amount, CallbackInfo callBackInfo) {
		Player player = (Player) (Object) this;
		ItemStack useItem = player.getUseItem();
		if (useItem.is(ESTags.Items.GREATSWORDS)) {
			useItem.hurtAndBreak(Math.max((int) (amount / 5f), 1), player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
			player.stopUsingItem();
			player.getCooldowns().addCooldown(useItem.getItem(), 100);
		}
	}

	@Inject(method = "disableShield", at = @At("HEAD"), cancellable = true)
	private void disableShield(CallbackInfo ci) {
		Player player = (Player) (Object) this;
		ItemStack useItem = player.getUseItem();
		if (useItem.is(ESTags.Items.GREATSWORDS)) {
			ci.cancel();
		}
	}

	@Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtEnemy(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/player/Player;)Z"))
	private void attackHurtEnemy(Entity entity, CallbackInfo ci) {
		if (entity instanceof LivingEntity living) {
			ESCommonHandler.handleFlowglazeWeaponAttack((Player) (Object) this, living);
		}
	}

	@Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSprinting()Z", ordinal = 0))
	private void attackCheckHammerStrength(Entity entity, CallbackInfo ci, @Local(ordinal = 0) LocalBooleanRef localRef) {
		if (getWeaponItem().is(ESTags.Items.HAMMERS)) {
			localRef.set(true);
		}
	}

	@Inject(method = "attack", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;walkDist:F", opcode = Opcodes.GETFIELD))
	private void attackBeforeScytheSweepCheck(Entity entity, CallbackInfo ci, @Local(ordinal = 0) LocalBooleanRef localRef) {
		if (getWeaponItem().is(ESTags.Items.SCYTHES)) {
			originalEnoughAttackStrength = localRef.get();
			localRef.set(true);
		}
	}

	@Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"))
	private void attackAfterScytheSweepCheck(Entity entity, CallbackInfo ci, @Local(ordinal = 0) LocalBooleanRef localRef) {
		if (getWeaponItem().is(ESTags.Items.SCYTHES)) {
			localRef.set(originalEnoughAttackStrength);
		}
	}

	@Inject(method = "aiStep", at = @At(value = "TAIL"))
	private void aiStep(CallbackInfo ci) {
		Player player = (Player) (Object) this;
		if (!player.level().isClientSide) {
			Entity entity = player.level().getEntity(ESDataAttachments.GRAPPLING.getData(player));
			if (entity instanceof Grappling grappling && grappling.reachedTarget() && grappling.shouldPull()) {
				player.resetFallDistance();
				if (!player.level().isClientSide) {
					float length = grappling.length();
					double d = entity.position().subtract(player.getEyePosition()).length();
					if (d > (double) length) {
						double e = d / (double) length * 0.1;
						boolean crouch = player.isCrouching();
						player.addDeltaMovement(entity.position().subtract(player.getEyePosition()).scale(1.0 / d).multiply(e, e * 1.1, e).scale(crouch ? 0.6 : (player.onGround() ? 1.8 : 1)));
						player.hurtMarked = true;
					}
				}
			}
		}
	}

	@Inject(method = "getWeaponItem", at = @At("RETURN"), cancellable = true)
	private void getWeaponItem(CallbackInfoReturnable<ItemStack> cir) {
		Player player = (Player) (Object) this;
		if (ESDataAttachments.OFFHAND_ATTACK.getData(player)) {
			cir.setReturnValue(player.getOffhandItem());
		}
	}

	@WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack useOffhandWeapon(Player instance, InteractionHand hand, Operation<ItemStack> original) {
		if (hand == InteractionHand.MAIN_HAND && ESDataAttachments.OFFHAND_ATTACK.getData(instance)) {
			return original.call(instance, InteractionHand.OFF_HAND);
		}
		return original.call(instance, hand);
	}

	@WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAttackStrengthScale(F)F"))
	private float useOffhandAttackStrengthTimer(Player instance, float f, Operation<Float> original) {
		if (ESDataAttachments.OFFHAND_ATTACK.getData(instance)) {
			return Mth.clamp(((float) ESDataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER.getData(instance) + f) / instance.getCurrentItemAttackStrengthDelay(), 0.0F, 1.0F);
		}
		return original.call(instance, f);
	}

	@WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;resetAttackStrengthTicker()V"))
	private void resetOffhandAttackStrengthTimer(Player instance, Operation<Void> original) {
		if (ESDataAttachments.OFFHAND_ATTACK.getData(instance)) {
			ESDataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER.setData(instance, 0);
		} else {
			original.call(instance);
		}
	}

	@Inject(method = "isModelPartShown", at = @At("RETURN"), cancellable = true)
	private void isModelPartShown(PlayerModelPart part, CallbackInfoReturnable<Boolean> cir) {
		Player player = (Player) (Object) this;
		if (player.level().getEntity(ESDataAttachments.HUSK_OWNER_ID.getData(player)) instanceof Player huskOwner) {
			cir.setReturnValue(huskOwner.isModelPartShown(part));
		}
	}

	@WrapOperation(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
	private void playEatSound(Level instance, Player player, double x, double y, double z, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch, Operation<Void> original) {
		if (!((Player) (Object) this).getItemBySlot(EquipmentSlot.HEAD).is(ESItems.UNREALIUM_HELMET.get())) {
			original.call(instance, player, x, y, z, soundEvent, soundSource, volume, pitch);
		}
	}

	@Inject(method = "getHurtSound", at = @At("RETURN"), cancellable = true)
	private void getHurtSound(DamageSource damageSource, CallbackInfoReturnable<SoundEvent> cir) {
		if (((Player) (Object) this).getItemBySlot(EquipmentSlot.CHEST).is(ESItems.UNREALIUM_CHESTPLATE.get())) {
			cir.setReturnValue(null);
		}
	}

	@Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
	private void playStepSound(BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
		if (((Player) (Object) this).getItemBySlot(EquipmentSlot.FEET).is(ESItems.UNREALIUM_BOOTS.get())) {
			ci.cancel();
		}
	}
}
