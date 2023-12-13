package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ESRecipeProvider extends RecipeProvider {
    public ESRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        addWoodRecipes(recipeOutput);
        addStoneRecipes(recipeOutput);
        addAetherSentRecipes(recipeOutput);
        addSwampSilverRecipes(recipeOutput);
        addThermalSpringstoneRecipes(recipeOutput);

        // flower -> dye
        addSingleConversion(recipeOutput, ItemInit.STARLIGHT_FLOWER.get(), Items.BLUE_DYE);
        addSingleConversion(recipeOutput, ItemInit.CONEBLOOM.get(), Items.BROWN_DYE);
        addSingleConversion(recipeOutput, ItemInit.NIGHTFAN.get(), Items.PURPLE_DYE);
        addSingleConversion(recipeOutput, ItemInit.PINK_ROSE.get(), Items.PINK_DYE);
        addSingleConversion(recipeOutput, ItemInit.STARLIGHT_TORCHFLOWER.get(), Items.YELLOW_DYE);
        addSingleConversion(recipeOutput, ItemInit.SWAMP_ROSE.get(), Items.GREEN_DYE);

        // smelt
        addSmelt(recipeOutput, "smelting", RecipeSerializer.SMELTING_RECIPE, "golem_steel_ingot", 200, ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get(), ItemInit.GOLEM_STEEL_INGOT.get(), ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get());
        addSmelt(recipeOutput, "blasting", RecipeSerializer.BLASTING_RECIPE, "golem_steel_ingot", 100, ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get(), ItemInit.GOLEM_STEEL_INGOT.get(), ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get());

        // misc
        addShapeless(recipeOutput, "muddy_starlight_mangrove_roots", ItemInit.STARLIGHT_MANGROVE_ROOTS.get(), ItemInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get(), 1, ItemInit.STARLIGHT_MANGROVE_ROOTS.get(), ItemInit.NIGHTSHADE_MUD.get());
        addShapeless(recipeOutput, "packed_nightshade_mud", ItemInit.NIGHTSHADE_MUD.get(), ItemInit.PACKED_NIGHTSHADE_MUD.get(), 1, ItemInit.NIGHTSHADE_MUD.get(), ItemInit.LUNAR_BERRIES.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.SEEKING_EYE.get())
                .pattern("FFF")
                .pattern("FPF")
                .pattern("FFF")
                .define('P', Items.ENDER_PEARL)
                .define('F', ItemInit.STARLIGHT_FLOWER.get())
                .unlockedBy("has_item", has(ItemInit.STARLIGHT_FLOWER.get()))
                .save(recipeOutput, getModLocation("seeking_eye"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.CRYSTAL_CROSSBOW.get())
                .pattern("BGB")
                .pattern("STS")
                .pattern(" R ")
                .define('S', Items.STRING)
                .define('T', Items.TRIPWIRE_HOOK)
                .define('G', ItemInit.GOLEM_STEEL_INGOT.get())
                .define('B', ItemInit.BLUE_STARLIGHT_CRYSTAL_SHARD.get())
                .define('R', ItemInit.RED_STARLIGHT_CRYSTAL_SHARD.get())
                .unlockedBy("has_item", has(ItemInit.GOLEM_STEEL_INGOT.get()))
                .save(recipeOutput, getModLocation("crystal_crossbow"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.MOONRING_GREATSWORD.get())
                .pattern("TTT")
                .pattern("TGT")
                .pattern("SSS")
                .define('S', Items.STICK)
                .define('G', ItemInit.GOLEM_STEEL_INGOT.get())
                .define('T', ItemInit.TENACIOUS_PETAL.get())
                .unlockedBy("has_item", has(ItemInit.TENACIOUS_PETAL.get()))
                .save(recipeOutput, getModLocation("moonring_greatsword"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.PETAL_SCYTHE.get())
                .pattern("GTT")
                .pattern("GS ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('G', ItemInit.GOLEM_STEEL_INGOT.get())
                .define('T', ItemInit.TENACIOUS_PETAL.get())
                .unlockedBy("has_item", has(ItemInit.TENACIOUS_PETAL.get()))
                .save(recipeOutput, getModLocation("petal_scythe"));
    }

    private void addWoodRecipes(RecipeOutput recipeOutput) {
        addButton(recipeOutput, "lunar", BlockInit.LUNAR_BUTTON.get(), BlockInit.LUNAR_PLANKS.get());
        addDoor(recipeOutput, "lunar", BlockInit.LUNAR_DOOR.get(), BlockInit.LUNAR_PLANKS.get());
        addFence(recipeOutput, "lunar", BlockInit.LUNAR_FENCE.get(), BlockInit.LUNAR_PLANKS.get());
        addFenceGate(recipeOutput, "lunar", BlockInit.LUNAR_FENCE_GATE.get(), BlockInit.LUNAR_PLANKS.get());
        addPlanks(recipeOutput, "lunar", BlockInit.LUNAR_PLANKS.get(), ESTags.Items.LUNAR_LOGS);
        addWood(recipeOutput, "lunar", BlockInit.LUNAR_WOOD.get(), BlockInit.LUNAR_LOG.get());
        addStrippedWood(recipeOutput, "lunar", BlockInit.STRIPPED_LUNAR_WOOD.get(), BlockInit.STRIPPED_LUNAR_LOG.get());
        addPressurePlate(recipeOutput, "lunar", BlockInit.LUNAR_PRESSURE_PLATE.get(), BlockInit.LUNAR_PLANKS.get());
        addSlab(recipeOutput, "lunar", BlockInit.LUNAR_SLAB.get(), BlockInit.LUNAR_PLANKS.get());
        addStairs(recipeOutput, "lunar", BlockInit.LUNAR_STAIRS.get(), BlockInit.LUNAR_PLANKS.get());
        addTrapdoor(recipeOutput, "lunar", BlockInit.LUNAR_TRAPDOOR.get(), BlockInit.LUNAR_PLANKS.get());
        addSign(recipeOutput, "lunar", ItemInit.LUNAR_SIGN.get(), BlockInit.LUNAR_PLANKS.get());
        addHangingSign(recipeOutput, "lunar", ItemInit.LUNAR_HANGING_SIGN.get(), BlockInit.STRIPPED_LUNAR_LOG.get());
        addBoat(recipeOutput, ItemInit.LUNAR_BOAT.get(), ItemInit.LUNAR_CHEST_BOAT.get(), BlockInit.LUNAR_PLANKS.get());

        addButton(recipeOutput, "northland", BlockInit.NORTHLAND_BUTTON.get(), BlockInit.NORTHLAND_PLANKS.get());
        addDoor(recipeOutput, "northland", BlockInit.NORTHLAND_DOOR.get(), BlockInit.NORTHLAND_PLANKS.get());
        addFence(recipeOutput, "northland", BlockInit.NORTHLAND_FENCE.get(), BlockInit.NORTHLAND_PLANKS.get());
        addFenceGate(recipeOutput, "northland", BlockInit.NORTHLAND_FENCE_GATE.get(), BlockInit.NORTHLAND_PLANKS.get());
        addPlanks(recipeOutput, "northland", BlockInit.NORTHLAND_PLANKS.get(), ESTags.Items.NORTHLAND_LOGS);
        addWood(recipeOutput, "northland", BlockInit.NORTHLAND_WOOD.get(), BlockInit.NORTHLAND_LOG.get());
        addStrippedWood(recipeOutput, "northland", BlockInit.STRIPPED_NORTHLAND_WOOD.get(), BlockInit.STRIPPED_NORTHLAND_LOG.get());
        addPressurePlate(recipeOutput, "northland", BlockInit.NORTHLAND_PRESSURE_PLATE.get(), BlockInit.NORTHLAND_PLANKS.get());
        addSlab(recipeOutput, "northland", BlockInit.NORTHLAND_SLAB.get(), BlockInit.NORTHLAND_PLANKS.get());
        addStairs(recipeOutput, "northland", BlockInit.NORTHLAND_STAIRS.get(), BlockInit.NORTHLAND_PLANKS.get());
        addTrapdoor(recipeOutput, "northland", BlockInit.NORTHLAND_TRAPDOOR.get(), BlockInit.NORTHLAND_PLANKS.get());
        addSign(recipeOutput, "northland", ItemInit.NORTHLAND_SIGN.get(), BlockInit.NORTHLAND_PLANKS.get());
        addHangingSign(recipeOutput, "northland", ItemInit.NORTHLAND_HANGING_SIGN.get(), BlockInit.STRIPPED_NORTHLAND_LOG.get());
        addBoat(recipeOutput, ItemInit.NORTHLAND_BOAT.get(), ItemInit.NORTHLAND_CHEST_BOAT.get(), BlockInit.NORTHLAND_PLANKS.get());

        addButton(recipeOutput, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_BUTTON.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addDoor(recipeOutput, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_DOOR.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addFence(recipeOutput, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_FENCE.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addFenceGate(recipeOutput, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_FENCE_GATE.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addPlanks(recipeOutput, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_PLANKS.get(), ESTags.Items.STARLIGHT_MANGROVE_LOGS);
        addWood(recipeOutput, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_WOOD.get(), BlockInit.STARLIGHT_MANGROVE_LOG.get());
        addStrippedWood(recipeOutput, "starlight_mangrove", BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get(), BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
        addPressurePlate(recipeOutput, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addSlab(recipeOutput, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_SLAB.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addStairs(recipeOutput, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_STAIRS.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addTrapdoor(recipeOutput, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_TRAPDOOR.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addSign(recipeOutput, "starlight_mangrove", ItemInit.STARLIGHT_MANGROVE_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addHangingSign(recipeOutput, "starlight_mangrove", ItemInit.STARLIGHT_MANGROVE_HANGING_SIGN.get(), BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
        addBoat(recipeOutput, ItemInit.STARLIGHT_MANGROVE_BOAT.get(), ItemInit.STARLIGHT_MANGROVE_CHEST_BOAT.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
    }

    private void addStoneRecipes(RecipeOutput recipeOutput) {
        addPolished(recipeOutput, "grimstone", BlockInit.POLISHED_GRIMSTONE.get(), BlockInit.GRIMSTONE.get());
        addBricks(recipeOutput, "grimstone", BlockInit.GRIMSTONE_BRICKS.get(), BlockInit.POLISHED_GRIMSTONE.get());
        addChiseled(recipeOutput, "grimstone", BlockInit.CHISELED_GRIMSTONE.get(), BlockInit.GRIMSTONE_BRICK_SLAB.get());
        addStoneCuttingChiseled(recipeOutput, "grimstone", BlockInit.GRIMSTONE_BRICKS.get(), ItemInit.CHISELED_GRIMSTONE.get());
        addStoneCuttingBricks(recipeOutput, "grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.GRIMSTONE_BRICKS.get());
        addWall(recipeOutput, "grimstone", BlockInit.GRIMSTONE_BRICK_WALL.get(), BlockInit.GRIMSTONE_BRICKS.get());
        addStoneCuttingWall(recipeOutput, "grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.GRIMSTONE_BRICK_WALL.get());
        addStoneCuttingWall(recipeOutput, "grimstone", BlockInit.GRIMSTONE_BRICKS.get(), ItemInit.GRIMSTONE_BRICK_WALL.get());
        addStairs(recipeOutput, "grimstone", BlockInit.GRIMSTONE_BRICK_STAIRS.get(), BlockInit.GRIMSTONE_BRICKS.get());
        addStoneCuttingStairs(recipeOutput, "grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.GRIMSTONE_BRICK_STAIRS.get());
        addStoneCuttingStairs(recipeOutput, "grimstone", BlockInit.GRIMSTONE_BRICKS.get(), ItemInit.GRIMSTONE_BRICK_STAIRS.get());
        addSlab(recipeOutput, "grimstone", BlockInit.GRIMSTONE_BRICK_SLAB.get(), BlockInit.GRIMSTONE_BRICKS.get());
        addStoneCuttingSlab(recipeOutput, "grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.GRIMSTONE_BRICK_SLAB.get());
        addStoneCuttingSlab(recipeOutput, "grimstone", BlockInit.GRIMSTONE_BRICKS.get(), ItemInit.GRIMSTONE_BRICK_SLAB.get());
        addWall(recipeOutput, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE_WALL.get(), BlockInit.POLISHED_GRIMSTONE.get());
        addStoneCuttingWall(recipeOutput, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.POLISHED_GRIMSTONE_WALL.get());
        addStairs(recipeOutput, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE_STAIRS.get(), BlockInit.POLISHED_GRIMSTONE.get());
        addStoneCuttingStairs(recipeOutput, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.POLISHED_GRIMSTONE_STAIRS.get());
        addSlab(recipeOutput, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE_SLAB.get(), BlockInit.POLISHED_GRIMSTONE.get());
        addStoneCuttingSlab(recipeOutput, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.POLISHED_GRIMSTONE_SLAB.get());

        addPolished(recipeOutput, "voidstone", BlockInit.POLISHED_VOIDSTONE.get(), BlockInit.VOIDSTONE.get());
        addBricks(recipeOutput, "voidstone", BlockInit.VOIDSTONE_BRICKS.get(), BlockInit.POLISHED_VOIDSTONE.get());
        addChiseled(recipeOutput, "voidstone", BlockInit.CHISELED_VOIDSTONE.get(), BlockInit.VOIDSTONE_BRICK_SLAB.get());
        addStoneCuttingChiseled(recipeOutput, "voidstone", BlockInit.VOIDSTONE_BRICKS.get(), ItemInit.CHISELED_VOIDSTONE.get());
        addStoneCuttingBricks(recipeOutput, "voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.VOIDSTONE_BRICKS.get());
        addWall(recipeOutput, "voidstone", BlockInit.VOIDSTONE_BRICK_WALL.get(), BlockInit.VOIDSTONE_BRICKS.get());
        addStoneCuttingWall(recipeOutput, "voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.VOIDSTONE_BRICK_WALL.get());
        addStoneCuttingWall(recipeOutput, "voidstone", BlockInit.VOIDSTONE_BRICKS.get(), ItemInit.VOIDSTONE_BRICK_WALL.get());
        addStairs(recipeOutput, "voidstone", BlockInit.VOIDSTONE_BRICK_STAIRS.get(), BlockInit.VOIDSTONE_BRICKS.get());
        addStoneCuttingStairs(recipeOutput, "voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.VOIDSTONE_BRICK_STAIRS.get());
        addStoneCuttingStairs(recipeOutput, "voidstone", BlockInit.VOIDSTONE_BRICKS.get(), ItemInit.VOIDSTONE_BRICK_STAIRS.get());
        addSlab(recipeOutput, "voidstone", BlockInit.VOIDSTONE_BRICK_SLAB.get(), BlockInit.VOIDSTONE_BRICKS.get());
        addStoneCuttingSlab(recipeOutput, "voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.VOIDSTONE_BRICK_SLAB.get());
        addStoneCuttingSlab(recipeOutput, "voidstone", BlockInit.VOIDSTONE_BRICKS.get(), ItemInit.VOIDSTONE_BRICK_SLAB.get());
        addWall(recipeOutput, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE_WALL.get(), BlockInit.POLISHED_VOIDSTONE.get());
        addStoneCuttingWall(recipeOutput, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.POLISHED_VOIDSTONE_WALL.get());
        addStairs(recipeOutput, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE_STAIRS.get(), BlockInit.POLISHED_VOIDSTONE.get());
        addStoneCuttingStairs(recipeOutput, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.POLISHED_VOIDSTONE_STAIRS.get());
        addSlab(recipeOutput, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE_SLAB.get(), BlockInit.POLISHED_VOIDSTONE.get());
        addStoneCuttingSlab(recipeOutput, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.POLISHED_VOIDSTONE_SLAB.get());

        addPolished(recipeOutput, "doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), BlockInit.DOOMEDEN_BRICKS.get());
        addBricks(recipeOutput, "doomeden", BlockInit.DOOMEDEN_BRICKS.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        addChiseled(recipeOutput, "doomeden", BlockInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get(), BlockInit.DOOMEDEN_BRICK_SLAB.get());
        addStoneCuttingChiseled(recipeOutput, "doomeden", BlockInit.DOOMEDEN_BRICKS.get(), ItemInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        addStoneCuttingBricks(recipeOutput, "doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICKS.get());
        addWall(recipeOutput, "doomeden", BlockInit.DOOMEDEN_BRICK_WALL.get(), BlockInit.DOOMEDEN_BRICKS.get());
        addStoneCuttingWall(recipeOutput, "doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_WALL.get());
        addStoneCuttingWall(recipeOutput, "doomeden", BlockInit.DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_WALL.get());
        addStairs(recipeOutput, "doomeden", BlockInit.DOOMEDEN_BRICK_STAIRS.get(), BlockInit.DOOMEDEN_BRICKS.get());
        addStoneCuttingStairs(recipeOutput, "doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_STAIRS.get());
        addStoneCuttingStairs(recipeOutput, "doomeden", BlockInit.DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_STAIRS.get());
        addSlab(recipeOutput, "doomeden", BlockInit.DOOMEDEN_BRICK_SLAB.get(), BlockInit.DOOMEDEN_BRICKS.get());
        addStoneCuttingSlab(recipeOutput, "doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_SLAB.get());
        addStoneCuttingSlab(recipeOutput, "doomeden", BlockInit.DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_SLAB.get());
        addWall(recipeOutput, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICK_WALL.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        addStoneCuttingWall(recipeOutput, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.POLISHED_DOOMEDEN_BRICK_WALL.get());
        addStairs(recipeOutput, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        addStoneCuttingStairs(recipeOutput, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get());
        addSlab(recipeOutput, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICK_SLAB.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        addStoneCuttingSlab(recipeOutput, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.POLISHED_DOOMEDEN_BRICK_SLAB.get());

        addBricks(recipeOutput, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS.get(), BlockInit.PACKED_NIGHTSHADE_MUD.get());
        addWall(recipeOutput, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICK_WALL.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        addStoneCuttingWall(recipeOutput, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS.get(), ItemInit.NIGHTSHADE_MUD_BRICK_WALL.get());
        addStairs(recipeOutput, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        addStoneCuttingStairs(recipeOutput, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS.get(), ItemInit.NIGHTSHADE_MUD_BRICK_STAIRS.get());
        addSlab(recipeOutput, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICK_SLAB.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        addStoneCuttingSlab(recipeOutput, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS.get(), ItemInit.NIGHTSHADE_MUD_BRICK_SLAB.get());

        addStoneCompress(recipeOutput, "twilight_sandstone_from_sand", BlockInit.TWILIGHT_SANDSTONE.get(), BlockInit.TWILIGHT_SAND.get());
        addWall(recipeOutput, "twilight_sandstone", BlockInit.TWILIGHT_SANDSTONE_WALL.get(), BlockInit.TWILIGHT_SANDSTONE.get());
        addStoneCuttingWall(recipeOutput, "twilight_sandstone", BlockInit.TWILIGHT_SANDSTONE.get(), ItemInit.TWILIGHT_SANDSTONE_WALL.get());
        addStairs(recipeOutput, "twilight_sandstone", BlockInit.TWILIGHT_SANDSTONE_STAIRS.get(), BlockInit.TWILIGHT_SANDSTONE.get());
        addStoneCuttingStairs(recipeOutput, "twilight_sandstone", BlockInit.TWILIGHT_SANDSTONE.get(), ItemInit.TWILIGHT_SANDSTONE_STAIRS.get());
        addSlab(recipeOutput, "twilight_sandstone", BlockInit.TWILIGHT_SANDSTONE_SLAB.get(), BlockInit.TWILIGHT_SANDSTONE.get());
        addStoneCuttingSlab(recipeOutput, "twilight_sandstone", BlockInit.TWILIGHT_SANDSTONE.get(), ItemInit.TWILIGHT_SANDSTONE_SLAB.get());
        addCut(recipeOutput, "twilight_sandstone", BlockInit.TWILIGHT_SANDSTONE.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get());
        addStoneCuttingCut(recipeOutput, "twilight_sandstone", BlockInit.TWILIGHT_SANDSTONE.get(), ItemInit.CUT_TWILIGHT_SANDSTONE.get());
        addWall(recipeOutput, "cut_twilight_sandstone", BlockInit.CUT_TWILIGHT_SANDSTONE_WALL.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get());
        addStoneCuttingWall(recipeOutput, "cut_twilight_sandstone", BlockInit.CUT_TWILIGHT_SANDSTONE.get(), ItemInit.CUT_TWILIGHT_SANDSTONE_WALL.get());
        addStairs(recipeOutput, "cut_twilight_sandstone", BlockInit.CUT_TWILIGHT_SANDSTONE_STAIRS.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get());
        addStoneCuttingStairs(recipeOutput, "cut_twilight_sandstone", BlockInit.CUT_TWILIGHT_SANDSTONE.get(), ItemInit.CUT_TWILIGHT_SANDSTONE_STAIRS.get());
        addSlab(recipeOutput, "cut_twilight_sandstone", BlockInit.CUT_TWILIGHT_SANDSTONE_SLAB.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get());
        addStoneCuttingSlab(recipeOutput, "cut_twilight_sandstone", BlockInit.CUT_TWILIGHT_SANDSTONE.get(), ItemInit.CUT_TWILIGHT_SANDSTONE_SLAB.get());
        addChiseled(recipeOutput, "twilight_sandstone", BlockInit.CHISELED_TWILIGHT_SANDSTONE.get(), BlockInit.CUT_TWILIGHT_SANDSTONE_SLAB.get());
        addStoneCuttingChiseled(recipeOutput, "twilight_sandstone", BlockInit.CUT_TWILIGHT_SANDSTONE.get(), ItemInit.CHISELED_TWILIGHT_SANDSTONE.get());
    }

    private void addAetherSentRecipes(RecipeOutput recipeOutput) {
        addCompressed(recipeOutput, "aethersent", ItemInit.AETHERSENT_INGOT.get(), ItemInit.AETHERSENT_BLOCK.get());
        addReverseCompressed(recipeOutput, "aethersent", ItemInit.AETHERSENT_BLOCK.get(), ItemInit.AETHERSENT_INGOT.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_HOOD.get())
                .pattern("###")
                .pattern("A A")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(recipeOutput, getModLocation("aethersent_hood"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_CAPE.get())
                .pattern("A A")
                .pattern("#A#")
                .pattern("###")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(recipeOutput, getModLocation("aethersent_cape"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_BOTTOMS.get())
                .pattern("###")
                .pattern("A A")
                .pattern("# #")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(recipeOutput, getModLocation("aethersent_bottoms"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_BOOTS.get())
                .pattern("A A")
                .pattern("# #")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(recipeOutput, getModLocation("aethersent_boots"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.STARFALL_LONGBOW.get())
                .pattern(" AS")
                .pattern("A S")
                .pattern(" AS")
                .define('S', Items.STRING)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(recipeOutput, getModLocation("starfall_longbow"));
        addSword(recipeOutput, "rage_of_stars", ItemInit.RAGE_OF_STARS.get(), ItemInit.AETHERSENT_INGOT.get(), Items.STICK);
    }

    private void addSwampSilverRecipes(RecipeOutput recipeOutput) {
        addCompressed(recipeOutput, "swamp_silver", ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_BLOCK.get());
        addReverseCompressed(recipeOutput, "swamp_silver", ItemInit.SWAMP_SILVER_BLOCK.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addAxe(recipeOutput, "swamp_silver_axe", ItemInit.SWAMP_SILVER_AXE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addPickaxe(recipeOutput, "swamp_silver_pickaxe", ItemInit.SWAMP_SILVER_PICKAXE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addHoe(recipeOutput, "swamp_silver_sickle", ItemInit.SWAMP_SILVER_SICKLE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addSword(recipeOutput, "swamp_silver_sword", ItemInit.SWAMP_SILVER_SWORD.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addHelmet(recipeOutput, "swamp_silver_helmet", ItemInit.SWAMP_SILVER_HELMET.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addChestplate(recipeOutput, "swamp_silver_chestplate", ItemInit.SWAMP_SILVER_CHESTPLATE.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addLeggings(recipeOutput, "swamp_silver_leggings", ItemInit.SWAMP_SILVER_LEGGINGS.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addBoots(recipeOutput, "swamp_silver_boots", ItemInit.SWAMP_SILVER_BOOTS.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.SWAMP_SILVER_INGOT.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Ingredient.of(ItemInit.SWAMP_SILVER_NUGGET.get()))
                .unlockedBy("has_item", has(ItemInit.SWAMP_SILVER_NUGGET.get()))
                .save(recipeOutput, getModLocation("swamp_silver_ingot_from_nuggets"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.SWAMP_SILVER_NUGGET.get(), 9)
                .requires(ItemInit.SWAMP_SILVER_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.SWAMP_SILVER_INGOT.get()))
                .save(recipeOutput, getModLocation("swamp_silver_nuggets_from_ingot"));
        addSmelt(recipeOutput, "smelting", RecipeSerializer.SMELTING_RECIPE, "swamp_silver_ingot", 200, ItemInit.SWAMP_SILVER_ORE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_ORE.get());
        addSmelt(recipeOutput, "blasting", RecipeSerializer.BLASTING_RECIPE, "swamp_silver_ingot", 100, ItemInit.SWAMP_SILVER_ORE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_ORE.get());
        addSmelt(recipeOutput, "smelting", RecipeSerializer.SMELTING_RECIPE, "swamp_silver_nugget", 200, ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_PICKAXE.get(), ItemInit.SWAMP_SILVER_AXE.get(), ItemInit.SWAMP_SILVER_SICKLE.get(), ItemInit.SWAMP_SILVER_SWORD.get(), ItemInit.SWAMP_SILVER_HELMET.get(), ItemInit.SWAMP_SILVER_CHESTPLATE.get(), ItemInit.SWAMP_SILVER_LEGGINGS.get(), ItemInit.SWAMP_SILVER_BOOTS.get());
        addSmelt(recipeOutput, "blasting", RecipeSerializer.BLASTING_RECIPE, "swamp_silver_nugget", 100, ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_PICKAXE.get(), ItemInit.SWAMP_SILVER_AXE.get(), ItemInit.SWAMP_SILVER_SICKLE.get(), ItemInit.SWAMP_SILVER_SWORD.get(), ItemInit.SWAMP_SILVER_HELMET.get(), ItemInit.SWAMP_SILVER_CHESTPLATE.get(), ItemInit.SWAMP_SILVER_LEGGINGS.get(), ItemInit.SWAMP_SILVER_BOOTS.get());
    }

    private void addThermalSpringstoneRecipes(RecipeOutput recipeOutput) {
        addAxe(recipeOutput, "thermal_springstone_axe", ItemInit.THERMAL_SPRINGSTONE_AXE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addPickaxe(recipeOutput, "thermal_springstone_pickaxe", ItemInit.THERMAL_SPRINGSTONE_PICKAXE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addHoe(recipeOutput, "thermal_springstone_scythe", ItemInit.THERMAL_SPRINGSTONE_SCYTHE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addSword(recipeOutput, "thermal_springstone_sword", ItemInit.THERMAL_SPRINGSTONE_SWORD.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addHammer(recipeOutput, "thermal_springstone_hammer", ItemInit.THERMAL_SPRINGSTONE_HAMMER.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addHelmet(recipeOutput, "thermal_springstone_helmet", ItemInit.THERMAL_SPRINGSTONE_HELMET.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addChestplate(recipeOutput, "thermal_springstone_chestplate", ItemInit.THERMAL_SPRINGSTONE_CHESTPLATE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addLeggings(recipeOutput, "thermal_springstone_leggings", ItemInit.THERMAL_SPRINGSTONE_LEGGINGS.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addBoots(recipeOutput, "thermal_springstone_boots", ItemInit.THERMAL_SPRINGSTONE_BOOTS.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addSmelt(recipeOutput, "smelting", RecipeSerializer.SMELTING_RECIPE, "thermal_springstone_ingot", 200, ItemInit.THERMAL_SPRINGSTONE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), ItemInit.THERMAL_SPRINGSTONE.get());
        addSmelt(recipeOutput, "blasting", RecipeSerializer.BLASTING_RECIPE, "thermal_springstone_ingot", 100, ItemInit.THERMAL_SPRINGSTONE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), ItemInit.THERMAL_SPRINGSTONE.get());
    }

    // misc
    protected final void addSmelt(RecipeOutput recipeOutput, String recipeTypeName, RecipeSerializer<? extends AbstractCookingRecipe> recipeSerializer, String id, int time, ItemLike criteria, ItemLike output, ItemLike... input) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(input), RecipeCategory.MISC, output, 1.0f, time, recipeSerializer).unlockedBy("has_item", has(criteria)).save(recipeOutput, getModLocation(id + "_" + recipeTypeName));
    }

    protected final void addCompressed(RecipeOutput recipeOutput, String id, ItemLike toCompress, ItemLike output) {
        addCompressed(recipeOutput, id, RecipeCategory.MISC, toCompress, output);
    }

    protected final void addCompressed(RecipeOutput recipeOutput, String id, RecipeCategory category, ItemLike toCompress, ItemLike output) {
        ShapedRecipeBuilder.shaped(category, output)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Ingredient.of(toCompress))
                .unlockedBy("has_item", has(toCompress))
                .save(recipeOutput, getModLocation("compressed/" + id));
    }

    protected final void addReverseCompressed(RecipeOutput recipeOutput, String id, ItemLike compressed, ItemLike output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 9)
                .requires(compressed)
                .unlockedBy("has_item", has(compressed))
                .save(recipeOutput, getModLocation("compressed/reversed/" + id));
    }

    protected final void addSingleConversion(RecipeOutput recipeOutput, Item from, Item to) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, to)
                .requires(from)
                .unlockedBy("has_item", has(from))
                .save(recipeOutput, getModLocation("shapeless/" + BuiltInRegistries.ITEM.getKey(to).getPath() + "_from_" + BuiltInRegistries.ITEM.getKey(from).getPath()));
    }

    protected final void addShapeless(RecipeOutput recipeOutput, String id, ItemLike criteria, ItemLike output, int num, ItemLike... ingredients) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, num);
        for (ItemLike item : ingredients) {
            builder.requires(item);
        }
        builder.unlockedBy("has_item", has(criteria)).save(recipeOutput, getModLocation("shapeless/" + id));
    }

    // combat & tools
    protected final void addHelmet(RecipeOutput recipeOutput, String id, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("###")
                .pattern("# #")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id));
    }

    protected final void addChestplate(RecipeOutput recipeOutput, String id, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id));
    }

    protected final void addLeggings(RecipeOutput recipeOutput, String id, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id));
    }

    protected final void addBoots(RecipeOutput recipeOutput, String id, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("# #")
                .pattern("# #")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id));
    }

    protected final void addHoe(RecipeOutput recipeOutput, String id, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("##")
                .pattern(" H")
                .pattern(" H")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id));
    }

    protected final void addPickaxe(RecipeOutput recipeOutput, String id, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("###")
                .pattern(" H ")
                .pattern(" H ")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id));
    }

    protected final void addSword(RecipeOutput recipeOutput, String id, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("#")
                .pattern("#")
                .pattern("H")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id));
    }

    protected final void addAxe(RecipeOutput recipeOutput, String id, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("##")
                .pattern("#H")
                .pattern(" H")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id));
    }

    protected final void addHammer(RecipeOutput recipeOutput, String id, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("###")
                .pattern("#H#")
                .pattern(" H ")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id));
    }

    // building blocks and wooden stuff
    protected final void addPlanks(RecipeOutput recipeOutput, String id, Block output, TagKey<Item> input) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .requires(input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_planks"));
    }

    protected final void addWood(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_wood"));
    }

    protected final void addStrippedWood(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_stripped_wood"));
    }

    protected final void addButton(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, output)
                .requires(input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_button"));
    }

    protected final void addDoor(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output, 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_door"));
    }

    protected final void addFence(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("#S#")
                .pattern("#S#")
                .define('#', input)
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_fence"));
    }

    protected final void addFenceGate(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output)
                .pattern("S#S")
                .pattern("S#S")
                .define('#', input)
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_fence_gate"));
    }

    protected final void addPressurePlate(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output)
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_pressure_plate"));
    }

    protected final void addSlab(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_slab"));
    }

    protected final void addStairs(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 2)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_stairs"));
    }

    protected final void addTrapdoor(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output, 2)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_trapdoor"));
    }

    protected final void addSign(RecipeOutput recipeOutput, String id, ItemLike output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 3)
                .pattern("###")
                .pattern("###")
                .pattern(" S ")
                .define('#', input)
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_sign"));
    }

    protected final void addHangingSign(RecipeOutput recipeOutput, String id, ItemLike output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 6)
                .pattern("C C")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .define('C', Items.CHAIN)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput, getModLocation(id + "_hanging_sign"));
    }

    protected final void addBoat(RecipeOutput recipeOutput, Item boat, Item chestBoat, Block planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, boat)
                .pattern("P P")
                .pattern("PPP")
                .define('P', planks)
                .group("boat")
                .unlockedBy("in_water", insideOf(Blocks.WATER))
                .save(recipeOutput, getModLocation(BuiltInRegistries.ITEM.getKey(boat).getPath()));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, chestBoat)
                .requires(boat)
                .group("chest_boat")
                .unlockedBy("has_boat", has(ItemTags.BOATS))
                .save(recipeOutput, getModLocation(BuiltInRegistries.ITEM.getKey(chestBoat).getPath()));
    }

    // stone
    protected final void addPolished(RecipeOutput recipeOutput, String id, Block output, Block input) {
        addStoneCompress(recipeOutput, id + "_polished", output, input);
    }

    protected final void addBricks(RecipeOutput recipeOutput, String id, Block output, Block input) {
        addStoneCompress(recipeOutput, id + "_bricks", output, input);
    }

    protected final void addCut(RecipeOutput recipeOutput, String id, Block output, Block input) {
        addStoneCompress(recipeOutput, id + "_cut", output, input);
    }

    protected final void addStoneCompress(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(input).getPath(), has(input))
                .save(recipeOutput, getModLocation(id));
    }

    protected final void addWall(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(input).getPath(), has(input))
                .save(recipeOutput, getModLocation(id + "_wall"));
    }

    protected final void addChiseled(RecipeOutput recipeOutput, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("#")
                .pattern("#")
                .define('#', input)
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(input).getPath(), has(input))
                .save(recipeOutput, getModLocation(id + "_chiseled"));
    }

    protected final void addStoneCuttingChiseled(RecipeOutput recipeOutput, String id, Block input, ItemLike output) {
        addSimpleStoneCutting(recipeOutput, id + "_chiseled", input, output);
    }

    protected final void addStoneCuttingBricks(RecipeOutput recipeOutput, String id, Block input, ItemLike output) {
        addSimpleStoneCutting(recipeOutput, id + "_bricks", input, output);
    }

    protected final void addStoneCuttingCut(RecipeOutput recipeOutput, String id, Block input, ItemLike output) {
        addSimpleStoneCutting(recipeOutput, id + "_cut", input, output);
    }

    protected final void addStoneCuttingSlab(RecipeOutput recipeOutput, String id, Block input, ItemLike output) {
        addSimpleStoneCutting(recipeOutput, id + "_slab", input, output, 2);
    }

    protected final void addStoneCuttingStairs(RecipeOutput recipeOutput, String id, Block input, ItemLike output) {
        addSimpleStoneCutting(recipeOutput, id + "_stairs", input, output);
    }

    protected final void addStoneCuttingWall(RecipeOutput recipeOutput, String id, Block input, ItemLike output) {
        addSimpleStoneCutting(recipeOutput, id + "_wall", input, output);
    }

    protected final void addSimpleStoneCutting(RecipeOutput recipeOutput, String id, Block input, ItemLike output) {
        makeStonecuttingRecipeBuilder(input, output)
                .save(recipeOutput, getModLocation(id + "_stonecutting_from_" + BuiltInRegistries.BLOCK.getKey(input).getPath()));
    }

    protected final void addSimpleStoneCutting(RecipeOutput recipeOutput, String id, Block input, ItemLike output, int amount) {
        makeStonecuttingRecipeBuilder(input, output, amount)
                .save(recipeOutput, getModLocation(id + "_stonecutting_from_" + BuiltInRegistries.BLOCK.getKey(input).getPath()));
    }

    public SingleItemRecipeBuilder makeStonecuttingRecipeBuilder(Block input, ItemLike output) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, output)
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(input), has(input));
    }

    public SingleItemRecipeBuilder makeStonecuttingRecipeBuilder(Block input, ItemLike output, int outputAmount) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, output, outputAmount)
                .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(input), has(input));
    }

    protected final ResourceLocation getModLocation(String id) {
        return new ResourceLocation(EternalStarlight.MOD_ID, id);
    }
}
