package cn.leolezury.eternalstarlight.fabric.mixin.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
	@WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 1))
	private boolean isCrossbow(ItemStack instance, Item item, Operation<Boolean> original) {
		return original.call(instance, item) || (BuiltInRegistries.ITEM.getKey(instance.getItem()).getNamespace().equals(EternalStarlight.ID) && instance.getItem() instanceof CrossbowItem);
	}

	@Inject(method = "isChargedCrossbow", at = @At("RETURN"), cancellable = true)
	private static void isChargedCrossbow(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
		if (BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getNamespace().equals(EternalStarlight.ID) && itemStack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemStack)) {
			cir.setReturnValue(true);
		}
	}
}
