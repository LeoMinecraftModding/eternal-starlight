package cn.leolezury.eternalstarlight.forge.datagen.provider.sub;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AbyssalKelp;
import cn.leolezury.eternalstarlight.common.block.BerriesVines;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
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
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    public ESBlockLootSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        add(BlockInit.BERRIES_VINES.get(), this::createBerriesVinesDrop);
        add(BlockInit.BERRIES_VINES_PLANT.get(), this::createBerriesVinesDrop);
        dropSelf(BlockInit.CAVE_MOSS.get());
        dropOther(BlockInit.CAVE_MOSS_PLANT.get(), BlockInit.CAVE_MOSS.get());
        add(BlockInit.ABYSSAL_KELP.get(), this::createAbyssalKelpDrop);
        add(BlockInit.ABYSSAL_KELP_PLANT.get(), this::createAbyssalKelpDrop);

        add(BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ItemInit.RED_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ItemInit.RED_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        add(BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ItemInit.BLUE_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ItemInit.BLUE_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));

        dropSelf(BlockInit.RED_STARLIGHT_CRYSTAL_BLOCK.get());
        dropSelf(BlockInit.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());

        dropSelf(BlockInit.RED_CRYSTAL_MOSS_CARPET.get());
        dropSelf(BlockInit.BLUE_CRYSTAL_MOSS_CARPET.get());

        dropWhenSilkTouch(BlockInit.DEAD_TENTACLES_CORAL.get());
        dropWhenSilkTouch(BlockInit.TENTACLES_CORAL.get());
        dropWhenSilkTouch(BlockInit.DEAD_TENTACLES_CORAL_FAN.get());
        otherWhenSilkTouch(BlockInit.DEAD_TENTACLES_CORAL_WALL_FAN.get(), BlockInit.DEAD_TENTACLES_CORAL_FAN.get());
        dropWhenSilkTouch(BlockInit.TENTACLES_CORAL_FAN.get());
        otherWhenSilkTouch(BlockInit.TENTACLES_CORAL_WALL_FAN.get(), BlockInit.TENTACLES_CORAL_FAN.get());
        dropSelf(BlockInit.DEAD_TENTACLES_CORAL_BLOCK.get());

        add(BlockInit.TENTACLES_CORAL_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, BlockInit.DEAD_TENTACLES_CORAL_BLOCK.get()));dropWhenSilkTouch(BlockInit.DEAD_GOLDEN_CORAL.get());
        dropWhenSilkTouch(BlockInit.GOLDEN_CORAL.get());
        dropWhenSilkTouch(BlockInit.DEAD_GOLDEN_CORAL_FAN.get());
        otherWhenSilkTouch(BlockInit.DEAD_GOLDEN_CORAL_WALL_FAN.get(), BlockInit.DEAD_GOLDEN_CORAL_FAN.get());
        dropWhenSilkTouch(BlockInit.GOLDEN_CORAL_FAN.get());
        otherWhenSilkTouch(BlockInit.GOLDEN_CORAL_WALL_FAN.get(), BlockInit.GOLDEN_CORAL_FAN.get());
        dropSelf(BlockInit.DEAD_GOLDEN_CORAL_BLOCK.get());
        add(BlockInit.GOLDEN_CORAL_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, BlockInit.DEAD_GOLDEN_CORAL_BLOCK.get()));dropWhenSilkTouch(BlockInit.DEAD_CRYSTALLUM_CORAL.get());

        dropWhenSilkTouch(BlockInit.CRYSTALLUM_CORAL.get());
        dropWhenSilkTouch(BlockInit.DEAD_CRYSTALLUM_CORAL_FAN.get());
        otherWhenSilkTouch(BlockInit.DEAD_CRYSTALLUM_CORAL_WALL_FAN.get(), BlockInit.DEAD_CRYSTALLUM_CORAL_FAN.get());
        dropWhenSilkTouch(BlockInit.CRYSTALLUM_CORAL_FAN.get());
        otherWhenSilkTouch(BlockInit.CRYSTALLUM_CORAL_WALL_FAN.get(), BlockInit.CRYSTALLUM_CORAL_FAN.get());
        dropSelf(BlockInit.DEAD_CRYSTALLUM_CORAL_BLOCK.get());
        add(BlockInit.CRYSTALLUM_CORAL_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, BlockInit.DEAD_CRYSTALLUM_CORAL_BLOCK.get()));

        add(BlockInit.LUNAR_LEAVES.get(), (block) -> this.createLunarLeavesDrops(block, BlockInit.LUNAR_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(BlockInit.LUNAR_LOG.get());
        dropSelf(BlockInit.LUNAR_WOOD.get());
        dropSelf(BlockInit.LUNAR_PLANKS.get());
        dropSelf(BlockInit.STRIPPED_LUNAR_LOG.get());
        dropSelf(BlockInit.STRIPPED_LUNAR_WOOD.get());
        add(BlockInit.LUNAR_DOOR.get(), this::createDoorTable);
        dropSelf(BlockInit.LUNAR_TRAPDOOR.get());
        dropSelf(BlockInit.LUNAR_PRESSURE_PLATE.get());
        dropSelf(BlockInit.LUNAR_BUTTON.get());
        dropSelf(BlockInit.LUNAR_FENCE.get());
        dropSelf(BlockInit.LUNAR_FENCE_GATE.get());
        add(BlockInit.LUNAR_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.LUNAR_STAIRS.get());
        dropSelf(BlockInit.LUNAR_SIGN.get());
        dropSelf(BlockInit.LUNAR_WALL_SIGN.get());
        dropSelf(BlockInit.LUNAR_HANGING_SIGN.get());
        dropSelf(BlockInit.LUNAR_WALL_HANGING_SIGN.get());
        dropSelf(BlockInit.LUNAR_SAPLING.get());
        dropPottedContents(BlockInit.POTTED_LUNAR_SAPLING.get());

        dropSelf(BlockInit.DEAD_LUNAR_LOG.get());
        dropSelf(BlockInit.RED_CRYSTALLIZED_LUNAR_LOG.get());
        dropSelf(BlockInit.BLUE_CRYSTALLIZED_LUNAR_LOG.get());

        add(BlockInit.NORTHLAND_LEAVES.get(), (block) -> this.createLeavesDrops(block, BlockInit.NORTHLAND_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(BlockInit.NORTHLAND_LOG.get());
        dropSelf(BlockInit.NORTHLAND_WOOD.get());
        dropSelf(BlockInit.NORTHLAND_PLANKS.get());
        dropSelf(BlockInit.STRIPPED_NORTHLAND_LOG.get());
        dropSelf(BlockInit.STRIPPED_NORTHLAND_WOOD.get());
        add(BlockInit.NORTHLAND_DOOR.get(), this::createDoorTable);
        dropSelf(BlockInit.NORTHLAND_TRAPDOOR.get());
        dropSelf(BlockInit.NORTHLAND_PRESSURE_PLATE.get());
        dropSelf(BlockInit.NORTHLAND_BUTTON.get());
        dropSelf(BlockInit.NORTHLAND_FENCE.get());
        dropSelf(BlockInit.NORTHLAND_FENCE_GATE.get());
        add(BlockInit.NORTHLAND_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.NORTHLAND_STAIRS.get());
        dropSelf(BlockInit.NORTHLAND_SIGN.get());
        dropSelf(BlockInit.NORTHLAND_WALL_SIGN.get());
        dropSelf(BlockInit.NORTHLAND_HANGING_SIGN.get());
        dropSelf(BlockInit.NORTHLAND_WALL_HANGING_SIGN.get());
        dropSelf(BlockInit.NORTHLAND_SAPLING.get());
        dropPottedContents(BlockInit.POTTED_NORTHLAND_SAPLING.get());

        add(BlockInit.STARLIGHT_MANGROVE_LEAVES.get(), (block) -> this.createLeavesDrops(block, BlockInit.STARLIGHT_MANGROVE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(BlockInit.STARLIGHT_MANGROVE_LOG.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_WOOD.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        dropSelf(BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
        dropSelf(BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get());
        add(BlockInit.STARLIGHT_MANGROVE_DOOR.get(), this::createDoorTable);
        dropSelf(BlockInit.STARLIGHT_MANGROVE_TRAPDOOR.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_BUTTON.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_FENCE.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_FENCE_GATE.get());
        add(BlockInit.STARLIGHT_MANGROVE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.STARLIGHT_MANGROVE_STAIRS.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_SIGN.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_WALL_SIGN.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_HANGING_SIGN.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_SAPLING.get());
        dropPottedContents(BlockInit.POTTED_STARLIGHT_MANGROVE_SAPLING.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_ROOTS.get());
        dropSelf(BlockInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get());

        add(BlockInit.SCARLET_LEAVES.get(), (block) -> this.createLeavesDrops(block, BlockInit.SCARLET_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        add(BlockInit.SCARLET_LEAVES_PILE.get(), noDrop());
        dropSelf(BlockInit.SCARLET_LOG.get());
        dropSelf(BlockInit.SCARLET_WOOD.get());
        dropSelf(BlockInit.SCARLET_PLANKS.get());
        dropSelf(BlockInit.STRIPPED_SCARLET_LOG.get());
        dropSelf(BlockInit.STRIPPED_SCARLET_WOOD.get());
        add(BlockInit.SCARLET_DOOR.get(), this::createDoorTable);
        dropSelf(BlockInit.SCARLET_TRAPDOOR.get());
        dropSelf(BlockInit.SCARLET_PRESSURE_PLATE.get());
        dropSelf(BlockInit.SCARLET_BUTTON.get());
        dropSelf(BlockInit.SCARLET_FENCE.get());
        dropSelf(BlockInit.SCARLET_FENCE_GATE.get());
        add(BlockInit.SCARLET_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.SCARLET_STAIRS.get());
        dropSelf(BlockInit.SCARLET_SIGN.get());
        dropSelf(BlockInit.SCARLET_WALL_SIGN.get());
        dropSelf(BlockInit.SCARLET_HANGING_SIGN.get());
        dropSelf(BlockInit.SCARLET_WALL_HANGING_SIGN.get());
        dropSelf(BlockInit.SCARLET_SAPLING.get());
        dropPottedContents(BlockInit.POTTED_SCARLET_SAPLING.get());

        add(BlockInit.GRIMSTONE.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, BlockInit.COBBLED_GRIMSTONE.get()));
        dropSelf(BlockInit.COBBLED_GRIMSTONE.get());
        add(BlockInit.COBBLED_GRIMSTONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.COBBLED_GRIMSTONE_STAIRS.get());
        dropSelf(BlockInit.COBBLED_GRIMSTONE_WALL.get());
        dropSelf(BlockInit.GRIMSTONE_BRICKS.get());
        add(BlockInit.GRIMSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.GRIMSTONE_BRICK_STAIRS.get());
        dropSelf(BlockInit.GRIMSTONE_BRICK_WALL.get());
        dropSelf(BlockInit.POLISHED_GRIMSTONE.get());
        add(BlockInit.POLISHED_GRIMSTONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_GRIMSTONE_STAIRS.get());
        dropSelf(BlockInit.POLISHED_GRIMSTONE_WALL.get());
        dropSelf(BlockInit.GRIMSTONE_TILES.get());
        add(BlockInit.GRIMSTONE_TILE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.GRIMSTONE_TILE_STAIRS.get());
        dropSelf(BlockInit.GRIMSTONE_TILE_WALL.get());
        dropSelf(BlockInit.CHISELED_GRIMSTONE.get());
        dropSelf(BlockInit.GLOWING_GRIMSTONE.get());

        add(BlockInit.VOIDSTONE.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, BlockInit.COBBLED_VOIDSTONE.get()));
        dropSelf(BlockInit.COBBLED_VOIDSTONE.get());
        add(BlockInit.COBBLED_VOIDSTONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.COBBLED_VOIDSTONE_STAIRS.get());
        dropSelf(BlockInit.COBBLED_VOIDSTONE_WALL.get());
        dropSelf(BlockInit.VOIDSTONE_BRICKS.get());
        add(BlockInit.VOIDSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.VOIDSTONE_BRICK_STAIRS.get());
        dropSelf(BlockInit.VOIDSTONE_BRICK_WALL.get());
        dropSelf(BlockInit.POLISHED_VOIDSTONE.get());
        add(BlockInit.POLISHED_VOIDSTONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_VOIDSTONE_STAIRS.get());
        dropSelf(BlockInit.POLISHED_VOIDSTONE_WALL.get());
        dropSelf(BlockInit.VOIDSTONE_TILES.get());
        add(BlockInit.VOIDSTONE_TILE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.VOIDSTONE_TILE_STAIRS.get());
        dropSelf(BlockInit.VOIDSTONE_TILE_WALL.get());
        dropSelf(BlockInit.CHISELED_VOIDSTONE.get());
        dropSelf(BlockInit.GLOWING_VOIDSTONE.get());

        dropSelf(BlockInit.ABYSSLATE.get());
        dropSelf(BlockInit.POLISHED_ABYSSLATE.get());
        add(BlockInit.POLISHED_ABYSSLATE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_ABYSSLATE_STAIRS.get());
        dropSelf(BlockInit.POLISHED_ABYSSLATE_WALL.get());
        dropSelf(BlockInit.POLISHED_ABYSSLATE_BRICKS.get());
        add(BlockInit.POLISHED_ABYSSLATE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_ABYSSLATE_BRICK_STAIRS.get());
        dropSelf(BlockInit.POLISHED_ABYSSLATE_BRICK_WALL.get());
        dropSelf(BlockInit.CHISELED_POLISHED_ABYSSLATE.get());
        dropSelf(BlockInit.ABYSSAL_MAGMA_BLOCK.get());
        dropOther(BlockInit.ABYSSAL_GEYSER.get(), ItemInit.ABYSSLATE.get());

        dropSelf(BlockInit.THERMABYSSLATE.get());
        dropSelf(BlockInit.POLISHED_THERMABYSSLATE.get());
        add(BlockInit.POLISHED_THERMABYSSLATE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_THERMABYSSLATE_STAIRS.get());
        dropSelf(BlockInit.POLISHED_THERMABYSSLATE_WALL.get());
        dropSelf(BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get());
        add(BlockInit.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get());
        dropSelf(BlockInit.POLISHED_THERMABYSSLATE_BRICK_WALL.get());
        dropSelf(BlockInit.CHISELED_POLISHED_THERMABYSSLATE.get());
        dropSelf(BlockInit.THERMABYSSAL_MAGMA_BLOCK.get());
        dropOther(BlockInit.THERMABYSSAL_GEYSER.get(), ItemInit.THERMABYSSLATE.get());

        dropSelf(BlockInit.CRYOBYSSLATE.get());
        dropSelf(BlockInit.POLISHED_CRYOBYSSLATE.get());
        add(BlockInit.POLISHED_CRYOBYSSLATE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_CRYOBYSSLATE_STAIRS.get());
        dropSelf(BlockInit.POLISHED_CRYOBYSSLATE_WALL.get());
        dropSelf(BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get());
        add(BlockInit.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get());
        dropSelf(BlockInit.POLISHED_CRYOBYSSLATE_BRICK_WALL.get());
        dropSelf(BlockInit.CHISELED_POLISHED_CRYOBYSSLATE.get());
        dropSelf(BlockInit.CRYOBYSSAL_MAGMA_BLOCK.get());
        dropOther(BlockInit.CRYOBYSSAL_GEYSER.get(), ItemInit.CRYOBYSSLATE.get());

        dropSelf(BlockInit.NIGHTSHADE_MUD.get());
        dropSelf(BlockInit.GLOWING_NIGHTSHADE_MUD.get());
        dropSelf(BlockInit.PACKED_NIGHTSHADE_MUD.get());
        dropSelf(BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        add(BlockInit.NIGHTSHADE_MUD_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS.get());
        dropSelf(BlockInit.NIGHTSHADE_MUD_BRICK_WALL.get());

        dropSelf(BlockInit.TWILIGHT_SAND.get());
        dropSelf(BlockInit.TWILIGHT_SANDSTONE.get());
        add(BlockInit.TWILIGHT_SANDSTONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.TWILIGHT_SANDSTONE_STAIRS.get());
        dropSelf(BlockInit.TWILIGHT_SANDSTONE_WALL.get());
        dropSelf(BlockInit.CUT_TWILIGHT_SANDSTONE.get());
        add(BlockInit.CUT_TWILIGHT_SANDSTONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.CUT_TWILIGHT_SANDSTONE_STAIRS.get());
        dropSelf(BlockInit.CUT_TWILIGHT_SANDSTONE_WALL.get());
        dropSelf(BlockInit.CHISELED_TWILIGHT_SANDSTONE.get());

        dropSelf(BlockInit.GOLEM_STEEL_BLOCK.get());
        dropSelf(BlockInit.OXIDIZED_GOLEM_STEEL_BLOCK.get());
        add(BlockInit.GOLEM_STEEL_SLAB.get(), this::createSlabItemTable);
        add(BlockInit.OXIDIZED_GOLEM_STEEL_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.GOLEM_STEEL_STAIRS.get());
        dropSelf(BlockInit.OXIDIZED_GOLEM_STEEL_STAIRS.get());
        dropSelf(BlockInit.GOLEM_STEEL_TILES.get());
        dropSelf(BlockInit.OXIDIZED_GOLEM_STEEL_TILES.get());
        add(BlockInit.GOLEM_STEEL_TILE_SLAB.get(), this::createSlabItemTable);
        add(BlockInit.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.GOLEM_STEEL_TILE_STAIRS.get());
        dropSelf(BlockInit.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get());
        dropSelf(BlockInit.CHISELED_GOLEM_STEEL_BLOCK.get());
        dropSelf(BlockInit.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get());

        dropSelf(BlockInit.LUNAR_MOSAIC.get());
        add(BlockInit.LUNAR_MOSAIC_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.LUNAR_MOSAIC_STAIRS.get());
        dropSelf(BlockInit.LUNAR_MOSAIC_FENCE.get());
        dropSelf(BlockInit.LUNAR_MOSAIC_FENCE_GATE.get());
        dropSelf(BlockInit.LUNAR_MAT.get());

        dropSelf(BlockInit.DOOMED_TORCH.get());
        dropOther(BlockInit.WALL_DOOMED_TORCH.get(), ItemInit.DOOMED_TORCH.get());
        dropSelf(BlockInit.DOOMED_REDSTONE_TORCH.get());
        dropOther(BlockInit.WALL_DOOMED_REDSTONE_TORCH.get(), ItemInit.DOOMED_REDSTONE_TORCH.get());
        dropSelf(BlockInit.DOOMEDEN_BRICKS.get());
        add(BlockInit.DOOMEDEN_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.DOOMEDEN_BRICK_STAIRS.get());
        dropSelf(BlockInit.DOOMEDEN_BRICK_WALL.get());
        dropSelf(BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        add(BlockInit.POLISHED_DOOMEDEN_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get());
        dropSelf(BlockInit.POLISHED_DOOMEDEN_BRICK_WALL.get());
        dropSelf(BlockInit.DOOMEDEN_TILES.get());
        add(BlockInit.DOOMEDEN_TILE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.DOOMEDEN_TILE_STAIRS.get());
        dropSelf(BlockInit.DOOMEDEN_TILE_WALL.get());
        dropSelf(BlockInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        dropSelf(BlockInit.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        dropSelf(BlockInit.DOOMEDEN_LIGHT.get());
        dropSelf(BlockInit.DOOMEDEN_KEYHOLE.get());
        dropSelf(BlockInit.REDSTONE_DOOMEDEN_KEYHOLE.get());

        dropSelf(BlockInit.STARLIGHT_FLOWER.get());
        dropPottedContents(BlockInit.POTTED_STARLIGHT_FLOWER.get());
        dropSelf(BlockInit.CONEBLOOM.get());
        dropPottedContents(BlockInit.POTTED_CONEBLOOM.get());
        dropSelf(BlockInit.NIGHTFAN.get());
        dropPottedContents(BlockInit.POTTED_NIGHTFAN.get());
        dropSelf(BlockInit.PINK_ROSE.get());
        dropPottedContents(BlockInit.POTTED_PINK_ROSE.get());
        dropSelf(BlockInit.STARLIGHT_TORCHFLOWER.get());
        dropPottedContents(BlockInit.POTTED_STARLIGHT_TORCHFLOWER.get());
        add(BlockInit.NIGHTFAN_BUSH.get(), BlockLootSubProvider::createDoublePlantShearsDrop);
        add(BlockInit.PINK_ROSE_BUSH.get(), BlockLootSubProvider::createDoublePlantShearsDrop);
        dropSelf(BlockInit.NIGHT_SPROUTS.get());
        dropSelf(BlockInit.SMALL_NIGHT_SPROUTS.get());
        dropSelf(BlockInit.GLOWING_NIGHT_SPROUTS.get());
        dropSelf(BlockInit.SMALL_GLOWING_NIGHT_SPROUTS.get());
        dropSelf(BlockInit.LUNAR_GRASS.get());
        dropSelf(BlockInit.GLOWING_LUNAR_GRASS.get());
        dropSelf(BlockInit.CRESCENT_GRASS.get());
        dropSelf(BlockInit.GLOWING_CRESCENT_GRASS.get());
        dropSelf(BlockInit.PARASOL_GRASS.get());
        dropSelf(BlockInit.GLOWING_PARASOL_GRASS.get());
        dropSelf(BlockInit.LUNAR_BUSH.get());
        dropSelf(BlockInit.GLOWING_LUNAR_BUSH.get());
        add(BlockInit.TALL_CRESCENT_GRASS.get(), BlockLootSubProvider::createDoublePlantShearsDrop);
        add(BlockInit.TALL_GLOWING_CRESCENT_GRASS.get(), BlockLootSubProvider::createDoublePlantShearsDrop);
        add(BlockInit.LUNAR_REED.get(), BlockLootSubProvider::createDoublePlantShearsDrop);
        dropSelf(BlockInit.WHISPERBLOOM.get());
        dropSelf(BlockInit.GLADESPIKE.get());
        dropSelf(BlockInit.VIVIDSTALK.get());
        add(BlockInit.TALL_GLADESPIKE.get(), BlockLootSubProvider::createDoublePlantShearsDrop);
        dropSelf(BlockInit.GLOWING_MUSHROOM.get());
        add(BlockInit.GLOWING_MUSHROOM_BLOCK.get(), (block -> createMushroomBlockDrop(block, BlockInit.GLOWING_MUSHROOM.get())));

        dropSelf(BlockInit.SWAMP_ROSE.get());
        dropPottedContents(BlockInit.POTTED_SWAMP_ROSE.get());
        dropSelf(BlockInit.FANTABUD.get());
        dropSelf(BlockInit.GREEN_FANTABUD.get());
        dropSelf(BlockInit.FANTAFERN.get());
        dropSelf(BlockInit.GREEN_FANTAFERN.get());
        dropSelf(BlockInit.FANTAGRASS.get());
        dropSelf(BlockInit.GREEN_FANTAGRASS.get());

        dropSelf(BlockInit.ORANGE_SCARLET_BUD.get());
        dropSelf(BlockInit.PURPLE_SCARLET_BUD.get());
        dropSelf(BlockInit.RED_SCARLET_BUD.get());
        dropSelf(BlockInit.SCARLET_GRASS.get());

        dropSelf(BlockInit.DEAD_LUNAR_BUSH.get());

        add(BlockInit.NIGHTSHADE_GRASS_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, BlockInit.NIGHTSHADE_DIRT.get()));
        add(BlockInit.FANTASY_GRASS_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, BlockInit.NIGHTSHADE_MUD.get()));
        dropSelf(BlockInit.NIGHTSHADE_DIRT.get());

        dropSelf(BlockInit.AETHERSENT_BLOCK.get());
        dropSelf(BlockInit.SPRINGSTONE.get());
        dropSelf(BlockInit.THERMAL_SPRINGSTONE.get());
        add(BlockInit.SWAMP_SILVER_ORE.get(), (block) -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ItemInit.SWAMP_SILVER_NUGGET.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        dropSelf(BlockInit.SWAMP_SILVER_BLOCK.get());

        // nothing
        add(BlockInit.ENERGY_BLOCK.get(), noDrop());
        add(BlockInit.STARLIGHT_GOLEM_SPAWNER.get(), noDrop());
        add(BlockInit.LUNAR_MONSTROSITY_SPAWNER.get(), noDrop());
        add(BlockInit.STARLIGHT_PORTAL.get(), noDrop());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream().filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(EternalStarlight.MOD_ID)).collect(Collectors.toList());
    }

    private LootTable.Builder createBerriesVinesDrop(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemInit.LUNAR_BERRIES.get())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BerriesVines.BERRIES, true))));
    }

    private LootTable.Builder createAbyssalKelpDrop(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemInit.ABYSSAL_FRUIT.get())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbyssalKelp.BERRIES, true))));
    }

    private LootTable.Builder createLunarLeavesDrops(Block leaves, Block sapling, float... saplingChances) {
        return this.createLeavesDrops(leaves, sapling, saplingChances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(((LootPoolSingletonContainer.Builder<?>)this.applyExplosionCondition(leaves, LootItem.lootTableItem(ItemInit.LUNAR_BERRIES.get()))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }
}
