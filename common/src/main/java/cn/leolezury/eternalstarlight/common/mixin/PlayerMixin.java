package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.entity.interfaces.Grappling;
import cn.leolezury.eternalstarlight.common.entity.interfaces.GrapplingOwner;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
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
public abstract class PlayerMixin implements SpellCaster, GrapplingOwner {
	@Shadow
	@NotNull
	public abstract ItemStack getWeaponItem();

	@Unique
	private Entity grappling;
	@Unique
	private boolean originalEnoughAttackStrength;

	@Inject(method = "hurtCurrentlyUsedShield", at = @At(value = "HEAD"))
	private void damageShield(float amount, CallbackInfo callBackInfo) {
		Player player = (Player) (Object) this;
		ItemStack useItem = player.getUseItem();
		if (useItem.is(ESTags.Items.GREATSWORDS)) {
			useItem.hurtAndBreak(Math.max((int) (amount / 5f), 1), player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
			player.stopUsingItem();
			player.getCooldowns().addCooldown(useItem.getItem(), 100);
		}
	}

	@Inject(method = "disableShield", at = @At(value = "HEAD"), cancellable = true)
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
			CommonHandlers.handleFlowglazeWeaponAttack((Player) (Object) this, living);
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
		if (this.grappling != null && (this.grappling instanceof Grappling grappling1 && grappling1.reachedTarget() && grappling1.shouldPull())) {
			player.resetFallDistance();
			if (!player.level().isClientSide) {
				float length = grappling1.length();
				double d = this.grappling.position().subtract(player.getEyePosition()).length();
				if (d > (double) length) {
					double e = d / (double) length * 0.1;
					boolean crouch = player.isCrouching();
					player.addDeltaMovement(this.grappling.position().subtract(player.getEyePosition()).scale(1.0 / d).multiply(e, e * 1.1, e).scale(crouch ? 0.6 : (player.onGround() ? 1.8 : 1)));
					player.hurtMarked = true;
				}
			}
		}
	}


	@Inject(method = "isModelPartShown", at = @At(value = "RETURN"), cancellable = true)
	private void isModelPartShown(PlayerModelPart part, CallbackInfoReturnable<Boolean> cir) {
		Player player = (Player) (Object) this;
		if (player.level().getEntity(ESDataAttachments.HUSK_OWNER_ID.getData(player)) instanceof Player huskOwner) {
			cir.setReturnValue(huskOwner.isModelPartShown(part));
		}
	}

	@Override
	public Entity getESGrappling() {
		return grappling;
	}

	@Override
	public void setESGrappling(Entity grappling) {
		this.grappling = grappling;
	}
}
