package cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.*;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;
import java.util.stream.Collectors;

public class ESBlockLootSubProvider extends BlockLootSubProvider {
	protected static final LootItemCondition.Builder HAS_SHEARS_OR_SICKLE = HAS_SHEARS.or(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ESTags.Items.SICKLES)));

	public ESBlockLootSubProvider(HolderLookup.Provider lookup) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookup);
	}

	@Override
	protected void generate() {
		HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		add(ESBlocks.LUNAR_LEAVES.get(), block -> this.createLunarLeavesDrops(block, ESBlocks.LUNAR_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ESBlocks.CYAN_LUNAR_LEAVES.get(), block -> this.createLunarLeavesDrops(block, ESBlocks.LUNAR_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ESBlocks.PURPLE_LUNAR_LEAVES.get(), block -> this.createLunarLeavesDrops(block, ESBlocks.LUNAR_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		dropSelf(ESBlocks.LUNAR_LOG.get());
		dropSelf(ESBlocks.LUNAR_WOOD.get());
		dropSelf(ESBlocks.LUNAR_PLANKS.get());
		dropSelf(ESBlocks.STRIPPED_LUNAR_LOG.get());
		dropSelf(ESBlocks.STRIPPED_LUNAR_WOOD.get());
		add(ESBlocks.LUNAR_DOOR.get(), this::createDoorTable);
		dropSelf(ESBlocks.LUNAR_TRAPDOOR.get());
		dropSelf(ESBlocks.LUNAR_PRESSURE_PLATE.get());
		dropSelf(ESBlocks.LUNAR_BUTTON.get());
		dropSelf(ESBlocks.LUNAR_FENCE.get());
		dropSelf(ESBlocks.LUNAR_FENCE_GATE.get());
		add(ESBlocks.LUNAR_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.LUNAR_STAIRS.get());
		dropSelf(ESBlocks.LUNAR_SIGN.get());
		dropSelf(ESBlocks.LUNAR_WALL_SIGN.get());
		dropSelf(ESBlocks.LUNAR_HANGING_SIGN.get());
		dropSelf(ESBlocks.LUNAR_WALL_HANGING_SIGN.get());
		dropSelf(ESBlocks.LUNAR_SAPLING.get());
		dropPottedContents(ESBlocks.POTTED_LUNAR_SAPLING.get());

		dropSelf(ESBlocks.DEAD_LUNAR_LOG.get());
		dropSelf(ESBlocks.RED_CRYSTALLIZED_LUNAR_LOG.get());
		dropSelf(ESBlocks.BLUE_CRYSTALLIZED_LUNAR_LOG.get());

		add(ESBlocks.NORTHLAND_LEAVES.get(), block -> this.createLeavesDrops(block, ESBlocks.NORTHLAND_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		dropSelf(ESBlocks.NORTHLAND_LOG.get());
		dropSelf(ESBlocks.NORTHLAND_WOOD.get());
		dropSelf(ESBlocks.NORTHLAND_PLANKS.get());
		dropSelf(ESBlocks.STRIPPED_NORTHLAND_LOG.get());
		dropSelf(ESBlocks.STRIPPED_NORTHLAND_WOOD.get());
		add(ESBlocks.NORTHLAND_DOOR.get(), this::createDoorTable);
		dropSelf(ESBlocks.NORTHLAND_TRAPDOOR.get());
		dropSelf(ESBlocks.NORTHLAND_PRESSURE_PLATE.get());
		dropSelf(ESBlocks.NORTHLAND_BUTTON.get());
		dropSelf(ESBlocks.NORTHLAND_FENCE.get());
		dropSelf(ESBlocks.NORTHLAND_FENCE_GATE.get());
		add(ESBlocks.NORTHLAND_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.NORTHLAND_STAIRS.get());
		dropSelf(ESBlocks.NORTHLAND_SIGN.get());
		dropSelf(ESBlocks.NORTHLAND_WALL_SIGN.get());
		dropSelf(ESBlocks.NORTHLAND_HANGING_SIGN.get());
		dropSelf(ESBlocks.NORTHLAND_WALL_HANGING_SIGN.get());
		dropSelf(ESBlocks.NORTHLAND_SAPLING.get());
		dropPottedContents(ESBlocks.POTTED_NORTHLAND_SAPLING.get());

		add(ESBlocks.BANYIN_LEAVES.get(), block -> this.createLeavesDrops(block, ESBlocks.BANYIN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		dropSelf(ESBlocks.BANYIN_LOG.get());
		dropSelf(ESBlocks.BANYIN_WOOD.get());
		dropSelf(ESBlocks.BANYIN_PLANKS.get());
		dropSelf(ESBlocks.STRIPPED_BANYIN_LOG.get());
		dropSelf(ESBlocks.STRIPPED_BANYIN_WOOD.get());
		add(ESBlocks.BANYIN_DOOR.get(), this::createDoorTable);
		dropSelf(ESBlocks.BANYIN_TRAPDOOR.get());
		dropSelf(ESBlocks.BANYIN_PRESSURE_PLATE.get());
		dropSelf(ESBlocks.BANYIN_BUTTON.get());
		dropSelf(ESBlocks.BANYIN_FENCE.get());
		dropSelf(ESBlocks.BANYIN_FENCE_GATE.get());
		add(ESBlocks.BANYIN_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.BANYIN_STAIRS.get());
		dropSelf(ESBlocks.BANYIN_SIGN.get());
		dropSelf(ESBlocks.BANYIN_WALL_SIGN.get());
		dropSelf(ESBlocks.BANYIN_HANGING_SIGN.get());
		dropSelf(ESBlocks.BANYIN_WALL_HANGING_SIGN.get());
		dropSelf(ESBlocks.BANYIN_SAPLING.get());
		dropPottedContents(ESBlocks.POTTED_BANYIN_SAPLING.get());
		dropSelf(ESBlocks.BANYIN_ROOTS.get());
		dropSelf(ESBlocks.MUDDY_BANYIN_ROOTS.get());

		add(ESBlocks.SCARLET_LEAVES.get(), block -> this.createLeavesDrops(block, ESBlocks.SCARLET_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ESBlocks.SCARLET_LEAVES_PILE.get(), (block) -> LootTable.lootTable()
			.withPool(LootPool.lootPool()
				.when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS))
				.add(AlternativesEntry.alternatives(
					AlternativesEntry.alternatives(
						LayeredBlock.LAYERS.getPossibleValues(), (i) ->
							LootItem.lootTableItem(Items.STICK)
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayeredBlock.LAYERS, i)))
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0, i)))
					).when(HAS_SHEARS_OR_SICKLE.invert()),
					AlternativesEntry.alternatives(
						LayeredBlock.LAYERS.getPossibleValues(), (i) ->
							LootItem.lootTableItem(ESBlocks.SCARLET_LEAVES_PILE.get())
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayeredBlock.LAYERS, i)))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(i)))
					)
				))
			)
		);
		dropSelf(ESBlocks.SCARLET_LOG.get());
		dropSelf(ESBlocks.SCARLET_WOOD.get());
		dropSelf(ESBlocks.SCARLET_PLANKS.get());
		dropSelf(ESBlocks.STRIPPED_SCARLET_LOG.get());
		dropSelf(ESBlocks.STRIPPED_SCARLET_WOOD.get());
		add(ESBlocks.SCARLET_DOOR.get(), this::createDoorTable);
		dropSelf(ESBlocks.SCARLET_TRAPDOOR.get());
		dropSelf(ESBlocks.SCARLET_PRESSURE_PLATE.get());
		dropSelf(ESBlocks.SCARLET_BUTTON.get());
		dropSelf(ESBlocks.SCARLET_FENCE.get());
		dropSelf(ESBlocks.SCARLET_FENCE_GATE.get());
		add(ESBlocks.SCARLET_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.SCARLET_STAIRS.get());
		dropSelf(ESBlocks.SCARLET_SIGN.get());
		dropSelf(ESBlocks.SCARLET_WALL_SIGN.get());
		dropSelf(ESBlocks.SCARLET_HANGING_SIGN.get());
		dropSelf(ESBlocks.SCARLET_WALL_HANGING_SIGN.get());
		dropSelf(ESBlocks.SCARLET_SAPLING.get());
		dropPottedContents(ESBlocks.POTTED_SCARLET_SAPLING.get());

		add(ESBlocks.TORREYA_LEAVES.get(), block -> this.createLeavesDrops(block, ESBlocks.TORREYA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		dropSelf(ESBlocks.TORREYA_LOG.get());
		dropSelf(ESBlocks.TORREYA_WOOD.get());
		dropSelf(ESBlocks.TORREYA_PLANKS.get());
		dropSelf(ESBlocks.STRIPPED_TORREYA_LOG.get());
		dropSelf(ESBlocks.STRIPPED_TORREYA_WOOD.get());
		add(ESBlocks.TORREYA_DOOR.get(), this::createDoorTable);
		dropSelf(ESBlocks.TORREYA_TRAPDOOR.get());
		dropSelf(ESBlocks.TORREYA_PRESSURE_PLATE.get());
		dropSelf(ESBlocks.TORREYA_BUTTON.get());
		dropSelf(ESBlocks.TORREYA_FENCE.get());
		dropSelf(ESBlocks.TORREYA_FENCE_GATE.get());
		add(ESBlocks.TORREYA_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.TORREYA_STAIRS.get());
		dropSelf(ESBlocks.TORREYA_SIGN.get());
		dropSelf(ESBlocks.TORREYA_WALL_SIGN.get());
		dropSelf(ESBlocks.TORREYA_HANGING_SIGN.get());
		dropSelf(ESBlocks.TORREYA_WALL_HANGING_SIGN.get());
		dropSelf(ESBlocks.TORREYA_SAPLING.get());
		dropPottedContents(ESBlocks.POTTED_TORREYA_SAPLING.get());
		dropSelf(ESBlocks.TORREYA_VINES.get());
		dropOther(ESBlocks.TORREYA_VINES_PLANT.get(), ESBlocks.TORREYA_VINES.get());
		add(ESBlocks.TORREYA_CAMPFIRE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(ESItems.RAW_AMARAMBER.get()))));

		add(ESBlocks.HANGING_ALGALEAVES.get(), block -> this.createLeavesDrops(block, ESBlocks.JINGLESTEM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ESBlocks.HANGING_ALGALEAVES_PLANT.get(), block -> this.createLeavesDrops(ESBlocks.HANGING_ALGALEAVES.get(), ESBlocks.JINGLESTEM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ESBlocks.ALGALEAVES.get(), block -> createMultifaceBlockDrops(block, HAS_SHEARS_OR_SICKLE));
		dropSelf(ESBlocks.JINGLESTEM_LOG.get());
		dropSelf(ESBlocks.JINGLESTEM_WOOD.get());
		dropSelf(ESBlocks.JINGLESTEM_PLANKS.get());
		dropSelf(ESBlocks.STRIPPED_JINGLESTEM_LOG.get());
		dropSelf(ESBlocks.STRIPPED_JINGLESTEM_WOOD.get());
		add(ESBlocks.JINGLESTEM_DOOR.get(), this::createDoorTable);
		dropSelf(ESBlocks.JINGLESTEM_TRAPDOOR.get());
		dropSelf(ESBlocks.JINGLESTEM_PRESSURE_PLATE.get());
		dropSelf(ESBlocks.JINGLESTEM_BUTTON.get());
		dropSelf(ESBlocks.JINGLESTEM_FENCE.get());
		dropSelf(ESBlocks.JINGLESTEM_FENCE_GATE.get());
		add(ESBlocks.JINGLESTEM_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.JINGLESTEM_STAIRS.get());
		dropSelf(ESBlocks.JINGLESTEM_SIGN.get());
		dropSelf(ESBlocks.JINGLESTEM_WALL_SIGN.get());
		dropSelf(ESBlocks.JINGLESTEM_HANGING_SIGN.get());
		dropSelf(ESBlocks.JINGLESTEM_WALL_HANGING_SIGN.get());
		dropSelf(ESBlocks.JINGLESTEM_SAPLING.get());
		dropPottedContents(ESBlocks.POTTED_JINGLESTEM_SAPLING.get());

		add(ESBlocks.CRADLEWOOD_LEAVES.get(), block -> this.createLeavesDrops(block, ESBlocks.CRADLEWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		dropSelf(ESBlocks.CRADLEWOOD_LOG.get());
		dropSelf(ESBlocks.CRADLEWOOD_WOOD.get());
		dropSelf(ESBlocks.CRADLEWOOD_PLANKS.get());
		dropSelf(ESBlocks.STRIPPED_CRADLEWOOD_LOG.get());
		dropSelf(ESBlocks.STRIPPED_CRADLEWOOD_WOOD.get());
		add(ESBlocks.CRADLEWOOD_DOOR.get(), this::createDoorTable);
		dropSelf(ESBlocks.CRADLEWOOD_TRAPDOOR.get());
		dropSelf(ESBlocks.CRADLEWOOD_PRESSURE_PLATE.get());
		dropSelf(ESBlocks.CRADLEWOOD_BUTTON.get());
		dropSelf(ESBlocks.CRADLEWOOD_FENCE.get());
		dropSelf(ESBlocks.CRADLEWOOD_FENCE_GATE.get());
		add(ESBlocks.CRADLEWOOD_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.CRADLEWOOD_STAIRS.get());
		dropSelf(ESBlocks.CRADLEWOOD_SIGN.get());
		dropSelf(ESBlocks.CRADLEWOOD_WALL_SIGN.get());
		dropSelf(ESBlocks.CRADLEWOOD_HANGING_SIGN.get());
		dropSelf(ESBlocks.CRADLEWOOD_WALL_HANGING_SIGN.get());
		dropSelf(ESBlocks.CRADLEWOOD_SAPLING.get());
		dropPottedContents(ESBlocks.POTTED_CRADLEWOOD_SAPLING.get());

		add(ESBlocks.GRIMSTONE.get(), block -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.COBBLED_GRIMSTONE.get()));
		add(ESBlocks.GRIMSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.GRIMSTONE_STAIRS.get());
		dropSelf(ESBlocks.GRIMSTONE_WALL.get());
		dropSelf(ESBlocks.COBBLED_GRIMSTONE.get());
		add(ESBlocks.COBBLED_GRIMSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.COBBLED_GRIMSTONE_STAIRS.get());
		dropSelf(ESBlocks.COBBLED_GRIMSTONE_WALL.get());
		dropSelf(ESBlocks.GRIMSTONE_BRICKS.get());
		add(ESBlocks.GRIMSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.GRIMSTONE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.GRIMSTONE_BRICK_WALL.get());
		dropSelf(ESBlocks.CRACKED_GRIMSTONE_BRICKS.get());
		dropSelf(ESBlocks.POLISHED_GRIMSTONE.get());
		add(ESBlocks.POLISHED_GRIMSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_GRIMSTONE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_GRIMSTONE_WALL.get());
		dropSelf(ESBlocks.GRIMSTONE_TILES.get());
		add(ESBlocks.GRIMSTONE_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.GRIMSTONE_TILE_STAIRS.get());
		dropSelf(ESBlocks.GRIMSTONE_TILE_WALL.get());
		dropSelf(ESBlocks.CRACKED_GRIMSTONE_TILES.get());
		dropSelf(ESBlocks.POLISHED_GRIMSTONE_TILES.get());
		add(ESBlocks.POLISHED_GRIMSTONE_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_GRIMSTONE_TILE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_GRIMSTONE_TILE_WALL.get());
		dropSelf(ESBlocks.CHISELED_GRIMSTONE.get());
		dropSelf(ESBlocks.GLOWING_GRIMSTONE.get());

		add(ESBlocks.VOIDSTONE.get(), block -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.COBBLED_VOIDSTONE.get()));
		add(ESBlocks.VOIDSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.VOIDSTONE_STAIRS.get());
		dropSelf(ESBlocks.VOIDSTONE_WALL.get());
		dropSelf(ESBlocks.COBBLED_VOIDSTONE.get());
		add(ESBlocks.COBBLED_VOIDSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.COBBLED_VOIDSTONE_STAIRS.get());
		dropSelf(ESBlocks.COBBLED_VOIDSTONE_WALL.get());
		dropSelf(ESBlocks.VOIDSTONE_BRICKS.get());
		add(ESBlocks.VOIDSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.VOIDSTONE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.VOIDSTONE_BRICK_WALL.get());
		dropSelf(ESBlocks.CRACKED_VOIDSTONE_BRICKS.get());
		dropSelf(ESBlocks.POLISHED_VOIDSTONE.get());
		add(ESBlocks.POLISHED_VOIDSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_VOIDSTONE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_VOIDSTONE_WALL.get());
		dropSelf(ESBlocks.VOIDSTONE_TILES.get());
		add(ESBlocks.VOIDSTONE_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.VOIDSTONE_TILE_STAIRS.get());
		dropSelf(ESBlocks.VOIDSTONE_TILE_WALL.get());
		dropSelf(ESBlocks.CRACKED_VOIDSTONE_TILES.get());
		dropSelf(ESBlocks.CHISELED_VOIDSTONE.get());
		dropSelf(ESBlocks.GLOWING_VOIDSTONE.get());
		dropSelf(ESBlocks.VOIDSTONE_SPIKE.get());

		dropSelf(ESBlocks.ETERNAL_ICE.get());
		dropWhenSilkTouch(ESBlocks.THIN_ETERNAL_ICE.get());
		dropSelf(ESBlocks.ETERNAL_ICE_BRICKS.get());
		add(ESBlocks.ETERNAL_ICE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.ETERNAL_ICE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.ETERNAL_ICE_BRICK_WALL.get());
		dropSelf(ESBlocks.ETERNAL_ICE_LANTERN.get());
		dropSelf(ESBlocks.HAZE_ICE.get());
		dropSelf(ESBlocks.HAZE_ICE_BRICKS.get());
		add(ESBlocks.HAZE_ICE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.HAZE_ICE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.HAZE_ICE_BRICK_WALL.get());
		dropSelf(ESBlocks.HAZE_ICE_LANTERN.get());
		dropSelf(ESBlocks.REINFORCED_ICE.get());
		dropSelf(ESBlocks.REINFORCED_ICE_PANE.get());
		dropSelf(ESBlocks.ICICLE.get());
		add(ESBlocks.ASHEN_SNOW.get(), (block) -> LootTable.lootTable()
			.withPool(LootPool.lootPool()
				.when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS))
				.add(AlternativesEntry.alternatives(
					AlternativesEntry.alternatives(
						SnowLayerBlock.LAYERS.getPossibleValues(), (i) ->
							LootItem.lootTableItem(ESItems.ASHEN_SNOWBALL.get())
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SnowLayerBlock.LAYERS, i)))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(i)))
					).when(this.doesNotHaveSilkTouch()),
					AlternativesEntry.alternatives(
						SnowLayerBlock.LAYERS.getPossibleValues(), (i) ->
							LootItem.lootTableItem(ESBlocks.ASHEN_SNOW.get())
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SnowLayerBlock.LAYERS, i)))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(i)))
					)
				))
			)
		);

		dropSelf(ESBlocks.NEBULAITE.get());
		dropSelf(ESBlocks.NEBULAITE_BRICKS.get());
		add(ESBlocks.NEBULAITE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.NEBULAITE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.NEBULAITE_BRICK_WALL.get());
		dropSelf(ESBlocks.CHISELED_NEBULAITE_BRICKS.get());

		dropSelf(ESBlocks.STARCORE_BLOCK.get());
		dropSelf(ESBlocks.BLAZING_STARCORE_BLOCK.get());
		dropSelf(ESBlocks.STARCORE_LIGHT.get());
		add(ESBlocks.GRIMSTONE_STARCORE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.STARCORE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.VOIDSTONE_STARCORE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.STARCORE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.ETERNAL_ICE_STARCORE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.STARCORE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.HAZE_ICE_STARCORE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.STARCORE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.RADIANITE.get(), block -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.COBBLED_RADIANITE.get()));
		add(ESBlocks.RADIANITE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.RADIANITE_STAIRS.get());
		dropSelf(ESBlocks.RADIANITE_WALL.get());
		dropSelf(ESBlocks.COBBLED_RADIANITE.get());
		add(ESBlocks.COBBLED_RADIANITE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.COBBLED_RADIANITE_STAIRS.get());
		dropSelf(ESBlocks.COBBLED_RADIANITE_WALL.get());
		dropSelf(ESBlocks.RADIANITE_PILLAR.get());
		dropSelf(ESBlocks.POLISHED_RADIANITE.get());
		add(ESBlocks.POLISHED_RADIANITE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_RADIANITE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_RADIANITE_WALL.get());
		dropSelf(ESBlocks.RADIANITE_BRICKS.get());
		add(ESBlocks.RADIANITE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.RADIANITE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.RADIANITE_BRICK_WALL.get());
		dropSelf(ESBlocks.CHISELED_RADIANITE.get());
		dropSelf(ESBlocks.FLARE_BRICKS.get());
		add(ESBlocks.FLARE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.FLARE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.FLARE_BRICK_WALL.get());
		dropSelf(ESBlocks.CUT_FLARE_BRICKS.get());
		add(ESBlocks.CUT_FLARE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.CUT_FLARE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.CUT_FLARE_BRICK_WALL.get());
		dropSelf(ESBlocks.FLARE_TILES.get());
		add(ESBlocks.FLARE_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.FLARE_TILE_STAIRS.get());
		dropSelf(ESBlocks.FLARE_TILE_WALL.get());
		dropSelf(ESBlocks.CUT_FLARE_TILES.get());
		add(ESBlocks.CUT_FLARE_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.CUT_FLARE_TILE_STAIRS.get());
		dropSelf(ESBlocks.CUT_FLARE_TILE_WALL.get());
		dropSelf(ESBlocks.CHISELED_FLARE_PILLAR.get());

		dropSelf(ESBlocks.STELLAGMITE.get());
		add(ESBlocks.STELLAGMITE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.STELLAGMITE_STAIRS.get());
		dropSelf(ESBlocks.STELLAGMITE_WALL.get());
		dropOther(ESBlocks.MOLTEN_STELLAGMITE.get(), ESBlocks.STELLAGMITE.get());
		add(ESBlocks.MOLTEN_STELLAGMITE_SLAB.get(), block -> createSlabItemTable(ESBlocks.STELLAGMITE_SLAB.get()));
		dropOther(ESBlocks.MOLTEN_STELLAGMITE_STAIRS.get(), ESBlocks.STELLAGMITE_STAIRS.get());
		dropOther(ESBlocks.MOLTEN_STELLAGMITE_WALL.get(), ESBlocks.STELLAGMITE_WALL.get());
		dropSelf(ESBlocks.POLISHED_STELLAGMITE.get());
		add(ESBlocks.POLISHED_STELLAGMITE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_STELLAGMITE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_STELLAGMITE_WALL.get());

		add(ESBlocks.ABYSSAL_FIRE.get(), noDrop());

		dropSelf(ESBlocks.ABYSSLATE.get());
		dropSelf(ESBlocks.POLISHED_ABYSSLATE.get());
		add(ESBlocks.POLISHED_ABYSSLATE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_ABYSSLATE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_ABYSSLATE_WALL.get());
		dropSelf(ESBlocks.POLISHED_ABYSSLATE_BRICKS.get());
		add(ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_ABYSSLATE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_ABYSSLATE_BRICK_WALL.get());
		dropSelf(ESBlocks.CHISELED_POLISHED_ABYSSLATE.get());
		dropSelf(ESBlocks.ABYSSAL_MAGMA_BLOCK.get());
		dropOther(ESBlocks.ABYSSAL_GEYSER.get(), ESItems.ABYSSLATE.get());

		dropSelf(ESBlocks.THERMABYSSLATE.get());
		dropSelf(ESBlocks.POLISHED_THERMABYSSLATE.get());
		add(ESBlocks.POLISHED_THERMABYSSLATE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_THERMABYSSLATE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_THERMABYSSLATE_WALL.get());
		dropSelf(ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get());
		add(ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_THERMABYSSLATE_BRICK_WALL.get());
		dropSelf(ESBlocks.CHISELED_POLISHED_THERMABYSSLATE.get());
		dropSelf(ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get());
		dropOther(ESBlocks.THERMABYSSAL_GEYSER.get(), ESItems.THERMABYSSLATE.get());

		dropSelf(ESBlocks.CRYOBYSSLATE.get());
		dropSelf(ESBlocks.POLISHED_CRYOBYSSLATE.get());
		add(ESBlocks.POLISHED_CRYOBYSSLATE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_CRYOBYSSLATE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_CRYOBYSSLATE_WALL.get());
		dropSelf(ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get());
		add(ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_WALL.get());
		dropSelf(ESBlocks.CHISELED_POLISHED_CRYOBYSSLATE.get());
		dropSelf(ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get());
		dropOther(ESBlocks.CRYOBYSSAL_GEYSER.get(), ESItems.CRYOBYSSLATE.get());

		dropSelf(ESBlocks.NIGHTFALL_MUD.get());
		dropSelf(ESBlocks.GLOWING_NIGHTFALL_MUD.get());
		dropSelf(ESBlocks.PACKED_NIGHTFALL_MUD.get());
		dropSelf(ESBlocks.NIGHTFALL_MUD_BRICKS.get());
		add(ESBlocks.NIGHTFALL_MUD_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.NIGHTFALL_MUD_BRICK_STAIRS.get());
		dropSelf(ESBlocks.NIGHTFALL_MUD_BRICK_WALL.get());

		dropSelf(ESBlocks.TWILIGHT_SAND.get());
		dropSelf(ESBlocks.TWILIGHT_SANDSTONE.get());
		add(ESBlocks.TWILIGHT_SANDSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.TWILIGHT_SANDSTONE_STAIRS.get());
		dropSelf(ESBlocks.TWILIGHT_SANDSTONE_WALL.get());
		dropSelf(ESBlocks.CUT_TWILIGHT_SANDSTONE.get());
		add(ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.CUT_TWILIGHT_SANDSTONE_STAIRS.get());
		dropSelf(ESBlocks.CUT_TWILIGHT_SANDSTONE_WALL.get());
		dropSelf(ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get());

		add(ESBlocks.DUSTED_GRAVEL.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(ESItems.DUSTED_SHARD.get()).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(block)))));
		dropSelf(ESBlocks.DUSTED_BRICKS.get());
		add(ESBlocks.DUSTED_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.DUSTED_BRICK_STAIRS.get());
		dropSelf(ESBlocks.DUSTED_BRICK_WALL.get());
		add(ESBlocks.MOSSY_DUSTED_GRAVEL.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(ESItems.DUSTED_SHARD.get()).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(ESBlocks.DUSTED_GRAVEL.get())))));
		add(ESBlocks.GLOWING_MOSSY_DUSTED_GRAVEL.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(ESItems.DUSTED_SHARD.get()).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(ESBlocks.DUSTED_GRAVEL.get())))));
		add(ESBlocks.SUSPICIOUS_DUSTED_GRAVEL.get(), noDrop());

		add(ESBlocks.DIMSLAG.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, AlternativesEntry.alternatives(
			AlternativesEntry.alternatives(
				LootItem.lootTableItem(ESItems.OXIDIZED_GOLEM_STEEL_NUGGET.get()).when(LootItemRandomChanceCondition.randomChance(0.05f)),
				LootItem.lootTableItem(ESItems.DEEPSILVER_NUGGET.get())
			).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.15F, 0.25F, 0.5F, 1.0F)),
			LootItem.lootTableItem(block)
		))));
		add(ESBlocks.SUSPICIOUS_DIMSLAG.get(), noDrop());

		dropSelf(ESBlocks.STARLIGHT_FLOWER.get());
		dropPottedContents(ESBlocks.POTTED_STARLIGHT_FLOWER.get());
		dropSelf(ESBlocks.AUREATE_FLOWER.get());
		dropPottedContents(ESBlocks.POTTED_AUREATE_FLOWER.get());
		dropSelf(ESBlocks.CONEBLOOM.get());
		dropPottedContents(ESBlocks.POTTED_CONEBLOOM.get());
		dropSelf(ESBlocks.NIGHTFAN.get());
		dropPottedContents(ESBlocks.POTTED_NIGHTFAN.get());
		dropSelf(ESBlocks.PINK_ROSE.get());
		dropPottedContents(ESBlocks.POTTED_PINK_ROSE.get());
		dropSelf(ESBlocks.STARLIGHT_TORCHFLOWER.get());
		dropPottedContents(ESBlocks.POTTED_STARLIGHT_TORCHFLOWER.get());
		add(ESBlocks.NIGHTFAN_BUSH.get(), this::createDoublePlantDrops);
		add(ESBlocks.PINK_ROSE_BUSH.get(), this::createDoublePlantDrops);
		plant(ESBlocks.NIGHT_SPROUTS.get());
		plant(ESBlocks.SMALL_NIGHT_SPROUTS.get());
		plant(ESBlocks.GLOWING_NIGHT_SPROUTS.get());
		plant(ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS.get());
		plant(ESBlocks.LUNAR_GRASS.get());
		plant(ESBlocks.GLOWING_LUNAR_GRASS.get());
		plant(ESBlocks.CRESCENT_GRASS.get());
		dropPottedContents(ESBlocks.POTTED_CRESCENT_GRASS.get());
		plant(ESBlocks.GLOWING_CRESCENT_GRASS.get());
		dropPottedContents(ESBlocks.POTTED_GLOWING_CRESCENT_GRASS.get());
		plant(ESBlocks.PARASOL_GRASS.get());
		dropPottedContents(ESBlocks.POTTED_PARASOL_GRASS.get());
		plant(ESBlocks.GLOWING_PARASOL_GRASS.get());
		dropPottedContents(ESBlocks.POTTED_GLOWING_PARASOL_GRASS.get());
		plant(ESBlocks.LUNAR_BUSH.get());
		plant(ESBlocks.GLOWING_LUNAR_BUSH.get());
		add(ESBlocks.TALL_CRESCENT_GRASS.get(), this::createDoublePlantDrops);
		add(ESBlocks.TALL_GLOWING_CRESCENT_GRASS.get(), this::createDoublePlantDrops);
		add(ESBlocks.LUNAR_REED.get(), this::createDoublePlantDrops);
		dropSelf(ESBlocks.WHISPERBLOOM.get());
		dropPottedContents(ESBlocks.POTTED_WHISPERBLOOM.get());
		dropSelf(ESBlocks.GLADESPIKE.get());
		dropPottedContents(ESBlocks.POTTED_GLADESPIKE.get());
		plant(ESBlocks.VIVIDSTALK.get());
		dropPottedContents(ESBlocks.POTTED_VIVIDSTALK.get());
		add(ESBlocks.TALL_GLADESPIKE.get(), this::createDoublePlantDrops);
		plant(ESBlocks.MOONLIGHT_BUSH.get());
		add(ESBlocks.GLINTGRASS.get(), this::createDoublePlantDrops);
		dropSelf(ESBlocks.GLOWING_MUSHROOM.get());
		dropPottedContents(ESBlocks.POTTED_GLOWING_MUSHROOM.get());
		add(ESBlocks.GLOWING_MUSHROOM_BLOCK.get(), (block -> createMushroomBlockDrop(block, ESBlocks.GLOWING_MUSHROOM.get())));
		dropWhenSilkTouch(ESBlocks.GLOWING_MUSHROOM_STEM.get());
		dropSelf(ESBlocks.SHINING_MUSHROOM.get());
		dropPottedContents(ESBlocks.POTTED_SHINING_MUSHROOM.get());
		add(ESBlocks.SHINING_MUSHROOM_BLOCK.get(), (block -> createMushroomBlockDrop(block, ESBlocks.SHINING_MUSHROOM.get())));
		dropWhenSilkTouch(ESBlocks.SHINING_MUSHROOM_STEM.get());
		add(ESBlocks.BERRIES_VINES.get(), this::createBerriesVinesDrop);
		add(ESBlocks.BERRIES_VINES_PLANT.get(), this::createBerriesVinesDrop);
		plant(ESBlocks.CAVE_MOSS.get());
		plantDropOther(ESBlocks.CAVE_MOSS_PLANT.get(), ESBlocks.CAVE_MOSS.get());
		plantDropOther(ESBlocks.CAVE_MOSS_VEIN.get(), ESBlocks.CAVE_MOSS.get());
		dropSelf(ESBlocks.CAVE_MOSS_BLOCK.get());
		dropSelf(ESBlocks.CAVE_MOSS_CARPET.get());
		dropSelf(ESBlocks.BOULDERSHROOM.get());
		dropPottedContents(ESBlocks.POTTED_BOULDERSHROOM.get());
		add(ESBlocks.BOULDERSHROOM_BLOCK.get(), (block -> createMushroomBlockDrop(block, ESBlocks.BOULDERSHROOM.get())));
		dropWhenSilkTouch(ESBlocks.BOULDERSHROOM_STEM.get());
		add(ESBlocks.BOULDERSHROOM_ROOTS.get(), noDrop());
		add(ESBlocks.BOULDERSHROOM_ROOTS_PLANT.get(), noDrop());

		dropSelf(ESBlocks.SWAMP_ROSE.get());
		dropPottedContents(ESBlocks.POTTED_SWAMP_ROSE.get());
		plant(ESBlocks.FANTABUD.get());
		plant(ESBlocks.GREEN_FANTABUD.get());
		plant(ESBlocks.FANTAFERN.get());
		dropPottedContents(ESBlocks.POTTED_FANTAFERN.get());
		plant(ESBlocks.GREEN_FANTAFERN.get());
		dropPottedContents(ESBlocks.POTTED_GREEN_FANTAFERN.get());
		plant(ESBlocks.FANTAGRASS.get());
		plant(ESBlocks.GREEN_FANTAGRASS.get());
		plant(ESBlocks.HANGING_FANTAGRASS.get());
		plant(ESBlocks.HANGING_FANTAGRASS_PLANT.get());

		plant(ESBlocks.ORANGE_SCARLET_BUD.get());
		plant(ESBlocks.PURPLE_SCARLET_BUD.get());
		plant(ESBlocks.RED_SCARLET_BUD.get());
		plant(ESBlocks.SCARLET_GRASS.get());
		plant(ESBlocks.MAUVE_FERN.get());

		dropSelf(ESBlocks.WITHERED_STARLIGHT_FLOWER.get());
		dropPottedContents(ESBlocks.POTTED_WITHERED_STARLIGHT_FLOWER.get());
		dropSelf(ESBlocks.AMARAMBER_GRASS.get());
		dropPottedContents(ESBlocks.POTTED_AMARAMBER_GRASS.get());
		dropSelf(ESBlocks.AMARAMBER_GRASS_BUSH.get());
		plant(ESBlocks.GLOOMCANDLE_ROOT.get());

		dropSelf(ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get());
		dropSelf(ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
		add(ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
		add(ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
		dropSelf(ESBlocks.RED_STARLIGHT_CRYSTAL_LANTERN.get());
		dropSelf(ESBlocks.BLUE_STARLIGHT_CRYSTAL_LANTERN.get());
		deadBush(ESBlocks.DEAD_LUNAR_BUSH.get());
		dropPottedContents(ESBlocks.POTTED_DEAD_LUNAR_BUSH.get());
		dropSelf(ESBlocks.DESERT_AMETHYSIA.get());
		dropPottedContents(ESBlocks.POTTED_DESERT_AMETHYSIA.get());
		dropSelf(ESBlocks.WITHERED_DESERT_AMETHYSIA.get());
		dropPottedContents(ESBlocks.POTTED_WITHERED_DESERT_AMETHYSIA.get());
		dropSelf(ESBlocks.SUNSET_THORNBLOOM.get());
		add(ESBlocks.AMETHYSIA_GRASS.get(), block -> createPlantDrops(block, ESItems.CRINOA_SEEDS.get()));
		dropPottedContents(ESBlocks.POTTED_SUNSET_THORNBLOOM.get());
		add(ESBlocks.LUNARIS_CACTUS.get(), this::createLunarisCactusDrop);
		dropSelf(ESBlocks.LUNARIS_CACTUS_GEL_BLOCK.get());
		dropSelf(ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get());
		dropSelf(ESBlocks.LUNARIS_CACTUS_FRUIT_LANTERN.get());
		add(ESBlocks.BLOOMING_RED_STARLIGHT_CRYSTAL_CLUSTER.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
		add(ESBlocks.BLOOMING_BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
		dropSelf(ESBlocks.RED_CRYSTALFLEUR.get());
		dropPottedContents(ESBlocks.POTTED_RED_CRYSTALFLEUR.get());
		dropSelf(ESBlocks.BLUE_CRYSTALFLEUR.get());
		dropPottedContents(ESBlocks.POTTED_BLUE_CRYSTALFLEUR.get());
		add(ESBlocks.RED_CRYSTALFLEUR_VINE.get(), block -> this.createMultifaceBlockDrops(block, HAS_SHEARS_OR_SICKLE));
		add(ESBlocks.BLUE_CRYSTALFLEUR_VINE.get(), block -> this.createMultifaceBlockDrops(block, HAS_SHEARS_OR_SICKLE));
		dropSelf(ESBlocks.RED_CRYSTAL_MOSS_BLOCK.get());
		dropSelf(ESBlocks.BLUE_CRYSTAL_MOSS_BLOCK.get());
		dropSelf(ESBlocks.RED_CRYSTAL_MOSS_CARPET.get());
		dropSelf(ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get());

		dropSelf(ESBlocks.FIRE_ORCHID.get());
		dropPottedContents(ESBlocks.POTTED_FIRE_ORCHID.get());
		plant(ESBlocks.BLAZEBANK_GRASS.get());

		dropSelf(ESBlocks.MOONLIGHT_LILY_PAD.get());
		dropSelf(ESBlocks.STARLIT_LILY_PAD.get());
		dropSelf(ESBlocks.MOONLIGHT_DUCKWEED.get());

		add(ESBlocks.ABYSSAL_KELP.get(), this::createAbyssalKelpDrop);
		add(ESBlocks.ABYSSAL_KELP_PLANT.get(), this::createAbyssalKelpDrop);
		dropSelf(ESBlocks.ORBFLORA.get());
		dropOther(ESBlocks.ORBFLORA_PLANT.get(), ESBlocks.ORBFLORA.get());
		dropSelf(ESBlocks.ORBFLORA_LIGHT.get());
		dropSelf(ESBlocks.SPIRAL_KELP.get());
		dropOther(ESBlocks.SPIRAL_KELP_PLANT.get(), ESBlocks.SPIRAL_KELP.get());
		add(ESBlocks.SEA_ROSA.get(), block -> createMultifaceBlockDrops(block, HAS_SHEARS_OR_SICKLE));
		plant(ESBlocks.WICK_GRASS.get());
		dropSelf(ESBlocks.LUMENSTEM.get());
		dropOther(ESBlocks.LUMENSTEM_PLANT.get(), ESBlocks.LUMENSTEM.get());
		plant(ESBlocks.MARIMOLD.get());
		add(ESBlocks.MARIMOLD_BLOCK.get(), (block -> createMushroomBlockDrop(block, ESBlocks.MARIMOLD.get())));
		dropWhenSilkTouch(ESBlocks.MARIMOLD_STEM.get());
		plant(ESBlocks.CIRCULUSH.get());
		plant(ESBlocks.STONETT.get());
		plant(ESBlocks.LUMINIS.get());
		plant(ESBlocks.GLOWLIS.get());
		plant(ESBlocks.GLOREED.get());
		plant(ESBlocks.STARLIGHT_SEAGRASS.get());
		dropSelf(ESBlocks.JINGLING_PICKLE.get());
		dropWhenSilkTouch(ESBlocks.DEAD_TENTACLES_CORAL.get());
		dropWhenSilkTouch(ESBlocks.TENTACLES_CORAL.get());
		dropWhenSilkTouch(ESBlocks.DEAD_TENTACLES_CORAL_FAN.get());
		dropWhenSilkTouch(ESBlocks.TENTACLES_CORAL_FAN.get());
		dropSelf(ESBlocks.DEAD_TENTACLES_CORAL_BLOCK.get());
		add(ESBlocks.TENTACLES_CORAL_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.DEAD_TENTACLES_CORAL_BLOCK.get()));
		dropWhenSilkTouch(ESBlocks.DEAD_GOLDEN_CORAL.get());
		dropWhenSilkTouch(ESBlocks.GOLDEN_CORAL.get());
		dropWhenSilkTouch(ESBlocks.DEAD_GOLDEN_CORAL_FAN.get());
		dropWhenSilkTouch(ESBlocks.GOLDEN_CORAL_FAN.get());
		dropSelf(ESBlocks.DEAD_GOLDEN_CORAL_BLOCK.get());
		add(ESBlocks.GOLDEN_CORAL_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.DEAD_GOLDEN_CORAL_BLOCK.get()));
		dropWhenSilkTouch(ESBlocks.DEAD_CRYSTALLUM_CORAL.get());
		dropWhenSilkTouch(ESBlocks.CRYSTALLUM_CORAL.get());
		dropWhenSilkTouch(ESBlocks.DEAD_CRYSTALLUM_CORAL_FAN.get());
		dropWhenSilkTouch(ESBlocks.CRYSTALLUM_CORAL_FAN.get());
		dropSelf(ESBlocks.DEAD_CRYSTALLUM_CORAL_BLOCK.get());
		add(ESBlocks.CRYSTALLUM_CORAL_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.DEAD_CRYSTALLUM_CORAL_BLOCK.get()));
		add(ESBlocks.VELVETUMOSS.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(ESItems.VELVETUMOSS_BALL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		dropWhenSilkTouch(ESBlocks.VELVETUMOSS_VILLI.get());
		dropSelf(ESBlocks.RED_VELVETUMOSS.get());
		dropWhenSilkTouch(ESBlocks.RED_VELVETUMOSS_VILLI.get());
		dropSelf(ESBlocks.RED_VELVETUMOSS_FLOWER.get());
		dropPottedContents(ESBlocks.POTTED_RED_VELVETUMOSS_FLOWER.get());

		plant(ESBlocks.CRYSTALLIZED_LUNAR_GRASS.get());
		dropSelf(ESBlocks.RED_CRYSTAL_ROOTS.get());
		dropSelf(ESBlocks.BLUE_CRYSTAL_ROOTS.get());
		add(ESBlocks.TWILVEWRYM_HERB.get(), this::createDoublePlantDrops);
		add(ESBlocks.STELLAFLY_BUSH.get(), this::createDoublePlantDrops);
		add(ESBlocks.GLIMMERFLY_BUSH.get(), this::createDoublePlantDrops);

		dropSelf(ESBlocks.SACRED_STARLIGHT_FLOWER.get());
		dropPottedContents(ESBlocks.POTTED_SACRED_STARLIGHT_FLOWER.get());
		plant(ESBlocks.CRESCENTLEAF.get());
		plant(ESBlocks.GOLDEN_GRASS.get());
		add(ESBlocks.TALL_GOLDEN_GRASS.get(), this::createDoublePlantDrops);
		plant(ESBlocks.SACRED_LANTERNVINE.get());
		plant(ESBlocks.SACRED_LANTERNVINE_PLANT.get());
		plant(ESBlocks.HANGING_SACRED_LANTERNVINE.get());
		plant(ESBlocks.HANGING_SACRED_LANTERNVINE_PLANT.get());

		dropSelf(ESBlocks.NIGHTFALL_DIRT.get());
		dropOther(ESBlocks.NIGHTFALL_FARMLAND.get(), ESBlocks.NIGHTFALL_DIRT.get());
		dropOther(ESBlocks.NIGHTFALL_DIRT_PATH.get(), ESBlocks.NIGHTFALL_DIRT.get());
		add(ESBlocks.NIGHTFALL_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.NIGHTFALL_DIRT.get()));
		add(ESBlocks.NIGHTFALL_PODZOL.get(), block -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.NIGHTFALL_DIRT.get()));
		add(ESBlocks.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.NIGHTFALL_DIRT.get()));
		add(ESBlocks.FANTASY_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.NIGHTFALL_MUD.get()));
		dropSelf(ESBlocks.FANTASY_GRASS_CARPET.get());
		add(ESBlocks.GOLDEN_GRASS_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.NIGHTFALL_DIRT.get()));

		add(ESBlocks.CRINOA.get(), this.createCropDrops(ESBlocks.CRINOA.get(), ESItems.CRINOA.get(), ESItems.CRINOA_SEEDS.get(), LootItemBlockStatePropertyCondition.hasBlockStateProperties(ESBlocks.CRINOA.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CrinoaBlock.AGE, 7))));
		dropSelf(ESBlocks.CRINOA_BALE.get());

		add(ESBlocks.NOCTURNAL_MILLET_PANICLE.get(), block -> this.applyExplosionDecay(block, LootTable.lootTable()
			.withPool(LootPool.lootPool()
				.add(AlternativesEntry.alternatives(
					LootItem.lootTableItem(ESItems.NOCTURNAL_MILLET.get())
						.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ESBlocks.NOCTURNAL_MILLET_PANICLE.get())
							.setProperties(StatePropertiesPredicate.Builder.properties()
								.hasProperty(NocturnalMilletTopBlock.AGE, 2)
								.hasProperty(NocturnalMilletTopBlock.FORGOTTEN, false))),
					LootItem.lootTableItem(ESItems.FORGOTTEN_NOCTURNAL_MILLET.get())
						.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ESBlocks.NOCTURNAL_MILLET_PANICLE.get())
							.setProperties(StatePropertiesPredicate.Builder.properties()
								.hasProperty(NocturnalMilletTopBlock.AGE, 2)
								.hasProperty(NocturnalMilletTopBlock.FORGOTTEN, true))),
					LootItem.lootTableItem(ESItems.NOCTURNAL_MILLET_SEEDS.get())
				))
			)
			.withPool(LootPool.lootPool()
				.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ESBlocks.NOCTURNAL_MILLET_PANICLE.get())
					.setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(NocturnalMilletTopBlock.AGE, 2)))
				.add(LootItem.lootTableItem(ESItems.NOCTURNAL_MILLET_SEEDS.get())
					.apply(ApplyBonusCount.addBonusBinomialDistributionCount(enchantments.getOrThrow(Enchantments.FORTUNE), 0.5714286F, 3))
				)
			)
		));
		dropOther(ESBlocks.NOCTURNAL_MILLET_STALK.get(), ESItems.NOCTURNAL_MILLET_SEEDS.get());

		dropSelf(ESBlocks.RAW_AETHERSENT_BLOCK.get());
		dropSelf(ESBlocks.AETHERSENT_BLOCK.get());

		dropSelf(ESBlocks.SPRINGSTONE.get());
		add(ESBlocks.SPRINGSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.SPRINGSTONE_STAIRS.get());
		dropSelf(ESBlocks.SPRINGSTONE_WALL.get());
		dropSelf(ESBlocks.SPRINGSTONE_BRICKS.get());
		add(ESBlocks.SPRINGSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.SPRINGSTONE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.SPRINGSTONE_BRICK_WALL.get());
		dropSelf(ESBlocks.POLISHED_SPRINGSTONE.get());
		add(ESBlocks.POLISHED_SPRINGSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_SPRINGSTONE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_SPRINGSTONE_WALL.get());
		dropSelf(ESBlocks.CHISELED_SPRINGSTONE.get());

		dropSelf(ESBlocks.THERMAL_SPRINGSTONE.get());
		add(ESBlocks.THERMAL_SPRINGSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.THERMAL_SPRINGSTONE_STAIRS.get());
		dropSelf(ESBlocks.THERMAL_SPRINGSTONE_WALL.get());
		dropSelf(ESBlocks.THERMAL_SPRINGSTONE_BRICKS.get());
		add(ESBlocks.THERMAL_SPRINGSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.THERMAL_SPRINGSTONE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.THERMAL_SPRINGSTONE_BRICK_WALL.get());

		add(ESBlocks.GLACITE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.GLACITE_SHARD.get()).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		dropSelf(ESBlocks.GLACITE_BLOCK.get());

		add(ESBlocks.GRIMSTONE_STARLIT_DIAMOND_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.STARLIT_DIAMOND.get()).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.VOIDSTONE_STARLIT_DIAMOND_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.STARLIT_DIAMOND.get()).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.ETERNAL_ICE_STARLIT_DIAMOND_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.STARLIT_DIAMOND.get()).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.HAZE_ICE_STARLIT_DIAMOND_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.STARLIT_DIAMOND.get()).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		dropSelf(ESBlocks.STARLIT_DIAMOND_BLOCK.get());

		add(ESBlocks.GRIMSTONE_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
		add(ESBlocks.VOIDSTONE_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
		add(ESBlocks.ETERNAL_ICE_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
		add(ESBlocks.HAZE_ICE_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
		add(ESBlocks.NIGHTFALL_MUD_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
		add(ESBlocks.PACKED_NIGHTFALL_MUD_DEEPSILVER_ORE.get(), block -> this.createOreDrop(block, ESItems.RAW_DEEPSILVER.get()));
		dropSelf(ESBlocks.RAW_DEEPSILVER_BLOCK.get());
		dropSelf(ESBlocks.DEEPSILVER_BLOCK.get());
		dropSelf(ESBlocks.DEEPSILVER_GRATE.get());
		dropSelf(ESBlocks.DEEPSILVER_BARS.get());

		dropSelf(ESBlocks.UNREALIUM_BLOCK.get());
		dropSelf(ESBlocks.UNREALIUM_BARS.get());

		add(ESBlocks.GRIMSTONE_MALARITE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.MALARITE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.VOIDSTONE_MALARITE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.MALARITE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.NIGHTFALL_MUD_MALARITE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.MALARITE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.PACKED_NIGHTFALL_MUD_MALARITE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.MALARITE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		dropSelf(ESBlocks.MALARITE_BLOCK.get());

		add(ESBlocks.GRIMSTONE_SALTPETER_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.SALTPETER_POWDER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.VOIDSTONE_SALTPETER_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.SALTPETER_POWDER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.ETERNAL_ICE_SALTPETER_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.SALTPETER_POWDER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.HAZE_ICE_SALTPETER_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.SALTPETER_POWDER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		dropSelf(ESBlocks.SALTPETER_BLOCK.get());

		add(ESBlocks.PUNGENCY_FRUIT_VINES.get(), this.createCropDrops(ESBlocks.PUNGENCY_FRUIT_VINES.get(), ESItems.PUNGENCY_FRUIT.get(), ESItems.PUNGENCY_FRUIT_SEEDS.get(), LootItemBlockStatePropertyCondition.hasBlockStateProperties(ESBlocks.PUNGENCY_FRUIT_VINES.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PungencyFruitVinesBlock.AGE, 7))));
		dropSelf(ESBlocks.TEAR_BOMB.get());

		dropSelf(ESBlocks.DRYING_RACK.get());

		add(ESBlocks.STARFIRE_BIRD_NEST.get(), this::createStarfireBirdNestDrop);
		add(ESBlocks.OAK_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.SPRUCE_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.BIRCH_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.ACACIA_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.CHERRY_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.JUNGLE_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.DARK_OAK_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.CRIMSON_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.WARPED_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.MANGROVE_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.BAMBOO_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.LUNAR_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.NORTHLAND_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.BANYIN_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.SCARLET_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.TORREYA_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.JINGLESTEM_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);
		add(ESBlocks.CRADLEWOOD_STARFIRE_BIRD_AVIARY.get(), this::createStarfireBirdAviaryDrop);

		dropSelf(ESBlocks.RAW_FLOWGLAZE.get());
		dropSelf(ESBlocks.FLOWGLAZE.get());
		dropSelf(ESBlocks.FLOWGLAZE_PANE.get());
		dropSelf(ESBlocks.FLOWGLAZE_BRICKS.get());
		add(ESBlocks.FLOWGLAZE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.FLOWGLAZE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.FLOWGLAZE_BRICK_WALL.get());

		dropSelf(ESBlocks.RAW_AMARAMBER_BLOCK.get());
		dropSelf(ESBlocks.AMARAMBER_LANTERN.get());
		add(ESBlocks.AMARAMBER_CANDLE.get(), this::createCandleDrops);
		add(ESBlocks.AMARAMBER_CANDLE_CAKE.get(), createCandleCakeDrops(ESBlocks.AMARAMBER_CANDLE.get()));
		add(ESBlocks.AMARAMBER_FIRE.get(), noDrop());
		dropSelf(ESBlocks.AMARAMBER_BRICKS.get());
		add(ESBlocks.AMARAMBER_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.AMARAMBER_BRICK_STAIRS.get());
		dropSelf(ESBlocks.AMARAMBER_BRICK_WALL.get());
		dropSelf(ESBlocks.TORREYA_TILES.get());
		add(ESBlocks.TORREYA_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.TORREYA_TILE_STAIRS.get());
		dropSelf(ESBlocks.TORREYA_TILE_WALL.get());

		add(ESBlocks.THIOQUARTZ_BLOCK.get(), this::createThioquartzBlockDrop);
		add(ESBlocks.BUDDING_THIOQUARTZ.get(), noDrop());
		add(ESBlocks.THIOQUARTZ_CLUSTER.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ESItems.THIOQUARTZ_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.THIOQUARTZ_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
		dropSelf(ESBlocks.TOXITE.get());
		add(ESBlocks.TOXITE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.TOXITE_STAIRS.get());
		dropSelf(ESBlocks.TOXITE_WALL.get());
		dropSelf(ESBlocks.TOXITE_BRICKS.get());
		add(ESBlocks.TOXITE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.TOXITE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.TOXITE_BRICK_WALL.get());
		dropSelf(ESBlocks.POLISHED_TOXITE.get());
		add(ESBlocks.POLISHED_TOXITE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_TOXITE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_TOXITE_WALL.get());
		dropSelf(ESBlocks.CHISELED_TOXITE.get());

		add(ESBlocks.GRIMSTONE_REDSTONE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.VOIDSTONE_REDSTONE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.ETERNAL_ICE_REDSTONE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.HAZE_ICE_REDSTONE_ORE.get(), block -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));

		dropSelf(ESBlocks.WHITE_YETI_FUR.get());
		dropSelf(ESBlocks.ORANGE_YETI_FUR.get());
		dropSelf(ESBlocks.MAGENTA_YETI_FUR.get());
		dropSelf(ESBlocks.LIGHT_BLUE_YETI_FUR.get());
		dropSelf(ESBlocks.YELLOW_YETI_FUR.get());
		dropSelf(ESBlocks.LIME_YETI_FUR.get());
		dropSelf(ESBlocks.PINK_YETI_FUR.get());
		dropSelf(ESBlocks.GRAY_YETI_FUR.get());
		dropSelf(ESBlocks.LIGHT_GRAY_YETI_FUR.get());
		dropSelf(ESBlocks.CYAN_YETI_FUR.get());
		dropSelf(ESBlocks.PURPLE_YETI_FUR.get());
		dropSelf(ESBlocks.BLUE_YETI_FUR.get());
		dropSelf(ESBlocks.BROWN_YETI_FUR.get());
		dropSelf(ESBlocks.GREEN_YETI_FUR.get());
		dropSelf(ESBlocks.RED_YETI_FUR.get());
		dropSelf(ESBlocks.BLACK_YETI_FUR.get());

		dropSelf(ESBlocks.WHITE_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.ORANGE_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.MAGENTA_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.LIGHT_BLUE_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.YELLOW_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.LIME_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.PINK_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.GRAY_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.LIGHT_GRAY_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.CYAN_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.PURPLE_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.BLUE_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.BROWN_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.GREEN_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.RED_YETI_FUR_CARPET.get());
		dropSelf(ESBlocks.BLACK_YETI_FUR_CARPET.get());

		dropSelf(ESBlocks.TANGLED_SKULL.get());
		dropOther(ESBlocks.TANGLED_WALL_SKULL.get(), ESBlocks.TANGLED_SKULL.get());

		dropSelf(ESBlocks.TOOTH_OF_HUNGER_TILES.get());
		add(ESBlocks.TOOTH_OF_HUNGER_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.TOOTH_OF_HUNGER_TILE_STAIRS.get());
		dropSelf(ESBlocks.TOOTH_OF_HUNGER_TILE_WALL.get());
		dropSelf(ESBlocks.CHISELED_TOOTH_OF_HUNGER_TILES.get());
		dropSelf(ESBlocks.CRYSTALBORN_CATALYST.get());
		dropSelf(ESBlocks.CRYSTALLIZED_SAND.get());

		add(ESBlocks.LOOT_CHEST.get(), noDrop());

		add(ESBlocks.THE_GATEKEEPER_SPAWNER.get(), noDrop());
		add(ESBlocks.STARLIGHT_GOLEM_SPAWNER.get(), noDrop());
		add(ESBlocks.PERMAFROST_SPAWNER.get(), noDrop());
		add(ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get(), noDrop());
		add(ESBlocks.SOLAR_EGG.get(), noDrop());

		dropSelf(ESBlocks.GOLEM_STEEL_BLOCK.get());
		dropSelf(ESBlocks.WAXED_GOLEM_STEEL_BLOCK.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get());
		add(ESBlocks.GOLEM_STEEL_SLAB.get(), this::createSlabItemTable);
		add(ESBlocks.WAXED_GOLEM_STEEL_SLAB.get(), this::createSlabItemTable);
		add(ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.GOLEM_STEEL_STAIRS.get());
		dropSelf(ESBlocks.WAXED_GOLEM_STEEL_STAIRS.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get());
		dropSelf(ESBlocks.GOLEM_STEEL_TILES.get());
		dropSelf(ESBlocks.WAXED_GOLEM_STEEL_TILES.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get());
		add(ESBlocks.GOLEM_STEEL_TILE_SLAB.get(), this::createSlabItemTable);
		add(ESBlocks.WAXED_GOLEM_STEEL_TILE_SLAB.get(), this::createSlabItemTable);
		add(ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.GOLEM_STEEL_TILE_STAIRS.get());
		dropSelf(ESBlocks.WAXED_GOLEM_STEEL_TILE_STAIRS.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get());
		dropSelf(ESBlocks.GOLEM_STEEL_GRATE.get());
		dropSelf(ESBlocks.WAXED_GOLEM_STEEL_GRATE.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get());
		dropSelf(ESBlocks.GOLEM_STEEL_PILLAR.get());
		dropSelf(ESBlocks.WAXED_GOLEM_STEEL_PILLAR.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_PILLAR.get());
		dropSelf(ESBlocks.GOLEM_STEEL_BARS.get());
		dropSelf(ESBlocks.WAXED_GOLEM_STEEL_BARS.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_BARS.get());
		dropSelf(ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get());
		dropSelf(ESBlocks.WAXED_CHISELED_GOLEM_STEEL_BLOCK.get());
		dropSelf(ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get());
		dropSelf(ESBlocks.GOLEM_STEEL_JET.get());
		dropSelf(ESBlocks.WAXED_GOLEM_STEEL_JET.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_JET.get());
		add(ESBlocks.GOLEM_STEEL_CRATE.get(), this::createShulkerBoxDrop);
		dropSelf(ESBlocks.ENERGY_TRANSMITTER.get());
		dropSelf(ESBlocks.ACCUMULATOR.get());
		add(ESBlocks.MECHANICAL_SPAWNER.get(), noDrop());
		dropSelf(ESBlocks.ALLOY_FURNACE.get());
		dropSelf(ESBlocks.WAXED_ALLOY_FURNACE.get());
		dropSelf(ESBlocks.OXIDIZED_ALLOY_FURNACE.get());
		add(ESBlocks.ENERGY_BLOCK.get(), noDrop());

		dropSelf(ESBlocks.SHADEGRIEVE.get());
		dropSelf(ESBlocks.BLOOMING_SHADEGRIEVE.get());
		dropSelf(ESBlocks.LUNAR_VINE.get());
		dropSelf(ESBlocks.LUNAR_MOSAIC.get());
		add(ESBlocks.LUNAR_MOSAIC_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.LUNAR_MOSAIC_STAIRS.get());
		dropSelf(ESBlocks.LUNAR_MOSAIC_FENCE.get());
		dropSelf(ESBlocks.LUNAR_MOSAIC_FENCE_GATE.get());
		dropSelf(ESBlocks.LUNAR_MAT.get());

		dropWhenSilkTouch(ESBlocks.DUSK_GLASS.get());
		dropSelf(ESBlocks.DUSK_LIGHT.get());
		dropSelf(ESBlocks.REINFORCED_DUSK_LIGHT.get());
		dropSelf(ESBlocks.DUSK_EMITTER.get());
		add(ESBlocks.DUSK_LOCKBOX.get(), noDrop());
		add(ESBlocks.FLARE_SPAWNER.get(), noDrop());
		add(ESBlocks.ECLIPSE_CORE.get(), noDrop());

		dropSelf(ESBlocks.DOOMED_TORCH.get());
		dropOther(ESBlocks.WALL_DOOMED_TORCH.get(), ESItems.DOOMED_TORCH.get());
		dropSelf(ESBlocks.DOOMED_REDSTONE_TORCH.get());
		dropOther(ESBlocks.WALL_DOOMED_REDSTONE_TORCH.get(), ESItems.DOOMED_REDSTONE_TORCH.get());
		dropSelf(ESBlocks.DOOMEDEN_BRICKS.get());
		add(ESBlocks.DOOMEDEN_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.DOOMEDEN_BRICK_STAIRS.get());
		dropSelf(ESBlocks.DOOMEDEN_BRICK_WALL.get());
		dropSelf(ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
		add(ESBlocks.POLISHED_DOOMEDEN_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_DOOMEDEN_BRICK_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_DOOMEDEN_BRICK_WALL.get());
		dropSelf(ESBlocks.DOOMEDEN_TILES.get());
		add(ESBlocks.DOOMEDEN_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.DOOMEDEN_TILE_STAIRS.get());
		dropSelf(ESBlocks.DOOMEDEN_TILE_WALL.get());
		dropSelf(ESBlocks.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
		dropSelf(ESBlocks.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
		dropSelf(ESBlocks.DOOMEDEN_LIGHT.get());
		dropSelf(ESBlocks.DOOMEDEN_KEYHOLE.get());
		dropSelf(ESBlocks.REDSTONE_DOOMEDEN_KEYHOLE.get());

		dropSelf(ESBlocks.STELLAR_RACK.get());
		add(ESBlocks.ENCHANTED_GRIMSTONE_BRICKS.get(), noDrop());
		add(ESBlocks.STARLIGHT_PORTAL.get(), noDrop());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return BuiltInRegistries.BLOCK.stream().filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(EternalStarlight.ID)).collect(Collectors.toList());
	}

	private void plant(Block block) {
		add(block, this::createSickleOrShearsOnlyDrop);
	}

	private void plantDropOther(Block block, ItemLike other) {
		add(block, createSickleOrShearsOnlyDrop(other));
	}

	private LootTable.Builder createSickleOrShearsOnlyDrop(ItemLike item) {
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_SHEARS_OR_SICKLE).add(LootItem.lootTableItem(item)));
	}

	private void deadBush(Block block) {
		add(block, b -> createPlantDrops(b, Items.STICK));
	}

	public LootTable.Builder createPlantDrops(Block block, Item additional) {
		HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
		return this.createShearsDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(additional).when(LootItemRandomChanceCondition.randomChance(0.125F)).apply(ApplyBonusCount.addUniformBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE), 2))));
	}

	protected LootTable.Builder createDoublePlantDrops(Block block) {
		return LootTable.lootTable().withPool(LootPool.lootPool()
			.add(LootItem.lootTableItem(block).when(HAS_SHEARS_OR_SICKLE))
			.when((LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))
				.and(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))), new BlockPos(0, 1, 0))))
				.or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))
					.and(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))), new BlockPos(0, -1, 0))))
			)
		);
	}

	private LootTable.Builder createLunarisCactusDrop(Block block) {
		return LootTable.lootTable()
			.withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LunarisCactusBlock.FRUIT, false)))))
			.withPool(LootPool.lootPool().add(LootItem.lootTableItem(ESItems.LUNARIS_CACTUS_FRUIT.get())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LunarisCactusBlock.FRUIT, true))));
	}

	private LootTable.Builder createBerriesVinesDrop(Block block) {
		return LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(ESItems.LUNAR_BERRIES.get())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BerriesVines.BERRIES, true))));
	}

	private LootTable.Builder createAbyssalKelpDrop(Block block) {
		return LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(ESItems.ABYSSAL_FRUIT.get())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbyssalKelp.BERRIES, true))));
	}

	private LootTable.Builder createLunarLeavesDrops(Block leaves, Block sapling, float... saplingChances) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
		return this.createLeavesDrops(leaves, sapling, saplingChances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(this.doesNotHaveShearsOrSilkTouch()).add((this.applyExplosionCondition(leaves, LootItem.lootTableItem(ESItems.LUNAR_BERRIES.get()))).when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
	}

	private LootTable.Builder createThioquartzBlockDrop(Block block) {
		return LootTable.lootTable()
			.withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ESItems.NOCTURNAL_MILLET_SEEDS.get()).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ThioquartzBlock.SEED, true))))))
			.withPool(LootPool.lootPool().add(LootItem.lootTableItem(block)));
	}

	protected LootTable.Builder createStarfireBirdNestDrop(Block block) {
		return LootTable.lootTable().withPool(LootPool.lootPool().when(this.hasSilkTouch()).setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(DataComponents.CONTAINER).include(ESDataComponents.BIRDS.get())).apply(CopyBlockState.copyState(block).copy(StarfireBirdNestBlock.EGGS))));
	}

	protected LootTable.Builder createStarfireBirdAviaryDrop(Block block) {
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).when(this.hasSilkTouch()).apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(DataComponents.CONTAINER).include(ESDataComponents.BIRDS.get())).apply(CopyBlockState.copyState(block).copy(StarfireBirdNestBlock.EGGS)).otherwise(LootItem.lootTableItem(block).apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(DataComponents.CONTAINER)))));
	}

	private LootItemCondition.Builder hasShearsOrSilkTouch() {
		return HAS_SHEARS.or(this.hasSilkTouch());
	}

	private LootItemCondition.Builder doesNotHaveShearsOrSilkTouch() {
		return this.hasShearsOrSilkTouch().invert();
	}
}
