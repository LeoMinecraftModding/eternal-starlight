package cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AbyssalKelp;
import cn.leolezury.eternalstarlight.common.block.BerriesVines;
import cn.leolezury.eternalstarlight.common.block.LunarisCactusBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;
import java.util.stream.Collectors;

public class ESBlockLootSubProvider extends BlockLootSubProvider {
	public ESBlockLootSubProvider(HolderLookup.Provider lookup) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookup);
	}

	@Override
	protected void generate() {
		HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		add(ESBlocks.BERRIES_VINES.get(), this::createBerriesVinesDrop);
		add(ESBlocks.BERRIES_VINES_PLANT.get(), this::createBerriesVinesDrop);
		dropSelf(ESBlocks.CAVE_MOSS.get());
		dropOther(ESBlocks.CAVE_MOSS_PLANT.get(), ESBlocks.CAVE_MOSS.get());
		dropOther(ESBlocks.CAVE_MOSS_VEIN.get(), ESBlocks.CAVE_MOSS.get());
		add(ESBlocks.ABYSSAL_KELP.get(), this::createAbyssalKelpDrop);
		add(ESBlocks.ABYSSAL_KELP_PLANT.get(), this::createAbyssalKelpDrop);
		dropSelf(ESBlocks.ORBFLORA.get());
		add(ESBlocks.ORBFLORA_PLANT.get(), noDrop());
		dropSelf(ESBlocks.ORBFLORA_LIGHT.get());

		add(ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
		add(ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));

		dropSelf(ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get());
		dropSelf(ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());

		dropSelf(ESBlocks.RED_CRYSTAL_MOSS_CARPET.get());
		dropSelf(ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get());

		dropSelf(ESBlocks.JINGLING_PICKLE.get());
		dropWhenSilkTouch(ESBlocks.DEAD_TENTACLES_CORAL.get());
		dropWhenSilkTouch(ESBlocks.TENTACLES_CORAL.get());
		dropWhenSilkTouch(ESBlocks.DEAD_TENTACLES_CORAL_FAN.get());
		otherWhenSilkTouch(ESBlocks.DEAD_TENTACLES_CORAL_WALL_FAN.get(), ESBlocks.DEAD_TENTACLES_CORAL_FAN.get());
		dropWhenSilkTouch(ESBlocks.TENTACLES_CORAL_FAN.get());
		otherWhenSilkTouch(ESBlocks.TENTACLES_CORAL_WALL_FAN.get(), ESBlocks.TENTACLES_CORAL_FAN.get());
		dropSelf(ESBlocks.DEAD_TENTACLES_CORAL_BLOCK.get());

		add(ESBlocks.TENTACLES_CORAL_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.DEAD_TENTACLES_CORAL_BLOCK.get()));
		dropWhenSilkTouch(ESBlocks.DEAD_GOLDEN_CORAL.get());
		dropWhenSilkTouch(ESBlocks.GOLDEN_CORAL.get());
		dropWhenSilkTouch(ESBlocks.DEAD_GOLDEN_CORAL_FAN.get());
		otherWhenSilkTouch(ESBlocks.DEAD_GOLDEN_CORAL_WALL_FAN.get(), ESBlocks.DEAD_GOLDEN_CORAL_FAN.get());
		dropWhenSilkTouch(ESBlocks.GOLDEN_CORAL_FAN.get());
		otherWhenSilkTouch(ESBlocks.GOLDEN_CORAL_WALL_FAN.get(), ESBlocks.GOLDEN_CORAL_FAN.get());
		dropSelf(ESBlocks.DEAD_GOLDEN_CORAL_BLOCK.get());
		add(ESBlocks.GOLDEN_CORAL_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.DEAD_GOLDEN_CORAL_BLOCK.get()));
		dropWhenSilkTouch(ESBlocks.DEAD_CRYSTALLUM_CORAL.get());

		dropWhenSilkTouch(ESBlocks.CRYSTALLUM_CORAL.get());
		dropWhenSilkTouch(ESBlocks.DEAD_CRYSTALLUM_CORAL_FAN.get());
		otherWhenSilkTouch(ESBlocks.DEAD_CRYSTALLUM_CORAL_WALL_FAN.get(), ESBlocks.DEAD_CRYSTALLUM_CORAL_FAN.get());
		dropWhenSilkTouch(ESBlocks.CRYSTALLUM_CORAL_FAN.get());
		otherWhenSilkTouch(ESBlocks.CRYSTALLUM_CORAL_WALL_FAN.get(), ESBlocks.CRYSTALLUM_CORAL_FAN.get());
		dropSelf(ESBlocks.DEAD_CRYSTALLUM_CORAL_BLOCK.get());
		add(ESBlocks.CRYSTALLUM_CORAL_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.DEAD_CRYSTALLUM_CORAL_BLOCK.get()));

		add(ESBlocks.LUNAR_LEAVES.get(), (block) -> this.createLunarLeavesDrops(block, ESBlocks.LUNAR_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
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

		add(ESBlocks.NORTHLAND_LEAVES.get(), (block) -> this.createLeavesDrops(block, ESBlocks.NORTHLAND_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
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

		add(ESBlocks.STARLIGHT_MANGROVE_LEAVES.get(), (block) -> this.createLeavesDrops(block, ESBlocks.STARLIGHT_MANGROVE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_LOG.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_WOOD.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_PLANKS.get());
		dropSelf(ESBlocks.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
		dropSelf(ESBlocks.STRIPPED_STARLIGHT_MANGROVE_WOOD.get());
		add(ESBlocks.STARLIGHT_MANGROVE_DOOR.get(), this::createDoorTable);
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_TRAPDOOR.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_PRESSURE_PLATE.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_BUTTON.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_FENCE.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_FENCE_GATE.get());
		add(ESBlocks.STARLIGHT_MANGROVE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_STAIRS.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_SIGN.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_WALL_SIGN.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_HANGING_SIGN.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_SAPLING.get());
		dropPottedContents(ESBlocks.POTTED_STARLIGHT_MANGROVE_SAPLING.get());
		dropSelf(ESBlocks.STARLIGHT_MANGROVE_ROOTS.get());
		dropSelf(ESBlocks.MUDDY_STARLIGHT_MANGROVE_ROOTS.get());

		add(ESBlocks.SCARLET_LEAVES.get(), (block) -> this.createLeavesDrops(block, ESBlocks.SCARLET_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ESBlocks.SCARLET_LEAVES_PILE.get(), noDrop());
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

		add(ESBlocks.TORREYA_LEAVES.get(), (block) -> this.createLeavesDrops(block, ESBlocks.TORREYA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
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
		add(ESBlocks.TORREYA_CAMPFIRE.get(), (block) -> createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(ESItems.RAW_AMARAMBER.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));

		add(ESBlocks.GRIMSTONE.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.COBBLED_GRIMSTONE.get()));
		dropSelf(ESBlocks.COBBLED_GRIMSTONE.get());
		add(ESBlocks.COBBLED_GRIMSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.COBBLED_GRIMSTONE_STAIRS.get());
		dropSelf(ESBlocks.COBBLED_GRIMSTONE_WALL.get());
		dropSelf(ESBlocks.GRIMSTONE_BRICKS.get());
		add(ESBlocks.GRIMSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.GRIMSTONE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.GRIMSTONE_BRICK_WALL.get());
		dropSelf(ESBlocks.POLISHED_GRIMSTONE.get());
		add(ESBlocks.POLISHED_GRIMSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_GRIMSTONE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_GRIMSTONE_WALL.get());
		dropSelf(ESBlocks.GRIMSTONE_TILES.get());
		add(ESBlocks.GRIMSTONE_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.GRIMSTONE_TILE_STAIRS.get());
		dropSelf(ESBlocks.GRIMSTONE_TILE_WALL.get());
		dropSelf(ESBlocks.CHISELED_GRIMSTONE.get());
		dropSelf(ESBlocks.GLOWING_GRIMSTONE.get());

		add(ESBlocks.VOIDSTONE.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.COBBLED_VOIDSTONE.get()));
		dropSelf(ESBlocks.COBBLED_VOIDSTONE.get());
		add(ESBlocks.COBBLED_VOIDSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.COBBLED_VOIDSTONE_STAIRS.get());
		dropSelf(ESBlocks.COBBLED_VOIDSTONE_WALL.get());
		dropSelf(ESBlocks.VOIDSTONE_BRICKS.get());
		add(ESBlocks.VOIDSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.VOIDSTONE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.VOIDSTONE_BRICK_WALL.get());
		dropSelf(ESBlocks.POLISHED_VOIDSTONE.get());
		add(ESBlocks.POLISHED_VOIDSTONE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_VOIDSTONE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_VOIDSTONE_WALL.get());
		dropSelf(ESBlocks.VOIDSTONE_TILES.get());
		add(ESBlocks.VOIDSTONE_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.VOIDSTONE_TILE_STAIRS.get());
		dropSelf(ESBlocks.VOIDSTONE_TILE_WALL.get());
		dropSelf(ESBlocks.CHISELED_VOIDSTONE.get());
		dropSelf(ESBlocks.GLOWING_VOIDSTONE.get());

		dropSelf(ESBlocks.ETERNAL_ICE.get());
		dropSelf(ESBlocks.ETERNAL_ICE_BRICKS.get());
		add(ESBlocks.ETERNAL_ICE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.ETERNAL_ICE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.ETERNAL_ICE_BRICK_WALL.get());

		dropSelf(ESBlocks.NEBULAITE.get());
		dropSelf(ESBlocks.NEBULAITE_BRICKS.get());
		add(ESBlocks.NEBULAITE_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.NEBULAITE_BRICK_STAIRS.get());
		dropSelf(ESBlocks.NEBULAITE_BRICK_WALL.get());
		dropSelf(ESBlocks.CHISELED_NEBULAITE_BRICKS.get());

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

		dropSelf(ESBlocks.THIOQUARTZ_BLOCK.get());
		add(ESBlocks.BUDDING_THIOQUARTZ.get(), noDrop());
		add(ESBlocks.THIOQUARTZ_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ESItems.THIOQUARTZ_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.THIOQUARTZ_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
		dropSelf(ESBlocks.TOXITE.get());
		add(ESBlocks.TOXITE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.TOXITE_STAIRS.get());
		dropSelf(ESBlocks.TOXITE_WALL.get());
		dropSelf(ESBlocks.POLISHED_TOXITE.get());
		add(ESBlocks.POLISHED_TOXITE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.POLISHED_TOXITE_STAIRS.get());
		dropSelf(ESBlocks.POLISHED_TOXITE_WALL.get());

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

		add(ESBlocks.DUSTED_GRAVEL.get(), (block) -> createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(ESItems.DUSTED_SHARD.get()).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(block)))));
		dropSelf(ESBlocks.DUSTED_BRICKS.get());
		add(ESBlocks.DUSTED_BRICK_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.DUSTED_BRICK_STAIRS.get());
		dropSelf(ESBlocks.DUSTED_BRICK_WALL.get());

		dropSelf(ESBlocks.GOLEM_STEEL_BLOCK.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get());
		add(ESBlocks.GOLEM_STEEL_SLAB.get(), this::createSlabItemTable);
		add(ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.GOLEM_STEEL_STAIRS.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get());
		dropSelf(ESBlocks.GOLEM_STEEL_TILES.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get());
		add(ESBlocks.GOLEM_STEEL_TILE_SLAB.get(), this::createSlabItemTable);
		add(ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.GOLEM_STEEL_TILE_STAIRS.get());
		dropSelf(ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get());
		dropSelf(ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get());
		dropSelf(ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get());

		dropSelf(ESBlocks.SHADEGRIEVE.get());
		dropSelf(ESBlocks.BLOOMING_SHADEGRIEVE.get());
		dropSelf(ESBlocks.LUNAR_VINE.get());
		dropSelf(ESBlocks.LUNAR_MOSAIC.get());
		add(ESBlocks.LUNAR_MOSAIC_SLAB.get(), this::createSlabItemTable);
		dropSelf(ESBlocks.LUNAR_MOSAIC_STAIRS.get());
		dropSelf(ESBlocks.LUNAR_MOSAIC_FENCE.get());
		dropSelf(ESBlocks.LUNAR_MOSAIC_FENCE_GATE.get());
		dropSelf(ESBlocks.LUNAR_MAT.get());

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

		dropSelf(ESBlocks.STARLIGHT_FLOWER.get());
		dropPottedContents(ESBlocks.POTTED_STARLIGHT_FLOWER.get());
		dropSelf(ESBlocks.CONEBLOOM.get());
		dropPottedContents(ESBlocks.POTTED_CONEBLOOM.get());
		dropSelf(ESBlocks.NIGHTFAN.get());
		dropPottedContents(ESBlocks.POTTED_NIGHTFAN.get());
		dropSelf(ESBlocks.PINK_ROSE.get());
		dropPottedContents(ESBlocks.POTTED_PINK_ROSE.get());
		dropSelf(ESBlocks.STARLIGHT_TORCHFLOWER.get());
		dropPottedContents(ESBlocks.POTTED_STARLIGHT_TORCHFLOWER.get());
		add(ESBlocks.NIGHTFAN_BUSH.get(), this::createDoublePlantShearsDrop);
		add(ESBlocks.PINK_ROSE_BUSH.get(), this::createDoublePlantShearsDrop);
		dropSelf(ESBlocks.NIGHT_SPROUTS.get());
		dropSelf(ESBlocks.SMALL_NIGHT_SPROUTS.get());
		dropSelf(ESBlocks.GLOWING_NIGHT_SPROUTS.get());
		dropSelf(ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS.get());
		dropSelf(ESBlocks.LUNAR_GRASS.get());
		dropSelf(ESBlocks.GLOWING_LUNAR_GRASS.get());
		dropSelf(ESBlocks.CRESCENT_GRASS.get());
		dropPottedContents(ESBlocks.POTTED_CRESCENT_GRASS.get());
		dropSelf(ESBlocks.GLOWING_CRESCENT_GRASS.get());
		dropPottedContents(ESBlocks.POTTED_GLOWING_CRESCENT_GRASS.get());
		dropSelf(ESBlocks.PARASOL_GRASS.get());
		dropPottedContents(ESBlocks.POTTED_PARASOL_GRASS.get());
		dropSelf(ESBlocks.GLOWING_PARASOL_GRASS.get());
		dropPottedContents(ESBlocks.POTTED_GLOWING_PARASOL_GRASS.get());
		dropSelf(ESBlocks.LUNAR_BUSH.get());
		dropSelf(ESBlocks.GLOWING_LUNAR_BUSH.get());
		add(ESBlocks.TALL_CRESCENT_GRASS.get(), this::createDoublePlantShearsDrop);
		add(ESBlocks.TALL_GLOWING_CRESCENT_GRASS.get(), this::createDoublePlantShearsDrop);
		add(ESBlocks.LUNAR_REED.get(), this::createDoublePlantShearsDrop);
		dropSelf(ESBlocks.WHISPERBLOOM.get());
		dropPottedContents(ESBlocks.POTTED_WHISPERBLOOM.get());
		dropSelf(ESBlocks.GLADESPIKE.get());
		dropPottedContents(ESBlocks.POTTED_GLADESPIKE.get());
		dropSelf(ESBlocks.VIVIDSTALK.get());
		dropPottedContents(ESBlocks.POTTED_VIVIDSTALK.get());
		add(ESBlocks.TALL_GLADESPIKE.get(), this::createDoublePlantShearsDrop);
		dropSelf(ESBlocks.GLOWING_MUSHROOM.get());
		dropPottedContents(ESBlocks.POTTED_GLOWING_MUSHROOM.get());
		add(ESBlocks.GLOWING_MUSHROOM_BLOCK.get(), (block -> createMushroomBlockDrop(block, ESBlocks.GLOWING_MUSHROOM.get())));
		dropWhenSilkTouch(ESBlocks.GLOWING_MUSHROOM_STEM.get());

		dropSelf(ESBlocks.SWAMP_ROSE.get());
		dropPottedContents(ESBlocks.POTTED_SWAMP_ROSE.get());
		dropSelf(ESBlocks.FANTABUD.get());
		dropSelf(ESBlocks.GREEN_FANTABUD.get());
		dropSelf(ESBlocks.FANTAFERN.get());
		dropPottedContents(ESBlocks.POTTED_FANTAFERN.get());
		dropSelf(ESBlocks.GREEN_FANTAFERN.get());
		dropPottedContents(ESBlocks.POTTED_GREEN_FANTAFERN.get());
		dropSelf(ESBlocks.FANTAGRASS.get());
		dropSelf(ESBlocks.GREEN_FANTAGRASS.get());

		dropSelf(ESBlocks.ORANGE_SCARLET_BUD.get());
		dropSelf(ESBlocks.PURPLE_SCARLET_BUD.get());
		dropSelf(ESBlocks.RED_SCARLET_BUD.get());
		dropSelf(ESBlocks.SCARLET_GRASS.get());

		dropSelf(ESBlocks.WITHERED_STARLIGHT_FLOWER.get());
		dropPottedContents(ESBlocks.POTTED_WITHERED_STARLIGHT_FLOWER.get());

		dropSelf(ESBlocks.DEAD_LUNAR_BUSH.get());
		dropPottedContents(ESBlocks.POTTED_DEAD_LUNAR_BUSH.get());
		dropSelf(ESBlocks.DESERT_AMETHYSIA.get());
		dropPottedContents(ESBlocks.POTTED_DESERT_AMETHYSIA.get());
		dropSelf(ESBlocks.WITHERED_DESERT_AMETHYSIA.get());
		dropPottedContents(ESBlocks.POTTED_WITHERED_DESERT_AMETHYSIA.get());
		dropSelf(ESBlocks.SUNSET_THORNBLOOM.get());
		dropPottedContents(ESBlocks.POTTED_SUNSET_THORNBLOOM.get());
		add(ESBlocks.LUNARIS_CACTUS.get(), this::createLunarisCactusDrop);
		dropSelf(ESBlocks.LUNARIS_CACTUS_GEL_BLOCK.get());

		dropSelf(ESBlocks.MOONLIGHT_LILY_PAD.get());
		dropSelf(ESBlocks.STARLIT_LILY_PAD.get());
		dropSelf(ESBlocks.MOONLIGHT_DUCKWEED.get());

		dropSelf(ESBlocks.CRYSTALLIZED_LUNAR_GRASS.get());
		dropSelf(ESBlocks.RED_CRYSTAL_ROOTS.get());
		dropSelf(ESBlocks.BLUE_CRYSTAL_ROOTS.get());
		add(ESBlocks.TWILVEWRYM_HERB.get(), this::createDoublePlantShearsDrop);
		add(ESBlocks.STELLAFLY_BUSH.get(), this::createDoublePlantShearsDrop);
		add(ESBlocks.GLIMMERFLY_BUSH.get(), this::createDoublePlantShearsDrop);

		add(ESBlocks.NIGHTFALL_GRASS_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.NIGHTFALL_DIRT.get()));
		add(ESBlocks.FANTASY_GRASS_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, ESBlocks.NIGHTFALL_MUD.get()));
		dropSelf(ESBlocks.FANTASY_GRASS_CARPET.get());
		dropSelf(ESBlocks.NIGHTFALL_DIRT.get());

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

		dropSelf(ESBlocks.RAW_AETHERSENT_BLOCK.get());
		dropSelf(ESBlocks.AETHERSENT_BLOCK.get());
		dropSelf(ESBlocks.SPRINGSTONE.get());
		dropSelf(ESBlocks.THERMAL_SPRINGSTONE.get());
		add(ESBlocks.GLACITE.get(), (block) -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.GLACITE_SHARD.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.SWAMP_SILVER_ORE.get(), (block) -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.SWAMP_SILVER_NUGGET.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		dropSelf(ESBlocks.SWAMP_SILVER_BLOCK.get());
		add(ESBlocks.GRIMSTONE_REDSTONE_ORE.get(), (block) -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.VOIDSTONE_REDSTONE_ORE.get(), (block) -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.GRIMSTONE_SALTPETER_ORE.get(), (block) -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.SALTPETER_POWDER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		add(ESBlocks.VOIDSTONE_SALTPETER_ORE.get(), (block) -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ESItems.SALTPETER_POWDER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
		dropSelf(ESBlocks.SALTPETER_BLOCK.get());

		dropSelf(ESBlocks.AMARAMBER_LANTERN.get());
		add(ESBlocks.AMARAMBER_CANDLE.get(), this::createCandleDrops);

		dropOther(ESBlocks.NIGHTFALL_FARMLAND.get(), ESBlocks.NIGHTFALL_DIRT.get());

		dropSelf(ESBlocks.STELLAR_RACK.get());

		// nothing
		add(ESBlocks.ENCHANTED_GRIMSTONE_BRICKS.get(), noDrop());
		add(ESBlocks.ENERGY_BLOCK.get(), noDrop());
		add(ESBlocks.THE_GATEKEEPER_SPAWNER.get(), noDrop());
		add(ESBlocks.STARLIGHT_GOLEM_SPAWNER.get(), noDrop());
		add(ESBlocks.TANGLED_HATRED_SPAWNER.get(), noDrop());
		add(ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get(), noDrop());
		add(ESBlocks.STARLIGHT_PORTAL.get(), noDrop());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return BuiltInRegistries.BLOCK.stream().filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(EternalStarlight.ID)).collect(Collectors.toList());
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
		return this.createLeavesDrops(leaves, sapling, saplingChances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(this.doesNotHaveShearsOrSilkTouch()).add(((LootPoolSingletonContainer.Builder<?>) this.applyExplosionCondition(leaves, LootItem.lootTableItem(ESItems.LUNAR_BERRIES.get()))).when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
	}

	private LootItemCondition.Builder hasShearsOrSilkTouch() {
		return HAS_SHEARS.or(this.hasSilkTouch());
	}

	private LootItemCondition.Builder doesNotHaveShearsOrSilkTouch() {
		return this.hasShearsOrSilkTouch().invert();
	}
}
