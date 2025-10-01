package cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot;

import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record ESFishingLootSubProvider(HolderLookup.Provider registries) implements LootTableSubProvider {
	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
		HolderLookup.RegistryLookup<Biome> biomes = this.registries.lookupOrThrow(Registries.BIOME);
		consumer.accept(ESLootTables.GAMEPLAY_FISHING,
			LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
					.add(NestedLootTable.lootTableReference(ESLootTables.GAMEPLAY_FISHING_JUNK).setWeight(10).setQuality(-2))
					.add(NestedLootTable.lootTableReference(ESLootTables.GAMEPLAY_FISHING_TREASURE).setWeight(5).setQuality(2)
						.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(FishingHookPredicate.inOpenWater(true)))))
					.add(NestedLootTable.lootTableReference(ESLootTables.GAMEPLAY_FISHING_FISH).setWeight(85).setQuality(-1))));
		consumer.accept(ESLootTables.GAMEPLAY_FISHING_FISH,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ESItems.ROOKFISH.get()).setWeight(25))
					.add(LootItem.lootTableItem(ESItems.ROOKFISH_AIR_SAC.get()).setWeight(5))
					.add(LootItem.lootTableItem(ESItems.LUMINOFISH.get()).setWeight(15)
						.when(LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(biomes.getOrThrow(ESBiomes.THE_ABYSS)))))
					.add(LootItem.lootTableItem(ESItems.LUMINARIS.get()).setWeight(15)
						.when(LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(biomes.getOrThrow(ESBiomes.THE_ABYSS)))))));
		consumer.accept(ESLootTables.GAMEPLAY_FISHING_JUNK,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(Items.LEATHER_BOOTS)
						.setWeight(10)
						.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))))
					.add(LootItem.lootTableItem(Items.LEATHER).setWeight(10))
					.add(LootItem.lootTableItem(Items.BONE).setWeight(10))
					.add(LootItem.lootTableItem(Items.POTION).setWeight(10).apply(SetPotionFunction.setPotion(Potions.WATER)))
					.add(LootItem.lootTableItem(Items.STRING).setWeight(5))
					.add(LootItem.lootTableItem(Items.FISHING_ROD).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.9F))))
					.add(LootItem.lootTableItem(Items.BOWL).setWeight(10))
					.add(LootItem.lootTableItem(Items.STICK).setWeight(5))
					.add(LootItem.lootTableItem(Items.INK_SAC).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(10.0F))))
					.add(LootItem.lootTableItem(Blocks.TRIPWIRE_HOOK).setWeight(10))
					.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10))
					.add(LootItem.lootTableItem(ESItems.MOONLIGHT_LILY_PAD.get()).setWeight(6))
					.add(LootItem.lootTableItem(ESItems.STARLIT_LILY_PAD.get()).setWeight(6))
					.add(LootItem.lootTableItem(ESItems.MOONLIGHT_DUCKWEED.get()).setWeight(6))
					.add(LootItem.lootTableItem(ESItems.ABYSSAL_FRUIT.get())
						.when(LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(biomes.getOrThrow(ESBiomes.THE_ABYSS))))
						.setWeight(10))
					.add(LootItem.lootTableItem(ESItems.SPIRAL_KELP.get())
						.when(LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(biomes.getOrThrow(ESBiomes.SPIRAL_KELP_FOREST))))
						.setWeight(10))
					.add(LootItem.lootTableItem(ESItems.JINGLESTEM_SAPLING.get())
						.when(LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(biomes.getOrThrow(ESBiomes.LUSH_SHALLOW_SEA))))
						.setWeight(10))));
		consumer.accept(
			ESLootTables.GAMEPLAY_FISHING_TREASURE,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.NAME_TAG))
						.add(LootItem.lootTableItem(Items.SADDLE))
						.add(LootItem.lootTableItem(Items.BOW)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.25F)))
							.apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, ConstantValue.exactly(30.0F))))
						.add(LootItem.lootTableItem(Items.FISHING_ROD)
							.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.0F, 0.25F)))
							.apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, ConstantValue.exactly(30.0F))))
						.add(LootItem.lootTableItem(Items.BOOK)
							.apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, ConstantValue.exactly(30.0F))))
						.add(LootItem.lootTableItem(Items.NAUTILUS_SHELL))
						.add(LootItem.lootTableItem(ESItems.PEARL_NECKLACE.get())
							.when(LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(biomes.getOrThrow(ESBiomes.THE_ABYSS)))))
						.add(LootItem.lootTableItem(ESItems.RAW_AETHERSENT.get())
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 10.0F))))
						.add(LootItem.lootTableItem(ESItems.SONAR_BOMB.get())
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 10.0F))))
						.add(LootItem.lootTableItem(ESItems.FROZEN_BOMB.get())
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 10.0F))))));
	}
}
