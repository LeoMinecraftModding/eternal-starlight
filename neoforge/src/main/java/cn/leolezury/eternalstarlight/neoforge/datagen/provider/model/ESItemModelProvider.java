package cn.leolezury.eternalstarlight.neoforge.datagen.provider.model;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ESItemModelProvider extends ItemModelProvider {
	public ESItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		spawnEgg(ESItems.BOARWARF_SPAWN_EGG.get());
		spawnEgg(ESItems.ASTRAL_GOLEM_SPAWN_EGG.get());
		spawnEgg(ESItems.GLEECH_SPAWN_EGG.get());
		spawnEgg(ESItems.LONESTAR_SKELETON_SPAWN_EGG.get());
		spawnEgg(ESItems.NIGHTFALL_SPIDER_SPAWN_EGG.get());
		spawnEgg(ESItems.THIRST_WALKER_SPAWN_EGG.get());
		spawnEgg(ESItems.ENT_SPAWN_EGG.get());
		spawnEgg(ESItems.RATLIN_SPAWN_EGG.get());
		spawnEgg(ESItems.YETI_SPAWN_EGG.get());
		spawnEgg(ESItems.AURORA_DEER_SPAWN_EGG.get());
		spawnEgg(ESItems.CRYSTALLIZED_MOTH_SPAWN_EGG.get());
		spawnEgg(ESItems.SHIMMER_LACEWING_SPAWN_EGG.get());
		spawnEgg(ESItems.GRIMSTONE_GOLEM_SPAWN_EGG.get());
		spawnEgg(ESItems.LUMINOFISH_SPAWN_EGG.get());
		spawnEgg(ESItems.LUMINARIS_SPAWN_EGG.get());
		spawnEgg(ESItems.TWILIGHT_GAZE_SPAWN_EGG.get());
		spawnEgg(ESItems.FREEZE_SPAWN_EGG.get());
		spawnEgg(ESItems.TANGLED_SPAWN_EGG.get());
		spawnEgg(ESItems.TANGLED_SKULL_SPAWN_EGG.get());
		spawnEgg(ESItems.TANGLED_HATRED_SPAWN_EGG.get());

		block(ESItems.RED_STARLIGHT_CRYSTAL_BLOCK.get());
		block(ESItems.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
		flatBlockTexture(ESItems.RED_STARLIGHT_CRYSTAL_CLUSTER.get());
		flatBlockTexture(ESItems.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
		block(ESItems.RED_CRYSTAL_MOSS_CARPET.get());
		block(ESItems.BLUE_CRYSTAL_MOSS_CARPET.get());
		basicItem(ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get());
		basicItem(ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get());
		basicItem(ESItems.LUNAR_BERRIES.get());
		flatBlockTexture(ESItems.CAVE_MOSS.get());
		basicItem(ESItems.ABYSSAL_FRUIT.get());
		block(ESItems.ORBFLORA.get());
		block(ESItems.ORBFLORA_LIGHT.get());

		flatBlockTexture(ESItems.JINGLING_PICKLE.get());
		flatBlockTexture(ESItems.DEAD_TENTACLES_CORAL.get());
		flatBlockTexture(ESItems.TENTACLES_CORAL.get());
		flatBlockTexture(ESItems.DEAD_TENTACLES_CORAL_FAN.get());
		flatBlockTexture(ESItems.TENTACLES_CORAL_FAN.get());
		block(ESItems.DEAD_TENTACLES_CORAL_BLOCK.get());
		block(ESItems.TENTACLES_CORAL_BLOCK.get());
		flatBlockTexture(ESItems.DEAD_GOLDEN_CORAL.get());
		flatBlockTexture(ESItems.GOLDEN_CORAL.get());
		flatBlockTexture(ESItems.DEAD_GOLDEN_CORAL_FAN.get());
		flatBlockTexture(ESItems.GOLDEN_CORAL_FAN.get());
		block(ESItems.DEAD_GOLDEN_CORAL_BLOCK.get());
		block(ESItems.GOLDEN_CORAL_BLOCK.get());
		flatBlockTexture(ESItems.DEAD_CRYSTALLUM_CORAL.get());
		flatBlockTexture(ESItems.CRYSTALLUM_CORAL.get());
		flatBlockTexture(ESItems.DEAD_CRYSTALLUM_CORAL_FAN.get());
		flatBlockTexture(ESItems.CRYSTALLUM_CORAL_FAN.get());
		block(ESItems.DEAD_CRYSTALLUM_CORAL_BLOCK.get());
		block(ESItems.CRYSTALLUM_CORAL_BLOCK.get());

		cubeAll(ESItems.VELVETUMOSS.get());
		basicItem(ESItems.VELVETUMOSS_BALL.get());
		flatBlockTexture(ESItems.VELVETUMOSS_VILLI.get());

		block(ESItems.RED_VELVETUMOSS.get());
		flatBlockTexture(ESItems.RED_VELVETUMOSS_VILLI.get());
		flatBlockTexture(ESItems.RED_VELVETUMOSS_FLOWER.get());

		// wood
		flatBlockTexture(ESItems.LUNAR_SAPLING.get());
		block(ESItems.LUNAR_LEAVES.get());
		block(ESItems.LUNAR_LOG.get());
		block(ESItems.LUNAR_WOOD.get());
		block(ESItems.LUNAR_PLANKS.get());
		block(ESItems.STRIPPED_LUNAR_LOG.get());
		block(ESItems.STRIPPED_LUNAR_WOOD.get());
		basicItem(ESItems.LUNAR_DOOR.get());
		trapdoor(ESItems.LUNAR_TRAPDOOR.get());
		block(ESItems.LUNAR_PRESSURE_PLATE.get());
		button(ESItems.LUNAR_BUTTON.get(), ESItems.LUNAR_PLANKS.get());
		fence(ESItems.LUNAR_FENCE.get(), ESItems.LUNAR_PLANKS.get());
		block(ESItems.LUNAR_FENCE_GATE.get());
		block(ESItems.LUNAR_SLAB.get());
		block(ESItems.LUNAR_STAIRS.get());
		block(ESItems.DEAD_LUNAR_LOG.get());
		block(ESItems.RED_CRYSTALLIZED_LUNAR_LOG.get());
		block(ESItems.BLUE_CRYSTALLIZED_LUNAR_LOG.get());
		basicItem(ESItems.LUNAR_SIGN.get());
		basicItem(ESItems.LUNAR_HANGING_SIGN.get());
		basicItem(ESItems.LUNAR_BOAT.get());
		basicItem(ESItems.LUNAR_CHEST_BOAT.get());

		flatBlockTexture(ESItems.NORTHLAND_SAPLING.get());
		block(ESItems.NORTHLAND_LEAVES.get());
		block(ESItems.NORTHLAND_LOG.get());
		block(ESItems.NORTHLAND_WOOD.get());
		block(ESItems.NORTHLAND_PLANKS.get());
		block(ESItems.STRIPPED_NORTHLAND_LOG.get());
		block(ESItems.STRIPPED_NORTHLAND_WOOD.get());
		basicItem(ESItems.NORTHLAND_DOOR.get());
		trapdoor(ESItems.NORTHLAND_TRAPDOOR.get());
		block(ESItems.NORTHLAND_PRESSURE_PLATE.get());
		button(ESItems.NORTHLAND_BUTTON.get(), ESItems.NORTHLAND_PLANKS.get());
		fence(ESItems.NORTHLAND_FENCE.get(), ESItems.NORTHLAND_PLANKS.get());
		block(ESItems.NORTHLAND_FENCE_GATE.get());
		block(ESItems.NORTHLAND_SLAB.get());
		block(ESItems.NORTHLAND_STAIRS.get());
		basicItem(ESItems.NORTHLAND_SIGN.get());
		basicItem(ESItems.NORTHLAND_HANGING_SIGN.get());
		basicItem(ESItems.NORTHLAND_BOAT.get());
		basicItem(ESItems.NORTHLAND_CHEST_BOAT.get());

		flatBlockTexture(ESItems.STARLIGHT_MANGROVE_SAPLING.get());
		block(ESItems.STARLIGHT_MANGROVE_LEAVES.get());
		block(ESItems.STARLIGHT_MANGROVE_LOG.get());
		block(ESItems.STARLIGHT_MANGROVE_WOOD.get());
		block(ESItems.STARLIGHT_MANGROVE_PLANKS.get());
		block(ESItems.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
		block(ESItems.STRIPPED_STARLIGHT_MANGROVE_WOOD.get());
		basicItem(ESItems.STARLIGHT_MANGROVE_DOOR.get());
		trapdoor(ESItems.STARLIGHT_MANGROVE_TRAPDOOR.get());
		block(ESItems.STARLIGHT_MANGROVE_PRESSURE_PLATE.get());
		button(ESItems.STARLIGHT_MANGROVE_BUTTON.get(), ESItems.STARLIGHT_MANGROVE_PLANKS.get());
		fence(ESItems.STARLIGHT_MANGROVE_FENCE.get(), ESItems.STARLIGHT_MANGROVE_PLANKS.get());
		block(ESItems.STARLIGHT_MANGROVE_FENCE_GATE.get());
		block(ESItems.STARLIGHT_MANGROVE_SLAB.get());
		block(ESItems.STARLIGHT_MANGROVE_STAIRS.get());
		basicItem(ESItems.STARLIGHT_MANGROVE_SIGN.get());
		basicItem(ESItems.STARLIGHT_MANGROVE_HANGING_SIGN.get());
		basicItem(ESItems.STARLIGHT_MANGROVE_BOAT.get());
		basicItem(ESItems.STARLIGHT_MANGROVE_CHEST_BOAT.get());
		block(ESItems.STARLIGHT_MANGROVE_ROOTS.get());
		block(ESItems.MUDDY_STARLIGHT_MANGROVE_ROOTS.get());

		flatBlockTexture(ESItems.SCARLET_SAPLING.get());
		block(ESItems.SCARLET_LEAVES.get());
		layeredBlock(ESItems.SCARLET_LEAVES_PILE.get());
		block(ESItems.SCARLET_LOG.get());
		block(ESItems.SCARLET_WOOD.get());
		block(ESItems.SCARLET_PLANKS.get());
		block(ESItems.STRIPPED_SCARLET_LOG.get());
		block(ESItems.STRIPPED_SCARLET_WOOD.get());
		basicItem(ESItems.SCARLET_DOOR.get());
		trapdoor(ESItems.SCARLET_TRAPDOOR.get());
		block(ESItems.SCARLET_PRESSURE_PLATE.get());
		button(ESItems.SCARLET_BUTTON.get(), ESItems.SCARLET_PLANKS.get());
		fence(ESItems.SCARLET_FENCE.get(), ESItems.SCARLET_PLANKS.get());
		block(ESItems.SCARLET_FENCE_GATE.get());
		block(ESItems.SCARLET_SLAB.get());
		block(ESItems.SCARLET_STAIRS.get());
		basicItem(ESItems.SCARLET_SIGN.get());
		basicItem(ESItems.SCARLET_HANGING_SIGN.get());
		basicItem(ESItems.SCARLET_BOAT.get());
		basicItem(ESItems.SCARLET_CHEST_BOAT.get());

		flatBlockTexture(ESItems.TORREYA_SAPLING.get());
		block(ESItems.TORREYA_LEAVES.get());
		block(ESItems.TORREYA_LOG.get());
		block(ESItems.TORREYA_WOOD.get());
		block(ESItems.TORREYA_PLANKS.get());
		block(ESItems.STRIPPED_TORREYA_LOG.get());
		block(ESItems.STRIPPED_TORREYA_WOOD.get());
		basicItem(ESItems.TORREYA_DOOR.get());
		trapdoor(ESItems.TORREYA_TRAPDOOR.get());
		block(ESItems.TORREYA_PRESSURE_PLATE.get());
		button(ESItems.TORREYA_BUTTON.get(), ESItems.TORREYA_PLANKS.get());
		fence(ESItems.TORREYA_FENCE.get(), ESItems.TORREYA_PLANKS.get());
		block(ESItems.TORREYA_FENCE_GATE.get());
		block(ESItems.TORREYA_SLAB.get());
		block(ESItems.TORREYA_STAIRS.get());
		basicItem(ESItems.TORREYA_SIGN.get());
		basicItem(ESItems.TORREYA_HANGING_SIGN.get());
		basicItem(ESItems.TORREYA_BOAT.get());
		basicItem(ESItems.TORREYA_CHEST_BOAT.get());
		flatBlockTexture(ESItems.TORREYA_VINES.get());
		basicItem(ESItems.TORREYA_CAMPFIRE.get());

		block(ESItems.GRIMSTONE.get());
		block(ESItems.COBBLED_GRIMSTONE.get());
		block(ESItems.COBBLED_GRIMSTONE_SLAB.get());
		block(ESItems.COBBLED_GRIMSTONE_STAIRS.get());
		wall(ESItems.COBBLED_GRIMSTONE_WALL.get(), ESItems.COBBLED_GRIMSTONE.get());
		block(ESItems.GRIMSTONE_BRICKS.get());
		block(ESItems.GRIMSTONE_BRICK_SLAB.get());
		block(ESItems.GRIMSTONE_BRICK_STAIRS.get());
		wall(ESItems.GRIMSTONE_BRICK_WALL.get(), ESItems.GRIMSTONE_BRICKS.get());
		block(ESItems.POLISHED_GRIMSTONE.get());
		block(ESItems.POLISHED_GRIMSTONE_SLAB.get());
		block(ESItems.POLISHED_GRIMSTONE_STAIRS.get());
		wall(ESItems.POLISHED_GRIMSTONE_WALL.get(), ESItems.POLISHED_GRIMSTONE.get());
		block(ESItems.GRIMSTONE_TILES.get());
		block(ESItems.GRIMSTONE_TILE_SLAB.get());
		block(ESItems.GRIMSTONE_TILE_STAIRS.get());
		wall(ESItems.GRIMSTONE_TILE_WALL.get(), ESItems.GRIMSTONE_TILES.get());
		block(ESItems.CHISELED_GRIMSTONE.get());
		block(ESItems.GLOWING_GRIMSTONE.get());

		block(ESItems.VOIDSTONE.get());
		block(ESItems.COBBLED_VOIDSTONE.get());
		block(ESItems.COBBLED_VOIDSTONE_SLAB.get());
		block(ESItems.COBBLED_VOIDSTONE_STAIRS.get());
		wall(ESItems.COBBLED_VOIDSTONE_WALL.get(), ESItems.COBBLED_VOIDSTONE.get());
		block(ESItems.VOIDSTONE_BRICKS.get());
		block(ESItems.VOIDSTONE_BRICK_SLAB.get());
		block(ESItems.VOIDSTONE_BRICK_STAIRS.get());
		wall(ESItems.VOIDSTONE_BRICK_WALL.get(), ESItems.VOIDSTONE_BRICKS.get());
		block(ESItems.POLISHED_VOIDSTONE.get());
		block(ESItems.POLISHED_VOIDSTONE_SLAB.get());
		block(ESItems.POLISHED_VOIDSTONE_STAIRS.get());
		wall(ESItems.POLISHED_VOIDSTONE_WALL.get(), ESItems.POLISHED_VOIDSTONE.get());
		block(ESItems.VOIDSTONE_TILES.get());
		block(ESItems.VOIDSTONE_TILE_SLAB.get());
		block(ESItems.VOIDSTONE_TILE_STAIRS.get());
		wall(ESItems.VOIDSTONE_TILE_WALL.get(), ESItems.VOIDSTONE_TILES.get());
		block(ESItems.CHISELED_VOIDSTONE.get());
		block(ESItems.GLOWING_VOIDSTONE.get());

		block(ESItems.ETERNAL_ICE.get());
		block(ESItems.ETERNAL_ICE_BRICKS.get());
		block(ESItems.ETERNAL_ICE_BRICK_SLAB.get());
		block(ESItems.ETERNAL_ICE_BRICK_STAIRS.get());
		wall(ESItems.ETERNAL_ICE_BRICK_WALL.get(), ESItems.ETERNAL_ICE_BRICKS.get());
		basicItem(ESItems.ICICLE.get(), blockTextureFromItem(ESItems.ICICLE.get()).withSuffix("_tip"));
		layeredBlock(ESItems.ASHEN_SNOW.get());

		block(ESItems.NEBULAITE.get());
		block(ESItems.NEBULAITE_BRICKS.get());
		block(ESItems.NEBULAITE_BRICK_SLAB.get());
		block(ESItems.NEBULAITE_BRICK_STAIRS.get());
		wall(ESItems.NEBULAITE_BRICK_WALL.get(), ESItems.NEBULAITE_BRICKS.get());
		block(ESItems.CHISELED_NEBULAITE_BRICKS.get());

		basicItem(ESItems.ATALPHAITE.get());
		block(ESItems.ATALPHAITE_BLOCK.get());
		block(ESItems.BLAZING_ATALPHAITE_BLOCK.get());
		block(ESItems.ATALPHAITE_LIGHT.get());
		block(ESItems.VOIDSTONE_ATALPHAITE_ORE.get());
		block(ESItems.GRIMSTONE_ATALPHAITE_ORE.get());
		block(ESItems.DUSK_GLASS.get());
		block(ESItems.DUSK_LIGHT.get());
		block(ESItems.ECLIPSE_CORE.get());
		block(ESItems.RADIANITE.get());
		block(ESItems.RADIANITE_SLAB.get());
		block(ESItems.RADIANITE_STAIRS.get());
		wall(ESItems.RADIANITE_WALL.get(), ESItems.RADIANITE.get());
		block(ESItems.RADIANITE_PILLAR.get());
		block(ESItems.POLISHED_RADIANITE.get());
		block(ESItems.POLISHED_RADIANITE_SLAB.get());
		block(ESItems.POLISHED_RADIANITE_STAIRS.get());
		wall(ESItems.POLISHED_RADIANITE_WALL.get(), ESItems.POLISHED_RADIANITE.get());
		block(ESItems.RADIANITE_BRICKS.get());
		block(ESItems.RADIANITE_BRICK_SLAB.get());
		block(ESItems.RADIANITE_BRICK_STAIRS.get());
		wall(ESItems.RADIANITE_BRICK_WALL.get(), ESItems.RADIANITE_BRICKS.get());
		block(ESItems.CHISELED_RADIANITE.get());
		basicItem(ESItems.FLARE_BRICK.get());
		block(ESItems.FLARE_BRICKS.get());
		block(ESItems.FLARE_BRICK_SLABS.get());
		block(ESItems.FLARE_BRICK_STAIRS.get());
		wall(ESItems.FLARE_BRICK_WALL.get(), ESItems.FLARE_BRICKS.get());
		block(ESItems.FLARE_TILES.get());
		block(ESItems.FLARE_TILE_SLABS.get());
		block(ESItems.FLARE_TILE_STAIRS.get());
		wall(ESItems.FLARE_TILE_WALL.get(), ESItems.FLARE_TILES.get());
		block(ESItems.CHISELED_FLARE_PILLAR.get());

		block(ESItems.FLARE_BRICKS.get());
		block(ESItems.FLARE_BRICK_SLABS.get());
		block(ESItems.FLARE_BRICK_STAIRS.get());
		wall(ESItems.FLARE_BRICK_WALL.get(), ESItems.FLARE_BRICKS.get());
		block(ESItems.CHISELED_FLARE_PILLAR.get());

		block(ESItems.STELLAGMITE.get());
		block(ESItems.STELLAGMITE_SLAB.get());
		block(ESItems.STELLAGMITE_STAIRS.get());
		wall(ESItems.STELLAGMITE_WALL.get(), ESItems.STELLAGMITE.get());
		block(ESItems.MOLTEN_STELLAGMITE.get());
		block(ESItems.MOLTEN_STELLAGMITE_SLAB.get());
		block(ESItems.MOLTEN_STELLAGMITE_STAIRS.get());
		wall(ESItems.MOLTEN_STELLAGMITE_WALL.get(), ESItems.MOLTEN_STELLAGMITE.get());
		block(ESItems.POLISHED_STELLAGMITE.get());
		block(ESItems.POLISHED_STELLAGMITE_SLAB.get());
		block(ESItems.POLISHED_STELLAGMITE_STAIRS.get());
		wall(ESItems.POLISHED_STELLAGMITE_WALL.get(), ESItems.POLISHED_STELLAGMITE.get());

		block(ESItems.TOOTH_OF_HUNGER_TILES.get());
		block(ESItems.TOOTH_OF_HUNGER_TILE_SLAB.get());
		block(ESItems.TOOTH_OF_HUNGER_TILE_STAIRS.get());
		wall(ESItems.TOOTH_OF_HUNGER_TILE_WALL.get(), ESItems.TOOTH_OF_HUNGER_TILES.get());
		block(ESItems.CHISELED_TOOTH_OF_HUNGER_TILES.get());

		block(ESItems.NIGHTFALL_MUD.get());
		block(ESItems.GLOWING_NIGHTFALL_MUD.get());
		block(ESItems.PACKED_NIGHTFALL_MUD.get());
		block(ESItems.NIGHTFALL_MUD_BRICKS.get());
		block(ESItems.NIGHTFALL_MUD_BRICK_SLAB.get());
		block(ESItems.NIGHTFALL_MUD_BRICK_STAIRS.get());
		wall(ESItems.NIGHTFALL_MUD_BRICK_WALL.get(), ESItems.NIGHTFALL_MUD_BRICKS.get());

		block(ESItems.ABYSSLATE.get());
		block(ESItems.POLISHED_ABYSSLATE.get());
		block(ESItems.POLISHED_ABYSSLATE_SLAB.get());
		block(ESItems.POLISHED_ABYSSLATE_STAIRS.get());
		wall(ESItems.POLISHED_ABYSSLATE_WALL.get(), ESItems.POLISHED_ABYSSLATE.get());
		block(ESItems.POLISHED_ABYSSLATE_BRICKS.get());
		block(ESItems.POLISHED_ABYSSLATE_BRICK_SLAB.get());
		block(ESItems.POLISHED_ABYSSLATE_BRICK_STAIRS.get());
		wall(ESItems.POLISHED_ABYSSLATE_BRICK_WALL.get(), ESItems.POLISHED_ABYSSLATE_BRICKS.get());
		block(ESItems.CHISELED_POLISHED_ABYSSLATE.get());
		block(ESItems.ABYSSAL_MAGMA_BLOCK.get());
		block(ESItems.ABYSSAL_GEYSER.get());

		block(ESItems.THERMABYSSLATE.get());
		block(ESItems.POLISHED_THERMABYSSLATE.get());
		block(ESItems.POLISHED_THERMABYSSLATE_SLAB.get());
		block(ESItems.POLISHED_THERMABYSSLATE_STAIRS.get());
		wall(ESItems.POLISHED_THERMABYSSLATE_WALL.get(), ESItems.POLISHED_THERMABYSSLATE.get());
		block(ESItems.POLISHED_THERMABYSSLATE_BRICKS.get());
		block(ESItems.POLISHED_THERMABYSSLATE_BRICK_SLAB.get());
		block(ESItems.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get());
		wall(ESItems.POLISHED_THERMABYSSLATE_BRICK_WALL.get(), ESItems.POLISHED_THERMABYSSLATE_BRICKS.get());
		block(ESItems.CHISELED_POLISHED_THERMABYSSLATE.get());
		block(ESItems.THERMABYSSAL_MAGMA_BLOCK.get());
		block(ESItems.THERMABYSSAL_GEYSER.get());

		block(ESItems.CRYOBYSSLATE.get());
		block(ESItems.POLISHED_CRYOBYSSLATE.get());
		block(ESItems.POLISHED_CRYOBYSSLATE_SLAB.get());
		block(ESItems.POLISHED_CRYOBYSSLATE_STAIRS.get());
		wall(ESItems.POLISHED_CRYOBYSSLATE_WALL.get(), ESItems.POLISHED_CRYOBYSSLATE.get());
		block(ESItems.POLISHED_CRYOBYSSLATE_BRICKS.get());
		block(ESItems.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get());
		block(ESItems.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get());
		wall(ESItems.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(), ESItems.POLISHED_CRYOBYSSLATE_BRICKS.get());
		block(ESItems.CHISELED_POLISHED_CRYOBYSSLATE.get());
		block(ESItems.CRYOBYSSAL_MAGMA_BLOCK.get());
		block(ESItems.CRYOBYSSAL_GEYSER.get());

		block(ESItems.THIOQUARTZ_BLOCK.get());
		block(ESItems.BUDDING_THIOQUARTZ.get());
		flatBlockTexture(ESItems.THIOQUARTZ_CLUSTER.get());
		basicItem(ESItems.THIOQUARTZ_SHARD.get());
		block(ESItems.TOXITE.get());
		block(ESItems.TOXITE_SLAB.get());
		block(ESItems.TOXITE_STAIRS.get());
		wall(ESItems.TOXITE_WALL.get(), ESItems.TOXITE.get());
		block(ESItems.POLISHED_TOXITE.get());
		block(ESItems.POLISHED_TOXITE_SLAB.get());
		block(ESItems.POLISHED_TOXITE_STAIRS.get());
		wall(ESItems.POLISHED_TOXITE_WALL.get(), ESItems.POLISHED_TOXITE.get());
		basicItem(ESItems.ALCHEMIST_MASK.get());
		basicItem(ESItems.ALCHEMIST_ROBE.get());

		block(ESItems.TWILIGHT_SAND.get());
		block(ESItems.TWILIGHT_SANDSTONE.get());
		block(ESItems.TWILIGHT_SANDSTONE_SLAB.get());
		block(ESItems.TWILIGHT_SANDSTONE_STAIRS.get());
		wall(ESItems.TWILIGHT_SANDSTONE_WALL.get(), ESItems.TWILIGHT_SANDSTONE.get());
		block(ESItems.CUT_TWILIGHT_SANDSTONE.get());
		block(ESItems.CUT_TWILIGHT_SANDSTONE_SLAB.get());
		block(ESItems.CUT_TWILIGHT_SANDSTONE_STAIRS.get());
		wall(ESItems.CUT_TWILIGHT_SANDSTONE_WALL.get(), ESItems.CUT_TWILIGHT_SANDSTONE.get());
		block(ESItems.CHISELED_TWILIGHT_SANDSTONE.get());

		block(ESItems.DUSTED_GRAVEL.get());
		basicItem(ESItems.DUSTED_SHARD.get());
		block(ESItems.DUSTED_BRICKS.get());
		block(ESItems.DUSTED_BRICK_SLAB.get());
		block(ESItems.DUSTED_BRICK_STAIRS.get());
		wall(ESItems.DUSTED_BRICK_WALL.get(), ESItems.DUSTED_BRICKS.get());

		block(ESItems.GOLEM_STEEL_BLOCK.get());
		block(ESItems.OXIDIZED_GOLEM_STEEL_BLOCK.get());
		block(ESItems.GOLEM_STEEL_SLAB.get());
		block(ESItems.OXIDIZED_GOLEM_STEEL_SLAB.get());
		block(ESItems.GOLEM_STEEL_STAIRS.get());
		block(ESItems.OXIDIZED_GOLEM_STEEL_STAIRS.get());
		block(ESItems.GOLEM_STEEL_TILES.get());
		block(ESItems.OXIDIZED_GOLEM_STEEL_TILES.get());
		block(ESItems.GOLEM_STEEL_TILE_SLAB.get());
		block(ESItems.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get());
		block(ESItems.GOLEM_STEEL_TILE_STAIRS.get());
		block(ESItems.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get());
		block(ESItems.GOLEM_STEEL_GRATE.get());
		block(ESItems.OXIDIZED_GOLEM_STEEL_GRATE.get());
		block(ESItems.GOLEM_STEEL_PILLAR.get());
		block(ESItems.OXIDIZED_GOLEM_STEEL_PILLAR.get());
		flatBlockTexture(ESItems.GOLEM_STEEL_BARS.get());
		flatBlockTexture(ESItems.OXIDIZED_GOLEM_STEEL_BARS.get());
		block(ESItems.CHISELED_GOLEM_STEEL_BLOCK.get());
		block(ESItems.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get());
		block(ESItems.GOLEM_STEEL_JET.get());
		block(ESItems.OXIDIZED_GOLEM_STEEL_JET.get());

		block(ESItems.SHADEGRIEVE.get());
		block(ESItems.BLOOMING_SHADEGRIEVE.get());
		basicItem(ESItems.LUNAR_VINE.get());
		block(ESItems.LUNAR_MOSAIC.get());
		block(ESItems.LUNAR_MOSAIC_SLAB.get());
		block(ESItems.LUNAR_MOSAIC_STAIRS.get());
		fence(ESItems.LUNAR_MOSAIC_FENCE.get(), ESItems.LUNAR_MOSAIC.get());
		block(ESItems.LUNAR_MOSAIC_FENCE_GATE.get());
		block(ESItems.LUNAR_MAT.get());

		basicItem(ESItems.BROKEN_DOOMEDEN_BONE.get());
		greatsword(ESItems.BONEMORE.get());
		greatswordInventory(ESItems.BONEMORE.get());
		bow(ESItems.BOW_OF_BLOOD.get());
		handheld(ESItems.LIVING_ARM.get());
		flatBlockTexture(ESItems.DOOMED_TORCH.get());
		flatBlockTexture(ESItems.DOOMED_REDSTONE_TORCH.get());
		basicItem(ESItems.DOOMEDEN_CARRION.get());
		handheld(ESItems.ROTTEN_HAM.get());
		basicItem(ESItems.EYE_OF_DOOM.get());
		basicItem(ESItems.DOOMEDEN_RAG.get());
		handheld(ESItems.FLESH_GRINDER.get());
		inventoryHandheld(ESItems.DOOMEDEN_RAPIER.get());
		block(ESItems.DOOMEDEN_BRICKS.get());
		block(ESItems.DOOMEDEN_BRICK_SLAB.get());
		block(ESItems.DOOMEDEN_BRICK_STAIRS.get());
		wall(ESItems.DOOMEDEN_BRICK_WALL.get(), ESItems.DOOMEDEN_BRICKS.get());
		block(ESItems.POLISHED_DOOMEDEN_BRICKS.get());
		block(ESItems.POLISHED_DOOMEDEN_BRICK_SLAB.get());
		block(ESItems.POLISHED_DOOMEDEN_BRICK_STAIRS.get());
		wall(ESItems.POLISHED_DOOMEDEN_BRICK_WALL.get(), ESItems.POLISHED_DOOMEDEN_BRICKS.get());
		block(ESItems.DOOMEDEN_TILES.get());
		block(ESItems.DOOMEDEN_TILE_SLAB.get());
		block(ESItems.DOOMEDEN_TILE_STAIRS.get());
		wall(ESItems.DOOMEDEN_TILE_WALL.get(), ESItems.DOOMEDEN_TILES.get());
		block(ESItems.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
		block(ESItems.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
		block(ESItems.DOOMEDEN_LIGHT.get());
		block(ESItems.DOOMEDEN_KEYHOLE.get());
		block(ESItems.REDSTONE_DOOMEDEN_KEYHOLE.get());

		flatBlockTexture(ESItems.STARLIGHT_FLOWER.get());
		flatBlockTexture(ESItems.AUREATE_FLOWER.get());
		flatBlockTexture(ESItems.CONEBLOOM.get());
		flatBlockTexture(ESItems.NIGHTFAN.get());
		flatBlockTexture(ESItems.PINK_ROSE.get());
		flatBlockTexture(ESItems.STARLIGHT_TORCHFLOWER.get());
		basicItem(ESItems.NIGHTFAN_BUSH.get(), blockTextureFromItem(ESItems.NIGHTFAN_BUSH.get()).withSuffix("_top"));
		basicItem(ESItems.PINK_ROSE_BUSH.get(), blockTextureFromItem(ESItems.PINK_ROSE_BUSH.get()).withSuffix("_top"));
		flatBlockTexture(ESItems.NIGHT_SPROUTS.get());
		flatBlockTexture(ESItems.GLOWING_NIGHT_SPROUTS.get());
		flatBlockTexture(ESItems.SMALL_NIGHT_SPROUTS.get());
		flatBlockTexture(ESItems.SMALL_GLOWING_NIGHT_SPROUTS.get());
		flatBlockTexture(ESItems.LUNAR_GRASS.get());
		flatBlockTexture(ESItems.GLOWING_LUNAR_GRASS.get());
		flatBlockTexture(ESItems.CRESCENT_GRASS.get());
		flatBlockTexture(ESItems.GLOWING_CRESCENT_GRASS.get());
		flatBlockTexture(ESItems.PARASOL_GRASS.get());
		flatBlockTexture(ESItems.GLOWING_PARASOL_GRASS.get());
		flatBlockTexture(ESItems.LUNAR_BUSH.get());
		flatBlockTexture(ESItems.GLOWING_LUNAR_BUSH.get());
		basicItem(ESItems.TALL_CRESCENT_GRASS.get(), blockTextureFromItem(ESItems.TALL_CRESCENT_GRASS.get()).withSuffix("_top"));
		basicItem(ESItems.TALL_GLOWING_CRESCENT_GRASS.get(), blockTextureFromItem(ESItems.TALL_GLOWING_CRESCENT_GRASS.get()).withSuffix("_top"));
		basicItem(ESItems.LUNAR_REED.get(), blockTextureFromItem(ESItems.LUNAR_REED.get()).withSuffix("_top"));
		flatBlockTexture(ESItems.WHISPERBLOOM.get());
		flatBlockTexture(ESItems.GLADESPIKE.get());
		flatBlockTexture(ESItems.VIVIDSTALK.get());
		basicItem(ESItems.TALL_GLADESPIKE.get(), blockTextureFromItem(ESItems.TALL_GLADESPIKE.get()).withSuffix("_top"));
		flatBlockTexture(ESItems.GLOWING_MUSHROOM.get());
		cubeAll(ESItems.GLOWING_MUSHROOM_BLOCK.get());
		cubeAll(ESItems.GLOWING_MUSHROOM_STEM.get());

		flatBlockTexture(ESItems.SWAMP_ROSE.get());
		flatBlockTexture(ESItems.FANTABUD.get());
		flatBlockTexture(ESItems.GREEN_FANTABUD.get());
		flatBlockTexture(ESItems.FANTAFERN.get());
		flatBlockTexture(ESItems.GREEN_FANTAFERN.get());
		flatBlockTexture(ESItems.FANTAGRASS.get());
		flatBlockTexture(ESItems.GREEN_FANTAGRASS.get());

		flatBlockTexture(ESItems.ORANGE_SCARLET_BUD.get());
		flatBlockTexture(ESItems.PURPLE_SCARLET_BUD.get());
		flatBlockTexture(ESItems.RED_SCARLET_BUD.get());
		flatBlockTexture(ESItems.SCARLET_GRASS.get());
		flatBlockTexture(ESItems.MAUVE_FERN.get());

		flatBlockTexture(ESItems.WITHERED_STARLIGHT_FLOWER.get());

		flatBlockTexture(ESItems.DEAD_LUNAR_BUSH.get());
		flatBlockTexture(ESItems.DESERT_AMETHYSIA.get());
		flatBlockTexture(ESItems.WITHERED_DESERT_AMETHYSIA.get());
		flatBlockTexture(ESItems.SUNSET_THORNBLOOM.get());
		flatBlockTexture(ESItems.AMETHYSIA_GRASS.get());
		block(ESItems.LUNARIS_CACTUS.get());
		block(ESItems.LUNARIS_CACTUS_GEL_BLOCK.get());
		basicItem(ESItems.LUNARIS_CACTUS_FRUIT.get());
		basicItem(ESItems.LUNARIS_CACTUS_GEL.get());
		block(ESItems.CARVED_LUNARIS_CACTUS_FRUIT.get());
		block(ESItems.LUNARIS_CACTUS_FRUIT_LANTERN.get());

		flatBlockTexture(ESItems.MOONLIGHT_LILY_PAD.get());
		flatBlockTexture(ESItems.STARLIT_LILY_PAD.get());
		flatBlockTexture(ESItems.MOONLIGHT_DUCKWEED.get());

		flatBlockTexture(ESItems.CRYSTALLIZED_LUNAR_GRASS.get());
		flatBlockTexture(ESItems.RED_CRYSTAL_ROOTS.get());
		flatBlockTexture(ESItems.BLUE_CRYSTAL_ROOTS.get());
		basicItem(ESItems.TWILVEWRYM_HERB.get(), blockTextureFromItem(ESItems.TWILVEWRYM_HERB.get()).withSuffix("_top"));
		basicItem(ESItems.STELLAFLY_BUSH.get(), blockTextureFromItem(ESItems.STELLAFLY_BUSH.get()).withSuffix("_top"));
		basicItem(ESItems.GLIMMERFLY_BUSH.get(), blockTextureFromItem(ESItems.GLIMMERFLY_BUSH.get()).withSuffix("_top"));

		flatBlockTexture(ESItems.GOLDEN_GRASS.get());
		flatBlockTexture(ESItems.TALL_GOLDEN_GRASS.get());

		block(ESItems.NIGHTFALL_DIRT.get());
		block(ESItems.NIGHTFALL_FARMLAND.get());
		block(ESItems.NIGHTFALL_DIRT_PATH.get());
		block(ESItems.NIGHTFALL_GRASS_BLOCK.get());
		block(ESItems.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get());
		block(ESItems.GOLDEN_GRASS_BLOCK.get());
		block(ESItems.FANTASY_GRASS_BLOCK.get());
		block(ESItems.FANTASY_GRASS_CARPET.get());

		basicItem(ESItems.RAW_AETHERSENT.get());
		block(ESItems.RAW_AETHERSENT_BLOCK.get());
		block(ESItems.AETHERSENT_BLOCK.get());
		basicItem(ESItems.AETHERSENT_INGOT.get());
		basicItem(ESItems.AETHERSENT_NUGGET.get());
		handheld(ESItems.RAGE_OF_STARS.get());
		bow(ESItems.STARFALL_LONGBOW.get());
		armorWithTrim((ArmorItem) ESItems.AETHERSENT_HOOD.get());
		basicItem(ESItems.AETHERSENT_CAPE.get());
		basicItem(ESItems.AETHERSENT_BOTTOMS.get());
		basicItem(ESItems.AETHERSENT_BOOTS.get());

		block(ESItems.SPRINGSTONE.get());
		block(ESItems.SPRINGSTONE_SLAB.get());
		block(ESItems.SPRINGSTONE_STAIRS.get());
		wall(ESItems.SPRINGSTONE_WALL.get(), ESItems.SPRINGSTONE.get());
		block(ESItems.SPRINGSTONE_BRICKS.get());
		block(ESItems.SPRINGSTONE_BRICK_SLAB.get());
		block(ESItems.SPRINGSTONE_BRICK_STAIRS.get());
		wall(ESItems.SPRINGSTONE_BRICK_WALL.get(), ESItems.SPRINGSTONE_BRICKS.get());
		block(ESItems.POLISHED_SPRINGSTONE.get());
		block(ESItems.POLISHED_SPRINGSTONE_SLAB.get());
		block(ESItems.POLISHED_SPRINGSTONE_STAIRS.get());
		wall(ESItems.POLISHED_SPRINGSTONE_WALL.get(), ESItems.POLISHED_SPRINGSTONE.get());
		block(ESItems.CHISELED_SPRINGSTONE.get());
		block(ESItems.THERMAL_SPRINGSTONE.get());
		block(ESItems.THERMAL_SPRINGSTONE_SLAB.get());
		block(ESItems.THERMAL_SPRINGSTONE_STAIRS.get());
		wall(ESItems.THERMAL_SPRINGSTONE_WALL.get(), ESItems.THERMAL_SPRINGSTONE.get());
		block(ESItems.THERMAL_SPRINGSTONE_BRICKS.get());
		block(ESItems.THERMAL_SPRINGSTONE_BRICK_SLAB.get());
		block(ESItems.THERMAL_SPRINGSTONE_BRICK_STAIRS.get());
		wall(ESItems.THERMAL_SPRINGSTONE_BRICK_WALL.get(), ESItems.THERMAL_SPRINGSTONE_BRICKS.get());
		basicItem(ESItems.THERMAL_SPRINGSTONE_INGOT.get());
		handheld(ESItems.THERMAL_SPRINGSTONE_SWORD.get());
		handheld(ESItems.THERMAL_SPRINGSTONE_PICKAXE.get());
		handheld(ESItems.THERMAL_SPRINGSTONE_AXE.get());
		handheld(ESItems.THERMAL_SPRINGSTONE_HOE.get());
		handheld(ESItems.THERMAL_SPRINGSTONE_SHOVEL.get());
		largeHandheld(ESItems.THERMAL_SPRINGSTONE_SCYTHE.get());
		inventoryHandheld(ESItems.THERMAL_SPRINGSTONE_SCYTHE.get());
		inventoryHandheld(ESItems.THERMAL_SPRINGSTONE_HAMMER.get());
		basicItem(ESItems.THERMAL_SPRINGSTONE_HELMET.get());
		basicItem(ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get());
		basicItem(ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get());
		basicItem(ESItems.THERMAL_SPRINGSTONE_BOOTS.get());

		block(ESItems.GLACITE.get());
		basicItem(ESItems.GLACITE_SHARD.get());
		handheld(ESItems.GLACITE_SWORD.get());
		handheld(ESItems.GLACITE_PICKAXE.get());
		handheld(ESItems.GLACITE_AXE.get());
		handheld(ESItems.GLACITE_HOE.get());
		handheld(ESItems.GLACITE_SHOVEL.get());
		largeHandheld(ESItems.GLACITE_SCYTHE.get());
		inventoryHandheld(ESItems.GLACITE_SCYTHE.get());
		armorWithTrim((ArmorItem) ESItems.GLACITE_HELMET.get());
		armorWithTrim((ArmorItem) ESItems.GLACITE_CHESTPLATE.get());
		armorWithTrim((ArmorItem) ESItems.GLACITE_LEGGINGS.get());
		armorWithTrim((ArmorItem) ESItems.GLACITE_BOOTS.get());

		block(ESItems.SWAMP_SILVER_ORE.get());
		block(ESItems.SWAMP_SILVER_BLOCK.get());
		basicItem(ESItems.SWAMP_SILVER_INGOT.get());
		basicItem(ESItems.SWAMP_SILVER_NUGGET.get());
		handheld(ESItems.SWAMP_SILVER_SWORD.get());
		handheld(ESItems.SWAMP_SILVER_PICKAXE.get());
		handheld(ESItems.SWAMP_SILVER_AXE.get());
		handheld(ESItems.SWAMP_SILVER_SICKLE.get());
		armorWithTrim((ArmorItem) ESItems.SWAMP_SILVER_HELMET.get());
		armorWithTrim((ArmorItem) ESItems.SWAMP_SILVER_CHESTPLATE.get());
		basicItem(ESItems.SWAMP_SILVER_LEGGINGS.get());
		basicItem(ESItems.SWAMP_SILVER_BOOTS.get());

		block(ESItems.GRIMSTONE_REDSTONE_ORE.get());
		block(ESItems.VOIDSTONE_REDSTONE_ORE.get());

		block(ESItems.GRIMSTONE_SALTPETER_ORE.get());
		block(ESItems.VOIDSTONE_SALTPETER_ORE.get());
		block(ESItems.SALTPETER_BLOCK.get());
		basicItem(ESItems.SALTPETER_POWDER.get());
		basicItem(ESItems.SALTPETER_MATCHBOX.get());

		basicItem(ESItems.RAW_AMARAMBER.get());
		basicItem(ESItems.AMARAMBER_INGOT.get());
		basicItem(ESItems.AMARAMBER_NUGGET.get());
		basicItem(ESItems.AMARAMBER_LANTERN.get());
		basicItem(ESItems.AMARAMBER_CANDLE.get());
		basicItem(ESItems.AMARAMBER_ARROW.get());
		handheld(ESItems.AMARAMBER_AXE.get());
		handheld(ESItems.AMARAMBER_HOE.get());
		handheld(ESItems.AMARAMBER_SHOVEL.get());
		armorWithTrim((ArmorItem) ESItems.AMARAMBER_HELMET.get());
		armorWithTrim((ArmorItem) ESItems.AMARAMBER_CHESTPLATE.get());

		shatteredSword(ESItems.SHATTERED_SWORD.get());
		basicItem(ESItems.SHATTERED_SWORD_BLADE.get());
		basicItem(ESItems.GOLEM_STEEL_INGOT.get());
		basicItem(ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get());
		handheld(ESItems.ENERGY_SWORD.get());
		basicItem(ESItems.TENACIOUS_PETAL.get());
		basicItem(ESItems.TENACIOUS_VINE.get());
		crossbow(ESItems.CRYSTAL_CROSSBOW.get());
		crossbow(ESItems.MECHANICAL_CROSSBOW.get());
		bow(ESItems.MOONRING_BOW.get());
		greatsword(ESItems.MOONRING_GREATSWORD.get());
		greatswordInventory(ESItems.MOONRING_GREATSWORD.get());
		largeHandheld(ESItems.PETAL_SCYTHE.get());
		inventoryHandheld(ESItems.PETAL_SCYTHE.get());
		handheld(ESItems.WAND_OF_TELEPORTATION.get());
		chainOfSouls(ESItems.CHAIN_OF_SOULS.get());
		inventoryHandheld(ESItems.CRESCENT_SPEAR.get());
		basicItem(ESItems.SEEKING_EYE.get());

		basicItem(ESItems.LUMINOFISH_BUCKET.get());
		basicItem(ESItems.LUMINOFISH.get());
		basicItem(ESItems.COOKED_LUMINOFISH.get());

		basicItem(ESItems.LUMINARIS_BUCKET.get());
		basicItem(ESItems.LUMINARIS.get());
		basicItem(ESItems.COOKED_LUMINARIS.get());

		block(ESItems.WHITE_YETI_FUR.get());
		block(ESItems.ORANGE_YETI_FUR.get());
		block(ESItems.MAGENTA_YETI_FUR.get());
		block(ESItems.LIGHT_BLUE_YETI_FUR.get());
		block(ESItems.YELLOW_YETI_FUR.get());
		block(ESItems.LIME_YETI_FUR.get());
		block(ESItems.PINK_YETI_FUR.get());
		block(ESItems.GRAY_YETI_FUR.get());
		block(ESItems.LIGHT_GRAY_YETI_FUR.get());
		block(ESItems.CYAN_YETI_FUR.get());
		block(ESItems.PURPLE_YETI_FUR.get());
		block(ESItems.BLUE_YETI_FUR.get());
		block(ESItems.BROWN_YETI_FUR.get());
		block(ESItems.GREEN_YETI_FUR.get());
		block(ESItems.RED_YETI_FUR.get());
		block(ESItems.BLACK_YETI_FUR.get());

		block(ESItems.WHITE_YETI_FUR_CARPET.get());
		block(ESItems.ORANGE_YETI_FUR_CARPET.get());
		block(ESItems.MAGENTA_YETI_FUR_CARPET.get());
		block(ESItems.LIGHT_BLUE_YETI_FUR_CARPET.get());
		block(ESItems.YELLOW_YETI_FUR_CARPET.get());
		block(ESItems.LIME_YETI_FUR_CARPET.get());
		block(ESItems.PINK_YETI_FUR_CARPET.get());
		block(ESItems.GRAY_YETI_FUR_CARPET.get());
		block(ESItems.LIGHT_GRAY_YETI_FUR_CARPET.get());
		block(ESItems.CYAN_YETI_FUR_CARPET.get());
		block(ESItems.PURPLE_YETI_FUR_CARPET.get());
		block(ESItems.BLUE_YETI_FUR_CARPET.get());
		block(ESItems.BROWN_YETI_FUR_CARPET.get());
		block(ESItems.GREEN_YETI_FUR_CARPET.get());
		block(ESItems.RED_YETI_FUR_CARPET.get());
		block(ESItems.BLACK_YETI_FUR_CARPET.get());

		handheld(ESItems.AURORA_DEER_ANTLER.get());
		basicItem(ESItems.AURORA_DEER_STEAK.get());
		basicItem(ESItems.COOKED_AURORA_DEER_STEAK.get());

		basicItem(ESItems.RATLIN_MEAT.get());
		basicItem(ESItems.COOKED_RATLIN_MEAT.get());

		basicItem(ESItems.FROZEN_TUBE.get());

		basicItem(ESItems.SHIVERING_GEL.get());
		basicItem(ESItems.SONAR_BOMB.get());

		basicItem(ESItems.NIGHTFALL_SPIDER_EYE.get());
		basicItem(ESItems.TRAPPED_SOUL.get());
		basicItem(ESItems.SOULIT_SPECTATOR.get());

		templateSkull(ESItems.TANGLED_SKULL.get());

		basicItem(ESItems.GLEECH_EGG.get());

		basicItem(ESItems.TOOTH_OF_HUNGER.get());
		daggerOfHunger(ESItems.DAGGER_OF_HUNGER.get());
		basicItem(ESItems.VORACIOUS_ARROW.get());

		orbOfProphecyInventory(ESItems.ORB_OF_PROPHECY.get());
		block(ESItems.STELLAR_RACK.get());
		block(ESItems.ENCHANTED_GRIMSTONE_BRICKS.get());
		block(ESItems.CREST_POT.get());
		basicItem(ESItems.MANA_CRYSTAL.get());
		basicItem(ESItems.TERRA_CRYSTAL.get());
		basicItem(ESItems.WIND_CRYSTAL.get());
		basicItem(ESItems.WATER_CRYSTAL.get());
		basicItem(ESItems.LUNAR_CRYSTAL.get());
		basicItem(ESItems.BLAZE_CRYSTAL.get());
		basicItem(ESItems.LIGHT_CRYSTAL.get());
		basicItem(ESItems.MANA_CRYSTAL_SHARD.get());

		basicItem(ESItems.KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE.get());
		basicItem(ESItems.FORGE_ARMOR_TRIM_SMITHING_TEMPLATE.get());

		basicItem(ESItems.MUSIC_DISC_WHISPER_OF_THE_STARS.get());
		basicItem(ESItems.MUSIC_DISC_TRANQUILITY.get());
		basicItem(ESItems.MUSIC_DISC_ATLANTIS.get());

		basicItem(ESItems.STARLIT_PAINTING.get());
		basicItem(ESItems.ETHER_BUCKET.get());
		block(ESItems.ENERGY_BLOCK.get());
		block(ESItems.THE_GATEKEEPER_SPAWNER.get());
		block(ESItems.STARLIGHT_GOLEM_SPAWNER.get());
		block(ESItems.TANGLED_HATRED_SPAWNER.get());
		block(ESItems.LUNAR_MONSTROSITY_SPAWNER.get());
		basicItem(ESItems.STARLIGHT_SILVER_COIN.get());
		basicItem(ESItems.LOOT_BAG.get());
		basicItem(ESItems.BOOK.get());
		basicItem(ESItems.BLOSSOM_OF_STARS.get());
	}

	private void spawnEgg(Item item) {
		getBuilder(item.toString())
			.parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
	}

	private void armorWithTrim(ArmorItem armor) {
		ItemModelBuilder armorBuilder = basicItem(armor);
		for (ItemModelGenerators.TrimModelData trimModelData : ItemModelGenerators.GENERATED_TRIM_MODELS) {
			ModelFile trimModel = withExistingParent(name(armor) + "_" + trimModelData.name() + "_trim", mcLoc("item/generated"))
				.texture("layer0", modLoc("item/" + name(armor)))
				.texture("layer1", mcLoc("trims/items/" + armor.getType().getName() + "_trim_" + trimModelData.name()));
			armorBuilder.override().predicate(ResourceLocation.withDefaultNamespace("trim_type"), trimModelData.itemModelIndex()).model(trimModel).end();
		}
	}

	private void greatsword(Item item) {
		ModelFile blocking = withExistingParent(name(item) + "_blocking", EternalStarlight.id("item/large_handheld_blocking"))
			.texture("layer0", itemTexture(item));
		withExistingParent(name(item), EternalStarlight.id("item/large_handheld"))
			.texture("layer0", itemTexture(item))
			.override().predicate(ResourceLocation.withDefaultNamespace("blocking"), 1).model(blocking).end();
	}

	private void greatswordInventory(Item item) {
		withExistingParent(name(item) + "_blocking_inventory", ResourceLocation.withDefaultNamespace("item/handheld"))
			.texture("layer0", itemTexture(item).withSuffix("_inventory"));
		withExistingParent(name(item) + "_inventory", ResourceLocation.withDefaultNamespace("item/handheld"))
			.texture("layer0", itemTexture(item).withSuffix("_inventory"));
	}

	private void chainOfSouls(Item item) {
		ModelFile extendedModel = withExistingParent(name(item) + "_extended", "item/handheld")
			.texture("layer0", itemTexture(item).withSuffix("_extended"));
		withExistingParent(name(item), "item/handheld")
			.texture("layer0", itemTexture(item))
			.override().predicate(EternalStarlight.id("extended"), 1).model(extendedModel).end();
	}

	private void shatteredSword(Item item) {
		ModelFile noBladeModel = withExistingParent(name(item) + "_no_blade", "item/handheld")
			.texture("layer0", itemTexture(item).withSuffix("_no_blade"));
		withExistingParent(name(item), "item/handheld")
			.texture("layer0", itemTexture(item))
			.override().predicate(EternalStarlight.id("no_blade"), 1).model(noBladeModel).end();
	}

	private void daggerOfHunger(Item item) {
		ModelFile normal = withExistingParent(name(item) + "_normal", "item/handheld")
			.texture("layer0", itemTexture(item));
		ModelFile saturated = withExistingParent(name(item) + "_saturated", "item/handheld")
			.texture("layer0", itemTexture(item).withSuffix("_saturated"));
		withExistingParent(name(item), "item/handheld")
			.texture("layer0", itemTexture(item).withSuffix("_hungry"))
			.override().predicate(EternalStarlight.id("hunger_state"), 0.5f).model(normal).end()
			.override().predicate(EternalStarlight.id("hunger_state"), 1.0f).model(saturated).end();
	}

	private void crossbow(Item item) {
		ModelFile pull0 = withExistingParent(name(item) + "_pulling_0", "item/crossbow")
			.texture("layer0", itemTexture(item).withSuffix("_pulling_0"));
		ModelFile pull1 = withExistingParent(name(item) + "_pulling_1", "item/crossbow")
			.texture("layer0", itemTexture(item).withSuffix("_pulling_1"));
		ModelFile pull2 = withExistingParent(name(item) + "_pulling_2", "item/crossbow")
			.texture("layer0", itemTexture(item).withSuffix("_pulling_2"));
		ModelFile arrow = withExistingParent(name(item) + "_arrow", "item/crossbow")
			.texture("layer0", itemTexture(item).withSuffix("_arrow"));
		ModelFile firework = withExistingParent(name(item) + "_firework", "item/crossbow")
			.texture("layer0", itemTexture(item).withSuffix("_firework"));
		withExistingParent(name(item), "item/crossbow")
			.texture("layer0", itemTexture(item).withSuffix("_standby"))
			.override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).model(pull0).end()
			.override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), (float) 0.58).model(pull1).end()
			.override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), (float) 1.0).model(pull2).end()
			.override().predicate(ResourceLocation.withDefaultNamespace("charged"), 1).model(arrow).end()
			.override().predicate(ResourceLocation.withDefaultNamespace("charged"), 1).predicate(ResourceLocation.withDefaultNamespace("firework"), 1).model(firework).end();
	}

	private void bow(Item item) {
		ModelFile pull0 = withExistingParent(name(item) + "_pulling_0", "item/bow")
			.texture("layer0", itemTexture(item).withSuffix("_pulling_0"));
		ModelFile pull1 = withExistingParent(name(item) + "_pulling_1", "item/bow")
			.texture("layer0", itemTexture(item).withSuffix("_pulling_1"));
		ModelFile pull2 = withExistingParent(name(item) + "_pulling_2", "item/bow")
			.texture("layer0", itemTexture(item).withSuffix("_pulling_2"));
		withExistingParent(name(item), "item/bow")
			.texture("layer0", itemTexture(item))
			.override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).model(pull0).end()
			.override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), (float) 0.65).model(pull1).end()
			.override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), (float) 0.9).model(pull2).end();
	}

	private void orbOfProphecyInventory(Item item) {
		ModelFile withCrestModel = withExistingParent(name(item) + "_with_crests_inventory", "item/generated")
			.texture("layer0", itemTexture(item).withSuffix("_with_crests_inventory"));
		ModelFile temporaryModel = withExistingParent(name(item) + "_temporary_inventory", "item/generated")
			.texture("layer0", itemTexture(item).withSuffix("_temporary_inventory"));
		withExistingParent(name(item) + "_inventory", "item/generated")
			.texture("layer0", itemTexture(item).withSuffix("_inventory"))
			.override().predicate(EternalStarlight.id("orb_type"), 0.5f).model(temporaryModel).end()
			.override().predicate(EternalStarlight.id("orb_type"), 1).model(withCrestModel).end();
	}

	private void trapdoor(Item item) {
		withExistingParent(name(item), modLoc(ModelProvider.BLOCK_FOLDER + "/" + name(item) + "_bottom"));
	}

	private void button(Item button, Item material) {
		getBuilder(name(button))
			.parent(getExistingFile(mcLoc("block/button_inventory")))
			.texture("texture", blockTextureFromItem(material));
	}

	private void fence(Item fence, Item planks) {
		getBuilder(name(fence))
			.parent(getExistingFile(mcLoc("block/fence_inventory")))
			.texture("texture", blockTextureFromItem(planks));
	}

	private void wall(Item wall, Item stone) {
		getBuilder(name(wall))
			.parent(getExistingFile(mcLoc("block/wall_inventory")))
			.texture("wall", blockTextureFromItem(stone));
	}

	private void cubeAll(Item cube) {
		getBuilder(name(cube))
			.parent(getExistingFile(mcLoc("block/cube_all")))
			.texture("all", blockTextureFromItem(cube));
	}

	private void templateSkull(Item skull) {
		getBuilder(name(skull))
			.parent(getExistingFile(mcLoc("item/template_skull")));
	}

	private void block(Item item) {
		withExistingParent(name(item), modLoc(ModelProvider.BLOCK_FOLDER + "/" + name(item)));
	}

	private void layeredBlock(Item item) {
		withExistingParent(name(item), modLoc(ModelProvider.BLOCK_FOLDER + "/" + name(item) + "_height2"));
	}

	private void flatBlockTexture(Item item) {
		basicItem(item, blockTextureFromItem(item));
	}

	private void basicItem(Item item, ResourceLocation texture) {
		getBuilder(item.toString())
			.parent(new ModelFile.UncheckedModelFile("item/generated"))
			.texture("layer0", texture);
	}

	private void largeHandheld(Item item) {
		handheld(item, itemTexture(item), true);
	}

	private void handheld(Item item) {
		handheld(item, itemTexture(item), false);
	}

	private ItemModelBuilder handheld(Item item, ResourceLocation texture, boolean large) {
		return getBuilder(item.toString())
			.parent(large ? new ModelFile.UncheckedModelFile(EternalStarlight.ID + ":item/large_handheld") : new ModelFile.UncheckedModelFile("item/handheld"))
			.texture("layer0", texture);
	}

	private void inventoryHandheld(Item item) {
		getBuilder(item.toString() + "_inventory")
			.parent(new ModelFile.UncheckedModelFile("item/handheld"))
			.texture("layer0", itemTexture(item) + "_inventory");
	}

	public ResourceLocation blockTexture(Block block) {
		ResourceLocation name = key(block);
		return texture(name, ModelProvider.BLOCK_FOLDER);
	}

	public ResourceLocation blockTextureFromItem(Item item) {
		ResourceLocation name = key(item);
		return texture(name, ModelProvider.BLOCK_FOLDER);
	}

	public ResourceLocation itemTexture(Item item) {
		ResourceLocation name = key(item);
		return texture(name, ModelProvider.ITEM_FOLDER);
	}

	public ResourceLocation texture(ResourceLocation key, String prefix) {
		return ResourceLocation.fromNamespaceAndPath(key.getNamespace(), prefix + "/" + key.getPath());
	}

	private ResourceLocation key(Block block) {
		return BuiltInRegistries.BLOCK.getKey(block);
	}

	private String name(Block block) {
		return key(block).getPath();
	}

	private ResourceLocation key(Item item) {
		return BuiltInRegistries.ITEM.getKey(item);
	}

	private String name(Item item) {
		return key(item).getPath();
	}
}
