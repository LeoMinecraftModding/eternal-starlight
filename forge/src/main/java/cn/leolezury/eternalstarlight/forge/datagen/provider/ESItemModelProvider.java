package cn.leolezury.eternalstarlight.forge.datagen.provider;

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
        super(output, EternalStarlight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicBlockItem(ESItems.RED_STARLIGHT_CRYSTAL_BLOCK.get());
        basicBlockItem(ESItems.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
        basicItemWithBlockTexture(ESItems.RED_STARLIGHT_CRYSTAL_CLUSTER.get());
        basicItemWithBlockTexture(ESItems.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
        basicBlockItem(ESItems.RED_CRYSTAL_MOSS_CARPET.get());
        basicBlockItem(ESItems.BLUE_CRYSTAL_MOSS_CARPET.get());
        basicItem(ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get());
        basicItem(ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get());
        basicItem(ESItems.LUNAR_BERRIES.get());
        basicItemWithBlockTexture(ESItems.CAVE_MOSS.get());
        basicItem(ESItems.ABYSSAL_FRUIT.get());
        basicBlockItem(ESItems.ORBFLORA.get());
        basicBlockItem(ESItems.ORBFLORA_LIGHT.get());

        basicItemWithBlockTexture(ESItems.DEAD_TENTACLES_CORAL.get());
        basicItemWithBlockTexture(ESItems.TENTACLES_CORAL.get());
        basicItemWithBlockTexture(ESItems.DEAD_TENTACLES_CORAL_FAN.get());
        basicItemWithBlockTexture(ESItems.TENTACLES_CORAL_FAN.get());
        basicBlockItem(ESItems.DEAD_TENTACLES_CORAL_BLOCK.get());
        basicBlockItem(ESItems.TENTACLES_CORAL_BLOCK.get());
        basicItemWithBlockTexture(ESItems.DEAD_GOLDEN_CORAL.get());
        basicItemWithBlockTexture(ESItems.GOLDEN_CORAL.get());
        basicItemWithBlockTexture(ESItems.DEAD_GOLDEN_CORAL_FAN.get());
        basicItemWithBlockTexture(ESItems.GOLDEN_CORAL_FAN.get());
        basicBlockItem(ESItems.DEAD_GOLDEN_CORAL_BLOCK.get());
        basicBlockItem(ESItems.GOLDEN_CORAL_BLOCK.get());
        basicItemWithBlockTexture(ESItems.DEAD_CRYSTALLUM_CORAL.get());
        basicItemWithBlockTexture(ESItems.CRYSTALLUM_CORAL.get());
        basicItemWithBlockTexture(ESItems.DEAD_CRYSTALLUM_CORAL_FAN.get());
        basicItemWithBlockTexture(ESItems.CRYSTALLUM_CORAL_FAN.get());
        basicBlockItem(ESItems.DEAD_CRYSTALLUM_CORAL_BLOCK.get());
        basicBlockItem(ESItems.CRYSTALLUM_CORAL_BLOCK.get());

        // wood
        basicItemWithBlockTexture(ESItems.LUNAR_SAPLING.get());
        basicBlockItem(ESItems.LUNAR_LEAVES.get());
        basicBlockItem(ESItems.LUNAR_LOG.get());
        basicBlockItem(ESItems.LUNAR_WOOD.get());
        basicBlockItem(ESItems.LUNAR_PLANKS.get());
        basicBlockItem(ESItems.STRIPPED_LUNAR_LOG.get());
        basicBlockItem(ESItems.STRIPPED_LUNAR_WOOD.get());
        basicItem(ESItems.LUNAR_DOOR.get());
        trapdoor(ESItems.LUNAR_TRAPDOOR.get());
        basicBlockItem(ESItems.LUNAR_PRESSURE_PLATE.get());
        button(ESItems.LUNAR_BUTTON.get(), ESItems.LUNAR_PLANKS.get());
        fence(ESItems.LUNAR_FENCE.get(), ESItems.LUNAR_PLANKS.get());
        basicBlockItem(ESItems.LUNAR_FENCE_GATE.get());
        basicBlockItem(ESItems.LUNAR_SLAB.get());
        basicBlockItem(ESItems.LUNAR_STAIRS.get());
        basicBlockItem(ESItems.DEAD_LUNAR_LOG.get());
        basicBlockItem(ESItems.RED_CRYSTALLIZED_LUNAR_LOG.get());
        basicBlockItem(ESItems.BLUE_CRYSTALLIZED_LUNAR_LOG.get());
        basicItem(ESItems.LUNAR_SIGN.get());
        basicItem(ESItems.LUNAR_HANGING_SIGN.get());
        basicItem(ESItems.LUNAR_BOAT.get());
        basicItem(ESItems.LUNAR_CHEST_BOAT.get());

        basicItemWithBlockTexture(ESItems.NORTHLAND_SAPLING.get());
        basicBlockItem(ESItems.NORTHLAND_LEAVES.get());
        basicBlockItem(ESItems.NORTHLAND_LOG.get());
        basicBlockItem(ESItems.NORTHLAND_WOOD.get());
        basicBlockItem(ESItems.NORTHLAND_PLANKS.get());
        basicBlockItem(ESItems.STRIPPED_NORTHLAND_LOG.get());
        basicBlockItem(ESItems.STRIPPED_NORTHLAND_WOOD.get());
        basicItem(ESItems.NORTHLAND_DOOR.get());
        trapdoor(ESItems.NORTHLAND_TRAPDOOR.get());
        basicBlockItem(ESItems.NORTHLAND_PRESSURE_PLATE.get());
        button(ESItems.NORTHLAND_BUTTON.get(), ESItems.NORTHLAND_PLANKS.get());
        fence(ESItems.NORTHLAND_FENCE.get(), ESItems.NORTHLAND_PLANKS.get());
        basicBlockItem(ESItems.NORTHLAND_FENCE_GATE.get());
        basicBlockItem(ESItems.NORTHLAND_SLAB.get());
        basicBlockItem(ESItems.NORTHLAND_STAIRS.get());
        basicItem(ESItems.NORTHLAND_SIGN.get());
        basicItem(ESItems.NORTHLAND_HANGING_SIGN.get());
        basicItem(ESItems.NORTHLAND_BOAT.get());
        basicItem(ESItems.NORTHLAND_CHEST_BOAT.get());

        basicItemWithBlockTexture(ESItems.STARLIGHT_MANGROVE_SAPLING.get());
        basicBlockItem(ESItems.STARLIGHT_MANGROVE_LEAVES.get());
        basicBlockItem(ESItems.STARLIGHT_MANGROVE_LOG.get());
        basicBlockItem(ESItems.STARLIGHT_MANGROVE_WOOD.get());
        basicBlockItem(ESItems.STARLIGHT_MANGROVE_PLANKS.get());
        basicBlockItem(ESItems.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
        basicBlockItem(ESItems.STRIPPED_STARLIGHT_MANGROVE_WOOD.get());
        basicItem(ESItems.STARLIGHT_MANGROVE_DOOR.get());
        trapdoor(ESItems.STARLIGHT_MANGROVE_TRAPDOOR.get());
        basicBlockItem(ESItems.STARLIGHT_MANGROVE_PRESSURE_PLATE.get());
        button(ESItems.STARLIGHT_MANGROVE_BUTTON.get(), ESItems.STARLIGHT_MANGROVE_PLANKS.get());
        fence(ESItems.STARLIGHT_MANGROVE_FENCE.get(), ESItems.STARLIGHT_MANGROVE_PLANKS.get());
        basicBlockItem(ESItems.STARLIGHT_MANGROVE_FENCE_GATE.get());
        basicBlockItem(ESItems.STARLIGHT_MANGROVE_SLAB.get());
        basicBlockItem(ESItems.STARLIGHT_MANGROVE_STAIRS.get());
        basicItem(ESItems.STARLIGHT_MANGROVE_SIGN.get());
        basicItem(ESItems.STARLIGHT_MANGROVE_HANGING_SIGN.get());
        basicItem(ESItems.STARLIGHT_MANGROVE_BOAT.get());
        basicItem(ESItems.STARLIGHT_MANGROVE_CHEST_BOAT.get());
        basicBlockItem(ESItems.STARLIGHT_MANGROVE_ROOTS.get());
        basicBlockItem(ESItems.MUDDY_STARLIGHT_MANGROVE_ROOTS.get());

        basicItemWithBlockTexture(ESItems.SCARLET_SAPLING.get());
        basicBlockItem(ESItems.SCARLET_LEAVES.get());
        basicBlockItem(ESItems.SCARLET_LEAVES_PILE.get());
        basicBlockItem(ESItems.SCARLET_LOG.get());
        basicBlockItem(ESItems.SCARLET_WOOD.get());
        basicBlockItem(ESItems.SCARLET_PLANKS.get());
        basicBlockItem(ESItems.STRIPPED_SCARLET_LOG.get());
        basicBlockItem(ESItems.STRIPPED_SCARLET_WOOD.get());
        basicItem(ESItems.SCARLET_DOOR.get());
        trapdoor(ESItems.SCARLET_TRAPDOOR.get());
        basicBlockItem(ESItems.SCARLET_PRESSURE_PLATE.get());
        button(ESItems.SCARLET_BUTTON.get(), ESItems.SCARLET_PLANKS.get());
        fence(ESItems.SCARLET_FENCE.get(), ESItems.SCARLET_PLANKS.get());
        basicBlockItem(ESItems.SCARLET_FENCE_GATE.get());
        basicBlockItem(ESItems.SCARLET_SLAB.get());
        basicBlockItem(ESItems.SCARLET_STAIRS.get());
        basicItem(ESItems.SCARLET_SIGN.get());
        basicItem(ESItems.SCARLET_HANGING_SIGN.get());
        basicItem(ESItems.SCARLET_BOAT.get());
        basicItem(ESItems.SCARLET_CHEST_BOAT.get());

        basicItemWithBlockTexture(ESItems.TORREYA_SAPLING.get());
        basicBlockItem(ESItems.TORREYA_LEAVES.get());
        basicBlockItem(ESItems.TORREYA_LOG.get());
        basicBlockItem(ESItems.TORREYA_WOOD.get());
        basicBlockItem(ESItems.TORREYA_PLANKS.get());
        basicBlockItem(ESItems.STRIPPED_TORREYA_LOG.get());
        basicBlockItem(ESItems.STRIPPED_TORREYA_WOOD.get());
        basicItem(ESItems.TORREYA_DOOR.get());
        trapdoor(ESItems.TORREYA_TRAPDOOR.get());
        basicBlockItem(ESItems.TORREYA_PRESSURE_PLATE.get());
        button(ESItems.TORREYA_BUTTON.get(), ESItems.TORREYA_PLANKS.get());
        fence(ESItems.TORREYA_FENCE.get(), ESItems.TORREYA_PLANKS.get());
        basicBlockItem(ESItems.TORREYA_FENCE_GATE.get());
        basicBlockItem(ESItems.TORREYA_SLAB.get());
        basicBlockItem(ESItems.TORREYA_STAIRS.get());
        basicItem(ESItems.TORREYA_SIGN.get());
        basicItem(ESItems.TORREYA_HANGING_SIGN.get());
        basicItem(ESItems.TORREYA_BOAT.get());
        basicItem(ESItems.TORREYA_CHEST_BOAT.get());
        basicItemWithBlockTexture(ESItems.TORREYA_VINES.get());

        basicBlockItem(ESItems.GRIMSTONE.get());
        basicBlockItem(ESItems.COBBLED_GRIMSTONE.get());
        basicBlockItem(ESItems.COBBLED_GRIMSTONE_SLAB.get());
        basicBlockItem(ESItems.COBBLED_GRIMSTONE_STAIRS.get());
        wall(ESItems.COBBLED_GRIMSTONE_WALL.get(), ESItems.COBBLED_GRIMSTONE.get());
        basicBlockItem(ESItems.GRIMSTONE_BRICKS.get());
        basicBlockItem(ESItems.GRIMSTONE_BRICK_SLAB.get());
        basicBlockItem(ESItems.GRIMSTONE_BRICK_STAIRS.get());
        wall(ESItems.GRIMSTONE_BRICK_WALL.get(), ESItems.GRIMSTONE_BRICKS.get());
        basicBlockItem(ESItems.POLISHED_GRIMSTONE.get());
        basicBlockItem(ESItems.POLISHED_GRIMSTONE_SLAB.get());
        basicBlockItem(ESItems.POLISHED_GRIMSTONE_STAIRS.get());
        wall(ESItems.POLISHED_GRIMSTONE_WALL.get(), ESItems.POLISHED_GRIMSTONE.get());
        basicBlockItem(ESItems.GRIMSTONE_TILES.get());
        basicBlockItem(ESItems.GRIMSTONE_TILE_SLAB.get());
        basicBlockItem(ESItems.GRIMSTONE_TILE_STAIRS.get());
        wall(ESItems.GRIMSTONE_TILE_WALL.get(), ESItems.GRIMSTONE_TILES.get());
        basicBlockItem(ESItems.CHISELED_GRIMSTONE.get());
        basicBlockItem(ESItems.GLOWING_GRIMSTONE.get());

        basicBlockItem(ESItems.VOIDSTONE.get());
        basicBlockItem(ESItems.COBBLED_VOIDSTONE.get());
        basicBlockItem(ESItems.COBBLED_VOIDSTONE_SLAB.get());
        basicBlockItem(ESItems.COBBLED_VOIDSTONE_STAIRS.get());
        wall(ESItems.COBBLED_VOIDSTONE_WALL.get(), ESItems.COBBLED_VOIDSTONE.get());
        basicBlockItem(ESItems.VOIDSTONE_BRICKS.get());
        basicBlockItem(ESItems.VOIDSTONE_BRICK_SLAB.get());
        basicBlockItem(ESItems.VOIDSTONE_BRICK_STAIRS.get());
        wall(ESItems.VOIDSTONE_BRICK_WALL.get(), ESItems.VOIDSTONE_BRICKS.get());
        basicBlockItem(ESItems.POLISHED_VOIDSTONE.get());
        basicBlockItem(ESItems.POLISHED_VOIDSTONE_SLAB.get());
        basicBlockItem(ESItems.POLISHED_VOIDSTONE_STAIRS.get());
        wall(ESItems.POLISHED_VOIDSTONE_WALL.get(), ESItems.POLISHED_VOIDSTONE.get());
        basicBlockItem(ESItems.VOIDSTONE_TILES.get());
        basicBlockItem(ESItems.VOIDSTONE_TILE_SLAB.get());
        basicBlockItem(ESItems.VOIDSTONE_TILE_STAIRS.get());
        wall(ESItems.VOIDSTONE_TILE_WALL.get(), ESItems.VOIDSTONE_TILES.get());
        basicBlockItem(ESItems.CHISELED_VOIDSTONE.get());
        basicBlockItem(ESItems.GLOWING_VOIDSTONE.get());

        basicBlockItem(ESItems.NIGHTSHADE_MUD.get());
        basicBlockItem(ESItems.GLOWING_NIGHTSHADE_MUD.get());
        basicBlockItem(ESItems.PACKED_NIGHTSHADE_MUD.get());
        basicBlockItem(ESItems.NIGHTSHADE_MUD_BRICKS.get());
        basicBlockItem(ESItems.NIGHTSHADE_MUD_BRICK_SLAB.get());
        basicBlockItem(ESItems.NIGHTSHADE_MUD_BRICK_STAIRS.get());
        wall(ESItems.NIGHTSHADE_MUD_BRICK_WALL.get(), ESItems.NIGHTSHADE_MUD_BRICKS.get());

        basicBlockItem(ESItems.ABYSSLATE.get());
        basicBlockItem(ESItems.POLISHED_ABYSSLATE.get());
        basicBlockItem(ESItems.POLISHED_ABYSSLATE_SLAB.get());
        basicBlockItem(ESItems.POLISHED_ABYSSLATE_STAIRS.get());
        wall(ESItems.POLISHED_ABYSSLATE_WALL.get(), ESItems.POLISHED_ABYSSLATE.get());
        basicBlockItem(ESItems.POLISHED_ABYSSLATE_BRICKS.get());
        basicBlockItem(ESItems.POLISHED_ABYSSLATE_BRICK_SLAB.get());
        basicBlockItem(ESItems.POLISHED_ABYSSLATE_BRICK_STAIRS.get());
        wall(ESItems.POLISHED_ABYSSLATE_BRICK_WALL.get(), ESItems.POLISHED_ABYSSLATE_BRICKS.get());
        basicBlockItem(ESItems.CHISELED_POLISHED_ABYSSLATE.get());
        basicBlockItem(ESItems.ABYSSAL_MAGMA_BLOCK.get());
        basicBlockItem(ESItems.ABYSSAL_GEYSER.get());

        basicBlockItem(ESItems.THERMABYSSLATE.get());
        basicBlockItem(ESItems.POLISHED_THERMABYSSLATE.get());
        basicBlockItem(ESItems.POLISHED_THERMABYSSLATE_SLAB.get());
        basicBlockItem(ESItems.POLISHED_THERMABYSSLATE_STAIRS.get());
        wall(ESItems.POLISHED_THERMABYSSLATE_WALL.get(), ESItems.POLISHED_THERMABYSSLATE.get());
        basicBlockItem(ESItems.POLISHED_THERMABYSSLATE_BRICKS.get());
        basicBlockItem(ESItems.POLISHED_THERMABYSSLATE_BRICK_SLAB.get());
        basicBlockItem(ESItems.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get());
        wall(ESItems.POLISHED_THERMABYSSLATE_BRICK_WALL.get(), ESItems.POLISHED_THERMABYSSLATE_BRICKS.get());
        basicBlockItem(ESItems.CHISELED_POLISHED_THERMABYSSLATE.get());
        basicBlockItem(ESItems.THERMABYSSAL_MAGMA_BLOCK.get());
        basicBlockItem(ESItems.THERMABYSSAL_GEYSER.get());

        basicBlockItem(ESItems.CRYOBYSSLATE.get());
        basicBlockItem(ESItems.POLISHED_CRYOBYSSLATE.get());
        basicBlockItem(ESItems.POLISHED_CRYOBYSSLATE_SLAB.get());
        basicBlockItem(ESItems.POLISHED_CRYOBYSSLATE_STAIRS.get());
        wall(ESItems.POLISHED_CRYOBYSSLATE_WALL.get(), ESItems.POLISHED_CRYOBYSSLATE.get());
        basicBlockItem(ESItems.POLISHED_CRYOBYSSLATE_BRICKS.get());
        basicBlockItem(ESItems.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get());
        basicBlockItem(ESItems.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get());
        wall(ESItems.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(), ESItems.POLISHED_CRYOBYSSLATE_BRICKS.get());
        basicBlockItem(ESItems.CHISELED_POLISHED_CRYOBYSSLATE.get());
        basicBlockItem(ESItems.CRYOBYSSAL_MAGMA_BLOCK.get());
        basicBlockItem(ESItems.CRYOBYSSAL_GEYSER.get());

        basicBlockItem(ESItems.TWILIGHT_SAND.get());
        basicBlockItem(ESItems.TWILIGHT_SANDSTONE.get());
        basicBlockItem(ESItems.TWILIGHT_SANDSTONE_SLAB.get());
        basicBlockItem(ESItems.TWILIGHT_SANDSTONE_STAIRS.get());
        wall(ESItems.TWILIGHT_SANDSTONE_WALL.get(), ESItems.TWILIGHT_SANDSTONE.get());
        basicBlockItem(ESItems.CUT_TWILIGHT_SANDSTONE.get());
        basicBlockItem(ESItems.CUT_TWILIGHT_SANDSTONE_SLAB.get());
        basicBlockItem(ESItems.CUT_TWILIGHT_SANDSTONE_STAIRS.get());
        wall(ESItems.CUT_TWILIGHT_SANDSTONE_WALL.get(), ESItems.CUT_TWILIGHT_SANDSTONE.get());
        basicBlockItem(ESItems.CHISELED_TWILIGHT_SANDSTONE.get());

        basicBlockItem(ESItems.DUSTED_GRAVEL.get());
        basicItem(ESItems.DUSTED_SHARD.get());
        basicBlockItem(ESItems.DUSTED_BRICKS.get());
        basicBlockItem(ESItems.DUSTED_BRICK_SLAB.get());
        basicBlockItem(ESItems.DUSTED_BRICK_STAIRS.get());
        wall(ESItems.DUSTED_BRICK_WALL.get(), ESItems.DUSTED_BRICKS.get());

        basicBlockItem(ESItems.GOLEM_STEEL_BLOCK.get());
        basicBlockItem(ESItems.OXIDIZED_GOLEM_STEEL_BLOCK.get());
        basicBlockItem(ESItems.GOLEM_STEEL_SLAB.get());
        basicBlockItem(ESItems.OXIDIZED_GOLEM_STEEL_SLAB.get());
        basicBlockItem(ESItems.GOLEM_STEEL_STAIRS.get());
        basicBlockItem(ESItems.OXIDIZED_GOLEM_STEEL_STAIRS.get());
        basicBlockItem(ESItems.GOLEM_STEEL_TILES.get());
        basicBlockItem(ESItems.OXIDIZED_GOLEM_STEEL_TILES.get());
        basicBlockItem(ESItems.GOLEM_STEEL_TILE_SLAB.get());
        basicBlockItem(ESItems.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get());
        basicBlockItem(ESItems.GOLEM_STEEL_TILE_STAIRS.get());
        basicBlockItem(ESItems.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get());
        basicBlockItem(ESItems.CHISELED_GOLEM_STEEL_BLOCK.get());
        basicBlockItem(ESItems.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get());

        basicBlockItem(ESItems.LUNAR_MOSAIC.get());
        basicBlockItem(ESItems.LUNAR_MOSAIC_SLAB.get());
        basicBlockItem(ESItems.LUNAR_MOSAIC_STAIRS.get());
        fence(ESItems.LUNAR_MOSAIC_FENCE.get(), ESItems.LUNAR_MOSAIC.get());
        basicBlockItem(ESItems.LUNAR_MOSAIC_FENCE_GATE.get());
        basicBlockItem(ESItems.LUNAR_MAT.get());

        basicItem(ESItems.BROKEN_DOOMEDEN_BONE.get());
        largeHandheld(ESItems.BONEMORE_BROADSWORD.get());
        inventoryModel(ESItems.BONEMORE_BROADSWORD.get());
        bow(ESItems.BOW_OF_BLOOD.get());
        basicHandheld(ESItems.LIVING_ARM.get());
        basicItemWithBlockTexture(ESItems.DOOMED_TORCH.get());
        basicItemWithBlockTexture(ESItems.DOOMED_REDSTONE_TORCH.get());
        basicItem(ESItems.DOOMEDEN_CARRION.get());
        basicHandheld(ESItems.ROTTEN_HAM.get());
        basicItem(ESItems.EYE_OF_DOOM.get());
        basicItem(ESItems.DOOMEDEN_RAG.get());
        basicHandheld(ESItems.DOOMEDEN_FLESH_GRINDER.get());
        inventoryModel(ESItems.DOOMEDEN_SWORD.get());
        basicBlockItem(ESItems.DOOMEDEN_BRICKS.get());
        basicBlockItem(ESItems.DOOMEDEN_BRICK_SLAB.get());
        basicBlockItem(ESItems.DOOMEDEN_BRICK_STAIRS.get());
        wall(ESItems.DOOMEDEN_BRICK_WALL.get(), ESItems.DOOMEDEN_BRICKS.get());
        basicBlockItem(ESItems.POLISHED_DOOMEDEN_BRICKS.get());
        basicBlockItem(ESItems.POLISHED_DOOMEDEN_BRICK_SLAB.get());
        basicBlockItem(ESItems.POLISHED_DOOMEDEN_BRICK_STAIRS.get());
        wall(ESItems.POLISHED_DOOMEDEN_BRICK_WALL.get(), ESItems.POLISHED_DOOMEDEN_BRICKS.get());
        basicBlockItem(ESItems.DOOMEDEN_TILES.get());
        basicBlockItem(ESItems.DOOMEDEN_TILE_SLAB.get());
        basicBlockItem(ESItems.DOOMEDEN_TILE_STAIRS.get());
        wall(ESItems.DOOMEDEN_TILE_WALL.get(), ESItems.DOOMEDEN_TILES.get());
        basicBlockItem(ESItems.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        basicBlockItem(ESItems.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        basicBlockItem(ESItems.DOOMEDEN_LIGHT.get());
        basicBlockItem(ESItems.DOOMEDEN_KEYHOLE.get());
        basicBlockItem(ESItems.REDSTONE_DOOMEDEN_KEYHOLE.get());

        basicItemWithBlockTexture(ESItems.STARLIGHT_FLOWER.get());
        basicItemWithBlockTexture(ESItems.CONEBLOOM.get());
        basicItemWithBlockTexture(ESItems.NIGHTFAN.get());
        basicItemWithBlockTexture(ESItems.PINK_ROSE.get());
        basicItemWithBlockTexture(ESItems.STARLIGHT_TORCHFLOWER.get());
        basicItem(ESItems.NIGHTFAN_BUSH.get(), blockTextureFromItem(ESItems.NIGHTFAN_BUSH.get()).withSuffix("_top"));
        basicItem(ESItems.PINK_ROSE_BUSH.get(), blockTextureFromItem(ESItems.PINK_ROSE_BUSH.get()).withSuffix("_top"));
        basicItemWithBlockTexture(ESItems.NIGHT_SPROUTS.get());
        basicItemWithBlockTexture(ESItems.GLOWING_NIGHT_SPROUTS.get());
        basicItemWithBlockTexture(ESItems.SMALL_NIGHT_SPROUTS.get());
        basicItemWithBlockTexture(ESItems.SMALL_GLOWING_NIGHT_SPROUTS.get());
        basicItemWithBlockTexture(ESItems.LUNAR_GRASS.get());
        basicItemWithBlockTexture(ESItems.GLOWING_LUNAR_GRASS.get());
        basicItemWithBlockTexture(ESItems.CRESCENT_GRASS.get());
        basicItemWithBlockTexture(ESItems.GLOWING_CRESCENT_GRASS.get());
        basicItemWithBlockTexture(ESItems.PARASOL_GRASS.get());
        basicItemWithBlockTexture(ESItems.GLOWING_PARASOL_GRASS.get());
        basicItemWithBlockTexture(ESItems.LUNAR_BUSH.get());
        basicItemWithBlockTexture(ESItems.GLOWING_LUNAR_BUSH.get());
        basicItem(ESItems.TALL_CRESCENT_GRASS.get(), blockTextureFromItem(ESItems.TALL_CRESCENT_GRASS.get()).withSuffix("_top"));
        basicItem(ESItems.TALL_GLOWING_CRESCENT_GRASS.get(), blockTextureFromItem(ESItems.TALL_GLOWING_CRESCENT_GRASS.get()).withSuffix("_top"));
        basicItem(ESItems.LUNAR_REED.get(), blockTextureFromItem(ESItems.LUNAR_REED.get()).withSuffix("_top"));
        basicItemWithBlockTexture(ESItems.WHISPERBLOOM.get());
        basicItemWithBlockTexture(ESItems.GLADESPIKE.get());
        basicItemWithBlockTexture(ESItems.VIVIDSTALK.get());
        basicItem(ESItems.TALL_GLADESPIKE.get(), blockTextureFromItem(ESItems.TALL_GLADESPIKE.get()).withSuffix("_top"));
        basicItemWithBlockTexture(ESItems.GLOWING_MUSHROOM.get());
        cubeAll(ESItems.GLOWING_MUSHROOM_BLOCK.get());
        cubeAll(ESItems.GLOWING_MUSHROOM_STEM.get());

        basicItemWithBlockTexture(ESItems.SWAMP_ROSE.get());
        basicItemWithBlockTexture(ESItems.FANTABUD.get());
        basicItemWithBlockTexture(ESItems.GREEN_FANTABUD.get());
        basicItemWithBlockTexture(ESItems.FANTAFERN.get());
        basicItemWithBlockTexture(ESItems.GREEN_FANTAFERN.get());
        basicItemWithBlockTexture(ESItems.FANTAGRASS.get());
        basicItemWithBlockTexture(ESItems.GREEN_FANTAGRASS.get());

        basicItemWithBlockTexture(ESItems.ORANGE_SCARLET_BUD.get());
        basicItemWithBlockTexture(ESItems.PURPLE_SCARLET_BUD.get());
        basicItemWithBlockTexture(ESItems.RED_SCARLET_BUD.get());
        basicItemWithBlockTexture(ESItems.SCARLET_GRASS.get());

        basicItemWithBlockTexture(ESItems.WITHERED_STARLIGHT_FLOWER.get());

        basicItemWithBlockTexture(ESItems.DEAD_LUNAR_BUSH.get());

        basicItemWithBlockTexture(ESItems.MOONLIGHT_LILY_PAD.get());
        basicItemWithBlockTexture(ESItems.STARLIT_LILY_PAD.get());
        basicItemWithBlockTexture(ESItems.MOONLIGHT_DUCKWEED.get());

        basicItemWithBlockTexture(ESItems.CRYSTALLIZED_LUNAR_GRASS.get());
        basicItemWithBlockTexture(ESItems.RED_CRYSTAL_ROOTS.get());
        basicItemWithBlockTexture(ESItems.BLUE_CRYSTAL_ROOTS.get());
        basicItem(ESItems.TWILVEWRYM_HERB.get(), blockTextureFromItem(ESItems.TWILVEWRYM_HERB.get()).withSuffix("_top"));
        basicItem(ESItems.STELLAFLY_BUSH.get(), blockTextureFromItem(ESItems.STELLAFLY_BUSH.get()).withSuffix("_top"));
        basicItem(ESItems.GLIMMERFLY_BUSH.get(), blockTextureFromItem(ESItems.GLIMMERFLY_BUSH.get()).withSuffix("_top"));

        basicBlockItem(ESItems.NIGHTSHADE_DIRT.get());
        basicBlockItem(ESItems.NIGHTSHADE_GRASS_BLOCK.get());
        basicBlockItem(ESItems.FANTASY_GRASS_BLOCK.get());

        basicBlockItem(ESItems.AETHERSENT_BLOCK.get());
        basicItem(ESItems.AETHERSENT_INGOT.get());
        basicHandheld(ESItems.RAGE_OF_STARS.get());
        bow(ESItems.STARFALL_LONGBOW.get());
        armorWithTrim((ArmorItem) ESItems.AETHERSENT_HOOD.get());
        basicItem(ESItems.AETHERSENT_CAPE.get());
        basicItem(ESItems.AETHERSENT_BOTTOMS.get());
        basicItem(ESItems.AETHERSENT_BOOTS.get());

        basicBlockItem(ESItems.SPRINGSTONE.get());
        basicBlockItem(ESItems.THERMAL_SPRINGSTONE.get());
        basicItem(ESItems.THERMAL_SPRINGSTONE_INGOT.get());
        basicHandheld(ESItems.THERMAL_SPRINGSTONE_SWORD.get());
        basicHandheld(ESItems.THERMAL_SPRINGSTONE_PICKAXE.get());
        basicHandheld(ESItems.THERMAL_SPRINGSTONE_AXE.get());
        basicHandheld(ESItems.THERMAL_SPRINGSTONE_SCYTHE.get());
        inventoryModel(ESItems.THERMAL_SPRINGSTONE_HAMMER.get());
        basicItem(ESItems.THERMAL_SPRINGSTONE_HELMET.get());
        basicItem(ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get());
        basicItem(ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get());
        basicItem(ESItems.THERMAL_SPRINGSTONE_BOOTS.get());

        basicBlockItem(ESItems.SWAMP_SILVER_ORE.get());
        basicBlockItem(ESItems.SWAMP_SILVER_BLOCK.get());
        basicItem(ESItems.SWAMP_SILVER_INGOT.get());
        basicItem(ESItems.SWAMP_SILVER_NUGGET.get());
        basicHandheld(ESItems.SWAMP_SILVER_SWORD.get());
        basicHandheld(ESItems.SWAMP_SILVER_PICKAXE.get());
        basicHandheld(ESItems.SWAMP_SILVER_AXE.get());
        basicHandheld(ESItems.SWAMP_SILVER_SICKLE.get());
        armorWithTrim((ArmorItem) ESItems.SWAMP_SILVER_HELMET.get());
        armorWithTrim((ArmorItem) ESItems.SWAMP_SILVER_CHESTPLATE.get());
        basicItem(ESItems.SWAMP_SILVER_LEGGINGS.get());
        basicItem(ESItems.SWAMP_SILVER_BOOTS.get());

        basicBlockItem(ESItems.GRIMSTONE_REDSTONE_ORE.get());
        basicBlockItem(ESItems.VOIDSTONE_REDSTONE_ORE.get());

        basicBlockItem(ESItems.GRIMSTONE_SALTPETER_ORE.get());
        basicBlockItem(ESItems.VOIDSTONE_SALTPETER_ORE.get());
        basicBlockItem(ESItems.SALTPETER_BLOCK.get());
        basicItem(ESItems.SALTPETER_POWDER.get());
        basicItem(ESItems.SALTPETER_MATCHBOX.get());

        basicItem(ESItems.RAW_AMARAMBER.get());
        basicItem(ESItems.AMARAMBER_INGOT.get());
        basicItem(ESItems.AMARAMBER_NUGGET.get());
        basicItem(ESItems.AMARAMBER_LANTERN.get());
        basicItem(ESItems.AMARAMBER_CANDLE.get());
        basicItem(ESItems.AMARAMBER_ARROW.get());
        basicItem(ESItems.AMARAMBER_AXE.get());
        basicItem(ESItems.AMARAMBER_HOE.get());
        basicItem(ESItems.AMARAMBER_SHOVEL.get());
        armorWithTrim((ArmorItem) ESItems.AMARAMBER_HELMET.get());
        armorWithTrim((ArmorItem) ESItems.AMARAMBER_CHESTPLATE.get());

        shatteredSword(ESItems.SHATTERED_SWORD.get());
        basicItem(ESItems.SHATTERED_SWORD_BLADE.get());
        basicItem(ESItems.GOLEM_STEEL_INGOT.get());
        basicItem(ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get());
        basicHandheld(ESItems.ENERGY_SWORD.get());
        basicItem(ESItems.TENACIOUS_PETAL.get());
        crossbow(ESItems.CRYSTAL_CROSSBOW.get());
        bow(ESItems.MOONRING_BOW.get());
        largeHandheld(ESItems.MOONRING_GREATSWORD.get());
        inventoryModel(ESItems.MOONRING_GREATSWORD.get());
        largeHandheld(ESItems.PETAL_SCYTHE.get());
        inventoryModel(ESItems.PETAL_SCYTHE.get());
        basicHandheld(ESItems.WAND_OF_TELEPORTATION.get());
        basicItem(ESItems.SEEKING_EYE.get());

        basicItem(ESItems.LUMINOFISH_BUCKET.get());
        basicItem(ESItems.LUMINOFISH.get());
        basicItem(ESItems.COOKED_LUMINOFISH.get());

        basicItem(ESItems.LUMINARIS_BUCKET.get());
        basicItem(ESItems.LUMINARIS.get());
        basicItem(ESItems.COOKED_LUMINARIS.get());

        basicBlockItem(ESItems.WHITE_YETI_FUR.get());
        basicBlockItem(ESItems.ORANGE_YETI_FUR.get());
        basicBlockItem(ESItems.MAGENTA_YETI_FUR.get());
        basicBlockItem(ESItems.LIGHT_BLUE_YETI_FUR.get());
        basicBlockItem(ESItems.YELLOW_YETI_FUR.get());
        basicBlockItem(ESItems.LIME_YETI_FUR.get());
        basicBlockItem(ESItems.PINK_YETI_FUR.get());
        basicBlockItem(ESItems.GRAY_YETI_FUR.get());
        basicBlockItem(ESItems.LIGHT_GRAY_YETI_FUR.get());
        basicBlockItem(ESItems.CYAN_YETI_FUR.get());
        basicBlockItem(ESItems.PURPLE_YETI_FUR.get());
        basicBlockItem(ESItems.BLUE_YETI_FUR.get());
        basicBlockItem(ESItems.BROWN_YETI_FUR.get());
        basicBlockItem(ESItems.GREEN_YETI_FUR.get());
        basicBlockItem(ESItems.RED_YETI_FUR.get());
        basicBlockItem(ESItems.BLACK_YETI_FUR.get());

        basicBlockItem(ESItems.WHITE_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.ORANGE_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.MAGENTA_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.LIGHT_BLUE_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.YELLOW_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.LIME_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.PINK_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.GRAY_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.LIGHT_GRAY_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.CYAN_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.PURPLE_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.BLUE_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.BROWN_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.GREEN_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.RED_YETI_FUR_CARPET.get());
        basicBlockItem(ESItems.BLACK_YETI_FUR_CARPET.get());

        basicHandheld(ESItems.AURORA_DEER_ANTLER.get());
        basicItem(ESItems.AURORA_DEER_STEAK.get());
        basicItem(ESItems.COOKED_AURORA_DEER_STEAK.get());

        basicItem(ESItems.FROZEN_TUBE.get());

        orbOfProphecyInventory(ESItems.ORB_OF_PROPHECY.get());
        basicBlockItem(ESItems.ENCHANTED_GRIMSTONE_BRICKS.get());
        basicItem(ESItems.MANA_CRYSTAL.get());
        basicItem(ESItems.TERRA_CRYSTAL.get());
        basicItem(ESItems.WIND_CRYSTAL.get());
        basicItem(ESItems.WATER_CRYSTAL.get());
        basicItem(ESItems.LUNAR_CRYSTAL.get());
        basicItem(ESItems.BLAZE_CRYSTAL.get());
        basicItem(ESItems.LIGHT_CRYSTAL.get());
        basicItem(ESItems.MANA_CRYSTAL_SHARD.get());

        basicItem(ESItems.ETHER_BUCKET.get());
        basicBlockItem(ESItems.ENERGY_BLOCK.get());
        basicBlockItem(ESItems.STARLIGHT_GOLEM_SPAWNER.get());
        basicBlockItem(ESItems.LUNAR_MONSTROSITY_SPAWNER.get());
        basicItem(ESItems.STARLIGHT_SILVER_COIN.get());
        basicItem(ESItems.LOOT_BAG.get());
        basicItem(ESItems.BOOK.get());
    }

    private void armorWithTrim(ArmorItem armor) {
        ItemModelBuilder armorBuilder = basicItem(armor);
        for (ItemModelGenerators.TrimModelData trimModelData : ItemModelGenerators.GENERATED_TRIM_MODELS) {
            ModelFile trimModel = withExistingParent(name(armor) + "_" + trimModelData.name() + "_trim", mcLoc("item/generated"))
                    .texture("layer0", modLoc("item/" + name(armor)))
                    .texture("layer1", mcLoc("trims/items/" + armor.getType().getName() + "_trim_" + trimModelData.name()));
            armorBuilder.override().predicate(new ResourceLocation("trim_type"), trimModelData.itemModelIndex()).model(trimModel).end();
        }
    }

    private void shatteredSword(Item item) {
        ModelFile noBladeModel = withExistingParent(name(item) + "_no_blade", "item/handheld")
                .texture("layer0", itemTexture(item).withSuffix("_no_blade"));
        withExistingParent(name(item), "item/handheld")
                .texture("layer0", itemTexture(item))
                .override().predicate(new ResourceLocation("no_blade"), 1).model(noBladeModel).end();
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
                .override().predicate(new ResourceLocation("pulling"), 1).model(pull0).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.58).model(pull1).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 1.0).model(pull2).end()
                .override().predicate(new ResourceLocation("charged"), 1).model(arrow).end()
                .override().predicate(new ResourceLocation("charged"), 1).predicate(new ResourceLocation("firework"), 1).model(firework).end();
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
                .override().predicate(new ResourceLocation("pulling"), 1).model(pull0).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.65).model(pull1).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.9).model(pull2).end();
    }

    private void orbOfProphecyInventory(Item item) {
        ModelFile withCrestModel = withExistingParent(name(item) + "_with_crests_inventory", "item/generated")
                .texture("layer0", itemTexture(item).withSuffix("_with_crests_inventory"));
        ModelFile temporaryModel = withExistingParent(name(item) + "_temporary_inventory", "item/generated")
                .texture("layer0", itemTexture(item).withSuffix("_temporary_inventory"));
        withExistingParent(name(item) + "_inventory", "item/generated")
                .texture("layer0", itemTexture(item).withSuffix("_inventory"))
                .override().predicate(new ResourceLocation("crests_mode"), 0.5f).model(temporaryModel).end()
                .override().predicate(new ResourceLocation("crests_mode"), 1).model(withCrestModel).end();
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

    private void basicBlockItem(Item item) {
        withExistingParent(name(item), modLoc(ModelProvider.BLOCK_FOLDER + "/" + name(item)));
    }

    private void basicItemWithBlockTexture(Item item) {
        basicItem(item, blockTextureFromItem(item));
    }

    private ItemModelBuilder basicItem(Item item, ResourceLocation texture) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", texture);
    }

    private ItemModelBuilder largeHandheld(Item item) {
        return basicHandheld(item, itemTexture(item), true);
    }

    private ItemModelBuilder basicHandheld(Item item) {
        return basicHandheld(item, itemTexture(item), false);
    }

    private ItemModelBuilder basicHandheld(Item item, ResourceLocation texture, boolean large) {
        return getBuilder(item.toString())
                .parent(large ? new ModelFile.UncheckedModelFile(EternalStarlight.MOD_ID + ":item/large_handheld") : new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", texture);
    }

    private ItemModelBuilder inventoryModel(Item item) {
        return getBuilder(item.toString() + "_inventory")
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
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
        return new ResourceLocation(key.getNamespace(), prefix + "/" + key.getPath());
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
