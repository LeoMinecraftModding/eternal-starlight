package cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Gleech;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class ESEntityLootSubProvider extends EntityLootSubProvider {
	public ESEntityLootSubProvider(HolderLookup.Provider lookup) {
		super(FeatureFlags.REGISTRY.allFlags(), lookup);
	}

	@Override
	public void generate() {
		add(ESEntities.GLEECH.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.GLEECH_EGG.get())
					.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NbtPredicate(Gleech.NOT_LARVAL))))
					.when(LootItemRandomChanceCondition.randomChance(0.3f))
					.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F))))));

		add(ESEntities.LONESTAR_SKELETON.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(Items.BONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.SWAMP_SILVER_NUGGET.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F))))));

		add(ESEntities.NIGHTFALL_SPIDER.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(Items.STRING).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.NIGHTFALL_SPIDER_EYE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))).when(LootItemKilledByPlayerCondition.killedByPlayer())));

		add(ESEntities.THIRST_WALKER.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.when(LootItemRandomChanceCondition.randomChance(0.3f))
				.add(LootItem.lootTableItem(ESItems.TOOTH_OF_HUNGER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F))))));

		add(ESEntities.ENT.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.LUNAR_BERRIES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.LUNAR_LEAVES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F))))));

		add(ESEntities.RATLIN.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.CAVE_MOSS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.RATLIN_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F))).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())))));

		add(ESEntities.YETI.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(Items.STRING).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.WHITE_YETI_FUR.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F))))));

		add(ESEntities.AURORA_DEER.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 2.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.AURORA_DEER_STEAK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F))).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())))));

		add(ESEntities.CRYSTALLIZED_MOTH.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.when(LootItemRandomChanceCondition.randomChance(0.3f))
				.add(LootItem.lootTableItem(ESItems.SHIVERING_GEL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F))))));

		add(ESEntities.SHIMMER_LACEWING.get(), LootTable.lootTable());

		add(ESEntities.GRIMSTONE_GOLEM.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.GRIMSTONE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.COBBLED_GRIMSTONE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F))))));

		add(ESEntities.LUMINOFISH.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.LUMINOFISH.get()).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(Items.BONE_MEAL)).when(LootItemRandomChanceCondition.randomChance(0.05F))));

		add(ESEntities.LUMINARIS.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.LUMINARIS.get()).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(Items.BONE_MEAL)).when(LootItemRandomChanceCondition.randomChance(0.05F))));

		add(ESEntities.TWILIGHT_GAZE.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(Items.BONE_MEAL)).when(LootItemRandomChanceCondition.randomChance(0.05F))));

		add(ESEntities.FREEZE.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(ESItems.FROZEN_TUBE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 2.0F))))));

		add(ESEntities.TANGLED.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(Items.VINE).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))))
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(Items.BONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F))))));

		add(ESEntities.TANGLED_SKULL.get(), LootTable.lootTable()
			.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.when(LootItemRandomChanceCondition.randomChance(0.3F))
				.add(LootItem.lootTableItem(ESItems.TANGLED_SKULL.get()))));

		// no loot

		// how dare you hurt the cute npc
		add(ESEntities.BOARWARF.get(), LootTable.lootTable());
		add(ESEntities.ASTRAL_GOLEM.get(), LootTable.lootTable());

		// our boss uses custom loots
		add(ESEntities.THE_GATEKEEPER.get(), LootTable.lootTable());
		add(ESEntities.STARLIGHT_GOLEM.get(), LootTable.lootTable());
		add(ESEntities.TANGLED_HATRED.get(), LootTable.lootTable());
		add(ESEntities.TANGLED_HATRED_PART.get(), LootTable.lootTable());
		add(ESEntities.LUNAR_MONSTROSITY.get(), LootTable.lootTable());
	}

	@Override
	protected Stream<EntityType<?>> getKnownEntityTypes() {
		return BuiltInRegistries.ENTITY_TYPE.stream().filter(type -> BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace().equals(EternalStarlight.ID));
	}
}
