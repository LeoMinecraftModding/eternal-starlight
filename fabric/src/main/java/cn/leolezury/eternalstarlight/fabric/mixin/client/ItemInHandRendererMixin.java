package cn.leolezury.eternalstarlight.fabric.mixin.client;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
	@WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 1))
	private boolean isCrossbow(ItemStack instance, Item item, Operation<Boolean> original) {
		return original.call(instance, item) || instance.is(ESItems.CRYSTAL_CROSSBOW.get()) || instance.is(ESItems.MECHANICAL_CROSSBOW.get());
	}

	@Inject(method = "isChargedCrossbow", at = @At("RETURN"), cancellable = true)
	private static void isChargedCrossbow(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
		if ((itemStack.is(ESItems.CRYSTAL_CROSSBOW.get()) || itemStack.is(ESItems.MECHANICAL_CROSSBOW.get())) && CrossbowItem.isCharged(itemStack)) {
			cir.setReturnValue(true);
		}
	}
}
