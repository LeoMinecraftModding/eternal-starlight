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
import net.minecraft.world.item.crafting.*;
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
        addSingleConversion(recipeOutput, ItemInit.NIGHTFAN_BUSH.get(), Items.PURPLE_DYE);
        addSingleConversion(recipeOutput, ItemInit.PINK_ROSE_BUSH.get(), Items.PINK_DYE);
        addSingleConversion(recipeOutput, ItemInit.SWAMP_ROSE.get(), Items.GREEN_DYE);
        addSingleConversion(recipeOutput, ItemInit.WHISPERBLOOM.get(), Items.PINK_DYE);

        // food
        addCookingRecipes(recipeOutput, "smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100);
        addCookingRecipes(recipeOutput, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600);
        addSmelt(recipeOutput, 200, ItemInit.LUMINOFISH.get(), ItemInit.COOKED_LUMINOFISH.get(), ItemInit.LUMINOFISH.get());

        // ores
        addSmelt(recipeOutput, 200, ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get(), ItemInit.GOLEM_STEEL_INGOT.get(), ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get());
        addBlast(recipeOutput, 100, ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get(), ItemInit.GOLEM_STEEL_INGOT.get(), ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get());

        // misc
        addShapeless(recipeOutput, ItemInit.STARLIGHT_MANGROVE_ROOTS.get(), ItemInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get(), 1, ItemInit.STARLIGHT_MANGROVE_ROOTS.get(), ItemInit.NIGHTSHADE_MUD.get());
        addShapeless(recipeOutput, ItemInit.NIGHTSHADE_MUD.get(), ItemInit.PACKED_NIGHTSHADE_MUD.get(), 1, ItemInit.NIGHTSHADE_MUD.get(), ItemInit.LUNAR_BERRIES.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.SEEKING_EYE.get())
                .pattern("FFF")
                .pattern("FPF")
                .pattern("FFF")
                .define('P', Items.ENDER_PEARL)
                .define('F', ItemInit.STARLIGHT_FLOWER.get())
                .unlockedBy("has_item", has(ItemInit.STARLIGHT_FLOWER.get()))
                .save(recipeOutput);
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
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.MOONRING_GREATSWORD.get())
                .pattern("TTT")
                .pattern("TGT")
                .pattern("SSS")
                .define('S', Items.STICK)
                .define('G', ItemInit.GOLEM_STEEL_INGOT.get())
                .define('T', ItemInit.TENACIOUS_PETAL.get())
                .unlockedBy("has_item", has(ItemInit.TENACIOUS_PETAL.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.PETAL_SCYTHE.get())
                .pattern("GTT")
                .pattern("GS ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('G', ItemInit.GOLEM_STEEL_INGOT.get())
                .define('T', ItemInit.TENACIOUS_PETAL.get())
                .unlockedBy("has_item", has(ItemInit.TENACIOUS_PETAL.get()))
                .save(recipeOutput);
    }

    private <T extends AbstractCookingRecipe> void addCookingRecipes(RecipeOutput recipeOutput, String name, RecipeSerializer<T> recipeSerializer, AbstractCookingRecipe.Factory<T> factory, int time) {
        simpleCookingRecipe(recipeOutput, name, recipeSerializer, factory, time, ItemInit.LUMINOFISH.get(), ItemInit.COOKED_LUMINOFISH.get(), 0.35F);
    }

    private <T extends AbstractCookingRecipe> void simpleCookingRecipe(RecipeOutput recipeOutput, String name, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> factory, int time, ItemLike input, ItemLike output, float exp) {
        SimpleCookingRecipeBuilder builder = SimpleCookingRecipeBuilder.generic(Ingredient.of(input), RecipeCategory.FOOD, output, exp, time, serializer, factory).unlockedBy(getHasName(input), has(input));
        builder.save(recipeOutput, name(output) + "_from_" + name);
    }

    private void addWoodRecipes(RecipeOutput recipeOutput) {
        addButton(recipeOutput, BlockInit.LUNAR_BUTTON.get(), BlockInit.LUNAR_PLANKS.get());
        addDoor(recipeOutput, BlockInit.LUNAR_DOOR.get(), BlockInit.LUNAR_PLANKS.get());
        addFence(recipeOutput, BlockInit.LUNAR_FENCE.get(), BlockInit.LUNAR_PLANKS.get());
        addFenceGate(recipeOutput, BlockInit.LUNAR_FENCE_GATE.get(), BlockInit.LUNAR_PLANKS.get());
        addPlanks(recipeOutput, BlockInit.LUNAR_PLANKS.get(), ESTags.Items.LUNAR_LOGS);
        addWood(recipeOutput, BlockInit.LUNAR_WOOD.get(), BlockInit.LUNAR_LOG.get());
        addStrippedWood(recipeOutput, BlockInit.STRIPPED_LUNAR_WOOD.get(), BlockInit.STRIPPED_LUNAR_LOG.get());
        addPressurePlate(recipeOutput, BlockInit.LUNAR_PRESSURE_PLATE.get(), BlockInit.LUNAR_PLANKS.get());
        addSlab(recipeOutput, BlockInit.LUNAR_SLAB.get(), BlockInit.LUNAR_PLANKS.get());
        addStairs(recipeOutput, BlockInit.LUNAR_STAIRS.get(), BlockInit.LUNAR_PLANKS.get());
        addTrapdoor(recipeOutput, BlockInit.LUNAR_TRAPDOOR.get(), BlockInit.LUNAR_PLANKS.get());
        addSign(recipeOutput, ItemInit.LUNAR_SIGN.get(), BlockInit.LUNAR_PLANKS.get());
        hangingSign(recipeOutput, ItemInit.LUNAR_HANGING_SIGN.get(), BlockInit.STRIPPED_LUNAR_LOG.get());
        addBoat(recipeOutput, ItemInit.LUNAR_BOAT.get(), ItemInit.LUNAR_CHEST_BOAT.get(), BlockInit.LUNAR_PLANKS.get());

        addButton(recipeOutput, BlockInit.NORTHLAND_BUTTON.get(), BlockInit.NORTHLAND_PLANKS.get());
        addDoor(recipeOutput, BlockInit.NORTHLAND_DOOR.get(), BlockInit.NORTHLAND_PLANKS.get());
        addFence(recipeOutput, BlockInit.NORTHLAND_FENCE.get(), BlockInit.NORTHLAND_PLANKS.get());
        addFenceGate(recipeOutput, BlockInit.NORTHLAND_FENCE_GATE.get(), BlockInit.NORTHLAND_PLANKS.get());
        addPlanks(recipeOutput, BlockInit.NORTHLAND_PLANKS.get(), ESTags.Items.NORTHLAND_LOGS);
        addWood(recipeOutput, BlockInit.NORTHLAND_WOOD.get(), BlockInit.NORTHLAND_LOG.get());
        addStrippedWood(recipeOutput, BlockInit.STRIPPED_NORTHLAND_WOOD.get(), BlockInit.STRIPPED_NORTHLAND_LOG.get());
        addPressurePlate(recipeOutput, BlockInit.NORTHLAND_PRESSURE_PLATE.get(), BlockInit.NORTHLAND_PLANKS.get());
        addSlab(recipeOutput, BlockInit.NORTHLAND_SLAB.get(), BlockInit.NORTHLAND_PLANKS.get());
        addStairs(recipeOutput, BlockInit.NORTHLAND_STAIRS.get(), BlockInit.NORTHLAND_PLANKS.get());
        addTrapdoor(recipeOutput, BlockInit.NORTHLAND_TRAPDOOR.get(), BlockInit.NORTHLAND_PLANKS.get());
        addSign(recipeOutput, ItemInit.NORTHLAND_SIGN.get(), BlockInit.NORTHLAND_PLANKS.get());
        hangingSign(recipeOutput, ItemInit.NORTHLAND_HANGING_SIGN.get(), BlockInit.STRIPPED_NORTHLAND_LOG.get());
        addBoat(recipeOutput, ItemInit.NORTHLAND_BOAT.get(), ItemInit.NORTHLAND_CHEST_BOAT.get(), BlockInit.NORTHLAND_PLANKS.get());

        addButton(recipeOutput, BlockInit.SCARLET_BUTTON.get(), BlockInit.SCARLET_PLANKS.get());
        addDoor(recipeOutput, BlockInit.SCARLET_DOOR.get(), BlockInit.SCARLET_PLANKS.get());
        addFence(recipeOutput, BlockInit.SCARLET_FENCE.get(), BlockInit.SCARLET_PLANKS.get());
        addFenceGate(recipeOutput, BlockInit.SCARLET_FENCE_GATE.get(), BlockInit.SCARLET_PLANKS.get());
        addPlanks(recipeOutput, BlockInit.SCARLET_PLANKS.get(), ESTags.Items.SCARLET_LOGS);
        addWood(recipeOutput, BlockInit.SCARLET_WOOD.get(), BlockInit.SCARLET_LOG.get());
        addStrippedWood(recipeOutput, BlockInit.STRIPPED_SCARLET_WOOD.get(), BlockInit.STRIPPED_SCARLET_LOG.get());
        addPressurePlate(recipeOutput, BlockInit.SCARLET_PRESSURE_PLATE.get(), BlockInit.SCARLET_PLANKS.get());
        addSlab(recipeOutput, BlockInit.SCARLET_SLAB.get(), BlockInit.SCARLET_PLANKS.get());
        addStairs(recipeOutput, BlockInit.SCARLET_STAIRS.get(), BlockInit.SCARLET_PLANKS.get());
        addTrapdoor(recipeOutput, BlockInit.SCARLET_TRAPDOOR.get(), BlockInit.SCARLET_PLANKS.get());
        addSign(recipeOutput, ItemInit.SCARLET_SIGN.get(), BlockInit.SCARLET_PLANKS.get());
        hangingSign(recipeOutput, ItemInit.SCARLET_HANGING_SIGN.get(), BlockInit.STRIPPED_SCARLET_LOG.get());
        addBoat(recipeOutput, ItemInit.SCARLET_BOAT.get(), ItemInit.SCARLET_CHEST_BOAT.get(), BlockInit.SCARLET_PLANKS.get());

        addButton(recipeOutput, BlockInit.STARLIGHT_MANGROVE_BUTTON.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addDoor(recipeOutput, BlockInit.STARLIGHT_MANGROVE_DOOR.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addFence(recipeOutput, BlockInit.STARLIGHT_MANGROVE_FENCE.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addFenceGate(recipeOutput, BlockInit.STARLIGHT_MANGROVE_FENCE_GATE.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addPlanks(recipeOutput, BlockInit.STARLIGHT_MANGROVE_PLANKS.get(), ESTags.Items.STARLIGHT_MANGROVE_LOGS);
        addWood(recipeOutput, BlockInit.STARLIGHT_MANGROVE_WOOD.get(), BlockInit.STARLIGHT_MANGROVE_LOG.get());
        addStrippedWood(recipeOutput, BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get(), BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
        addPressurePlate(recipeOutput, BlockInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addSlab(recipeOutput, BlockInit.STARLIGHT_MANGROVE_SLAB.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addStairs(recipeOutput, BlockInit.STARLIGHT_MANGROVE_STAIRS.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addTrapdoor(recipeOutput, BlockInit.STARLIGHT_MANGROVE_TRAPDOOR.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addSign(recipeOutput, ItemInit.STARLIGHT_MANGROVE_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        hangingSign(recipeOutput, ItemInit.STARLIGHT_MANGROVE_HANGING_SIGN.get(), BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
        addBoat(recipeOutput, ItemInit.STARLIGHT_MANGROVE_BOAT.get(), ItemInit.STARLIGHT_MANGROVE_CHEST_BOAT.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
    }

    private void addStoneRecipes(RecipeOutput recipeOutput) {
        addSmelt(recipeOutput, 200, BlockInit.COBBLED_GRIMSTONE.get(), BlockInit.GRIMSTONE.get(), BlockInit.COBBLED_GRIMSTONE.get());
        addStoneCompress(recipeOutput, BlockInit.POLISHED_GRIMSTONE.get(), BlockInit.GRIMSTONE.get());
        addStoneCompress(recipeOutput, BlockInit.GRIMSTONE_BRICKS.get(), BlockInit.POLISHED_GRIMSTONE.get());
        chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_GRIMSTONE.get(), BlockInit.GRIMSTONE_BRICK_SLAB.get());
        addStoneCompress(recipeOutput, BlockInit.GRIMSTONE_TILES.get(), BlockInit.GRIMSTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_GRIMSTONE.get(), BlockInit.COBBLED_GRIMSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_GRIMSTONE.get(), BlockInit.GRIMSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_GRIMSTONE.get(), BlockInit.GRIMSTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_BRICKS.get(), BlockInit.POLISHED_GRIMSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_TILES.get(), BlockInit.GRIMSTONE_BRICKS.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.COBBLED_GRIMSTONE_WALL.get(), BlockInit.COBBLED_GRIMSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.COBBLED_GRIMSTONE_WALL.get(), BlockInit.COBBLED_GRIMSTONE.get());
        addStairs(recipeOutput, BlockInit.COBBLED_GRIMSTONE_STAIRS.get(), BlockInit.COBBLED_GRIMSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.COBBLED_GRIMSTONE_STAIRS.get(), BlockInit.COBBLED_GRIMSTONE.get());
        addSlab(recipeOutput, BlockInit.COBBLED_GRIMSTONE_SLAB.get(), BlockInit.COBBLED_GRIMSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.COBBLED_GRIMSTONE_SLAB.get(), BlockInit.COBBLED_GRIMSTONE.get(), 2);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_BRICK_WALL.get(), BlockInit.GRIMSTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_BRICK_WALL.get(), BlockInit.POLISHED_GRIMSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_BRICK_WALL.get(), BlockInit.GRIMSTONE_BRICKS.get());
        addStairs(recipeOutput, BlockInit.GRIMSTONE_BRICK_STAIRS.get(), BlockInit.GRIMSTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_BRICK_STAIRS.get(), BlockInit.POLISHED_GRIMSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_BRICK_STAIRS.get(), BlockInit.GRIMSTONE_BRICKS.get());
        addSlab(recipeOutput, BlockInit.GRIMSTONE_BRICK_SLAB.get(), BlockInit.GRIMSTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_BRICK_SLAB.get(), BlockInit.POLISHED_GRIMSTONE.get(), 2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_BRICK_SLAB.get(), BlockInit.GRIMSTONE_BRICKS.get(), 2);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_GRIMSTONE_WALL.get(), BlockInit.POLISHED_GRIMSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_GRIMSTONE_WALL.get(), BlockInit.POLISHED_GRIMSTONE.get());
        addStairs(recipeOutput, BlockInit.POLISHED_GRIMSTONE_STAIRS.get(), BlockInit.POLISHED_GRIMSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_GRIMSTONE_STAIRS.get(), BlockInit.POLISHED_GRIMSTONE.get());
        addSlab(recipeOutput, BlockInit.POLISHED_GRIMSTONE_SLAB.get(), BlockInit.POLISHED_GRIMSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_GRIMSTONE_SLAB.get(), BlockInit.POLISHED_GRIMSTONE.get(), 2);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_TILE_WALL.get(), BlockInit.GRIMSTONE_TILES.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_TILE_WALL.get(), BlockInit.GRIMSTONE_TILES.get());
        addStairs(recipeOutput, BlockInit.GRIMSTONE_TILE_STAIRS.get(), BlockInit.GRIMSTONE_TILES.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_TILE_STAIRS.get(), BlockInit.GRIMSTONE_TILES.get());
        addSlab(recipeOutput, BlockInit.GRIMSTONE_TILE_SLAB.get(), BlockInit.GRIMSTONE_TILES.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.GRIMSTONE_TILE_SLAB.get(), BlockInit.GRIMSTONE_TILES.get(), 2);
        
        addSmelt(recipeOutput, 200, BlockInit.COBBLED_VOIDSTONE.get(), BlockInit.VOIDSTONE.get(), BlockInit.COBBLED_VOIDSTONE.get());
        addStoneCompress(recipeOutput, BlockInit.POLISHED_VOIDSTONE.get(), BlockInit.VOIDSTONE.get());
        addStoneCompress(recipeOutput, BlockInit.VOIDSTONE_BRICKS.get(), BlockInit.POLISHED_VOIDSTONE.get());
        chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_VOIDSTONE.get(), BlockInit.VOIDSTONE_BRICK_SLAB.get());
        addStoneCompress(recipeOutput, BlockInit.VOIDSTONE_TILES.get(), BlockInit.VOIDSTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_VOIDSTONE.get(), BlockInit.COBBLED_VOIDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_VOIDSTONE.get(), BlockInit.VOIDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_VOIDSTONE.get(), BlockInit.VOIDSTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_BRICKS.get(), BlockInit.POLISHED_VOIDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_TILES.get(), BlockInit.VOIDSTONE_BRICKS.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.COBBLED_VOIDSTONE_WALL.get(), BlockInit.COBBLED_VOIDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.COBBLED_VOIDSTONE_WALL.get(), BlockInit.COBBLED_VOIDSTONE.get());
        addStairs(recipeOutput, BlockInit.COBBLED_VOIDSTONE_STAIRS.get(), BlockInit.COBBLED_VOIDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.COBBLED_VOIDSTONE_STAIRS.get(), BlockInit.COBBLED_VOIDSTONE.get());
        addSlab(recipeOutput, BlockInit.COBBLED_VOIDSTONE_SLAB.get(), BlockInit.COBBLED_VOIDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.COBBLED_VOIDSTONE_SLAB.get(), BlockInit.COBBLED_VOIDSTONE.get(), 2);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_BRICK_WALL.get(), BlockInit.VOIDSTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_BRICK_WALL.get(), BlockInit.POLISHED_VOIDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_BRICK_WALL.get(), BlockInit.VOIDSTONE_BRICKS.get());
        addStairs(recipeOutput, BlockInit.VOIDSTONE_BRICK_STAIRS.get(), BlockInit.VOIDSTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_BRICK_STAIRS.get(), BlockInit.POLISHED_VOIDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_BRICK_STAIRS.get(), BlockInit.VOIDSTONE_BRICKS.get());
        addSlab(recipeOutput, BlockInit.VOIDSTONE_BRICK_SLAB.get(), BlockInit.VOIDSTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_BRICK_SLAB.get(), BlockInit.POLISHED_VOIDSTONE.get(), 2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_BRICK_SLAB.get(), BlockInit.VOIDSTONE_BRICKS.get(), 2);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_VOIDSTONE_WALL.get(), BlockInit.POLISHED_VOIDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_VOIDSTONE_WALL.get(), BlockInit.POLISHED_VOIDSTONE.get());
        addStairs(recipeOutput, BlockInit.POLISHED_VOIDSTONE_STAIRS.get(), BlockInit.POLISHED_VOIDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_VOIDSTONE_STAIRS.get(), BlockInit.POLISHED_VOIDSTONE.get());
        addSlab(recipeOutput, BlockInit.POLISHED_VOIDSTONE_SLAB.get(), BlockInit.POLISHED_VOIDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_VOIDSTONE_SLAB.get(), BlockInit.POLISHED_VOIDSTONE.get(), 2);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_TILE_WALL.get(), BlockInit.VOIDSTONE_TILES.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_TILE_WALL.get(), BlockInit.VOIDSTONE_TILES.get());
        addStairs(recipeOutput, BlockInit.VOIDSTONE_TILE_STAIRS.get(), BlockInit.VOIDSTONE_TILES.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_TILE_STAIRS.get(), BlockInit.VOIDSTONE_TILES.get());
        addSlab(recipeOutput, BlockInit.VOIDSTONE_TILE_SLAB.get(), BlockInit.VOIDSTONE_TILES.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.VOIDSTONE_TILE_SLAB.get(), BlockInit.VOIDSTONE_TILES.get(), 2);

        addStoneCompress(recipeOutput, BlockInit.POLISHED_ABYSSLATE.get(), BlockInit.ABYSSLATE.get());
        addStoneCompress(recipeOutput, BlockInit.POLISHED_ABYSSLATE_BRICKS.get(), BlockInit.POLISHED_ABYSSLATE.get());
        chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_POLISHED_ABYSSLATE.get(), BlockInit.POLISHED_ABYSSLATE_BRICK_SLAB.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE.get(), BlockInit.ABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_POLISHED_ABYSSLATE.get(), BlockInit.POLISHED_ABYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_BRICKS.get(), BlockInit.POLISHED_ABYSSLATE.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_BRICK_WALL.get(), BlockInit.POLISHED_ABYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_BRICK_WALL.get(), BlockInit.POLISHED_ABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_BRICK_WALL.get(), BlockInit.POLISHED_ABYSSLATE_BRICKS.get());
        addStairs(recipeOutput, BlockInit.POLISHED_ABYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_ABYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_ABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_ABYSSLATE_BRICKS.get());
        addSlab(recipeOutput, BlockInit.POLISHED_ABYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_ABYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_ABYSSLATE.get(), 2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_ABYSSLATE_BRICKS.get(), 2);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_WALL.get(), BlockInit.POLISHED_ABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_WALL.get(), BlockInit.POLISHED_ABYSSLATE.get());
        addStairs(recipeOutput, BlockInit.POLISHED_ABYSSLATE_STAIRS.get(), BlockInit.POLISHED_ABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_STAIRS.get(), BlockInit.POLISHED_ABYSSLATE.get());
        addSlab(recipeOutput, BlockInit.POLISHED_ABYSSLATE_SLAB.get(), BlockInit.POLISHED_ABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_ABYSSLATE_SLAB.get(), BlockInit.POLISHED_ABYSSLATE.get(), 2);

        addStoneCompress(recipeOutput, BlockInit.POLISHED_THERMABYSSLATE.get(), BlockInit.THERMABYSSLATE.get());
        addStoneCompress(recipeOutput, BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get(), BlockInit.POLISHED_THERMABYSSLATE.get());
        chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_POLISHED_THERMABYSSLATE.get(), BlockInit.POLISHED_THERMABYSSLATE_BRICK_SLAB.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE.get(), BlockInit.THERMABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_POLISHED_THERMABYSSLATE.get(), BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get(), BlockInit.POLISHED_THERMABYSSLATE.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_BRICK_WALL.get(), BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_BRICK_WALL.get(), BlockInit.POLISHED_THERMABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_BRICK_WALL.get(), BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get());
        addStairs(recipeOutput, BlockInit.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_THERMABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get());
        addSlab(recipeOutput, BlockInit.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_THERMABYSSLATE.get(), 2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_THERMABYSSLATE_BRICKS.get(), 2);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_WALL.get(), BlockInit.POLISHED_THERMABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_WALL.get(), BlockInit.POLISHED_THERMABYSSLATE.get());
        addStairs(recipeOutput, BlockInit.POLISHED_THERMABYSSLATE_STAIRS.get(), BlockInit.POLISHED_THERMABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_STAIRS.get(), BlockInit.POLISHED_THERMABYSSLATE.get());
        addSlab(recipeOutput, BlockInit.POLISHED_THERMABYSSLATE_SLAB.get(), BlockInit.POLISHED_THERMABYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_THERMABYSSLATE_SLAB.get(), BlockInit.POLISHED_THERMABYSSLATE.get(), 2);

        addStoneCompress(recipeOutput, BlockInit.POLISHED_CRYOBYSSLATE.get(), BlockInit.CRYOBYSSLATE.get());
        addStoneCompress(recipeOutput, BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get(), BlockInit.POLISHED_CRYOBYSSLATE.get());
        chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_POLISHED_CRYOBYSSLATE.get(), BlockInit.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE.get(), BlockInit.CRYOBYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_POLISHED_CRYOBYSSLATE.get(), BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get(), BlockInit.POLISHED_CRYOBYSSLATE.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(), BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(), BlockInit.POLISHED_CRYOBYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(), BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get());
        addStairs(recipeOutput, BlockInit.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_CRYOBYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(), BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get());
        addSlab(recipeOutput, BlockInit.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_CRYOBYSSLATE.get(), 2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), BlockInit.POLISHED_CRYOBYSSLATE_BRICKS.get(), 2);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_WALL.get(), BlockInit.POLISHED_CRYOBYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_WALL.get(), BlockInit.POLISHED_CRYOBYSSLATE.get());
        addStairs(recipeOutput, BlockInit.POLISHED_CRYOBYSSLATE_STAIRS.get(), BlockInit.POLISHED_CRYOBYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_STAIRS.get(), BlockInit.POLISHED_CRYOBYSSLATE.get());
        addSlab(recipeOutput, BlockInit.POLISHED_CRYOBYSSLATE_SLAB.get(), BlockInit.POLISHED_CRYOBYSSLATE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_CRYOBYSSLATE_SLAB.get(), BlockInit.POLISHED_CRYOBYSSLATE.get(), 2);

        addStoneCompress(recipeOutput, BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), BlockInit.DOOMEDEN_BRICKS.get());
        chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get(), BlockInit.DOOMEDEN_BRICK_SLAB.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), BlockInit.DOOMEDEN_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get(), BlockInit.DOOMEDEN_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.DOOMEDEN_BRICKS.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.DOOMEDEN_BRICK_WALL.get(), BlockInit.DOOMEDEN_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.DOOMEDEN_BRICK_WALL.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.DOOMEDEN_BRICK_WALL.get(), BlockInit.DOOMEDEN_BRICKS.get());
        addStairs(recipeOutput, BlockInit.DOOMEDEN_BRICK_STAIRS.get(), BlockInit.DOOMEDEN_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.DOOMEDEN_BRICK_STAIRS.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.DOOMEDEN_BRICK_STAIRS.get(), BlockInit.DOOMEDEN_BRICKS.get());
        addSlab(recipeOutput, BlockInit.DOOMEDEN_BRICK_SLAB.get(), BlockInit.DOOMEDEN_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.DOOMEDEN_BRICK_SLAB.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), 2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.DOOMEDEN_BRICK_SLAB.get(), BlockInit.DOOMEDEN_BRICKS.get(), 2);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_DOOMEDEN_BRICK_WALL.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_DOOMEDEN_BRICK_WALL.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        addStairs(recipeOutput, BlockInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        addSlab(recipeOutput, BlockInit.POLISHED_DOOMEDEN_BRICK_SLAB.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.POLISHED_DOOMEDEN_BRICK_SLAB.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), 2);

        addStoneCompress(recipeOutput, BlockInit.NIGHTSHADE_MUD_BRICKS.get(), BlockInit.PACKED_NIGHTSHADE_MUD.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.NIGHTSHADE_MUD_BRICK_WALL.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.NIGHTSHADE_MUD_BRICK_WALL.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        addStairs(recipeOutput, BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        addSlab(recipeOutput, BlockInit.NIGHTSHADE_MUD_BRICK_SLAB.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.NIGHTSHADE_MUD_BRICK_SLAB.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get(), 2);

        addStoneCompress(recipeOutput, BlockInit.TWILIGHT_SANDSTONE.get(), BlockInit.TWILIGHT_SAND.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.TWILIGHT_SANDSTONE_WALL.get(), BlockInit.TWILIGHT_SANDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.TWILIGHT_SANDSTONE_WALL.get(), BlockInit.TWILIGHT_SANDSTONE.get());
        addStairs(recipeOutput, BlockInit.TWILIGHT_SANDSTONE_STAIRS.get(), BlockInit.TWILIGHT_SANDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.TWILIGHT_SANDSTONE_STAIRS.get(), BlockInit.TWILIGHT_SANDSTONE.get());
        addSlab(recipeOutput, BlockInit.TWILIGHT_SANDSTONE_SLAB.get(), BlockInit.TWILIGHT_SANDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.TWILIGHT_SANDSTONE_SLAB.get(), BlockInit.TWILIGHT_SANDSTONE.get(), 2);
        addStoneCompress(recipeOutput, BlockInit.CUT_TWILIGHT_SANDSTONE.get(), BlockInit.TWILIGHT_SANDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CUT_TWILIGHT_SANDSTONE.get(), BlockInit.TWILIGHT_SANDSTONE.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CUT_TWILIGHT_SANDSTONE_WALL.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CUT_TWILIGHT_SANDSTONE_WALL.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get());
        addStairs(recipeOutput, BlockInit.CUT_TWILIGHT_SANDSTONE_STAIRS.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CUT_TWILIGHT_SANDSTONE_STAIRS.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get());
        addSlab(recipeOutput, BlockInit.CUT_TWILIGHT_SANDSTONE_SLAB.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CUT_TWILIGHT_SANDSTONE_SLAB.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get(), 2);
        chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_TWILIGHT_SANDSTONE.get(), BlockInit.CUT_TWILIGHT_SANDSTONE_SLAB.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, BlockInit.CHISELED_TWILIGHT_SANDSTONE.get(), BlockInit.CUT_TWILIGHT_SANDSTONE.get());
    }

    private void addAetherSentRecipes(RecipeOutput recipeOutput) {
        nineBlockStorageRecipesRecipesWithCustomUnpacking(recipeOutput, RecipeCategory.MISC, ItemInit.AETHERSENT_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ItemInit.AETHERSENT_BLOCK.get(), "aethersent_ingot_from_iron_block", "aethersent_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_HOOD.get())
                .pattern("###")
                .pattern("A A")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_CAPE.get())
                .pattern("A A")
                .pattern("#A#")
                .pattern("###")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_BOTTOMS.get())
                .pattern("###")
                .pattern("A A")
                .pattern("# #")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_BOOTS.get())
                .pattern("A A")
                .pattern("# #")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.STARFALL_LONGBOW.get())
                .pattern(" AS")
                .pattern("A S")
                .pattern(" AS")
                .define('S', Items.STRING)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(recipeOutput);
        addSword(recipeOutput, ItemInit.RAGE_OF_STARS.get(), ItemInit.AETHERSENT_INGOT.get(), Items.STICK);
    }

    private void addSwampSilverRecipes(RecipeOutput recipeOutput) {
        nineBlockStorageRecipesRecipesWithCustomUnpacking(recipeOutput, RecipeCategory.MISC, ItemInit.SWAMP_SILVER_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ItemInit.SWAMP_SILVER_BLOCK.get(), "swamp_silver_ingot_from_iron_block", "swamp_silver_ingot");
        nineBlockStorageRecipesWithCustomPacking(recipeOutput, RecipeCategory.MISC, ItemInit.SWAMP_SILVER_NUGGET.get(), RecipeCategory.MISC, ItemInit.SWAMP_SILVER_INGOT.get(), "swamp_silver_ingot_from_nuggets", "swamp_silver_ingot");
        addAxe(recipeOutput, ItemInit.SWAMP_SILVER_AXE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addPickaxe(recipeOutput, ItemInit.SWAMP_SILVER_PICKAXE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addHoe(recipeOutput, ItemInit.SWAMP_SILVER_SICKLE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addSword(recipeOutput, ItemInit.SWAMP_SILVER_SWORD.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addHelmet(recipeOutput, ItemInit.SWAMP_SILVER_HELMET.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addChestplate(recipeOutput, ItemInit.SWAMP_SILVER_CHESTPLATE.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addLeggings(recipeOutput, ItemInit.SWAMP_SILVER_LEGGINGS.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addBoots(recipeOutput, ItemInit.SWAMP_SILVER_BOOTS.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addSmelt(recipeOutput, 200, ItemInit.SWAMP_SILVER_ORE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_ORE.get());
        addBlast(recipeOutput, 100, ItemInit.SWAMP_SILVER_ORE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_ORE.get());
        addSmelt(recipeOutput, 200, ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_NUGGET.get(), ItemInit.SWAMP_SILVER_PICKAXE.get(), ItemInit.SWAMP_SILVER_AXE.get(), ItemInit.SWAMP_SILVER_SICKLE.get(), ItemInit.SWAMP_SILVER_SWORD.get(), ItemInit.SWAMP_SILVER_HELMET.get(), ItemInit.SWAMP_SILVER_CHESTPLATE.get(), ItemInit.SWAMP_SILVER_LEGGINGS.get(), ItemInit.SWAMP_SILVER_BOOTS.get());
        addBlast(recipeOutput, 100, ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_NUGGET.get(), ItemInit.SWAMP_SILVER_PICKAXE.get(), ItemInit.SWAMP_SILVER_AXE.get(), ItemInit.SWAMP_SILVER_SICKLE.get(), ItemInit.SWAMP_SILVER_SWORD.get(), ItemInit.SWAMP_SILVER_HELMET.get(), ItemInit.SWAMP_SILVER_CHESTPLATE.get(), ItemInit.SWAMP_SILVER_LEGGINGS.get(), ItemInit.SWAMP_SILVER_BOOTS.get());
    }

    private void addThermalSpringstoneRecipes(RecipeOutput recipeOutput) {
        addAxe(recipeOutput, ItemInit.THERMAL_SPRINGSTONE_AXE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addPickaxe(recipeOutput, ItemInit.THERMAL_SPRINGSTONE_PICKAXE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addHoe(recipeOutput, ItemInit.THERMAL_SPRINGSTONE_SCYTHE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addSword(recipeOutput, ItemInit.THERMAL_SPRINGSTONE_SWORD.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addHammer(recipeOutput, ItemInit.THERMAL_SPRINGSTONE_HAMMER.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addHelmet(recipeOutput, ItemInit.THERMAL_SPRINGSTONE_HELMET.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addChestplate(recipeOutput, ItemInit.THERMAL_SPRINGSTONE_CHESTPLATE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addLeggings(recipeOutput, ItemInit.THERMAL_SPRINGSTONE_LEGGINGS.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addBoots(recipeOutput, ItemInit.THERMAL_SPRINGSTONE_BOOTS.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addSmelt(recipeOutput, 200, ItemInit.THERMAL_SPRINGSTONE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), ItemInit.THERMAL_SPRINGSTONE.get());
        addBlast(recipeOutput, 100, ItemInit.THERMAL_SPRINGSTONE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), ItemInit.THERMAL_SPRINGSTONE.get());
    }

    // misc
    protected final void addSmelt(RecipeOutput recipeOutput, int time, ItemLike criteria, ItemLike output, ItemLike... input) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, output, 1.0f, time).unlockedBy("has_item", has(criteria)).save(recipeOutput, getModLocation(name(output) + "_smelting"));
    }
    
    protected final void addBlast(RecipeOutput recipeOutput, int time, ItemLike criteria, ItemLike output, ItemLike... input) {
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(input), RecipeCategory.MISC, output, 1.0f, time).unlockedBy("has_item", has(criteria)).save(recipeOutput, getModLocation(name(output) + "_blasting"));
    }

    protected final void addSingleConversion(RecipeOutput recipeOutput, Item from, Item to) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, to)
                .requires(from)
                .unlockedBy("has_item", has(from))
                .save(recipeOutput, getModLocation("shapeless/" + name(to) + "_from_" + name(from)));
    }

    protected final void addShapeless(RecipeOutput recipeOutput, ItemLike criteria, ItemLike output, int num, ItemLike... ingredients) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, num);
        for (ItemLike item : ingredients) {
            builder.requires(item);
        }
        builder.unlockedBy("has_item", has(criteria)).save(recipeOutput, getModLocation("shapeless/" + name(output)));
    }

    // combat & tools
    protected final void addHelmet(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("###")
                .pattern("# #")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addChestplate(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addLeggings(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addBoots(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("# #")
                .pattern("# #")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addHoe(RecipeOutput recipeOutput, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("##")
                .pattern(" H")
                .pattern(" H")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addPickaxe(RecipeOutput recipeOutput, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("###")
                .pattern(" H ")
                .pattern(" H ")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addSword(RecipeOutput recipeOutput, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("#")
                .pattern("#")
                .pattern("H")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addAxe(RecipeOutput recipeOutput, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("##")
                .pattern("#H")
                .pattern(" H")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addHammer(RecipeOutput recipeOutput, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("###")
                .pattern("#H#")
                .pattern(" H ")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    // building blocks and wooden stuff
    protected final void addPlanks(RecipeOutput recipeOutput, Block output, TagKey<Item> input) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .requires(input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addWood(RecipeOutput recipeOutput, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addStrippedWood(RecipeOutput recipeOutput, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addButton(RecipeOutput recipeOutput, Block output, Block input) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, output)
                .requires(input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addDoor(RecipeOutput recipeOutput, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output, 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addFence(RecipeOutput recipeOutput, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("#S#")
                .pattern("#S#")
                .define('#', input)
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addFenceGate(RecipeOutput recipeOutput, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output)
                .pattern("S#S")
                .pattern("S#S")
                .define('#', input)
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addPressurePlate(RecipeOutput recipeOutput, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output)
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addSlab(RecipeOutput recipeOutput, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addStairs(RecipeOutput recipeOutput, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 2)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addTrapdoor(RecipeOutput recipeOutput, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output, 2)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addSign(RecipeOutput recipeOutput, ItemLike output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 3)
                .pattern("###")
                .pattern("###")
                .pattern(" S ")
                .define('#', input)
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final void addBoat(RecipeOutput recipeOutput, Item boat, Item chestBoat, Block planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, boat)
                .pattern("P P")
                .pattern("PPP")
                .define('P', planks)
                .group("boat")
                .unlockedBy("in_water", insideOf(Blocks.WATER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, chestBoat)
                .requires(boat)
                .group("chest_boat")
                .unlockedBy("has_boat", has(ItemTags.BOATS))
                .save(recipeOutput);
    }

    // stone
    protected final void addStoneCompress(RecipeOutput recipeOutput, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(recipeOutput);
    }

    protected final String name(ItemLike item) {
        return key(item).getPath();
    }

    protected final ResourceLocation key(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem());
    }

    protected final ResourceLocation getModLocation(String id) {
        return new ResourceLocation(EternalStarlight.MOD_ID, id);
    }
}
