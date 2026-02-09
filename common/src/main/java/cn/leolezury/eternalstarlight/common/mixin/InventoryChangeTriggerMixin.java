package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.item.component.GuideBook;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.util.ESBookUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(InventoryChangeTrigger.class)
public abstract class InventoryChangeTriggerMixin {
	@Inject(method = "trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"))
	public void trigger(ServerPlayer player, Inventory inventory, ItemStack stack, CallbackInfo ci) {
		List<String> listeningNamespaces = null;
		for (int i = 0; i < inventory.getContainerSize(); i++) {
			ItemStack inventoryItem = inventory.getItem(i);
			GuideBook guideBook = inventoryItem.get(ESDataComponents.BOOK.get());
			if (guideBook != null) {
				if (listeningNamespaces == null) {
					listeningNamespaces = new ArrayList<>();
				}
				listeningNamespaces.addAll(guideBook.listeningNamespaces());
			}
		}
		ESDataAttachments.GUIDEBOOK_LISTENING_NAMESPACES.setData(player, listeningNamespaces == null ? List.of() : listeningNamespaces);
		if (stack.is(ESTags.Items.AFFECTS_PROGRESSION)) {
			ESBookUtil.unlock(player, BuiltInRegistries.ITEM.getKey(stack.getItem()).withPrefix("item_"));
		}
	}
}
