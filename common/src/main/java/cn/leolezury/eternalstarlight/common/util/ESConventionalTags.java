package cn.leolezury.eternalstarlight.common.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ESConventionalTags {
	public static class Items {
		public static final TagKey<Item> SEEDS_CRINOA = seed("crinoa");
		public static final TagKey<Item> CROPS_CRINOA = crop("crinoa");
		public static final TagKey<Item> STORAGE_BLOCKS_CRINOA = storageBlock("crinoa");

		public static final TagKey<Item> SEEDS_PUNGENCY_FRUIT = seed("pungency_fruit");
		public static final TagKey<Item> CROPS_PUNGENCY_FRUIT = crop("pungency_fruit");

		public static final TagKey<Item> SEEDS_NOCTURNAL_MILLET = seed("nocturnal_millet");
		public static final TagKey<Item> CROPS_NOCTURNAL_MILLET = crop("nocturnal_millet");

		public static final TagKey<Item> CAMPFIRES = create("campfires");

		public static final TagKey<Item> ORES_STARCORE = ore("starcore");
		public static final TagKey<Item> STORAGE_BLOCKS_STARCORE = storageBlock("starcore");
		public static final TagKey<Item> GEMS_STARCORE = gem("starcore");

		public static final TagKey<Item> RAW_MATERIALS_AETHERSENT = rawMaterial("aethersent");
		public static final TagKey<Item> STORAGE_BLOCKS_RAW_AETHERSENT = storageBlock("raw_aethersent");
		public static final TagKey<Item> STORAGE_BLOCKS_AETHERSENT = storageBlock("aethersent");
		public static final TagKey<Item> INGOTS_AETHERSENT = ingot("aethersent");
		public static final TagKey<Item> NUGGETS_AETHERSENT = nugget("aethersent");

		public static final TagKey<Item> INGOTS_THERMAL_SPRINGSTONE = ingot("thermal_springstone");

		public static final TagKey<Item> STORAGE_BLOCKS_GLACITE = storageBlock("glacite");
		public static final TagKey<Item> GEMS_GLACITE = gem("glacite");

		public static final TagKey<Item> ORES_STARLIT_DIAMOND = ore("starlit_diamond");
		public static final TagKey<Item> STORAGE_BLOCKS_STARLIT_DIAMOND = storageBlock("starlit_diamond");
		public static final TagKey<Item> GEMS_STARLIT_DIAMOND = gem("starlit_diamond");

		public static final TagKey<Item> RAW_MATERIALS_DEEPSILVER = rawMaterial("deepsilver");
		public static final TagKey<Item> ORES_DEEPSILVER = ore("deepsilver");
		public static final TagKey<Item> STORAGE_BLOCKS_RAW_DEEPSILVER = storageBlock("raw_deepsilver");
		public static final TagKey<Item> STORAGE_BLOCKS_DEEPSILVER = storageBlock("deepsilver");
		public static final TagKey<Item> INGOTS_DEEPSILVER = ingot("deepsilver");
		public static final TagKey<Item> NUGGETS_DEEPSILVER = nugget("deepsilver");

		public static final TagKey<Item> STORAGE_BLOCKS_UNREALIUM = storageBlock("unrealium");
		public static final TagKey<Item> INGOTS_UNREALIUM = ingot("unrealium");
		public static final TagKey<Item> NUGGETS_UNREALIUM = nugget("unrealium");

		public static final TagKey<Item> ORES_MALARITE = ore("malarite");
		public static final TagKey<Item> STORAGE_BLOCKS_MALARITE = storageBlock("malarite");
		public static final TagKey<Item> GEMS_MALARITE = gem("malarite");

		public static final TagKey<Item> ORES_SALTPETER = ore("saltpeter");
		public static final TagKey<Item> STORAGE_BLOCKS_SALTPETER = storageBlock("saltpeter");
		public static final TagKey<Item> DUSTS_SALTPETER = dust("saltpeter");

		public static final TagKey<Item> RAW_MATERIALS_AMARAMBER = rawMaterial("amaramber");
		public static final TagKey<Item> STORAGE_BLOCKS_RAW_AMARAMBER = storageBlock("raw_amaramber");
		public static final TagKey<Item> INGOTS_AMARAMBER = ingot("amaramber");
		public static final TagKey<Item> NUGGETS_AMARAMBER = nugget("amaramber");

		public static final TagKey<Item> GEMS_THIOQUARTZ = gem("thioquartz");

		public static final TagKey<Item> STORAGE_BLOCKS_GOLEM_STEEL = storageBlock("golem_steel");
		public static final TagKey<Item> STORAGE_BLOCKS_OXIDIZED_GOLEM_STEEL = storageBlock("oxidized_golem_steel");
		public static final TagKey<Item> INGOTS_GOLEM_STEEL = ingot("golem_steel");
		public static final TagKey<Item> INGOTS_OXIDIZED_GOLEM_STEEL = ingot("oxidized_golem_steel");
		public static final TagKey<Item> NUGGETS_GOLEM_STEEL = nugget("golem_steel");
		public static final TagKey<Item> NUGGETS_OXIDIZED_GOLEM_STEEL = nugget("oxidized_golem_steel");

		public static final TagKey<Item> ORES_IN_GROUND_GRIMSTONE = oresInGround("grimstone");
		public static final TagKey<Item> ORES_IN_GROUND_VOIDSTONE = oresInGround("voidstone");
		public static final TagKey<Item> ORES_IN_GROUND_NIGHTFALL_MUD = oresInGround("nightfall_mud");
		public static final TagKey<Item> ORES_IN_GROUND_PACKED_NIGHTFALL_MUD = oresInGround("packed_nightfall_mud");

		private static TagKey<Item> seed(String string) {
			return create("seeds/" + string);
		}

		private static TagKey<Item> crop(String string) {
			return create("crops/" + string);
		}

		private static TagKey<Item> gem(String string) {
			return create("gems/" + string);
		}

		private static TagKey<Item> nugget(String string) {
			return create("nuggets/" + string);
		}

		private static TagKey<Item> ingot(String string) {
			return create("ingots/" + string);
		}

		private static TagKey<Item> rawMaterial(String string) {
			return create("raw_materials/" + string);
		}

		private static TagKey<Item> dust(String string) {
			return create("dusts/" + string);
		}

		private static TagKey<Item> ore(String string) {
			return create("ores/" + string);
		}

		private static TagKey<Item> storageBlock(String string) {
			return create("storage_blocks/" + string);
		}

		private static TagKey<Item> oresInGround(String string) {
			return create("ores_in_ground/" + string);
		}

		private static TagKey<Item> create(String string) {
			return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", string));
		}
	}

	public static class Blocks {
		public static final TagKey<Block> STORAGE_BLOCKS_CRINOA = storageBlock("crinoa");

		public static final TagKey<Block> ORES_STARCORE = ore("starcore");
		public static final TagKey<Block> STORAGE_BLOCKS_STARCORE = storageBlock("starcore");

		public static final TagKey<Block> STORAGE_BLOCKS_RAW_AETHERSENT = storageBlock("raw_aethersent");
		public static final TagKey<Block> STORAGE_BLOCKS_AETHERSENT = storageBlock("aethersent");

		public static final TagKey<Block> STORAGE_BLOCKS_GLACITE = storageBlock("glacite");

		public static final TagKey<Block> ORES_STARLIT_DIAMOND = ore("starlit_diamond");
		public static final TagKey<Block> STORAGE_BLOCKS_STARLIT_DIAMOND = storageBlock("starlit_diamond");

		public static final TagKey<Block> ORES_DEEPSILVER = ore("deepsilver");
		public static final TagKey<Block> STORAGE_BLOCKS_RAW_DEEPSILVER = storageBlock("raw_deepsilver");
		public static final TagKey<Block> STORAGE_BLOCKS_DEEPSILVER = storageBlock("deepsilver");

		public static final TagKey<Block> STORAGE_BLOCKS_UNREALIUM = storageBlock("unrealium");

		public static final TagKey<Block> ORES_MALARITE = ore("malarite");
		public static final TagKey<Block> STORAGE_BLOCKS_MALARITE = storageBlock("malarite");

		public static final TagKey<Block> ORES_SALTPETER = ore("saltpeter");
		public static final TagKey<Block> STORAGE_BLOCKS_SALTPETER = storageBlock("saltpeter");

		public static final TagKey<Block> STORAGE_BLOCKS_RAW_AMARAMBER = storageBlock("raw_amaramber");

		public static final TagKey<Block> STORAGE_BLOCKS_GOLEM_STEEL = storageBlock("golem_steel");
		public static final TagKey<Block> STORAGE_BLOCKS_OXIDIZED_GOLEM_STEEL = storageBlock("oxidized_golem_steel");

		public static final TagKey<Block> ORES_IN_GROUND_GRIMSTONE = oresInGround("grimstone");
		public static final TagKey<Block> ORES_IN_GROUND_VOIDSTONE = oresInGround("voidstone");
		public static final TagKey<Block> ORES_IN_GROUND_ETERNAL_ICE = oresInGround("eternal_ice");
		public static final TagKey<Block> ORES_IN_GROUND_HAZE_ICE = oresInGround("haze_ice");
		public static final TagKey<Block> ORES_IN_GROUND_NIGHTFALL_MUD = oresInGround("nightfall_mud");
		public static final TagKey<Block> ORES_IN_GROUND_PACKED_NIGHTFALL_MUD = oresInGround("packed_nightfall_mud");

		private static TagKey<Block> ore(String string) {
			return create("ores/" + string);
		}

		private static TagKey<Block> storageBlock(String string) {
			return create("storage_blocks/" + string);
		}

		private static TagKey<Block> oresInGround(String string) {
			return create("ores_in_ground/" + string);
		}

		private static TagKey<Block> create(String string) {
			return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", string));
		}
	}
}
