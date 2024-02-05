package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.TorreyaVinesPlantBlock;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ESBlockStateProvider extends BlockStateProvider {
    // Render Types
    private static final ResourceLocation SOLID = new ResourceLocation("solid");
    private static final ResourceLocation CUTOUT = new ResourceLocation("cutout");
    private static final ResourceLocation CUTOUT_MIPPED = new ResourceLocation("cutout_mipped");
    private static final ResourceLocation TRANSLUCENT = new ResourceLocation("translucent");

    public ESBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EternalStarlight.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        vinesWithFruit(BlockInit.BERRIES_VINES.get());
        vinesWithFruit(BlockInit.BERRIES_VINES_PLANT.get());
        tintedCross(BlockInit.CAVE_MOSS.get());
        tintedCross(BlockInit.CAVE_MOSS_PLANT.get(), blockTexture(BlockInit.CAVE_MOSS.get()), CUTOUT);
        vinesWithFruit(BlockInit.ABYSSAL_KELP.get());
        vinesWithFruit(BlockInit.ABYSSAL_KELP_PLANT.get());
        crystalCluster(BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get());
        crystalCluster(BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
        simpleBlock(BlockInit.RED_STARLIGHT_CRYSTAL_BLOCK.get());
        simpleBlock(BlockInit.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
        carpet(BlockInit.RED_CRYSTAL_MOSS_CARPET.get(), blockTexture(BlockInit.RED_CRYSTAL_MOSS_CARPET.get()));
        carpet(BlockInit.BLUE_CRYSTAL_MOSS_CARPET.get(), blockTexture(BlockInit.BLUE_CRYSTAL_MOSS_CARPET.get()));

        cross(BlockInit.DEAD_TENTACLES_CORAL.get());
        cross(BlockInit.TENTACLES_CORAL.get());
        coralFan(BlockInit.DEAD_TENTACLES_CORAL_FAN.get());
        coralFan(BlockInit.TENTACLES_CORAL_FAN.get());
        coralWallFan(BlockInit.DEAD_TENTACLES_CORAL_WALL_FAN.get(), BlockInit.DEAD_TENTACLES_CORAL_FAN.get());
        coralWallFan(BlockInit.TENTACLES_CORAL_WALL_FAN.get(), BlockInit.TENTACLES_CORAL_FAN.get());
        simpleBlock(BlockInit.DEAD_TENTACLES_CORAL_BLOCK.get());
        simpleBlock(BlockInit.TENTACLES_CORAL_BLOCK.get());

        cross(BlockInit.DEAD_GOLDEN_CORAL.get());
        cross(BlockInit.GOLDEN_CORAL.get());
        coralFan(BlockInit.DEAD_GOLDEN_CORAL_FAN.get());
        coralFan(BlockInit.GOLDEN_CORAL_FAN.get());
        coralWallFan(BlockInit.DEAD_GOLDEN_CORAL_WALL_FAN.get(), BlockInit.DEAD_GOLDEN_CORAL_FAN.get());
        coralWallFan(BlockInit.GOLDEN_CORAL_WALL_FAN.get(), BlockInit.GOLDEN_CORAL_FAN.get());
        simpleBlock(BlockInit.DEAD_GOLDEN_CORAL_BLOCK.get());
        simpleBlock(BlockInit.GOLDEN_CORAL_BLOCK.get());

        cross(BlockInit.DEAD_CRYSTALLUM_CORAL.get());
        cross(BlockInit.CRYSTALLUM_CORAL.get());
        coralFan(BlockInit.DEAD_CRYSTALLUM_CORAL_FAN.get());
        coralFan(BlockInit.CRYSTALLUM_CORAL_FAN.get());
        coralWallFan(BlockInit.DEAD_CRYSTALLUM_CORAL_WALL_FAN.get(), BlockInit.DEAD_CRYSTALLUM_CORAL_FAN.get());
        coralWallFan(BlockInit.CRYSTALLUM_CORAL_WALL_FAN.get(), BlockInit.CRYSTALLUM_CORAL_FAN.get());
        simpleBlock(BlockInit.DEAD_CRYSTALLUM_CORAL_BLOCK.get());
        simpleBlock(BlockInit.CRYSTALLUM_CORAL_BLOCK.get());

        // woods
        leaves(BlockInit.LUNAR_LEAVES.get());
        woodSet(BlockInit.LUNAR_LOG.get(), BlockInit.LUNAR_WOOD.get(), BlockInit.LUNAR_PLANKS.get(), BlockInit.STRIPPED_LUNAR_LOG.get(), BlockInit.STRIPPED_LUNAR_WOOD.get(), BlockInit.LUNAR_DOOR.get(), false, BlockInit.LUNAR_TRAPDOOR.get(), false, BlockInit.LUNAR_PRESSURE_PLATE.get(), BlockInit.LUNAR_BUTTON.get(), BlockInit.LUNAR_FENCE.get(), BlockInit.LUNAR_FENCE_GATE.get(), BlockInit.LUNAR_SLAB.get(), BlockInit.LUNAR_STAIRS.get(), BlockInit.LUNAR_SIGN.get(), BlockInit.LUNAR_WALL_SIGN.get(), BlockInit.LUNAR_HANGING_SIGN.get(), BlockInit.LUNAR_WALL_HANGING_SIGN.get());
        cross(BlockInit.LUNAR_SAPLING.get());
        pottedPlant(BlockInit.POTTED_LUNAR_SAPLING.get(), blockTexture(BlockInit.LUNAR_SAPLING.get()));
        logBlock(BlockInit.DEAD_LUNAR_LOG.get());
        axisBlock(BlockInit.RED_CRYSTALLIZED_LUNAR_LOG.get(), blockTexture(BlockInit.RED_CRYSTALLIZED_LUNAR_LOG.get()), blockTexture(BlockInit.DEAD_LUNAR_LOG.get()).withSuffix("_top"));
        axisBlock(BlockInit.BLUE_CRYSTALLIZED_LUNAR_LOG.get(), blockTexture(BlockInit.BLUE_CRYSTALLIZED_LUNAR_LOG.get()), blockTexture(BlockInit.DEAD_LUNAR_LOG.get()).withSuffix("_top"));

        leaves(BlockInit.NORTHLAND_LEAVES.get());
        woodSet(BlockInit.NORTHLAND_LOG.get(), BlockInit.NORTHLAND_WOOD.get(), BlockInit.NORTHLAND_PLANKS.get(), BlockInit.STRIPPED_NORTHLAND_LOG.get(), BlockInit.STRIPPED_NORTHLAND_WOOD.get(), BlockInit.NORTHLAND_DOOR.get(), false, BlockInit.NORTHLAND_TRAPDOOR.get(), false, BlockInit.NORTHLAND_PRESSURE_PLATE.get(), BlockInit.NORTHLAND_BUTTON.get(), BlockInit.NORTHLAND_FENCE.get(), BlockInit.NORTHLAND_FENCE_GATE.get(), BlockInit.NORTHLAND_SLAB.get(), BlockInit.NORTHLAND_STAIRS.get(), BlockInit.NORTHLAND_SIGN.get(), BlockInit.NORTHLAND_WALL_SIGN.get(), BlockInit.NORTHLAND_HANGING_SIGN.get(), BlockInit.NORTHLAND_WALL_HANGING_SIGN.get());
        cross(BlockInit.NORTHLAND_SAPLING.get());
        pottedPlant(BlockInit.POTTED_NORTHLAND_SAPLING.get(), blockTexture(BlockInit.NORTHLAND_SAPLING.get()));

        leaves(BlockInit.STARLIGHT_MANGROVE_LEAVES.get());
        woodSet(BlockInit.STARLIGHT_MANGROVE_LOG.get(), BlockInit.STARLIGHT_MANGROVE_WOOD.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get(), BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get(), BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get(), BlockInit.STARLIGHT_MANGROVE_DOOR.get(), true, BlockInit.STARLIGHT_MANGROVE_TRAPDOOR.get(), true, BlockInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get(), BlockInit.STARLIGHT_MANGROVE_BUTTON.get(), BlockInit.STARLIGHT_MANGROVE_FENCE.get(), BlockInit.STARLIGHT_MANGROVE_FENCE_GATE.get(), BlockInit.STARLIGHT_MANGROVE_SLAB.get(), BlockInit.STARLIGHT_MANGROVE_STAIRS.get(), BlockInit.STARLIGHT_MANGROVE_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_HANGING_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get());
        cross(BlockInit.STARLIGHT_MANGROVE_SAPLING.get());
        pottedPlant(BlockInit.POTTED_STARLIGHT_MANGROVE_SAPLING.get(), blockTexture(BlockInit.STARLIGHT_MANGROVE_SAPLING.get()));
        mangroveRoots(BlockInit.STARLIGHT_MANGROVE_ROOTS.get());
        mangroveRoots(BlockInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get());

        leaves(BlockInit.SCARLET_LEAVES.get());
        layered(BlockInit.SCARLET_LEAVES_PILE.get(), blockTexture(BlockInit.SCARLET_LEAVES.get()));
        woodSet(BlockInit.SCARLET_LOG.get(), BlockInit.SCARLET_WOOD.get(), BlockInit.SCARLET_PLANKS.get(), BlockInit.STRIPPED_SCARLET_LOG.get(), BlockInit.STRIPPED_SCARLET_WOOD.get(), BlockInit.SCARLET_DOOR.get(), false, BlockInit.SCARLET_TRAPDOOR.get(), false, BlockInit.SCARLET_PRESSURE_PLATE.get(), BlockInit.SCARLET_BUTTON.get(), BlockInit.SCARLET_FENCE.get(), BlockInit.SCARLET_FENCE_GATE.get(), BlockInit.SCARLET_SLAB.get(), BlockInit.SCARLET_STAIRS.get(), BlockInit.SCARLET_SIGN.get(), BlockInit.SCARLET_WALL_SIGN.get(), BlockInit.SCARLET_HANGING_SIGN.get(), BlockInit.SCARLET_WALL_HANGING_SIGN.get());
        cross(BlockInit.SCARLET_SAPLING.get());
        pottedPlant(BlockInit.POTTED_SCARLET_SAPLING.get(), blockTexture(BlockInit.SCARLET_SAPLING.get()));

        leaves(BlockInit.TORREYA_LEAVES.get());
        woodSet(BlockInit.TORREYA_LOG.get(), BlockInit.TORREYA_WOOD.get(), BlockInit.TORREYA_PLANKS.get(), BlockInit.STRIPPED_TORREYA_LOG.get(), BlockInit.STRIPPED_TORREYA_WOOD.get(), BlockInit.TORREYA_DOOR.get(), true, BlockInit.TORREYA_TRAPDOOR.get(), true, BlockInit.TORREYA_PRESSURE_PLATE.get(), BlockInit.TORREYA_BUTTON.get(), BlockInit.TORREYA_FENCE.get(), BlockInit.TORREYA_FENCE_GATE.get(), BlockInit.TORREYA_SLAB.get(), BlockInit.TORREYA_STAIRS.get(), BlockInit.TORREYA_SIGN.get(), BlockInit.TORREYA_WALL_SIGN.get(), BlockInit.TORREYA_HANGING_SIGN.get(), BlockInit.TORREYA_WALL_HANGING_SIGN.get());
        cross(BlockInit.TORREYA_SAPLING.get());
        pottedPlant(BlockInit.POTTED_TORREYA_SAPLING.get(), blockTexture(BlockInit.TORREYA_SAPLING.get()));
        cross(BlockInit.TORREYA_VINES.get());
        torreyaVines(BlockInit.TORREYA_VINES_PLANT.get());

        // stones
        simpleBlock(BlockInit.GRIMSTONE.get());
        simpleBlock(BlockInit.CHISELED_GRIMSTONE.get());
        stoneSet(BlockInit.COBBLED_GRIMSTONE.get(), BlockInit.COBBLED_GRIMSTONE_SLAB.get(), BlockInit.COBBLED_GRIMSTONE_STAIRS.get(), BlockInit.COBBLED_GRIMSTONE_WALL.get());
        stoneSet(BlockInit.GRIMSTONE_BRICKS.get(), BlockInit.GRIMSTONE_BRICK_SLAB.get(), BlockInit.GRIMSTONE_BRICK_STAIRS.get(), BlockInit.GRIMSTONE_BRICK_WALL.get());
        stoneSet(BlockInit.POLISHED_GRIMSTONE.get(), BlockInit.POLISHED_GRIMSTONE_SLAB.get(), BlockInit.POLISHED_GRIMSTONE_STAIRS.get(), BlockInit.POLISHED_GRIMSTONE_WALL.get());
        stoneSet(BlockInit.GRIMSTONE_TILES.get(), BlockInit.GRIMSTONE_TILE_SLAB.get(), BlockInit.GRIMSTONE_TILE_STAIRS.get(), BlockInit.GRIMSTONE_TILE_WALL.get());
        simpleBlock(BlockInit.GLOWING_GRIMSTONE.get());

        simpleBlock(BlockInit.VOIDSTONE.get());
        simpleBlock(BlockInit.CHISELED_VOIDSTONE.get());
        stoneSet(BlockInit.COBBLED_VOIDSTONE.get(), BlockInit.COBBLED_VOIDSTONE_SLAB.get(), BlockInit.COBBLED_VOIDSTONE_STAIRS.get(), BlockInit.COBBLED_VOIDSTONE_WALL.get());
        stoneSet(BlockInit.VOIDSTONE_BRICKS.get(), BlockInit.VOIDSTONE_BRICK_SLAB.get(), BlockInit.VOIDSTONE_BRICK_STAIRS.get(), BlockInit.VOIDSTONE_BRICK_WALL.get());
        stoneSet(BlockInit.POLISHED_VOIDSTONE.get(), BlockInit.POLISHED_VOIDSTONE_SLAB.get(), BlockInit.POLISHED_VOIDSTONE_STAIRS.get(), BlockInit.POLISHED_VOIDSTONE_WALL.get());
        stoneSet(BlockInit.VOIDSTONE_TILES.get(), BlockInit.VOIDSTONE_TILE_SLAB.get(), BlockInit.VOIDSTONE_TILE_STAIRS.get(), BlockInit.VOIDSTONE_TILE_WALL.get());
        simpleBlock(BlockInit.GLOWING_VOIDSTONE.get());

        simpleBlock(BlockInit.ABYSSLATE.get());
        simpleBlock(BlockInit.CHISELED_POLISHED_ABYSSLATE.get());
        stoneSet(BlockInit.POLISHED_ABYSSLATE.get(), BlockInit.POLISHED_ABYSSLATE_SLAB.get(), BlockInit.POLISHED_ABYSSLATE_STAIRS.get(), BlockInit.POLISHED_ABYSSLATE_WALL.get());
        stoneSet(BlockInit.POLISHED_ABYSSLATE_BRICKS.get(), BlockInit.POLISHED_ABYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_ABYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_ABYSSLATE_BRICK_WALL.get());
        simpleBlock(BlockInit.ABYSSAL_MAGMA_BLOCK.get());
        geyser(BlockInit.ABYSSLATE.get(), BlockInit.ABYSSAL_GEYSER.get());

        simpleBlock(BlockInit.THERMABYSSLATE.get());
        simpleBlock(BlockInit.CHISELED_POLISHED_THERMABYSSLATE.get());
        stoneSet(BlockInit.POLISHED_THERMABYSSLATE.get(), BlockInit.POLISHED_THERMABYSSLATE_SLAB.get(), BlockInit.POLISHED_THERMABYSSLATE_STAIRS.get(), BlockInit.POLISHED_THERMABYSSLATE_WALL.get());
        stoneSet(BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get(), BlockInit.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_THERMABYSSLATE_BRICK_WALL.get());
        simpleBlock(BlockInit.THERMABYSSAL_MAGMA_BLOCK.get());
        geyser(BlockInit.THERMABYSSLATE.get(), BlockInit.THERMABYSSAL_GEYSER.get());

        simpleBlock(BlockInit.CRYOBYSSLATE.get());
        simpleBlock(BlockInit.CHISELED_POLISHED_CRYOBYSSLATE.get());
        stoneSet(BlockInit.POLISHED_CRYOBYSSLATE.get(), BlockInit.POLISHED_CRYOBYSSLATE_SLAB.get(), BlockInit.POLISHED_CRYOBYSSLATE_STAIRS.get(), BlockInit.POLISHED_CRYOBYSSLATE_WALL.get());
        stoneSet(BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get(), BlockInit.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_CRYOBYSSLATE_BRICK_WALL.get());
        simpleBlock(BlockInit.CRYOBYSSAL_MAGMA_BLOCK.get());
        geyser(BlockInit.CRYOBYSSLATE.get(), BlockInit.CRYOBYSSAL_GEYSER.get());

        simpleBlock(BlockInit.NIGHTSHADE_MUD.get());
        simpleBlock(BlockInit.GLOWING_NIGHTSHADE_MUD.get());
        simpleBlock(BlockInit.PACKED_NIGHTSHADE_MUD.get());
        stoneSet(BlockInit.NIGHTSHADE_MUD_BRICKS.get(), BlockInit.NIGHTSHADE_MUD_BRICK_SLAB.get(), BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS.get(), BlockInit.NIGHTSHADE_MUD_BRICK_WALL.get());

        simpleBlock(BlockInit.TWILIGHT_SAND.get());
        sandstoneAndCut(BlockInit.TWILIGHT_SANDSTONE.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get());
        slabBlock(BlockInit.TWILIGHT_SANDSTONE_SLAB.get(), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
        stairsBlock(BlockInit.TWILIGHT_SANDSTONE_STAIRS.get(), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
        wallBlock(BlockInit.TWILIGHT_SANDSTONE_WALL.get(), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"));
        slabBlock(BlockInit.CUT_TWILIGHT_SANDSTONE_SLAB.get(), blockTexture(BlockInit.CUT_TWILIGHT_SANDSTONE.get()), blockTexture(BlockInit.CUT_TWILIGHT_SANDSTONE.get()), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()).withSuffix("_top"), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
        stairsBlock(BlockInit.CUT_TWILIGHT_SANDSTONE_STAIRS.get(), blockTexture(BlockInit.CUT_TWILIGHT_SANDSTONE.get()), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()).withSuffix("_top"), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
        wallBlock(BlockInit.CUT_TWILIGHT_SANDSTONE_WALL.get(), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
        simpleBlock(BlockInit.CHISELED_TWILIGHT_SANDSTONE.get(), models().cubeColumn(name(BlockInit.CHISELED_TWILIGHT_SANDSTONE.get()), blockTexture(BlockInit.CHISELED_TWILIGHT_SANDSTONE.get()), blockTexture(BlockInit.TWILIGHT_SANDSTONE.get()).withSuffix("_top")));

        simpleBlock(BlockInit.GOLEM_STEEL_BLOCK.get());
        simpleBlock(BlockInit.OXIDIZED_GOLEM_STEEL_BLOCK.get());
        slabBlock(BlockInit.GOLEM_STEEL_SLAB.get(), blockTexture(BlockInit.GOLEM_STEEL_BLOCK.get()), blockTexture(BlockInit.GOLEM_STEEL_BLOCK.get()));
        slabBlock(BlockInit.OXIDIZED_GOLEM_STEEL_SLAB.get(), blockTexture(BlockInit.OXIDIZED_GOLEM_STEEL_BLOCK.get()), blockTexture(BlockInit.OXIDIZED_GOLEM_STEEL_BLOCK.get()));
        stairsBlock(BlockInit.GOLEM_STEEL_STAIRS.get(), blockTexture(BlockInit.GOLEM_STEEL_BLOCK.get()));
        stairsBlock(BlockInit.OXIDIZED_GOLEM_STEEL_STAIRS.get(), blockTexture(BlockInit.OXIDIZED_GOLEM_STEEL_BLOCK.get()));
        simpleBlock(BlockInit.GOLEM_STEEL_TILES.get());
        simpleBlock(BlockInit.OXIDIZED_GOLEM_STEEL_TILES.get());
        slabBlock(BlockInit.GOLEM_STEEL_TILE_SLAB.get(), blockTexture(BlockInit.GOLEM_STEEL_TILES.get()), blockTexture(BlockInit.GOLEM_STEEL_TILES.get()));
        slabBlock(BlockInit.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(), blockTexture(BlockInit.OXIDIZED_GOLEM_STEEL_TILES.get()), blockTexture(BlockInit.OXIDIZED_GOLEM_STEEL_TILES.get()));
        stairsBlock(BlockInit.GOLEM_STEEL_TILE_STAIRS.get(), blockTexture(BlockInit.GOLEM_STEEL_TILES.get()));
        stairsBlock(BlockInit.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(), blockTexture(BlockInit.OXIDIZED_GOLEM_STEEL_TILES.get()));
        simpleBlock(BlockInit.CHISELED_GOLEM_STEEL_BLOCK.get());
        simpleBlock(BlockInit.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get());

        simpleBlock(BlockInit.LUNAR_MOSAIC.get());
        slabBlock(BlockInit.LUNAR_MOSAIC_SLAB.get(), blockTexture(BlockInit.LUNAR_MOSAIC.get()), blockTexture(BlockInit.LUNAR_MOSAIC.get()));
        stairsBlock(BlockInit.LUNAR_MOSAIC_STAIRS.get(), blockTexture(BlockInit.LUNAR_MOSAIC.get()));
        fenceBlock(BlockInit.LUNAR_MOSAIC_FENCE.get(), blockTexture(BlockInit.LUNAR_MOSAIC.get()));
        fenceGateBlock(BlockInit.LUNAR_MOSAIC_FENCE_GATE.get(), blockTexture(BlockInit.LUNAR_MOSAIC.get()));
        carpet(BlockInit.LUNAR_MAT.get(), blockTexture(BlockInit.LUNAR_MAT.get()));

        // doomeden
        stoneSet(BlockInit.DOOMEDEN_TILES.get(), BlockInit.DOOMEDEN_TILE_SLAB.get(), BlockInit.DOOMEDEN_TILE_STAIRS.get(), BlockInit.DOOMEDEN_TILE_WALL.get());
        simpleBlock(BlockInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        simpleBlock(BlockInit.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        torch(BlockInit.DOOMED_TORCH.get(), BlockInit.WALL_DOOMED_TORCH.get());
        redstoneTorch(BlockInit.DOOMED_REDSTONE_TORCH.get(), BlockInit.WALL_DOOMED_REDSTONE_TORCH.get());
        stoneSet(BlockInit.DOOMEDEN_BRICKS.get(), BlockInit.DOOMEDEN_BRICK_SLAB.get(), BlockInit.DOOMEDEN_BRICK_STAIRS.get(), BlockInit.DOOMEDEN_BRICK_WALL.get());
        stoneSet(BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), BlockInit.POLISHED_DOOMEDEN_BRICK_SLAB.get(), BlockInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), BlockInit.POLISHED_DOOMEDEN_BRICK_WALL.get());
        onOffBlock(BlockInit.DOOMEDEN_LIGHT.get());
        doomedenKeyhole(BlockInit.DOOMEDEN_KEYHOLE.get(), BlockInit.REDSTONE_DOOMEDEN_KEYHOLE.get());

        cross(BlockInit.STARLIGHT_FLOWER.get());
        pottedPlant(BlockInit.POTTED_STARLIGHT_FLOWER.get(), blockTexture(BlockInit.STARLIGHT_FLOWER.get()));
        cross(BlockInit.CONEBLOOM.get());
        pottedPlant(BlockInit.POTTED_CONEBLOOM.get(), blockTexture(BlockInit.CONEBLOOM.get()));
        cross(BlockInit.NIGHTFAN.get());
        pottedPlant(BlockInit.POTTED_NIGHTFAN.get(), blockTexture(BlockInit.NIGHTFAN.get()));
        cross(BlockInit.PINK_ROSE.get());
        pottedPlant(BlockInit.POTTED_PINK_ROSE.get(), blockTexture(BlockInit.PINK_ROSE.get()));
        cross(BlockInit.STARLIGHT_TORCHFLOWER.get());
        pottedPlant(BlockInit.POTTED_STARLIGHT_TORCHFLOWER.get(), blockTexture(BlockInit.STARLIGHT_TORCHFLOWER.get()));
        doublePlant(BlockInit.NIGHTFAN_BUSH.get());
        doublePlant(BlockInit.PINK_ROSE_BUSH.get());
        cross(BlockInit.NIGHT_SPROUTS.get());
        cross(BlockInit.SMALL_NIGHT_SPROUTS.get());
        cross(BlockInit.GLOWING_NIGHT_SPROUTS.get());
        cross(BlockInit.SMALL_GLOWING_NIGHT_SPROUTS.get());
        cross(BlockInit.LUNAR_GRASS.get());
        cross(BlockInit.GLOWING_LUNAR_GRASS.get());
        cross(BlockInit.CRESCENT_GRASS.get());
        cross(BlockInit.GLOWING_CRESCENT_GRASS.get());
        cross(BlockInit.PARASOL_GRASS.get());
        cross(BlockInit.GLOWING_PARASOL_GRASS.get());
        cross(BlockInit.LUNAR_BUSH.get());
        cross(BlockInit.GLOWING_LUNAR_BUSH.get());
        doublePlant(BlockInit.TALL_CRESCENT_GRASS.get());
        doublePlant(BlockInit.TALL_GLOWING_CRESCENT_GRASS.get());
        doublePlant(BlockInit.LUNAR_REED.get());
        cross(BlockInit.WHISPERBLOOM.get());
        cross(BlockInit.GLADESPIKE.get());
        cross(BlockInit.VIVIDSTALK.get());
        doublePlant(BlockInit.TALL_GLADESPIKE.get());
        cross(BlockInit.GLOWING_MUSHROOM.get());
        mushroomBlock(BlockInit.GLOWING_MUSHROOM_BLOCK.get());
        mushroomBlock(BlockInit.GLOWING_MUSHROOM_STEM.get(), blockTexture(BlockInit.GLOWING_MUSHROOM_BLOCK.get()).withSuffix("_inside"));

        cross(BlockInit.SWAMP_ROSE.get());
        pottedPlant(BlockInit.POTTED_SWAMP_ROSE.get(), blockTexture(BlockInit.SWAMP_ROSE.get()));
        cross(BlockInit.FANTABUD.get());
        cross(BlockInit.GREEN_FANTABUD.get());
        cross(BlockInit.FANTAFERN.get());
        cross(BlockInit.GREEN_FANTAFERN.get());
        cross(BlockInit.FANTAGRASS.get());
        cross(BlockInit.GREEN_FANTAGRASS.get());

        cross(BlockInit.ORANGE_SCARLET_BUD.get());
        cross(BlockInit.PURPLE_SCARLET_BUD.get());
        cross(BlockInit.RED_SCARLET_BUD.get());
        cross(BlockInit.SCARLET_GRASS.get());

        cross(BlockInit.WITHERED_STARLIGHT_FLOWER.get());

        cross(BlockInit.DEAD_LUNAR_BUSH.get());

        waterlily(BlockInit.MOONLIGHT_LILY_PAD.get());
        waterlily(BlockInit.STARLIT_LILY_PAD.get());
        waterlily(BlockInit.MOONLIGHT_DUCKWEED.get());

        simpleBlock(BlockInit.NIGHTSHADE_DIRT.get());
        grassBlock(BlockInit.NIGHTSHADE_GRASS_BLOCK.get(), blockTexture(BlockInit.NIGHTSHADE_DIRT.get()));
        simpleGrassBlock(BlockInit.FANTASY_GRASS_BLOCK.get(), blockTexture(BlockInit.NIGHTSHADE_MUD.get()));

        tintedCubeAll(BlockInit.WHITE_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.ORANGE_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.MAGENTA_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.LIGHT_BLUE_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.YELLOW_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.LIME_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.PINK_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.GRAY_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.LIGHT_GRAY_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.CYAN_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.PURPLE_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.BLUE_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.BROWN_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.GREEN_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.RED_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
        tintedCubeAll(BlockInit.BLACK_YETI_FUR.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);

        tintedCarpet(BlockInit.WHITE_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.ORANGE_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.MAGENTA_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.LIGHT_BLUE_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.YELLOW_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.LIME_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.PINK_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.GRAY_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.LIGHT_GRAY_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.CYAN_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.PURPLE_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.BLUE_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.BROWN_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.GREEN_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.RED_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
        tintedCarpet(BlockInit.BLACK_YETI_FUR_CARPET.get(), new ResourceLocation(EternalStarlight.MOD_ID, ModelProvider.BLOCK_FOLDER + "/yeti_fur"));

        simpleBlock(BlockInit.AETHERSENT_BLOCK.get());
        simpleBlock(BlockInit.SPRINGSTONE.get());
        simpleBlock(BlockInit.THERMAL_SPRINGSTONE.get());
        simpleBlock(BlockInit.SWAMP_SILVER_ORE.get());
        simpleBlock(BlockInit.SWAMP_SILVER_BLOCK.get());
        simpleBlock(BlockInit.GRIMSTONE_REDSTONE_ORE.get());
        simpleBlock(BlockInit.VOIDSTONE_REDSTONE_ORE.get());
        simpleBlock(BlockInit.GRIMSTONE_SALTPETER_ORE.get());
        simpleBlock(BlockInit.VOIDSTONE_SALTPETER_ORE.get());
        simpleBlock(BlockInit.SALTPETER_BLOCK.get());
        particleOnly(BlockInit.ETHER.get());
        onOffBlock(BlockInit.ENERGY_BLOCK.get());
        spawner(BlockInit.STARLIGHT_GOLEM_SPAWNER.get());
        spawner(BlockInit.LUNAR_MONSTROSITY_SPAWNER.get());
        portal(BlockInit.STARLIGHT_PORTAL.get());
    }

    private void woodSet(RotatedPillarBlock log, Block wood, Block planks, RotatedPillarBlock strippedLog, Block strippedWood, DoorBlock door, boolean cutoutDoor, TrapDoorBlock trapdoor, boolean cutoutTrapdoor, PressurePlateBlock pressurePlate, ButtonBlock button, FenceBlock fence, FenceGateBlock fenceGate, SlabBlock slab, StairBlock stairs, Block sign, Block wallSign, Block hangingSign, Block wallHangingSign) {
        logBlock(log);
        simpleBlock(wood, models().cubeAll(name(wood), blockTexture(log)));
        simpleBlock(planks);
        logBlock(strippedLog);
        simpleBlock(strippedWood, models().cubeAll(name(strippedWood), blockTexture(strippedLog)));
        if (cutoutDoor) {
            doorBlockWithRenderType(door, blockTexture(door).withSuffix("_bottom"), blockTexture(door).withSuffix("_top"), CUTOUT);
        } else {
            doorBlock(door, blockTexture(door).withSuffix("_bottom"), blockTexture(door).withSuffix("_top"));
        }
        if (cutoutTrapdoor) {
            trapdoorBlockWithRenderType(trapdoor, blockTexture(trapdoor), true, CUTOUT);
        } else {
            trapdoorBlock(trapdoor, blockTexture(trapdoor), true);
        }
        pressurePlateBlock(pressurePlate, blockTexture(planks));
        buttonBlock(button, blockTexture(planks));
        fenceBlock(fence, blockTexture(planks));
        fenceGateBlock(fenceGate, blockTexture(planks));
        slabBlock(slab, blockTexture(planks), blockTexture(planks));
        stairsBlock(stairs, blockTexture(planks));
        simpleSign(sign, wallSign, blockTexture(planks));
        simpleSign(hangingSign, wallHangingSign, blockTexture(planks));
    }

    private void stoneSet(Block stone, SlabBlock slab, StairBlock stairs, WallBlock wall) {
        simpleBlock(stone);
        slabBlock(slab, blockTexture(stone), blockTexture(stone));
        stairsBlock(stairs, blockTexture(stone));
        wallBlock(wall, blockTexture(stone));
    }

    private void portal(Block block) {
        ModelFile modelEw = models().getBuilder(name(block) + "_ew")
                .texture("particle", blockTexture(block))
                .texture("portal", blockTexture(block))
                .renderType(TRANSLUCENT)
                .element()
                .from(6, 0, 0)
                .to(10, 16, 16)
                .face(Direction.EAST)
                .uvs(0, 0, 16, 16)
                .texture("#portal")
                .end()
                .face(Direction.WEST)
                .uvs(0, 0, 16, 16)
                .texture("#portal")
                .end()
                .end();
        ModelFile modelNs = models().getBuilder(name(block) + "_ns")
                .texture("particle", blockTexture(block))
                .texture("portal", blockTexture(block))
                .renderType(TRANSLUCENT)
                .element()
                .from(0, 0, 6)
                .to(16, 16, 10)
                .face(Direction.NORTH)
                .uvs(0, 0, 16, 16)
                .texture("#portal")
                .end()
                .face(Direction.SOUTH)
                .uvs(0, 0, 16, 16)
                .texture("#portal")
                .end()
                .end();
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Direction.Axis.X ? modelNs : modelEw).build());
    }

    private void torreyaVines(Block block) {
        ModelFile modelNormal = models().cross(name(block), blockTexture(block)).renderType(CUTOUT);
        ModelFile modelTop = models().cross(name(block) + "_top", blockTexture(block).withSuffix("_top")).renderType(CUTOUT);
        onOffBlock(block, TorreyaVinesPlantBlock.TOP, modelTop, modelNormal);
    }

    private void vinesWithFruit(Block block) {
        ModelFile modelNormal = models().cross(name(block), blockTexture(block)).renderType(CUTOUT);
        ModelFile modelLit = models().cross(name(block) + "_lit", blockTexture(block).withSuffix("_lit")).renderType(CUTOUT);
        onOffBlock(block, BlockStateProperties.BERRIES, modelLit, modelNormal);
    }

    private void mangroveRoots(Block block) {
        cubeColumn(block, blockTexture(block).withSuffix("_top"), blockTexture(block).withSuffix("_side"), CUTOUT_MIPPED);
    }

    private void doomedenKeyhole(Block block, Block redstone) {
        ModelFile modelOn = models().orientable(name(block) + "_lit", blockTexture(block).withSuffix("_on_side"), blockTexture(block).withSuffix("_on_front"), blockTexture(BlockInit.DOOMEDEN_TILES.get()));
        ModelFile modelOff = models().orientable(name(block), blockTexture(block).withSuffix("_off_side"), blockTexture(block).withSuffix("_off_front"), blockTexture(BlockInit.DOOMEDEN_TILES.get()));
        horizontalBlock(block, state -> state.getValue(BlockStateProperties.LIT) ? modelOn : modelOff);

        ModelFile modelOnRedstone = models().orientable(name(redstone) + "_lit", blockTexture(block).withSuffix("_on_side"), blockTexture(redstone).withSuffix("_on"), blockTexture(BlockInit.DOOMEDEN_TILES.get()));
        ModelFile modelOffRedstone = models().orientable(name(redstone), blockTexture(block).withSuffix("_off_side"), blockTexture(redstone).withSuffix("_off"), blockTexture(BlockInit.DOOMEDEN_TILES.get()));
        horizontalBlock(redstone, state -> state.getValue(BlockStateProperties.LIT) ? modelOnRedstone : modelOffRedstone);
    }

    private void crystalCluster(Block block) {
        ModelFile modelFile = models().cross(name(block), blockTexture(block)).renderType(CUTOUT);
        getVariantBuilder(block).forAllStates((state) -> {
            Direction direction = state.getValue(BlockStateProperties.FACING);
            int rotX = direction == Direction.DOWN ? 180 : direction == Direction.UP ? 0 : 90;
            return ConfiguredModel.builder()
                    .modelFile(modelFile).rotationY(((int) direction.toYRot() + 180) % 360).rotationX(rotX).build();
        });
    }

    private void spawner(Block block) {
        simpleBlock(block, models().cubeAll(name(block), blockTexture(Blocks.SPAWNER)).renderType(CUTOUT));
    }

    private void geyser(Block stone, Block geyser) {
        ModelFile modelFile = models().cubeBottomTop(name(geyser), blockTexture(stone), blockTexture(stone), blockTexture(geyser));
        simpleBlock(geyser, modelFile);
    }

    private void waterlily(Block lily) {
        ModelFile model = models().getBuilder(name(lily))
                .ao(false)
                .texture("particle", blockTexture(lily))
                .texture("texture", blockTexture(lily))
                .renderType(TRANSLUCENT)
                .element()
                .from(0, 0.25f, 0)
                .to(16, 0.25f, 16)
                .face(Direction.DOWN)
                .uvs(0, 16, 16, 0)
                .texture("#texture")
                .tintindex(0)
                .end()
                .face(Direction.UP)
                .uvs(0, 0, 16, 16)
                .texture("#texture")
                .tintindex(0)
                .end()
                .end();
        getVariantBuilder(lily).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(model).nextModel()
                .rotationY(270).modelFile(model).nextModel()
                .rotationY(180).modelFile(model).nextModel()
                .rotationY(90).modelFile(model).build());
    }

    private void mushroomBlock(Block block) {
        mushroomBlock(block, blockTexture(block).withSuffix("_inside"));
    }

    private void mushroomBlock(Block block, ResourceLocation inner) {
        ModelFile modelOutside = models().singleTexture(name(block), new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/template_single_face"), blockTexture(block));
        ModelFile modelInside = models().singleTexture(name(block) + "_inside", new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/template_single_face"), inner);
        getMultipartBuilder(block)
                .part().modelFile(modelOutside).addModel().condition(BlockStateProperties.NORTH, true).end()
                .part().modelFile(modelOutside).uvLock(true).rotationY(90).addModel().condition(BlockStateProperties.EAST, true).end()
                .part().modelFile(modelOutside).uvLock(true).rotationY(180).addModel().condition(BlockStateProperties.SOUTH, true).end()
                .part().modelFile(modelOutside).uvLock(true).rotationY(270).addModel().condition(BlockStateProperties.WEST, true).end()
                .part().modelFile(modelOutside).uvLock(true).rotationX(270).addModel().condition(BlockStateProperties.UP, true).end()
                .part().modelFile(modelOutside).uvLock(true).rotationX(90).addModel().condition(BlockStateProperties.DOWN, true).end()
                .part().modelFile(modelInside).addModel().condition(BlockStateProperties.NORTH, false).end()
                .part().modelFile(modelInside).uvLock(false).rotationY(90).addModel().condition(BlockStateProperties.EAST, false).end()
                .part().modelFile(modelInside).uvLock(false).rotationY(180).addModel().condition(BlockStateProperties.SOUTH, false).end()
                .part().modelFile(modelInside).uvLock(false).rotationY(270).addModel().condition(BlockStateProperties.WEST, false).end()
                .part().modelFile(modelInside).uvLock(false).rotationX(270).addModel().condition(BlockStateProperties.UP, false).end()
                .part().modelFile(modelInside).uvLock(false).rotationX(90).addModel().condition(BlockStateProperties.DOWN, false).end();
    }

    private void simpleGrassBlock(Block grassBlock, ResourceLocation dirt) {
        ModelFile modelFile = models().cubeBottomTop(name(grassBlock), blockTexture(grassBlock).withSuffix("_side"), dirt, blockTexture(grassBlock).withSuffix("_top"));
        getVariantBuilder(grassBlock).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(modelFile).nextModel()
                .rotationY(270).modelFile(modelFile).nextModel()
                .rotationY(180).modelFile(modelFile).nextModel()
                .rotationY(90).modelFile(modelFile).build());
    }

    private void grassBlock(Block grassBlock, ResourceLocation dirt) {
        ModelFile modelNormal = models().withExistingParent(name(grassBlock), new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/block")).renderType(CUTOUT_MIPPED)
                .texture("particle", dirt).texture("bottom", dirt).texture("top", blockTexture(grassBlock).withSuffix("_top")).texture("side", blockTexture(grassBlock).withSuffix("_side")).texture("overlay", blockTexture(grassBlock).withSuffix("_side_overlay"))
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .allFaces((dir, builder) -> {
                    var faceBuilder = builder
                            .uvs(0, 0, 16, 16)
                            .texture("#" + (dir == Direction.UP ? "top" : (dir == Direction.DOWN ? "bottom" : "side")))
                            .cullface(dir);
                    if (dir == Direction.UP) {
                        faceBuilder.tintindex(0).end();
                    } else {
                        faceBuilder.end();
                    }
                })
                .end()
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .face(Direction.NORTH)
                .uvs(0, 0, 16, 16)
                .texture("#overlay")
                .cullface(Direction.NORTH)
                .tintindex(0)
                .end()
                .face(Direction.SOUTH)
                .uvs(0, 0, 16, 16)
                .texture("#overlay")
                .cullface(Direction.SOUTH)
                .tintindex(0)
                .end()
                .face(Direction.WEST)
                .uvs(0, 0, 16, 16)
                .texture("#overlay")
                .cullface(Direction.WEST)
                .tintindex(0)
                .end()
                .face(Direction.EAST)
                .uvs(0, 0, 16, 16)
                .texture("#overlay")
                .cullface(Direction.EAST)
                .tintindex(0)
                .end()
                .end();
        ModelFile modelSnow = models().cubeBottomTop(name(grassBlock) + "_snow", blockTexture(grassBlock).withSuffix("_snow"), dirt, blockTexture(grassBlock).withSuffix("_top"));
        getVariantBuilder(grassBlock).forAllStates(state -> {
            if (state.getValue(BlockStateProperties.SNOWY)) {
                return ConfiguredModel.builder().modelFile(modelSnow).build();
            } else {
                return ConfiguredModel.builder()
                        .modelFile(modelNormal).nextModel()
                        .rotationY(270).modelFile(modelNormal).nextModel()
                        .rotationY(180).modelFile(modelNormal).nextModel()
                        .rotationY(90).modelFile(modelNormal).build();
            }
        });
    }

    private void sandstoneAndCut(Block sandstone, Block cut) {
        ModelFile modelFile = models().cubeBottomTop(name(sandstone), blockTexture(sandstone), blockTexture(sandstone).withSuffix("_bottom"), blockTexture(sandstone).withSuffix("_top"));
        ModelFile modelCut = models().cubeColumn(name(cut), blockTexture(cut), blockTexture(sandstone).withSuffix("_top"));
        simpleBlock(sandstone, modelFile);
        simpleBlock(cut, modelCut);
    }

    private void coralFan(Block fan) {
        ModelFile modelFile = models().singleTexture(name(fan), new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/coral_fan"), "fan", blockTexture(fan)).renderType(CUTOUT);
        simpleBlock(fan, modelFile);
    }

    private void coralWallFan(Block wall, Block fan) {
        ModelFile modelFile = models().singleTexture(name(wall), new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/coral_wall_fan"), "fan", blockTexture(fan)).renderType(CUTOUT);
        getVariantBuilder(wall).forAllStates((state) -> ConfiguredModel.builder().modelFile(modelFile).rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build());
    }

    private void torch(Block normal, Block wall) {
        ModelFile modelNormal = models().torch(name(normal), blockTexture(normal)).renderType(CUTOUT);
        ModelFile modelWall = models().torchWall(name(wall), blockTexture(normal)).renderType(CUTOUT);
        simpleBlock(normal, modelNormal);
        horizontalBlock(wall, modelWall, 90);
    }

    private void redstoneTorch(Block normal, Block wall) {
        ModelFile modelNormal = models().torch(name(normal) + "_lit", blockTexture(normal)).renderType(CUTOUT);
        ModelFile modelNormalOff = models().torch(name(normal), blockTexture(normal).withSuffix("_off")).renderType(CUTOUT);
        ModelFile modelWall = models().torchWall(name(wall) + "_lit", blockTexture(normal)).renderType(CUTOUT);
        ModelFile modelWallOff = models().torchWall(name(wall), blockTexture(normal).withSuffix("_off")).renderType(CUTOUT);
        onOffBlock(normal, BlockStateProperties.LIT, modelNormal, modelNormalOff);
        horizontalBlock(wall, state -> state.getValue(BlockStateProperties.LIT) ? modelWall : modelWallOff, 90);
    }

    private void onOffBlock(Block block) {
        ModelFile on = models().cubeAll(name(block) + "_lit", blockTexture(block).withSuffix("_lit"));
        ModelFile off = models().cubeAll(name(block), blockTexture(block));
        onOffBlock(block, BlockStateProperties.LIT, on, off);
    }

    private void onOffBlock(Block block, BooleanProperty property, ModelFile on, ModelFile off) {
        getVariantBuilder(block)
                .partialState().with(property, false)
                .modelForState().modelFile(off).addModel()
                .partialState().with(property, true)
                .modelForState().modelFile(on).addModel();
    }

    private void simpleSign(Block normal, Block wall, ResourceLocation location) {
        particleOnly(normal, location);
        particleOnly(wall, location);
    }

    private void particleOnly(Block block) {
        particleOnly(block, blockTexture(block));
    }

    private void particleOnly(Block block, ResourceLocation location) {
        simpleBlock(block, models().getBuilder(name(block)).texture("particle", location));
    }

    private void leaves(Block leaves) {
        ModelFile modelFile = models().singleTexture(name(leaves), new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/leaves"), "all", blockTexture(leaves)).renderType(CUTOUT_MIPPED);
        simpleBlock(leaves, modelFile);
    }

    private void layered(Block layered, ResourceLocation texture) {
        getVariantBuilder(layered).forAllStates((state -> {
            int height = state.getValue(BlockStateProperties.LAYERS) * 2;
            ModelFile modelFile = height < 16 ? models().withExistingParent(name(layered) + "_height" + height, new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/thin_block"))
                    .texture("particle", texture)
                    .texture("texture", texture)
                    .renderType(CUTOUT_MIPPED)
                    .element()
                    .from(0, 0, 0)
                    .to(16, height, 16)
                    .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#texture").cullface(Direction.DOWN).end()
                    .face(Direction.UP).uvs(0, 0, 16, 16).texture("#texture").end()
                    .face(Direction.NORTH).uvs(0, 16 - height, 16, 16).texture("#texture").cullface(Direction.NORTH).end()
                    .face(Direction.SOUTH).uvs(0, 16 - height, 16, 16).texture("#texture").cullface(Direction.SOUTH).end()
                    .face(Direction.WEST).uvs(0, 16 - height, 16, 16).texture("#texture").cullface(Direction.WEST).end()
                    .face(Direction.EAST).uvs(0, 16 - height, 16, 16).texture("#texture").cullface(Direction.EAST).end()
                    .end() : models().cubeAll(name(layered), texture);
            return ConfiguredModel.builder().modelFile(modelFile).build();
        }));
    }

    private void pottedPlant(Block potted, ResourceLocation location) {
        ModelFile modelFile = models().singleTexture(name(potted), new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/flower_pot_cross"), "plant", location).renderType(CUTOUT);
        simpleBlock(potted, modelFile);
    }

    private void doublePlant(Block block) {
        ModelFile upper = models().cross(name(block) + "_top", blockTexture(block).withSuffix("_top")).renderType(CUTOUT);
        ModelFile lower = models().cross(name(block) + "_bottom", blockTexture(block).withSuffix("_bottom")).renderType(CUTOUT);
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER)
                .modelForState().modelFile(upper).addModel()
                .partialState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
                .modelForState().modelFile(lower).addModel();
    }

    private void cubeColumn(Block block, ResourceLocation end, ResourceLocation side, ResourceLocation renderType) {
        ModelFile modelFile = models().cubeColumn(name(block), side, end).renderType(renderType);
        simpleBlock(block, modelFile);
    }

    private void tintedCubeAll(Block block, ResourceLocation texture, ResourceLocation renderType) {
        ModelFile modelFile = models().withExistingParent(name(block), "block/block")
                .renderType(renderType)
                .texture("particle", texture)
                .texture("all", texture)
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .allFaces((direction, builder) -> {
                    builder.texture("#all").cullface(direction).tintindex(0);
                }).end();
        simpleBlock(block, modelFile);
    }

    private void cross(Block block) {
        cross(block, blockTexture(block), CUTOUT);
    }

    private void cross(Block block, ResourceLocation texture, ResourceLocation renderType) {
        ModelFile modelFile = models().cross(name(block), texture).renderType(renderType);
        simpleBlock(block, modelFile);
    }

    private void tintedCross(Block block) {
        tintedCross(block, blockTexture(block), CUTOUT);
    }

    private void tintedCross(Block block, ResourceLocation texture, ResourceLocation renderType) {
        ModelFile modelFile = models().withExistingParent(name(block), "tinted_cross").texture("cross", texture).renderType(renderType);
        simpleBlock(block, modelFile);
    }

    private void tintedCarpet(Block block, ResourceLocation wool) {
        ModelFile modelFile = models().withExistingParent(name(block), "block/thin_block")
                .texture("particle", wool)
                .texture("wool", wool)
                .element()
                .from(0, 0, 0)
                .to(16, 1, 16)
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#wool").cullface(Direction.DOWN).tintindex(0).end()
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#wool").tintindex(0).end()
                .face(Direction.NORTH).uvs(0, 15, 16, 16).texture("#wool").cullface(Direction.NORTH).tintindex(0).end()
                .face(Direction.SOUTH).uvs(0, 15, 16, 16).texture("#wool").cullface(Direction.SOUTH).tintindex(0).end()
                .face(Direction.WEST).uvs(0, 15, 16, 16).texture("#wool").cullface(Direction.WEST).tintindex(0).end()
                .face(Direction.EAST).uvs(0, 15, 16, 16).texture("#wool").cullface(Direction.EAST).tintindex(0).end()
                .end();
        simpleBlock(block, modelFile);
    }

    private void carpet(Block block, ResourceLocation wool) {
        ModelFile modelFile = models().carpet(name(block), wool);
        simpleBlock(block, modelFile);
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}
