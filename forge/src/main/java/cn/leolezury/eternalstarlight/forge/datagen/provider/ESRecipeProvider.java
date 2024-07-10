package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.recipe.ManaCrystalRecipe;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ESRecipeProvider extends RecipeProvider {
	public ESRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		addWoodRecipes(recipeOutput);
		addStoneRecipes(recipeOutput);
		addAlchemistArmorRecipes(recipeOutput);
		addAetherSentRecipes(recipeOutput);
		addSwampSilverRecipes(recipeOutput);
		addThermalSpringstoneRecipes(recipeOutput);
		addGlaciteRecipes(recipeOutput);
		addSaltpeterRecipes(recipeOutput);
		addAmaramberRecipes(recipeOutput);

		smithingTrims().forEach((template) -> trimSmithing(recipeOutput, template.template(), template.id()));
		copySmithingTemplate(recipeOutput, ESItems.KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ESItems.GRIMSTONE.get(), ESItems.SWAMP_SILVER_INGOT.get());
		copySmithingTemplate(recipeOutput, ESItems.FORGE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ESItems.VOIDSTONE.get(), ESItems.GOLEM_STEEL_INGOT.get());

		// yeti fur
		List<Item> dyeList = List.of(Items.BLACK_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.CYAN_DYE, Items.GRAY_DYE, Items.GREEN_DYE, Items.LIGHT_BLUE_DYE, Items.LIGHT_GRAY_DYE, Items.LIME_DYE, Items.MAGENTA_DYE, Items.ORANGE_DYE, Items.PINK_DYE, Items.PURPLE_DYE, Items.RED_DYE, Items.YELLOW_DYE, Items.WHITE_DYE);
		List<Item> bedList = List.of(Items.BLACK_BED, Items.BLUE_BED, Items.BROWN_BED, Items.CYAN_BED, Items.GRAY_BED, Items.GREEN_BED, Items.LIGHT_BLUE_BED, Items.LIGHT_GRAY_BED, Items.LIME_BED, Items.MAGENTA_BED, Items.ORANGE_BED, Items.PINK_BED, Items.PURPLE_BED, Items.RED_BED, Items.YELLOW_BED, Items.WHITE_BED);
		List<Item> furList = List.of(ESItems.BLACK_YETI_FUR.get(), ESItems.BLUE_YETI_FUR.get(), ESItems.BROWN_YETI_FUR.get(), ESItems.CYAN_YETI_FUR.get(), ESItems.GRAY_YETI_FUR.get(), ESItems.GREEN_YETI_FUR.get(), ESItems.LIGHT_BLUE_YETI_FUR.get(), ESItems.LIGHT_GRAY_YETI_FUR.get(), ESItems.LIME_YETI_FUR.get(), ESItems.MAGENTA_YETI_FUR.get(), ESItems.ORANGE_YETI_FUR.get(), ESItems.PINK_YETI_FUR.get(), ESItems.PURPLE_YETI_FUR.get(), ESItems.RED_YETI_FUR.get(), ESItems.YELLOW_YETI_FUR.get(), ESItems.WHITE_YETI_FUR.get());
		colorWithDye(recipeOutput, dyeList, furList, "yeti_fur");
		List<Item> carpetList = List.of(ESItems.BLACK_YETI_FUR_CARPET.get(), ESItems.BLUE_YETI_FUR_CARPET.get(), ESItems.BROWN_YETI_FUR_CARPET.get(), ESItems.CYAN_YETI_FUR_CARPET.get(), ESItems.GRAY_YETI_FUR_CARPET.get(), ESItems.GREEN_YETI_FUR_CARPET.get(), ESItems.LIGHT_BLUE_YETI_FUR_CARPET.get(), ESItems.LIGHT_GRAY_YETI_FUR_CARPET.get(), ESItems.LIME_YETI_FUR_CARPET.get(), ESItems.MAGENTA_YETI_FUR_CARPET.get(), ESItems.ORANGE_YETI_FUR_CARPET.get(), ESItems.PINK_YETI_FUR_CARPET.get(), ESItems.PURPLE_YETI_FUR_CARPET.get(), ESItems.RED_YETI_FUR_CARPET.get(), ESItems.YELLOW_YETI_FUR_CARPET.get(), ESItems.WHITE_YETI_FUR_CARPET.get());
		colorWithDye(recipeOutput, dyeList, carpetList, "yeti_fur_carpet");
		for (int i = 0; i < furList.size(); i++) {
			customCarpet(recipeOutput, carpetList.get(i), furList.get(i));
		}
		for (int i = 0; i < furList.size(); i++) {
			bed(recipeOutput, bedList.get(i), furList.get(i));
		}

		// flower -> dye
		addSingleConversion(recipeOutput, Items.BLUE_DYE, ESItems.STARLIGHT_FLOWER.get());
		addSingleConversion(recipeOutput, Items.BROWN_DYE, ESItems.CONEBLOOM.get());
		addSingleConversion(recipeOutput, Items.PURPLE_DYE, ESItems.NIGHTFAN.get());
		addSingleConversion(recipeOutput, Items.PINK_DYE, ESItems.PINK_ROSE.get());
		addSingleConversion(recipeOutput, Items.YELLOW_DYE, ESItems.STARLIGHT_TORCHFLOWER.get());
		addSingleConversion(recipeOutput, Items.PURPLE_DYE, ESItems.NIGHTFAN_BUSH.get());
		addSingleConversion(recipeOutput, Items.PINK_DYE, ESItems.PINK_ROSE_BUSH.get());
		addSingleConversion(recipeOutput, Items.GREEN_DYE, ESItems.SWAMP_ROSE.get());
		addSingleConversion(recipeOutput, Items.PINK_DYE, ESItems.WHISPERBLOOM.get());
		addSingleConversion(recipeOutput, Items.ORANGE_DYE, ESItems.WITHERED_STARLIGHT_FLOWER.get());
		addSingleConversion(recipeOutput, Items.PURPLE_DYE, ESItems.DESERT_AMETHYSIA.get());
		addSingleConversion(recipeOutput, Items.PURPLE_DYE, ESItems.WITHERED_DESERT_AMETHYSIA.get());
		addSingleConversion(recipeOutput, Items.ORANGE_DYE, ESItems.SUNSET_THORNBLOOM.get());

		addSingleConversion(recipeOutput, Items.YELLOW_DYE, ESItems.GLADESPIKE.get());
		addSingleConversion(recipeOutput, Items.RED_DYE, ESItems.VIVIDSTALK.get());
		addSingleConversion(recipeOutput, Items.YELLOW_DYE, ESItems.TALL_GLADESPIKE.get());

		// food
		addCookingRecipes(recipeOutput, "smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100);
		addCookingRecipes(recipeOutput, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600);
		addSmelt(recipeOutput, 200, ESItems.LUMINOFISH.get(), ESItems.COOKED_LUMINOFISH.get(), ESItems.LUMINOFISH.get());
		addSmelt(recipeOutput, 200, ESItems.LUMINARIS.get(), ESItems.COOKED_LUMINARIS.get(), ESItems.LUMINARIS.get());
		addSmelt(recipeOutput, 200, ESItems.AURORA_DEER_STEAK.get(), ESItems.COOKED_AURORA_DEER_STEAK.get(), ESItems.AURORA_DEER_STEAK.get());
		addSmelt(recipeOutput, 200, ESItems.RATLIN_MEAT.get(), ESItems.COOKED_RATLIN_MEAT.get(), ESItems.RATLIN_MEAT.get());
		addSmelt(recipeOutput, 200, ESItems.LUNARIS_CACTUS_FRUIT.get(), ESItems.LUNARIS_CACTUS_GEL.get(), ESItems.LUNARIS_CACTUS_FRUIT.get());

		// smelt
		addSmelt(recipeOutput, 200, ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get(), ESItems.GOLEM_STEEL_INGOT.get(), ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get());
		addBlast(recipeOutput, 100, ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get(), ESItems.GOLEM_STEEL_INGOT.get(), ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get());
		addSmelt(recipeOutput, 200, ESItems.RAW_AETHERSENT.get(), ESItems.AETHERSENT_INGOT.get(), ESItems.RAW_AETHERSENT.get());
		addBlast(recipeOutput, 100, ESItems.RAW_AETHERSENT.get(), ESItems.AETHERSENT_INGOT.get(), ESItems.RAW_AETHERSENT.get());
		addSmelt(recipeOutput, 200, ESItems.GRIMSTONE_REDSTONE_ORE.get(), Items.REDSTONE, ESItems.GRIMSTONE_REDSTONE_ORE.get());
		addBlast(recipeOutput, 100, ESItems.GRIMSTONE_REDSTONE_ORE.get(), Items.REDSTONE, ESItems.GRIMSTONE_REDSTONE_ORE.get());
		addSmelt(recipeOutput, 200, ESItems.VOIDSTONE_REDSTONE_ORE.get(), Items.REDSTONE, ESItems.VOIDSTONE_REDSTONE_ORE.get());
		addBlast(recipeOutput, 100, ESItems.VOIDSTONE_REDSTONE_ORE.get(), Items.REDSTONE, ESItems.VOIDSTONE_REDSTONE_ORE.get());

		// magic
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ESItems.ORB_OF_PROPHECY.get())
			.pattern("GCG")
			.pattern("CCC")
			.pattern("GCG")
			.define('C', ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get())
			.define('G', Items.GLASS)
			.unlockedBy("has_item", has(ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get()))
			.save(recipeOutput);
		addShapeless(recipeOutput, ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get(), ESItems.MANA_CRYSTAL.get(), 1, ESItems.TERRA_CRYSTAL.get(), ESItems.WIND_CRYSTAL.get(), ESItems.WATER_CRYSTAL.get(), ESItems.LUNAR_CRYSTAL.get(), ESItems.BLAZE_CRYSTAL.get(), ESItems.LIGHT_CRYSTAL.get());
		SpecialRecipeBuilder.special(category -> new ManaCrystalRecipe(category, ManaType.TERRA, ESItems.TERRA_CRYSTAL.get())).save(recipeOutput, EternalStarlight.id("terra_crystal"));
		SpecialRecipeBuilder.special(category -> new ManaCrystalRecipe(category, ManaType.WIND, ESItems.WIND_CRYSTAL.get())).save(recipeOutput, EternalStarlight.id("wind_crystal"));
		SpecialRecipeBuilder.special(category -> new ManaCrystalRecipe(category, ManaType.WATER, ESItems.WATER_CRYSTAL.get())).save(recipeOutput, EternalStarlight.id("water_crystal"));
		SpecialRecipeBuilder.special(category -> new ManaCrystalRecipe(category, ManaType.LUNAR, ESItems.LUNAR_CRYSTAL.get())).save(recipeOutput, EternalStarlight.id("lunar_crystal"));
		SpecialRecipeBuilder.special(category -> new ManaCrystalRecipe(category, ManaType.BLAZE, ESItems.BLAZE_CRYSTAL.get())).save(recipeOutput, EternalStarlight.id("blaze_crystal"));
		SpecialRecipeBuilder.special(category -> new ManaCrystalRecipe(category, ManaType.LIGHT, ESItems.LIGHT_CRYSTAL.get())).save(recipeOutput, EternalStarlight.id("light_crystal"));

		// misc
		addShapeless(recipeOutput, ESItems.STARLIGHT_MANGROVE_ROOTS.get(), ESItems.MUDDY_STARLIGHT_MANGROVE_ROOTS.get(), 1, ESItems.STARLIGHT_MANGROVE_ROOTS.get(), ESItems.NIGHTFALL_MUD.get());
		addShapeless(recipeOutput, ESItems.NIGHTFALL_MUD.get(), ESItems.PACKED_NIGHTFALL_MUD.get(), 1, ESItems.NIGHTFALL_MUD.get(), ESItems.LUNAR_BERRIES.get());
		nineBlockStorageCustomUnpacking(recipeOutput, RecipeCategory.MISC, ESItems.LUNARIS_CACTUS_GEL.get(), RecipeCategory.BUILDING_BLOCKS, ESItems.LUNARIS_CACTUS_GEL_BLOCK.get(), "lunaris_cactus_gel_block_from_lunaris_cactus_gel", "lunaris_cactus_gel");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ESItems.SEEKING_EYE.get())
			.pattern("FFF")
			.pattern("FPF")
			.pattern("FFF")
			.define('P', Items.ENDER_PEARL)
			.define('F', ESItems.STARLIGHT_FLOWER.get())
			.unlockedBy("has_item", has(ESItems.STARLIGHT_FLOWER.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.CRYSTAL_CROSSBOW.get())
			.pattern("BGB")
			.pattern("STS")
			.pattern(" R ")
			.define('S', Items.STRING)
			.define('T', Items.TRIPWIRE_HOOK)
			.define('G', ESItems.GOLEM_STEEL_INGOT.get())
			.define('B', ESItems.BLUE_STARLIGHT_CRYSTAL_SHARD.get())
			.define('R', ESItems.RED_STARLIGHT_CRYSTAL_SHARD.get())
			.unlockedBy("has_item", has(ESItems.GOLEM_STEEL_INGOT.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.MECHANICAL_CROSSBOW.get())
			.pattern("#&#")
			.pattern("~$~")
			.pattern(" # ")
			.define('~', Items.STRING)
			.define('#', Items.STICK)
			.define('&', ESItems.GOLEM_STEEL_INGOT.get())
			.define('$', Items.TRIPWIRE_HOOK)
			.unlockedBy("has_item", has(ESItems.GOLEM_STEEL_INGOT.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.MOONRING_GREATSWORD.get())
			.pattern("TTT")
			.pattern("TGT")
			.pattern("SSS")
			.define('S', Items.STICK)
			.define('G', ESItems.GOLEM_STEEL_INGOT.get())
			.define('T', ESItems.TENACIOUS_PETAL.get())
			.unlockedBy("has_item", has(ESItems.TENACIOUS_PETAL.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.PETAL_SCYTHE.get())
			.pattern("GTT")
			.pattern("GS ")
			.pattern(" S ")
			.define('S', Items.STICK)
			.define('G', ESItems.GOLEM_STEEL_INGOT.get())
			.define('T', ESItems.TENACIOUS_PETAL.get())
			.unlockedBy("has_item", has(ESItems.TENACIOUS_PETAL.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.CHAIN_OF_SOULS.get())
			.pattern("SP")
			.pattern(" V")
			.pattern(" V")
			.define('S', ESItems.TRAPPED_SOUL.get())
			.define('P', ESItems.TENACIOUS_PETAL.get())
			.define('V', ESItems.TENACIOUS_VINE.get())
			.unlockedBy("has_item", has(ESItems.TENACIOUS_PETAL.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.SONAR_BOMB.get())
			.pattern(" N ")
			.pattern("NGN")
			.pattern(" N ")
			.define('N', ESItems.SWAMP_SILVER_NUGGET.get())
			.define('G', ESItems.SHIVERING_GEL.get())
			.unlockedBy("has_item", has(ESItems.SHIVERING_GEL.get()))
			.save(recipeOutput);
		addShapeless(recipeOutput, ESItems.TRAPPED_SOUL.get(), ESItems.SOULIT_SPECTATOR.get(), 1, ESItems.TRAPPED_SOUL.get(), ESItems.NIGHTFALL_SPIDER_EYE.get());
		addSword(recipeOutput, ESItems.DAGGER_OF_HUNGER.get(), ESItems.TOOTH_OF_HUNGER.get(), Items.STICK);
	}

	private <T extends AbstractCookingRecipe> void addCookingRecipes(RecipeOutput recipeOutput, String name, RecipeSerializer<T> recipeSerializer, AbstractCookingRecipe.Factory<T> factory, int time) {
		simpleCooking(recipeOutput, name, recipeSerializer, factory, time, ESItems.LUMINOFISH.get(), ESItems.COOKED_LUMINOFISH.get(), 0.35F);
		simpleCooking(recipeOutput, name, recipeSerializer, factory, time, ESItems.LUMINARIS.get(), ESItems.COOKED_LUMINARIS.get(), 0.35F);
		simpleCooking(recipeOutput, name, recipeSerializer, factory, time, ESItems.AURORA_DEER_STEAK.get(), ESItems.COOKED_AURORA_DEER_STEAK.get(), 0.35F);
		simpleCooking(recipeOutput, name, recipeSerializer, factory, time, ESItems.RATLIN_MEAT.get(), ESItems.COOKED_RATLIN_MEAT.get(), 0.35F);
		simpleCooking(recipeOutput, name, recipeSerializer, factory, time, ESItems.LUNARIS_CACTUS_FRUIT.get(), ESItems.LUNARIS_CACTUS_GEL.get(), 0.35F);
	}

	private void addWoodRecipes(RecipeOutput recipeOutput) {
		addButton(recipeOutput, ESBlocks.LUNAR_BUTTON.get(), ESBlocks.LUNAR_PLANKS.get());
		addDoor(recipeOutput, ESBlocks.LUNAR_DOOR.get(), ESBlocks.LUNAR_PLANKS.get());
		addFence(recipeOutput, ESBlocks.LUNAR_FENCE.get(), ESBlocks.LUNAR_PLANKS.get());
		addFenceGate(recipeOutput, ESBlocks.LUNAR_FENCE_GATE.get(), ESBlocks.LUNAR_PLANKS.get());
		addPlanks(recipeOutput, ESBlocks.LUNAR_PLANKS.get(), ESTags.Items.LUNAR_LOGS);
		addWood(recipeOutput, ESBlocks.LUNAR_WOOD.get(), ESBlocks.LUNAR_LOG.get());
		addStrippedWood(recipeOutput, ESBlocks.STRIPPED_LUNAR_WOOD.get(), ESBlocks.STRIPPED_LUNAR_LOG.get());
		addPressurePlate(recipeOutput, ESBlocks.LUNAR_PRESSURE_PLATE.get(), ESBlocks.LUNAR_PLANKS.get());
		addSlab(recipeOutput, ESBlocks.LUNAR_SLAB.get(), ESBlocks.LUNAR_PLANKS.get());
		addStairs(recipeOutput, ESBlocks.LUNAR_STAIRS.get(), ESBlocks.LUNAR_PLANKS.get());
		addTrapdoor(recipeOutput, ESBlocks.LUNAR_TRAPDOOR.get(), ESBlocks.LUNAR_PLANKS.get());
		addSign(recipeOutput, ESItems.LUNAR_SIGN.get(), ESBlocks.LUNAR_PLANKS.get());
		hangingSign(recipeOutput, ESItems.LUNAR_HANGING_SIGN.get(), ESBlocks.STRIPPED_LUNAR_LOG.get());
		addBoat(recipeOutput, ESItems.LUNAR_BOAT.get(), ESItems.LUNAR_CHEST_BOAT.get(), ESBlocks.LUNAR_PLANKS.get());

		addButton(recipeOutput, ESBlocks.NORTHLAND_BUTTON.get(), ESBlocks.NORTHLAND_PLANKS.get());
		addDoor(recipeOutput, ESBlocks.NORTHLAND_DOOR.get(), ESBlocks.NORTHLAND_PLANKS.get());
		addFence(recipeOutput, ESBlocks.NORTHLAND_FENCE.get(), ESBlocks.NORTHLAND_PLANKS.get());
		addFenceGate(recipeOutput, ESBlocks.NORTHLAND_FENCE_GATE.get(), ESBlocks.NORTHLAND_PLANKS.get());
		addPlanks(recipeOutput, ESBlocks.NORTHLAND_PLANKS.get(), ESTags.Items.NORTHLAND_LOGS);
		addWood(recipeOutput, ESBlocks.NORTHLAND_WOOD.get(), ESBlocks.NORTHLAND_LOG.get());
		addStrippedWood(recipeOutput, ESBlocks.STRIPPED_NORTHLAND_WOOD.get(), ESBlocks.STRIPPED_NORTHLAND_LOG.get());
		addPressurePlate(recipeOutput, ESBlocks.NORTHLAND_PRESSURE_PLATE.get(), ESBlocks.NORTHLAND_PLANKS.get());
		addSlab(recipeOutput, ESBlocks.NORTHLAND_SLAB.get(), ESBlocks.NORTHLAND_PLANKS.get());
		addStairs(recipeOutput, ESBlocks.NORTHLAND_STAIRS.get(), ESBlocks.NORTHLAND_PLANKS.get());
		addTrapdoor(recipeOutput, ESBlocks.NORTHLAND_TRAPDOOR.get(), ESBlocks.NORTHLAND_PLANKS.get());
		addSign(recipeOutput, ESItems.NORTHLAND_SIGN.get(), ESBlocks.NORTHLAND_PLANKS.get());
		hangingSign(recipeOutput, ESItems.NORTHLAND_HANGING_SIGN.get(), ESBlocks.STRIPPED_NORTHLAND_LOG.get());
		addBoat(recipeOutput, ESItems.NORTHLAND_BOAT.get(), ESItems.NORTHLAND_CHEST_BOAT.get(), ESBlocks.NORTHLAND_PLANKS.get());

		addButton(recipeOutput, ESBlocks.STARLIGHT_MANGROVE_BUTTON.get(), ESBlocks.STARLIGHT_MANGROVE_PLANKS.get());
		addDoor(recipeOutput, ESBlocks.STARLIGHT_MANGROVE_DOOR.get(), ESBlocks.STARLIGHT_MANGROVE_PLANKS.get());
		addFence(recipeOutput, ESBlocks.STARLIGHT_MANGROVE_FENCE.get(), ESBlocks.STARLIGHT_MANGROVE_PLANKS.get());
		addFenceGate(recipeOutput, ESBlocks.STARLIGHT_MANGROVE_FENCE_GATE.get(), ESBlocks.STARLIGHT_MANGROVE_PLANKS.get());
		addPlanks(recipeOutput, ESBlocks.STARLIGHT_MANGROVE_PLANKS.get(), ESTags.Items.STARLIGHT_MANGROVE_LOGS);
		addWood(recipeOutput, ESBlocks.STARLIGHT_MANGROVE_WOOD.get(), ESBlocks.STARLIGHT_MANGROVE_LOG.get());
		addStrippedWood(recipeOutput, ESBlocks.STRIPPED_STARLIGHT_MANGROVE_WOOD.get(), ESBlocks.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
		addPressurePlate(recipeOutput, ESBlocks.STARLIGHT_MANGROVE_PRESSURE_PLATE.get(), ESBlocks.STARLIGHT_MANGROVE_PLANKS.get());
		addSlab(recipeOutput, ESBlocks.STARLIGHT_MANGROVE_SLAB.get(), ESBlocks.STARLIGHT_MANGROVE_PLANKS.get());
		addStairs(recipeOutput, ESBlocks.STARLIGHT_MANGROVE_STAIRS.get(), ESBlocks.STARLIGHT_MANGROVE_PLANKS.get());
		addTrapdoor(recipeOutput, ESBlocks.STARLIGHT_MANGROVE_TRAPDOOR.get(), ESBlocks.STARLIGHT_MANGROVE_PLANKS.get());
		addSign(recipeOutput, ESItems.STARLIGHT_MANGROVE_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_PLANKS.get());
		hangingSign(recipeOutput, ESItems.STARLIGHT_MANGROVE_HANGING_SIGN.get(), ESBlocks.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
		addBoat(recipeOutput, ESItems.STARLIGHT_MANGROVE_BOAT.get(), ESItems.STARLIGHT_MANGROVE_CHEST_BOAT.get(), ESBlocks.STARLIGHT_MANGROVE_PLANKS.get());

		addButton(recipeOutput, ESBlocks.SCARLET_BUTTON.get(), ESBlocks.SCARLET_PLANKS.get());
		addDoor(recipeOutput, ESBlocks.SCARLET_DOOR.get(), ESBlocks.SCARLET_PLANKS.get());
		addFence(recipeOutput, ESBlocks.SCARLET_FENCE.get(), ESBlocks.SCARLET_PLANKS.get());
		addFenceGate(recipeOutput, ESBlocks.SCARLET_FENCE_GATE.get(), ESBlocks.SCARLET_PLANKS.get());
		addPlanks(recipeOutput, ESBlocks.SCARLET_PLANKS.get(), ESTags.Items.SCARLET_LOGS);
		addWood(recipeOutput, ESBlocks.SCARLET_WOOD.get(), ESBlocks.SCARLET_LOG.get());
		addStrippedWood(recipeOutput, ESBlocks.STRIPPED_SCARLET_WOOD.get(), ESBlocks.STRIPPED_SCARLET_LOG.get());
		addPressurePlate(recipeOutput, ESBlocks.SCARLET_PRESSURE_PLATE.get(), ESBlocks.SCARLET_PLANKS.get());
		addSlab(recipeOutput, ESBlocks.SCARLET_SLAB.get(), ESBlocks.SCARLET_PLANKS.get());
		addStairs(recipeOutput, ESBlocks.SCARLET_STAIRS.get(), ESBlocks.SCARLET_PLANKS.get());
		addTrapdoor(recipeOutput, ESBlocks.SCARLET_TRAPDOOR.get(), ESBlocks.SCARLET_PLANKS.get());
		addSign(recipeOutput, ESItems.SCARLET_SIGN.get(), ESBlocks.SCARLET_PLANKS.get());
		hangingSign(recipeOutput, ESItems.SCARLET_HANGING_SIGN.get(), ESBlocks.STRIPPED_SCARLET_LOG.get());
		addBoat(recipeOutput, ESItems.SCARLET_BOAT.get(), ESItems.SCARLET_CHEST_BOAT.get(), ESBlocks.SCARLET_PLANKS.get());

		addButton(recipeOutput, ESBlocks.TORREYA_BUTTON.get(), ESBlocks.TORREYA_PLANKS.get());
		addDoor(recipeOutput, ESBlocks.TORREYA_DOOR.get(), ESBlocks.TORREYA_PLANKS.get());
		addFence(recipeOutput, ESBlocks.TORREYA_FENCE.get(), ESBlocks.TORREYA_PLANKS.get());
		addFenceGate(recipeOutput, ESBlocks.TORREYA_FENCE_GATE.get(), ESBlocks.TORREYA_PLANKS.get());
		addPlanks(recipeOutput, ESBlocks.TORREYA_PLANKS.get(), ESTags.Items.TORREYA_LOGS);
		addWood(recipeOutput, ESBlocks.TORREYA_WOOD.get(), ESBlocks.TORREYA_LOG.get());
		addStrippedWood(recipeOutput, ESBlocks.STRIPPED_TORREYA_WOOD.get(), ESBlocks.STRIPPED_TORREYA_LOG.get());
		addPressurePlate(recipeOutput, ESBlocks.TORREYA_PRESSURE_PLATE.get(), ESBlocks.TORREYA_PLANKS.get());
		addSlab(recipeOutput, ESBlocks.TORREYA_SLAB.get(), ESBlocks.TORREYA_PLANKS.get());
		addStairs(recipeOutput, ESBlocks.TORREYA_STAIRS.get(), ESBlocks.TORREYA_PLANKS.get());
		addTrapdoor(recipeOutput, ESBlocks.TORREYA_TRAPDOOR.get(), ESBlocks.TORREYA_PLANKS.get());
		addSign(recipeOutput, ESItems.TORREYA_SIGN.get(), ESBlocks.TORREYA_PLANKS.get());
		hangingSign(recipeOutput, ESItems.TORREYA_HANGING_SIGN.get(), ESBlocks.STRIPPED_TORREYA_LOG.get());
		addBoat(recipeOutput, ESItems.TORREYA_BOAT.get(), ESItems.TORREYA_CHEST_BOAT.get(), ESBlocks.TORREYA_PLANKS.get());
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ESBlocks.TORREYA_CAMPFIRE.get())
			.pattern(" S ")
			.pattern("SAS")
			.pattern("LLL")
			.define('L', ESTags.Items.TORREYA_LOGS)
			.define('S', Items.STICK)
			.define('A', ESItems.RAW_AMARAMBER.get())
			.unlockedBy("has_stick", has(Items.STICK)).unlockedBy("has_fuel", has(ESItems.RAW_AMARAMBER.get())).save(recipeOutput);
	}

	private void addStoneRecipes(RecipeOutput recipeOutput) {
		addSmelt(recipeOutput, 200, ESBlocks.COBBLED_GRIMSTONE.get(), ESBlocks.GRIMSTONE.get(), ESBlocks.COBBLED_GRIMSTONE.get());
		addStoneCompress(recipeOutput, ESBlocks.POLISHED_GRIMSTONE.get(), ESBlocks.GRIMSTONE.get());
		addStoneCompress(recipeOutput, ESBlocks.GRIMSTONE_BRICKS.get(), ESBlocks.POLISHED_GRIMSTONE.get());
		chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_GRIMSTONE.get(), ESBlocks.GRIMSTONE_BRICK_SLAB.get());
		addStoneCompress(recipeOutput, ESBlocks.GRIMSTONE_TILES.get(), ESBlocks.GRIMSTONE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_GRIMSTONE.get(), ESBlocks.COBBLED_GRIMSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_GRIMSTONE.get(), ESBlocks.GRIMSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_GRIMSTONE.get(), ESBlocks.GRIMSTONE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_BRICKS.get(), ESBlocks.POLISHED_GRIMSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_TILES.get(), ESBlocks.GRIMSTONE_BRICKS.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.COBBLED_GRIMSTONE_WALL.get(), ESBlocks.COBBLED_GRIMSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.COBBLED_GRIMSTONE_WALL.get(), ESBlocks.COBBLED_GRIMSTONE.get());
		addStairs(recipeOutput, ESBlocks.COBBLED_GRIMSTONE_STAIRS.get(), ESBlocks.COBBLED_GRIMSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.COBBLED_GRIMSTONE_STAIRS.get(), ESBlocks.COBBLED_GRIMSTONE.get());
		addSlab(recipeOutput, ESBlocks.COBBLED_GRIMSTONE_SLAB.get(), ESBlocks.COBBLED_GRIMSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.COBBLED_GRIMSTONE_SLAB.get(), ESBlocks.COBBLED_GRIMSTONE.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_BRICK_WALL.get(), ESBlocks.GRIMSTONE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_BRICK_WALL.get(), ESBlocks.POLISHED_GRIMSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_BRICK_WALL.get(), ESBlocks.GRIMSTONE_BRICKS.get());
		addStairs(recipeOutput, ESBlocks.GRIMSTONE_BRICK_STAIRS.get(), ESBlocks.GRIMSTONE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_BRICK_STAIRS.get(), ESBlocks.POLISHED_GRIMSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_BRICK_STAIRS.get(), ESBlocks.GRIMSTONE_BRICKS.get());
		addSlab(recipeOutput, ESBlocks.GRIMSTONE_BRICK_SLAB.get(), ESBlocks.GRIMSTONE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_BRICK_SLAB.get(), ESBlocks.POLISHED_GRIMSTONE.get(), 2);
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_BRICK_SLAB.get(), ESBlocks.GRIMSTONE_BRICKS.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_GRIMSTONE_WALL.get(), ESBlocks.POLISHED_GRIMSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_GRIMSTONE_WALL.get(), ESBlocks.POLISHED_GRIMSTONE.get());
		addStairs(recipeOutput, ESBlocks.POLISHED_GRIMSTONE_STAIRS.get(), ESBlocks.POLISHED_GRIMSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_GRIMSTONE_STAIRS.get(), ESBlocks.POLISHED_GRIMSTONE.get());
		addSlab(recipeOutput, ESBlocks.POLISHED_GRIMSTONE_SLAB.get(), ESBlocks.POLISHED_GRIMSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_GRIMSTONE_SLAB.get(), ESBlocks.POLISHED_GRIMSTONE.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_TILE_WALL.get(), ESBlocks.GRIMSTONE_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_TILE_WALL.get(), ESBlocks.GRIMSTONE_TILES.get());
		addStairs(recipeOutput, ESBlocks.GRIMSTONE_TILE_STAIRS.get(), ESBlocks.GRIMSTONE_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_TILE_STAIRS.get(), ESBlocks.GRIMSTONE_TILES.get());
		addSlab(recipeOutput, ESBlocks.GRIMSTONE_TILE_SLAB.get(), ESBlocks.GRIMSTONE_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GRIMSTONE_TILE_SLAB.get(), ESBlocks.GRIMSTONE_TILES.get(), 2);

		addSmelt(recipeOutput, 200, ESBlocks.COBBLED_VOIDSTONE.get(), ESBlocks.VOIDSTONE.get(), ESBlocks.COBBLED_VOIDSTONE.get());
		addStoneCompress(recipeOutput, ESBlocks.POLISHED_VOIDSTONE.get(), ESBlocks.VOIDSTONE.get());
		addStoneCompress(recipeOutput, ESBlocks.VOIDSTONE_BRICKS.get(), ESBlocks.POLISHED_VOIDSTONE.get());
		chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_VOIDSTONE.get(), ESBlocks.VOIDSTONE_BRICK_SLAB.get());
		addStoneCompress(recipeOutput, ESBlocks.VOIDSTONE_TILES.get(), ESBlocks.VOIDSTONE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_VOIDSTONE.get(), ESBlocks.COBBLED_VOIDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_VOIDSTONE.get(), ESBlocks.VOIDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_VOIDSTONE.get(), ESBlocks.VOIDSTONE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_BRICKS.get(), ESBlocks.POLISHED_VOIDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_TILES.get(), ESBlocks.VOIDSTONE_BRICKS.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.COBBLED_VOIDSTONE_WALL.get(), ESBlocks.COBBLED_VOIDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.COBBLED_VOIDSTONE_WALL.get(), ESBlocks.COBBLED_VOIDSTONE.get());
		addStairs(recipeOutput, ESBlocks.COBBLED_VOIDSTONE_STAIRS.get(), ESBlocks.COBBLED_VOIDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.COBBLED_VOIDSTONE_STAIRS.get(), ESBlocks.COBBLED_VOIDSTONE.get());
		addSlab(recipeOutput, ESBlocks.COBBLED_VOIDSTONE_SLAB.get(), ESBlocks.COBBLED_VOIDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.COBBLED_VOIDSTONE_SLAB.get(), ESBlocks.COBBLED_VOIDSTONE.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_BRICK_WALL.get(), ESBlocks.VOIDSTONE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_BRICK_WALL.get(), ESBlocks.POLISHED_VOIDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_BRICK_WALL.get(), ESBlocks.VOIDSTONE_BRICKS.get());
		addStairs(recipeOutput, ESBlocks.VOIDSTONE_BRICK_STAIRS.get(), ESBlocks.VOIDSTONE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_BRICK_STAIRS.get(), ESBlocks.POLISHED_VOIDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_BRICK_STAIRS.get(), ESBlocks.VOIDSTONE_BRICKS.get());
		addSlab(recipeOutput, ESBlocks.VOIDSTONE_BRICK_SLAB.get(), ESBlocks.VOIDSTONE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_BRICK_SLAB.get(), ESBlocks.POLISHED_VOIDSTONE.get(), 2);
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_BRICK_SLAB.get(), ESBlocks.VOIDSTONE_BRICKS.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_VOIDSTONE_WALL.get(), ESBlocks.POLISHED_VOIDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_VOIDSTONE_WALL.get(), ESBlocks.POLISHED_VOIDSTONE.get());
		addStairs(recipeOutput, ESBlocks.POLISHED_VOIDSTONE_STAIRS.get(), ESBlocks.POLISHED_VOIDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_VOIDSTONE_STAIRS.get(), ESBlocks.POLISHED_VOIDSTONE.get());
		addSlab(recipeOutput, ESBlocks.POLISHED_VOIDSTONE_SLAB.get(), ESBlocks.POLISHED_VOIDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_VOIDSTONE_SLAB.get(), ESBlocks.POLISHED_VOIDSTONE.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_TILE_WALL.get(), ESBlocks.VOIDSTONE_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_TILE_WALL.get(), ESBlocks.VOIDSTONE_TILES.get());
		addStairs(recipeOutput, ESBlocks.VOIDSTONE_TILE_STAIRS.get(), ESBlocks.VOIDSTONE_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_TILE_STAIRS.get(), ESBlocks.VOIDSTONE_TILES.get());
		addSlab(recipeOutput, ESBlocks.VOIDSTONE_TILE_SLAB.get(), ESBlocks.VOIDSTONE_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.VOIDSTONE_TILE_SLAB.get(), ESBlocks.VOIDSTONE_TILES.get(), 2);

		addStoneCompress(recipeOutput, ESBlocks.ETERNAL_ICE_BRICKS.get(), ESBlocks.ETERNAL_ICE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.ETERNAL_ICE_BRICKS.get(), ESBlocks.ETERNAL_ICE.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.ETERNAL_ICE_BRICK_WALL.get(), ESBlocks.ETERNAL_ICE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.ETERNAL_ICE_BRICK_WALL.get(), ESBlocks.ETERNAL_ICE_BRICKS.get());
		addStairs(recipeOutput, ESBlocks.ETERNAL_ICE_BRICK_STAIRS.get(), ESBlocks.ETERNAL_ICE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.ETERNAL_ICE_BRICK_STAIRS.get(), ESBlocks.ETERNAL_ICE_BRICKS.get());
		addSlab(recipeOutput, ESBlocks.ETERNAL_ICE_BRICK_SLAB.get(), ESBlocks.ETERNAL_ICE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.ETERNAL_ICE_BRICK_SLAB.get(), ESBlocks.ETERNAL_ICE_BRICKS.get(), 2);

		addShapeless(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESItems.RAW_AETHERSENT.get(), ESBlocks.NEBULAITE.get(), 1, ESBlocks.VOIDSTONE.get(), ESBlocks.VOIDSTONE.get(), ESItems.RAW_AETHERSENT.get(), ESItems.RAW_AETHERSENT.get());
		addStoneCompress(recipeOutput, ESBlocks.NEBULAITE_BRICKS.get(), ESBlocks.NEBULAITE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.NEBULAITE_BRICKS.get(), ESBlocks.NEBULAITE.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.NEBULAITE_BRICK_WALL.get(), ESBlocks.NEBULAITE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.NEBULAITE_BRICK_WALL.get(), ESBlocks.NEBULAITE_BRICKS.get());
		addStairs(recipeOutput, ESBlocks.NEBULAITE_BRICK_STAIRS.get(), ESBlocks.NEBULAITE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.NEBULAITE_BRICK_STAIRS.get(), ESBlocks.NEBULAITE_BRICKS.get());
		addSlab(recipeOutput, ESBlocks.NEBULAITE_BRICK_SLAB.get(), ESBlocks.NEBULAITE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.NEBULAITE_BRICK_SLAB.get(), ESBlocks.NEBULAITE_BRICKS.get(), 2);
		chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_NEBULAITE_BRICKS.get(), ESBlocks.NEBULAITE_BRICK_SLAB.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_NEBULAITE_BRICKS.get(), ESBlocks.NEBULAITE_BRICKS.get());

		addStoneCompress(recipeOutput, ESBlocks.POLISHED_ABYSSLATE.get(), ESBlocks.ABYSSLATE.get());
		addStoneCompress(recipeOutput, ESBlocks.POLISHED_ABYSSLATE_BRICKS.get(), ESBlocks.POLISHED_ABYSSLATE.get());
		chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_POLISHED_ABYSSLATE.get(), ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE.get(), ESBlocks.ABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_POLISHED_ABYSSLATE.get(), ESBlocks.POLISHED_ABYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_BRICKS.get(), ESBlocks.POLISHED_ABYSSLATE.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_BRICK_WALL.get(), ESBlocks.POLISHED_ABYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_BRICK_WALL.get(), ESBlocks.POLISHED_ABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_BRICK_WALL.get(), ESBlocks.POLISHED_ABYSSLATE_BRICKS.get());
		addStairs(recipeOutput, ESBlocks.POLISHED_ABYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_ABYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_ABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_ABYSSLATE_BRICKS.get());
		addSlab(recipeOutput, ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_ABYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_ABYSSLATE.get(), 2);
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_ABYSSLATE_BRICKS.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_WALL.get(), ESBlocks.POLISHED_ABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_WALL.get(), ESBlocks.POLISHED_ABYSSLATE.get());
		addStairs(recipeOutput, ESBlocks.POLISHED_ABYSSLATE_STAIRS.get(), ESBlocks.POLISHED_ABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_STAIRS.get(), ESBlocks.POLISHED_ABYSSLATE.get());
		addSlab(recipeOutput, ESBlocks.POLISHED_ABYSSLATE_SLAB.get(), ESBlocks.POLISHED_ABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_ABYSSLATE_SLAB.get(), ESBlocks.POLISHED_ABYSSLATE.get(), 2);

		addStoneCompress(recipeOutput, ESBlocks.POLISHED_THERMABYSSLATE.get(), ESBlocks.THERMABYSSLATE.get());
		addStoneCompress(recipeOutput, ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get(), ESBlocks.POLISHED_THERMABYSSLATE.get());
		chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_POLISHED_THERMABYSSLATE.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE.get(), ESBlocks.THERMABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_POLISHED_THERMABYSSLATE.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get(), ESBlocks.POLISHED_THERMABYSSLATE.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_BRICK_WALL.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_BRICK_WALL.get(), ESBlocks.POLISHED_THERMABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_BRICK_WALL.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get());
		addStairs(recipeOutput, ESBlocks.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_THERMABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get());
		addSlab(recipeOutput, ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_THERMABYSSLATE.get(), 2);
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_WALL.get(), ESBlocks.POLISHED_THERMABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_WALL.get(), ESBlocks.POLISHED_THERMABYSSLATE.get());
		addStairs(recipeOutput, ESBlocks.POLISHED_THERMABYSSLATE_STAIRS.get(), ESBlocks.POLISHED_THERMABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_STAIRS.get(), ESBlocks.POLISHED_THERMABYSSLATE.get());
		addSlab(recipeOutput, ESBlocks.POLISHED_THERMABYSSLATE_SLAB.get(), ESBlocks.POLISHED_THERMABYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_THERMABYSSLATE_SLAB.get(), ESBlocks.POLISHED_THERMABYSSLATE.get(), 2);

		addStoneCompress(recipeOutput, ESBlocks.POLISHED_CRYOBYSSLATE.get(), ESBlocks.CRYOBYSSLATE.get());
		addStoneCompress(recipeOutput, ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get(), ESBlocks.POLISHED_CRYOBYSSLATE.get());
		chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_POLISHED_CRYOBYSSLATE.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE.get(), ESBlocks.CRYOBYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_POLISHED_CRYOBYSSLATE.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get(), ESBlocks.POLISHED_CRYOBYSSLATE.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(), ESBlocks.POLISHED_CRYOBYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get());
		addStairs(recipeOutput, ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_CRYOBYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get());
		addSlab(recipeOutput, ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_CRYOBYSSLATE.get(), 2);
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_WALL.get(), ESBlocks.POLISHED_CRYOBYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_WALL.get(), ESBlocks.POLISHED_CRYOBYSSLATE.get());
		addStairs(recipeOutput, ESBlocks.POLISHED_CRYOBYSSLATE_STAIRS.get(), ESBlocks.POLISHED_CRYOBYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_STAIRS.get(), ESBlocks.POLISHED_CRYOBYSSLATE.get());
		addSlab(recipeOutput, ESBlocks.POLISHED_CRYOBYSSLATE_SLAB.get(), ESBlocks.POLISHED_CRYOBYSSLATE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_CRYOBYSSLATE_SLAB.get(), ESBlocks.POLISHED_CRYOBYSSLATE.get(), 2);

		addStoneCompress(recipeOutput, ESBlocks.POLISHED_DOOMEDEN_BRICKS.get(), ESBlocks.DOOMEDEN_BRICKS.get());
		addStoneCompress(recipeOutput, ESBlocks.DOOMEDEN_TILES.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
		chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_POLISHED_DOOMEDEN_BRICKS.get(), ESBlocks.DOOMEDEN_BRICK_SLAB.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_DOOMEDEN_BRICKS.get(), ESBlocks.DOOMEDEN_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_POLISHED_DOOMEDEN_BRICKS.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_TILES.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_BRICK_WALL.get(), ESBlocks.DOOMEDEN_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_BRICK_WALL.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_BRICK_WALL.get(), ESBlocks.DOOMEDEN_BRICKS.get());
		addStairs(recipeOutput, ESBlocks.DOOMEDEN_BRICK_STAIRS.get(), ESBlocks.DOOMEDEN_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_BRICK_STAIRS.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_BRICK_STAIRS.get(), ESBlocks.DOOMEDEN_BRICKS.get());
		addSlab(recipeOutput, ESBlocks.DOOMEDEN_BRICK_SLAB.get(), ESBlocks.DOOMEDEN_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_BRICK_SLAB.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get(), 2);
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_BRICK_SLAB.get(), ESBlocks.DOOMEDEN_BRICKS.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_DOOMEDEN_BRICK_WALL.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_DOOMEDEN_BRICK_WALL.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
		addStairs(recipeOutput, ESBlocks.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
		addSlab(recipeOutput, ESBlocks.POLISHED_DOOMEDEN_BRICK_SLAB.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_DOOMEDEN_BRICK_SLAB.get(), ESBlocks.POLISHED_DOOMEDEN_BRICKS.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_TILE_WALL.get(), ESBlocks.DOOMEDEN_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_TILE_WALL.get(), ESBlocks.DOOMEDEN_TILES.get());
		addStairs(recipeOutput, ESBlocks.DOOMEDEN_TILE_STAIRS.get(), ESBlocks.DOOMEDEN_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_TILE_STAIRS.get(), ESBlocks.DOOMEDEN_TILES.get());
		addSlab(recipeOutput, ESBlocks.DOOMEDEN_TILE_SLAB.get(), ESBlocks.DOOMEDEN_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMEDEN_TILE_SLAB.get(), ESBlocks.DOOMEDEN_TILES.get(), 2);
		addShapeless(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DOOMED_TORCH.get(), ESBlocks.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get(), 1, ESBlocks.DOOMED_TORCH.get(), ESBlocks.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());

		nineBlockStorageCustomUnpacking(recipeOutput, RecipeCategory.MISC, ESItems.THIOQUARTZ_SHARD.get(), RecipeCategory.BUILDING_BLOCKS, ESItems.THIOQUARTZ_BLOCK.get(), "thioquartz_block_from_thioquartz_shard", "athioquartz_shard");
		addStoneCompress(recipeOutput, ESBlocks.POLISHED_TOXITE.get(), ESBlocks.TOXITE.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.TOXITE_WALL.get(), ESBlocks.TOXITE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.TOXITE_WALL.get(), ESBlocks.TOXITE.get());
		addStairs(recipeOutput, ESBlocks.TOXITE_STAIRS.get(), ESBlocks.TOXITE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.TOXITE_STAIRS.get(), ESBlocks.TOXITE.get());
		addSlab(recipeOutput, ESBlocks.TOXITE_SLAB.get(), ESBlocks.TOXITE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.TOXITE_SLAB.get(), ESBlocks.TOXITE.get(), 2);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_TOXITE_WALL.get(), ESBlocks.POLISHED_TOXITE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_TOXITE_WALL.get(), ESBlocks.POLISHED_TOXITE.get());
		addStairs(recipeOutput, ESBlocks.POLISHED_TOXITE_STAIRS.get(), ESBlocks.POLISHED_TOXITE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_TOXITE_STAIRS.get(), ESBlocks.POLISHED_TOXITE.get());
		addSlab(recipeOutput, ESBlocks.POLISHED_TOXITE_SLAB.get(), ESBlocks.POLISHED_TOXITE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.POLISHED_TOXITE_SLAB.get(), ESBlocks.POLISHED_TOXITE.get(), 2);

		addStoneCompress(recipeOutput, ESBlocks.NIGHTFALL_MUD_BRICKS.get(), ESBlocks.PACKED_NIGHTFALL_MUD.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.NIGHTFALL_MUD_BRICK_WALL.get(), ESBlocks.NIGHTFALL_MUD_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.NIGHTFALL_MUD_BRICK_WALL.get(), ESBlocks.NIGHTFALL_MUD_BRICKS.get());
		addStairs(recipeOutput, ESBlocks.NIGHTFALL_MUD_BRICK_STAIRS.get(), ESBlocks.NIGHTFALL_MUD_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.NIGHTFALL_MUD_BRICK_STAIRS.get(), ESBlocks.NIGHTFALL_MUD_BRICKS.get());
		addSlab(recipeOutput, ESBlocks.NIGHTFALL_MUD_BRICK_SLAB.get(), ESBlocks.NIGHTFALL_MUD_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.NIGHTFALL_MUD_BRICK_SLAB.get(), ESBlocks.NIGHTFALL_MUD_BRICKS.get(), 2);

		addStoneCompress(recipeOutput, ESBlocks.TWILIGHT_SANDSTONE.get(), ESBlocks.TWILIGHT_SAND.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.TWILIGHT_SANDSTONE_WALL.get(), ESBlocks.TWILIGHT_SANDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.TWILIGHT_SANDSTONE_WALL.get(), ESBlocks.TWILIGHT_SANDSTONE.get());
		addStairs(recipeOutput, ESBlocks.TWILIGHT_SANDSTONE_STAIRS.get(), ESBlocks.TWILIGHT_SANDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.TWILIGHT_SANDSTONE_STAIRS.get(), ESBlocks.TWILIGHT_SANDSTONE.get());
		addSlab(recipeOutput, ESBlocks.TWILIGHT_SANDSTONE_SLAB.get(), ESBlocks.TWILIGHT_SANDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.TWILIGHT_SANDSTONE_SLAB.get(), ESBlocks.TWILIGHT_SANDSTONE.get(), 2);
		addStoneCompress(recipeOutput, ESBlocks.CUT_TWILIGHT_SANDSTONE.get(), ESBlocks.TWILIGHT_SANDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CUT_TWILIGHT_SANDSTONE.get(), ESBlocks.TWILIGHT_SANDSTONE.get());
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CUT_TWILIGHT_SANDSTONE_WALL.get(), ESBlocks.CUT_TWILIGHT_SANDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CUT_TWILIGHT_SANDSTONE_WALL.get(), ESBlocks.CUT_TWILIGHT_SANDSTONE.get());
		addStairs(recipeOutput, ESBlocks.CUT_TWILIGHT_SANDSTONE_STAIRS.get(), ESBlocks.CUT_TWILIGHT_SANDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CUT_TWILIGHT_SANDSTONE_STAIRS.get(), ESBlocks.CUT_TWILIGHT_SANDSTONE.get());
		addSlab(recipeOutput, ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get(), ESBlocks.CUT_TWILIGHT_SANDSTONE.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get(), ESBlocks.CUT_TWILIGHT_SANDSTONE.get(), 2);
		chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get(), ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get(), ESBlocks.CUT_TWILIGHT_SANDSTONE.get());

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ESItems.DUSTED_BRICKS.get())
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', ESItems.DUSTED_SHARD.get())
			.unlockedBy("has_item", has(ESItems.DUSTED_SHARD.get()))
			.save(recipeOutput);
		wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DUSTED_BRICK_WALL.get(), ESBlocks.DUSTED_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DUSTED_BRICK_WALL.get(), ESBlocks.DUSTED_BRICKS.get());
		addStairs(recipeOutput, ESBlocks.DUSTED_BRICK_STAIRS.get(), ESBlocks.DUSTED_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DUSTED_BRICK_STAIRS.get(), ESBlocks.DUSTED_BRICKS.get());
		addSlab(recipeOutput, ESBlocks.DUSTED_BRICK_SLAB.get(), ESBlocks.DUSTED_BRICKS.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.DUSTED_BRICK_SLAB.get(), ESBlocks.DUSTED_BRICKS.get(), 2);

		nineBlockStorageCustomUnpacking(recipeOutput, RecipeCategory.MISC, ESItems.GOLEM_STEEL_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ESItems.GOLEM_STEEL_BLOCK.get(), "golem_steel_ingot_from_golem_steel_block", "golem_steel_ingot");
		nineBlockStorageCustomUnpacking(recipeOutput, RecipeCategory.MISC, ESItems.OXIDIZED_GOLEM_STEEL_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ESItems.OXIDIZED_GOLEM_STEEL_BLOCK.get(), "oxidized_golem_steel_ingot_from_oxidized_golem_steel_block", "oxidized_golem_steel_ingot");
		addStoneCompress(recipeOutput, ESBlocks.GOLEM_STEEL_TILES.get(), ESBlocks.GOLEM_STEEL_BLOCK.get());
		chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get(), ESBlocks.GOLEM_STEEL_SLAB.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get(), ESBlocks.GOLEM_STEEL_BLOCK.get());
		addStairs(recipeOutput, ESBlocks.GOLEM_STEEL_STAIRS.get(), ESBlocks.GOLEM_STEEL_BLOCK.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GOLEM_STEEL_STAIRS.get(), ESBlocks.GOLEM_STEEL_BLOCK.get());
		addSlab(recipeOutput, ESBlocks.GOLEM_STEEL_SLAB.get(), ESBlocks.GOLEM_STEEL_BLOCK.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GOLEM_STEEL_SLAB.get(), ESBlocks.GOLEM_STEEL_BLOCK.get(), 2);
		addStairs(recipeOutput, ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(), ESBlocks.GOLEM_STEEL_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(), ESBlocks.GOLEM_STEEL_TILES.get());
		addSlab(recipeOutput, ESBlocks.GOLEM_STEEL_TILE_SLAB.get(), ESBlocks.GOLEM_STEEL_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.GOLEM_STEEL_TILE_SLAB.get(), ESBlocks.GOLEM_STEEL_TILES.get(), 2);
		addStoneCompress(recipeOutput, ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get());
		chiseled(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get());
		addStairs(recipeOutput, ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get());
		addSlab(recipeOutput, ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get(), 2);
		addStairs(recipeOutput, ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get());
		addSlab(recipeOutput, ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get(), 2);

		nineBlockStorageCustomUnpacking(recipeOutput, RecipeCategory.MISC, ESItems.TENACIOUS_PETAL.get(), RecipeCategory.BUILDING_BLOCKS, ESItems.LUNAR_MOSAIC.get(), "tenacious_petal_from_lunar_mosaic", "tenacious_petal");
		addStairs(recipeOutput, ESBlocks.LUNAR_MOSAIC_STAIRS.get(), ESBlocks.LUNAR_MOSAIC.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.LUNAR_MOSAIC_STAIRS.get(), ESBlocks.LUNAR_MOSAIC.get());
		addSlab(recipeOutput, ESBlocks.LUNAR_MOSAIC_SLAB.get(), ESBlocks.LUNAR_MOSAIC.get());
		stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ESBlocks.LUNAR_MOSAIC_SLAB.get(), ESBlocks.LUNAR_MOSAIC.get(), 2);
		addFence(recipeOutput, ESBlocks.LUNAR_MOSAIC_FENCE.get(), ESBlocks.LUNAR_MOSAIC.get());
		addFenceGate(recipeOutput, ESBlocks.LUNAR_MOSAIC_FENCE_GATE.get(), ESBlocks.LUNAR_MOSAIC.get());
		customCarpet(recipeOutput, ESBlocks.LUNAR_MAT.get(), ESBlocks.LUNAR_MOSAIC.get());
	}

	private void addAlchemistArmorRecipes(RecipeOutput recipeOutput) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.ALCHEMIST_MASK.get())
			.pattern("###")
			.pattern("S S")
			.define('#', Items.LEATHER)
			.define('S', ESItems.THIOQUARTZ_SHARD.get())
			.unlockedBy("has_item", has(ESItems.THIOQUARTZ_SHARD.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.ALCHEMIST_ROBE.get())
			.pattern("S S")
			.pattern("#S#")
			.pattern("#S#")
			.define('#', Items.LEATHER)
			.define('S', ESItems.THIOQUARTZ_SHARD.get())
			.unlockedBy("has_item", has(ESItems.THIOQUARTZ_SHARD.get()))
			.save(recipeOutput);
	}

	private void addAetherSentRecipes(RecipeOutput recipeOutput) {
		nineBlockStorageCustomUnpacking(recipeOutput, RecipeCategory.MISC, ESItems.RAW_AETHERSENT.get(), RecipeCategory.BUILDING_BLOCKS, ESItems.RAW_AETHERSENT_BLOCK.get(), "raw_aethersent_from_raw_aethersent_block", "raw_aethersent");
		nineBlockStorageCustomUnpacking(recipeOutput, RecipeCategory.MISC, ESItems.AETHERSENT_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ESItems.AETHERSENT_BLOCK.get(), "aethersent_ingot_from_aethersent_block", "aethersent_ingot");
		nineBlockStorageCustomPacking(recipeOutput, RecipeCategory.MISC, ESItems.AETHERSENT_NUGGET.get(), RecipeCategory.MISC, ESItems.AETHERSENT_INGOT.get(), "aethersent_ingot_from_nuggets", "aethersent_ingot");
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.AETHERSENT_HOOD.get())
			.pattern("###")
			.pattern("A A")
			.define('#', Items.LEATHER)
			.define('A', ESItems.AETHERSENT_INGOT.get())
			.unlockedBy("has_item", has(ESItems.AETHERSENT_INGOT.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.AETHERSENT_CAPE.get())
			.pattern("A A")
			.pattern("#A#")
			.pattern("###")
			.define('#', Items.LEATHER)
			.define('A', ESItems.AETHERSENT_INGOT.get())
			.unlockedBy("has_item", has(ESItems.AETHERSENT_INGOT.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.AETHERSENT_BOTTOMS.get())
			.pattern("###")
			.pattern("A A")
			.pattern("# #")
			.define('#', Items.LEATHER)
			.define('A', ESItems.AETHERSENT_INGOT.get())
			.unlockedBy("has_item", has(ESItems.AETHERSENT_INGOT.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.AETHERSENT_BOOTS.get())
			.pattern("A A")
			.pattern("# #")
			.define('#', Items.LEATHER)
			.define('A', ESItems.AETHERSENT_INGOT.get())
			.unlockedBy("has_item", has(ESItems.AETHERSENT_INGOT.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.STARFALL_LONGBOW.get())
			.pattern(" AS")
			.pattern("A S")
			.pattern(" AS")
			.define('S', Items.STRING)
			.define('A', ESItems.AETHERSENT_INGOT.get())
			.unlockedBy("has_item", has(ESItems.AETHERSENT_INGOT.get()))
			.save(recipeOutput);
		addSword(recipeOutput, ESItems.RAGE_OF_STARS.get(), ESItems.AETHERSENT_INGOT.get(), Items.STICK);
	}

	private void addSwampSilverRecipes(RecipeOutput recipeOutput) {
		nineBlockStorageCustomUnpacking(recipeOutput, RecipeCategory.MISC, ESItems.SWAMP_SILVER_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ESItems.SWAMP_SILVER_BLOCK.get(), "swamp_silver_ingot_from_swamp_silver_block", "swamp_silver_ingot");
		nineBlockStorageCustomPacking(recipeOutput, RecipeCategory.MISC, ESItems.SWAMP_SILVER_NUGGET.get(), RecipeCategory.MISC, ESItems.SWAMP_SILVER_INGOT.get(), "swamp_silver_ingot_from_nuggets", "swamp_silver_ingot");
		addAxe(recipeOutput, ESItems.SWAMP_SILVER_AXE.get(), ESItems.SWAMP_SILVER_INGOT.get(), Items.STICK);
		addPickaxe(recipeOutput, ESItems.SWAMP_SILVER_PICKAXE.get(), ESItems.SWAMP_SILVER_INGOT.get(), Items.STICK);
		addHoe(recipeOutput, ESItems.SWAMP_SILVER_SICKLE.get(), ESItems.SWAMP_SILVER_INGOT.get(), Items.STICK);
		addSword(recipeOutput, ESItems.SWAMP_SILVER_SWORD.get(), ESItems.SWAMP_SILVER_INGOT.get(), Items.STICK);
		addHelmet(recipeOutput, ESItems.SWAMP_SILVER_HELMET.get(), ESItems.SWAMP_SILVER_INGOT.get());
		addChestplate(recipeOutput, ESItems.SWAMP_SILVER_CHESTPLATE.get(), ESItems.SWAMP_SILVER_INGOT.get());
		addLeggings(recipeOutput, ESItems.SWAMP_SILVER_LEGGINGS.get(), ESItems.SWAMP_SILVER_INGOT.get());
		addBoots(recipeOutput, ESItems.SWAMP_SILVER_BOOTS.get(), ESItems.SWAMP_SILVER_INGOT.get());
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.SHIELD)
			.pattern("PIP")
			.pattern("PPP")
			.pattern(" P ")
			.define('P', ItemTags.PLANKS)
			.define('I', ESItems.SWAMP_SILVER_INGOT.get())
			.unlockedBy("has_item", has(ESItems.SWAMP_SILVER_INGOT.get()))
			.save(recipeOutput, EternalStarlight.id("shield_from_swamp_silver_ingot"));
		addSmelt(recipeOutput, 200, ESItems.SWAMP_SILVER_ORE.get(), ESItems.SWAMP_SILVER_INGOT.get(), ESItems.SWAMP_SILVER_ORE.get());
		addBlast(recipeOutput, 100, ESItems.SWAMP_SILVER_ORE.get(), ESItems.SWAMP_SILVER_INGOT.get(), ESItems.SWAMP_SILVER_ORE.get());
		addSmelt(recipeOutput, 200, ESItems.SWAMP_SILVER_INGOT.get(), ESItems.SWAMP_SILVER_NUGGET.get(), ESItems.SWAMP_SILVER_PICKAXE.get(), ESItems.SWAMP_SILVER_AXE.get(), ESItems.SWAMP_SILVER_SICKLE.get(), ESItems.SWAMP_SILVER_SWORD.get(), ESItems.SWAMP_SILVER_HELMET.get(), ESItems.SWAMP_SILVER_CHESTPLATE.get(), ESItems.SWAMP_SILVER_LEGGINGS.get(), ESItems.SWAMP_SILVER_BOOTS.get());
		addBlast(recipeOutput, 100, ESItems.SWAMP_SILVER_INGOT.get(), ESItems.SWAMP_SILVER_NUGGET.get(), ESItems.SWAMP_SILVER_PICKAXE.get(), ESItems.SWAMP_SILVER_AXE.get(), ESItems.SWAMP_SILVER_SICKLE.get(), ESItems.SWAMP_SILVER_SWORD.get(), ESItems.SWAMP_SILVER_HELMET.get(), ESItems.SWAMP_SILVER_CHESTPLATE.get(), ESItems.SWAMP_SILVER_LEGGINGS.get(), ESItems.SWAMP_SILVER_BOOTS.get());
	}

	private void addThermalSpringstoneRecipes(RecipeOutput recipeOutput) {
		addAxe(recipeOutput, ESItems.THERMAL_SPRINGSTONE_AXE.get(), ESItems.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
		addPickaxe(recipeOutput, ESItems.THERMAL_SPRINGSTONE_PICKAXE.get(), ESItems.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
		addHoe(recipeOutput, ESItems.THERMAL_SPRINGSTONE_SCYTHE.get(), ESItems.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
		addSword(recipeOutput, ESItems.THERMAL_SPRINGSTONE_SWORD.get(), ESItems.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
		addHammer(recipeOutput, ESItems.THERMAL_SPRINGSTONE_HAMMER.get(), ESItems.THERMAL_SPRINGSTONE_INGOT.get(), Items.STICK);
		addHelmet(recipeOutput, ESItems.THERMAL_SPRINGSTONE_HELMET.get(), ESItems.THERMAL_SPRINGSTONE_INGOT.get());
		addChestplate(recipeOutput, ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get(), ESItems.THERMAL_SPRINGSTONE_INGOT.get());
		addLeggings(recipeOutput, ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get(), ESItems.THERMAL_SPRINGSTONE_INGOT.get());
		addBoots(recipeOutput, ESItems.THERMAL_SPRINGSTONE_BOOTS.get(), ESItems.THERMAL_SPRINGSTONE_INGOT.get());
		addSmelt(recipeOutput, 200, ESItems.THERMAL_SPRINGSTONE.get(), ESItems.THERMAL_SPRINGSTONE_INGOT.get(), ESItems.THERMAL_SPRINGSTONE.get());
		addBlast(recipeOutput, 100, ESItems.THERMAL_SPRINGSTONE.get(), ESItems.THERMAL_SPRINGSTONE_INGOT.get(), ESItems.THERMAL_SPRINGSTONE.get());
	}

	private void addGlaciteRecipes(RecipeOutput recipeOutput) {
		addAxe(recipeOutput, ESItems.GLACITE_AXE.get(), ESItems.GLACITE_SHARD.get(), Items.STICK);
		addPickaxe(recipeOutput, ESItems.GLACITE_PICKAXE.get(), ESItems.GLACITE_SHARD.get(), Items.STICK);
		addHoe(recipeOutput, ESItems.GLACITE_SCYTHE.get(), ESItems.GLACITE_SHARD.get(), Items.STICK);
		addSword(recipeOutput, ESItems.GLACITE_SWORD.get(), ESItems.GLACITE_SHARD.get(), Items.STICK);
		addHelmet(recipeOutput, ESItems.GLACITE_HELMET.get(), ESItems.GLACITE_SHARD.get());
		addChestplate(recipeOutput, ESItems.GLACITE_CHESTPLATE.get(), ESItems.GLACITE_SHARD.get());
		addLeggings(recipeOutput, ESItems.GLACITE_LEGGINGS.get(), ESItems.GLACITE_SHARD.get());
		addBoots(recipeOutput, ESItems.GLACITE_BOOTS.get(), ESItems.GLACITE_SHARD.get());
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.GLACITE_SHIELD.get())
			.pattern("PSP")
			.pattern("PPP")
			.pattern(" P ")
			.define('S', ESItems.GLACITE_SHARD.get())
			.define('P', Ingredient.of(ItemTags.PLANKS))
			.unlockedBy("has_item", has(ESItems.GLACITE_SHARD.get()))
			.save(recipeOutput);
		addSmelt(recipeOutput, 200, ESItems.GLACITE.get(), ESItems.GLACITE_SHARD.get(), ESItems.GLACITE.get());
		addBlast(recipeOutput, 100, ESItems.GLACITE.get(), ESItems.GLACITE_SHARD.get(), ESItems.GLACITE.get());
	}

	private void addSaltpeterRecipes(RecipeOutput recipeOutput) {
		nineBlockStorageCustomUnpacking(recipeOutput, RecipeCategory.MISC, ESItems.SALTPETER_POWDER.get(), RecipeCategory.BUILDING_BLOCKS, ESItems.SALTPETER_BLOCK.get(), "saltpeter_powder_from_saltpeter_block", "saltpeter_powder");
		addSmelt(recipeOutput, 200, ESItems.GRIMSTONE_SALTPETER_ORE.get(), ESItems.SALTPETER_POWDER.get(), ESItems.GRIMSTONE_SALTPETER_ORE.get());
		addBlast(recipeOutput, 100, ESItems.GRIMSTONE_SALTPETER_ORE.get(), ESItems.SALTPETER_POWDER.get(), ESItems.GRIMSTONE_SALTPETER_ORE.get());
		addSmelt(recipeOutput, 200, ESItems.VOIDSTONE_SALTPETER_ORE.get(), ESItems.SALTPETER_POWDER.get(), ESItems.VOIDSTONE_SALTPETER_ORE.get());
		addBlast(recipeOutput, 100, ESItems.VOIDSTONE_SALTPETER_ORE.get(), ESItems.SALTPETER_POWDER.get(), ESItems.VOIDSTONE_SALTPETER_ORE.get());
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ESItems.SALTPETER_MATCHBOX.get())
			.pattern("SSS")
			.pattern("PPP")
			.pattern("SSS")
			.define('S', Items.STRING)
			.define('P', ESItems.SALTPETER_POWDER.get())
			.unlockedBy("has_item", has(ESItems.SALTPETER_POWDER.get()))
			.save(recipeOutput);
	}

	private void addAmaramberRecipes(RecipeOutput recipeOutput) {
		nineBlockStorageCustomPacking(recipeOutput, RecipeCategory.MISC, ESItems.AMARAMBER_NUGGET.get(), RecipeCategory.MISC, ESItems.AMARAMBER_INGOT.get(), "amaramber_ingot_from_nuggets", "amaramber_ingot");
		addShapeless(recipeOutput, ESItems.RAW_AMARAMBER.get(), ESItems.AMARAMBER_INGOT.get(), 2, ESItems.RAW_AMARAMBER.get(), Items.DEEPSLATE);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ESItems.AMARAMBER_CANDLE.get())
			.pattern("S")
			.pattern("A")
			.define('S', Items.STRING)
			.define('A', ESItems.AMARAMBER_INGOT.get())
			.unlockedBy("has_item", has(ESItems.AMARAMBER_INGOT.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ESItems.AMARAMBER_LANTERN.get())
			.pattern("NNN")
			.pattern("NAN")
			.pattern("NNN")
			.define('N', Items.IRON_NUGGET)
			.define('A', ESItems.AMARAMBER_CANDLE.get())
			.unlockedBy("has_item", has(ESItems.AMARAMBER_CANDLE.get()))
			.save(recipeOutput);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ESItems.AMARAMBER_LANTERN.get())
			.pattern("NNN")
			.pattern("NAN")
			.pattern("NNN")
			.define('N', ESItems.SWAMP_SILVER_INGOT.get())
			.define('A', ESItems.AMARAMBER_CANDLE.get())
			.unlockedBy("has_item", has(ESItems.AMARAMBER_CANDLE.get()))
			.save(recipeOutput, EternalStarlight.id("amaramber_lantern_from_swamp_silver_nuggets"));
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ESItems.AMARAMBER_ARROW.get())
			.pattern("A")
			.pattern("S")
			.pattern("S")
			.define('S', Items.STICK)
			.define('A', ESItems.AMARAMBER_INGOT.get())
			.unlockedBy("has_item", has(ESItems.AMARAMBER_INGOT.get()))
			.save(recipeOutput);
		addAxe(recipeOutput, ESItems.AMARAMBER_AXE.get(), ESItems.AMARAMBER_INGOT.get(), Items.STICK);
		addHoe(recipeOutput, ESItems.AMARAMBER_HOE.get(), ESItems.AMARAMBER_INGOT.get(), Items.STICK);
		addShovel(recipeOutput, ESItems.AMARAMBER_SHOVEL.get(), ESItems.AMARAMBER_INGOT.get(), Items.STICK);
		addHelmet(recipeOutput, ESItems.AMARAMBER_HELMET.get(), ESItems.AMARAMBER_INGOT.get());
		addChestplate(recipeOutput, ESItems.AMARAMBER_CHESTPLATE.get(), ESItems.AMARAMBER_INGOT.get());
		addSmelt(recipeOutput, 200, ESItems.AMARAMBER_INGOT.get(), ESItems.AMARAMBER_NUGGET.get(), ESItems.AMARAMBER_AXE.get(), ESItems.AMARAMBER_HOE.get(), ESItems.AMARAMBER_SHOVEL.get(), ESItems.AMARAMBER_HELMET.get(), ESItems.AMARAMBER_CHESTPLATE.get());
		addBlast(recipeOutput, 100, ESItems.AMARAMBER_INGOT.get(), ESItems.AMARAMBER_NUGGET.get(), ESItems.AMARAMBER_AXE.get(), ESItems.AMARAMBER_HOE.get(), ESItems.AMARAMBER_SHOVEL.get(), ESItems.AMARAMBER_HELMET.get(), ESItems.AMARAMBER_CHESTPLATE.get());
	}

	// misc
	protected final void addSmelt(RecipeOutput recipeOutput, int time, ItemLike criteria, ItemLike output, ItemLike... input) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, output, 1.0f, time).unlockedBy("has_item", has(criteria)).save(recipeOutput, EternalStarlight.id(name(output) + "_smelting_from_" + name(criteria)));
	}

	protected final void addBlast(RecipeOutput recipeOutput, int time, ItemLike criteria, ItemLike output, ItemLike... input) {
		SimpleCookingRecipeBuilder.blasting(Ingredient.of(input), RecipeCategory.MISC, output, 1.0f, time).unlockedBy("has_item", has(criteria)).save(recipeOutput, EternalStarlight.id(name(output) + "_blasting_from_" + name(criteria)));
	}

	protected final void addSingleConversion(RecipeOutput recipeOutput, Item to, Item from) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, to)
			.requires(from)
			.unlockedBy("has_item", has(from))
			.save(recipeOutput, EternalStarlight.id("shapeless/" + name(to) + "_from_" + name(from)));
	}

	protected final void addShapeless(RecipeOutput recipeOutput, ItemLike criteria, ItemLike output, int num, ItemLike... ingredients) {
		addShapeless(recipeOutput, RecipeCategory.MISC, criteria, output, num, ingredients);
	}

	protected final void addShapeless(RecipeOutput recipeOutput, RecipeCategory category, ItemLike criteria, ItemLike output, int num, ItemLike... ingredients) {
		ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(category, output, num);
		for (ItemLike item : ingredients) {
			builder.requires(item);
		}
		builder.unlockedBy("has_item", has(criteria)).save(recipeOutput, EternalStarlight.id("shapeless/" + name(output)));
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

	protected final void addShovel(RecipeOutput recipeOutput, ItemLike output, ItemLike input, ItemLike handle) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
			.pattern("#")
			.pattern("H")
			.pattern("H")
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
			.requires(Items.CHEST)
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

	// vanilla copies
	protected void stonecutting(RecipeOutput recipeOutput, RecipeCategory category, ItemLike output, ItemLike input) {
		stonecutting(recipeOutput, category, output, input, 1);
	}

	protected void stonecutting(RecipeOutput recipeOutput, RecipeCategory category, ItemLike output, ItemLike input, int count) {
		SingleItemRecipeBuilder builder = SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), category, output, count).unlockedBy(getHasName(input), has(input));
		String name = getConversionRecipeName(output, input);
		builder.save(recipeOutput, EternalStarlight.id(name + "_stonecutting"));
	}

	protected void nineBlockStorageCustomPacking(RecipeOutput recipeOutput, RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed, String packName, String packGroup) {
		nineBlockStorage(recipeOutput, unpackCategory, unpacked, packCategory, packed, packName, packGroup, getSimpleRecipeName(unpacked), null);
	}

	protected void nineBlockStorageCustomUnpacking(RecipeOutput recipeOutput, RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed, String unpackName, String unpackGroup) {
		nineBlockStorage(recipeOutput, unpackCategory, unpacked, packCategory, packed, getSimpleRecipeName(packed), null, unpackName, unpackGroup);
	}

	protected void nineBlockStorage(RecipeOutput recipeOutput, RecipeCategory unpackCategory, ItemLike unpacked, RecipeCategory packCategory, ItemLike packed, String packName, String packGroup, String unpackName, String unpackGroup) {
		ShapelessRecipeBuilder.shapeless(unpackCategory, unpacked, 9).requires(packed).group(unpackGroup).unlockedBy(getHasName(packed), has(packed)).save(recipeOutput, EternalStarlight.id(unpackName));
		ShapedRecipeBuilder.shaped(packCategory, packed).define('#', unpacked).pattern("###").pattern("###").pattern("###").group(packGroup).unlockedBy(getHasName(unpacked), has(unpacked)).save(recipeOutput, EternalStarlight.id(packName));
	}

	protected void colorWithDye(RecipeOutput recipeOutput, List<Item> dyes, List<Item> toDye, String name) {
		for (int i = 0; i < dyes.size(); ++i) {
			Item item = dyes.get(i);
			Item item1 = toDye.get(i);
			ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, item1).requires(item).requires(Ingredient.of(toDye.stream().filter((argx) -> !argx.equals(item1)).map(ItemStack::new))).group(name).unlockedBy("has_needed_dye", has(item)).save(recipeOutput, EternalStarlight.id("dye_" + getItemName(item1)));
		}
	}

	protected void customCarpet(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 3).define('#', input).pattern("##").group("carpet").unlockedBy(getHasName(input), has(input)).save(recipeOutput, EternalStarlight.id(name(output)));
	}

	protected void bed(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output).define('#', input).define('X', ItemTags.PLANKS).pattern("###").pattern("XXX").group("bed").unlockedBy(getHasName(input), has(input)).save(recipeOutput, EternalStarlight.id(name(output)));
	}

	protected <T extends AbstractCookingRecipe> void simpleCooking(RecipeOutput recipeOutput, String fromName, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> factory, int time, ItemLike input, ItemLike output, float xp) {
		SimpleCookingRecipeBuilder builder = SimpleCookingRecipeBuilder.generic(Ingredient.of(input), RecipeCategory.FOOD, output, xp, time, serializer, factory).unlockedBy(getHasName(input), has(input));
		String itemName = getItemName(output);
		builder.save(recipeOutput, EternalStarlight.id(itemName + "_from_" + fromName));
	}

	protected static void copySmithingTemplate(RecipeOutput recipeOutput, ItemLike template, ItemLike ingredient, ItemLike ingotIngredient) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, template, 2).define('#', ingotIngredient).define('C', ingredient).define('S', template).pattern("#S#").pattern("#C#").pattern("###").unlockedBy(getHasName(template), has(template)).save(recipeOutput);
	}

	public static Stream<VanillaRecipeProvider.TrimTemplate> smithingTrims() {
		return Stream.of(ESItems.KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE.get(), ESItems.FORGE_ARMOR_TRIM_SMITHING_TEMPLATE.get()).map((item) -> new VanillaRecipeProvider.TrimTemplate(item, EternalStarlight.id(getItemName(item) + "_smithing_trim")));
	}

	protected final String name(ItemLike item) {
		return key(item).getPath();
	}

	protected final ResourceLocation key(ItemLike item) {
		return BuiltInRegistries.ITEM.getKey(item.asItem());
	}
}
