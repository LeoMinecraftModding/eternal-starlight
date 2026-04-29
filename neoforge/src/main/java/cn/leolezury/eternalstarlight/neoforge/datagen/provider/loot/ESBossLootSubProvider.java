package cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.data.ESPaintingVariants;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record ESBossLootSubProvider(HolderLookup.Provider registries) implements LootTableSubProvider {

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
		HolderLookup.RegistryLookup<PaintingVariant> paintings = this.registries.lookupOrThrow(Registries.PAINTING_VARIANT);

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
					.add(LootItem.lootTableItem(ESItems.DEEPSILVER_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(30))
					.add(LootItem.lootTableItem(ESItems.THERMAL_SPRINGSTONE_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(30))
					.add(LootItem.lootTableItem(ESItems.GLACITE_SHARD.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(30))
					.add(LootItem.lootTableItem(ESItems.AETHERSENT_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(15))));

		consumer.accept(ESLootTables.BOSS_THE_GATEKEEPER,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.BOOK.get())))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.ORB_OF_PROPHECY.get())))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.GLISTERING_SWORD.get()).when(LootItemRandomChanceCondition.randomChance(0.3f)))
					.add(LootItem.lootTableItem(ESItems.GLISTERING_GREATSWORD.get()).when(LootItemRandomChanceCondition.randomChance(0.3f)))
					.add(LootItem.lootTableItem(ESItems.GLISTERING_MORNING_STAR.get()).when(LootItemRandomChanceCondition.randomChance(0.3f)))
					.add(LootItem.lootTableItem(ESItems.GLISTERING_BOW.get()).when(LootItemRandomChanceCondition.randomChance(0.3f))))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.MUSIC_DISC_OPTIMIZED_OPTION.get())))
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(5, 8))
					.add(LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
					.add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
					.add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
					.add(LootItem.lootTableItem(ESItems.SEEKING_EYE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 10))).setWeight(75))));

		consumer.accept(ESLootTables.BOSS_PERMAFROST,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.add(NestedLootTable.lootTableReference(ESLootTables.BOSS_COMMON)))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.COLDSNAP.get())))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.OXIDIZED_GOLEM_STEEL_NUGGET.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 12)))))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.FROZEN_TUBE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(12, 18)))))
				.withPool(LootPool.lootPool()
					.when(LootItemRandomChanceCondition.randomChance(0.5f))
					.add(LootItem.lootTableItem(ESItems.STARLIT_PAINTING.get()).apply(SetComponentsFunction.setComponent(DataComponents.ENTITY_DATA, CustomData.EMPTY.update(registries.createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC, paintings.getOrThrow(ESPaintingVariants.ABSOLUTE_ZERO)).getOrThrow().update((compoundTag) -> compoundTag.putString("id", EternalStarlight.ID + ":painting")))))));

		consumer.accept(ESLootTables.BOSS_STARLIGHT_GOLEM,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.add(NestedLootTable.lootTableReference(ESLootTables.BOSS_COMMON)))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 10)))))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.FORGE_ARMOR_TRIM_SMITHING_TEMPLATE.get())))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.OXIDIZED_ALLOY_FURNACE.get())))
				.withPool(LootPool.lootPool()
					.when(LootItemRandomChanceCondition.randomChance(0.75f))
					.add(LootItem.lootTableItem(ESItems.ENERGY_SWORD.get())))
				.withPool(LootPool.lootPool()
					.when(LootItemRandomChanceCondition.randomChance(0.5f))
					.add(LootItem.lootTableItem(ESItems.MUSIC_DISC_MECHANICAL_FOSSIL.get())))
				.withPool(LootPool.lootPool()
					.when(LootItemRandomChanceCondition.randomChance(0.5f))
					.add(LootItem.lootTableItem(ESItems.STARLIT_PAINTING.get()).apply(SetComponentsFunction.setComponent(DataComponents.ENTITY_DATA, CustomData.EMPTY.update(registries.createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC, paintings.getOrThrow(ESPaintingVariants.ENERGIZED)).getOrThrow().update((compoundTag) -> compoundTag.putString("id", EternalStarlight.ID + ":painting")))))));

		consumer.accept(ESLootTables.BOSS_LUNAR_MONSTROSITY,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.add(NestedLootTable.lootTableReference(ESLootTables.BOSS_COMMON)))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.MOONRING_BOW.get())))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.TENACIOUS_PETAL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(10, 15)))))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.TENACIOUS_VINE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(10, 15)))))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.SOUL_DEW.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 4)))))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.TANGLED_SKULL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 8)))))
				.withPool(LootPool.lootPool()
					.when(LootItemRandomChanceCondition.randomChance(0.75f))
					.add(LootItem.lootTableItem(ESItems.WAND_OF_TELEPORTATION.get())))
				.withPool(LootPool.lootPool()
					.when(LootItemRandomChanceCondition.randomChance(0.75f))
					.add(LootItem.lootTableItem(ESItems.CRESCENT_SPEAR.get())))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.CRESCENT_PENDANT.get())))
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.TWINING_ARMOR_TRIM_SMITHING_TEMPLATE.get())))
				.withPool(LootPool.lootPool()
					.when(LootItemRandomChanceCondition.randomChance(0.5f))
					.add(LootItem.lootTableItem(ESItems.MUSIC_DISC_MOONLIGHT.get())))
				.withPool(LootPool.lootPool()
					.when(LootItemRandomChanceCondition.randomChance(0.5f))
					.add(LootItem.lootTableItem(ESItems.MUSIC_DISC_FAKE_LIGHT.get())))
				.withPool(LootPool.lootPool()
					.when(LootItemRandomChanceCondition.randomChance(0.5f))
					.add(LootItem.lootTableItem(ESItems.STARLIT_PAINTING.get()).apply(SetComponentsFunction.setComponent(DataComponents.ENTITY_DATA, CustomData.EMPTY.update(registries.createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC, paintings.getOrThrow(ESPaintingVariants.MONSTROUS)).getOrThrow().update((compoundTag) -> compoundTag.putString("id", EternalStarlight.ID + ":painting")))))));
	}
}
