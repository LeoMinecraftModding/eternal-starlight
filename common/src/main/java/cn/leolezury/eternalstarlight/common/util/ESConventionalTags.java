package cn.leolezury.eternalstarlight.common.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ESConventionalTags {
	public static class Items {
		public static final TagKey<Item> GEMS_THIOQUARTZ = gem("thioquartz");

		public static final TagKey<Item> STORAGE_BLOCKS_GOLEM_STEEL = storageBlock("golem_steel");
		public static final TagKey<Item> STORAGE_BLOCKS_OXIDIZED_GOLEM_STEEL = storageBlock("oxidized_golem_steel");
		public static final TagKey<Item> INGOTS_GOLEM_STEEL = ingot("golem_steel");
		public static final TagKey<Item> INGOTS_OXIDIZED_GOLEM_STEEL = ingot("oxidized_golem_steel");

		public static final TagKey<Item> RAW_MATERIALS_AETHERSENT = rawMaterial("aethersent");
		public static final TagKey<Item> STORAGE_BLOCKS_RAW_AETHERSENT = storageBlock("raw_aethersent");
		public static final TagKey<Item> STORAGE_BLOCKS_AETHERSENT = storageBlock("aethersent");
		public static final TagKey<Item> INGOTS_AETHERSENT = ingot("aethersent");
		public static final TagKey<Item> NUGGETS_AETHERSENT = nugget("aethersent");

		public static final TagKey<Item> INGOTS_THERMAL_SPRINGSTONE = ingot("thermal_springstone");

		public static final TagKey<Item> GEMS_GLACITE = gem("glacite");

		public static final TagKey<Item> ORES_SWAMP_SILVER = ore("swamp_silver");
		public static final TagKey<Item> STORAGE_BLOCKS_SWAMP_SILVER = storageBlock("swamp_silver");
		public static final TagKey<Item> INGOTS_SWAMP_SILVER = ingot("swamp_silver");
		public static final TagKey<Item> NUGGETS_SWAMP_SILVER = nugget("swamp_silver");

		public static final TagKey<Item> ORES_SALTPETER = ore("saltpeter");
		public static final TagKey<Item> STORAGE_BLOCKS_SALTPETER = storageBlock("saltpeter");

		public static final TagKey<Item> RAW_MATERIALS_AMARAMBER = rawMaterial("amaramber");
		public static final TagKey<Item> INGOTS_AMARAMBER = ingot("amaramber");
		public static final TagKey<Item> NUGGETS_AMARAMBER = nugget("amaramber");

		public static final TagKey<Item> ORES_IN_GROUND_GRIMSTONE = oresInGround("grimstone");
		public static final TagKey<Item> ORES_IN_GROUND_VOIDSTONE = oresInGround("voidstone");
		public static final TagKey<Item> ORES_IN_GROUND_NIGHTFALL_MUD = oresInGround("nightfall_mud");

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
		public static final TagKey<Block> STORAGE_BLOCKS_GOLEM_STEEL = storageBlock("golem_steel");
		public static final TagKey<Block> STORAGE_BLOCKS_OXIDIZED_GOLEM_STEEL = storageBlock("oxidized_golem_steel");

		public static final TagKey<Block> STORAGE_BLOCKS_RAW_AETHERSENT = storageBlock("raw_aethersent");
		public static final TagKey<Block> STORAGE_BLOCKS_AETHERSENT = storageBlock("aethersent");

		public static final TagKey<Block> ORES_SWAMP_SILVER = ore("swamp_silver");
		public static final TagKey<Block> STORAGE_BLOCKS_SWAMP_SILVER = storageBlock("swamp_silver");

		public static final TagKey<Block> ORES_SALTPETER = ore("saltpeter");
		public static final TagKey<Block> STORAGE_BLOCKS_SALTPETER = storageBlock("saltpeter");

		public static final TagKey<Block> ORES_IN_GROUND_GRIMSTONE = oresInGround("grimstone");
		public static final TagKey<Block> ORES_IN_GROUND_VOIDSTONE = oresInGround("voidstone");
		public static final TagKey<Block> ORES_IN_GROUND_NIGHTFALL_MUD = oresInGround("nightfall_mud");

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
