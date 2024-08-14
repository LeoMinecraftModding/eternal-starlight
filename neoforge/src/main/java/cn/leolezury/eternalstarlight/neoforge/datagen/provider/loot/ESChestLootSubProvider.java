package cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot;

import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ESChestLootSubProvider implements LootTableSubProvider {
	private final HolderLookup.Provider registries;

	public ESChestLootSubProvider(HolderLookup.Provider lookup) {
		this.registries = lookup;
	}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
		HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		consumer.accept(ESLootTables.GOLEM_FORGE_CHEST,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.setRolls(ConstantValue.exactly(4))
					.add(LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 20))).setWeight(25))
					.add(LootItem.lootTableItem(Items.STONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 9))).setWeight(25))
					.add(LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 9))).setWeight(50))
					.add(LootItem.lootTableItem(Items.CHARCOAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 9))).setWeight(50))
					.add(LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))).setWeight(40))
					.add(LootItem.lootTableItem(Items.GUNPOWDER).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))).setWeight(20))
					.add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(6, 15))).setWeight(50))
					.add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 12))).setWeight(40))
					.add(LootItem.lootTableItem(ESItems.AMARAMBER_ARROW.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 10))).setWeight(35))
					.add(LootItem.lootTableItem(ESItems.VOIDSTONE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
					.add(LootItem.lootTableItem(ESItems.VOIDSTONE_BRICK_SLAB.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
					.add(LootItem.lootTableItem(ESItems.VOIDSTONE_BRICK_STAIRS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
					.add(LootItem.lootTableItem(ESItems.SWAMP_SILVER_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(25))
					.add(LootItem.lootTableItem(ESItems.BLAZE_CRYSTAL.get()).setWeight(20)))
				.withPool(LootPool.lootPool()
					.setRolls(ConstantValue.exactly(3))
					.add(LootItem.lootTableItem(Items.LAVA_BUCKET).setWeight(50))
					.add(LootItem.lootTableItem(Items.WATER_BUCKET).setWeight(50))
					.add(LootItem.lootTableItem(Items.BOOK).apply(new EnchantRandomlyFunction.Builder().withOneOf(enchantments.getOrThrow(ESTags.Enchantments.GOLEM_FORGE_LOOT))).setWeight(25))
					.add(LootItem.lootTableItem(Items.NETHERRACK).setWeight(10))
					.add(LootItem.lootTableItem(ESItems.COOKED_RATLIN_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))).setWeight(25))
					.add(LootItem.lootTableItem(ESItems.COOKED_AURORA_DEER_STEAK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))).setWeight(12))
					.add(LootItem.lootTableItem(ESItems.LUNAR_BERRIES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))).setWeight(50))
					.add(LootItem.lootTableItem(ESItems.FROZEN_TUBE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))).setWeight(30))
					.add(LootItem.lootTableItem(ESItems.AMARAMBER_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 10))).setWeight(30))
					.add(LootItem.lootTableItem(ESItems.AETHERSENT_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))).setWeight(15))
					.add(LootItem.lootTableItem(ESItems.SWAMP_SILVER_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))).setWeight(45))
					.add(LootItem.lootTableItem(ESItems.GLACITE_SHARD.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))).setWeight(20)))
				.withPool(LootPool.lootPool()
					.setRolls(ConstantValue.exactly(2))
					.add(LootItem.lootTableItem(Items.BOOK).apply(new EnchantRandomlyFunction.Builder().withOneOf(enchantments.getOrThrow(ESTags.Enchantments.GOLEM_FORGE_LOOT))).setWeight(75))
					.add(LootItem.lootTableItem(Items.GOLDEN_CARROT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(25))
					.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(10))
					.add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
					.add(LootItem.lootTableItem(Items.POTION).apply(SetPotionFunction.setPotion(Potions.STRONG_HEALING)).setWeight(15))
					.add(LootItem.lootTableItem(ESItems.MUSIC_DISC_WHISPER_OF_THE_STARS.get()).setWeight(3))
					.add(LootItem.lootTableItem(ESItems.MUSIC_DISC_TRANQUILITY.get()).setWeight(3))
					.add(LootItem.lootTableItem(ESItems.MUSIC_DISC_ATLANTIS.get()).setWeight(3))
					.add(LootItem.lootTableItem(ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get()).setWeight(5))));

		consumer.accept(ESLootTables.CURSED_GARDEN_CHEST,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.setRolls(ConstantValue.exactly(4))
					.add(LootItem.lootTableItem(Items.MOSS_BLOCK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(25))
					.add(LootItem.lootTableItem(Items.VINE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 6))).setWeight(25))
					.add(LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 20))).setWeight(25))
					.add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(6, 15))).setWeight(50))
					.add(LootItem.lootTableItem(Items.MILK_BUCKET).setWeight(20))
					.add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 12))).setWeight(40))
					.add(LootItem.lootTableItem(ESItems.AMARAMBER_ARROW.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 10))).setWeight(35))
					.add(LootItem.lootTableItem(ESItems.LUNAR_GRASS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
					.add(LootItem.lootTableItem(ESItems.GLOWING_LUNAR_GRASS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(25))
					.add(LootItem.lootTableItem(ESItems.PARASOL_GRASS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
					.add(LootItem.lootTableItem(ESItems.GLOWING_PARASOL_GRASS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(25))
					.add(LootItem.lootTableItem(ESItems.CRESCENT_GRASS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
					.add(LootItem.lootTableItem(ESItems.GLOWING_CRESCENT_GRASS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(25))
					.add(LootItem.lootTableItem(ESItems.BLAZE_CRYSTAL.get()).setWeight(20))
					.add(LootItem.lootTableItem(ESItems.LUNAR_CRYSTAL.get()).setWeight(20)))
				.withPool(LootPool.lootPool()
					.setRolls(ConstantValue.exactly(3))
					.add(LootItem.lootTableItem(Items.BOOK).apply(new EnchantRandomlyFunction.Builder().withOneOf(enchantments.getOrThrow(ESTags.Enchantments.CURSED_GARDEN_LOOT))).setWeight(40))
					.add(LootItem.lootTableItem(ESItems.LUNAR_BERRIES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))).setWeight(60))
					.add(LootItem.lootTableItem(ESItems.COOKED_RATLIN_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))).setWeight(35))
					.add(LootItem.lootTableItem(ESItems.COOKED_AURORA_DEER_STEAK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 10))).setWeight(15))
					.add(LootItem.lootTableItem(ESItems.SALTPETER_POWDER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))).setWeight(20))
					.add(LootItem.lootTableItem(ESItems.SALTPETER_MATCHBOX.get()).setWeight(10)))
				.withPool(LootPool.lootPool()
					.setRolls(ConstantValue.exactly(2))
					.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(15))
					.add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
					.add(LootItem.lootTableItem(Items.POTION).apply(SetPotionFunction.setPotion(Potions.STRONG_HEALING)).setWeight(25))
					.add(LootItem.lootTableItem(ESItems.MUSIC_DISC_WHISPER_OF_THE_STARS.get()).setWeight(2))
					.add(LootItem.lootTableItem(ESItems.MUSIC_DISC_TRANQUILITY.get()).setWeight(2))
					.add(LootItem.lootTableItem(ESItems.MUSIC_DISC_ATLANTIS.get()).setWeight(2))
					.add(LootItem.lootTableItem(ESItems.AMARAMBER_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 8))).setWeight(30))
					.add(LootItem.lootTableItem(ESItems.AETHERSENT_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))).setWeight(25))
					.add(LootItem.lootTableItem(ESItems.SWAMP_SILVER_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 9))).setWeight(45))
					.add(LootItem.lootTableItem(ESItems.THERMAL_SPRINGSTONE_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(25))));
	}
}
