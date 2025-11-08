package cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot;

import cn.leolezury.eternalstarlight.common.data.ESEnchantments;
import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESPotions;
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
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record ESBarteringLootSubProvider(HolderLookup.Provider registries) implements LootTableSubProvider {

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
		HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		consumer.accept(ESLootTables.GAMEPLAY_STRANGHOUL_BARTERING,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.setRolls(UniformGenerator.between(2, 4))
					.add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(new EnchantRandomlyFunction.Builder().withEnchantment(enchantments.getOrThrow(ESEnchantments.TEARING))))
					.add(LootItem.lootTableItem(Items.SPLASH_POTION).setWeight(8).apply(SetPotionFunction.setPotion(ESPotions.HUNGER.asHolder())))
					.add(LootItem.lootTableItem(Items.POTION).setWeight(10).apply(SetPotionFunction.setPotion(Potions.WATER)))
					.add(LootItem.lootTableItem(Items.LEATHER).setWeight(30).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
					.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(30).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
					.add(LootItem.lootTableItem(ESItems.SEEDS_LAUNCHER.get()).apply(EnchantWithLevelsFunction.enchantWithLevels(registries, UniformGenerator.between(10.0F, 20.0F))).setWeight(4))
					.add(LootItem.lootTableItem(ESItems.BATTLEAXE_PENDANT.get()).setWeight(4))
					.add(LootItem.lootTableItem(ESItems.SEEKING_EYE.get()).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
					.add(LootItem.lootTableItem(ESItems.PUNGENCY_FRUIT_UPGRADE_SMITHING_TEMPLATE.get()).setWeight(8))
					.add(LootItem.lootTableItem(ESItems.DRYING_RACK.get()).setWeight(8))
					.add(LootItem.lootTableItem(ESItems.DEEPSILVER_NUGGET.get()).setWeight(12).apply(SetItemCountFunction.setCount(UniformGenerator.between(10.0F, 36.0F))))
					.add(LootItem.lootTableItem(ESItems.THIOQUARTZ_SHARD.get()).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 12.0F))))
					.add(LootItem.lootTableItem(ESItems.SALTPETER_POWDER.get()).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 12.0F))))
					.add(LootItem.lootTableItem(ESItems.GRIMSTONE.get()).setWeight(20))
					.add(LootItem.lootTableItem(ESItems.VOIDSTONE.get()).setWeight(20))
					.add(LootItem.lootTableItem(ESItems.MALARITE_ARROW.get()).setWeight(30).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 9.0F))))
					.add(LootItem.lootTableItem(ESItems.ROTTEN_FLESH_JERKY.get()).setWeight(30).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
					.add(LootItem.lootTableItem(ESItems.PUNGENCY_FRUIT.get()).setWeight(30).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
					.add(LootItem.lootTableItem(ESItems.PUNGENCY_FRUIT_SEEDS.get()).setWeight(32).apply(SetItemCountFunction.setCount(UniformGenerator.between(6.0F, 12.0F))))));
	}
}
