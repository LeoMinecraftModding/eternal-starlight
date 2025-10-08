package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.util.ESBookUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryChangeTrigger.class)
public abstract class InventoryChangeTriggerMixin {
	@Inject(method = "trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
	private void trigger(ServerPlayer player, Inventory inventory, ItemStack stack, CallbackInfo ci) {
		for (int i = 0; i < inventory.getContainerSize(); ++i) {
			ItemStack inventoryItem = inventory.getItem(i);
			if (!inventoryItem.isEmpty()) {
				ESBookUtil.unlock(player, BuiltInRegistries.ITEM.getKey(inventoryItem.getItem()).withPrefix("item_"));
			}
		}
	}
}
