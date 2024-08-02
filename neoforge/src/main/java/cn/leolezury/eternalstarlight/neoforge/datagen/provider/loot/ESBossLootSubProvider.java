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
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
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
		consumer.accept(ESLootTables.BOSS_COMMON,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(5, 8))
					.add(LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
					.add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
					.add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
					.add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(32, 64))).setWeight(50))
					.add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(32, 64))).setWeight(50))
					.add(LootItem.lootTableItem(ESItems.AMARAMBER_ARROW.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(20, 32))).setWeight(35))
					.add(LootItem.lootTableItem(ESItems.SEEKING_EYE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
					.add(LootItem.lootTableItem(ESItems.LUNAR_BERRIES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
					.add(LootItem.lootTableItem(ESItems.SWAMP_SILVER_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(30))
					.add(LootItem.lootTableItem(ESItems.THERMAL_SPRINGSTONE_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(30))
					.add(LootItem.lootTableItem(ESItems.GLACITE_SHARD.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(30))
					.add(LootItem.lootTableItem(ESItems.AETHERSENT_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(15))));

		consumer.accept(ESLootTables.BOSS_THE_GATEKEEPER,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
					.add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
					.add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
					.add(LootItem.lootTableItem(ESItems.SEEKING_EYE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))).setWeight(75))));

		consumer.accept(ESLootTables.BOSS_STARLIGHT_GOLEM,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.add(NestedLootTable.lootTableReference(ESLootTables.BOSS_COMMON)))
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(3, 5))
					.add(LootItem.lootTableItem(ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get())))
				.withPool(LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1))
					.add(LootItem.lootTableItem(ESItems.FORGE_ARMOR_TRIM_SMITHING_TEMPLATE.get())))
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(0, 1))
					.add(LootItem.lootTableItem(ESItems.ENERGY_SWORD.get()))));

		consumer.accept(ESLootTables.BOSS_TANGLED_HATRED,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(3, 5))
					.add(LootItem.lootTableItem(ESItems.TENACIOUS_VINE.get())))
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(1, 2))
					.add(LootItem.lootTableItem(ESItems.TRAPPED_SOUL.get()))));

		consumer.accept(ESLootTables.BOSS_LUNAR_MONSTROSITY,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.add(NestedLootTable.lootTableReference(ESLootTables.BOSS_COMMON)))
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
					.add(LootItem.lootTableItem(ESItems.WAND_OF_TELEPORTATION.get())))
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(0, 1))
					.when(LootItemRandomChanceCondition.randomChance(0.4f))
					.add(LootItem.lootTableItem(ESItems.CRESCENT_SPEAR.get()))));
	}
}
