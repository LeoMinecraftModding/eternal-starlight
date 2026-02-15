package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.misc.GalacticQuiverItem;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {
	@Shadow
	@Nullable
	private ItemStack firedFromWeapon;

	@Inject(method = "shotFromCrossbow", at = @At("RETURN"), cancellable = true)
	public void shotFromCrossbow(CallbackInfoReturnable<Boolean> cir) {
		if (this.firedFromWeapon != null && (BuiltInRegistries.ITEM.getKey(firedFromWeapon.getItem()).getNamespace().equals(EternalStarlight.ID) && firedFromWeapon.getItem() instanceof CrossbowItem)) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "getWaterInertia", at = @At("RETURN"), cancellable = true)
	public void getWaterInertia(CallbackInfoReturnable<Float> cir) {
		if (this.firedFromWeapon != null && this.firedFromWeapon.is(ESItems.WILTED_CROSSBOW.get())) {
			cir.setReturnValue(0.99f);
		}
	}

	@WrapOperation(method = "tryPickup", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z"))
	public boolean addToInventory(Inventory instance, ItemStack itemStack, Operation<Boolean> original) {
		if (itemStack.is(ItemTags.ARROWS)) {
			boolean arrowSuccess = GalacticQuiverItem.addArrowToInventory(instance, itemStack);
			return arrowSuccess || original.call(instance, itemStack);
		}
		return original.call(instance, itemStack);
	}

	@WrapOperation(method = "doKnockback", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;scale(D)Lnet/minecraft/world/phys/Vec3;"))
	public Vec3 scaleKnockback(Vec3 instance, double scale, Operation<Vec3> original) {
		if (this.firedFromWeapon != null && this.firedFromWeapon.is(ESItems.UNREALIUM_CROSSBOW.get())) {
			return original.call(instance, scale * 0.5);
		}
		return original.call(instance, scale);
	}
}
