package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import cn.leolezury.eternalstarlight.common.handler.ESCommonSetupHandler;
import cn.leolezury.eternalstarlight.common.item.combat.CrescentSpearItem;
import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherItem;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
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
	@ModifyVariable(method = "actuallyHurt", at = @At(value = "STORE", ordinal = 1), ordinal = 0, argsOnly = true)
	private float modifyActualHurtDamage(float original, @Local(argsOnly = true) DamageSource source) {
		return ESCommonHandler.onModifyLivingActualHurtDamage((Player) (Object) this, source, original);
	}

	@Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setHealth(F)V", shift = At.Shift.AFTER))
	private void actuallyHurt(DamageSource source, float amount, CallbackInfo ci) {
		ESCommonHandler.onPostLivingHurt((Player) (Object) this, source, amount);
	}

	@Inject(method = "hurtCurrentlyUsedShield", at = @At("HEAD"))
	private void damageShield(float amount, CallbackInfo callBackInfo) {
		Player player = (Player) (Object) this;
		ItemStack useItem = player.getUseItem();
		if (ESCommonSetupHandler.SHIELDS.stream().anyMatch(itemSupplier -> useItem.is(itemSupplier.get()))) {
			useItem.hurtAndBreak(Math.max((int) (amount / 5f), 1), player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
		}
	}

	@Inject(method = "disableShield", at = @At("HEAD"))
	private void disableShield(CallbackInfo ci) {
		Player player = (Player) (Object) this;
		ItemStack useItem = player.getUseItem();
		if (ESCommonSetupHandler.SHIELDS.stream().anyMatch(itemSupplier -> useItem.is(itemSupplier.get()))) {
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

	@Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;crit(Lnet/minecraft/world/entity/Entity;)V", shift = At.Shift.AFTER))
	private void attackCrit(Entity entity, CallbackInfo ci, @Local(ordinal = 2) float attackStrength) {
		ESCommonHandler.onCriticalHit((Player) (Object) this, entity, attackStrength);
	}

	@ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
	private float getDestroySpeed(float original, @Local(ordinal = 0, argsOnly = true) BlockState state) {
		return ESCommonHandler.onBlockBreakSpeed((Player) (Object) this, state, original);
	}

	@WrapOperation(method = "getProjectile", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack newItemStack(ItemLike itemLike, Operation<ItemStack> original, @Local(argsOnly = true) ItemStack stack) {
		return stack.getItem() instanceof SeedsLauncherItem ? new ItemStack(Items.WHEAT_SEEDS) : original.call(itemLike);
	}
}
