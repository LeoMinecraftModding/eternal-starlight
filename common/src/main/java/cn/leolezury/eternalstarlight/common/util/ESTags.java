package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;

public class ESTags {
	public static class Items {
		public static final TagKey<Item> LUNAR_LOGS = create("lunar_logs");
		public static final TagKey<Item> NORTHLAND_LOGS = create("northland_logs");
		public static final TagKey<Item> STARLIGHT_MANGROVE_LOGS = create("starlight_mangrove_logs");
		public static final TagKey<Item> SCARLET_LOGS = create("scarlet_logs");
		public static final TagKey<Item> TORREYA_LOGS = create("torreya_logs");
		public static final TagKey<Item> YETI_FUR = create("yeti_fur");
		public static final TagKey<Item> YETI_FUR_CARPETS = create("yeti_fur_carpets");
		public static final TagKey<Item> TOOTH_OF_HUNGER_BLOCKS = create("tooth_of_hunger_blocks");
		public static final TagKey<Item> SCYTHES = create("scythes");
		public static final TagKey<Item> SICKLES = create("sickles");
		public static final TagKey<Item> GREATSWORDS = create("greatswords");
		public static final TagKey<Item> HAMMERS = create("hammers");
		public static final TagKey<Item> SMALL_SHIELDS = create("small_shields");
		public static final TagKey<Item> AURORA_DEER_FOOD = create("aurora_deer_food");
		public static final TagKey<Item> CRYSTALLIZED_MOTH_FOOD = create("crystallized_moth_food");
		public static final TagKey<Item> ENT_FOOD = create("ent_food");
		public static final TagKey<Item> RATLIN_FOOD = create("ratlin_food");
		public static final TagKey<Item> YETI_FOOD = create("yeti_food");
		public static final TagKey<Item> UNTRIMMABLE_ARMOR = create("untrimmable_armor");
		public static final TagKey<Item> THERMAL_SPRINGSTONE_WEAPONS = create("thermal_springstone_weapons");
		public static final TagKey<Item> GLACITE_WEAPONS = create("glacite_weapons");
		public static final TagKey<Item> LUNAR_MONSTROSITY_IGNITERS = create("lunar_monstrosity_igniters");
		public static final TagKey<Item> STELLAGMITE_IGNITERS = create("stellagmite_igniters");
		public static final TagKey<Item> DOOMEDEN_KEYS = create("doomeden_keys");
		public static final TagKey<Item> SCYTHE_ENCHANTABLE = create("enchantable/scythe");
		public static final TagKey<Item> GREATSWORD_ENCHANTABLE = create("enchantable/greatsword");
		public static final TagKey<Item> HAMMER_ENCHANTABLE = create("enchantable/hammer");
		public static final TagKey<Item> CHAIN_OF_SOULS_ENCHANTABLE = create("enchantable/chain_of_souls");
		public static final TagKey<Item> GOLEM_FORGE_LOCATORS = create("golem_forge_locators");
		public static final TagKey<Item> CURSED_GARDEN_LOCATORS = create("cursed_garden_locators");
		public static final TagKey<Item> MANA_CRYSTAL_INGREDIENTS = create("mana_crystal_ingredients");
		public static final TagKey<Item> MANA_CRYSTALS = create("mana_crystals");
		public static final TagKey<Item> TERRA_CRYSTAL_INGREDIENTS = create("terra_crystal_ingredients");
		public static final TagKey<Item> TERRA_CRYSTALS = create("terra_crystals");
		public static final TagKey<Item> WIND_CRYSTAL_INGREDIENTS = create("wind_crystal_ingredients");
		public static final TagKey<Item> WIND_CRYSTALS = create("wind_crystals");
		public static final TagKey<Item> WATER_CRYSTAL_INGREDIENTS = create("water_crystal_ingredients");
		public static final TagKey<Item> WATER_CRYSTALS = create("water_crystals");
		public static final TagKey<Item> LUNAR_CRYSTAL_INGREDIENTS = create("lunar_crystal_ingredients");
		public static final TagKey<Item> LUNAR_CRYSTALS = create("lunar_crystals");
		public static final TagKey<Item> BLAZE_CRYSTAL_INGREDIENTS = create("blaze_crystal_ingredients");
		public static final TagKey<Item> BLAZE_CRYSTALS = create("blaze_crystals");
		public static final TagKey<Item> LIGHT_CRYSTAL_INGREDIENTS = create("light_crystal_ingredients");
		public static final TagKey<Item> LIGHT_CRYSTALS = create("light_crystals");
		public static final TagKey<Item> WIP = create("wip");

		private static TagKey<Item> create(String string) {
			return TagKey.create(Registries.ITEM, EternalStarlight.id(string));
		}
	}

	public static class Blocks {
		public static final TagKey<Block> LUNAR_LOGS = create("lunar_logs");
		public static final TagKey<Block> NORTHLAND_LOGS = create("northland_logs");
		public static final TagKey<Block> STARLIGHT_MANGROVE_LOGS = create("starlight_mangrove_logs");
		public static final TagKey<Block> SCARLET_LOGS = create("scarlet_logs");
		public static final TagKey<Block> TORREYA_LOGS = create("torreya_logs");
		public static final TagKey<Block> PORTAL_FRAME_BLOCKS = create("portal_frame_blocks");
		public static final TagKey<Block> BASE_STONE_STARLIGHT = create("base_stone_starlight");
		public static final TagKey<Block> STARLIGHT_CARVER_REPLACEABLES = create("starlight_carver_replaceables");
		public static final TagKey<Block> CORAL_PLANTS = create("coral_plants");
		public static final TagKey<Block> CORALS = create("corals");
		public static final TagKey<Block> WALL_CORALS = create("wall_corals");
		public static final TagKey<Block> CORAL_BLOCKS = create("coral_blocks");
		public static final TagKey<Block> YETI_FUR = create("yeti_fur");
		public static final TagKey<Block> YETI_FUR_CARPETS = create("yeti_fur_carpets");
		public static final TagKey<Block> ABYSSAL_FIRE_SURVIVES_ON = create("abyssal_fire_survives_on");
		public static final TagKey<Block> TOOTH_OF_HUNGER_BLOCKS = create("tooth_of_hunger_blocks");
		public static final TagKey<Block> ABYSSLATES = create("abysslates");
		public static final TagKey<Block> AETHERSENT_METEOR_REPLACEABLES = create("aethersent_meteor_replaceables");
		public static final TagKey<Block> DOOMEDEN_KEYHOLE_DESTROYABLES = create("doomeden_keyhole_destroyables");
		public static final TagKey<Block> DUSK_LIGHT_ENERGY_SOURCES = create("dusk_light_energy_sources");

		private static TagKey<Block> create(String string) {
			return TagKey.create(Registries.BLOCK, EternalStarlight.id(string));
		}
	}

	public static class EntityTypes {
		public static final TagKey<EntityType<?>> ROBOTIC = create("robotic");
		public static final TagKey<EntityType<?>> LUNAR_MONSTROSITY_ALLYS = create("lunar_monstrosity_allys");
		public static final TagKey<EntityType<?>> ABYSSAL_FIRE_IMMUNE = create("abyssal_fire_immune");
		public static final TagKey<EntityType<?>> VULNERABLE_TO_SONAR_BOMB = create("vulnerable_to_sonar_bomb");
		public static final TagKey<EntityType<?>> GLEECH_IMMUNE = create("gleech_immune");

		private static TagKey<EntityType<?>> create(String string) {
			return TagKey.create(Registries.ENTITY_TYPE, EternalStarlight.id(string));
		}
	}

	public static class Structures {
		public static final TagKey<Structure> BOSS_STRUCTURES = create("boss_structures");
		public static final TagKey<Structure> GOLEM_FORGE = create("golem_forge");
		public static final TagKey<Structure> CURSED_GARDEN = create("cursed_garden");
		public static final TagKey<Structure> PORTAL_RUINS = create("portal_ruins");

		private static TagKey<Structure> create(String string) {
			return TagKey.create(Registries.STRUCTURE, EternalStarlight.id(string));
		}
	}

	public static class Biomes {
		public static final TagKey<Biome> HAS_PORTAL_RUINS_COMMON = create("has_portal_ruins_common");
		public static final TagKey<Biome> HAS_PORTAL_RUINS_FOREST = create("has_portal_ruins_forest");
		public static final TagKey<Biome> HAS_PORTAL_RUINS_DESERT = create("has_portal_ruins_desert");
		public static final TagKey<Biome> HAS_PORTAL_RUINS_JUNGLE = create("has_portal_ruins_jungle");
		public static final TagKey<Biome> HAS_PORTAL_RUINS_COLD = create("has_portal_ruins_cold");
		public static final TagKey<Biome> HAS_GOLEM_FORGE = create("has_golem_forge");
		public static final TagKey<Biome> HAS_CURSED_GARDEN = create("has_cursed_garden");

		private static TagKey<Biome> create(String string) {
			return TagKey.create(Registries.BIOME, EternalStarlight.id(string));
		}
	}

	public static class Enchantments {
		public static final TagKey<Enchantment> GOLEM_FORGE_LOOT = create("golem_forge_loot");
		public static final TagKey<Enchantment> CURSED_GARDEN_LOOT = create("cursed_garden_loot");

		private static TagKey<Enchantment> create(String string) {
			return TagKey.create(Registries.ENCHANTMENT, EternalStarlight.id(string));
		}
	}

	public static class Fluids {
		public static final TagKey<Fluid> ETHER = create("ether");

		private static TagKey<Fluid> create(String string) {
			return TagKey.create(Registries.FLUID, EternalStarlight.id(string));
		}
	}

	public static class PaintingVariants {
		public static final TagKey<PaintingVariant> PLACEABLE = create("placeable");

		private static TagKey<PaintingVariant> create(String string) {
			return TagKey.create(Registries.PAINTING_VARIANT, EternalStarlight.id(string));
		}
	}

	public static class MobEffects {
		public static final TagKey<MobEffect> SWAMP_SILVER_ARMOR_CAN_REMOVE = create("swamp_silver_armor_can_remove");

		private static TagKey<MobEffect> create(String string) {
			return TagKey.create(Registries.MOB_EFFECT, EternalStarlight.id(string));
		}
	}

	public static class Crests {
		public static final TagKey<Crest> IS_IN_CREST_POT = create("is_in_crest_pot");

		private static TagKey<Crest> create(String string) {
			return TagKey.create(ESRegistries.CREST, EternalStarlight.id(string));
		}
	}
}
