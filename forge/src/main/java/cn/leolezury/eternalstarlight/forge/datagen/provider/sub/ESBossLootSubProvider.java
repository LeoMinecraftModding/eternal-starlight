package cn.leolezury.eternalstarlight.forge.datagen.provider.sub;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ESBossLootSubProvider implements LootTableSubProvider {
	private final HolderLookup.Provider registries;

	public ESBossLootSubProvider(HolderLookup.Provider lookup) {
		this.registries = lookup;
	}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
		consumer.accept(ResourceKey.create(Registries.LOOT_TABLE, EternalStarlight.id("bosses/boss_common")),
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(3, 5))
					.add(LootItem.lootTableItem(ESItems.SEEKING_EYE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
					.add(LootItem.lootTableItem(ESItems.LUNAR_BERRIES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
					.add(LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
					.add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
					.add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
					.add(LootItem.lootTableItem(ESItems.SWAMP_SILVER_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(30))
					.add(LootItem.lootTableItem(ESItems.THERMAL_SPRINGSTONE_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(30))
					.add(LootItem.lootTableItem(ESItems.AETHERSENT_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(15))));

		consumer.accept(ResourceKey.create(Registries.LOOT_TABLE, EternalStarlight.id("bosses/starlight_golem")),
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, EternalStarlight.id("bosses/boss_common")))))
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(3, 5))
					.add(LootItem.lootTableItem(ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get())))
				.withPool(LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1))
					.add(LootItem.lootTableItem(ESItems.FORGE_ARMOR_TRIM_SMITHING_TEMPLATE.get())))
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(0, 1))
					.add(LootItem.lootTableItem(ESItems.ENERGY_SWORD.get()))));

		consumer.accept(ResourceKey.create(Registries.LOOT_TABLE, EternalStarlight.id("bosses/lunar_monstrosity")),
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, EternalStarlight.id("bosses/boss_common")))))
				.withPool(LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1))
					.add(LootItem.lootTableItem(ESItems.MOONRING_BOW.get())))
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(3, 5))
					.add(LootItem.lootTableItem(ESItems.TENACIOUS_PETAL.get())))
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(1, 2))
					.add(LootItem.lootTableItem(ESItems.TRAPPED_SOUL.get())))
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(5, 8))
					.add(LootItem.lootTableItem(ESItems.TANGLED_SKULL.get())))
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(0, 1))
					.add(LootItem.lootTableItem(ESItems.WAND_OF_TELEPORTATION.get()))));
	}
}
