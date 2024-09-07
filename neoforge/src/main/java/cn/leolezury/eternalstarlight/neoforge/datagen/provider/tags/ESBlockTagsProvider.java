package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESConventionalTags;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESBlockTagsProvider extends BlockTagsProvider {
	public ESBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		// conventional tags
		tag(Tags.Blocks.BUDDING_BLOCKS).add(
			ESBlocks.BUDDING_THIOQUARTZ.get()
		);
		tag(Tags.Blocks.BUDS).add(
			ESBlocks.FANTABUD.get(),
			ESBlocks.GREEN_FANTABUD.get(),
			ESBlocks.ORANGE_SCARLET_BUD.get(),
			ESBlocks.PURPLE_SCARLET_BUD.get(),
			ESBlocks.RED_SCARLET_BUD.get()
		);
		tag(Tags.Blocks.CLUSTERS).add(
			ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get(),
			ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(),
			ESBlocks.THIOQUARTZ_CLUSTER.get()
		);
		tag(Tags.Blocks.COBBLESTONES).add(
			ESBlocks.COBBLED_GRIMSTONE.get(),
			ESBlocks.COBBLED_VOIDSTONE.get()
		);
		tag(Tags.Blocks.DYED_BLACK).add(
			ESBlocks.BLACK_YETI_FUR.get(),
			ESBlocks.BLACK_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_BLUE).add(
			ESBlocks.BLUE_YETI_FUR.get(),
			ESBlocks.BLUE_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_BROWN).add(
			ESBlocks.BROWN_YETI_FUR.get(),
			ESBlocks.BROWN_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_CYAN).add(
			ESBlocks.CYAN_YETI_FUR.get(),
			ESBlocks.CYAN_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_GRAY).add(
			ESBlocks.GRAY_YETI_FUR.get(),
			ESBlocks.GRAY_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_GREEN).add(
			ESBlocks.GREEN_YETI_FUR.get(),
			ESBlocks.GREEN_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_LIGHT_BLUE).add(
			ESBlocks.LIGHT_BLUE_YETI_FUR.get(),
			ESBlocks.LIGHT_BLUE_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_LIGHT_GRAY).add(
			ESBlocks.LIGHT_GRAY_YETI_FUR.get(),
			ESBlocks.LIGHT_GRAY_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_LIME).add(
			ESBlocks.LIME_YETI_FUR.get(),
			ESBlocks.LIME_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_MAGENTA).add(
			ESBlocks.MAGENTA_YETI_FUR.get(),
			ESBlocks.MAGENTA_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_ORANGE).add(
			ESBlocks.ORANGE_YETI_FUR.get(),
			ESBlocks.ORANGE_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_PINK).add(
			ESBlocks.PINK_YETI_FUR.get(),
			ESBlocks.PINK_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_PURPLE).add(
			ESBlocks.PURPLE_YETI_FUR.get(),
			ESBlocks.PURPLE_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_RED).add(
			ESBlocks.RED_YETI_FUR.get(),
			ESBlocks.RED_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_WHITE).add(
			ESBlocks.WHITE_YETI_FUR.get(),
			ESBlocks.WHITE_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.DYED_YELLOW).add(
			ESBlocks.YELLOW_YETI_FUR.get(),
			ESBlocks.YELLOW_YETI_FUR_CARPET.get()
		);
		tag(Tags.Blocks.FENCE_GATES).add(
			ESBlocks.LUNAR_MOSAIC_FENCE_GATE.get()
		);
		tag(Tags.Blocks.FENCE_GATES_WOODEN).add(
			ESBlocks.LUNAR_FENCE_GATE.get(),
			ESBlocks.NORTHLAND_FENCE_GATE.get(),
			ESBlocks.STARLIGHT_MANGROVE_FENCE_GATE.get(),
			ESBlocks.SCARLET_FENCE_GATE.get(),
			ESBlocks.TORREYA_FENCE_GATE.get()
		);
		tag(Tags.Blocks.FENCES).add(
			ESBlocks.LUNAR_MOSAIC_FENCE.get()
		);
		tag(Tags.Blocks.FENCES_WOODEN).add(
			ESBlocks.LUNAR_FENCE.get(),
			ESBlocks.NORTHLAND_FENCE.get(),
			ESBlocks.STARLIGHT_MANGROVE_FENCE.get(),
			ESBlocks.SCARLET_FENCE.get(),
			ESBlocks.TORREYA_FENCE.get()
		);
		tag(Tags.Blocks.GRAVELS).add(
			ESBlocks.DUSTED_GRAVEL.get()
		);
		tag(Tags.Blocks.GRAVELS).add(
			ESBlocks.DUSTED_GRAVEL.get()
		);
		tag(Tags.Blocks.ORE_RATES_DENSE).add(
			ESBlocks.GRIMSTONE_REDSTONE_ORE.get(),
			ESBlocks.GRIMSTONE_SALTPETER_ORE.get(),
			ESBlocks.VOIDSTONE_REDSTONE_ORE.get(),
			ESBlocks.VOIDSTONE_SALTPETER_ORE.get(),
			ESBlocks.SWAMP_SILVER_ORE.get()
		);
		tag(Tags.Blocks.ORE_RATES_SPARSE).add(
			ESBlocks.THERMAL_SPRINGSTONE.get(),
			ESBlocks.GRIMSTONE_ATALPHAITE_ORE.get(),
			ESBlocks.VOIDSTONE_ATALPHAITE_ORE.get()
		);
		tag(Tags.Blocks.ORES).add(
			ESBlocks.GRIMSTONE_ATALPHAITE_ORE.get(),
			ESBlocks.GRIMSTONE_REDSTONE_ORE.get(),
			ESBlocks.GRIMSTONE_SALTPETER_ORE.get(),
			ESBlocks.VOIDSTONE_ATALPHAITE_ORE.get(),
			ESBlocks.VOIDSTONE_REDSTONE_ORE.get(),
			ESBlocks.VOIDSTONE_SALTPETER_ORE.get(),
			ESBlocks.SWAMP_SILVER_ORE.get(),
			ESBlocks.THERMAL_SPRINGSTONE.get()
		);
		tag(Tags.Blocks.ORES_REDSTONE).add(
			ESBlocks.GRIMSTONE_REDSTONE_ORE.get(),
			ESBlocks.VOIDSTONE_REDSTONE_ORE.get()
		);
		tag(Tags.Blocks.SANDS).add(
			ESBlocks.TWILIGHT_SAND.get()
		);
		tag(Tags.Blocks.SANDS).add(
			ESBlocks.TWILIGHT_SAND.get()
		);
		tag(Tags.Blocks.SANDSTONE_BLOCKS).add(
			ESBlocks.TWILIGHT_SANDSTONE.get(),
			ESBlocks.CUT_TWILIGHT_SANDSTONE.get(),
			ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get()
		);
		tag(Tags.Blocks.SANDSTONE_SLABS).add(
			ESBlocks.TWILIGHT_SANDSTONE_SLAB.get(),
			ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get()
		);
		tag(Tags.Blocks.SANDSTONE_STAIRS).add(
			ESBlocks.TWILIGHT_SANDSTONE_STAIRS.get(),
			ESBlocks.CUT_TWILIGHT_SANDSTONE_STAIRS.get()
		);
		tag(Tags.Blocks.SKULLS).add(
			ESBlocks.TANGLED_SKULL.get(),
			ESBlocks.TANGLED_WALL_SKULL.get()
		);
		tag(Tags.Blocks.STONES).add(
			ESBlocks.GRIMSTONE.get(),
			ESBlocks.VOIDSTONE.get(),
			ESBlocks.TWILIGHT_SANDSTONE.get(),
			ESBlocks.TOXITE.get(),
			ESBlocks.ABYSSLATE.get(),
			ESBlocks.THERMABYSSLATE.get(),
			ESBlocks.CRYOBYSSLATE.get(),
			ESBlocks.STELLAGMITE.get(),
			ESBlocks.NEBULAITE.get(),
			ESBlocks.ETERNAL_ICE.get()
		);
		tag(Tags.Blocks.STORAGE_BLOCKS).add(
			ESBlocks.ATALPHAITE_BLOCK.get(),
			ESBlocks.LUNARIS_CACTUS_GEL_BLOCK.get(),
			ESBlocks.THIOQUARTZ_BLOCK.get(),
			ESBlocks.SWAMP_SILVER_BLOCK.get(),
			ESBlocks.RAW_AETHERSENT_BLOCK.get(),
			ESBlocks.AETHERSENT_BLOCK.get(),
			ESBlocks.SALTPETER_BLOCK.get(),
			ESBlocks.GOLEM_STEEL_BLOCK.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get(),
			ESBlocks.LUNAR_MOSAIC.get()
		);
		// mod conventional tags
		tag(ESConventionalTags.Blocks.STORAGE_BLOCKS_GOLEM_STEEL).add(
			ESBlocks.GOLEM_STEEL_BLOCK.get()
		);
		tag(ESConventionalTags.Blocks.STORAGE_BLOCKS_OXIDIZED_GOLEM_STEEL).add(
			ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get()
		);
		tag(ESConventionalTags.Blocks.STORAGE_BLOCKS_RAW_AETHERSENT).add(
			ESBlocks.RAW_AETHERSENT_BLOCK.get()
		);
		tag(ESConventionalTags.Blocks.STORAGE_BLOCKS_AETHERSENT).add(
			ESBlocks.AETHERSENT_BLOCK.get()
		);
		tag(ESConventionalTags.Blocks.ORES_SWAMP_SILVER).add(
			ESBlocks.SWAMP_SILVER_ORE.get()
		);
		tag(ESConventionalTags.Blocks.STORAGE_BLOCKS_SWAMP_SILVER).add(
			ESBlocks.SWAMP_SILVER_BLOCK.get()
		);
		tag(ESConventionalTags.Blocks.ORES_SALTPETER).add(
			ESBlocks.GRIMSTONE_SALTPETER_ORE.get(),
			ESBlocks.VOIDSTONE_SALTPETER_ORE.get()
		);
		tag(ESConventionalTags.Blocks.STORAGE_BLOCKS_SALTPETER).add(
			ESBlocks.SALTPETER_BLOCK.get()
		);
		tag(ESConventionalTags.Blocks.ORES_IN_GROUND_GRIMSTONE).add(
			ESBlocks.GRIMSTONE_ATALPHAITE_ORE.get(),
			ESBlocks.GRIMSTONE_REDSTONE_ORE.get(),
			ESBlocks.GRIMSTONE_SALTPETER_ORE.get()
		);
		tag(ESConventionalTags.Blocks.ORES_IN_GROUND_VOIDSTONE).add(
			ESBlocks.VOIDSTONE_ATALPHAITE_ORE.get(),
			ESBlocks.VOIDSTONE_REDSTONE_ORE.get(),
			ESBlocks.VOIDSTONE_SALTPETER_ORE.get()
		);
		tag(ESConventionalTags.Blocks.ORES_IN_GROUND_NIGHTFALL_MUD).add(
			ESBlocks.SWAMP_SILVER_ORE.get()
		);
		// mod tags
		tag(ESTags.Blocks.LUNAR_LOGS).add(
			ESBlocks.LUNAR_LOG.get(),
			ESBlocks.LUNAR_WOOD.get(),
			ESBlocks.STRIPPED_LUNAR_LOG.get(),
			ESBlocks.STRIPPED_LUNAR_WOOD.get(),
			ESBlocks.DEAD_LUNAR_LOG.get(),
			ESBlocks.RED_CRYSTALLIZED_LUNAR_LOG.get(),
			ESBlocks.BLUE_CRYSTALLIZED_LUNAR_LOG.get()
		);
		tag(ESTags.Blocks.NORTHLAND_LOGS).add(
			ESBlocks.NORTHLAND_LOG.get(),
			ESBlocks.NORTHLAND_WOOD.get(),
			ESBlocks.STRIPPED_NORTHLAND_LOG.get(),
			ESBlocks.STRIPPED_NORTHLAND_WOOD.get()
		);
		tag(ESTags.Blocks.STARLIGHT_MANGROVE_LOGS).add(
			ESBlocks.STARLIGHT_MANGROVE_LOG.get(),
			ESBlocks.STARLIGHT_MANGROVE_WOOD.get(),
			ESBlocks.STRIPPED_STARLIGHT_MANGROVE_LOG.get(),
			ESBlocks.STRIPPED_STARLIGHT_MANGROVE_WOOD.get()
		);
		tag(ESTags.Blocks.SCARLET_LOGS).add(
			ESBlocks.SCARLET_LOG.get(),
			ESBlocks.SCARLET_WOOD.get(),
			ESBlocks.STRIPPED_SCARLET_LOG.get(),
			ESBlocks.STRIPPED_SCARLET_WOOD.get()
		);
		tag(ESTags.Blocks.TORREYA_LOGS).add(
			ESBlocks.TORREYA_LOG.get(),
			ESBlocks.TORREYA_WOOD.get(),
			ESBlocks.STRIPPED_TORREYA_LOG.get(),
			ESBlocks.STRIPPED_TORREYA_WOOD.get()
		);
		tag(ESTags.Blocks.PORTAL_FRAME_BLOCKS).add(
			ESBlocks.CHISELED_VOIDSTONE.get()
		);
		tag(ESTags.Blocks.BASE_STONE_STARLIGHT).add(
			ESBlocks.GRIMSTONE.get(),
			ESBlocks.VOIDSTONE.get(),
			ESBlocks.ETERNAL_ICE.get(),
			Blocks.STONE,
			Blocks.DEEPSLATE
		);
		tag(ESTags.Blocks.STARLIGHT_CARVER_REPLACEABLES).add(
			Blocks.SNOW
		).addTags(
			ESTags.Blocks.BASE_STONE_STARLIGHT,
			BlockTags.DIRT,
			BlockTags.SAND
		);
		tag(ESTags.Blocks.CORAL_PLANTS).add(
			ESBlocks.TENTACLES_CORAL.get(),
			ESBlocks.GOLDEN_CORAL.get(),
			ESBlocks.CRYSTALLUM_CORAL.get()
		);
		tag(ESTags.Blocks.CORALS).addTag(ESTags.Blocks.CORAL_PLANTS).add(
			ESBlocks.TENTACLES_CORAL_FAN.get(),
			ESBlocks.GOLDEN_CORAL_FAN.get(),
			ESBlocks.CRYSTALLUM_CORAL_FAN.get()
		);
		tag(ESTags.Blocks.WALL_CORALS).add(
			ESBlocks.TENTACLES_CORAL_WALL_FAN.get(),
			ESBlocks.GOLDEN_CORAL_WALL_FAN.get(),
			ESBlocks.CRYSTALLUM_CORAL_WALL_FAN.get()
		);
		tag(ESTags.Blocks.CORAL_BLOCKS).add(
			ESBlocks.TENTACLES_CORAL_BLOCK.get(),
			ESBlocks.GOLDEN_CORAL_BLOCK.get(),
			ESBlocks.CRYSTALLUM_CORAL_BLOCK.get()
		);
		tag(ESTags.Blocks.YETI_FUR).add(
			ESBlocks.WHITE_YETI_FUR.get(),
			ESBlocks.ORANGE_YETI_FUR.get(),
			ESBlocks.MAGENTA_YETI_FUR.get(),
			ESBlocks.LIGHT_BLUE_YETI_FUR.get(),
			ESBlocks.YELLOW_YETI_FUR.get(),
			ESBlocks.LIME_YETI_FUR.get(),
			ESBlocks.PINK_YETI_FUR.get(),
			ESBlocks.GRAY_YETI_FUR.get(),
			ESBlocks.LIGHT_GRAY_YETI_FUR.get(),
			ESBlocks.CYAN_YETI_FUR.get(),
			ESBlocks.PURPLE_YETI_FUR.get(),
			ESBlocks.BLUE_YETI_FUR.get(),
			ESBlocks.BROWN_YETI_FUR.get(),
			ESBlocks.GREEN_YETI_FUR.get(),
			ESBlocks.RED_YETI_FUR.get(),
			ESBlocks.BLACK_YETI_FUR.get()
		);
		tag(ESTags.Blocks.YETI_FUR_CARPETS).add(
			ESBlocks.WHITE_YETI_FUR_CARPET.get(),
			ESBlocks.ORANGE_YETI_FUR_CARPET.get(),
			ESBlocks.MAGENTA_YETI_FUR_CARPET.get(),
			ESBlocks.LIGHT_BLUE_YETI_FUR_CARPET.get(),
			ESBlocks.YELLOW_YETI_FUR_CARPET.get(),
			ESBlocks.LIME_YETI_FUR_CARPET.get(),
			ESBlocks.PINK_YETI_FUR_CARPET.get(),
			ESBlocks.GRAY_YETI_FUR_CARPET.get(),
			ESBlocks.LIGHT_GRAY_YETI_FUR_CARPET.get(),
			ESBlocks.CYAN_YETI_FUR_CARPET.get(),
			ESBlocks.PURPLE_YETI_FUR_CARPET.get(),
			ESBlocks.BLUE_YETI_FUR_CARPET.get(),
			ESBlocks.BROWN_YETI_FUR_CARPET.get(),
			ESBlocks.GREEN_YETI_FUR_CARPET.get(),
			ESBlocks.RED_YETI_FUR_CARPET.get(),
			ESBlocks.BLACK_YETI_FUR_CARPET.get()
		);
		tag(ESTags.Blocks.ABYSSAL_FIRE_SURVIVES_ON).add(
			ESBlocks.ABYSSAL_MAGMA_BLOCK.get(),
			ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get(),
			ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get()
		);
		tag(ESTags.Blocks.ABYSSLATES).add(
			ESBlocks.ABYSSLATE.get(),
			ESBlocks.THERMABYSSLATE.get(),
			ESBlocks.CRYOBYSSLATE.get()
		);
		tag(ESTags.Blocks.AETHERSENT_METEOR_REPLACEABLES).addTags(
			BlockTags.LEAVES,
			BlockTags.LOGS,
			BlockTags.DIRT
		);
		tag(ESTags.Blocks.DOOMEDEN_KEYHOLE_DESTROYABLES).add(
			ESBlocks.DOOMEDEN_BRICKS.get(),
			ESBlocks.POLISHED_DOOMEDEN_BRICKS.get(),
			ESBlocks.DOOMEDEN_TILES.get(),
			ESBlocks.CHISELED_POLISHED_DOOMEDEN_BRICKS.get(),
			ESBlocks.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get(),
			ESBlocks.DOOMEDEN_LIGHT.get(),
			ESBlocks.DOOMEDEN_KEYHOLE.get(),
			ESBlocks.REDSTONE_DOOMEDEN_KEYHOLE.get()
		);
		tag(ESTags.Blocks.DUSK_LIGHT_ENERGY_SOURCES).add(
			ESBlocks.ATALPHAITE_BLOCK.get(),
			ESBlocks.BLAZING_ATALPHAITE_BLOCK.get(),
			ESBlocks.ATALPHAITE_LIGHT.get()
		);
		// mc tags
		tag(BlockTags.LOGS).addTags(
			ESTags.Blocks.LUNAR_LOGS,
			ESTags.Blocks.NORTHLAND_LOGS,
			ESTags.Blocks.STARLIGHT_MANGROVE_LOGS,
			ESTags.Blocks.SCARLET_LOGS,
			ESTags.Blocks.TORREYA_LOGS
		);
		tag(BlockTags.LOGS_THAT_BURN).addTags(
			ESTags.Blocks.LUNAR_LOGS,
			ESTags.Blocks.NORTHLAND_LOGS,
			ESTags.Blocks.STARLIGHT_MANGROVE_LOGS,
			ESTags.Blocks.SCARLET_LOGS,
			ESTags.Blocks.TORREYA_LOGS
		);
		tag(BlockTags.SAPLINGS).add(
			ESBlocks.LUNAR_SAPLING.get(),
			ESBlocks.NORTHLAND_SAPLING.get(),
			ESBlocks.STARLIGHT_MANGROVE_SAPLING.get(),
			ESBlocks.SCARLET_SAPLING.get(),
			ESBlocks.TORREYA_SAPLING.get()
		);
		tag(BlockTags.LEAVES).add(
			ESBlocks.LUNAR_LEAVES.get(),
			ESBlocks.NORTHLAND_LEAVES.get(),
			ESBlocks.STARLIGHT_MANGROVE_LEAVES.get(),
			ESBlocks.SCARLET_LEAVES.get(),
			ESBlocks.TORREYA_LEAVES.get()
		);
		tag(BlockTags.PLANKS).add(
			ESBlocks.LUNAR_PLANKS.get(),
			ESBlocks.NORTHLAND_PLANKS.get(),
			ESBlocks.STARLIGHT_MANGROVE_PLANKS.get(),
			ESBlocks.SCARLET_PLANKS.get(),
			ESBlocks.TORREYA_PLANKS.get()
		);
		tag(BlockTags.WOODEN_FENCES).add(
			ESBlocks.LUNAR_FENCE.get(),
			ESBlocks.NORTHLAND_FENCE.get(),
			ESBlocks.STARLIGHT_MANGROVE_FENCE.get(),
			ESBlocks.SCARLET_FENCE.get(),
			ESBlocks.TORREYA_FENCE.get(),
			ESBlocks.LUNAR_MOSAIC_FENCE.get()
		);
		tag(BlockTags.FENCE_GATES).add(
			ESBlocks.LUNAR_FENCE_GATE.get(),
			ESBlocks.NORTHLAND_FENCE_GATE.get(),
			ESBlocks.STARLIGHT_MANGROVE_FENCE_GATE.get(),
			ESBlocks.SCARLET_FENCE_GATE.get(),
			ESBlocks.TORREYA_FENCE_GATE.get(),
			ESBlocks.LUNAR_MOSAIC_FENCE_GATE.get()
		);
		tag(BlockTags.WOODEN_SLABS).add(
			ESBlocks.LUNAR_SLAB.get(),
			ESBlocks.NORTHLAND_SLAB.get(),
			ESBlocks.STARLIGHT_MANGROVE_SLAB.get(),
			ESBlocks.SCARLET_SLAB.get(),
			ESBlocks.TORREYA_SLAB.get(),
			ESBlocks.LUNAR_MOSAIC_SLAB.get()
		);
		tag(BlockTags.SLABS).add(
			ESBlocks.COBBLED_GRIMSTONE_SLAB.get(),
			ESBlocks.GRIMSTONE_BRICK_SLAB.get(),
			ESBlocks.POLISHED_GRIMSTONE_SLAB.get(),
			ESBlocks.GRIMSTONE_TILE_SLAB.get(),
			ESBlocks.COBBLED_VOIDSTONE_SLAB.get(),
			ESBlocks.VOIDSTONE_BRICK_SLAB.get(),
			ESBlocks.POLISHED_VOIDSTONE_SLAB.get(),
			ESBlocks.VOIDSTONE_TILE_SLAB.get(),
			ESBlocks.ETERNAL_ICE_BRICK_SLAB.get(),
			ESBlocks.NEBULAITE_BRICK_SLAB.get(),
			ESBlocks.RADIANITE_SLAB.get(),
			ESBlocks.POLISHED_RADIANITE_SLAB.get(),
			ESBlocks.RADIANITE_BRICK_SLAB.get(),
			ESBlocks.FLARE_BRICK_SLAB.get(),
			ESBlocks.FLARE_TILE_SLAB.get(),
			ESBlocks.STELLAGMITE_SLAB.get(),
			ESBlocks.MOLTEN_STELLAGMITE_SLAB.get(),
			ESBlocks.POLISHED_STELLAGMITE_SLAB.get(),
			ESBlocks.POLISHED_ABYSSLATE_SLAB.get(),
			ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_SLAB.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_SLAB.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(),
			ESBlocks.TOXITE_SLAB.get(),
			ESBlocks.POLISHED_TOXITE_SLAB.get(),
			ESBlocks.NIGHTFALL_MUD_BRICK_SLAB.get(),
			ESBlocks.TWILIGHT_SANDSTONE_SLAB.get(),
			ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get(),
			ESBlocks.DUSTED_BRICK_SLAB.get(),
			ESBlocks.GOLEM_STEEL_SLAB.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(),
			ESBlocks.GOLEM_STEEL_TILE_SLAB.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(),
			ESBlocks.DOOMEDEN_BRICK_SLAB.get(),
			ESBlocks.POLISHED_DOOMEDEN_BRICK_SLAB.get(),
			ESBlocks.DOOMEDEN_TILE_SLAB.get(),
			ESBlocks.SPRINGSTONE_SLAB.get(),
			ESBlocks.SPRINGSTONE_BRICK_SLAB.get(),
			ESBlocks.POLISHED_SPRINGSTONE_SLAB.get(),
			ESBlocks.THERMAL_SPRINGSTONE_SLAB.get(),
			ESBlocks.THERMAL_SPRINGSTONE_BRICK_SLAB.get()
		);
		tag(BlockTags.WOODEN_STAIRS).add(
			ESBlocks.LUNAR_STAIRS.get(),
			ESBlocks.NORTHLAND_STAIRS.get(),
			ESBlocks.STARLIGHT_MANGROVE_STAIRS.get(),
			ESBlocks.SCARLET_STAIRS.get(),
			ESBlocks.TORREYA_STAIRS.get(),
			ESBlocks.LUNAR_MOSAIC_STAIRS.get()
		);
		tag(BlockTags.STAIRS).add(
			ESBlocks.COBBLED_GRIMSTONE_STAIRS.get(),
			ESBlocks.GRIMSTONE_BRICK_STAIRS.get(),
			ESBlocks.POLISHED_GRIMSTONE_STAIRS.get(),
			ESBlocks.GRIMSTONE_TILE_STAIRS.get(),
			ESBlocks.COBBLED_VOIDSTONE_STAIRS.get(),
			ESBlocks.VOIDSTONE_BRICK_STAIRS.get(),
			ESBlocks.POLISHED_VOIDSTONE_STAIRS.get(),
			ESBlocks.VOIDSTONE_TILE_STAIRS.get(),
			ESBlocks.ETERNAL_ICE_BRICK_STAIRS.get(),
			ESBlocks.NEBULAITE_BRICK_STAIRS.get(),
			ESBlocks.RADIANITE_STAIRS.get(),
			ESBlocks.POLISHED_RADIANITE_STAIRS.get(),
			ESBlocks.RADIANITE_BRICK_STAIRS.get(),
			ESBlocks.FLARE_BRICK_STAIRS.get(),
			ESBlocks.FLARE_TILE_STAIRS.get(),
			ESBlocks.STELLAGMITE_STAIRS.get(),
			ESBlocks.MOLTEN_STELLAGMITE_STAIRS.get(),
			ESBlocks.POLISHED_STELLAGMITE_STAIRS.get(),
			ESBlocks.POLISHED_ABYSSLATE_STAIRS.get(),
			ESBlocks.POLISHED_ABYSSLATE_BRICK_STAIRS.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_STAIRS.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_STAIRS.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(),
			ESBlocks.TOXITE_STAIRS.get(),
			ESBlocks.POLISHED_TOXITE_STAIRS.get(),
			ESBlocks.NIGHTFALL_MUD_BRICK_STAIRS.get(),
			ESBlocks.TWILIGHT_SANDSTONE_STAIRS.get(),
			ESBlocks.CUT_TWILIGHT_SANDSTONE_STAIRS.get(),
			ESBlocks.DUSTED_BRICK_STAIRS.get(),
			ESBlocks.GOLEM_STEEL_STAIRS.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get(),
			ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(),
			ESBlocks.DOOMEDEN_BRICK_STAIRS.get(),
			ESBlocks.POLISHED_DOOMEDEN_BRICK_STAIRS.get(),
			ESBlocks.DOOMEDEN_TILE_STAIRS.get(),
			ESBlocks.SPRINGSTONE_STAIRS.get(),
			ESBlocks.SPRINGSTONE_BRICK_STAIRS.get(),
			ESBlocks.POLISHED_SPRINGSTONE_STAIRS.get(),
			ESBlocks.THERMAL_SPRINGSTONE_STAIRS.get(),
			ESBlocks.THERMAL_SPRINGSTONE_BRICK_STAIRS.get()
		);
		tag(BlockTags.WALLS).add(
			ESBlocks.COBBLED_GRIMSTONE_WALL.get(),
			ESBlocks.GRIMSTONE_BRICK_WALL.get(),
			ESBlocks.POLISHED_GRIMSTONE_WALL.get(),
			ESBlocks.GRIMSTONE_TILE_WALL.get(),
			ESBlocks.COBBLED_VOIDSTONE_WALL.get(),
			ESBlocks.VOIDSTONE_BRICK_WALL.get(),
			ESBlocks.POLISHED_VOIDSTONE_WALL.get(),
			ESBlocks.VOIDSTONE_TILE_WALL.get(),
			ESBlocks.ETERNAL_ICE_BRICK_WALL.get(),
			ESBlocks.NEBULAITE_BRICK_WALL.get(),
			ESBlocks.RADIANITE_WALL.get(),
			ESBlocks.POLISHED_RADIANITE_WALL.get(),
			ESBlocks.RADIANITE_BRICK_WALL.get(),
			ESBlocks.FLARE_BRICK_WALL.get(),
			ESBlocks.FLARE_TILE_WALL.get(),
			ESBlocks.STELLAGMITE_WALL.get(),
			ESBlocks.MOLTEN_STELLAGMITE_WALL.get(),
			ESBlocks.POLISHED_STELLAGMITE_WALL.get(),
			ESBlocks.POLISHED_ABYSSLATE_WALL.get(),
			ESBlocks.POLISHED_ABYSSLATE_BRICK_WALL.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_WALL.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_BRICK_WALL.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_WALL.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(),
			ESBlocks.TOXITE_WALL.get(),
			ESBlocks.POLISHED_TOXITE_WALL.get(),
			ESBlocks.NIGHTFALL_MUD_BRICK_WALL.get(),
			ESBlocks.TWILIGHT_SANDSTONE_WALL.get(),
			ESBlocks.CUT_TWILIGHT_SANDSTONE_WALL.get(),
			ESBlocks.DUSTED_BRICK_WALL.get(),
			ESBlocks.DOOMEDEN_BRICK_WALL.get(),
			ESBlocks.POLISHED_DOOMEDEN_BRICK_WALL.get(),
			ESBlocks.DOOMEDEN_TILE_WALL.get(),
			ESBlocks.SPRINGSTONE_WALL.get(),
			ESBlocks.SPRINGSTONE_BRICK_WALL.get(),
			ESBlocks.POLISHED_SPRINGSTONE_WALL.get(),
			ESBlocks.THERMAL_SPRINGSTONE_WALL.get(),
			ESBlocks.THERMAL_SPRINGSTONE_BRICK_WALL.get()
		);
		tag(BlockTags.WOODEN_BUTTONS).add(
			ESBlocks.LUNAR_BUTTON.get(),
			ESBlocks.NORTHLAND_BUTTON.get(),
			ESBlocks.STARLIGHT_MANGROVE_BUTTON.get(),
			ESBlocks.SCARLET_BUTTON.get(),
			ESBlocks.TORREYA_BUTTON.get()
		);
		tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
			ESBlocks.LUNAR_PRESSURE_PLATE.get(),
			ESBlocks.NORTHLAND_PRESSURE_PLATE.get(),
			ESBlocks.STARLIGHT_MANGROVE_PRESSURE_PLATE.get(),
			ESBlocks.SCARLET_PRESSURE_PLATE.get(),
			ESBlocks.TORREYA_PRESSURE_PLATE.get()
		);
		tag(BlockTags.WOODEN_DOORS).add(
			ESBlocks.LUNAR_DOOR.get(),
			ESBlocks.NORTHLAND_DOOR.get(),
			ESBlocks.STARLIGHT_MANGROVE_DOOR.get(),
			ESBlocks.SCARLET_DOOR.get(),
			ESBlocks.TORREYA_DOOR.get()
		);
		tag(BlockTags.WOODEN_TRAPDOORS).add(
			ESBlocks.LUNAR_TRAPDOOR.get(),
			ESBlocks.NORTHLAND_TRAPDOOR.get(),
			ESBlocks.STARLIGHT_MANGROVE_TRAPDOOR.get(),
			ESBlocks.SCARLET_TRAPDOOR.get(),
			ESBlocks.TORREYA_TRAPDOOR.get()
		);
		tag(BlockTags.SMALL_FLOWERS).add(
			ESBlocks.RED_VELVETUMOSS_FLOWER.get(),
			ESBlocks.STARLIGHT_FLOWER.get(),
			ESBlocks.AUREATE_FLOWER.get(),
			ESBlocks.CONEBLOOM.get(),
			ESBlocks.NIGHTFAN.get(),
			ESBlocks.PINK_ROSE.get(),
			ESBlocks.STARLIGHT_TORCHFLOWER.get(),
			ESBlocks.WHISPERBLOOM.get(),
			ESBlocks.SWAMP_ROSE.get(),
			ESBlocks.WITHERED_STARLIGHT_FLOWER.get(),
			ESBlocks.DESERT_AMETHYSIA.get(),
			ESBlocks.WITHERED_DESERT_AMETHYSIA.get()
		);
		tag(BlockTags.TALL_FLOWERS).add(
			ESBlocks.NIGHTFAN_BUSH.get(),
			ESBlocks.PINK_ROSE_BUSH.get()
		);
		tag(BlockTags.FLOWER_POTS).add(
			ESBlocks.POTTED_LUNAR_SAPLING.get(),
			ESBlocks.POTTED_NORTHLAND_SAPLING.get(),
			ESBlocks.POTTED_STARLIGHT_MANGROVE_SAPLING.get(),
			ESBlocks.POTTED_SCARLET_SAPLING.get(),
			ESBlocks.POTTED_TORREYA_SAPLING.get(),
			ESBlocks.POTTED_STARLIGHT_FLOWER.get(),
			ESBlocks.POTTED_CONEBLOOM.get(),
			ESBlocks.POTTED_NIGHTFAN.get(),
			ESBlocks.POTTED_PINK_ROSE.get(),
			ESBlocks.POTTED_STARLIGHT_TORCHFLOWER.get(),
			ESBlocks.POTTED_CRESCENT_GRASS.get(),
			ESBlocks.POTTED_GLOWING_CRESCENT_GRASS.get(),
			ESBlocks.POTTED_PARASOL_GRASS.get(),
			ESBlocks.POTTED_GLOWING_PARASOL_GRASS.get(),
			ESBlocks.POTTED_WHISPERBLOOM.get(),
			ESBlocks.POTTED_GLADESPIKE.get(),
			ESBlocks.POTTED_VIVIDSTALK.get(),
			ESBlocks.POTTED_GLOWING_MUSHROOM.get(),
			ESBlocks.POTTED_SWAMP_ROSE.get(),
			ESBlocks.POTTED_FANTAFERN.get(),
			ESBlocks.POTTED_GREEN_FANTAFERN.get(),
			ESBlocks.POTTED_WITHERED_STARLIGHT_FLOWER.get(),
			ESBlocks.POTTED_DEAD_LUNAR_BUSH.get(),
			ESBlocks.POTTED_DESERT_AMETHYSIA.get(),
			ESBlocks.POTTED_WITHERED_DESERT_AMETHYSIA.get(),
			ESBlocks.POTTED_SUNSET_THORNBLOOM.get()
		);
		tag(BlockTags.PORTALS).add(
			ESBlocks.STARLIGHT_PORTAL.get()
		);
		tag(BlockTags.CLIMBABLE).add(
			ESBlocks.BERRIES_VINES.get(),
			ESBlocks.BERRIES_VINES_PLANT.get(),
			ESBlocks.CAVE_MOSS.get(),
			ESBlocks.CAVE_MOSS_PLANT.get(),
			ESBlocks.CAVE_MOSS_VEIN.get(),
			ESBlocks.TORREYA_VINES.get(),
			ESBlocks.TORREYA_VINES_PLANT.get()
		);
		tag(BlockTags.STANDING_SIGNS).add(
			ESBlocks.LUNAR_SIGN.get(),
			ESBlocks.NORTHLAND_SIGN.get(),
			ESBlocks.STARLIGHT_MANGROVE_SIGN.get(),
			ESBlocks.SCARLET_SIGN.get(),
			ESBlocks.TORREYA_SIGN.get()
		);
		tag(BlockTags.WALL_SIGNS).add(
			ESBlocks.LUNAR_WALL_SIGN.get(),
			ESBlocks.NORTHLAND_WALL_SIGN.get(),
			ESBlocks.STARLIGHT_MANGROVE_WALL_SIGN.get(),
			ESBlocks.SCARLET_WALL_SIGN.get(),
			ESBlocks.TORREYA_WALL_SIGN.get()
		);
		tag(BlockTags.CEILING_HANGING_SIGNS).add(
			ESBlocks.LUNAR_HANGING_SIGN.get(),
			ESBlocks.NORTHLAND_HANGING_SIGN.get(),
			ESBlocks.STARLIGHT_MANGROVE_HANGING_SIGN.get(),
			ESBlocks.SCARLET_HANGING_SIGN.get(),
			ESBlocks.TORREYA_HANGING_SIGN.get()
		);
		tag(BlockTags.WALL_HANGING_SIGNS).add(
			ESBlocks.LUNAR_WALL_HANGING_SIGN.get(),
			ESBlocks.NORTHLAND_WALL_HANGING_SIGN.get(),
			ESBlocks.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get(),
			ESBlocks.SCARLET_WALL_HANGING_SIGN.get(),
			ESBlocks.TORREYA_WALL_HANGING_SIGN.get()
		);
		tag(BlockTags.BEACON_BASE_BLOCKS).add(
			ESBlocks.ATALPHAITE_BLOCK.get(),
			ESBlocks.AETHERSENT_BLOCK.get(),
			ESBlocks.SWAMP_SILVER_BLOCK.get(),
			ESBlocks.GOLEM_STEEL_BLOCK.get()
		);
		tag(BlockTags.DIRT).add(
			ESBlocks.NIGHTFALL_DIRT.get(),
			ESBlocks.NIGHTFALL_MUD.get(),
			ESBlocks.GLOWING_NIGHTFALL_MUD.get(),
			ESBlocks.NIGHTFALL_GRASS_BLOCK.get(),
			ESBlocks.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get(),
			ESBlocks.GOLDEN_GRASS_BLOCK.get(),
			ESBlocks.FANTASY_GRASS_BLOCK.get()
		);
		tag(BlockTags.SAND).add(
			ESBlocks.TWILIGHT_SAND.get()
		);
		tag(BlockTags.SMELTS_TO_GLASS).add(
			ESBlocks.TWILIGHT_SAND.get()
		);
		tag(BlockTags.MUSHROOM_GROW_BLOCK).add(
			ESBlocks.NIGHTFALL_GRASS_BLOCK.get(),
			ESBlocks.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get(),
			ESBlocks.GOLDEN_GRASS_BLOCK.get(),
			ESBlocks.NIGHTFALL_DIRT.get()
		);
		tag(BlockTags.WITHER_IMMUNE).add(
			ESBlocks.ENERGY_BLOCK.get()
		);
		tag(BlockTags.DRAGON_IMMUNE).add(
			ESBlocks.ENERGY_BLOCK.get()
		);
		tag(BlockTags.MOSS_REPLACEABLE).add(
			ESBlocks.GRIMSTONE.get(),
			ESBlocks.VOIDSTONE.get()
		);

		// mc tools stuff
		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
			ESBlocks.RAW_AETHERSENT_BLOCK.get(),
			ESBlocks.AETHERSENT_BLOCK.get(),
			ESBlocks.SWAMP_SILVER_BLOCK.get(),
			ESBlocks.SPRINGSTONE.get(),
			ESBlocks.THERMAL_SPRINGSTONE.get(),
			ESBlocks.GLACITE.get(),
			ESBlocks.GRIMSTONE_REDSTONE_ORE.get(),
			ESBlocks.VOIDSTONE_REDSTONE_ORE.get(),
			ESBlocks.GRIMSTONE_SALTPETER_ORE.get(),
			ESBlocks.VOIDSTONE_SALTPETER_ORE.get(),
			ESBlocks.SALTPETER_BLOCK.get(),
			ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get(),
			ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get(),
			ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get(),
			ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(),
			ESBlocks.TENTACLES_CORAL.get(),
			ESBlocks.GOLDEN_CORAL.get(),
			ESBlocks.CRYSTALLUM_CORAL.get(),
			ESBlocks.TENTACLES_CORAL_FAN.get(),
			ESBlocks.GOLDEN_CORAL_FAN.get(),
			ESBlocks.CRYSTALLUM_CORAL_FAN.get(),
			ESBlocks.TENTACLES_CORAL_WALL_FAN.get(),
			ESBlocks.GOLDEN_CORAL_WALL_FAN.get(),
			ESBlocks.CRYSTALLUM_CORAL_WALL_FAN.get(),
			ESBlocks.TENTACLES_CORAL_BLOCK.get(),
			ESBlocks.GOLDEN_CORAL_BLOCK.get(),
			ESBlocks.CRYSTALLUM_CORAL_BLOCK.get(),
			// all stone building blocks
			ESBlocks.GRIMSTONE.get(),
			ESBlocks.COBBLED_GRIMSTONE.get(),
			ESBlocks.COBBLED_GRIMSTONE_SLAB.get(),
			ESBlocks.COBBLED_GRIMSTONE_STAIRS.get(),
			ESBlocks.COBBLED_GRIMSTONE_WALL.get(),
			ESBlocks.GRIMSTONE_BRICKS.get(),
			ESBlocks.GRIMSTONE_BRICK_SLAB.get(),
			ESBlocks.GRIMSTONE_BRICK_STAIRS.get(),
			ESBlocks.GRIMSTONE_BRICK_WALL.get(),
			ESBlocks.POLISHED_GRIMSTONE.get(),
			ESBlocks.POLISHED_GRIMSTONE_SLAB.get(),
			ESBlocks.POLISHED_GRIMSTONE_STAIRS.get(),
			ESBlocks.POLISHED_GRIMSTONE_WALL.get(),
			ESBlocks.GRIMSTONE_TILES.get(),
			ESBlocks.GRIMSTONE_TILE_SLAB.get(),
			ESBlocks.GRIMSTONE_TILE_STAIRS.get(),
			ESBlocks.GRIMSTONE_TILE_WALL.get(),
			ESBlocks.CHISELED_GRIMSTONE.get(),
			ESBlocks.GLOWING_GRIMSTONE.get(),
			ESBlocks.VOIDSTONE.get(),
			ESBlocks.COBBLED_VOIDSTONE.get(),
			ESBlocks.COBBLED_VOIDSTONE_SLAB.get(),
			ESBlocks.COBBLED_VOIDSTONE_STAIRS.get(),
			ESBlocks.COBBLED_VOIDSTONE_WALL.get(),
			ESBlocks.VOIDSTONE_BRICKS.get(),
			ESBlocks.VOIDSTONE_BRICK_SLAB.get(),
			ESBlocks.VOIDSTONE_BRICK_STAIRS.get(),
			ESBlocks.VOIDSTONE_BRICK_WALL.get(),
			ESBlocks.POLISHED_VOIDSTONE.get(),
			ESBlocks.POLISHED_VOIDSTONE_SLAB.get(),
			ESBlocks.POLISHED_VOIDSTONE_STAIRS.get(),
			ESBlocks.POLISHED_VOIDSTONE_WALL.get(),
			ESBlocks.VOIDSTONE_TILES.get(),
			ESBlocks.VOIDSTONE_TILE_SLAB.get(),
			ESBlocks.VOIDSTONE_TILE_STAIRS.get(),
			ESBlocks.VOIDSTONE_TILE_WALL.get(),
			ESBlocks.CHISELED_VOIDSTONE.get(),
			ESBlocks.GLOWING_VOIDSTONE.get(),
			ESBlocks.ETERNAL_ICE.get(),
			ESBlocks.ETERNAL_ICE_BRICKS.get(),
			ESBlocks.ETERNAL_ICE_BRICK_SLAB.get(),
			ESBlocks.ETERNAL_ICE_BRICK_STAIRS.get(),
			ESBlocks.ETERNAL_ICE_BRICK_WALL.get(),
			ESBlocks.ICICLE.get(),
			ESBlocks.NEBULAITE.get(),
			ESBlocks.NEBULAITE_BRICKS.get(),
			ESBlocks.NEBULAITE_BRICK_SLAB.get(),
			ESBlocks.NEBULAITE_BRICK_STAIRS.get(),
			ESBlocks.NEBULAITE_BRICK_WALL.get(),
			ESBlocks.CHISELED_NEBULAITE_BRICKS.get(),
			ESBlocks.ATALPHAITE_BLOCK.get(),
			ESBlocks.BLAZING_ATALPHAITE_BLOCK.get(),
			ESBlocks.ATALPHAITE_LIGHT.get(),
			ESBlocks.GRIMSTONE_ATALPHAITE_ORE.get(),
			ESBlocks.VOIDSTONE_ATALPHAITE_ORE.get(),
			ESBlocks.RADIANITE.get(),
			ESBlocks.RADIANITE_SLAB.get(),
			ESBlocks.RADIANITE_STAIRS.get(),
			ESBlocks.RADIANITE_WALL.get(),
			ESBlocks.RADIANITE_PILLAR.get(),
			ESBlocks.POLISHED_RADIANITE.get(),
			ESBlocks.POLISHED_RADIANITE_SLAB.get(),
			ESBlocks.POLISHED_RADIANITE_STAIRS.get(),
			ESBlocks.POLISHED_RADIANITE_WALL.get(),
			ESBlocks.RADIANITE_BRICKS.get(),
			ESBlocks.RADIANITE_BRICK_SLAB.get(),
			ESBlocks.RADIANITE_BRICK_STAIRS.get(),
			ESBlocks.RADIANITE_BRICK_WALL.get(),
			ESBlocks.CHISELED_RADIANITE.get(),
			ESBlocks.FLARE_BRICKS.get(),
			ESBlocks.FLARE_BRICK_SLAB.get(),
			ESBlocks.FLARE_BRICK_STAIRS.get(),
			ESBlocks.FLARE_BRICK_WALL.get(),
			ESBlocks.FLARE_TILES.get(),
			ESBlocks.FLARE_TILE_SLAB.get(),
			ESBlocks.FLARE_TILE_STAIRS.get(),
			ESBlocks.FLARE_TILE_WALL.get(),
			ESBlocks.CHISELED_FLARE_PILLAR.get(),
			ESBlocks.STELLAGMITE.get(),
			ESBlocks.STELLAGMITE_SLAB.get(),
			ESBlocks.STELLAGMITE_STAIRS.get(),
			ESBlocks.STELLAGMITE_WALL.get(),
			ESBlocks.MOLTEN_STELLAGMITE.get(),
			ESBlocks.MOLTEN_STELLAGMITE_SLAB.get(),
			ESBlocks.MOLTEN_STELLAGMITE_STAIRS.get(),
			ESBlocks.MOLTEN_STELLAGMITE_WALL.get(),
			ESBlocks.POLISHED_STELLAGMITE.get(),
			ESBlocks.POLISHED_STELLAGMITE_SLAB.get(),
			ESBlocks.POLISHED_STELLAGMITE_STAIRS.get(),
			ESBlocks.POLISHED_STELLAGMITE_WALL.get(),
			ESBlocks.ABYSSLATE.get(),
			ESBlocks.POLISHED_ABYSSLATE.get(),
			ESBlocks.POLISHED_ABYSSLATE_SLAB.get(),
			ESBlocks.POLISHED_ABYSSLATE_STAIRS.get(),
			ESBlocks.POLISHED_ABYSSLATE_WALL.get(),
			ESBlocks.POLISHED_ABYSSLATE_BRICKS.get(),
			ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get(),
			ESBlocks.POLISHED_ABYSSLATE_BRICK_STAIRS.get(),
			ESBlocks.POLISHED_ABYSSLATE_BRICK_WALL.get(),
			ESBlocks.CHISELED_POLISHED_ABYSSLATE.get(),
			ESBlocks.ABYSSAL_MAGMA_BLOCK.get(),
			ESBlocks.ABYSSAL_GEYSER.get(),
			ESBlocks.THERMABYSSLATE.get(),
			ESBlocks.POLISHED_THERMABYSSLATE.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_SLAB.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_STAIRS.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_WALL.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(),
			ESBlocks.POLISHED_THERMABYSSLATE_BRICK_WALL.get(),
			ESBlocks.CHISELED_POLISHED_THERMABYSSLATE.get(),
			ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get(),
			ESBlocks.THERMABYSSAL_GEYSER.get(),
			ESBlocks.CRYOBYSSLATE.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_SLAB.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_STAIRS.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_WALL.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(),
			ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(),
			ESBlocks.CHISELED_POLISHED_CRYOBYSSLATE.get(),
			ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get(),
			ESBlocks.CRYOBYSSAL_GEYSER.get(),
			ESBlocks.THIOQUARTZ_BLOCK.get(),
			ESBlocks.THIOQUARTZ_CLUSTER.get(),
			ESBlocks.TOXITE.get(),
			ESBlocks.TOXITE_SLAB.get(),
			ESBlocks.TOXITE_STAIRS.get(),
			ESBlocks.TOXITE_WALL.get(),
			ESBlocks.POLISHED_TOXITE.get(),
			ESBlocks.POLISHED_TOXITE_SLAB.get(),
			ESBlocks.POLISHED_TOXITE_STAIRS.get(),
			ESBlocks.POLISHED_TOXITE_WALL.get(),
			ESBlocks.PACKED_NIGHTFALL_MUD.get(),
			ESBlocks.NIGHTFALL_MUD_BRICKS.get(),
			ESBlocks.NIGHTFALL_MUD_BRICK_SLAB.get(),
			ESBlocks.NIGHTFALL_MUD_BRICK_STAIRS.get(),
			ESBlocks.NIGHTFALL_MUD_BRICK_WALL.get(),
			ESBlocks.TWILIGHT_SANDSTONE.get(),
			ESBlocks.TWILIGHT_SANDSTONE_SLAB.get(),
			ESBlocks.TWILIGHT_SANDSTONE_STAIRS.get(),
			ESBlocks.TWILIGHT_SANDSTONE_WALL.get(),
			ESBlocks.CUT_TWILIGHT_SANDSTONE.get(),
			ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get(),
			ESBlocks.CUT_TWILIGHT_SANDSTONE_STAIRS.get(),
			ESBlocks.CUT_TWILIGHT_SANDSTONE_WALL.get(),
			ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get(),
			ESBlocks.DUSTED_BRICKS.get(),
			ESBlocks.DUSTED_BRICK_SLAB.get(),
			ESBlocks.DUSTED_BRICK_STAIRS.get(),
			ESBlocks.DUSTED_BRICK_WALL.get(),
			ESBlocks.GOLEM_STEEL_BLOCK.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get(),
			ESBlocks.GOLEM_STEEL_SLAB.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(),
			ESBlocks.GOLEM_STEEL_STAIRS.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get(),
			ESBlocks.GOLEM_STEEL_TILES.get(),
			ESBlocks.GOLEM_STEEL_TILE_SLAB.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(),
			ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(),
			ESBlocks.GOLEM_STEEL_GRATE.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get(),
			ESBlocks.GOLEM_STEEL_PILLAR.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_PILLAR.get(),
			ESBlocks.GOLEM_STEEL_BARS.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_BARS.get(),
			ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get(),
			ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get(),
			ESBlocks.GOLEM_STEEL_JET.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_JET.get(),
			ESBlocks.DOOMEDEN_BRICKS.get(),
			ESBlocks.DOOMEDEN_BRICK_SLAB.get(),
			ESBlocks.DOOMEDEN_BRICK_STAIRS.get(),
			ESBlocks.DOOMEDEN_BRICK_WALL.get(),
			ESBlocks.POLISHED_DOOMEDEN_BRICKS.get(),
			ESBlocks.POLISHED_DOOMEDEN_BRICK_SLAB.get(),
			ESBlocks.POLISHED_DOOMEDEN_BRICK_STAIRS.get(),
			ESBlocks.POLISHED_DOOMEDEN_BRICK_WALL.get(),
			ESBlocks.DOOMEDEN_TILES.get(),
			ESBlocks.DOOMEDEN_TILE_SLAB.get(),
			ESBlocks.DOOMEDEN_TILE_STAIRS.get(),
			ESBlocks.DOOMEDEN_TILE_WALL.get(),
			ESBlocks.CHISELED_POLISHED_DOOMEDEN_BRICKS.get(),
			ESBlocks.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get(),
			ESBlocks.DOOMEDEN_KEYHOLE.get(),
			ESBlocks.REDSTONE_DOOMEDEN_KEYHOLE.get(),
			ESBlocks.SPRINGSTONE_SLAB.get(),
			ESBlocks.SPRINGSTONE_STAIRS.get(),
			ESBlocks.SPRINGSTONE_WALL.get(),
			ESBlocks.SPRINGSTONE_BRICKS.get(),
			ESBlocks.SPRINGSTONE_BRICK_SLAB.get(),
			ESBlocks.SPRINGSTONE_BRICK_STAIRS.get(),
			ESBlocks.SPRINGSTONE_BRICK_WALL.get(),
			ESBlocks.POLISHED_SPRINGSTONE.get(),
			ESBlocks.POLISHED_SPRINGSTONE_SLAB.get(),
			ESBlocks.POLISHED_SPRINGSTONE_STAIRS.get(),
			ESBlocks.POLISHED_SPRINGSTONE_WALL.get(),
			ESBlocks.CHISELED_SPRINGSTONE.get(),
			ESBlocks.THERMAL_SPRINGSTONE_SLAB.get(),
			ESBlocks.THERMAL_SPRINGSTONE_STAIRS.get(),
			ESBlocks.THERMAL_SPRINGSTONE_WALL.get(),
			ESBlocks.THERMAL_SPRINGSTONE_BRICKS.get(),
			ESBlocks.THERMAL_SPRINGSTONE_BRICK_SLAB.get(),
			ESBlocks.THERMAL_SPRINGSTONE_BRICK_STAIRS.get(),
			ESBlocks.THERMAL_SPRINGSTONE_BRICK_WALL.get(),
			ESBlocks.STELLAR_RACK.get(),
			ESBlocks.ENCHANTED_GRIMSTONE_BRICKS.get()
		);
		tag(BlockTags.MINEABLE_WITH_AXE).add(
			ESBlocks.GLOWING_MUSHROOM_BLOCK.get(),
			ESBlocks.GLOWING_MUSHROOM_STEM.get(),
			ESBlocks.TORREYA_CAMPFIRE.get(),
			ESBlocks.MOONLIGHT_LILY_PAD.get(),
			ESBlocks.STARLIT_LILY_PAD.get(),
			ESBlocks.MOONLIGHT_DUCKWEED.get()
		);
		tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
			ESBlocks.NIGHTFALL_DIRT.get(),
			ESBlocks.NIGHTFALL_FARMLAND.get(),
			ESBlocks.NIGHTFALL_MUD.get(),
			ESBlocks.GLOWING_NIGHTFALL_MUD.get(),
			ESBlocks.TWILIGHT_SAND.get(),
			ESBlocks.DUSTED_GRAVEL.get(),
			ESBlocks.NIGHTFALL_GRASS_BLOCK.get(),
			ESBlocks.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get(),
			ESBlocks.GOLDEN_GRASS_BLOCK.get(),
			ESBlocks.FANTASY_GRASS_BLOCK.get(),
			ESBlocks.SWAMP_SILVER_ORE.get()
		);
		tag(BlockTags.SWORD_EFFICIENT).add(
			ESBlocks.MOONLIGHT_LILY_PAD.get(),
			ESBlocks.STARLIT_LILY_PAD.get(),
			ESBlocks.MOONLIGHT_DUCKWEED.get()
		);
		tag(BlockTags.NEEDS_IRON_TOOL).add(
			ESBlocks.RAW_AETHERSENT_BLOCK.get(),
			ESBlocks.AETHERSENT_BLOCK.get(),
			ESBlocks.SWAMP_SILVER_BLOCK.get(),
			ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get(),
			ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get(),
			ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get(),
			ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(),
			ESBlocks.THIOQUARTZ_BLOCK.get(),
			ESBlocks.THIOQUARTZ_CLUSTER.get(),
			ESBlocks.GOLEM_STEEL_BLOCK.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get(),
			ESBlocks.GOLEM_STEEL_SLAB.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(),
			ESBlocks.GOLEM_STEEL_STAIRS.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get(),
			ESBlocks.GOLEM_STEEL_TILES.get(),
			ESBlocks.GOLEM_STEEL_TILE_SLAB.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(),
			ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(),
			ESBlocks.GOLEM_STEEL_GRATE.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get(),
			ESBlocks.GOLEM_STEEL_PILLAR.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_PILLAR.get(),
			ESBlocks.GOLEM_STEEL_BARS.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_BARS.get(),
			ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get(),
			ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get(),
			ESBlocks.GOLEM_STEEL_JET.get(),
			ESBlocks.OXIDIZED_GOLEM_STEEL_JET.get()
		);
		tag(BlockTags.IMPERMEABLE).add(
			ESBlocks.DUSK_GLASS.get()
		);
		tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(
			ESBlocks.MOONLIGHT_LILY_PAD.get(),
			ESBlocks.STARLIT_LILY_PAD.get(),
			ESBlocks.MOONLIGHT_DUCKWEED.get()
		);
		tag(BlockTags.FROG_PREFER_JUMP_TO).add(
			ESBlocks.MOONLIGHT_LILY_PAD.get(),
			ESBlocks.STARLIT_LILY_PAD.get()
		);
		tag(BlockTags.CAMPFIRES).add(
			ESBlocks.TORREYA_CAMPFIRE.get()
		);
		tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).addTag(ESTags.Blocks.YETI_FUR_CARPETS);
		tag(BlockTags.OCCLUDES_VIBRATION_SIGNALS).addTag(ESTags.Blocks.YETI_FUR);
		tag(BlockTags.DAMPENS_VIBRATIONS).addTag(ESTags.Blocks.YETI_FUR).addTag(ESTags.Blocks.YETI_FUR_CARPETS);
	}
}
