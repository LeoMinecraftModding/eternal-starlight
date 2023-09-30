package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.util.ESTags;
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
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ESRecipeProvider extends RecipeProvider {
    public ESRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        addWoodRecipes(consumer);
        addStoneRecipes(consumer);
        addAetherSentRecipes(consumer);
        addSwampSilverRecipes(consumer);
        addThermalSpringstoneRecipes(consumer);

        // smelt
        addSmelt(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, "golem_steel_ingot", 200, ItemInit.OXIDIZED_GOLEM_STEEL_INGOT, ItemInit.GOLEM_STEEL_INGOT, ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get());
        addSmelt(consumer, "blasting", RecipeSerializer.BLASTING_RECIPE, "golem_steel_ingot", 100, ItemInit.OXIDIZED_GOLEM_STEEL_INGOT, ItemInit.GOLEM_STEEL_INGOT, ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get());

        // misc
        addShapeless(consumer, "muddy_starlight_mangrove_roots", ItemInit.STARLIGHT_MANGROVE_ROOTS, ItemInit.MUDDY_STARLIGHT_MANGROVE_ROOTS, 1, ItemInit.STARLIGHT_MANGROVE_ROOTS.get(), ItemInit.NIGHTSHADE_MUD.get());
        addShapeless(consumer, "packed_nightshade_mud", ItemInit.NIGHTSHADE_MUD, ItemInit.PACKED_NIGHTSHADE_MUD, 1, ItemInit.NIGHTSHADE_MUD.get(), ItemInit.LUNAR_BERRIES.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.SEEKING_EYE.get())
                .pattern("FFF")
                .pattern("FPF")
                .pattern("FFF")
                .define('P', Items.ENDER_PEARL)
                .define('F', ItemInit.STARLIGHT_FLOWER.get())
                .unlockedBy("has_item", has(ItemInit.STARLIGHT_FLOWER.get()))
                .save(consumer, getEquipmentLocation("seeking_eye"));
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
                .save(consumer, getEquipmentLocation("crystal_crossbow"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.MOONRING_GREATSWORD.get())
                .pattern("TTT")
                .pattern("TGT")
                .pattern("SSS")
                .define('S', Items.STICK)
                .define('G', ItemInit.GOLEM_STEEL_INGOT.get())
                .define('T', ItemInit.TENACIOUS_PETAL.get())
                .unlockedBy("has_item", has(ItemInit.TENACIOUS_PETAL.get()))
                .save(consumer, getEquipmentLocation("moonring_greatsword"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.PETAL_SCYTHE.get())
                .pattern("GTT")
                .pattern("GS ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('G', ItemInit.GOLEM_STEEL_INGOT.get())
                .define('T', ItemInit.TENACIOUS_PETAL.get())
                .unlockedBy("has_item", has(ItemInit.TENACIOUS_PETAL.get()))
                .save(consumer, getEquipmentLocation("petal_scythe"));
    }

    private void addWoodRecipes(Consumer<FinishedRecipe> consumer) {
        addButton(consumer, "lunar", BlockInit.LUNAR_BUTTON, BlockInit.LUNAR_PLANKS);
        addDoor(consumer, "lunar", BlockInit.LUNAR_DOOR, BlockInit.LUNAR_PLANKS);
        addFence(consumer, "lunar", BlockInit.LUNAR_FENCE, BlockInit.LUNAR_PLANKS);
        addFenceGate(consumer, "lunar", BlockInit.LUNAR_FENCE_GATE, BlockInit.LUNAR_PLANKS);
        addPlanks(consumer, "lunar", BlockInit.LUNAR_PLANKS, ESTags.Items.LUNAR_LOGS);
        addWood(consumer, "lunar", BlockInit.LUNAR_WOOD, BlockInit.LUNAR_LOG);
        addStrippedWood(consumer, "lunar", BlockInit.STRIPPED_LUNAR_WOOD, BlockInit.STRIPPED_LUNAR_LOG);
        addPressurePlate(consumer, "lunar", BlockInit.LUNAR_PRESSURE_PLATE, BlockInit.LUNAR_PLANKS);
        addSlab(consumer, "lunar", BlockInit.LUNAR_SLAB, BlockInit.LUNAR_PLANKS);
        addStairs(consumer, "lunar", BlockInit.LUNAR_STAIRS, BlockInit.LUNAR_PLANKS);
        addTrapdoor(consumer, "lunar", BlockInit.LUNAR_TRAPDOOR, BlockInit.LUNAR_PLANKS);
        addSign(consumer, "lunar", ItemInit.LUNAR_SIGN, BlockInit.LUNAR_PLANKS);
        addHangingSign(consumer, "lunar", ItemInit.LUNAR_HANGING_SIGN, BlockInit.STRIPPED_LUNAR_LOG);
        addBoat(consumer, ItemInit.LUNAR_BOAT, ItemInit.LUNAR_CHEST_BOAT, BlockInit.LUNAR_PLANKS);

        addButton(consumer, "northland", BlockInit.NORTHLAND_BUTTON, BlockInit.NORTHLAND_PLANKS);
        addDoor(consumer, "northland", BlockInit.NORTHLAND_DOOR, BlockInit.NORTHLAND_PLANKS);
        addFence(consumer, "northland", BlockInit.NORTHLAND_FENCE, BlockInit.NORTHLAND_PLANKS);
        addFenceGate(consumer, "northland", BlockInit.NORTHLAND_FENCE_GATE, BlockInit.NORTHLAND_PLANKS);
        addPlanks(consumer, "northland", BlockInit.NORTHLAND_PLANKS, ESTags.Items.NORTHLAND_LOGS);
        addWood(consumer, "northland", BlockInit.NORTHLAND_WOOD, BlockInit.NORTHLAND_LOG);
        addStrippedWood(consumer, "northland", BlockInit.STRIPPED_NORTHLAND_WOOD, BlockInit.STRIPPED_NORTHLAND_LOG);
        addPressurePlate(consumer, "northland", BlockInit.NORTHLAND_PRESSURE_PLATE, BlockInit.NORTHLAND_PLANKS);
        addSlab(consumer, "northland", BlockInit.NORTHLAND_SLAB, BlockInit.NORTHLAND_PLANKS);
        addStairs(consumer, "northland", BlockInit.NORTHLAND_STAIRS, BlockInit.NORTHLAND_PLANKS);
        addTrapdoor(consumer, "northland", BlockInit.NORTHLAND_TRAPDOOR, BlockInit.NORTHLAND_PLANKS);
        addSign(consumer, "northland", ItemInit.NORTHLAND_SIGN, BlockInit.NORTHLAND_PLANKS);
        addHangingSign(consumer, "northland", ItemInit.NORTHLAND_HANGING_SIGN, BlockInit.STRIPPED_NORTHLAND_LOG);
        addBoat(consumer, ItemInit.NORTHLAND_BOAT, ItemInit.NORTHLAND_CHEST_BOAT, BlockInit.NORTHLAND_PLANKS);

        addButton(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_BUTTON, BlockInit.STARLIGHT_MANGROVE_PLANKS);
        addDoor(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_DOOR, BlockInit.STARLIGHT_MANGROVE_PLANKS);
        addFence(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_FENCE, BlockInit.STARLIGHT_MANGROVE_PLANKS);
        addFenceGate(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_FENCE_GATE, BlockInit.STARLIGHT_MANGROVE_PLANKS);
        addPlanks(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_PLANKS, ESTags.Items.STARLIGHT_MANGROVE_LOGS);
        addWood(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_WOOD, BlockInit.STARLIGHT_MANGROVE_LOG);
        addStrippedWood(consumer, "starlight_mangrove", BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD, BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG);
        addPressurePlate(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_PRESSURE_PLATE, BlockInit.STARLIGHT_MANGROVE_PLANKS);
        addSlab(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_SLAB, BlockInit.STARLIGHT_MANGROVE_PLANKS);
        addStairs(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_STAIRS, BlockInit.STARLIGHT_MANGROVE_PLANKS);
        addTrapdoor(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_TRAPDOOR, BlockInit.STARLIGHT_MANGROVE_PLANKS);
        addSign(consumer, "starlight_mangrove", ItemInit.STARLIGHT_MANGROVE_SIGN, BlockInit.STARLIGHT_MANGROVE_PLANKS);
        addHangingSign(consumer, "starlight_mangrove", ItemInit.STARLIGHT_MANGROVE_HANGING_SIGN, BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG);
        addBoat(consumer, ItemInit.STARLIGHT_MANGROVE_BOAT, ItemInit.STARLIGHT_MANGROVE_CHEST_BOAT, BlockInit.STARLIGHT_MANGROVE_PLANKS);
    }

    private void addStoneRecipes(Consumer<FinishedRecipe> consumer) {
        addPolished(consumer, "grimstone", BlockInit.POLISHED_GRIMSTONE, BlockInit.GRIMSTONE);
        addBricks(consumer, "grimstone", BlockInit.GRIMSTONE_BRICKS, BlockInit.POLISHED_GRIMSTONE);
        addChiseled(consumer, "grimstone", BlockInit.CHISELED_GRIMSTONE, BlockInit.GRIMSTONE_BRICK_SLAB);
        addStoneCuttingChiseled(consumer, "grimstone", BlockInit.GRIMSTONE_BRICKS, ItemInit.CHISELED_GRIMSTONE.get());
        addStoneCuttingBricks(consumer, "grimstone", BlockInit.POLISHED_GRIMSTONE, ItemInit.GRIMSTONE_BRICKS.get());
        addWall(consumer, "grimstone", BlockInit.GRIMSTONE_BRICK_WALL, BlockInit.GRIMSTONE_BRICKS);
        addStoneCuttingWall(consumer, "grimstone", BlockInit.POLISHED_GRIMSTONE, ItemInit.GRIMSTONE_BRICK_WALL.get());
        addStoneCuttingWall(consumer, "grimstone", BlockInit.GRIMSTONE_BRICKS, ItemInit.GRIMSTONE_BRICK_WALL.get());
        addStairs(consumer, "grimstone", BlockInit.GRIMSTONE_BRICK_STAIRS, BlockInit.GRIMSTONE_BRICKS);
        addStoneCuttingStairs(consumer, "grimstone", BlockInit.POLISHED_GRIMSTONE, ItemInit.GRIMSTONE_BRICK_STAIRS.get());
        addStoneCuttingStairs(consumer, "grimstone", BlockInit.GRIMSTONE_BRICKS, ItemInit.GRIMSTONE_BRICK_STAIRS.get());
        addSlab(consumer, "grimstone", BlockInit.GRIMSTONE_BRICK_SLAB, BlockInit.GRIMSTONE_BRICKS);
        addStoneCuttingSlab(consumer, "grimstone", BlockInit.POLISHED_GRIMSTONE, ItemInit.GRIMSTONE_BRICK_SLAB.get());
        addStoneCuttingSlab(consumer, "grimstone", BlockInit.GRIMSTONE_BRICKS, ItemInit.GRIMSTONE_BRICK_SLAB.get());
        addWall(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE_WALL, BlockInit.POLISHED_GRIMSTONE);
        addStoneCuttingWall(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE, ItemInit.POLISHED_GRIMSTONE_WALL.get());
        addStairs(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE_STAIRS, BlockInit.POLISHED_GRIMSTONE);
        addStoneCuttingStairs(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE, ItemInit.POLISHED_GRIMSTONE_STAIRS.get());
        addSlab(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE_SLAB, BlockInit.POLISHED_GRIMSTONE);
        addStoneCuttingSlab(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE, ItemInit.POLISHED_GRIMSTONE_SLAB.get());

        addPolished(consumer, "voidstone", BlockInit.POLISHED_VOIDSTONE, BlockInit.VOIDSTONE);
        addBricks(consumer, "voidstone", BlockInit.VOIDSTONE_BRICKS, BlockInit.POLISHED_VOIDSTONE);
        addChiseled(consumer, "voidstone", BlockInit.CHISELED_VOIDSTONE, BlockInit.VOIDSTONE_BRICK_SLAB);
        addStoneCuttingChiseled(consumer, "voidstone", BlockInit.VOIDSTONE_BRICKS, ItemInit.CHISELED_VOIDSTONE.get());
        addStoneCuttingBricks(consumer, "voidstone", BlockInit.POLISHED_VOIDSTONE, ItemInit.VOIDSTONE_BRICKS.get());
        addWall(consumer, "voidstone", BlockInit.VOIDSTONE_BRICK_WALL, BlockInit.VOIDSTONE_BRICKS);
        addStoneCuttingWall(consumer, "voidstone", BlockInit.POLISHED_VOIDSTONE, ItemInit.VOIDSTONE_BRICK_WALL.get());
        addStoneCuttingWall(consumer, "voidstone", BlockInit.VOIDSTONE_BRICKS, ItemInit.VOIDSTONE_BRICK_WALL.get());
        addStairs(consumer, "voidstone", BlockInit.VOIDSTONE_BRICK_STAIRS, BlockInit.VOIDSTONE_BRICKS);
        addStoneCuttingStairs(consumer, "voidstone", BlockInit.POLISHED_VOIDSTONE, ItemInit.VOIDSTONE_BRICK_STAIRS.get());
        addStoneCuttingStairs(consumer, "voidstone", BlockInit.VOIDSTONE_BRICKS, ItemInit.VOIDSTONE_BRICK_STAIRS.get());
        addSlab(consumer, "voidstone", BlockInit.VOIDSTONE_BRICK_SLAB, BlockInit.VOIDSTONE_BRICKS);
        addStoneCuttingSlab(consumer, "voidstone", BlockInit.POLISHED_VOIDSTONE, ItemInit.VOIDSTONE_BRICK_SLAB.get());
        addStoneCuttingSlab(consumer, "voidstone", BlockInit.VOIDSTONE_BRICKS, ItemInit.VOIDSTONE_BRICK_SLAB.get());
        addWall(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE_WALL, BlockInit.POLISHED_VOIDSTONE);
        addStoneCuttingWall(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE, ItemInit.POLISHED_VOIDSTONE_WALL.get());
        addStairs(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE_STAIRS, BlockInit.POLISHED_VOIDSTONE);
        addStoneCuttingStairs(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE, ItemInit.POLISHED_VOIDSTONE_STAIRS.get());
        addSlab(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE_SLAB, BlockInit.POLISHED_VOIDSTONE);
        addStoneCuttingSlab(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE, ItemInit.POLISHED_VOIDSTONE_SLAB.get());

        addBricks(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS, BlockInit.PACKED_NIGHTSHADE_MUD);
        addWall(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICK_WALL, BlockInit.NIGHTSHADE_MUD_BRICKS);
        addStoneCuttingWall(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS, ItemInit.NIGHTSHADE_MUD_BRICK_WALL.get());
        addStairs(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS, BlockInit.NIGHTSHADE_MUD_BRICKS);
        addStoneCuttingStairs(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS, ItemInit.NIGHTSHADE_MUD_BRICK_STAIRS.get());
        addSlab(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICK_SLAB, BlockInit.NIGHTSHADE_MUD_BRICKS);
        addStoneCuttingSlab(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS, ItemInit.NIGHTSHADE_MUD_BRICK_SLAB.get());
    }

    private void addAetherSentRecipes(Consumer<FinishedRecipe> consumer) {
        addCompressed(consumer, "aethersent", ItemInit.AETHERSENT_INGOT.get(), BlockInit.AETHERSENT_BLOCK);
        addReverseCompressed(consumer, "aethersent", BlockInit.AETHERSENT_BLOCK.get(), ItemInit.AETHERSENT_INGOT);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_HOOD.get())
                .pattern("###")
                .pattern("A A")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(consumer, getEquipmentLocation("aethersent_hood"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_CAPE.get())
                .pattern("A A")
                .pattern("#A#")
                .pattern("###")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(consumer, getEquipmentLocation("aethersent_cape"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_BOTTOMS.get())
                .pattern("###")
                .pattern("A A")
                .pattern("# #")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(consumer, getEquipmentLocation("aethersent_bottoms"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_BOOTS.get())
                .pattern("A A")
                .pattern("# #")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(consumer, getEquipmentLocation("aethersent_boots"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.STARFALL_LONGBOW.get())
                .pattern(" AS")
                .pattern("A S")
                .pattern(" AS")
                .define('S', Items.STRING)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(consumer, getEquipmentLocation("starfall_longbow"));
        addSword(consumer, "rage_of_stars", ItemInit.RAGE_OF_STARS, ItemInit.AETHERSENT_INGOT, () -> Items.STICK);
    }

    private void addSwampSilverRecipes(Consumer<FinishedRecipe> consumer) {
        addCompressed(consumer, "swamp_silver", ItemInit.SWAMP_SILVER_INGOT.get(), BlockInit.SWAMP_SILVER_BLOCK);
        addReverseCompressed(consumer, "swamp_silver", BlockInit.SWAMP_SILVER_BLOCK.get(), ItemInit.SWAMP_SILVER_INGOT);
        addAxe(consumer, "swamp_silver_axe", ItemInit.SWAMP_SILVER_AXE, ItemInit.SWAMP_SILVER_INGOT, () -> Items.STICK);
        addPickaxe(consumer, "swamp_silver_pickaxe", ItemInit.SWAMP_SILVER_PICKAXE, ItemInit.SWAMP_SILVER_INGOT, () -> Items.STICK);
        addHoe(consumer, "swamp_silver_sickle", ItemInit.SWAMP_SILVER_SICKLE, ItemInit.SWAMP_SILVER_INGOT, () -> Items.STICK);
        addSword(consumer, "swamp_silver_sword", ItemInit.SWAMP_SILVER_SWORD, ItemInit.SWAMP_SILVER_INGOT, () -> Items.STICK);
        addHelmet(consumer, "swamp_silver_helmet", ItemInit.SWAMP_SILVER_HELMET, ItemInit.SWAMP_SILVER_INGOT);
        addChestplate(consumer, "swamp_silver_chestplate", ItemInit.SWAMP_SILVER_CHESTPLATE, ItemInit.SWAMP_SILVER_INGOT);
        addLeggings(consumer, "swamp_silver_leggings", ItemInit.SWAMP_SILVER_LEGGINGS, ItemInit.SWAMP_SILVER_INGOT);
        addBoots(consumer, "swamp_silver_boots", ItemInit.SWAMP_SILVER_BOOTS, ItemInit.SWAMP_SILVER_INGOT);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.SWAMP_SILVER_INGOT.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Ingredient.of(ItemInit.SWAMP_SILVER_NUGGET.get()))
                .unlockedBy("has_item", has(ItemInit.SWAMP_SILVER_NUGGET.get()))
                .save(consumer, getMiscLocation("swamp_silver_ingot_from_nuggets"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.SWAMP_SILVER_NUGGET.get(), 9)
                .requires(ItemInit.SWAMP_SILVER_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.SWAMP_SILVER_INGOT.get()))
                .save(consumer, getMiscLocation("swamp_silver_nuggets_from_ingot"));
        addSmelt(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, "swamp_silver_ingot", 200, ItemInit.SWAMP_SILVER_ORE, ItemInit.SWAMP_SILVER_INGOT, ItemInit.SWAMP_SILVER_ORE.get());
        addSmelt(consumer, "blasting", RecipeSerializer.BLASTING_RECIPE, "swamp_silver_ingot", 100, ItemInit.SWAMP_SILVER_ORE, ItemInit.SWAMP_SILVER_INGOT, ItemInit.SWAMP_SILVER_ORE.get());
        addSmelt(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, "swamp_silver_nugget", 200, ItemInit.SWAMP_SILVER_INGOT, ItemInit.SWAMP_SILVER_INGOT, ItemInit.SWAMP_SILVER_PICKAXE.get(), ItemInit.SWAMP_SILVER_AXE.get(), ItemInit.SWAMP_SILVER_SICKLE.get(), ItemInit.SWAMP_SILVER_SWORD.get(), ItemInit.SWAMP_SILVER_HELMET.get(), ItemInit.SWAMP_SILVER_CHESTPLATE.get(), ItemInit.SWAMP_SILVER_LEGGINGS.get(), ItemInit.SWAMP_SILVER_BOOTS.get());
        addSmelt(consumer, "blasting", RecipeSerializer.BLASTING_RECIPE, "swamp_silver_nugget", 100, ItemInit.SWAMP_SILVER_INGOT, ItemInit.SWAMP_SILVER_INGOT, ItemInit.SWAMP_SILVER_PICKAXE.get(), ItemInit.SWAMP_SILVER_AXE.get(), ItemInit.SWAMP_SILVER_SICKLE.get(), ItemInit.SWAMP_SILVER_SWORD.get(), ItemInit.SWAMP_SILVER_HELMET.get(), ItemInit.SWAMP_SILVER_CHESTPLATE.get(), ItemInit.SWAMP_SILVER_LEGGINGS.get(), ItemInit.SWAMP_SILVER_BOOTS.get());
    }

    private void addThermalSpringstoneRecipes(Consumer<FinishedRecipe> consumer) {
        addAxe(consumer, "thermal_springstone_axe", ItemInit.THERMAL_SPRINGSTONE_AXE, ItemInit.THERMAL_SPRINGSTONE_INGOT, () -> Items.STICK);
        addPickaxe(consumer, "thermal_springstone_pickaxe", ItemInit.THERMAL_SPRINGSTONE_PICKAXE, ItemInit.THERMAL_SPRINGSTONE_INGOT, () -> Items.STICK);
        addHoe(consumer, "thermal_springstone_scythe", ItemInit.THERMAL_SPRINGSTONE_SCYTHE, ItemInit.THERMAL_SPRINGSTONE_INGOT, () -> Items.STICK);
        addSword(consumer, "thermal_springstone_sword", ItemInit.THERMAL_SPRINGSTONE_SWORD, ItemInit.THERMAL_SPRINGSTONE_INGOT, () -> Items.STICK);
        addHammer(consumer, "thermal_springstone_hammer", ItemInit.THERMAL_SPRINGSTONE_HAMMER, ItemInit.THERMAL_SPRINGSTONE_INGOT, () -> Items.STICK);
        addHelmet(consumer, "thermal_springstone_helmet", ItemInit.THERMAL_SPRINGSTONE_HELMET, ItemInit.THERMAL_SPRINGSTONE_INGOT);
        addChestplate(consumer, "thermal_springstone_chestplate", ItemInit.THERMAL_SPRINGSTONE_CHESTPLATE, ItemInit.THERMAL_SPRINGSTONE_INGOT);
        addLeggings(consumer, "thermal_springstone_leggings", ItemInit.THERMAL_SPRINGSTONE_LEGGINGS, ItemInit.THERMAL_SPRINGSTONE_INGOT);
        addBoots(consumer, "thermal_springstone_boots", ItemInit.THERMAL_SPRINGSTONE_BOOTS, ItemInit.THERMAL_SPRINGSTONE_INGOT);
        addSmelt(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, "thermal_springstone_ingot", 200, ItemInit.THERMAL_SPRINGSTONE, ItemInit.THERMAL_SPRINGSTONE_INGOT, ItemInit.THERMAL_SPRINGSTONE.get());
        addSmelt(consumer, "blasting", RecipeSerializer.BLASTING_RECIPE, "thermal_springstone_ingot", 100, ItemInit.THERMAL_SPRINGSTONE, ItemInit.THERMAL_SPRINGSTONE_INGOT, ItemInit.THERMAL_SPRINGSTONE.get());
    }

    // misc
    protected final void addSmelt(Consumer<FinishedRecipe> finishedRecipeConsumer, String recipeTypeName, RecipeSerializer<? extends AbstractCookingRecipe> recipeSerializer, String id, int time, Supplier<? extends Item> criteria, Supplier<? extends Item> output, ItemLike... input) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(input), RecipeCategory.MISC, output.get(), 1.0f, time, recipeSerializer).unlockedBy("has_item", has(criteria.get())).save(finishedRecipeConsumer, getMiscLocation(id + "_" + recipeTypeName));
    }

    protected final void addCompressed(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, ItemLike toCompress, Supplier<? extends Block> output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Ingredient.of(toCompress))
                .unlockedBy("has_item", has(toCompress))
                .save(finishedRecipeConsumer, getModLocation("compressed_blocks/" + id));
    }

    protected final void addReverseCompressed(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Block compressed, Supplier<? extends Item> output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output.get(), 9)
                .requires(compressed)
                .unlockedBy("has_item", has(compressed))
                .save(finishedRecipeConsumer, getModLocation("compressed_blocks/reversed/" + id));
    }

    protected final void addShapeless(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> criteria, Supplier<? extends Item> output, int num, ItemLike... ingredients) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output.get(), num)
                .requires(Ingredient.of(ingredients))
                .unlockedBy("has_item", has(criteria.get()))
                .save(finishedRecipeConsumer, getModLocation("shapeless/" + id));
    }

    // combat & tools
    protected final void addHelmet(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> output, Supplier<? extends Item> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output.get())
                .pattern("###")
                .pattern("# #")
                .define('#', input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getEquipmentLocation(id));
    }

    protected final void addChestplate(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> output, Supplier<? extends Item> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output.get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getEquipmentLocation(id));
    }

    protected final void addLeggings(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> output, Supplier<? extends Item> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output.get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getEquipmentLocation(id));
    }

    protected final void addBoots(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> output, Supplier<? extends Item> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output.get())
                .pattern("# #")
                .pattern("# #")
                .define('#', input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getEquipmentLocation(id));
    }

    protected final void addHoe(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> output, Supplier<? extends Item> input, Supplier<? extends Item> handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output.get())
                .pattern("##")
                .pattern(" H")
                .pattern(" H")
                .define('#', input.get())
                .define('H', handle.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getEquipmentLocation(id));
    }

    protected final void addPickaxe(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> output, Supplier<? extends Item> input, Supplier<? extends Item> handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output.get())
                .pattern("###")
                .pattern(" H ")
                .pattern(" H ")
                .define('#', input.get())
                .define('H', handle.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getEquipmentLocation(id));
    }

    protected final void addSword(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> output, Supplier<? extends Item> input, Supplier<? extends Item> handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output.get())
                .pattern("#")
                .pattern("#")
                .pattern("H")
                .define('#', input.get())
                .define('H', handle.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getEquipmentLocation(id));
    }

    protected final void addAxe(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> output, Supplier<? extends Item> input, Supplier<? extends Item> handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output.get())
                .pattern("##")
                .pattern("#H")
                .pattern(" H")
                .define('#', input.get())
                .define('H', handle.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getEquipmentLocation(id));
    }

    protected final void addHammer(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> output, Supplier<? extends Item> input, Supplier<? extends Item> handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output.get())
                .pattern("###")
                .pattern("#H#")
                .pattern(" H ")
                .define('#', input.get())
                .define('H', handle.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getEquipmentLocation(id));
    }

    // building blocks and wooden stuff
    protected final void addPlanks(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, TagKey<Item> input) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output.get(), 4)
                .requires(input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_planks"));
    }

    protected final void addWood(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_wood"));
    }

    protected final void addStrippedWood(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_stripped_wood"));
    }

    protected final void addButton(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, output.get())
                .requires(input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_button"));
    }

    protected final void addDoor(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output.get(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_door"));
    }

    protected final void addFence(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output.get(), 3)
                .pattern("#S#")
                .pattern("#S#")
                .define('#', input.get())
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_fence"));
    }

    protected final void addFenceGate(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output.get())
                .pattern("S#S")
                .pattern("S#S")
                .define('#', input.get())
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_fence_gate"));
    }

    protected final void addPressurePlate(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output.get())
                .pattern("##")
                .define('#', input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_pressure_plate"));
    }

    protected final void addSlab(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output.get(), 6)
                .pattern("###")
                .define('#', input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_slab"));
    }

    protected final void addStairs(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output.get(), 2)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_stairs"));
    }

    protected final void addTrapdoor(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output.get(), 2)
                .pattern("###")
                .pattern("###")
                .define('#', input.get())
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_trapdoor"));
    }

    protected final void addSign(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output.get(), 3)
                .pattern("###")
                .pattern("###")
                .pattern(" S ")
                .define('#', input.get())
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_sign"));
    }

    protected final void addHangingSign(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Item> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output.get(), 6)
                .pattern("C C")
                .pattern("###")
                .pattern("###")
                .define('#', input.get())
                .define('C', Items.CHAIN)
                .unlockedBy("has_item", has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_hanging_sign"));
    }

    protected final void addBoat(Consumer<FinishedRecipe> finishedRecipeConsumer, Supplier<? extends Item> boat, Supplier<? extends Item> chestBoat, Supplier<? extends Block> planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, boat.get())
                .pattern("P P")
                .pattern("PPP")
                .define('P', planks.get())
                .group("boat")
                .unlockedBy("in_water", insideOf(Blocks.WATER))
                .save(finishedRecipeConsumer, getMiscLocation(ForgeRegistries.ITEMS.getKey(boat.get()).getPath()));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, chestBoat.get())
                .requires(boat.get())
                .group("chest_boat")
                .unlockedBy("has_boat", has(ItemTags.BOATS))
                .save(finishedRecipeConsumer, getMiscLocation(ForgeRegistries.ITEMS.getKey(chestBoat.get()).getPath()));
    }

    // stone
    protected final void addPolished(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output.get(), 4)
                .pattern("##")
                .pattern("##")
                .define('#', input.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input.get()).getPath(), has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_polished"));
    }

    protected final void addBricks(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output.get(), 4)
                .pattern("##")
                .pattern("##")
                .define('#', input.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input.get()).getPath(), has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_bricks"));
    }

    protected final void addWall(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output.get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', input.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input.get()).getPath(), has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_wall"));
    }

    protected final void addChiseled(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<? extends Block> output, Supplier<? extends Block> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output.get(), 6)
                .pattern("#")
                .pattern("#")
                .define('#', input.get())
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input.get()).getPath(), has(input.get()))
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_chiseled"));
    }

    protected final void addStoneCuttingChiseled(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<Block> input, ItemLike output) {
        makeStonecuttingRecipeBuilder(input, output)
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_chiseled_stonecutting_from_" + ForgeRegistries.BLOCKS.getKey(input.get()).getPath()));
    }

    protected final void addStoneCuttingBricks(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<Block> input, ItemLike output) {
        makeStonecuttingRecipeBuilder(input, output)
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_bricks_stonecutting_from_" + ForgeRegistries.BLOCKS.getKey(input.get()).getPath()));
    }

    protected final void addStoneCuttingSlab(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<Block> input, ItemLike output) {
        makeStonecuttingRecipeBuilder(input, output, 2)
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_slab_stonecutting_from_" + ForgeRegistries.BLOCKS.getKey(input.get()).getPath()));
    }

    protected final void addStoneCuttingStairs(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<Block> input, ItemLike output) {
        makeStonecuttingRecipeBuilder(input, output)
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_stairs_stonecutting_from_" + ForgeRegistries.BLOCKS.getKey(input.get()).getPath()));
    }

    protected final void addStoneCuttingWall(Consumer<FinishedRecipe> finishedRecipeConsumer, String id, Supplier<Block> input, ItemLike output) {
        makeStonecuttingRecipeBuilder(input, output)
                .save(finishedRecipeConsumer, getBuildingLocation(id + "_wall_stonecutting_from_" + ForgeRegistries.BLOCKS.getKey(input.get()).getPath()));
    }

    public SingleItemRecipeBuilder makeStonecuttingRecipeBuilder(Supplier<Block> input, ItemLike output) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(input.get()), RecipeCategory.BUILDING_BLOCKS, output)
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input.get()), has(input.get()));
    }

    public SingleItemRecipeBuilder makeStonecuttingRecipeBuilder(Supplier<Block> input, ItemLike output, int outputAmount) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(input.get()), RecipeCategory.BUILDING_BLOCKS, output, outputAmount)
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input.get()), has(input.get()));
    }

    protected final ResourceLocation getModLocation(String id) {
        return new ResourceLocation(EternalStarlight.MOD_ID, id);
    }

    protected final ResourceLocation getMiscLocation(String id) {
        return getModLocation("misc/" + id);
    }

    protected final ResourceLocation getEquipmentLocation(String id) {
        return getModLocation("equipment/" + id);
    }

    protected final ResourceLocation getBuildingLocation(String id) {
        return getModLocation("building/" + id);
    }
}
