package cn.leolezury.eternalstarlight.forge.datagen.provider.sub;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ESBossLootSubProvider implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(EternalStarlight.MOD_ID, "bosses/boss_common"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(3, 5))
                                .add(LootItem.lootTableItem(ItemInit.SEEKING_EYE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
                                .add(LootItem.lootTableItem(ItemInit.LUNAR_BERRIES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
                                .add(LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(75))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
                                .add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).setWeight(50))
                                .add(LootItem.lootTableItem(ItemInit.SWAMP_SILVER_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(30))
                                .add(LootItem.lootTableItem(ItemInit.THERMAL_SPRINGSTONE_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(30))
                                .add(LootItem.lootTableItem(ItemInit.AETHERSENT_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))).setWeight(15))));

        consumer.accept(new ResourceLocation(EternalStarlight.MOD_ID, "bosses/starlight_golem"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootTableReference.lootTableReference(new ResourceLocation(EternalStarlight.MOD_ID, "bosses/boss_common"))))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(3, 5))
                                .add(LootItem.lootTableItem(ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get()))));

        consumer.accept(new ResourceLocation(EternalStarlight.MOD_ID, "bosses/lunar_monstrosity"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootTableReference.lootTableReference(new ResourceLocation(EternalStarlight.MOD_ID, "bosses/boss_common"))))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(3, 5))
                                .add(LootItem.lootTableItem(ItemInit.TENACIOUS_PETAL.get())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ItemInit.WAND_OF_TELEPORTATION.get()))));
    }
}
