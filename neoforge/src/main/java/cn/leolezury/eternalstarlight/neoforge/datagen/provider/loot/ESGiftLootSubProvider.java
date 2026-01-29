package cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot;

import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record ESGiftLootSubProvider(HolderLookup.Provider registries) implements LootTableSubProvider {

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
		consumer.accept(ESLootTables.GAMEPLAY_STARFIRE_BIRD_GIFT,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(2, 4))
					.add(LootItem.lootTableItem(Items.FEATHER).setWeight(25).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
					.add(LootItem.lootTableItem(ESItems.STARFIRE.get()).setWeight(35).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
					.add(LootItem.lootTableItem(ESItems.DEEPSILVER_NUGGET.get()).setWeight(12).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 10.0F))))));
	}
}
