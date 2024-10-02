package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.common.item.weapon.CrescentSpearItem;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
	@Shadow
	@NotNull
	public abstract ItemStack getWeaponItem();

	// the player has a different actuallyHurt, modify it again
	// copied from LivingEntityMixin
	@ModifyVariable(method = "actuallyHurt", at = @At(value = "LOAD", ordinal = 0), ordinal = 0, argsOnly = true)
	private float actuallyHurt(float amount, DamageSource damageSource) {
		return CommonHandlers.onLivingHurt((LivingEntity) (Object) this, damageSource, amount);
	}

	@Inject(method = "hurtCurrentlyUsedShield", at = @At(value = "HEAD"))
	private void damageShield(float amount, CallbackInfo callBackInfo) {
		Player player = (Player) (Object) this;
		ItemStack useItem = player.getUseItem();
		if (CommonSetupHandlers.SHIELDS.stream().anyMatch(itemSupplier -> useItem.is(itemSupplier.get()))) {
			useItem.hurtAndBreak(Math.max((int) (amount / 5f), 1), player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
		}
	}

	@Inject(method = "disableShield", at = @At(value = "HEAD"))
	private void disableShield(CallbackInfo ci) {
		Player player = (Player) (Object) this;
		ItemStack useItem = player.getUseItem();
		if (CommonSetupHandlers.SHIELDS.stream().anyMatch(itemSupplier -> useItem.is(itemSupplier.get()))) {
			player.getCooldowns().addCooldown(useItem.getItem(), 100);
			player.stopUsingItem();
			player.level().broadcastEntityEvent(player, (byte) 30);
		}
	}

	@Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.AFTER))
	private void attack(Entity entity, CallbackInfo ci, @Local(ordinal = 3) LocalBooleanRef localRef) {
		if (getWeaponItem().is(ESTags.Items.SCYTHES) || getWeaponItem().getItem() instanceof CrescentSpearItem) {
			localRef.set(true);
		}
	}
}
