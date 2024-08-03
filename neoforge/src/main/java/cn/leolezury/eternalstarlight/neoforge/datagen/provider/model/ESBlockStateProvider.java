package cn.leolezury.eternalstarlight.neoforge.datagen.provider.model;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.*;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
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
	private static final ResourceLocation SOLID = ResourceLocation.withDefaultNamespace("solid");
	private static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace("cutout");
	private static final ResourceLocation CUTOUT_MIPPED = ResourceLocation.withDefaultNamespace("cutout_mipped");
	private static final ResourceLocation TRANSLUCENT = ResourceLocation.withDefaultNamespace("translucent");

	public ESBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, EternalStarlight.ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		vinesWithFruit(ESBlocks.BERRIES_VINES.get());
		vinesWithFruit(ESBlocks.BERRIES_VINES_PLANT.get());
		tintedCross(ESBlocks.CAVE_MOSS.get());
		tintedCross(ESBlocks.CAVE_MOSS_PLANT.get());
		multifaceBlock(ESBlocks.CAVE_MOSS_VEIN.get());
		vinesWithFruit(ESBlocks.ABYSSAL_KELP.get());
		vinesWithFruit(ESBlocks.ABYSSAL_KELP_PLANT.get());
		orbflora(ESBlocks.ORBFLORA.get());
		cross(ESBlocks.ORBFLORA_PLANT.get());
		simpleBlock(ESBlocks.ORBFLORA_LIGHT.get());
		crystalCluster(ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get());
		crystalCluster(ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
		simpleBlock(ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get());
		simpleBlock(ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
		carpet(ESBlocks.RED_CRYSTAL_MOSS_CARPET.get(), blockTexture(ESBlocks.RED_CRYSTAL_MOSS_CARPET.get()));
		carpet(ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get(), blockTexture(ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get()));

		cross(ESBlocks.JINGLING_PICKLE.get());
		cross(ESBlocks.DEAD_TENTACLES_CORAL.get());
		cross(ESBlocks.TENTACLES_CORAL.get());
		coralFan(ESBlocks.DEAD_TENTACLES_CORAL_FAN.get());
		coralFan(ESBlocks.TENTACLES_CORAL_FAN.get());
		coralWallFan(ESBlocks.DEAD_TENTACLES_CORAL_WALL_FAN.get(), ESBlocks.DEAD_TENTACLES_CORAL_FAN.get());
		coralWallFan(ESBlocks.TENTACLES_CORAL_WALL_FAN.get(), ESBlocks.TENTACLES_CORAL_FAN.get());
		simpleBlock(ESBlocks.DEAD_TENTACLES_CORAL_BLOCK.get());
		simpleBlock(ESBlocks.TENTACLES_CORAL_BLOCK.get());

		cross(ESBlocks.DEAD_GOLDEN_CORAL.get());
		cross(ESBlocks.GOLDEN_CORAL.get());
		coralFan(ESBlocks.DEAD_GOLDEN_CORAL_FAN.get());
		coralFan(ESBlocks.GOLDEN_CORAL_FAN.get());
		coralWallFan(ESBlocks.DEAD_GOLDEN_CORAL_WALL_FAN.get(), ESBlocks.DEAD_GOLDEN_CORAL_FAN.get());
		coralWallFan(ESBlocks.GOLDEN_CORAL_WALL_FAN.get(), ESBlocks.GOLDEN_CORAL_FAN.get());
		simpleBlock(ESBlocks.DEAD_GOLDEN_CORAL_BLOCK.get());
		simpleBlock(ESBlocks.GOLDEN_CORAL_BLOCK.get());

		cross(ESBlocks.DEAD_CRYSTALLUM_CORAL.get());
		cross(ESBlocks.CRYSTALLUM_CORAL.get());
		coralFan(ESBlocks.DEAD_CRYSTALLUM_CORAL_FAN.get());
		coralFan(ESBlocks.CRYSTALLUM_CORAL_FAN.get());
		coralWallFan(ESBlocks.DEAD_CRYSTALLUM_CORAL_WALL_FAN.get(), ESBlocks.DEAD_CRYSTALLUM_CORAL_FAN.get());
		coralWallFan(ESBlocks.CRYSTALLUM_CORAL_WALL_FAN.get(), ESBlocks.CRYSTALLUM_CORAL_FAN.get());
		simpleBlock(ESBlocks.DEAD_CRYSTALLUM_CORAL_BLOCK.get());
		simpleBlock(ESBlocks.CRYSTALLUM_CORAL_BLOCK.get());

		// woods
		leaves(ESBlocks.LUNAR_LEAVES.get());
		woodSet(ESBlocks.LUNAR_LOG.get(), ESBlocks.LUNAR_WOOD.get(), ESBlocks.LUNAR_PLANKS.get(), ESBlocks.STRIPPED_LUNAR_LOG.get(), ESBlocks.STRIPPED_LUNAR_WOOD.get(), ESBlocks.LUNAR_DOOR.get(), false, ESBlocks.LUNAR_TRAPDOOR.get(), false, ESBlocks.LUNAR_PRESSURE_PLATE.get(), ESBlocks.LUNAR_BUTTON.get(), ESBlocks.LUNAR_FENCE.get(), ESBlocks.LUNAR_FENCE_GATE.get(), ESBlocks.LUNAR_SLAB.get(), ESBlocks.LUNAR_STAIRS.get(), ESBlocks.LUNAR_SIGN.get(), ESBlocks.LUNAR_WALL_SIGN.get(), ESBlocks.LUNAR_HANGING_SIGN.get(), ESBlocks.LUNAR_WALL_HANGING_SIGN.get());
		cross(ESBlocks.LUNAR_SAPLING.get());
		pottedPlant(ESBlocks.POTTED_LUNAR_SAPLING.get(), blockTexture(ESBlocks.LUNAR_SAPLING.get()));
		logBlock(ESBlocks.DEAD_LUNAR_LOG.get());
		axisBlock(ESBlocks.RED_CRYSTALLIZED_LUNAR_LOG.get(), blockTexture(ESBlocks.RED_CRYSTALLIZED_LUNAR_LOG.get()), blockTexture(ESBlocks.DEAD_LUNAR_LOG.get()).withSuffix("_top"));
		axisBlock(ESBlocks.BLUE_CRYSTALLIZED_LUNAR_LOG.get(), blockTexture(ESBlocks.BLUE_CRYSTALLIZED_LUNAR_LOG.get()), blockTexture(ESBlocks.DEAD_LUNAR_LOG.get()).withSuffix("_top"));

		snowyLeaves(ESBlocks.NORTHLAND_LEAVES.get());
		woodSet(ESBlocks.NORTHLAND_LOG.get(), ESBlocks.NORTHLAND_WOOD.get(), ESBlocks.NORTHLAND_PLANKS.get(), ESBlocks.STRIPPED_NORTHLAND_LOG.get(), ESBlocks.STRIPPED_NORTHLAND_WOOD.get(), ESBlocks.NORTHLAND_DOOR.get(), false, ESBlocks.NORTHLAND_TRAPDOOR.get(), false, ESBlocks.NORTHLAND_PRESSURE_PLATE.get(), ESBlocks.NORTHLAND_BUTTON.get(), ESBlocks.NORTHLAND_FENCE.get(), ESBlocks.NORTHLAND_FENCE_GATE.get(), ESBlocks.NORTHLAND_SLAB.get(), ESBlocks.NORTHLAND_STAIRS.get(), ESBlocks.NORTHLAND_SIGN.get(), ESBlocks.NORTHLAND_WALL_SIGN.get(), ESBlocks.NORTHLAND_HANGING_SIGN.get(), ESBlocks.NORTHLAND_WALL_HANGING_SIGN.get());
		cross(ESBlocks.NORTHLAND_SAPLING.get());
		pottedPlant(ESBlocks.POTTED_NORTHLAND_SAPLING.get(), blockTexture(ESBlocks.NORTHLAND_SAPLING.get()));

		leaves(ESBlocks.STARLIGHT_MANGROVE_LEAVES.get());
		woodSet(ESBlocks.STARLIGHT_MANGROVE_LOG.get(), ESBlocks.STARLIGHT_MANGROVE_WOOD.get(), ESBlocks.STARLIGHT_MANGROVE_PLANKS.get(), ESBlocks.STRIPPED_STARLIGHT_MANGROVE_LOG.get(), ESBlocks.STRIPPED_STARLIGHT_MANGROVE_WOOD.get(), ESBlocks.STARLIGHT_MANGROVE_DOOR.get(), true, ESBlocks.STARLIGHT_MANGROVE_TRAPDOOR.get(), true, ESBlocks.STARLIGHT_MANGROVE_PRESSURE_PLATE.get(), ESBlocks.STARLIGHT_MANGROVE_BUTTON.get(), ESBlocks.STARLIGHT_MANGROVE_FENCE.get(), ESBlocks.STARLIGHT_MANGROVE_FENCE_GATE.get(), ESBlocks.STARLIGHT_MANGROVE_SLAB.get(), ESBlocks.STARLIGHT_MANGROVE_STAIRS.get(), ESBlocks.STARLIGHT_MANGROVE_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_WALL_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_HANGING_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get());
		cross(ESBlocks.STARLIGHT_MANGROVE_SAPLING.get());
		pottedPlant(ESBlocks.POTTED_STARLIGHT_MANGROVE_SAPLING.get(), blockTexture(ESBlocks.STARLIGHT_MANGROVE_SAPLING.get()));
		mangroveRoots(ESBlocks.STARLIGHT_MANGROVE_ROOTS.get());
		mangroveRoots(ESBlocks.MUDDY_STARLIGHT_MANGROVE_ROOTS.get());

		leaves(ESBlocks.SCARLET_LEAVES.get());
		layered(ESBlocks.SCARLET_LEAVES_PILE.get(), blockTexture(ESBlocks.SCARLET_LEAVES.get()));
		woodSet(ESBlocks.SCARLET_LOG.get(), ESBlocks.SCARLET_WOOD.get(), ESBlocks.SCARLET_PLANKS.get(), ESBlocks.STRIPPED_SCARLET_LOG.get(), ESBlocks.STRIPPED_SCARLET_WOOD.get(), ESBlocks.SCARLET_DOOR.get(), false, ESBlocks.SCARLET_TRAPDOOR.get(), false, ESBlocks.SCARLET_PRESSURE_PLATE.get(), ESBlocks.SCARLET_BUTTON.get(), ESBlocks.SCARLET_FENCE.get(), ESBlocks.SCARLET_FENCE_GATE.get(), ESBlocks.SCARLET_SLAB.get(), ESBlocks.SCARLET_STAIRS.get(), ESBlocks.SCARLET_SIGN.get(), ESBlocks.SCARLET_WALL_SIGN.get(), ESBlocks.SCARLET_HANGING_SIGN.get(), ESBlocks.SCARLET_WALL_HANGING_SIGN.get());
		cross(ESBlocks.SCARLET_SAPLING.get());
		pottedPlant(ESBlocks.POTTED_SCARLET_SAPLING.get(), blockTexture(ESBlocks.SCARLET_SAPLING.get()));

		leaves(ESBlocks.TORREYA_LEAVES.get());
		woodSet(ESBlocks.TORREYA_LOG.get(), ESBlocks.TORREYA_WOOD.get(), ESBlocks.TORREYA_PLANKS.get(), ESBlocks.STRIPPED_TORREYA_LOG.get(), ESBlocks.STRIPPED_TORREYA_WOOD.get(), ESBlocks.TORREYA_DOOR.get(), true, ESBlocks.TORREYA_TRAPDOOR.get(), true, ESBlocks.TORREYA_PRESSURE_PLATE.get(), ESBlocks.TORREYA_BUTTON.get(), ESBlocks.TORREYA_FENCE.get(), ESBlocks.TORREYA_FENCE_GATE.get(), ESBlocks.TORREYA_SLAB.get(), ESBlocks.TORREYA_STAIRS.get(), ESBlocks.TORREYA_SIGN.get(), ESBlocks.TORREYA_WALL_SIGN.get(), ESBlocks.TORREYA_HANGING_SIGN.get(), ESBlocks.TORREYA_WALL_HANGING_SIGN.get());
		cross(ESBlocks.TORREYA_SAPLING.get());
		pottedPlant(ESBlocks.POTTED_TORREYA_SAPLING.get(), blockTexture(ESBlocks.TORREYA_SAPLING.get()));
		cross(ESBlocks.TORREYA_VINES.get());
		torreyaVines(ESBlocks.TORREYA_VINES_PLANT.get());
		campfire(ESBlocks.TORREYA_CAMPFIRE.get());

		// stones
		simpleBlock(ESBlocks.GRIMSTONE.get());
		simpleBlock(ESBlocks.CHISELED_GRIMSTONE.get());
		stoneSet(ESBlocks.COBBLED_GRIMSTONE.get(), ESBlocks.COBBLED_GRIMSTONE_SLAB.get(), ESBlocks.COBBLED_GRIMSTONE_STAIRS.get(), ESBlocks.COBBLED_GRIMSTONE_WALL.get());
		stoneSet(ESBlocks.GRIMSTONE_BRICKS.get(), ESBlocks.GRIMSTONE_BRICK_SLAB.get(), ESBlocks.GRIMSTONE_BRICK_STAIRS.get(), ESBlocks.GRIMSTONE_BRICK_WALL.get());
		stoneSet(ESBlocks.POLISHED_GRIMSTONE.get(), ESBlocks.POLISHED_GRIMSTONE_SLAB.get(), ESBlocks.POLISHED_GRIMSTONE_STAIRS.get(), ESBlocks.POLISHED_GRIMSTONE_WALL.get());
		stoneSet(ESBlocks.GRIMSTONE_TILES.get(), ESBlocks.GRIMSTONE_TILE_SLAB.get(), ESBlocks.GRIMSTONE_TILE_STAIRS.get(), ESBlocks.GRIMSTONE_TILE_WALL.get());
		simpleBlock(ESBlocks.GLOWING_GRIMSTONE.get());

		simpleBlock(ESBlocks.VOIDSTONE.get());
		simpleBlock(ESBlocks.CHISELED_VOIDSTONE.get());
		stoneSet(ESBlocks.COBBLED_VOIDSTONE.get(), ESBlocks.COBBLED_VOIDSTONE_SLAB.get(), ESBlocks.COBBLED_VOIDSTONE_STAIRS.get(), ESBlocks.COBBLED_VOIDSTONE_WALL.get());
		stoneSet(ESBlocks.VOIDSTONE_BRICKS.get(), ESBlocks.VOIDSTONE_BRICK_SLAB.get(), ESBlocks.VOIDSTONE_BRICK_STAIRS.get(), ESBlocks.VOIDSTONE_BRICK_WALL.get());
		stoneSet(ESBlocks.POLISHED_VOIDSTONE.get(), ESBlocks.POLISHED_VOIDSTONE_SLAB.get(), ESBlocks.POLISHED_VOIDSTONE_STAIRS.get(), ESBlocks.POLISHED_VOIDSTONE_WALL.get());
		stoneSet(ESBlocks.VOIDSTONE_TILES.get(), ESBlocks.VOIDSTONE_TILE_SLAB.get(), ESBlocks.VOIDSTONE_TILE_STAIRS.get(), ESBlocks.VOIDSTONE_TILE_WALL.get());
		simpleBlock(ESBlocks.GLOWING_VOIDSTONE.get());

		simpleBlock(ESBlocks.ETERNAL_ICE.get());
		stoneSet(ESBlocks.ETERNAL_ICE_BRICKS.get(), ESBlocks.ETERNAL_ICE_BRICK_SLAB.get(), ESBlocks.ETERNAL_ICE_BRICK_STAIRS.get(), ESBlocks.ETERNAL_ICE_BRICK_WALL.get());

		simpleBlock(ESBlocks.NEBULAITE.get());
		stoneSet(ESBlocks.NEBULAITE_BRICKS.get(), ESBlocks.NEBULAITE_BRICK_SLAB.get(), ESBlocks.NEBULAITE_BRICK_STAIRS.get(), ESBlocks.NEBULAITE_BRICK_WALL.get());
		simpleBlock(ESBlocks.CHISELED_NEBULAITE_BRICKS.get());

		stoneSet(ESBlocks.STELLAGMITE.get(), ESBlocks.STELLAGMITE_SLAB.get(), ESBlocks.STELLAGMITE_STAIRS.get(), ESBlocks.STELLAGMITE_WALL.get());
		stoneSet(ESBlocks.MOLTEN_STELLAGMITE.get(), ESBlocks.MOLTEN_STELLAGMITE_SLAB.get(), ESBlocks.MOLTEN_STELLAGMITE_STAIRS.get(), ESBlocks.MOLTEN_STELLAGMITE_WALL.get());
		stoneSet(ESBlocks.POLISHED_STELLAGMITE.get(), ESBlocks.POLISHED_STELLAGMITE_SLAB.get(), ESBlocks.POLISHED_STELLAGMITE_STAIRS.get(), ESBlocks.POLISHED_STELLAGMITE_WALL.get());

		simpleBlock(ESBlocks.ABYSSLATE.get());
		simpleBlock(ESBlocks.CHISELED_POLISHED_ABYSSLATE.get());
		stoneSet(ESBlocks.POLISHED_ABYSSLATE.get(), ESBlocks.POLISHED_ABYSSLATE_SLAB.get(), ESBlocks.POLISHED_ABYSSLATE_STAIRS.get(), ESBlocks.POLISHED_ABYSSLATE_WALL.get());
		stoneSet(ESBlocks.POLISHED_ABYSSLATE_BRICKS.get(), ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_ABYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_ABYSSLATE_BRICK_WALL.get());
		simpleBlock(ESBlocks.ABYSSAL_MAGMA_BLOCK.get());
		geyser(ESBlocks.ABYSSLATE.get(), ESBlocks.ABYSSAL_GEYSER.get());

		simpleBlock(ESBlocks.THERMABYSSLATE.get());
		simpleBlock(ESBlocks.CHISELED_POLISHED_THERMABYSSLATE.get());
		stoneSet(ESBlocks.POLISHED_THERMABYSSLATE.get(), ESBlocks.POLISHED_THERMABYSSLATE_SLAB.get(), ESBlocks.POLISHED_THERMABYSSLATE_STAIRS.get(), ESBlocks.POLISHED_THERMABYSSLATE_WALL.get());
		stoneSet(ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICK_WALL.get());
		simpleBlock(ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get());
		geyser(ESBlocks.THERMABYSSLATE.get(), ESBlocks.THERMABYSSAL_GEYSER.get());

		simpleBlock(ESBlocks.CRYOBYSSLATE.get());
		simpleBlock(ESBlocks.CHISELED_POLISHED_CRYOBYSSLATE.get());
		stoneSet(ESBlocks.POLISHED_CRYOBYSSLATE.get(), ESBlocks.POLISHED_CRYOBYSSLATE_SLAB.get(), ESBlocks.POLISHED_CRYOBYSSLATE_STAIRS.get(), ESBlocks.POLISHED_CRYOBYSSLATE_WALL.get());
		stoneSet(ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_WALL.get());
		simpleBlock(ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get());
		geyser(ESBlocks.CRYOBYSSLATE.get(), ESBlocks.CRYOBYSSAL_GEYSER.get());

		simpleBlock(ESBlocks.THIOQUARTZ_BLOCK.get());
		simpleBlock(ESBlocks.BUDDING_THIOQUARTZ.get());
		crystalCluster(ESBlocks.THIOQUARTZ_CLUSTER.get());
		stoneSet(ESBlocks.TOXITE.get(), ESBlocks.TOXITE_SLAB.get(), ESBlocks.TOXITE_STAIRS.get(), ESBlocks.TOXITE_WALL.get());
		polishedToxite(ESBlocks.POLISHED_TOXITE.get(), ESBlocks.TOXITE.get());
		slabBlock(ESBlocks.POLISHED_TOXITE_SLAB.get(), blockTexture(ESBlocks.POLISHED_TOXITE.get()), blockTexture(ESBlocks.POLISHED_TOXITE.get()));
		stairsBlock(ESBlocks.POLISHED_TOXITE_STAIRS.get(), blockTexture(ESBlocks.POLISHED_TOXITE.get()));
		wallBlock(ESBlocks.POLISHED_TOXITE_WALL.get(), blockTexture(ESBlocks.POLISHED_TOXITE.get()));

		simpleBlock(ESBlocks.NIGHTFALL_MUD.get());
		simpleBlock(ESBlocks.GLOWING_NIGHTFALL_MUD.get());
		simpleBlock(ESBlocks.PACKED_NIGHTFALL_MUD.get());
		stoneSet(ESBlocks.NIGHTFALL_MUD_BRICKS.get(), ESBlocks.NIGHTFALL_MUD_BRICK_SLAB.get(), ESBlocks.NIGHTFALL_MUD_BRICK_STAIRS.get(), ESBlocks.NIGHTFALL_MUD_BRICK_WALL.get());

		simpleBlock(ESBlocks.TWILIGHT_SAND.get());
		sandstoneAndCut(ESBlocks.TWILIGHT_SANDSTONE.get(), ESBlocks.CUT_TWILIGHT_SANDSTONE.get());
		slabBlock(ESBlocks.TWILIGHT_SANDSTONE_SLAB.get(), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
		stairsBlock(ESBlocks.TWILIGHT_SANDSTONE_STAIRS.get(), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
		wallBlock(ESBlocks.TWILIGHT_SANDSTONE_WALL.get(), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"));
		slabBlock(ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get(), blockTexture(ESBlocks.CUT_TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.CUT_TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
		stairsBlock(ESBlocks.CUT_TWILIGHT_SANDSTONE_STAIRS.get(), blockTexture(ESBlocks.CUT_TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
		wallBlock(ESBlocks.CUT_TWILIGHT_SANDSTONE_WALL.get(), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
		simpleBlock(ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get(), models().cubeColumn(name(ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top")));

		simpleBlock(ESBlocks.DUSTED_GRAVEL.get());
		stoneSet(ESBlocks.DUSTED_BRICKS.get(), ESBlocks.DUSTED_BRICK_SLAB.get(), ESBlocks.DUSTED_BRICK_STAIRS.get(), ESBlocks.DUSTED_BRICK_WALL.get());

		simpleBlock(ESBlocks.GOLEM_STEEL_BLOCK.get());
		simpleBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get());
		slabBlock(ESBlocks.GOLEM_STEEL_SLAB.get(), blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get()), blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get()));
		slabBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get()), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get()));
		stairsBlock(ESBlocks.GOLEM_STEEL_STAIRS.get(), blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get()));
		stairsBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get(), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get()));
		simpleBlock(ESBlocks.GOLEM_STEEL_TILES.get());
		simpleBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get());
		slabBlock(ESBlocks.GOLEM_STEEL_TILE_SLAB.get(), blockTexture(ESBlocks.GOLEM_STEEL_TILES.get()), blockTexture(ESBlocks.GOLEM_STEEL_TILES.get()));
		slabBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get()), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get()));
		stairsBlock(ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(), blockTexture(ESBlocks.GOLEM_STEEL_TILES.get()));
		stairsBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get()));
		simpleBlock(ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get());
		simpleBlock(ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get());

		shadegrieve(ESBlocks.SHADEGRIEVE.get());
		shadegrieve(ESBlocks.BLOOMING_SHADEGRIEVE.get());
		particleOnly(ESBlocks.LUNAR_VINE.get(), itemTextureFromBlock(ESBlocks.LUNAR_VINE.get()));
		simpleBlock(ESBlocks.LUNAR_MOSAIC.get());
		slabBlock(ESBlocks.LUNAR_MOSAIC_SLAB.get(), blockTexture(ESBlocks.LUNAR_MOSAIC.get()), blockTexture(ESBlocks.LUNAR_MOSAIC.get()));
		stairsBlock(ESBlocks.LUNAR_MOSAIC_STAIRS.get(), blockTexture(ESBlocks.LUNAR_MOSAIC.get()));
		fenceBlock(ESBlocks.LUNAR_MOSAIC_FENCE.get(), blockTexture(ESBlocks.LUNAR_MOSAIC.get()));
		fenceGateBlock(ESBlocks.LUNAR_MOSAIC_FENCE_GATE.get(), blockTexture(ESBlocks.LUNAR_MOSAIC.get()));
		carpet(ESBlocks.LUNAR_MAT.get(), blockTexture(ESBlocks.LUNAR_MAT.get()));

		// doomeden
		stoneSet(ESBlocks.DOOMEDEN_TILES.get(), ESBlocks.DOOMEDEN_TILE_SLAB.get(), ESBlocks.DOOMEDEN_TILE_STAIRS.get(), ESBlocks.DOOMEDEN_TILE_WALL.get());
		simpleBlock(ESBlocks.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
		simpleBlock(ESBlocks.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
		doomedenTorch(ESBlocks.DOOMED_TORCH.get(), ESBlocks.WALL_DOOMED_TORCH.get());
		doomedenRedstoneTorch(ESBlocks.DOOMED_REDSTONE_TORCH.get(), ESBlocks.WALL_DOOMED_REDSTONE_TORCH.get());
		stoneSet(ESBlocks.DOOMEDEN_BRICKS.get(), ESBlocks.DOOMEDEN_BRICK_SLAB.get(), ESBlocks.DOOMEDEN_BRICK_STAIRS.get(), ESBlocks.DOOMEDEN_BRICK_WALL.get());
		stoneSet(ESBlocks.POLISHED_DOOMEDEN_BRICKS.get(), ESBlocks.POLISHED_DOOMEDEN_BRICK_SLAB.get(), ESBlocks.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), ESBlocks.POLISHED_DOOMEDEN_BRICK_WALL.get());
		onOffBlock(ESBlocks.DOOMEDEN_LIGHT.get());
		doomedenKeyhole(ESBlocks.DOOMEDEN_KEYHOLE.get(), ESBlocks.REDSTONE_DOOMEDEN_KEYHOLE.get());

		cross(ESBlocks.STARLIGHT_FLOWER.get());
		pottedPlant(ESBlocks.POTTED_STARLIGHT_FLOWER.get(), blockTexture(ESBlocks.STARLIGHT_FLOWER.get()));
		cross(ESBlocks.CONEBLOOM.get());
		pottedPlant(ESBlocks.POTTED_CONEBLOOM.get(), blockTexture(ESBlocks.CONEBLOOM.get()));
		cross(ESBlocks.NIGHTFAN.get());
		pottedPlant(ESBlocks.POTTED_NIGHTFAN.get(), blockTexture(ESBlocks.NIGHTFAN.get()));
		cross(ESBlocks.PINK_ROSE.get());
		pottedPlant(ESBlocks.POTTED_PINK_ROSE.get(), blockTexture(ESBlocks.PINK_ROSE.get()));
		cross(ESBlocks.STARLIGHT_TORCHFLOWER.get());
		pottedPlant(ESBlocks.POTTED_STARLIGHT_TORCHFLOWER.get(), blockTexture(ESBlocks.STARLIGHT_TORCHFLOWER.get()));
		doublePlant(ESBlocks.NIGHTFAN_BUSH.get());
		doublePlant(ESBlocks.PINK_ROSE_BUSH.get());
		cross(ESBlocks.NIGHT_SPROUTS.get());
		cross(ESBlocks.SMALL_NIGHT_SPROUTS.get());
		cross(ESBlocks.GLOWING_NIGHT_SPROUTS.get());
		cross(ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS.get());
		cross(ESBlocks.LUNAR_GRASS.get());
		cross(ESBlocks.GLOWING_LUNAR_GRASS.get());
		cross(ESBlocks.CRESCENT_GRASS.get());
		pottedPlant(ESBlocks.POTTED_CRESCENT_GRASS.get(), blockTexture(ESBlocks.CRESCENT_GRASS.get()));
		cross(ESBlocks.GLOWING_CRESCENT_GRASS.get());
		pottedPlant(ESBlocks.POTTED_GLOWING_CRESCENT_GRASS.get(), blockTexture(ESBlocks.GLOWING_CRESCENT_GRASS.get()));
		cross(ESBlocks.PARASOL_GRASS.get());
		pottedPlant(ESBlocks.POTTED_PARASOL_GRASS.get(), blockTexture(ESBlocks.PARASOL_GRASS.get()));
		cross(ESBlocks.GLOWING_PARASOL_GRASS.get());
		pottedPlant(ESBlocks.POTTED_GLOWING_PARASOL_GRASS.get(), blockTexture(ESBlocks.GLOWING_PARASOL_GRASS.get()));
		cross(ESBlocks.LUNAR_BUSH.get());
		cross(ESBlocks.GLOWING_LUNAR_BUSH.get());
		doublePlant(ESBlocks.TALL_CRESCENT_GRASS.get());
		doublePlant(ESBlocks.TALL_GLOWING_CRESCENT_GRASS.get());
		doublePlant(ESBlocks.LUNAR_REED.get());
		cross(ESBlocks.WHISPERBLOOM.get());
		pottedPlant(ESBlocks.POTTED_WHISPERBLOOM.get(), blockTexture(ESBlocks.WHISPERBLOOM.get()));
		cross(ESBlocks.GLADESPIKE.get());
		pottedPlant(ESBlocks.POTTED_GLADESPIKE.get(), blockTexture(ESBlocks.GLADESPIKE.get()));
		cross(ESBlocks.VIVIDSTALK.get());
		pottedPlant(ESBlocks.POTTED_VIVIDSTALK.get(), blockTexture(ESBlocks.VIVIDSTALK.get()));
		doublePlant(ESBlocks.TALL_GLADESPIKE.get());
		cross(ESBlocks.GLOWING_MUSHROOM.get());
		mushroomBlock(ESBlocks.GLOWING_MUSHROOM_BLOCK.get());
		pottedPlant(ESBlocks.POTTED_GLOWING_MUSHROOM.get(), blockTexture(ESBlocks.GLOWING_MUSHROOM.get()));
		mushroomBlock(ESBlocks.GLOWING_MUSHROOM_STEM.get(), blockTexture(ESBlocks.GLOWING_MUSHROOM_BLOCK.get()).withSuffix("_inside"));

		cross(ESBlocks.SWAMP_ROSE.get());
		pottedPlant(ESBlocks.POTTED_SWAMP_ROSE.get(), blockTexture(ESBlocks.SWAMP_ROSE.get()));
		cross(ESBlocks.FANTABUD.get());
		cross(ESBlocks.GREEN_FANTABUD.get());
		cross(ESBlocks.FANTAFERN.get());
		pottedPlant(ESBlocks.POTTED_FANTAFERN.get(), blockTexture(ESBlocks.FANTAFERN.get()));
		cross(ESBlocks.GREEN_FANTAFERN.get());
		pottedPlant(ESBlocks.POTTED_GREEN_FANTAFERN.get(), blockTexture(ESBlocks.GREEN_FANTAFERN.get()));
		cross(ESBlocks.FANTAGRASS.get());
		cross(ESBlocks.GREEN_FANTAGRASS.get());

		cross(ESBlocks.ORANGE_SCARLET_BUD.get());
		cross(ESBlocks.PURPLE_SCARLET_BUD.get());
		cross(ESBlocks.RED_SCARLET_BUD.get());
		cross(ESBlocks.SCARLET_GRASS.get());
		cross(ESBlocks.MAUVE_FERN.get());

		cross(ESBlocks.WITHERED_STARLIGHT_FLOWER.get());
		pottedPlant(ESBlocks.POTTED_WITHERED_STARLIGHT_FLOWER.get(), blockTexture(ESBlocks.WITHERED_STARLIGHT_FLOWER.get()));

		cross(ESBlocks.DEAD_LUNAR_BUSH.get());
		pottedPlant(ESBlocks.POTTED_DEAD_LUNAR_BUSH.get(), blockTexture(ESBlocks.DEAD_LUNAR_BUSH.get()));
		cross(ESBlocks.DESERT_AMETHYSIA.get());
		pottedPlant(ESBlocks.POTTED_DESERT_AMETHYSIA.get(), blockTexture(ESBlocks.DESERT_AMETHYSIA.get()));
		cross(ESBlocks.WITHERED_DESERT_AMETHYSIA.get());
		pottedPlant(ESBlocks.POTTED_WITHERED_DESERT_AMETHYSIA.get(), blockTexture(ESBlocks.WITHERED_DESERT_AMETHYSIA.get()));
		cross(ESBlocks.SUNSET_THORNBLOOM.get());
		pottedPlant(ESBlocks.POTTED_SUNSET_THORNBLOOM.get(), blockTexture(ESBlocks.SUNSET_THORNBLOOM.get()));
		cross(ESBlocks.AMETHYSIA_GRASS.get());
		lunarisCactus(ESBlocks.LUNARIS_CACTUS.get());
		translucentCubeAll(ESBlocks.LUNARIS_CACTUS_GEL_BLOCK.get());

		waterlily(ESBlocks.MOONLIGHT_LILY_PAD.get());
		waterlily(ESBlocks.STARLIT_LILY_PAD.get());
		waterlily(ESBlocks.MOONLIGHT_DUCKWEED.get());

		cross(ESBlocks.CRYSTALLIZED_LUNAR_GRASS.get());
		cross(ESBlocks.RED_CRYSTAL_ROOTS.get());
		cross(ESBlocks.BLUE_CRYSTAL_ROOTS.get());
		doublePlant(ESBlocks.TWILVEWRYM_HERB.get());
		doublePlant(ESBlocks.STELLAFLY_BUSH.get());
		doublePlant(ESBlocks.GLIMMERFLY_BUSH.get());

		simpleBlock(ESBlocks.NIGHTFALL_DIRT.get());
		grassBlock(ESBlocks.NIGHTFALL_GRASS_BLOCK.get(), blockTexture(ESBlocks.NIGHTFALL_DIRT.get()));
		simpleGrassBlock(ESBlocks.FANTASY_GRASS_BLOCK.get(), blockTexture(ESBlocks.NIGHTFALL_MUD.get()));
		carpet(ESBlocks.FANTASY_GRASS_CARPET.get(), blockTexture(ESBlocks.FANTASY_GRASS_BLOCK.get()).withSuffix("_top"));

		tintedCubeAll(ESBlocks.WHITE_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.ORANGE_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.MAGENTA_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.LIGHT_BLUE_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.YELLOW_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.LIME_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.PINK_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.GRAY_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.LIGHT_GRAY_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.CYAN_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.PURPLE_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.BLUE_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.BROWN_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.GREEN_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.RED_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);
		tintedCubeAll(ESBlocks.BLACK_YETI_FUR.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"), SOLID);

		tintedCarpet(ESBlocks.WHITE_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.ORANGE_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.MAGENTA_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.LIGHT_BLUE_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.YELLOW_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.LIME_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.PINK_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.GRAY_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.LIGHT_GRAY_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.CYAN_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.PURPLE_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.BLUE_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.BROWN_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.GREEN_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.RED_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));
		tintedCarpet(ESBlocks.BLACK_YETI_FUR_CARPET.get(), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/yeti_fur"));

		particleOnly(ESBlocks.TANGLED_SKULL.get(), blockTexture(Blocks.SOUL_SAND));
		particleOnly(ESBlocks.TANGLED_WALL_SKULL.get(), blockTexture(Blocks.SOUL_SAND));

		simpleBlock(ESBlocks.RAW_AETHERSENT_BLOCK.get());
		simpleBlock(ESBlocks.AETHERSENT_BLOCK.get());
		simpleBlock(ESBlocks.SPRINGSTONE.get());
		simpleBlock(ESBlocks.THERMAL_SPRINGSTONE.get());
		simpleBlock(ESBlocks.GLACITE.get());
		simpleBlock(ESBlocks.SWAMP_SILVER_ORE.get());
		simpleBlock(ESBlocks.SWAMP_SILVER_BLOCK.get());
		simpleBlock(ESBlocks.GRIMSTONE_REDSTONE_ORE.get());
		simpleBlock(ESBlocks.VOIDSTONE_REDSTONE_ORE.get());
		simpleBlock(ESBlocks.GRIMSTONE_SALTPETER_ORE.get());
		simpleBlock(ESBlocks.VOIDSTONE_SALTPETER_ORE.get());
		simpleBlock(ESBlocks.SALTPETER_BLOCK.get());

		lantern(ESBlocks.AMARAMBER_LANTERN.get());
		candle(ESBlocks.AMARAMBER_CANDLE.get());

		farmland(ESBlocks.NIGHTFALL_FARMLAND.get(), ESBlocks.NIGHTFALL_DIRT.get());

		stellarRack(ESBlocks.STELLAR_RACK.get());
		horizontalBlock(ESBlocks.ENCHANTED_GRIMSTONE_BRICKS.get(), blockTexture(ESBlocks.GRIMSTONE_BRICKS.get()), blockTexture(ESBlocks.ENCHANTED_GRIMSTONE_BRICKS.get()), blockTexture(ESBlocks.POLISHED_GRIMSTONE.get()));
		particleOnly(ESBlocks.ETHER.get());
		onOffBlock(ESBlocks.ENERGY_BLOCK.get());
		spawner(ESBlocks.THE_GATEKEEPER_SPAWNER.get());
		spawner(ESBlocks.STARLIGHT_GOLEM_SPAWNER.get());
		spawner(ESBlocks.TANGLED_HATRED_SPAWNER.get());
		spawner(ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get());
		portal(ESBlocks.STARLIGHT_PORTAL.get());
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

	private void stellarRack(Block block) {
		ModelFile modelFile = models().withExistingParent(name(block), ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/block"))
			.texture("particle", blockTexture(block).withSuffix("_bottom"))
			.texture("bottom", blockTexture(block).withSuffix("_bottom"))
			.texture("top", blockTexture(block).withSuffix("_top"))
			.texture("side", blockTexture(block).withSuffix("_side"))
			.element().from(0, 0, 0).to(16, 12, 16)
			.face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#bottom").cullface(Direction.DOWN).end()
			.face(Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
			.face(Direction.NORTH).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.NORTH).end()
			.face(Direction.SOUTH).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.SOUTH).end()
			.face(Direction.WEST).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.WEST).end()
			.face(Direction.EAST).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.EAST).end()
			.end();
		simpleBlock(block, modelFile);
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

	private void polishedToxite(Block block, Block stone) {
		getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(PolishedToxiteBlock.PART) == PolishedToxiteBlock.Part.MIDDLE
			? cubeAll(block) : (state.getValue(PolishedToxiteBlock.PART) == PolishedToxiteBlock.Part.UPPER
			? models().cubeBottomTop(name(block) + "_upper", blockTexture(block).withSuffix("_upper"), blockTexture(block), blockTexture(stone)) : models().cubeBottomTop(name(block) + "_lower", blockTexture(block).withSuffix("_lower"), blockTexture(stone), blockTexture(block)))).build());
	}

	private void orbflora(Block block) {
		ModelFile modelAge0 = models().getExistingFile(EternalStarlight.id("orbflora_age_0"));
		ModelFile modelAge1 = models().getExistingFile(EternalStarlight.id("orbflora_age_1"));
		ModelFile modelAge2 = models().getExistingFile(EternalStarlight.id("orbflora"));
		getVariantBuilder(block)
			.partialState().with(OrbfloraBlock.ORBFLORA_AGE, 0)
			.modelForState().modelFile(modelAge0).addModel()
			.partialState().with(OrbfloraBlock.ORBFLORA_AGE, 1)
			.modelForState().modelFile(modelAge1).addModel()
			.partialState().with(OrbfloraBlock.ORBFLORA_AGE, 2)
			.modelForState().modelFile(modelAge2).addModel();
	}

	private void lunarisCactus(Block block) {
		ModelFile modelNormal = models().getExistingFile(EternalStarlight.id("lunaris_cactus"));
		ModelFile modelFruit = models().getExistingFile(EternalStarlight.id("lunaris_cactus_fruit"));
		getVariantBuilder(block)
			.partialState().with(LunarisCactusBlock.FRUIT, false)
			.modelForState().modelFile(modelNormal).addModel()
			.partialState().with(LunarisCactusBlock.FRUIT, true)
			.modelForState().modelFile(modelFruit).addModel();
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
		ModelFile modelOn = models().orientable(name(block) + "_lit", blockTexture(block).withSuffix("_on_side"), blockTexture(block).withSuffix("_on_front"), blockTexture(ESBlocks.DOOMEDEN_TILES.get()));
		ModelFile modelOff = models().orientable(name(block), blockTexture(block).withSuffix("_off_side"), blockTexture(block).withSuffix("_off_front"), blockTexture(ESBlocks.DOOMEDEN_TILES.get()));
		horizontalBlock(block, state -> state.getValue(BlockStateProperties.LIT) ? modelOn : modelOff);

		ModelFile modelOnRedstone = models().orientable(name(redstone) + "_lit", blockTexture(block).withSuffix("_on_side"), blockTexture(redstone).withSuffix("_on"), blockTexture(ESBlocks.DOOMEDEN_TILES.get()));
		ModelFile modelOffRedstone = models().orientable(name(redstone), blockTexture(block).withSuffix("_off_side"), blockTexture(redstone).withSuffix("_off"), blockTexture(ESBlocks.DOOMEDEN_TILES.get()));
		horizontalBlock(redstone, state -> state.getValue(BlockStateProperties.LIT) ? modelOnRedstone : modelOffRedstone);
	}

	private void doomedenTorch(Block normal, Block wall) {
		ModelFile modelNormal = models().singleTexture(name(normal), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/template_doomeden_torch"), "torch", blockTexture(normal)).renderType(CUTOUT);
		ModelFile modelWall = models().singleTexture(name(wall), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/template_doomeden_torch_wall"), "torch", blockTexture(normal)).renderType(CUTOUT);
		simpleBlock(normal, modelNormal);
		horizontalBlock(wall, modelWall, 90);
	}

	private void doomedenRedstoneTorch(Block normal, Block wall) {
		ModelFile modelNormal = models().singleTexture(name(normal) + "_lit", EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/template_doomeden_torch"), "torch", blockTexture(normal)).renderType(CUTOUT);
		ModelFile modelNormalOff = models().singleTexture(name(normal), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/template_doomeden_torch"), "torch", blockTexture(normal).withSuffix("_off")).renderType(CUTOUT);
		ModelFile modelWall = models().singleTexture(name(wall) + "_lit", EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/template_doomeden_torch_wall"), "torch", blockTexture(normal)).renderType(CUTOUT);
		ModelFile modelWallOff = models().singleTexture(name(wall), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/template_doomeden_torch_wall"), "torch", blockTexture(normal).withSuffix("_off")).renderType(CUTOUT);
		onOffBlock(normal, BlockStateProperties.LIT, modelNormal, modelNormalOff);
		horizontalBlock(wall, state -> state.getValue(BlockStateProperties.LIT) ? modelWall : modelWallOff, 90);
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

	private void multifaceBlock(Block block) {
		ModelFile model = models().getBuilder(name(block))
			.ao(false)
			.texture("particle", blockTexture(block))
			.texture("texture", blockTexture(block))
			.renderType(TRANSLUCENT)
			.element()
			.from(0, 0, 0.1f)
			.to(16, 16, 0.1f)
			.face(Direction.NORTH)
			.uvs(16, 0, 0, 16)
			.texture("#texture")
			.tintindex(0)
			.end()
			.face(Direction.SOUTH)
			.uvs(0, 0, 16, 16)
			.texture("#texture")
			.tintindex(0)
			.end()
			.end();
		getMultipartBuilder(block)
			.part().modelFile(model).addModel().condition(BlockStateProperties.NORTH, true).end()
			.part().modelFile(model).addModel()
			.condition(BlockStateProperties.DOWN, false)
			.condition(BlockStateProperties.EAST, false)
			.condition(BlockStateProperties.NORTH, false)
			.condition(BlockStateProperties.SOUTH, false)
			.condition(BlockStateProperties.UP, false)
			.condition(BlockStateProperties.WEST, false).end()
			.part().modelFile(model).uvLock(true).rotationY(90).addModel().condition(BlockStateProperties.EAST, true).end()
			.part().modelFile(model).uvLock(true).rotationY(90).addModel()
			.condition(BlockStateProperties.DOWN, false)
			.condition(BlockStateProperties.EAST, false)
			.condition(BlockStateProperties.NORTH, false)
			.condition(BlockStateProperties.SOUTH, false)
			.condition(BlockStateProperties.UP, false)
			.condition(BlockStateProperties.WEST, false).end()
			.part().modelFile(model).uvLock(true).rotationY(180).addModel().condition(BlockStateProperties.SOUTH, true).end()
			.part().modelFile(model).uvLock(true).rotationY(180).addModel()
			.condition(BlockStateProperties.DOWN, false)
			.condition(BlockStateProperties.EAST, false)
			.condition(BlockStateProperties.NORTH, false)
			.condition(BlockStateProperties.SOUTH, false)
			.condition(BlockStateProperties.UP, false)
			.condition(BlockStateProperties.WEST, false).end()
			.part().modelFile(model).uvLock(true).rotationY(270).addModel().condition(BlockStateProperties.WEST, true).end()
			.part().modelFile(model).uvLock(true).rotationY(270).addModel()
			.condition(BlockStateProperties.DOWN, false)
			.condition(BlockStateProperties.EAST, false)
			.condition(BlockStateProperties.NORTH, false)
			.condition(BlockStateProperties.SOUTH, false)
			.condition(BlockStateProperties.UP, false)
			.condition(BlockStateProperties.WEST, false).end()
			.part().modelFile(model).uvLock(true).rotationX(270).addModel().condition(BlockStateProperties.UP, true).end()
			.part().modelFile(model).uvLock(true).rotationX(270).addModel()
			.condition(BlockStateProperties.DOWN, false)
			.condition(BlockStateProperties.EAST, false)
			.condition(BlockStateProperties.NORTH, false)
			.condition(BlockStateProperties.SOUTH, false)
			.condition(BlockStateProperties.UP, false)
			.condition(BlockStateProperties.WEST, false).end()
			.part().modelFile(model).uvLock(true).rotationX(90).addModel().condition(BlockStateProperties.DOWN, true).end()
			.part().modelFile(model).uvLock(true).rotationX(90).addModel()
			.condition(BlockStateProperties.DOWN, false)
			.condition(BlockStateProperties.EAST, false)
			.condition(BlockStateProperties.NORTH, false)
			.condition(BlockStateProperties.SOUTH, false)
			.condition(BlockStateProperties.UP, false)
			.condition(BlockStateProperties.WEST, false).end();
	}

	private void mushroomBlock(Block block) {
		mushroomBlock(block, blockTexture(block).withSuffix("_inside"));
	}

	private void mushroomBlock(Block block, ResourceLocation inner) {
		ModelFile modelOutside = models().singleTexture(name(block), ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/template_single_face"), blockTexture(block));
		ModelFile modelInside = models().singleTexture(name(block) + "_inside", ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/template_single_face"), inner);
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

	private void campfire(Block block) {
		campfire(block, blockTexture(block).withSuffix("_log"), blockTexture(block).withSuffix("_log_lit"), blockTexture(block).withSuffix("_fire"));
	}

	private void campfire(Block block, ResourceLocation log, ResourceLocation litLog, ResourceLocation fire) {
		ModelFile modelNormal = models().withExistingParent(name(block) + "_off", ResourceLocation.withDefaultNamespace("campfire_off")).texture("log", log).texture("particle", log).renderType(CUTOUT);
		ModelFile modelLit = models().withExistingParent(name(block), ResourceLocation.withDefaultNamespace("template_campfire")).texture("log", log).texture("lit_log", litLog).texture("fire", fire).renderType(CUTOUT);
		getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.LIT) ? modelLit : modelNormal).rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()).build());
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
		ModelFile modelNormal = models().withExistingParent(name(grassBlock), ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/block")).renderType(CUTOUT_MIPPED)
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

	private void farmland(Block farmland, Block dirt) {
		ModelFile normal = models().withExistingParent(name(farmland), "template_farmland")
			.texture("dirt", blockTexture(dirt))
			.texture("top", blockTexture(farmland));
		ModelFile moist = models().withExistingParent(name(farmland) + "_moist", "template_farmland")
			.texture("dirt", blockTexture(dirt))
			.texture("top", blockTexture(farmland).withSuffix("_moist"));
		getVariantBuilder(farmland).forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.MOISTURE) < 7 ? normal : moist).build());
	}

	private void lantern(Block lantern) {
		ModelFile normal = models().singleTexture(name(lantern), ResourceLocation.withDefaultNamespace("template_lantern"), "lantern", blockTexture(lantern)).renderType(CUTOUT);
		ModelFile hanging = models().singleTexture(name(lantern) + "_hanging", ResourceLocation.withDefaultNamespace("template_hanging_lantern"), "lantern", blockTexture(lantern)).renderType(CUTOUT);
		getVariantBuilder(lantern)
			.partialState().with(BlockStateProperties.HANGING, false)
			.modelForState().modelFile(normal).addModel()
			.partialState().with(BlockStateProperties.HANGING, true)
			.modelForState().modelFile(hanging).addModel();
	}

	private void candle(Block candle) {
		ModelFile one = models().withExistingParent(name(candle), "template_candle")
			.texture("all", blockTexture(candle))
			.texture("particle", blockTexture(candle));
		ModelFile oneLit = models().withExistingParent(name(candle) + "_lit", "template_candle")
			.texture("all", blockTexture(candle).withSuffix("_lit"))
			.texture("particle", blockTexture(candle).withSuffix("_lit"));
		ModelFile two = models().withExistingParent(name(candle) + "_two", "template_two_candles")
			.texture("all", blockTexture(candle))
			.texture("particle", blockTexture(candle));
		ModelFile twoLit = models().withExistingParent(name(candle) + "_two_lit", "template_two_candles")
			.texture("all", blockTexture(candle).withSuffix("_lit"))
			.texture("particle", blockTexture(candle).withSuffix("_lit"));
		ModelFile three = models().withExistingParent(name(candle) + "_three", "template_three_candles")
			.texture("all", blockTexture(candle))
			.texture("particle", blockTexture(candle));
		ModelFile threeLit = models().withExistingParent(name(candle) + "_three_lit", "template_three_candles")
			.texture("all", blockTexture(candle).withSuffix("_lit"))
			.texture("particle", blockTexture(candle).withSuffix("_lit"));
		ModelFile four = models().withExistingParent(name(candle) + "_four", "template_four_candles")
			.texture("all", blockTexture(candle))
			.texture("particle", blockTexture(candle));
		ModelFile fourLit = models().withExistingParent(name(candle) + "_four_lit", "template_four_candles")
			.texture("all", blockTexture(candle).withSuffix("_lit"))
			.texture("particle", blockTexture(candle).withSuffix("_lit"));
		getVariantBuilder(candle)
			.partialState().with(BlockStateProperties.CANDLES, 1).with(BlockStateProperties.LIT, false)
			.modelForState().modelFile(one).addModel()
			.partialState().with(BlockStateProperties.CANDLES, 1).with(BlockStateProperties.LIT, true)
			.modelForState().modelFile(oneLit).addModel()
			.partialState().with(BlockStateProperties.CANDLES, 2).with(BlockStateProperties.LIT, false)
			.modelForState().modelFile(two).addModel()
			.partialState().with(BlockStateProperties.CANDLES, 2).with(BlockStateProperties.LIT, true)
			.modelForState().modelFile(twoLit).addModel()
			.partialState().with(BlockStateProperties.CANDLES, 3).with(BlockStateProperties.LIT, false)
			.modelForState().modelFile(three).addModel()
			.partialState().with(BlockStateProperties.CANDLES, 3).with(BlockStateProperties.LIT, true)
			.modelForState().modelFile(threeLit).addModel()
			.partialState().with(BlockStateProperties.CANDLES, 4).with(BlockStateProperties.LIT, false)
			.modelForState().modelFile(four).addModel()
			.partialState().with(BlockStateProperties.CANDLES, 4).with(BlockStateProperties.LIT, true)
			.modelForState().modelFile(fourLit).addModel();
	}

	private void sandstoneAndCut(Block sandstone, Block cut) {
		ModelFile modelFile = models().cubeBottomTop(name(sandstone), blockTexture(sandstone), blockTexture(sandstone).withSuffix("_bottom"), blockTexture(sandstone).withSuffix("_top"));
		ModelFile modelCut = models().cubeColumn(name(cut), blockTexture(cut), blockTexture(sandstone).withSuffix("_top"));
		simpleBlock(sandstone, modelFile);
		simpleBlock(cut, modelCut);
	}

	private void coralFan(Block fan) {
		ModelFile modelFile = models().singleTexture(name(fan), ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/coral_fan"), "fan", blockTexture(fan)).renderType(CUTOUT);
		simpleBlock(fan, modelFile);
	}

	private void coralWallFan(Block wall, Block fan) {
		ModelFile modelFile = models().singleTexture(name(wall), ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/coral_wall_fan"), "fan", blockTexture(fan)).renderType(CUTOUT);
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

	private void snowyLeaves(Block leaves) {
		ModelFile modelNormal = models().singleTexture(name(leaves), ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/leaves"), "all", blockTexture(leaves)).renderType(CUTOUT_MIPPED);
		ModelFile modelSnowy = models().cubeBottomTop(name(leaves) + "_snowy", blockTexture(leaves).withSuffix("_snowy"), blockTexture(leaves), blockTexture(leaves)).renderType(CUTOUT_MIPPED);
		onOffBlock(leaves, BlockStateProperties.SNOWY, modelSnowy, modelNormal);
	}

	private void shadegrieve(Block block) {
		ModelFile modelNormal = cubeAll(block);
		ModelFile modelTop = models().withExistingParent(name(block) + "_top", EternalStarlight.id("template_top_shadegrieve")).renderType(CUTOUT)
			.texture("cube", blockTexture(block)).texture("particle", blockTexture(block)).texture("upper", blockTexture(block).withSuffix("_upper"));
		onOffBlock(block, ShadegrieveBlock.TOP, modelTop, modelNormal);
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
		ModelFile modelFile = models().singleTexture(name(leaves), ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/leaves"), "all", blockTexture(leaves)).renderType(CUTOUT_MIPPED);
		simpleBlock(leaves, modelFile);
	}

	private void layered(Block layered, ResourceLocation texture) {
		getVariantBuilder(layered).forAllStates((state -> {
			int height = state.getValue(BlockStateProperties.LAYERS) * 2;
			ModelFile modelFile = height < 16 ? models().withExistingParent(name(layered) + "_height" + height, ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/thin_block"))
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
		ModelFile modelFile = models().singleTexture(name(potted), ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/flower_pot_cross"), "plant", location).renderType(CUTOUT);
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

	private void translucentCubeAll(Block block) {
		simpleBlock(block, models().cubeAll(name(block), blockTexture(block)).renderType(TRANSLUCENT));
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

	public ResourceLocation itemTextureFromBlock(Block block) {
		ResourceLocation name = key(block);
		return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + name.getPath());
	}

	private ResourceLocation key(Block block) {
		return BuiltInRegistries.BLOCK.getKey(block);
	}

	private String name(Block block) {
		return key(block).getPath();
	}
}
