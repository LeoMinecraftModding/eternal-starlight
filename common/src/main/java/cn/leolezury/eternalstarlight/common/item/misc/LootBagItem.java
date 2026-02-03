package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class LootBagItem extends Item {
	public LootBagItem(Properties properties) {
		super(properties);
	}

	private boolean dropLoot(Level level, Player player, ItemStack stack) {
		ResourceKey<LootTable> component = stack.get(ESDataComponents.LOOT_TABLE.get());
		if (!level.isClientSide && level instanceof ServerLevel serverLevel && component != null) {
			MinecraftServer server = serverLevel.getServer();
			LootTable table = server.reloadableRegistries().getLootTable(component);
			LootParams.Builder paramBuilder = new LootParams.Builder(serverLevel);
			LootParams params = paramBuilder.create(LootContextParamSets.EMPTY);
			table.getRandomItems(params).forEach((loot) -> {
				ItemEntity itemEntity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), loot);
				ESDataAttachments.IMPORTANT_ITEM.setData(itemEntity, true);
				itemEntity.setNoPickUpDelay();
				itemEntity.setTarget(player.getUUID());
				itemEntity.setExtendedLifetime();
				level.addFreshEntity(itemEntity);
			});
			stack.consume(1, player);
		}
		return component != null;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		dropLoot(level, player, stack);
		return InteractionResultHolder.consume(stack);
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
		if (other.isEmpty() && action == ClickAction.SECONDARY) {
			return dropLoot(player.level(), player, stack);
		}
		return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
	}
}
