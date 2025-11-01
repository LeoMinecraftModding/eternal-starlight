package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.item.component.LargeItemStackList;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Mixin(ProjectileWeaponItem.class)
public abstract class ProjectileWeaponItemMixin {
	@Inject(method = "getHeldProjectile", at = @At("RETURN"), cancellable = true)
	private static void getHeldProjectile(LivingEntity livingEntity, Predicate<ItemStack> predicate, CallbackInfoReturnable<ItemStack> cir) {
		if (cir.getReturnValue().isEmpty() && livingEntity instanceof Player player) {
			Inventory inventory = player.getInventory();
			for (int i = 0; i < inventory.getContainerSize(); i++) {
				ItemStack inventoryItem = inventory.getItem(i);
				if (inventoryItem.is(ESItems.GALACTIC_QUIVER.get())) {
					List<LargeItemStackList.LargeItemStack> arrows = inventoryItem.getOrDefault(ESDataComponents.ARROWS.get(), new LargeItemStackList(List.of()));
					for (LargeItemStackList.LargeItemStack arrowStack : arrows) {
						if (predicate.test(arrowStack.getItem())) {
							ItemStack ammo = arrowStack.getItem().copy();
							ammo.set(ESDataComponents.QUIVER_ARROW.get(), Unit.INSTANCE);
							cir.setReturnValue(ammo);
							return;
						}
					}
				}
			}
		}
	}

	@Inject(method = "useAmmo", at = @At("RETURN"))
	private static void useAmmo(ItemStack weapon, ItemStack ammo, LivingEntity shooter, boolean intangable, CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 0) int ammoUse) {
		int use = ammoUse;
		ItemStack result = cir.getReturnValue();
		if (shooter instanceof Player player && result.has(ESDataComponents.QUIVER_ARROW.get()) && use > 0) {
			Inventory inventory = player.getInventory();
			for (int i = 0; i < inventory.getContainerSize(); i++) {
				ItemStack inventoryItem = inventory.getItem(i);
				if (inventoryItem.is(ESItems.GALACTIC_QUIVER.get())) {
					List<LargeItemStackList.LargeItemStack> arrows = new ArrayList<>(inventoryItem.getOrDefault(ESDataComponents.ARROWS.get(), new LargeItemStackList(List.of())));
					for (LargeItemStackList.LargeItemStack arrowStack : arrows) {
						ItemStack item = arrowStack.getItem().copy();
						item.set(ESDataComponents.QUIVER_ARROW.get(), Unit.INSTANCE);
						if (ItemStack.isSameItemSameComponents(item, result)) {
							int shrink = Math.min(use, arrowStack.getCount());
							arrowStack.shrink(shrink);
							use -= shrink;
						}
					}
					arrows.removeIf(LargeItemStackList.LargeItemStack::isEmpty);
					inventoryItem.set(ESDataComponents.ARROWS.get(), new LargeItemStackList(Collections.unmodifiableList(arrows)));
					if (player instanceof ServerPlayer serverPlayer) {
						serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(ClientboundContainerSetSlotPacket.PLAYER_INVENTORY, 0, i, inventoryItem));
					}
				}
			}
		}
	}
}
