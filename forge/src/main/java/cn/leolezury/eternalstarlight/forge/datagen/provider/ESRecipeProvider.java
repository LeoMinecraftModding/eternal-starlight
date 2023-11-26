package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
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
import net.neoforged.neoforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

public class ESRecipeProvider extends RecipeProvider {
    public ESRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        addWoodRecipes(consumer);
        addStoneRecipes(consumer);
        addAetherSentRecipes(consumer);
        addSwampSilverRecipes(consumer);
        addThermalSpringstoneRecipes(consumer);

        // smelt
        addSmelt(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, "golem_steel_ingot", 200, ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get(), ItemInit.GOLEM_STEEL_INGOT.get(), ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get());
        addSmelt(consumer, "blasting", RecipeSerializer.BLASTING_RECIPE, "golem_steel_ingot", 100, ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get(), ItemInit.GOLEM_STEEL_INGOT.get(), ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get());

        // misc
        addShapeless(consumer, "muddy_starlight_mangrove_roots", ItemInit.STARLIGHT_MANGROVE_ROOTS.get(), ItemInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get(), 1, ItemInit.STARLIGHT_MANGROVE_ROOTS.get(), ItemInit.NIGHTSHADE_MUD.get());
        addShapeless(consumer, "packed_nightshade_mud", ItemInit.NIGHTSHADE_MUD.get(), ItemInit.PACKED_NIGHTSHADE_MUD.get(), 1, ItemInit.NIGHTSHADE_MUD.get(), ItemInit.LUNAR_BERRIES.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.SEEKING_EYE.get())
                .pattern("FFF")
                .pattern("FPF")
                .pattern("FFF")
                .define('P', Items.ENDER_PEARL)
                .define('F', ItemInit.STARLIGHT_FLOWER.get())
                .unlockedBy("has_item", has(ItemInit.STARLIGHT_FLOWER.get()))
                .save(consumer, getModLocation("seeking_eye"));
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
                .save(consumer, getModLocation("crystal_crossbow"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.MOONRING_GREATSWORD.get())
                .pattern("TTT")
                .pattern("TGT")
                .pattern("SSS")
                .define('S', Items.STICK)
                .define('G', ItemInit.GOLEM_STEEL_INGOT.get())
                .define('T', ItemInit.TENACIOUS_PETAL.get())
                .unlockedBy("has_item", has(ItemInit.TENACIOUS_PETAL.get()))
                .save(consumer, getModLocation("moonring_greatsword"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.PETAL_SCYTHE.get())
                .pattern("GTT")
                .pattern("GS ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('G', ItemInit.GOLEM_STEEL_INGOT.get())
                .define('T', ItemInit.TENACIOUS_PETAL.get())
                .unlockedBy("has_item", has(ItemInit.TENACIOUS_PETAL.get()))
                .save(consumer, getModLocation("petal_scythe"));
    }

    private void addWoodRecipes(RecipeOutput consumer) {
        addButton(consumer, "lunar", BlockInit.LUNAR_BUTTON.get(), BlockInit.LUNAR_PLANKS.get());
        addDoor(consumer, "lunar", BlockInit.LUNAR_DOOR.get(), BlockInit.LUNAR_PLANKS.get());
        addFence(consumer, "lunar", BlockInit.LUNAR_FENCE.get(), BlockInit.LUNAR_PLANKS.get());
        addFenceGate(consumer, "lunar", BlockInit.LUNAR_FENCE_GATE.get(), BlockInit.LUNAR_PLANKS.get());
        addPlanks(consumer, "lunar", BlockInit.LUNAR_PLANKS.get(), ESTags.Items.LUNAR_LOGS);
        addWood(consumer, "lunar", BlockInit.LUNAR_WOOD.get(), BlockInit.LUNAR_LOG.get());
        addStrippedWood(consumer, "lunar", BlockInit.STRIPPED_LUNAR_WOOD.get(), BlockInit.STRIPPED_LUNAR_LOG.get());
        addPressurePlate(consumer, "lunar", BlockInit.LUNAR_PRESSURE_PLATE.get(), BlockInit.LUNAR_PLANKS.get());
        addSlab(consumer, "lunar", BlockInit.LUNAR_SLAB.get(), BlockInit.LUNAR_PLANKS.get());
        addStairs(consumer, "lunar", BlockInit.LUNAR_STAIRS.get(), BlockInit.LUNAR_PLANKS.get());
        addTrapdoor(consumer, "lunar", BlockInit.LUNAR_TRAPDOOR.get(), BlockInit.LUNAR_PLANKS.get());
        addSign(consumer, "lunar", ItemInit.LUNAR_SIGN.get(), BlockInit.LUNAR_PLANKS.get());
        addHangingSign(consumer, "lunar", ItemInit.LUNAR_HANGING_SIGN.get(), BlockInit.STRIPPED_LUNAR_LOG.get());
        addBoat(consumer, ItemInit.LUNAR_BOAT.get(), ItemInit.LUNAR_CHEST_BOAT.get(), BlockInit.LUNAR_PLANKS.get());

        addButton(consumer, "northland", BlockInit.NORTHLAND_BUTTON.get(), BlockInit.NORTHLAND_PLANKS.get());
        addDoor(consumer, "northland", BlockInit.NORTHLAND_DOOR.get(), BlockInit.NORTHLAND_PLANKS.get());
        addFence(consumer, "northland", BlockInit.NORTHLAND_FENCE.get(), BlockInit.NORTHLAND_PLANKS.get());
        addFenceGate(consumer, "northland", BlockInit.NORTHLAND_FENCE_GATE.get(), BlockInit.NORTHLAND_PLANKS.get());
        addPlanks(consumer, "northland", BlockInit.NORTHLAND_PLANKS.get(), ESTags.Items.NORTHLAND_LOGS);
        addWood(consumer, "northland", BlockInit.NORTHLAND_WOOD.get(), BlockInit.NORTHLAND_LOG.get());
        addStrippedWood(consumer, "northland", BlockInit.STRIPPED_NORTHLAND_WOOD.get(), BlockInit.STRIPPED_NORTHLAND_LOG.get());
        addPressurePlate(consumer, "northland", BlockInit.NORTHLAND_PRESSURE_PLATE.get(), BlockInit.NORTHLAND_PLANKS.get());
        addSlab(consumer, "northland", BlockInit.NORTHLAND_SLAB.get(), BlockInit.NORTHLAND_PLANKS.get());
        addStairs(consumer, "northland", BlockInit.NORTHLAND_STAIRS.get(), BlockInit.NORTHLAND_PLANKS.get());
        addTrapdoor(consumer, "northland", BlockInit.NORTHLAND_TRAPDOOR.get(), BlockInit.NORTHLAND_PLANKS.get());
        addSign(consumer, "northland", ItemInit.NORTHLAND_SIGN.get(), BlockInit.NORTHLAND_PLANKS.get());
        addHangingSign(consumer, "northland", ItemInit.NORTHLAND_HANGING_SIGN.get(), BlockInit.STRIPPED_NORTHLAND_LOG.get());
        addBoat(consumer, ItemInit.NORTHLAND_BOAT.get(), ItemInit.NORTHLAND_CHEST_BOAT.get(), BlockInit.NORTHLAND_PLANKS.get());

        addButton(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_BUTTON.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addDoor(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_DOOR.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addFence(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_FENCE.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addFenceGate(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_FENCE_GATE.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addPlanks(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_PLANKS.get(), ESTags.Items.STARLIGHT_MANGROVE_LOGS);
        addWood(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_WOOD.get(), BlockInit.STARLIGHT_MANGROVE_LOG.get());
        addStrippedWood(consumer, "starlight_mangrove", BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get(), BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
        addPressurePlate(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addSlab(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_SLAB.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addStairs(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_STAIRS.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addTrapdoor(consumer, "starlight_mangrove", BlockInit.STARLIGHT_MANGROVE_TRAPDOOR.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addSign(consumer, "starlight_mangrove", ItemInit.STARLIGHT_MANGROVE_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        addHangingSign(consumer, "starlight_mangrove", ItemInit.STARLIGHT_MANGROVE_HANGING_SIGN.get(), BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
        addBoat(consumer, ItemInit.STARLIGHT_MANGROVE_BOAT.get(), ItemInit.STARLIGHT_MANGROVE_CHEST_BOAT.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
    }

    private void addStoneRecipes(RecipeOutput consumer) {
        addPolished(consumer, "grimstone", BlockInit.POLISHED_GRIMSTONE.get(), BlockInit.GRIMSTONE.get());
        addBricks(consumer, "grimstone", BlockInit.GRIMSTONE_BRICKS.get(), BlockInit.POLISHED_GRIMSTONE.get());
        addChiseled(consumer, "grimstone", BlockInit.CHISELED_GRIMSTONE.get(), BlockInit.GRIMSTONE_BRICK_SLAB.get());
        addStoneCuttingChiseled(consumer, "grimstone", BlockInit.GRIMSTONE_BRICKS.get(), ItemInit.CHISELED_GRIMSTONE.get());
        addStoneCuttingBricks(consumer, "grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.GRIMSTONE_BRICKS.get());
        addWall(consumer, "grimstone", BlockInit.GRIMSTONE_BRICK_WALL.get(), BlockInit.GRIMSTONE_BRICKS.get());
        addStoneCuttingWall(consumer, "grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.GRIMSTONE_BRICK_WALL.get());
        addStoneCuttingWall(consumer, "grimstone", BlockInit.GRIMSTONE_BRICKS.get(), ItemInit.GRIMSTONE_BRICK_WALL.get());
        addStairs(consumer, "grimstone", BlockInit.GRIMSTONE_BRICK_STAIRS.get(), BlockInit.GRIMSTONE_BRICKS.get());
        addStoneCuttingStairs(consumer, "grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.GRIMSTONE_BRICK_STAIRS.get());
        addStoneCuttingStairs(consumer, "grimstone", BlockInit.GRIMSTONE_BRICKS.get(), ItemInit.GRIMSTONE_BRICK_STAIRS.get());
        addSlab(consumer, "grimstone", BlockInit.GRIMSTONE_BRICK_SLAB.get(), BlockInit.GRIMSTONE_BRICKS.get());
        addStoneCuttingSlab(consumer, "grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.GRIMSTONE_BRICK_SLAB.get());
        addStoneCuttingSlab(consumer, "grimstone", BlockInit.GRIMSTONE_BRICKS.get(), ItemInit.GRIMSTONE_BRICK_SLAB.get());
        addWall(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE_WALL.get(), BlockInit.POLISHED_GRIMSTONE.get());
        addStoneCuttingWall(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.POLISHED_GRIMSTONE_WALL.get());
        addStairs(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE_STAIRS.get(), BlockInit.POLISHED_GRIMSTONE.get());
        addStoneCuttingStairs(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.POLISHED_GRIMSTONE_STAIRS.get());
        addSlab(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE_SLAB.get(), BlockInit.POLISHED_GRIMSTONE.get());
        addStoneCuttingSlab(consumer, "polished_grimstone", BlockInit.POLISHED_GRIMSTONE.get(), ItemInit.POLISHED_GRIMSTONE_SLAB.get());

        addPolished(consumer, "voidstone", BlockInit.POLISHED_VOIDSTONE.get(), BlockInit.VOIDSTONE.get());
        addBricks(consumer, "voidstone", BlockInit.VOIDSTONE_BRICKS.get(), BlockInit.POLISHED_VOIDSTONE.get());
        addChiseled(consumer, "voidstone", BlockInit.CHISELED_VOIDSTONE.get(), BlockInit.VOIDSTONE_BRICK_SLAB.get());
        addStoneCuttingChiseled(consumer, "voidstone", BlockInit.VOIDSTONE_BRICKS.get(), ItemInit.CHISELED_VOIDSTONE.get());
        addStoneCuttingBricks(consumer, "voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.VOIDSTONE_BRICKS.get());
        addWall(consumer, "voidstone", BlockInit.VOIDSTONE_BRICK_WALL.get(), BlockInit.VOIDSTONE_BRICKS.get());
        addStoneCuttingWall(consumer, "voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.VOIDSTONE_BRICK_WALL.get());
        addStoneCuttingWall(consumer, "voidstone", BlockInit.VOIDSTONE_BRICKS.get(), ItemInit.VOIDSTONE_BRICK_WALL.get());
        addStairs(consumer, "voidstone", BlockInit.VOIDSTONE_BRICK_STAIRS.get(), BlockInit.VOIDSTONE_BRICKS.get());
        addStoneCuttingStairs(consumer, "voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.VOIDSTONE_BRICK_STAIRS.get());
        addStoneCuttingStairs(consumer, "voidstone", BlockInit.VOIDSTONE_BRICKS.get(), ItemInit.VOIDSTONE_BRICK_STAIRS.get());
        addSlab(consumer, "voidstone", BlockInit.VOIDSTONE_BRICK_SLAB.get(), BlockInit.VOIDSTONE_BRICKS.get());
        addStoneCuttingSlab(consumer, "voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.VOIDSTONE_BRICK_SLAB.get());
        addStoneCuttingSlab(consumer, "voidstone", BlockInit.VOIDSTONE_BRICKS.get(), ItemInit.VOIDSTONE_BRICK_SLAB.get());
        addWall(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE_WALL.get(), BlockInit.POLISHED_VOIDSTONE.get());
        addStoneCuttingWall(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.POLISHED_VOIDSTONE_WALL.get());
        addStairs(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE_STAIRS.get(), BlockInit.POLISHED_VOIDSTONE.get());
        addStoneCuttingStairs(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.POLISHED_VOIDSTONE_STAIRS.get());
        addSlab(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE_SLAB.get(), BlockInit.POLISHED_VOIDSTONE.get());
        addStoneCuttingSlab(consumer, "polished_voidstone", BlockInit.POLISHED_VOIDSTONE.get(), ItemInit.POLISHED_VOIDSTONE_SLAB.get());

        addPolished(consumer, "doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), BlockInit.DOOMEDEN_BRICKS.get());
        addBricks(consumer, "doomeden", BlockInit.DOOMEDEN_BRICKS.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        addChiseled(consumer, "doomeden", BlockInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get(), BlockInit.DOOMEDEN_BRICK_SLAB.get());
        addStoneCuttingChiseled(consumer, "doomeden", BlockInit.DOOMEDEN_BRICKS.get(), ItemInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        addStoneCuttingBricks(consumer, "doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICKS.get());
        addWall(consumer, "doomeden", BlockInit.DOOMEDEN_BRICK_WALL.get(), BlockInit.DOOMEDEN_BRICKS.get());
        addStoneCuttingWall(consumer, "doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_WALL.get());
        addStoneCuttingWall(consumer, "doomeden", BlockInit.DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_WALL.get());
        addStairs(consumer, "doomeden", BlockInit.DOOMEDEN_BRICK_STAIRS.get(), BlockInit.DOOMEDEN_BRICKS.get());
        addStoneCuttingStairs(consumer, "doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_STAIRS.get());
        addStoneCuttingStairs(consumer, "doomeden", BlockInit.DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_STAIRS.get());
        addSlab(consumer, "doomeden", BlockInit.DOOMEDEN_BRICK_SLAB.get(), BlockInit.DOOMEDEN_BRICKS.get());
        addStoneCuttingSlab(consumer, "doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_SLAB.get());
        addStoneCuttingSlab(consumer, "doomeden", BlockInit.DOOMEDEN_BRICKS.get(), ItemInit.DOOMEDEN_BRICK_SLAB.get());
        addWall(consumer, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICK_WALL.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        addStoneCuttingWall(consumer, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.POLISHED_DOOMEDEN_BRICK_WALL.get());
        addStairs(consumer, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        addStoneCuttingStairs(consumer, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get());
        addSlab(consumer, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICK_SLAB.get(), BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        addStoneCuttingSlab(consumer, "polished_doomeden", BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), ItemInit.POLISHED_DOOMEDEN_BRICK_SLAB.get());

        addBricks(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS.get(), BlockInit.PACKED_NIGHTSHADE_MUD.get());
        addWall(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICK_WALL.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        addStoneCuttingWall(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS.get(), ItemInit.NIGHTSHADE_MUD_BRICK_WALL.get());
        addStairs(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        addStoneCuttingStairs(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS.get(), ItemInit.NIGHTSHADE_MUD_BRICK_STAIRS.get());
        addSlab(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICK_SLAB.get(), BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        addStoneCuttingSlab(consumer, "nightshade_mud", BlockInit.NIGHTSHADE_MUD_BRICKS.get(), ItemInit.NIGHTSHADE_MUD_BRICK_SLAB.get());
    }

    private void addAetherSentRecipes(RecipeOutput consumer) {
        addCompressed(consumer, "aethersent", ItemInit.AETHERSENT_INGOT.get(), ItemInit.AETHERSENT_BLOCK.get());
        addReverseCompressed(consumer, "aethersent", ItemInit.AETHERSENT_BLOCK.get(), ItemInit.AETHERSENT_INGOT.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_HOOD.get())
                .pattern("###")
                .pattern("A A")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(consumer, getModLocation("aethersent_hood"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_CAPE.get())
                .pattern("A A")
                .pattern("#A#")
                .pattern("###")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(consumer, getModLocation("aethersent_cape"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_BOTTOMS.get())
                .pattern("###")
                .pattern("A A")
                .pattern("# #")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(consumer, getModLocation("aethersent_bottoms"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.AETHERSENT_BOOTS.get())
                .pattern("A A")
                .pattern("# #")
                .define('#', Items.LEATHER)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(consumer, getModLocation("aethersent_boots"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.STARFALL_LONGBOW.get())
                .pattern(" AS")
                .pattern("A S")
                .pattern(" AS")
                .define('S', Items.STRING)
                .define('A', ItemInit.AETHERSENT_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.AETHERSENT_INGOT.get()))
                .save(consumer, getModLocation("starfall_longbow"));
        addSword(consumer, "rage_of_stars", ItemInit.RAGE_OF_STARS.get(), ItemInit.AETHERSENT_INGOT.get(), Items.STICK);
    }

    private void addSwampSilverRecipes(RecipeOutput consumer) {
        addCompressed(consumer, "swamp_silver", ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_BLOCK.get());
        addReverseCompressed(consumer, "swamp_silver", ItemInit.SWAMP_SILVER_BLOCK.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addAxe(consumer, "swamp_silver_axe", ItemInit.SWAMP_SILVER_AXE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addPickaxe(consumer, "swamp_silver_pickaxe", ItemInit.SWAMP_SILVER_PICKAXE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addHoe(consumer, "swamp_silver_sickle", ItemInit.SWAMP_SILVER_SICKLE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addSword(consumer, "swamp_silver_sword", ItemInit.SWAMP_SILVER_SWORD.get(), ItemInit.SWAMP_SILVER_INGOT.get(), Items.STICK);
        addHelmet(consumer, "swamp_silver_helmet", ItemInit.SWAMP_SILVER_HELMET.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addChestplate(consumer, "swamp_silver_chestplate", ItemInit.SWAMP_SILVER_CHESTPLATE.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addLeggings(consumer, "swamp_silver_leggings", ItemInit.SWAMP_SILVER_LEGGINGS.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        addBoots(consumer, "swamp_silver_boots", ItemInit.SWAMP_SILVER_BOOTS.get(), ItemInit.SWAMP_SILVER_INGOT.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.SWAMP_SILVER_INGOT.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Ingredient.of(ItemInit.SWAMP_SILVER_NUGGET.get()))
                .unlockedBy("has_item", has(ItemInit.SWAMP_SILVER_NUGGET.get()))
                .save(consumer, getModLocation("swamp_silver_ingot_from_nuggets"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.SWAMP_SILVER_NUGGET.get(), 9)
                .requires(ItemInit.SWAMP_SILVER_INGOT.get())
                .unlockedBy("has_item", has(ItemInit.SWAMP_SILVER_INGOT.get()))
                .save(consumer, getModLocation("swamp_silver_nuggets_from_ingot"));
        addSmelt(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, "swamp_silver_ingot", 200, ItemInit.SWAMP_SILVER_ORE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_ORE.get());
        addSmelt(consumer, "blasting", RecipeSerializer.BLASTING_RECIPE, "swamp_silver_ingot", 100, ItemInit.SWAMP_SILVER_ORE.get(), ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_ORE.get());
        addSmelt(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, "swamp_silver_nugget", 200, ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_PICKAXE.get(), ItemInit.SWAMP_SILVER_AXE.get(), ItemInit.SWAMP_SILVER_SICKLE.get(), ItemInit.SWAMP_SILVER_SWORD.get(), ItemInit.SWAMP_SILVER_HELMET.get(), ItemInit.SWAMP_SILVER_CHESTPLATE.get(), ItemInit.SWAMP_SILVER_LEGGINGS.get(), ItemInit.SWAMP_SILVER_BOOTS.get());
        addSmelt(consumer, "blasting", RecipeSerializer.BLASTING_RECIPE, "swamp_silver_nugget", 100, ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_INGOT.get(), ItemInit.SWAMP_SILVER_PICKAXE.get(), ItemInit.SWAMP_SILVER_AXE.get(), ItemInit.SWAMP_SILVER_SICKLE.get(), ItemInit.SWAMP_SILVER_SWORD.get(), ItemInit.SWAMP_SILVER_HELMET.get(), ItemInit.SWAMP_SILVER_CHESTPLATE.get(), ItemInit.SWAMP_SILVER_LEGGINGS.get(), ItemInit.SWAMP_SILVER_BOOTS.get());
    }

    private void addThermalSpringstoneRecipes(RecipeOutput consumer) {
        addAxe(consumer, "thermal_springstone_axe", ItemInit.THERMAL_SPRINGSTONE_AXE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addPickaxe(consumer, "thermal_springstone_pickaxe", ItemInit.THERMAL_SPRINGSTONE_PICKAXE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addHoe(consumer, "thermal_springstone_scythe", ItemInit.THERMAL_SPRINGSTONE_SCYTHE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addSword(consumer, "thermal_springstone_sword", ItemInit.THERMAL_SPRINGSTONE_SWORD.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addHammer(consumer, "thermal_springstone_hammer", ItemInit.THERMAL_SPRINGSTONE_HAMMER.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
        addHelmet(consumer, "thermal_springstone_helmet", ItemInit.THERMAL_SPRINGSTONE_HELMET.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addChestplate(consumer, "thermal_springstone_chestplate", ItemInit.THERMAL_SPRINGSTONE_CHESTPLATE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addLeggings(consumer, "thermal_springstone_leggings", ItemInit.THERMAL_SPRINGSTONE_LEGGINGS.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addBoots(consumer, "thermal_springstone_boots", ItemInit.THERMAL_SPRINGSTONE_BOOTS.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        addSmelt(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, "thermal_springstone_ingot", 200, ItemInit.THERMAL_SPRINGSTONE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), ItemInit.THERMAL_SPRINGSTONE.get());
        addSmelt(consumer, "blasting", RecipeSerializer.BLASTING_RECIPE, "thermal_springstone_ingot", 100, ItemInit.THERMAL_SPRINGSTONE.get(), ItemInit.THERMAL_SPRINGSTONE_INGOT.get(), ItemInit.THERMAL_SPRINGSTONE.get());
    }

    // misc
    protected final void addSmelt(RecipeOutput finishedRecipeConsumer, String recipeTypeName, RecipeSerializer<? extends AbstractCookingRecipe> recipeSerializer, String id, int time, ItemLike criteria, ItemLike output, ItemLike... input) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(input), RecipeCategory.MISC, output, 1.0f, time, recipeSerializer).unlockedBy("has_item", has(criteria)).save(finishedRecipeConsumer, getModLocation(id + "_" + recipeTypeName));
    }

    protected final void addCompressed(RecipeOutput finishedRecipeConsumer, String id, ItemLike toCompress, ItemLike output) {
        addCompressed(finishedRecipeConsumer, id, RecipeCategory.MISC, toCompress, output);
    }

    protected final void addCompressed(RecipeOutput finishedRecipeConsumer, String id, RecipeCategory category, ItemLike toCompress, ItemLike output) {
        ShapedRecipeBuilder.shaped(category, output)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Ingredient.of(toCompress))
                .unlockedBy("has_item", has(toCompress))
                .save(finishedRecipeConsumer, getModLocation("compressed/" + id));
    }

    protected final void addReverseCompressed(RecipeOutput finishedRecipeConsumer, String id, ItemLike compressed, ItemLike output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 9)
                .requires(compressed)
                .unlockedBy("has_item", has(compressed))
                .save(finishedRecipeConsumer, getModLocation("compressed/reversed/" + id));
    }

    protected final void addShapeless(RecipeOutput finishedRecipeConsumer, String id, ItemLike criteria, ItemLike output, int num, ItemLike... ingredients) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, num);
        for (ItemLike item : ingredients) {
            builder.requires(item);
        }
        builder.unlockedBy("has_item", has(criteria)).save(finishedRecipeConsumer, getModLocation("shapeless/" + id));
    }

    // combat & tools
    protected final void addHelmet(RecipeOutput finishedRecipeConsumer, String id, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("###")
                .pattern("# #")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id));
    }

    protected final void addChestplate(RecipeOutput finishedRecipeConsumer, String id, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id));
    }

    protected final void addLeggings(RecipeOutput finishedRecipeConsumer, String id, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id));
    }

    protected final void addBoots(RecipeOutput finishedRecipeConsumer, String id, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("# #")
                .pattern("# #")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id));
    }

    protected final void addHoe(RecipeOutput finishedRecipeConsumer, String id, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("##")
                .pattern(" H")
                .pattern(" H")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id));
    }

    protected final void addPickaxe(RecipeOutput finishedRecipeConsumer, String id, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("###")
                .pattern(" H ")
                .pattern(" H ")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id));
    }

    protected final void addSword(RecipeOutput finishedRecipeConsumer, String id, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("#")
                .pattern("#")
                .pattern("H")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id));
    }

    protected final void addAxe(RecipeOutput finishedRecipeConsumer, String id, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("##")
                .pattern("#H")
                .pattern(" H")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id));
    }

    protected final void addHammer(RecipeOutput finishedRecipeConsumer, String id, ItemLike output, ItemLike input, ItemLike handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .pattern("###")
                .pattern("#H#")
                .pattern(" H ")
                .define('#', input)
                .define('H', handle)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id));
    }

    // building blocks and wooden stuff
    protected final void addPlanks(RecipeOutput finishedRecipeConsumer, String id, Block output, TagKey<Item> input) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .requires(input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_planks"));
    }

    protected final void addWood(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_wood"));
    }

    protected final void addStrippedWood(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_stripped_wood"));
    }

    protected final void addButton(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, output)
                .requires(input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_button"));
    }

    protected final void addDoor(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output, 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_door"));
    }

    protected final void addFence(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("#S#")
                .pattern("#S#")
                .define('#', input)
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_fence"));
    }

    protected final void addFenceGate(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output)
                .pattern("S#S")
                .pattern("S#S")
                .define('#', input)
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_fence_gate"));
    }

    protected final void addPressurePlate(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output)
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_pressure_plate"));
    }

    protected final void addSlab(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_slab"));
    }

    protected final void addStairs(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 2)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_stairs"));
    }

    protected final void addTrapdoor(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output, 2)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_trapdoor"));
    }

    protected final void addSign(RecipeOutput finishedRecipeConsumer, String id, ItemLike output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 3)
                .pattern("###")
                .pattern("###")
                .pattern(" S ")
                .define('#', input)
                .define('S', Items.STICK)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_sign"));
    }

    protected final void addHangingSign(RecipeOutput finishedRecipeConsumer, String id, ItemLike output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 6)
                .pattern("C C")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .define('C', Items.CHAIN)
                .unlockedBy("has_item", has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_hanging_sign"));
    }

    protected final void addBoat(RecipeOutput finishedRecipeConsumer, Item boat, Item chestBoat, Block planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, boat)
                .pattern("P P")
                .pattern("PPP")
                .define('P', planks)
                .group("boat")
                .unlockedBy("in_water", insideOf(Blocks.WATER))
                .save(finishedRecipeConsumer, getModLocation(ForgeRegistries.ITEMS.getKey(boat).getPath()));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, chestBoat)
                .requires(boat)
                .group("chest_boat")
                .unlockedBy("has_boat", has(ItemTags.BOATS))
                .save(finishedRecipeConsumer, getModLocation(ForgeRegistries.ITEMS.getKey(chestBoat).getPath()));
    }

    // stone
    protected final void addPolished(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input).getPath(), has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_polished"));
    }

    protected final void addBricks(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input).getPath(), has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_bricks"));
    }

    protected final void addWall(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input).getPath(), has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_wall"));
    }

    protected final void addChiseled(RecipeOutput finishedRecipeConsumer, String id, Block output, Block input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("#")
                .pattern("#")
                .define('#', input)
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input).getPath(), has(input))
                .save(finishedRecipeConsumer, getModLocation(id + "_chiseled"));
    }

    protected final void addStoneCuttingChiseled(RecipeOutput finishedRecipeConsumer, String id, Block input, ItemLike output) {
        makeStonecuttingRecipeBuilder(input, output)
                .save(finishedRecipeConsumer, getModLocation(id + "_chiseled_stonecutting_from_" + ForgeRegistries.BLOCKS.getKey(input).getPath()));
    }

    protected final void addStoneCuttingBricks(RecipeOutput finishedRecipeConsumer, String id, Block input, ItemLike output) {
        makeStonecuttingRecipeBuilder(input, output)
                .save(finishedRecipeConsumer, getModLocation(id + "_bricks_stonecutting_from_" + ForgeRegistries.BLOCKS.getKey(input).getPath()));
    }

    protected final void addStoneCuttingSlab(RecipeOutput finishedRecipeConsumer, String id, Block input, ItemLike output) {
        makeStonecuttingRecipeBuilder(input, output, 2)
                .save(finishedRecipeConsumer, getModLocation(id + "_slab_stonecutting_from_" + ForgeRegistries.BLOCKS.getKey(input).getPath()));
    }

    protected final void addStoneCuttingStairs(RecipeOutput finishedRecipeConsumer, String id, Block input, ItemLike output) {
        makeStonecuttingRecipeBuilder(input, output)
                .save(finishedRecipeConsumer, getModLocation(id + "_stairs_stonecutting_from_" + ForgeRegistries.BLOCKS.getKey(input).getPath()));
    }

    protected final void addStoneCuttingWall(RecipeOutput finishedRecipeConsumer, String id, Block input, ItemLike output) {
        makeStonecuttingRecipeBuilder(input, output)
                .save(finishedRecipeConsumer, getModLocation(id + "_wall_stonecutting_from_" + ForgeRegistries.BLOCKS.getKey(input).getPath()));
    }

    public SingleItemRecipeBuilder makeStonecuttingRecipeBuilder(Block input, ItemLike output) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, output)
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input), has(input));
    }

    public SingleItemRecipeBuilder makeStonecuttingRecipeBuilder(Block input, ItemLike output, int outputAmount) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, output, outputAmount)
                .unlockedBy("has_" + ForgeRegistries.BLOCKS.getKey(input), has(input));
    }

    protected final ResourceLocation getModLocation(String id) {
        return new ResourceLocation(EternalStarlight.MOD_ID, id);
    }
}
