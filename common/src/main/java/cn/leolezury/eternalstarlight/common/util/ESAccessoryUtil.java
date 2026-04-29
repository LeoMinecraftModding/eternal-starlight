package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.item.component.Accessory;
import cn.leolezury.eternalstarlight.common.item.component.ItemStackList;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class ESAccessoryUtil {
	public static Set<Item> getActiveAccessoriesOnArmors(LivingEntity entity) {
		return getActiveAccessories(entity, Set.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));
	}

	public static Set<Item> getActiveAccessories(LivingEntity entity, Set<EquipmentSlot> slots) {
		Set<Item> result = new HashSet<>();
		for (EquipmentSlot slot : slots) {
			result.addAll(getAccessories(entity.getItemBySlot(slot)));
		}
		return result;
	}

	public static Set<Item> getAccessories(ItemStack stack) {
		return stack.getOrDefault(ESDataComponents.ACCESSORIES.get(), List.<ItemStack>of()).stream().map(ItemStack::getItem).collect(Collectors.toSet());
	}

	public static int getAccessorySlotCount(ItemStack stack) {
		return stack.getOrDefault(ESDataComponents.ACCESSORY_SLOT_COUNT.get(), 1);
	}

	public static void applyAccessory(ItemStack equipmentStack, ItemStack accessoryStack) {
		if (accessoryStack.isEmpty()) {
			return;
		}
		List<ItemStack> accessories = new ArrayList<>(equipmentStack.getOrDefault(ESDataComponents.ACCESSORIES.get(), List.of()));
		accessories.add(accessoryStack.copyWithCount(1));
		equipmentStack.set(ESDataComponents.ACCESSORIES.get(), new ItemStackList(Collections.unmodifiableList(accessories)));
	}

	public static void removeAccessory(ItemStack equipmentStack, ItemStack accessoryStack) {
		List<ItemStack> accessories = new ArrayList<>(equipmentStack.getOrDefault(ESDataComponents.ACCESSORIES.get(), List.of()));
		accessories.removeIf(stack -> stack.is(accessoryStack.getItem()));
		equipmentStack.set(ESDataComponents.ACCESSORIES.get(), new ItemStackList(Collections.unmodifiableList(accessories)));
	}

	public static boolean overrideEquipmentOnAccessory(ItemStack stack, Slot slot, ClickAction action, Player player) {
		if (stack.getCount() != 1 || action != ClickAction.SECONDARY) return false;
		List<ItemStack> accessories = stack.getOrDefault(ESDataComponents.ACCESSORIES.get(), List.of());
		ItemStack slotItem = slot.getItem();
		Accessory accessory = slotItem.get(ESDataComponents.ACCESSORY.get());
		if (accessories.isEmpty() && accessory == null) return false;
		if (slotItem.isEmpty()) {
			if (!accessories.isEmpty()) {
				ItemStack removed = accessories.getLast();
				removeAccessory(stack, removed);
				ItemStack remain = slot.safeInsert(removed.copy());
				applyAccessory(stack, remain);
			}
		} else if (accessory != null && stack.is(accessory.combinationTarget()) && getAccessorySlotCount(stack) > accessories.size() && accessories.stream().noneMatch(s -> s.is(slotItem.getItem()))) {
			ItemStack taken = slot.safeTake(slotItem.getCount(), 1, player);
			applyAccessory(stack, taken);
		}
		return true;
	}

	public static boolean overrideAccessoryOnEquipment(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
		if (stack.getCount() != 1) return false;
		Accessory accessory = other.get(ESDataComponents.ACCESSORY.get());
		if (!other.isEmpty() && accessory == null) return false;
		if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
			List<ItemStack> accessories = stack.getOrDefault(ESDataComponents.ACCESSORIES.get(), List.of());
			if (other.isEmpty()) {
				if (!accessories.isEmpty()) {
					ItemStack removed = accessories.getLast();
					removeAccessory(stack, removed);
					access.set(removed.copy());
				}
			} else if (accessory != null && stack.is(accessory.combinationTarget()) && getAccessorySlotCount(stack) > accessories.size() && accessories.stream().noneMatch(s -> s.is(other.getItem()))) {
				applyAccessory(stack, other);
				other.shrink(1);
			}
			return true;
		} else {
			return false;
		}
	}
}
