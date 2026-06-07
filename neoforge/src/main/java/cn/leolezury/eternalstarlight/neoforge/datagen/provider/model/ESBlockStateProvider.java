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
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.IntUnaryOperator;

public class ESBlockStateProvider extends BlockStateProvider {
	// render types
	private static final ResourceLocation SOLID = ResourceLocation.withDefaultNamespace("solid");
	private static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace("cutout");
	private static final ResourceLocation CUTOUT_MIPPED = ResourceLocation.withDefaultNamespace("cutout_mipped");
	private static final ResourceLocation TRANSLUCENT = ResourceLocation.withDefaultNamespace("translucent");

	public ESBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, EternalStarlight.ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		// woods
		leaves(ESBlocks.LUNAR_LEAVES.get());
		leaves(ESBlocks.CYAN_LUNAR_LEAVES.get());
		leaves(ESBlocks.PURPLE_LUNAR_LEAVES.get());
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

		leaves(ESBlocks.BANYIN_LEAVES.get());
		woodSet(ESBlocks.BANYIN_LOG.get(), ESBlocks.BANYIN_WOOD.get(), ESBlocks.BANYIN_PLANKS.get(), ESBlocks.STRIPPED_BANYIN_LOG.get(), ESBlocks.STRIPPED_BANYIN_WOOD.get(), ESBlocks.BANYIN_DOOR.get(), true, ESBlocks.BANYIN_TRAPDOOR.get(), true, ESBlocks.BANYIN_PRESSURE_PLATE.get(), ESBlocks.BANYIN_BUTTON.get(), ESBlocks.BANYIN_FENCE.get(), ESBlocks.BANYIN_FENCE_GATE.get(), ESBlocks.BANYIN_SLAB.get(), ESBlocks.BANYIN_STAIRS.get(), ESBlocks.BANYIN_SIGN.get(), ESBlocks.BANYIN_WALL_SIGN.get(), ESBlocks.BANYIN_HANGING_SIGN.get(), ESBlocks.BANYIN_WALL_HANGING_SIGN.get());
		cross(ESBlocks.BANYIN_SAPLING.get());
		pottedPlant(ESBlocks.POTTED_BANYIN_SAPLING.get(), blockTexture(ESBlocks.BANYIN_SAPLING.get()));
		mangroveRoots(ESBlocks.BANYIN_ROOTS.get());
		muddyMangroveRoots(ESBlocks.MUDDY_BANYIN_ROOTS.get());

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
		torreyaCampfire(ESBlocks.TORREYA_CAMPFIRE.get());

		cross(ESBlocks.HANGING_ALGALEAVES.get());
		cross(ESBlocks.HANGING_ALGALEAVES_PLANT.get());
		multifaceBlock(ESBlocks.ALGALEAVES.get());
		woodSet(ESBlocks.JINGLESTEM_LOG.get(), ESBlocks.JINGLESTEM_WOOD.get(), ESBlocks.JINGLESTEM_PLANKS.get(), ESBlocks.STRIPPED_JINGLESTEM_LOG.get(), ESBlocks.STRIPPED_JINGLESTEM_WOOD.get(), ESBlocks.JINGLESTEM_DOOR.get(), true, ESBlocks.JINGLESTEM_TRAPDOOR.get(), true, ESBlocks.JINGLESTEM_PRESSURE_PLATE.get(), ESBlocks.JINGLESTEM_BUTTON.get(), ESBlocks.JINGLESTEM_FENCE.get(), ESBlocks.JINGLESTEM_FENCE_GATE.get(), ESBlocks.JINGLESTEM_SLAB.get(), ESBlocks.JINGLESTEM_STAIRS.get(), ESBlocks.JINGLESTEM_SIGN.get(), ESBlocks.JINGLESTEM_WALL_SIGN.get(), ESBlocks.JINGLESTEM_HANGING_SIGN.get(), ESBlocks.JINGLESTEM_WALL_HANGING_SIGN.get());
		cross(ESBlocks.JINGLESTEM_SAPLING.get());
		pottedPlant(ESBlocks.POTTED_JINGLESTEM_SAPLING.get(), blockTexture(ESBlocks.JINGLESTEM_SAPLING.get()));

		leaves(ESBlocks.CRADLEWOOD_LEAVES.get());
		woodSet(ESBlocks.CRADLEWOOD_LOG.get(), ESBlocks.CRADLEWOOD_WOOD.get(), ESBlocks.CRADLEWOOD_PLANKS.get(), ESBlocks.STRIPPED_CRADLEWOOD_LOG.get(), ESBlocks.STRIPPED_CRADLEWOOD_WOOD.get(), ESBlocks.CRADLEWOOD_DOOR.get(), true, ESBlocks.CRADLEWOOD_TRAPDOOR.get(), true, ESBlocks.CRADLEWOOD_PRESSURE_PLATE.get(), ESBlocks.CRADLEWOOD_BUTTON.get(), ESBlocks.CRADLEWOOD_FENCE.get(), ESBlocks.CRADLEWOOD_FENCE_GATE.get(), ESBlocks.CRADLEWOOD_SLAB.get(), ESBlocks.CRADLEWOOD_STAIRS.get(), ESBlocks.CRADLEWOOD_SIGN.get(), ESBlocks.CRADLEWOOD_WALL_SIGN.get(), ESBlocks.CRADLEWOOD_HANGING_SIGN.get(), ESBlocks.CRADLEWOOD_WALL_HANGING_SIGN.get());
		cross(ESBlocks.CRADLEWOOD_SAPLING.get());
		pottedPlant(ESBlocks.POTTED_CRADLEWOOD_SAPLING.get(), key(ESBlocks.CRADLEWOOD_SAPLING.get()).withPrefix("block/potted_"));

		// stones
		randomlyMirroredBlock(ESBlocks.GRIMSTONE.get());
		slabBlock(ESBlocks.GRIMSTONE_SLAB.get(), blockTexture(ESBlocks.GRIMSTONE.get()), blockTexture(ESBlocks.GRIMSTONE.get()));
		stairsBlock(ESBlocks.GRIMSTONE_STAIRS.get(), blockTexture(ESBlocks.GRIMSTONE.get()));
		wallBlock(ESBlocks.GRIMSTONE_WALL.get(), blockTexture(ESBlocks.GRIMSTONE.get()));
		simpleBlock(ESBlocks.CHISELED_GRIMSTONE.get());
		stoneSet(ESBlocks.COBBLED_GRIMSTONE.get(), ESBlocks.COBBLED_GRIMSTONE_SLAB.get(), ESBlocks.COBBLED_GRIMSTONE_STAIRS.get(), ESBlocks.COBBLED_GRIMSTONE_WALL.get());
		stoneSet(ESBlocks.GRIMSTONE_BRICKS.get(), ESBlocks.GRIMSTONE_BRICK_SLAB.get(), ESBlocks.GRIMSTONE_BRICK_STAIRS.get(), ESBlocks.GRIMSTONE_BRICK_WALL.get());
		simpleBlock(ESBlocks.CRACKED_GRIMSTONE_BRICKS.get());
		stoneSet(ESBlocks.POLISHED_GRIMSTONE.get(), ESBlocks.POLISHED_GRIMSTONE_SLAB.get(), ESBlocks.POLISHED_GRIMSTONE_STAIRS.get(), ESBlocks.POLISHED_GRIMSTONE_WALL.get());
		stoneSet(ESBlocks.GRIMSTONE_TILES.get(), ESBlocks.GRIMSTONE_TILE_SLAB.get(), ESBlocks.GRIMSTONE_TILE_STAIRS.get(), ESBlocks.GRIMSTONE_TILE_WALL.get());
		simpleBlock(ESBlocks.CRACKED_GRIMSTONE_TILES.get());
		stoneSet(ESBlocks.POLISHED_GRIMSTONE_TILES.get(), ESBlocks.POLISHED_GRIMSTONE_TILE_SLAB.get(), ESBlocks.POLISHED_GRIMSTONE_TILE_STAIRS.get(), ESBlocks.POLISHED_GRIMSTONE_TILE_WALL.get());
		simpleBlock(ESBlocks.GLOWING_GRIMSTONE.get());

		randomlyMirroredAndRotatedBlock(ESBlocks.VOIDSTONE.get());
		slabBlock(ESBlocks.VOIDSTONE_SLAB.get(), blockTexture(ESBlocks.VOIDSTONE.get()), blockTexture(ESBlocks.VOIDSTONE.get()));
		stairsBlock(ESBlocks.VOIDSTONE_STAIRS.get(), blockTexture(ESBlocks.VOIDSTONE.get()));
		wallBlock(ESBlocks.VOIDSTONE_WALL.get(), blockTexture(ESBlocks.VOIDSTONE.get()));
		simpleBlock(ESBlocks.CHISELED_VOIDSTONE.get());
		stoneSet(ESBlocks.COBBLED_VOIDSTONE.get(), ESBlocks.COBBLED_VOIDSTONE_SLAB.get(), ESBlocks.COBBLED_VOIDSTONE_STAIRS.get(), ESBlocks.COBBLED_VOIDSTONE_WALL.get());
		stoneSet(ESBlocks.VOIDSTONE_BRICKS.get(), ESBlocks.VOIDSTONE_BRICK_SLAB.get(), ESBlocks.VOIDSTONE_BRICK_STAIRS.get(), ESBlocks.VOIDSTONE_BRICK_WALL.get());
		simpleBlock(ESBlocks.CRACKED_VOIDSTONE_BRICKS.get());
		stoneSet(ESBlocks.POLISHED_VOIDSTONE.get(), ESBlocks.POLISHED_VOIDSTONE_SLAB.get(), ESBlocks.POLISHED_VOIDSTONE_STAIRS.get(), ESBlocks.POLISHED_VOIDSTONE_WALL.get());
		stoneSet(ESBlocks.VOIDSTONE_TILES.get(), ESBlocks.VOIDSTONE_TILE_SLAB.get(), ESBlocks.VOIDSTONE_TILE_STAIRS.get(), ESBlocks.VOIDSTONE_TILE_WALL.get());
		simpleBlock(ESBlocks.CRACKED_VOIDSTONE_TILES.get());
		simpleBlock(ESBlocks.GLOWING_VOIDSTONE.get());
		speleothem(ESBlocks.VOIDSTONE_SPIKE.get());

		simpleBlock(ESBlocks.ETERNAL_ICE.get());
		simpleBlock(ESBlocks.THIN_ETERNAL_ICE.get(), models().cubeAll(name(ESBlocks.THIN_ETERNAL_ICE.get()), blockTexture(ESBlocks.THIN_ETERNAL_ICE.get())).renderType(TRANSLUCENT));
		stoneSet(ESBlocks.ETERNAL_ICE_BRICKS.get(), ESBlocks.ETERNAL_ICE_BRICK_SLAB.get(), ESBlocks.ETERNAL_ICE_BRICK_STAIRS.get(), ESBlocks.ETERNAL_ICE_BRICK_WALL.get());
		onOffBlock(ESBlocks.ETERNAL_ICE_LANTERN.get(), BlockStateProperties.HANGING, models().getExistingFile(EternalStarlight.id("eternal_ice_lantern_hanging")), models().getExistingFile(EternalStarlight.id("eternal_ice_lantern")));
		simpleBlock(ESBlocks.HAZE_ICE.get());
		stoneSet(ESBlocks.HAZE_ICE_BRICKS.get(), ESBlocks.HAZE_ICE_BRICK_SLAB.get(), ESBlocks.HAZE_ICE_BRICK_STAIRS.get(), ESBlocks.HAZE_ICE_BRICK_WALL.get());
		onOffBlock(ESBlocks.HAZE_ICE_LANTERN.get(), BlockStateProperties.HANGING, models().getExistingFile(EternalStarlight.id("haze_ice_lantern_hanging")), models().getExistingFile(EternalStarlight.id("haze_ice_lantern")));
		simpleBlock(ESBlocks.REINFORCED_ICE.get(), models().cubeAll(name(ESBlocks.REINFORCED_ICE.get()), blockTexture(ESBlocks.REINFORCED_ICE.get())).renderType(TRANSLUCENT));
		paneBlockWithRenderType(ESBlocks.REINFORCED_ICE_PANE.get(), blockTexture(ESBlocks.REINFORCED_ICE.get()), blockTexture(ESBlocks.REINFORCED_ICE_PANE.get()).withSuffix("_top"), TRANSLUCENT);
		icicle(ESBlocks.ICICLE.get());
		layered(ESBlocks.ASHEN_SNOW.get(), blockTexture(ESBlocks.ASHEN_SNOW.get()));

		simpleBlock(ESBlocks.NEBULAITE.get());
		stoneSet(ESBlocks.NEBULAITE_BRICKS.get(), ESBlocks.NEBULAITE_BRICK_SLAB.get(), ESBlocks.NEBULAITE_BRICK_STAIRS.get(), ESBlocks.NEBULAITE_BRICK_WALL.get());
		simpleBlock(ESBlocks.CHISELED_NEBULAITE_BRICKS.get());

		simpleBlock(ESBlocks.STARCORE_BLOCK.get());
		simpleBlock(ESBlocks.BLAZING_STARCORE_BLOCK.get());
		simpleBlock(ESBlocks.STARCORE_LIGHT.get());
		simpleBlock(ESBlocks.GRIMSTONE_STARCORE_ORE.get());
		simpleBlock(ESBlocks.VOIDSTONE_STARCORE_ORE.get());
		simpleBlock(ESBlocks.ETERNAL_ICE_STARCORE_ORE.get());
		simpleBlock(ESBlocks.HAZE_ICE_STARCORE_ORE.get());
		stoneSet(ESBlocks.RADIANITE.get(), ESBlocks.RADIANITE_SLAB.get(), ESBlocks.RADIANITE_STAIRS.get(), ESBlocks.RADIANITE_WALL.get());
		stoneSet(ESBlocks.COBBLED_RADIANITE.get(), ESBlocks.COBBLED_RADIANITE_SLAB.get(), ESBlocks.COBBLED_RADIANITE_STAIRS.get(), ESBlocks.COBBLED_RADIANITE_WALL.get());
		axisBlock(ESBlocks.RADIANITE_PILLAR.get());
		stoneSet(ESBlocks.RADIANITE_BRICKS.get(), ESBlocks.RADIANITE_BRICK_SLAB.get(), ESBlocks.RADIANITE_BRICK_STAIRS.get(), ESBlocks.RADIANITE_BRICK_WALL.get());
		stoneSet(ESBlocks.POLISHED_RADIANITE.get(), ESBlocks.POLISHED_RADIANITE_SLAB.get(), ESBlocks.POLISHED_RADIANITE_STAIRS.get(), ESBlocks.POLISHED_RADIANITE_WALL.get());
		simpleBlock(ESBlocks.CHISELED_RADIANITE.get());
		stoneSet(ESBlocks.FLARE_BRICKS.get(), ESBlocks.FLARE_BRICK_SLAB.get(), ESBlocks.FLARE_BRICK_STAIRS.get(), ESBlocks.FLARE_BRICK_WALL.get());
		stoneSet(ESBlocks.CUT_FLARE_BRICKS.get(), ESBlocks.CUT_FLARE_BRICK_SLAB.get(), ESBlocks.CUT_FLARE_BRICK_STAIRS.get(), ESBlocks.CUT_FLARE_BRICK_WALL.get());
		stoneSet(ESBlocks.FLARE_TILES.get(), ESBlocks.FLARE_TILE_SLAB.get(), ESBlocks.FLARE_TILE_STAIRS.get(), ESBlocks.FLARE_TILE_WALL.get());
		stoneSet(ESBlocks.CUT_FLARE_TILES.get(), ESBlocks.CUT_FLARE_TILE_SLAB.get(), ESBlocks.CUT_FLARE_TILE_STAIRS.get(), ESBlocks.CUT_FLARE_TILE_WALL.get());
		axisBlock(ESBlocks.CHISELED_FLARE_PILLAR.get());

		stoneSet(ESBlocks.STELLAGMITE.get(), ESBlocks.STELLAGMITE_SLAB.get(), ESBlocks.STELLAGMITE_STAIRS.get(), ESBlocks.STELLAGMITE_WALL.get());
		stoneSet(ESBlocks.MOLTEN_STELLAGMITE.get(), ESBlocks.MOLTEN_STELLAGMITE_SLAB.get(), ESBlocks.MOLTEN_STELLAGMITE_STAIRS.get(), ESBlocks.MOLTEN_STELLAGMITE_WALL.get());
		stoneSet(ESBlocks.POLISHED_STELLAGMITE.get(), ESBlocks.POLISHED_STELLAGMITE_SLAB.get(), ESBlocks.POLISHED_STELLAGMITE_STAIRS.get(), ESBlocks.POLISHED_STELLAGMITE_WALL.get());

		simpleBlock(ESBlocks.ABYSSLATE.get());
		simpleBlock(ESBlocks.CHISELED_POLISHED_ABYSSLATE.get());
		stoneSet(ESBlocks.POLISHED_ABYSSLATE.get(), ESBlocks.POLISHED_ABYSSLATE_SLAB.get(), ESBlocks.POLISHED_ABYSSLATE_STAIRS.get(), ESBlocks.POLISHED_ABYSSLATE_WALL.get());
		stoneSet(ESBlocks.POLISHED_ABYSSLATE_BRICKS.get(), ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_ABYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_ABYSSLATE_BRICK_WALL.get());
		simpleBlock(ESBlocks.ABYSSAL_MAGMA_BLOCK.get());
		geyser(ESBlocks.ABYSSAL_GEYSER.get(), ESBlocks.ABYSSLATE.get());

		simpleBlock(ESBlocks.THERMABYSSLATE.get());
		simpleBlock(ESBlocks.CHISELED_POLISHED_THERMABYSSLATE.get());
		stoneSet(ESBlocks.POLISHED_THERMABYSSLATE.get(), ESBlocks.POLISHED_THERMABYSSLATE_SLAB.get(), ESBlocks.POLISHED_THERMABYSSLATE_STAIRS.get(), ESBlocks.POLISHED_THERMABYSSLATE_WALL.get());
		stoneSet(ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICK_WALL.get());
		simpleBlock(ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get());
		geyser(ESBlocks.THERMABYSSAL_GEYSER.get(), ESBlocks.THERMABYSSLATE.get());

		simpleBlock(ESBlocks.CRYOBYSSLATE.get());
		simpleBlock(ESBlocks.CHISELED_POLISHED_CRYOBYSSLATE.get());
		stoneSet(ESBlocks.POLISHED_CRYOBYSSLATE.get(), ESBlocks.POLISHED_CRYOBYSSLATE_SLAB.get(), ESBlocks.POLISHED_CRYOBYSSLATE_STAIRS.get(), ESBlocks.POLISHED_CRYOBYSSLATE_WALL.get());
		stoneSet(ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_WALL.get());
		simpleBlock(ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get());
		geyser(ESBlocks.CRYOBYSSAL_GEYSER.get(), ESBlocks.CRYOBYSSLATE.get());

		simpleBlock(ESBlocks.NIGHTFALL_MUD.get());
		simpleBlock(ESBlocks.GLOWING_NIGHTFALL_MUD.get());
		simpleBlock(ESBlocks.PACKED_NIGHTFALL_MUD.get());
		stoneSet(ESBlocks.NIGHTFALL_MUD_BRICKS.get(), ESBlocks.NIGHTFALL_MUD_BRICK_SLAB.get(), ESBlocks.NIGHTFALL_MUD_BRICK_STAIRS.get(), ESBlocks.NIGHTFALL_MUD_BRICK_WALL.get());

		sand(ESBlocks.TWILIGHT_SAND.get());
		sandstoneAndCut(ESBlocks.TWILIGHT_SANDSTONE.get(), ESBlocks.CUT_TWILIGHT_SANDSTONE.get());
		slabBlock(ESBlocks.TWILIGHT_SANDSTONE_SLAB.get(), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
		stairsBlock(ESBlocks.TWILIGHT_SANDSTONE_STAIRS.get(), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
		wallBlock(ESBlocks.TWILIGHT_SANDSTONE_WALL.get(), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"));
		slabBlock(ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get(), blockTexture(ESBlocks.CUT_TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.CUT_TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
		stairsBlock(ESBlocks.CUT_TWILIGHT_SANDSTONE_STAIRS.get(), blockTexture(ESBlocks.CUT_TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
		wallBlock(ESBlocks.CUT_TWILIGHT_SANDSTONE_WALL.get(), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
		simpleBlock(ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get(), models().cubeColumn(name(ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get()), blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top")));

		sand(ESBlocks.DUSTED_GRAVEL.get());
		stoneSet(ESBlocks.DUSTED_BRICKS.get(), ESBlocks.DUSTED_BRICK_SLAB.get(), ESBlocks.DUSTED_BRICK_STAIRS.get(), ESBlocks.DUSTED_BRICK_WALL.get());
		simpleGrassBlock(ESBlocks.MOSSY_DUSTED_GRAVEL.get(), blockTexture(ESBlocks.DUSTED_GRAVEL.get()));
		simpleGrassBlock(ESBlocks.GLOWING_MOSSY_DUSTED_GRAVEL.get(), blockTexture(ESBlocks.MOSSY_DUSTED_GRAVEL.get()).withSuffix("_side"), blockTexture(ESBlocks.GLOWING_MOSSY_DUSTED_GRAVEL.get()).withSuffix("_top"), blockTexture(ESBlocks.DUSTED_GRAVEL.get()));
		suspiciousBlock(ESBlocks.SUSPICIOUS_DUSTED_GRAVEL.get());

		sand(ESBlocks.DIMSLAG.get());
		suspiciousBlock(ESBlocks.SUSPICIOUS_DIMSLAG.get());

		cross(ESBlocks.STARLIGHT_FLOWER.get());
		pottedPlant(ESBlocks.POTTED_STARLIGHT_FLOWER.get(), blockTexture(ESBlocks.STARLIGHT_FLOWER.get()));
		cross(ESBlocks.AUREATE_FLOWER.get());
		pottedPlant(ESBlocks.POTTED_AUREATE_FLOWER.get(), blockTexture(ESBlocks.AUREATE_FLOWER.get()));
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
		onOffBlock(ESBlocks.MOONLIGHT_BUSH.get(), MoonlightBushBlock.BERRIES, models().cross(name(ESBlocks.MOONLIGHT_BUSH.get()) + "_berries", blockTexture(ESBlocks.MOONLIGHT_BUSH.get()).withSuffix("_berries")).renderType(CUTOUT), models().cross(name(ESBlocks.MOONLIGHT_BUSH.get()), blockTexture(ESBlocks.MOONLIGHT_BUSH.get())).renderType(CUTOUT));
		doublePlant(ESBlocks.GLINTGRASS.get());
		cross(ESBlocks.GLOWING_MUSHROOM.get());
		pottedPlant(ESBlocks.POTTED_GLOWING_MUSHROOM.get(), blockTexture(ESBlocks.GLOWING_MUSHROOM.get()));
		mushroomLikeBlock(ESBlocks.GLOWING_MUSHROOM_BLOCK.get());
		mushroomLikeBlock(ESBlocks.GLOWING_MUSHROOM_STEM.get(), name(ESBlocks.GLOWING_MUSHROOM_STEM.get()), blockTexture(ESBlocks.GLOWING_MUSHROOM_STEM.get()), name(ESBlocks.GLOWING_MUSHROOM_STEM.get()) + "_inside", blockTexture(ESBlocks.GLOWING_MUSHROOM_BLOCK.get()).withSuffix("_inside"));
		cross(ESBlocks.SHINING_MUSHROOM.get());
		pottedPlant(ESBlocks.POTTED_SHINING_MUSHROOM.get(), blockTexture(ESBlocks.SHINING_MUSHROOM.get()));
		mushroomLikeBlock(ESBlocks.SHINING_MUSHROOM_BLOCK.get());
		mushroomLikeBlock(ESBlocks.SHINING_MUSHROOM_STEM.get(), name(ESBlocks.SHINING_MUSHROOM_STEM.get()), blockTexture(ESBlocks.SHINING_MUSHROOM_STEM.get()), name(ESBlocks.SHINING_MUSHROOM_STEM.get()) + "_inside", blockTexture(ESBlocks.SHINING_MUSHROOM_BLOCK.get()).withSuffix("_inside"));
		vinesWithFruit(ESBlocks.BERRIES_VINES.get());
		vinesWithFruit(ESBlocks.BERRIES_VINES_PLANT.get());
		tintedCross(ESBlocks.CAVE_MOSS.get());
		tintedCross(ESBlocks.CAVE_MOSS_PLANT.get());
		multifaceBlock(ESBlocks.CAVE_MOSS_VEIN.get());
		caveMossFull(ESBlocks.CAVE_MOSS_BLOCK.get());
		onOffBlock(ESBlocks.CAVE_MOSS_CARPET.get(), CaveMossCarpetBlock.BOTTOM, models().getExistingFile(blockTexture(ESBlocks.CAVE_MOSS_CARPET.get()).withSuffix("_bottom")), models().singleTexture(name(ESBlocks.CAVE_MOSS_CARPET.get()), EternalStarlight.id("block/tinted_carpet"), "wool", blockTexture(ESBlocks.CAVE_MOSS_BLOCK.get())));
		directionalBud(ESBlocks.BOULDERSHROOM.get());
		pottedPlant(ESBlocks.POTTED_BOULDERSHROOM.get(), blockTexture(ESBlocks.BOULDERSHROOM.get()));
		mushroomLikeBlock(ESBlocks.BOULDERSHROOM_BLOCK.get());
		mushroomLikeBlock(ESBlocks.BOULDERSHROOM_STEM.get(), name(ESBlocks.BOULDERSHROOM_STEM.get()), blockTexture(ESBlocks.BOULDERSHROOM_STEM.get()), name(ESBlocks.BOULDERSHROOM_STEM.get()) + "_inside", blockTexture(ESBlocks.BOULDERSHROOM_BLOCK.get()).withSuffix("_inside"));
		cross(ESBlocks.BOULDERSHROOM_ROOTS.get());
		cross(ESBlocks.BOULDERSHROOM_ROOTS_PLANT.get());

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
		cross(ESBlocks.HANGING_FANTAGRASS.get());
		cross(ESBlocks.HANGING_FANTAGRASS_PLANT.get());

		cross(ESBlocks.ORANGE_SCARLET_BUD.get());
		cross(ESBlocks.PURPLE_SCARLET_BUD.get());
		cross(ESBlocks.RED_SCARLET_BUD.get());
		cross(ESBlocks.SCARLET_GRASS.get());
		cross(ESBlocks.MAUVE_FERN.get());

		cross(ESBlocks.WITHERED_STARLIGHT_FLOWER.get());
		pottedPlant(ESBlocks.POTTED_WITHERED_STARLIGHT_FLOWER.get(), blockTexture(ESBlocks.WITHERED_STARLIGHT_FLOWER.get()));
		cross(ESBlocks.AMARAMBER_GRASS.get());
		pottedPlant(ESBlocks.POTTED_AMARAMBER_GRASS.get(), blockTexture(ESBlocks.AMARAMBER_GRASS.get()));
		cross(ESBlocks.AMARAMBER_GRASS_BUSH.get());
		cross(ESBlocks.GLOOMCANDLE_ROOT.get());

		simpleBlock(ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get());
		simpleBlock(ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
		directionalBud(ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get());
		directionalBud(ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
		simpleBlock(ESBlocks.RED_STARLIGHT_CRYSTAL_LANTERN.get());
		simpleBlock(ESBlocks.BLUE_STARLIGHT_CRYSTAL_LANTERN.get());
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
		simpleExisting(ESBlocks.LUNARIS_CACTUS_GEL_BLOCK.get());
		horizontalBlock(ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get(), models().orientableWithBottom(name(ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get()), blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit"), blockTexture(ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get()), blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit_bottom"), blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit_top")));
		horizontalBlock(ESBlocks.LUNARIS_CACTUS_FRUIT_LANTERN.get(), models().orientableWithBottom(name(ESBlocks.LUNARIS_CACTUS_FRUIT_LANTERN.get()), blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit"), blockTexture(ESBlocks.LUNARIS_CACTUS_FRUIT_LANTERN.get()), blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit_bottom"), blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit_top")));
		directionalBud(ESBlocks.BLOOMING_RED_STARLIGHT_CRYSTAL_CLUSTER.get());
		directionalBud(ESBlocks.BLOOMING_BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
		cross(ESBlocks.RED_CRYSTALFLEUR.get());
		pottedPlant(ESBlocks.POTTED_RED_CRYSTALFLEUR.get(), blockTexture(ESBlocks.RED_CRYSTALFLEUR.get()));
		cross(ESBlocks.BLUE_CRYSTALFLEUR.get());
		pottedPlant(ESBlocks.POTTED_BLUE_CRYSTALFLEUR.get(), blockTexture(ESBlocks.BLUE_CRYSTALFLEUR.get()));
		multifaceBlock(ESBlocks.RED_CRYSTALFLEUR_VINE.get());
		multifaceBlock(ESBlocks.BLUE_CRYSTALFLEUR_VINE.get());
		randomlyMirroredAndRotatedBlock(ESBlocks.RED_CRYSTAL_MOSS_BLOCK.get());
		randomlyMirroredAndRotatedBlock(ESBlocks.BLUE_CRYSTAL_MOSS_BLOCK.get());
		carpet(ESBlocks.RED_CRYSTAL_MOSS_CARPET.get(), blockTexture(ESBlocks.RED_CRYSTAL_MOSS_BLOCK.get()));
		carpet(ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get(), blockTexture(ESBlocks.BLUE_CRYSTAL_MOSS_BLOCK.get()));

		cross(ESBlocks.FIRE_ORCHID.get());
		pottedPlant(ESBlocks.POTTED_FIRE_ORCHID.get(), blockTexture(ESBlocks.FIRE_ORCHID.get()));
		cross(ESBlocks.BLAZEBANK_GRASS.get());

		waterlily(ESBlocks.MOONLIGHT_LILY_PAD.get());
		waterlilyWithFlower(ESBlocks.STARLIT_LILY_PAD.get());
		waterlily(ESBlocks.MOONLIGHT_DUCKWEED.get());

		vinesWithFruit(ESBlocks.ABYSSAL_KELP.get());
		vinesWithFruit(ESBlocks.ABYSSAL_KELP_PLANT.get());
		orbflora(ESBlocks.ORBFLORA.get());
		cross(ESBlocks.ORBFLORA_PLANT.get());
		simpleBlock(ESBlocks.ORBFLORA_LIGHT.get());
		simpleExisting(ESBlocks.SPIRAL_KELP.get());
		simpleExisting(ESBlocks.SPIRAL_KELP_PLANT.get());
		multifaceBlock(ESBlocks.SEA_ROSA.get());
		doublePlant(ESBlocks.WICK_GRASS.get());
		lumenstem(ESBlocks.LUMENSTEM.get());
		lumenstemPlant(ESBlocks.LUMENSTEM_PLANT.get());
		cross(ESBlocks.MARIMOLD.get());
		simpleBlock(ESBlocks.MARIMOLD_BLOCK.get(), models().cubeAll(name(ESBlocks.MARIMOLD_BLOCK.get()), blockTexture(ESBlocks.MARIMOLD_BLOCK.get())).renderType(TRANSLUCENT));
		mushroomLikeBlock(ESBlocks.MARIMOLD_STEM.get());
		cross(ESBlocks.CIRCULUSH.get());
		cross(ESBlocks.STONETT.get());
		cross(ESBlocks.LUMINIS.get());
		cross(ESBlocks.GLOWLIS.get());
		cross(ESBlocks.GLOREED.get());
		cross(ESBlocks.STARLIGHT_SEAGRASS.get());
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
		mushroomLikeBlock(ESBlocks.VELVETUMOSS.get());
		directionalDenseCrossBud(ESBlocks.VELVETUMOSS_VILLI.get());
		simpleBlock(ESBlocks.RED_VELVETUMOSS.get());
		directionalDenseCrossBud(ESBlocks.RED_VELVETUMOSS_VILLI.get());
		cross(ESBlocks.RED_VELVETUMOSS_FLOWER.get());
		pottedPlant(ESBlocks.POTTED_RED_VELVETUMOSS_FLOWER.get(), blockTexture(ESBlocks.RED_VELVETUMOSS_FLOWER.get()));

		cross(ESBlocks.CRYSTALLIZED_LUNAR_GRASS.get());
		cross(ESBlocks.RED_CRYSTAL_ROOTS.get());
		cross(ESBlocks.BLUE_CRYSTAL_ROOTS.get());
		doublePlant(ESBlocks.TWILVEWRYM_HERB.get());
		doublePlant(ESBlocks.STELLAFLY_BUSH.get());
		doublePlant(ESBlocks.GLIMMERFLY_BUSH.get());

		cross(ESBlocks.SACRED_STARLIGHT_FLOWER.get());
		pottedPlant(ESBlocks.POTTED_SACRED_STARLIGHT_FLOWER.get(), blockTexture(ESBlocks.SACRED_STARLIGHT_FLOWER.get()));
		cross(ESBlocks.CRESCENTLEAF.get());
		cross(ESBlocks.GOLDEN_GRASS.get());
		doublePlant(ESBlocks.TALL_GOLDEN_GRASS.get());
		cross(ESBlocks.SACRED_LANTERNVINE.get());
		cross(ESBlocks.SACRED_LANTERNVINE_PLANT.get());
		cross(ESBlocks.HANGING_SACRED_LANTERNVINE.get());
		cross(ESBlocks.HANGING_SACRED_LANTERNVINE_PLANT.get());

		simpleBlock(ESBlocks.NIGHTFALL_DIRT.get());
		farmland(ESBlocks.NIGHTFALL_FARMLAND.get(), ESBlocks.NIGHTFALL_DIRT.get());
		dirtPath(ESBlocks.NIGHTFALL_DIRT_PATH.get(), ESBlocks.NIGHTFALL_DIRT.get());
		grassBlock(ESBlocks.NIGHTFALL_GRASS_BLOCK.get(), blockTexture(ESBlocks.NIGHTFALL_DIRT.get()));
		snowyDirtBlock(ESBlocks.NIGHTFALL_PODZOL.get(), blockTexture(ESBlocks.NIGHTFALL_DIRT.get()), blockTexture(ESBlocks.NIGHTFALL_GRASS_BLOCK.get()).withSuffix("_snow"));
		simpleGrassBlock(ESBlocks.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get(), blockTexture(ESBlocks.NIGHTFALL_DIRT.get()));
		simpleGrassBlock(ESBlocks.FANTASY_GRASS_BLOCK.get(), blockTexture(ESBlocks.NIGHTFALL_MUD.get()));
		carpet(ESBlocks.FANTASY_GRASS_CARPET.get(), blockTexture(ESBlocks.FANTASY_GRASS_BLOCK.get()).withSuffix("_top"));
		simpleGrassBlock(ESBlocks.GOLDEN_GRASS_BLOCK.get(), blockTexture(ESBlocks.NIGHTFALL_DIRT.get()));

		crinoa(ESBlocks.CRINOA.get());
		directionalCubeBottomTop(ESBlocks.CRINOA_BALE.get());

		nocturnalMilletTop(ESBlocks.NOCTURNAL_MILLET_PANICLE.get());
		nocturnalMilletBottom(ESBlocks.NOCTURNAL_MILLET_STALK.get());

		simpleBlock(ESBlocks.RAW_AETHERSENT_BLOCK.get());
		simpleBlock(ESBlocks.AETHERSENT_BLOCK.get());

		stoneSet(ESBlocks.SPRINGSTONE.get(), ESBlocks.SPRINGSTONE_SLAB.get(), ESBlocks.SPRINGSTONE_STAIRS.get(), ESBlocks.SPRINGSTONE_WALL.get());
		stoneSet(ESBlocks.SPRINGSTONE_BRICKS.get(), ESBlocks.SPRINGSTONE_BRICK_SLAB.get(), ESBlocks.SPRINGSTONE_BRICK_STAIRS.get(), ESBlocks.SPRINGSTONE_BRICK_WALL.get());
		stoneSet(ESBlocks.POLISHED_SPRINGSTONE.get(), ESBlocks.POLISHED_SPRINGSTONE_SLAB.get(), ESBlocks.POLISHED_SPRINGSTONE_STAIRS.get(), ESBlocks.POLISHED_SPRINGSTONE_WALL.get());
		simpleBlock(ESBlocks.CHISELED_SPRINGSTONE.get());

		stoneSet(ESBlocks.THERMAL_SPRINGSTONE.get(), ESBlocks.THERMAL_SPRINGSTONE_SLAB.get(), ESBlocks.THERMAL_SPRINGSTONE_STAIRS.get(), ESBlocks.THERMAL_SPRINGSTONE_WALL.get());
		stoneSet(ESBlocks.THERMAL_SPRINGSTONE_BRICKS.get(), ESBlocks.THERMAL_SPRINGSTONE_BRICK_SLAB.get(), ESBlocks.THERMAL_SPRINGSTONE_BRICK_STAIRS.get(), ESBlocks.THERMAL_SPRINGSTONE_BRICK_WALL.get());

		simpleBlock(ESBlocks.GLACITE.get());
		simpleBlock(ESBlocks.GLACITE_BLOCK.get());

		simpleBlock(ESBlocks.GRIMSTONE_STARLIT_DIAMOND_ORE.get());
		simpleBlock(ESBlocks.VOIDSTONE_STARLIT_DIAMOND_ORE.get());
		simpleBlock(ESBlocks.ETERNAL_ICE_STARLIT_DIAMOND_ORE.get());
		simpleBlock(ESBlocks.HAZE_ICE_STARLIT_DIAMOND_ORE.get());
		simpleBlock(ESBlocks.STARLIT_DIAMOND_BLOCK.get());

		simpleBlock(ESBlocks.GRIMSTONE_DEEPSILVER_ORE.get());
		simpleBlock(ESBlocks.VOIDSTONE_DEEPSILVER_ORE.get());
		simpleBlock(ESBlocks.ETERNAL_ICE_DEEPSILVER_ORE.get());
		simpleBlock(ESBlocks.HAZE_ICE_DEEPSILVER_ORE.get());
		simpleBlock(ESBlocks.NIGHTFALL_MUD_DEEPSILVER_ORE.get());
		simpleBlock(ESBlocks.PACKED_NIGHTFALL_MUD_DEEPSILVER_ORE.get());
		simpleBlock(ESBlocks.RAW_DEEPSILVER_BLOCK.get());
		simpleBlock(ESBlocks.DEEPSILVER_BLOCK.get());
		simpleBlock(ESBlocks.DEEPSILVER_GRATE.get(), models().cubeAll(name(ESBlocks.DEEPSILVER_GRATE.get()), blockTexture(ESBlocks.DEEPSILVER_GRATE.get())).renderType(CUTOUT));

		simpleExisting(ESBlocks.UNREALIUM_BLOCK.get());

		simpleBlock(ESBlocks.GRIMSTONE_MALARITE_ORE.get());
		simpleBlock(ESBlocks.VOIDSTONE_MALARITE_ORE.get());
		simpleBlock(ESBlocks.NIGHTFALL_MUD_MALARITE_ORE.get());
		simpleBlock(ESBlocks.PACKED_NIGHTFALL_MUD_MALARITE_ORE.get());
		simpleBlock(ESBlocks.MALARITE_BLOCK.get());

		pungencyFruit(ESBlocks.PUNGENCY_FRUIT_VINES.get());
		cubeBottomTop(ESBlocks.TEAR_BOMB.get());

		dryingRack(ESBlocks.DRYING_RACK.get());

		starfireBirdNest(ESBlocks.STARFIRE_BIRD_NEST.get());
		starfireBirdAviary(ESBlocks.OAK_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("oak"));
		starfireBirdAviary(ESBlocks.SPRUCE_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("spruce"));
		starfireBirdAviary(ESBlocks.BIRCH_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("birch"));
		starfireBirdAviary(ESBlocks.ACACIA_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("acacia"));
		starfireBirdAviary(ESBlocks.CHERRY_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("cherry"));
		starfireBirdAviary(ESBlocks.JUNGLE_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("jungle"));
		starfireBirdAviary(ESBlocks.DARK_OAK_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("dark_oak"));
		starfireBirdAviary(ESBlocks.CRIMSON_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("crimson"), "stem");
		starfireBirdAviary(ESBlocks.WARPED_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("warped"), "stem");
		starfireBirdAviary(ESBlocks.MANGROVE_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("mangrove"));
		starfireBirdAviary(ESBlocks.BAMBOO_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("bamboo"), "block");
		starfireBirdAviary(ESBlocks.LUNAR_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("lunar"));
		starfireBirdAviary(ESBlocks.NORTHLAND_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("northland"));
		starfireBirdAviary(ESBlocks.BANYIN_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("banyin"));
		starfireBirdAviary(ESBlocks.SCARLET_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("scarlet"));
		starfireBirdAviary(ESBlocks.TORREYA_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("torreya"));
		starfireBirdAviary(ESBlocks.JINGLESTEM_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("jinglestem"));
		starfireBirdAviary(ESBlocks.CRADLEWOOD_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("cradlewood"));

		simpleBlock(ESBlocks.RAW_FLOWGLAZE.get());
		simpleBlock(ESBlocks.FLOWGLAZE.get(), models().cubeAll(name(ESBlocks.FLOWGLAZE.get()), blockTexture(ESBlocks.FLOWGLAZE.get())).renderType(TRANSLUCENT));
		paneBlockWithRenderType(ESBlocks.FLOWGLAZE_PANE.get(), blockTexture(ESBlocks.FLOWGLAZE.get()), blockTexture(ESBlocks.FLOWGLAZE_PANE.get()).withSuffix("_top"), TRANSLUCENT);
		stoneSet(ESBlocks.FLOWGLAZE_BRICKS.get(), ESBlocks.FLOWGLAZE_BRICK_SLAB.get(), ESBlocks.FLOWGLAZE_BRICK_STAIRS.get(), ESBlocks.FLOWGLAZE_BRICK_WALL.get());

		simpleBlock(ESBlocks.GRIMSTONE_SALTPETER_ORE.get());
		simpleBlock(ESBlocks.VOIDSTONE_SALTPETER_ORE.get());
		simpleBlock(ESBlocks.ETERNAL_ICE_SALTPETER_ORE.get());
		simpleBlock(ESBlocks.HAZE_ICE_SALTPETER_ORE.get());
		simpleBlock(ESBlocks.SALTPETER_BLOCK.get());

		rawAmaramberBlock(ESBlocks.RAW_AMARAMBER_BLOCK.get());
		lantern(ESBlocks.AMARAMBER_LANTERN.get());
		candle(ESBlocks.AMARAMBER_CANDLE.get());
		candleCake(ESBlocks.AMARAMBER_CANDLE_CAKE.get(), Blocks.CAKE, ESBlocks.AMARAMBER_CANDLE.get());
		stoneSet(ESBlocks.AMARAMBER_BRICKS.get(), ESBlocks.AMARAMBER_BRICK_SLAB.get(), ESBlocks.AMARAMBER_BRICK_STAIRS.get(), ESBlocks.AMARAMBER_BRICK_WALL.get());
		stoneSet(ESBlocks.TORREYA_TILES.get(), ESBlocks.TORREYA_TILE_SLAB.get(), ESBlocks.TORREYA_TILE_STAIRS.get(), ESBlocks.TORREYA_TILE_WALL.get());

		particleOnly(ESBlocks.ETHER.get());
		thioquartzBlock(ESBlocks.THIOQUARTZ_BLOCK.get());
		simpleBlock(ESBlocks.BUDDING_THIOQUARTZ.get());
		directionalBud(ESBlocks.THIOQUARTZ_CLUSTER.get());
		stoneSet(ESBlocks.TOXITE.get(), ESBlocks.TOXITE_SLAB.get(), ESBlocks.TOXITE_STAIRS.get(), ESBlocks.TOXITE_WALL.get());
		stoneSet(ESBlocks.TOXITE_BRICKS.get(), ESBlocks.TOXITE_BRICK_SLAB.get(), ESBlocks.TOXITE_BRICK_STAIRS.get(), ESBlocks.TOXITE_BRICK_WALL.get());
		polishedToxite(ESBlocks.POLISHED_TOXITE.get());
		slabBlock(ESBlocks.POLISHED_TOXITE_SLAB.get(), blockTexture(ESBlocks.POLISHED_TOXITE.get()), blockTexture(ESBlocks.POLISHED_TOXITE.get()));
		stairsBlock(ESBlocks.POLISHED_TOXITE_STAIRS.get(), blockTexture(ESBlocks.POLISHED_TOXITE.get()));
		wallBlock(ESBlocks.POLISHED_TOXITE_WALL.get(), blockTexture(ESBlocks.POLISHED_TOXITE.get()));
		simpleBlock(ESBlocks.CHISELED_TOXITE.get());

		redstoneOre(ESBlocks.GRIMSTONE_REDSTONE_ORE.get());
		redstoneOre(ESBlocks.VOIDSTONE_REDSTONE_ORE.get());
		simpleBlock(ESBlocks.ETERNAL_ICE_REDSTONE_ORE.get());
		simpleBlock(ESBlocks.HAZE_ICE_REDSTONE_ORE.get());

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

		stoneSet(ESBlocks.TOOTH_OF_HUNGER_TILES.get(), ESBlocks.TOOTH_OF_HUNGER_TILE_SLAB.get(), ESBlocks.TOOTH_OF_HUNGER_TILE_STAIRS.get(), ESBlocks.TOOTH_OF_HUNGER_TILE_WALL.get());
		simpleBlock(ESBlocks.CHISELED_TOOTH_OF_HUNGER_TILES.get());
		directionalBlock(ESBlocks.CRYSTALBORN_CATALYST.get(), models().cubeBottomTop(name(ESBlocks.CRYSTALBORN_CATALYST.get()), EternalStarlight.id("block/machine_side"), EternalStarlight.id("block/machine_side"), blockTexture(ESBlocks.CRYSTALBORN_CATALYST.get())));
		simpleBlock(ESBlocks.CRYSTALLIZED_SAND.get());

		particleOnly(ESBlocks.LOOT_CHEST.get(), blockTexture(ESBlocks.LUNAR_PLANKS.get()));

		spawner(ESBlocks.THE_GATEKEEPER_SPAWNER.get());
		spawner(ESBlocks.STARLIGHT_GOLEM_SPAWNER.get());
		spawner(ESBlocks.PERMAFROST_SPAWNER.get());
		spawner(ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get());
		particleOnly(ESBlocks.SOLAR_EGG.get(), itemTextureFromBlock(ESBlocks.SOLAR_EGG.get()));

		simpleBlock(ESBlocks.GOLEM_STEEL_BLOCK.get());
		simpleBlock(ESBlocks.WAXED_GOLEM_STEEL_BLOCK.get(), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get())));
		simpleBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get());
		slabBlock(ESBlocks.GOLEM_STEEL_SLAB.get(), blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get()), blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get()));
		slabBlock(ESBlocks.WAXED_GOLEM_STEEL_SLAB.get(), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_SLAB.get())), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_SLAB.get()).withSuffix("_top")), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get())));
		slabBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get()), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get()));
		stairsBlock(ESBlocks.GOLEM_STEEL_STAIRS.get(), blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get()));
		stairsBlock(ESBlocks.WAXED_GOLEM_STEEL_STAIRS.get(), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_STAIRS.get())), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_STAIRS.get()).withSuffix("_inner")), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_STAIRS.get()).withSuffix("_outer")));
		stairsBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get(), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get()));
		simpleBlock(ESBlocks.GOLEM_STEEL_TILES.get());
		simpleBlock(ESBlocks.WAXED_GOLEM_STEEL_TILES.get(), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_TILES.get())));
		simpleBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get());
		slabBlock(ESBlocks.GOLEM_STEEL_TILE_SLAB.get(), blockTexture(ESBlocks.GOLEM_STEEL_TILES.get()), blockTexture(ESBlocks.GOLEM_STEEL_TILES.get()));
		slabBlock(ESBlocks.WAXED_GOLEM_STEEL_TILE_SLAB.get(), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_TILE_SLAB.get())), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_TILE_SLAB.get()).withSuffix("_top")), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_TILES.get())));
		slabBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get()), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get()));
		stairsBlock(ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(), blockTexture(ESBlocks.GOLEM_STEEL_TILES.get()));
		stairsBlock(ESBlocks.WAXED_GOLEM_STEEL_TILE_STAIRS.get(), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_TILE_STAIRS.get())), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_TILE_STAIRS.get()).withSuffix("_inner")), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_TILE_STAIRS.get()).withSuffix("_outer")));
		stairsBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get()));
		simpleBlock(ESBlocks.GOLEM_STEEL_GRATE.get(), models().cubeAll(name(ESBlocks.GOLEM_STEEL_GRATE.get()), blockTexture(ESBlocks.GOLEM_STEEL_GRATE.get())).renderType(CUTOUT));
		simpleBlock(ESBlocks.WAXED_GOLEM_STEEL_GRATE.get(), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_GRATE.get())));
		simpleBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get(), models().cubeAll(name(ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get()), blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get())).renderType(CUTOUT));
		axisBlock(ESBlocks.GOLEM_STEEL_PILLAR.get());
		axisBlock(ESBlocks.WAXED_GOLEM_STEEL_PILLAR.get(), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_PILLAR.get())), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_PILLAR.get()).withSuffix("_horizontal")));
		axisBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_PILLAR.get());
		simpleBlock(ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get());
		simpleBlock(ESBlocks.WAXED_CHISELED_GOLEM_STEEL_BLOCK.get(), models().getExistingFile(blockTexture(ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get())));
		simpleBlock(ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get());
		jetBlock(ESBlocks.GOLEM_STEEL_JET.get());
		directionalBlock(ESBlocks.WAXED_GOLEM_STEEL_JET.get(), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_JET.get())));
		jetBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_JET.get());
		directionalOnOffBlock(ESBlocks.GOLEM_STEEL_CRATE.get(), CrateBlock.OPEN, models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_CRATE.get()).withSuffix("_open")), models().getExistingFile(blockTexture(ESBlocks.GOLEM_STEEL_CRATE.get())));
		directionalOnOffBlock(ESBlocks.ENERGY_TRANSMITTER.get(), EnergyTransmitterBlock.POWERED, models().getExistingFile(blockTexture(ESBlocks.ENERGY_TRANSMITTER.get()).withSuffix("_on")), models().getExistingFile(blockTexture(ESBlocks.ENERGY_TRANSMITTER.get())),
			EnergyTransmitterBlock.OFFSET_TRANSFORMATION,
			EnergyTransmitterBlock.POWER,
			EnergyTransmitterBlock.DIRECT_POWER
		);
		accumulator(ESBlocks.ACCUMULATOR.get());
		particleOnly(ESBlocks.MECHANICAL_SPAWNER.get(), blockTexture(Blocks.SPAWNER));
		particleOnly(ESBlocks.ALLOY_FURNACE.get(), itemTextureFromBlock(ESBlocks.ALLOY_FURNACE.get()));
		particleOnly(ESBlocks.WAXED_ALLOY_FURNACE.get(), itemTextureFromBlock(ESBlocks.ALLOY_FURNACE.get()));
		particleOnly(ESBlocks.OXIDIZED_ALLOY_FURNACE.get(), itemTextureFromBlock(ESBlocks.OXIDIZED_ALLOY_FURNACE.get()));
		onOffBlock(ESBlocks.ENERGY_BLOCK.get());

		shadegrieve(ESBlocks.SHADEGRIEVE.get());
		shadegrieve(ESBlocks.BLOOMING_SHADEGRIEVE.get());
		particleOnly(ESBlocks.LUNAR_VINE.get(), itemTextureFromBlock(ESBlocks.LUNAR_VINE.get()));
		simpleBlock(ESBlocks.LUNAR_MOSAIC.get());
		slabBlock(ESBlocks.LUNAR_MOSAIC_SLAB.get(), blockTexture(ESBlocks.LUNAR_MOSAIC.get()), blockTexture(ESBlocks.LUNAR_MOSAIC.get()));
		stairsBlock(ESBlocks.LUNAR_MOSAIC_STAIRS.get(), blockTexture(ESBlocks.LUNAR_MOSAIC.get()));
		fenceBlock(ESBlocks.LUNAR_MOSAIC_FENCE.get(), blockTexture(ESBlocks.LUNAR_MOSAIC.get()));
		fenceGateBlock(ESBlocks.LUNAR_MOSAIC_FENCE_GATE.get(), blockTexture(ESBlocks.LUNAR_MOSAIC.get()));
		carpet(ESBlocks.LUNAR_MAT.get(), blockTexture(ESBlocks.LUNAR_MAT.get()));

		tintedCubeAll(ESBlocks.DUSK_GLASS.get(), blockTexture(ESBlocks.DUSK_GLASS.get()), TRANSLUCENT);
		simpleBlock(ESBlocks.DUSK_LIGHT.get());
		simpleBlock(ESBlocks.REINFORCED_DUSK_LIGHT.get());
		duskEmitter(ESBlocks.DUSK_EMITTER.get(), name(ESBlocks.DUSK_EMITTER.get()), blockTexture(ESBlocks.DUSK_EMITTER.get()), name(ESBlocks.DUSK_EMITTER.get()) + "_off", blockTexture(ESBlocks.DUSK_EMITTER.get()).withSuffix("_off"));
		simpleBlock(ESBlocks.DUSK_LOCKBOX.get());
		onOffBlock(ESBlocks.FLARE_SPAWNER.get(), FlareSpawnerBlock.LIT, models().cubeAll(name(ESBlocks.FLARE_SPAWNER.get()), blockTexture(ESBlocks.FLARE_SPAWNER.get())).renderType(CUTOUT), models().cubeAll(name(ESBlocks.FLARE_SPAWNER.get()) + "_off", blockTexture(ESBlocks.FLARE_SPAWNER.get()).withSuffix("_off")).renderType(CUTOUT));
		simpleBlock(ESBlocks.ECLIPSE_CORE.get());

		stoneSet(ESBlocks.DOOMEDEN_TILES.get(), ESBlocks.DOOMEDEN_TILE_SLAB.get(), ESBlocks.DOOMEDEN_TILE_STAIRS.get(), ESBlocks.DOOMEDEN_TILE_WALL.get());
		simpleBlock(ESBlocks.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
		simpleBlock(ESBlocks.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
		doomedenTorch(ESBlocks.DOOMED_TORCH.get(), ESBlocks.WALL_DOOMED_TORCH.get());
		doomedenRedstoneTorch(ESBlocks.DOOMED_REDSTONE_TORCH.get(), ESBlocks.WALL_DOOMED_REDSTONE_TORCH.get());
		stoneSet(ESBlocks.DOOMEDEN_BRICKS.get(), ESBlocks.DOOMEDEN_BRICK_SLAB.get(), ESBlocks.DOOMEDEN_BRICK_STAIRS.get(), ESBlocks.DOOMEDEN_BRICK_WALL.get());
		stoneSet(ESBlocks.POLISHED_DOOMEDEN_BRICKS.get(), ESBlocks.POLISHED_DOOMEDEN_BRICK_SLAB.get(), ESBlocks.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), ESBlocks.POLISHED_DOOMEDEN_BRICK_WALL.get());
		onOffBlock(ESBlocks.DOOMEDEN_LIGHT.get());
		doomedenKeyhole(ESBlocks.DOOMEDEN_KEYHOLE.get(), ESBlocks.REDSTONE_DOOMEDEN_KEYHOLE.get());

		stellarRack(ESBlocks.STELLAR_RACK.get());
		horizontalBlock(ESBlocks.ENCHANTED_GRIMSTONE_BRICKS.get(), blockTexture(ESBlocks.GRIMSTONE_BRICKS.get()), blockTexture(ESBlocks.ENCHANTED_GRIMSTONE_BRICKS.get()), blockTexture(ESBlocks.POLISHED_GRIMSTONE.get()));
		portal(ESBlocks.STARLIGHT_PORTAL.get());
	}

	private void simpleExisting(Block block) {
		ModelFile modelFile = models().getExistingFile(key(block));
		simpleBlock(block, modelFile);
	}

	private void woodSet(RotatedPillarBlock log, RotatedPillarBlock wood, Block planks, RotatedPillarBlock strippedLog, RotatedPillarBlock strippedWood, DoorBlock door, boolean cutoutDoor, TrapDoorBlock trapdoor, boolean cutoutTrapdoor, PressurePlateBlock pressurePlate, ButtonBlock button, FenceBlock fence, FenceGateBlock fenceGate, SlabBlock slab, StairBlock stairs, Block sign, Block wallSign, Block hangingSign, Block wallHangingSign) {
		logBlock(log);
		axisBlock(wood, blockTexture(log), blockTexture(log));
		simpleBlock(planks);
		logBlock(strippedLog);
		axisBlock(strippedWood, blockTexture(strippedLog), blockTexture(strippedLog));
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
		getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Direction.Axis.X ? modelNs : modelEw).build(), ESPortalBlock.CENTER);
	}

	private void crinoa(Block block) {
		getVariantBuilder(block).forAllStates(state -> {
			int stage = Math.max(state.getValue(BlockStateProperties.AGE_7) - 1, 0);
			return ConfiguredModel.builder().modelFile(models().crop(name(block) + "_stage" + stage, blockTexture(block).withSuffix("_stage" + stage)).renderType(CUTOUT)).build();
		});
	}

	private void pungencyFruit(Block block) {
		ModelFile stage0 = models().singleTexture(name(block) + "_stage0", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage0")).renderType(CUTOUT);
		ModelFile stage1 = models().singleTexture(name(block) + "_stage1", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage1")).renderType(CUTOUT);
		ModelFile stage2 = models().singleTexture(name(block) + "_stage2", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage2")).renderType(CUTOUT);
		ModelFile stage3 = models().singleTexture(name(block) + "_stage3", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage3")).renderType(CUTOUT);
		getVariantBuilder(block)
			.partialState().with(BlockStateProperties.AGE_7, 0)
			.modelForState().modelFile(stage0).addModel()
			.partialState().with(BlockStateProperties.AGE_7, 1)
			.modelForState().modelFile(stage0).addModel()
			.partialState().with(BlockStateProperties.AGE_7, 2)
			.modelForState().modelFile(stage1).addModel()
			.partialState().with(BlockStateProperties.AGE_7, 3)
			.modelForState().modelFile(stage1).addModel()
			.partialState().with(BlockStateProperties.AGE_7, 4)
			.modelForState().modelFile(stage2).addModel()
			.partialState().with(BlockStateProperties.AGE_7, 5)
			.modelForState().modelFile(stage2).addModel()
			.partialState().with(BlockStateProperties.AGE_7, 6)
			.modelForState().modelFile(stage2).addModel()
			.partialState().with(BlockStateProperties.AGE_7, 7)
			.modelForState().modelFile(stage3).addModel();
	}

	private void nocturnalMilletTop(Block block) {
		ModelFile stage0 = models().singleTexture(name(block) + "_stage0", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage0")).renderType(CUTOUT);
		ModelFile stage1 = models().singleTexture(name(block) + "_stage1", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage1")).renderType(CUTOUT);
		ModelFile stage2 = models().singleTexture(name(block) + "_stage2", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage2")).renderType(CUTOUT);
		ModelFile stageWithered = models().singleTexture(name(block) + "_withered", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_withered")).renderType(CUTOUT);
		ModelFile stage0Forgotten = models().singleTexture(name(block) + "_stage0_forgotten", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage0_forgotten")).renderType(CUTOUT);
		ModelFile stage1Forgotten = models().singleTexture(name(block) + "_stage1_forgotten", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage1_forgotten")).renderType(CUTOUT);
		ModelFile stage2Forgotten = models().singleTexture(name(block) + "_stage2_forgotten", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage2_forgotten")).renderType(CUTOUT);

		getVariantBuilder(block).forAllStates(state -> {
			int age = state.getValue(BlockStateProperties.AGE_2);
			boolean forgotten = state.getValue(NocturnalMilletTopBlock.FORGOTTEN);
			boolean withered = state.getValue(NocturnalMilletTopBlock.WITHERED);

			ModelFile model;
			if (withered) {
				model = forgotten ? stage2Forgotten : stageWithered;
			} else {
				if (forgotten) {
					model = switch (age) {
						case 0 -> stage0Forgotten;
						case 1 -> stage1Forgotten;
						default -> stage2Forgotten;
					};
				} else {
					model = switch (age) {
						case 0 -> stage0;
						case 1 -> stage1;
						default -> stage2;
					};
				}
			}
			return ConfiguredModel.builder().modelFile(model).build();
		});
	}

	private void nocturnalMilletBottom(Block block) {
		ModelFile stage0 = models().singleTexture(name(block) + "_stage0", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage0")).renderType(CUTOUT);
		ModelFile stage1 = models().singleTexture(name(block) + "_stage1", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage1")).renderType(CUTOUT);
		ModelFile stage2 = models().singleTexture(name(block) + "_stage2", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage2")).renderType(CUTOUT);
		ModelFile stage3 = models().singleTexture(name(block) + "_stage3", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage3")).renderType(CUTOUT);
		ModelFile stageWithered = models().singleTexture(name(block) + "_withered", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_withered")).renderType(CUTOUT);
		ModelFile stage0Forgotten = models().singleTexture(name(block) + "_stage0_forgotten", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage0_forgotten")).renderType(CUTOUT);
		ModelFile stage1Forgotten = models().singleTexture(name(block) + "_stage1_forgotten", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage1_forgotten")).renderType(CUTOUT);
		ModelFile stage2Forgotten = models().singleTexture(name(block) + "_stage2_forgotten", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage2_forgotten")).renderType(CUTOUT);
		ModelFile stage3Forgotten = models().singleTexture(name(block) + "_stage3_forgotten", EternalStarlight.id("block/cross_crop"), "cross", blockTexture(block).withSuffix("_stage3_forgotten")).renderType(CUTOUT);

		IntUnaryOperator ageToIndex = age -> {
			if (age <= 1) return 0;
			if (age <= 3) return 1;
			if (age <= 6) return 2;
			return 3;
		};

		getVariantBuilder(block).forAllStates(state -> {
			int age = state.getValue(BlockStateProperties.AGE_7);
			boolean forgotten = state.getValue(NocturnalMilletTopBlock.FORGOTTEN);
			boolean withered = state.getValue(NocturnalMilletTopBlock.WITHERED);

			ModelFile model;
			if (withered) {
				model = forgotten ? stage3Forgotten : stageWithered;
			} else {
				int idx = ageToIndex.applyAsInt(age);
				if (forgotten) {
					model = switch (idx) {
						case 0 -> stage0Forgotten;
						case 1 -> stage1Forgotten;
						case 2 -> stage2Forgotten;
						default -> stage3Forgotten;
					};
				} else {
					model = switch (idx) {
						case 0 -> stage0;
						case 1 -> stage1;
						case 2 -> stage2;
						default -> stage3;
					};
				}
			}
			return ConfiguredModel.builder().modelFile(model).build();
		});
	}

	private void dryingRack(Block block) {
		ModelFile modelFile = models().getExistingFile(EternalStarlight.id("drying_rack"));
		ModelFile modelCampfire = models().getExistingFile(EternalStarlight.id("drying_rack_campfire"));
		getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(state.getValue(DryingRackBlock.CAMPFIRE) ? modelCampfire : modelFile).rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()).build(), BlockStateProperties.LIT);
	}

	private void starfireBirdNest(Block block) {
		ModelFile modelFile = models().getExistingFile(EternalStarlight.id("starfire_bird_nest"));
		ModelFile modelEggs1 = models().getExistingFile(EternalStarlight.id("starfire_bird_nest_eggs1"));
		ModelFile modelEggs2 = models().getExistingFile(EternalStarlight.id("starfire_bird_nest_eggs2"));
		ModelFile modelEggs3 = models().getExistingFile(EternalStarlight.id("starfire_bird_nest_eggs3"));
		getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(switch (state.getValue(StarfireBirdNestBlock.EGGS)) {
			case 1 -> modelEggs1;
			case 2 -> modelEggs2;
			case 3 -> modelEggs3;
			default -> modelFile;
		}).rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build());
	}

	private void starfireBirdAviary(Block block, ResourceLocation type) {
		starfireBirdAviary(block, type, "log");
	}

	private void starfireBirdAviary(Block block, ResourceLocation type, String logSuffix) {
		ModelFile modelFile = models().withExistingParent(name(block), EternalStarlight.id("template_starfire_bird_aviary"))
			.texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("trapdoor", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_trapdoor"))
			.texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
		ModelFile modelOpen = models().withExistingParent(name(block) + "_open", EternalStarlight.id("template_starfire_bird_aviary_open"))
			.texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
		ModelFile modelEggs1 = models().withExistingParent(name(block) + "_eggs1", EternalStarlight.id("template_starfire_bird_aviary_eggs1"))
			.texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("trapdoor", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_trapdoor"))
			.texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
		ModelFile modelEggs1Open = models().withExistingParent(name(block) + "_eggs1_open", EternalStarlight.id("template_starfire_bird_aviary_eggs1_open"))
			.texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
		ModelFile modelEggs2 = models().withExistingParent(name(block) + "_eggs2", EternalStarlight.id("template_starfire_bird_aviary_eggs2"))
			.texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("trapdoor", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_trapdoor"))
			.texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
		ModelFile modelEggs2Open = models().withExistingParent(name(block) + "_eggs2_open", EternalStarlight.id("template_starfire_bird_aviary_eggs2_open"))
			.texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
		ModelFile modelEggs3 = models().withExistingParent(name(block) + "_eggs3", EternalStarlight.id("template_starfire_bird_aviary_eggs3"))
			.texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("trapdoor", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_trapdoor"))
			.texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
		ModelFile modelEggs3Open = models().withExistingParent(name(block) + "_eggs3_open", EternalStarlight.id("template_starfire_bird_aviary_eggs3_open"))
			.texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks"))
			.texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
		getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(switch (state.getValue(StarfireBirdNestBlock.EGGS)) {
			case 1 -> state.getValue(StarfireBirdAviaryBlock.OPEN) ? modelEggs1Open : modelEggs1;
			case 2 -> state.getValue(StarfireBirdAviaryBlock.OPEN) ? modelEggs2Open : modelEggs2;
			case 3 -> state.getValue(StarfireBirdAviaryBlock.OPEN) ? modelEggs3Open : modelEggs3;
			default -> state.getValue(StarfireBirdAviaryBlock.OPEN) ? modelOpen : modelFile;
		}).rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build());
	}

	private void rawAmaramberBlock(Block block) {
		ModelFile modelTop = models().cubeBottomTop(name(block) + "top", blockTexture(block).withSuffix("_side"), blockTexture(block), blockTexture(block).withSuffix("_top"));
		ModelFile modelNormal = models().cubeAll(name(block), blockTexture(block));
		getVariantBuilder(block)
			.partialState().with(RawAmaramberBlock.TOP, true)
			.modelForState().modelFile(modelTop).addModel()
			.partialState().with(RawAmaramberBlock.TOP, false)
			.modelForState().modelFile(modelNormal).addModel();
	}

	private void thioquartzBlock(Block block) {
		ModelFile modelNormal = models().cubeAll(name(block), blockTexture(block));
		ModelFile modelEmbedded = models().cubeAll(name(block) + "_seed", blockTexture(block).withSuffix("_seed"));
		getVariantBuilder(block)
			.partialState().with(ThioquartzBlock.SEED, false)
			.modelForState().modelFile(modelNormal).addModel()
			.partialState().with(ThioquartzBlock.SEED, true)
			.modelForState().modelFile(modelEmbedded).addModel();
	}

	private void polishedToxite(Block block) {
		getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(PolishedToxiteBlock.PART) == PolishedToxiteBlock.Part.FULL ? models().cubeAll(name(block), blockTexture(block)) : (state.getValue(PolishedToxiteBlock.PART) == PolishedToxiteBlock.Part.MIDDLE
			? models().cubeAll(name(block) + "_middle", blockTexture(block).withSuffix("_middle")) : (state.getValue(PolishedToxiteBlock.PART) == PolishedToxiteBlock.Part.UPPER
			? models().cubeBottomTop(name(block) + "_upper", blockTexture(block).withSuffix("_upper"), blockTexture(block).withSuffix("_middle"), blockTexture(block)) : models().cubeBottomTop(name(block) + "_lower", blockTexture(block).withSuffix("_lower"), blockTexture(block), blockTexture(block).withSuffix("_middle"))))).build());
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

	private void lumenstem(Block block) {
		ModelFile modelNormal = models().cross(name(block), blockTexture(block)).renderType(CUTOUT);
		ModelFile modelBottom = models().cross(name(block) + "_bottom", blockTexture(block).withSuffix("_bottom")).renderType(CUTOUT);
		onOffBlock(block, LumenstemBlock.BOTTOM, modelBottom, modelNormal);
	}

	private void lumenstemPlant(Block block) {
		getVariantBuilder(block).forAllStates((state -> {
			LumenstemPlantBlock.LumenstemState lumenstemState = state.getValue(LumenstemPlantBlock.LUMENSTEM_STATE);
			ModelFile modelFile = models().cross(name(block) + "_" + lumenstemState.getSerializedName(), blockTexture(block).withSuffix("_" + lumenstemState.getSerializedName())).renderType(CUTOUT);
			return ConfiguredModel.builder().modelFile(modelFile).build();
		}));
	}

	private void mangroveRoots(Block block) {
		cubeColumn(block, blockTexture(block).withSuffix("_top"), blockTexture(block).withSuffix("_side"), CUTOUT_MIPPED);
	}

	private void muddyMangroveRoots(RotatedPillarBlock block) {
		axisBlock(block, models().cubeColumn(name(block), blockTexture(block).withSuffix("_side"), blockTexture(block).withSuffix("_top")).renderType(CUTOUT_MIPPED), models().cubeColumnHorizontal(name(block) + "_horizontal", blockTexture(block).withSuffix("_side"), blockTexture(block).withSuffix("_top")).renderType(CUTOUT_MIPPED));
	}

	private void doomedenKeyhole(Block block, Block redstone) {
		ResourceLocation tiles = blockTexture(ESBlocks.DOOMEDEN_TILES.get());
		ModelFile modelOn = models().cube(name(block) + "_lit", tiles, tiles, blockTexture(block).withSuffix("_on_front"), blockTexture(block).withSuffix("_on_front"), blockTexture(block).withSuffix("_on_side"), blockTexture(block).withSuffix("_on_side")).texture("particle", tiles);
		ModelFile modelOff = models().cube(name(block), tiles, tiles, blockTexture(block).withSuffix("_off_front"), blockTexture(block).withSuffix("_off_front"), blockTexture(block).withSuffix("_off_side"), blockTexture(block).withSuffix("_off_side")).texture("particle", tiles);
		getVariantBuilder(block).forAllStates((state) -> {
			if (state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Direction.Axis.X) {
				return ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.LIT) ? modelOn : modelOff).rotationY(90).build();
			} else {
				return ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.LIT) ? modelOn : modelOff).build();
			}
		});
		ModelFile modelOnRedstone = models().cube(name(redstone) + "_lit", tiles, tiles, blockTexture(redstone).withSuffix("_on"), blockTexture(redstone).withSuffix("_on"), blockTexture(block).withSuffix("_on_side"), blockTexture(block).withSuffix("_on_side")).texture("particle", tiles);
		ModelFile modelOffRedstone = models().cube(name(redstone), tiles, tiles, blockTexture(redstone).withSuffix("_off"), blockTexture(redstone).withSuffix("_off"), blockTexture(block).withSuffix("_off_side"), blockTexture(block).withSuffix("_off_side")).texture("particle", tiles);
		getVariantBuilder(redstone).forAllStates((state) -> {
			if (state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Direction.Axis.X) {
				return ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.LIT) ? modelOnRedstone : modelOffRedstone).rotationY(90).build();
			} else {
				return ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.LIT) ? modelOnRedstone : modelOffRedstone).build();
			}
		});
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

	private void directionalBud(Block block) {
		ModelFile modelFile = models().cross(name(block), blockTexture(block)).renderType(CUTOUT);
		getVariantBuilder(block).forAllStates((state) -> {
			Direction direction = state.getValue(BlockStateProperties.FACING);
			int rotX = direction == Direction.DOWN ? 180 : direction == Direction.UP ? 0 : 90;
			return ConfiguredModel.builder()
				.modelFile(modelFile).rotationY(((int) direction.toYRot() + 180) % 360).rotationX(rotX).build();
		});
	}

	private void directionalDenseCrossBud(Block block) {
		ModelFile modelFile = models().singleTexture(name(block), EternalStarlight.id(ModelProvider.BLOCK_FOLDER + "/dense_cross"), "cross", blockTexture(block)).renderType(CUTOUT);
		getVariantBuilder(block).forAllStates((state) -> {
			Direction direction = state.getValue(BlockStateProperties.FACING);
			int rotX = direction == Direction.DOWN ? 180 : direction == Direction.UP ? 0 : 90;
			return ConfiguredModel.builder()
				.modelFile(modelFile).rotationY(((int) direction.toYRot() + 180) % 360).rotationX(rotX).build();
		});
	}

	private void directionalCubeBottomTop(Block block) {
		ModelFile modelFile = models().cubeBottomTop(name(block), blockTexture(block), blockTexture(block).withSuffix("_bottom"), blockTexture(block).withSuffix("_top"));
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

	private void geyser(Block geyser, Block stone) {
		ModelFile modelFile = models().cubeBottomTop(name(geyser), blockTexture(stone), blockTexture(stone), blockTexture(geyser));
		simpleBlock(geyser, modelFile);
	}

	private void jetBlock(Block block) {
		ModelFile modelFile = models().cubeBottomTop(name(block), blockTexture(block).withSuffix("_side"), blockTexture(block).withSuffix("_side"), blockTexture(block).withSuffix("_top"));
		directionalBlock(block, modelFile);
	}

	private void cubeBottomTop(Block block) {
		ModelFile modelFile = models().cubeBottomTop(name(block), blockTexture(block).withSuffix("_side"), blockTexture(block).withSuffix("_bottom"), blockTexture(block).withSuffix("_top"));
		simpleBlock(block, modelFile);
	}

	private void waterlilyWithFlower(Block lily) {
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
		getMultipartBuilder(lily)
			.part().modelFile(models().cross(name(lily) + "_flower", blockTexture(lily).withSuffix("_flower")).renderType(CUTOUT)).addModel().condition(WaterlilyWithFlowerBlock.LIT, true).end()
			.part().modelFile(model).nextModel()
			.rotationY(270).modelFile(model).nextModel()
			.rotationY(180).modelFile(model).nextModel()
			.rotationY(90).modelFile(model).addModel().end();
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

	private void mushroomLikeBlock(Block block) {
		mushroomLikeBlock(block, name(block), blockTexture(block), name(block) + "_inside", blockTexture(block).withSuffix("_inside"));
	}

	private void mushroomLikeBlock(Block block, String outerName, ResourceLocation outerTexture, String innerName, ResourceLocation innerTexture) {
		ModelFile modelOutside = models().singleTexture(outerName, ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/template_single_face"), outerTexture);
		ModelFile modelInside = models().singleTexture(innerName, ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/template_single_face"), innerTexture);
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

	private void duskEmitter(Block block, String outerName, ResourceLocation outerTexture, String innerName, ResourceLocation innerTexture) {
		ModelFile modelOutside = models().singleTexture(outerName, ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/template_single_face"), outerTexture);
		ModelFile modelInside = models().singleTexture(innerName, ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/template_single_face"), innerTexture);
		getMultipartBuilder(block)
			.part().modelFile(modelOutside).addModel().condition(BlockStateProperties.NORTH, true).end()
			.part().modelFile(modelOutside).uvLock(true).rotationY(90).addModel().condition(BlockStateProperties.EAST, true).end()
			.part().modelFile(modelOutside).uvLock(true).rotationY(180).addModel().condition(BlockStateProperties.SOUTH, true).end()
			.part().modelFile(modelOutside).uvLock(true).rotationY(270).addModel().condition(BlockStateProperties.WEST, true).end()
			.part().modelFile(modelOutside).uvLock(true).rotationX(270).rotationY(180).addModel().condition(BlockStateProperties.UP, true).end()
			.part().modelFile(modelOutside).uvLock(true).rotationX(90).rotationY(180).addModel().condition(BlockStateProperties.DOWN, true).end()
			.part().modelFile(modelInside).addModel().condition(BlockStateProperties.NORTH, false).end()
			.part().modelFile(modelInside).uvLock(false).rotationY(90).addModel().condition(BlockStateProperties.EAST, false).end()
			.part().modelFile(modelInside).uvLock(false).rotationY(180).addModel().condition(BlockStateProperties.SOUTH, false).end()
			.part().modelFile(modelInside).uvLock(false).rotationY(270).addModel().condition(BlockStateProperties.WEST, false).end()
			.part().modelFile(modelInside).uvLock(false).rotationX(270).rotationY(180).addModel().condition(BlockStateProperties.UP, false).end()
			.part().modelFile(modelInside).uvLock(false).rotationX(90).rotationY(180).addModel().condition(BlockStateProperties.DOWN, false).end();
	}

	private void torreyaCampfire(Block block) {
		ResourceLocation log = blockTexture(block).withSuffix("_log");
		ResourceLocation litLog = blockTexture(block).withSuffix("_log_lit");
		ResourceLocation fire = blockTexture(block).withSuffix("_fire");
		ResourceLocation starfire = blockTexture(block).withSuffix("_starfire");
		ModelFile modelNormal = models().withExistingParent(name(block) + "_off", ResourceLocation.withDefaultNamespace("campfire_off")).texture("log", log).texture("particle", log).renderType(CUTOUT);
		ModelFile modelLit = models().withExistingParent(name(block), ResourceLocation.withDefaultNamespace("template_campfire")).texture("log", log).texture("particle", log).texture("lit_log", litLog).texture("fire", fire).renderType(CUTOUT);
		ModelFile modelStarfire = models().withExistingParent(name(block) + "_starfire", ResourceLocation.withDefaultNamespace("template_campfire")).texture("log", log).texture("particle", log).texture("lit_log", litLog).texture("fire", starfire).renderType(CUTOUT);
		getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.LIT) ? (state.getValue(TorreyaCampfireBlock.STARFIRE) ? modelStarfire : modelLit) : modelNormal).rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()).build(), BlockStateProperties.WATERLOGGED, BlockStateProperties.SIGNAL_FIRE);
	}

	private void simpleGrassBlock(Block grassBlock, ResourceLocation dirt) {
		simpleGrassBlock(grassBlock, blockTexture(grassBlock).withSuffix("_side"), blockTexture(grassBlock).withSuffix("_top"), dirt);
	}

	private void simpleGrassBlock(Block grassBlock, ResourceLocation side, ResourceLocation top, ResourceLocation dirt) {
		ModelFile modelFile = models().cubeBottomTop(name(grassBlock), side, dirt, top);
		getVariantBuilder(grassBlock).forAllStates(state -> ConfiguredModel.builder()
			.modelFile(modelFile).nextModel()
			.rotationY(270).modelFile(modelFile).nextModel()
			.rotationY(180).modelFile(modelFile).nextModel()
			.rotationY(90).modelFile(modelFile).build());
	}

	private void snowyDirtBlock(Block block, ResourceLocation dirt, ResourceLocation snowy) {
		ModelFile modelFile = models().cubeBottomTop(name(block), blockTexture(block).withSuffix("_side"), dirt, blockTexture(block).withSuffix("_top"));
		ModelFile snowyModel = models().getExistingFile(snowy);
		getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder()
			.modelFile(state.getValue(BlockStateProperties.SNOWY) ? snowyModel : modelFile).nextModel()
			.rotationY(270).modelFile(state.getValue(BlockStateProperties.SNOWY) ? snowyModel : modelFile).nextModel()
			.rotationY(180).modelFile(state.getValue(BlockStateProperties.SNOWY) ? snowyModel : modelFile).nextModel()
			.rotationY(90).modelFile(state.getValue(BlockStateProperties.SNOWY) ? snowyModel : modelFile).build());
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

	private void sand(Block sand) {
		ModelFile modelFile = models().cubeAll(name(sand), blockTexture(sand));
		getVariantBuilder(sand).forAllStates(state -> ConfiguredModel.builder()
			.modelFile(modelFile).nextModel()
			.rotationY(270).modelFile(modelFile).nextModel()
			.rotationY(180).modelFile(modelFile).nextModel()
			.rotationY(90).modelFile(modelFile).build());
	}

	private void suspiciousBlock(Block block) {
		getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder()
			.modelFile(models().cubeAll(name(block) + "_" + state.getValue(BlockStateProperties.DUSTED), blockTexture(block).withSuffix("_" + state.getValue(BlockStateProperties.DUSTED)))).build());
	}

	private void randomlyMirroredAndRotatedBlock(Block stone) {
		ModelFile normal = models().cubeAll(name(stone), blockTexture(stone));
		ModelFile mirrored = models().singleTexture(name(stone) + "_mirrored", ResourceLocation.withDefaultNamespace("block/cube_mirrored_all"), "all", blockTexture(stone));
		getVariantBuilder(stone).forAllStates(state -> ConfiguredModel.builder()
			.modelFile(normal).nextModel()
			.modelFile(mirrored).nextModel()
			.rotationY(180).modelFile(normal).nextModel()
			.rotationY(180).modelFile(mirrored).build());
	}

	private void randomlyMirroredBlock(Block stone) {
		ModelFile normal = models().cubeAll(name(stone), blockTexture(stone));
		ModelFile mirrored = models().singleTexture(name(stone) + "_mirrored", ResourceLocation.withDefaultNamespace("block/cube_mirrored_all"), "all", blockTexture(stone));
		getVariantBuilder(stone).forAllStates(state -> ConfiguredModel.builder()
			.modelFile(normal).nextModel()
			.modelFile(mirrored).build());
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

	private void dirtPath(Block dirtPath, Block dirt) {
		getVariantBuilder(dirtPath).forAllStates((state -> {
			ModelFile modelFile = models().withExistingParent(name(dirtPath), ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/block"))
				.texture("top", blockTexture(dirtPath).withSuffix("_top"))
				.texture("side", blockTexture(dirtPath).withSuffix("_side"))
				.texture("bottom", blockTexture(dirt))
				.texture("particle", blockTexture(dirt))
				.element()
				.from(0, 0, 0)
				.to(16, 15, 16)
				.face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#bottom").cullface(Direction.DOWN).end()
				.face(Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
				.face(Direction.NORTH).uvs(0, 1, 16, 16).texture("#side").cullface(Direction.NORTH).end()
				.face(Direction.SOUTH).uvs(0, 1, 16, 16).texture("#side").cullface(Direction.SOUTH).end()
				.face(Direction.WEST).uvs(0, 1, 16, 16).texture("#side").cullface(Direction.WEST).end()
				.face(Direction.EAST).uvs(0, 1, 16, 16).texture("#side").cullface(Direction.EAST).end()
				.end();
			return ConfiguredModel.builder().modelFile(modelFile).nextModel()
				.rotationY(270).modelFile(modelFile).nextModel()
				.rotationY(180).modelFile(modelFile).nextModel()
				.rotationY(90).modelFile(modelFile).build();
		}));
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

	private void candleCake(Block candleCake, Block cake, Block candle) {
		ModelFile normal = models().withExistingParent(name(candleCake), "template_cake_with_candle")
			.texture("bottom", blockTexture(cake).withSuffix("_bottom"))
			.texture("side", blockTexture(cake).withSuffix("_side"))
			.texture("top", blockTexture(cake).withSuffix("_top"))
			.texture("candle", blockTexture(candle))
			.texture("particle", blockTexture(cake).withSuffix("_side"));
		ModelFile lit = models().withExistingParent(name(candleCake) + "_lit", "template_cake_with_candle")
			.texture("bottom", blockTexture(cake).withSuffix("_bottom"))
			.texture("side", blockTexture(cake).withSuffix("_side"))
			.texture("top", blockTexture(cake).withSuffix("_top"))
			.texture("candle", blockTexture(candle).withSuffix("_lit"))
			.texture("particle", blockTexture(cake).withSuffix("_side"));
		getVariantBuilder(candleCake)
			.partialState().with(BlockStateProperties.LIT, false)
			.modelForState().modelFile(normal).addModel()
			.partialState().with(BlockStateProperties.LIT, true)
			.modelForState().modelFile(lit).addModel();
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

	private void redstoneOre(Block block) {
		ModelFile on = models().cubeAll(name(block), blockTexture(block));
		ModelFile off = models().cubeAll(name(block) + "_off", blockTexture(block).withSuffix("_off"));
		onOffBlock(block, BlockStateProperties.LIT, on, off);
	}

	private void snowyLeaves(Block leaves) {
		ModelFile modelNormal = models().singleTexture(name(leaves), ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/leaves"), "all", blockTexture(leaves)).renderType(CUTOUT_MIPPED);
		ModelFile modelSnowy = models().cubeBottomTop(name(leaves) + "_snowy", blockTexture(leaves).withSuffix("_snowy"), blockTexture(leaves), blockTexture(leaves)).renderType(CUTOUT_MIPPED);
		onOffBlock(leaves, BlockStateProperties.SNOWY, modelSnowy, modelNormal);
	}

	private void shadegrieve(Block block) {
		ModelFile modelNormal = cubeAll(block);
		ModelFile modelTop = models().withExistingParent(name(block) + "_top", EternalStarlight.id("template_up_spreading_plant")).renderType(CUTOUT)
			.texture("cube", blockTexture(block)).texture("upper", blockTexture(block).withSuffix("_upper"));
		onOffBlock(block, ShadegrieveBlock.TOP, modelTop, modelNormal);
	}

	private void caveMossFull(Block block) {
		ModelFile modelNormal = models().singleTexture(name(block), EternalStarlight.id("block/tinted_cube_all"), "all", blockTexture(block));
		ModelFile modelBottom = models().withExistingParent(name(block) + "_bottom", EternalStarlight.id("template_down_spreading_plant")).renderType(CUTOUT)
			.texture("cube", blockTexture(block)).texture("lower", blockTexture(block).withSuffix("_lower"));
		onOffBlock(block, CaveMossFullBlock.BOTTOM, modelBottom, modelNormal);
	}

	private void onOffBlock(Block block, BooleanProperty property, ModelFile on, ModelFile off) {
		getVariantBuilder(block)
			.partialState().with(property, false)
			.modelForState().modelFile(off).addModel()
			.partialState().with(property, true)
			.modelForState().modelFile(on).addModel();
	}

	private void directionalOnOffBlock(Block block, BooleanProperty property, ModelFile on, ModelFile off, Property<?>... ignored) {
		getVariantBuilder(block).forAllStatesExcept(state -> {
			Direction direction = state.getValue(BlockStateProperties.FACING);
			int rotX = direction == Direction.DOWN ? 180 : direction == Direction.UP ? 0 : 90;
			return ConfiguredModel.builder().modelFile(state.getValue(property) ? on : off).rotationX(rotX).rotationY(((int) direction.toYRot() + 180) % 360).build();
		}, ignored);
	}

	private void accumulator(Block block) {
		ModelFile modelFile = models().withExistingParent(name(block), ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/block")).renderType(CUTOUT_MIPPED)
			.texture("particle", blockTexture(block)).texture("top", blockTexture(block).withSuffix("_top")).texture("top_overlay", blockTexture(block).withSuffix("_top_overlay")).texture("side", blockTexture(block)).texture("side_overlay", blockTexture(block).withSuffix("_overlay"))
			.element()
			.from(0, 0, 0)
			.to(16, 16, 16)
			.allFaces((dir, builder) -> builder.uvs(0, 0, 16, 16)
				.texture("#" + (dir == Direction.UP ? "top" : "side"))
				.cullface(dir)
				.end())
			.end()
			.element()
			.from(0, 0, 0)
			.to(16, 16, 16)
			.allFaces((dir, builder) -> builder.uvs(0, 0, 16, 16)
				.texture("#" + (dir == Direction.UP ? "top_overlay" : "side_overlay"))
				.cullface(dir)
				.tintindex(0)
				.end())
			.end();
		directionalBlock(block, modelFile);
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

	private void icicle(Block block) {
		getVariantBuilder(block).forAllStates((state -> {
			IcicleBlock.IcicleThickness thickness = state.getValue(IcicleBlock.THICKNESS);
			ModelFile modelFile = models().cross(name(block) + "_" + thickness.getSerializedName(), blockTexture(block).withSuffix("_" + thickness.getSerializedName())).renderType(CUTOUT);
			return ConfiguredModel.builder().modelFile(modelFile).rotationX(state.getValue(IcicleBlock.TIP_DIRECTION) == Direction.UP ? 0 : 180).build();
		}));
	}

	private void speleothem(Block block) {
		getVariantBuilder(block).forAllStates((state -> {
			String dir = state.getValue(SpeleothemBlock.TIP_DIRECTION) == Direction.UP ? "up" : "down";
			String thickness = state.getValue(SpeleothemBlock.THICKNESS).getSerializedName();
			String suffix = "_" + dir + "_" + thickness;
			ModelFile modelFile = models().cross(name(block) + suffix, blockTexture(block).withSuffix(suffix)).renderType(CUTOUT);
			return ConfiguredModel.builder().modelFile(modelFile).build();
		}));
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

	private void tintedCubeAll(Block block, ResourceLocation texture, ResourceLocation renderType) {
		simpleBlock(block, models().singleTexture(name(block), EternalStarlight.id("block/tinted_cube_all"), "all", texture).renderType(renderType));
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
		simpleBlock(block, models().singleTexture(name(block), EternalStarlight.id("block/tinted_carpet"), "wool", wool));
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
