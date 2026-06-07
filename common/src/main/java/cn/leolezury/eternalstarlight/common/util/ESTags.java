package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
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
		public static final TagKey<Item> BANYIN_LOGS = create("banyin_logs");
		public static final TagKey<Item> SCARLET_LOGS = create("scarlet_logs");
		public static final TagKey<Item> TORREYA_LOGS = create("torreya_logs");
		public static final TagKey<Item> JINGLESTEM_LOGS = create("jinglestem_logs");
		public static final TagKey<Item> CRADLEWOOD_LOGS = create("cradlewood_logs");
		public static final TagKey<Item> YETI_FUR = create("yeti_fur");
		public static final TagKey<Item> YETI_FUR_CARPETS = create("yeti_fur_carpets");
		public static final TagKey<Item> TOOTH_OF_HUNGER_BLOCKS = create("tooth_of_hunger_blocks");
		public static final TagKey<Item> SCYTHES = create("scythes");
		public static final TagKey<Item> SICKLES = create("sickles");
		public static final TagKey<Item> GREATSWORDS = create("greatswords");
		public static final TagKey<Item> HAMMERS = create("hammers");
		public static final TagKey<Item> BOOMERANGS = create("boomerangs");
		public static final TagKey<Item> WHIPS = create("whips");
		public static final TagKey<Item> SMALL_SHIELDS = create("small_shields");
		public static final TagKey<Item> ACCESSORIES = create("accessories");
		public static final TagKey<Item> AURORA_DEER_FOOD = create("aurora_deer_food");
		public static final TagKey<Item> CRYSTALLIZED_MOTH_FOOD = create("crystallized_moth_food");
		public static final TagKey<Item> STARFIRE_BIRD_FOOD = create("starfire_bird_food");
		public static final TagKey<Item> ENT_FOOD = create("ent_food");
		public static final TagKey<Item> RATLIN_FOOD = create("ratlin_food");
		public static final TagKey<Item> SHADOW_SNAIL_FOOD = create("shadow_snail_food");
		public static final TagKey<Item> YETI_FOOD = create("yeti_food");
		public static final TagKey<Item> UNTRIMMABLE_ARMOR = create("untrimmable_armor");
		public static final TagKey<Item> THERMAL_SPRINGSTONE_WEAPONS = create("thermal_springstone_weapons");
		public static final TagKey<Item> GLACITE_WEAPONS = create("glacite_weapons");
		public static final TagKey<Item> MALARITE_WEAPONS = create("malarite_weapons");
		public static final TagKey<Item> PUNGENCY_FRUIT_WEAPONS = create("pungency_fruit_weapons");
		public static final TagKey<Item> STARFIRE_WEAPONS = create("starfire_weapons");
		public static final TagKey<Item> FLOWGLAZE_WEAPONS = create("flowglaze_weapons");
		public static final TagKey<Item> MENDS_NATURALLY = create("mends_naturally");
		public static final TagKey<Item> HIDES_WITH_OWNER = create("hides_with_owner");
		public static final TagKey<Item> ALGALEAVES = create("algaleaves");
		public static final TagKey<Item> CRYSTALBORN_CATALYST_FUELS = create("crystalborn_catalyst_fuels");
		public static final TagKey<Item> CONSUMABLE_WHEN_WEARING_FUNGUS_AMULET = create("consumable_when_wearing_fungus_amulet");
		public static final TagKey<Item> REPAIRED_BY_CRESCENT_PENDANT = create("repaired_by_crescent_pendant");
		public static final TagKey<Item> LUNAR_MONSTROSITY_IGNITERS = create("lunar_monstrosity_igniters");
		public static final TagKey<Item> STELLAGMITE_IGNITERS = create("stellagmite_igniters");
		public static final TagKey<Item> ENT_FERTILIZERS = create("ent_fertilizers");
		public static final TagKey<Item> STRANGHOUL_CAN_USE = create("stranghoul_can_use");
		public static final TagKey<Item> STRANGHOUL_FOOD = create("stranghoul_food");
		public static final TagKey<Item> STRANGHOUL_HIRING_FOOD = create("stranghoul_hiring_food");
		public static final TagKey<Item> STRANGHOUL_CURRENCIES = create("stranghoul_currencies");
		public static final TagKey<Item> STRANGHOUL_VULNERABLE_TO = create("stranghoul_vulnerable_to");
		public static final TagKey<Item> SEEDS_LAUNCHER_AMMO = create("seeds_launcher_ammo");
		public static final TagKey<Item> ALLOY_FURNACES = create("alloy_furnaces");
		public static final TagKey<Item> DOOMEDEN_KEYS = create("doomeden_keys");
		public static final TagKey<Item> SCYTHE_ENCHANTABLE = create("enchantable/scythe");
		public static final TagKey<Item> GREATSWORD_ENCHANTABLE = create("enchantable/greatsword");
		public static final TagKey<Item> HAMMER_ENCHANTABLE = create("enchantable/hammer");
		public static final TagKey<Item> BOOMERANG_ENCHANTABLE = create("enchantable/boomerang");
		public static final TagKey<Item> WHIP_ENCHANTABLE = create("enchantable/whip");
		public static final TagKey<Item> CHAIN_OF_SOULS_ENCHANTABLE = create("enchantable/chain_of_souls");
		public static final TagKey<Item> PUNGENCY_FRUIT_SPEAR_ENCHANTABLE = create("enchantable/pungency_fruit_spear");
		public static final TagKey<Item> SEEDS_LAUNCHER_ENCHANTABLE = create("enchantable/seeds_launcher");
		public static final TagKey<Item> CHEST_ARMOR_ACCESSORY_APPLICABLE = create("accessory_applicable/chest_armor");
		public static final TagKey<Item> AXE_ACCESSORY_APPLICABLE = create("accessory_applicable/axe");
		public static final TagKey<Item> HAMMER_ACCESSORY_APPLICABLE = create("accessory_applicable/hammer");
		public static final TagKey<Item> AFFECTS_PROGRESSION = create("affects_progression");
		public static final TagKey<Item> GOLEM_FORGE_LOCATORS = create("golem_forge_locators");
		public static final TagKey<Item> CURSED_GARDEN_LOCATORS = create("cursed_garden_locators");
		public static final TagKey<Item> ARROW_FEATHERS = create("arrow_feathers");
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
		public static final TagKey<Block> BANYIN_LOGS = create("banyin_logs");
		public static final TagKey<Block> SCARLET_LOGS = create("scarlet_logs");
		public static final TagKey<Block> TORREYA_LOGS = create("torreya_logs");
		public static final TagKey<Block> JINGLESTEM_LOGS = create("jinglestem_logs");
		public static final TagKey<Block> CRADLEWOOD_LOGS = create("cradlewood_logs");
		public static final TagKey<Block> PORTAL_FRAME_BLOCKS = create("portal_frame_blocks");
		public static final TagKey<Block> BASE_STONE_STARLIGHT = create("base_stone_starlight");
		public static final TagKey<Block> SPELEOTHEMS = create("speleothems");
		public static final TagKey<Block> SPELEOTHEM_BASE_BLOCKS = create("speleothem_base_blocks");
		public static final TagKey<Block> STARLIGHT_CARVER_REPLACEABLES = create("starlight_carver_replaceables");
		public static final TagKey<Block> ABYSSAL_CAVE_REPLACEABLES = create("abyssal_cave_replaceables");
		public static final TagKey<Block> CORAL_PLANTS = create("coral_plants");
		public static final TagKey<Block> CORALS = create("corals");
		public static final TagKey<Block> WALL_CORALS = create("wall_corals");
		public static final TagKey<Block> CORAL_BLOCKS = create("coral_blocks");
		public static final TagKey<Block> YETI_FUR = create("yeti_fur");
		public static final TagKey<Block> YETI_FUR_CARPETS = create("yeti_fur_carpets");
		public static final TagKey<Block> ABYSSAL_FIRE_SURVIVES_ON = create("abyssal_fire_survives_on");
		public static final TagKey<Block> AMARAMBER_FIRE_SURVIVES_ON = create("amaramber_fire_survives_on");
		public static final TagKey<Block> TOOTH_OF_HUNGER_BLOCKS = create("tooth_of_hunger_blocks");
		public static final TagKey<Block> ABYSSLATES = create("abysslates");
		public static final TagKey<Block> AETHERSENT_METEOR_REPLACEABLES = create("aethersent_meteor_replaceables");
		public static final TagKey<Block> DOOMEDEN_KEYHOLE_DESTROYABLES = create("doomeden_keyhole_destroyables");
		public static final TagKey<Block> DUSK_LIGHT_DESTROYABLES = create("dusk_light_destroyables");
		public static final TagKey<Block> DUSK_LIGHT_ALWAYS_PASSABLE = create("dusk_light_always_passable");
		public static final TagKey<Block> DUSK_LIGHT_ALWAYS_UNPASSABLE = create("dusk_light_always_unpassable");
		public static final TagKey<Block> CRYSTALBORN_CATALYST_REPLACEABLES = create("crystalborn_catalyst_replaceables");
		public static final TagKey<Block> CRYSTALBORN_CATALYST_MOSS_REPLACEABLES = create("crystalborn_catalyst_moss_replaceables");
		public static final TagKey<Block> CRYSTALBORN_CATALYST_PREFERENCES = create("crystalborn_catalyst_preferences");
		public static final TagKey<Block> CONVERTS_NOCTURNAL_MILLET = create("converts_nocturnal_millet");
		public static final TagKey<Block> STARFIRE_BIRD_NESTS = create("starfire_bird_nests");
		public static final TagKey<Block> PREVENTS_MELTING = create("prevents_melting");
		public static final TagKey<Block> UNAFFECTED_BY_OBLIVION = create("unaffected_by_oblivion");

		private static TagKey<Block> create(String string) {
			return TagKey.create(Registries.BLOCK, EternalStarlight.id(string));
		}
	}

	public static class EntityTypes {
		public static final TagKey<EntityType<?>> AFFECTS_PROGRESSION = create("affects_progression");
		public static final TagKey<EntityType<?>> STARLIGHT_GOLEM_ALLIES = create("starlight_golem_allies");
		public static final TagKey<EntityType<?>> LUNAR_MONSTROSITY_ALLIES = create("lunar_monstrosity_allies");
		public static final TagKey<EntityType<?>> ABYSSAL_FIRE_IMMUNE = create("abyssal_fire_immune");
		public static final TagKey<EntityType<?>> VULNERABLE_TO_SONAR_BOMB = create("vulnerable_to_sonar_bomb");
		public static final TagKey<EntityType<?>> GLEECH_IMMUNE = create("gleech_immune");
		public static final TagKey<EntityType<?>> CHAIN_OF_SOULS_CANNOT_PULL = create("chan_of_souls_cannot_pull");
		public static final TagKey<EntityType<?>> TEARY_IMMUNE = create("teary_immune");
		public static final TagKey<EntityType<?>> STRANGHOUL_PREYS = create("stranghoul_preys");
		public static final TagKey<EntityType<?>> STRANGHOUL_CANNOT_HUNT = create("stranghoul_cannot_hunt");
		public static final TagKey<EntityType<?>> STARFIRE_BIRD_AFRAID_OF = create("starfire_bird_afraid_of");
		public static final TagKey<EntityType<?>> AETHERSENT_GOLEM_TARGETS = create("aethersent_golem_targets");
		public static final TagKey<EntityType<?>> SOLARIS_ISLES_INHABITANTS = create("solaris_isles_inhabitants");

		private static TagKey<EntityType<?>> create(String string) {
			return TagKey.create(Registries.ENTITY_TYPE, EternalStarlight.id(string));
		}
	}

	public static class DamageTypes {
		public static final TagKey<DamageType> BYPASSES_CRESCENT_PENDANT = create("bypasses_crescent_pendant");

		private static TagKey<DamageType> create(String string) {
			return TagKey.create(Registries.DAMAGE_TYPE, EternalStarlight.id(string));
		}
	}

	public static class Structures {
		public static final TagKey<Structure> BOSS_LANDMARKS = create("boss_landmarks");
		public static final TagKey<Structure> GOLEM_FORGE = create("golem_forge");
		public static final TagKey<Structure> CURSED_GARDEN = create("cursed_garden");
		public static final TagKey<Structure> PORTAL_RUINS = create("portal_ruins");
		public static final TagKey<Structure> ADAPTIVE_TERRAIN = create("adaptive_terrain");

		private static TagKey<Structure> create(String string) {
			return TagKey.create(Registries.STRUCTURE, EternalStarlight.id(string));
		}
	}

	public static class Biomes {
		public static final TagKey<Biome> PERMAFROST = create("permafrost");
		public static final TagKey<Biome> HAS_PORTAL_RUINS_COMMON = create("has_portal_ruins_common");
		public static final TagKey<Biome> HAS_PORTAL_RUINS_FOREST = create("has_portal_ruins_forest");
		public static final TagKey<Biome> HAS_PORTAL_RUINS_DESERT = create("has_portal_ruins_desert");
		public static final TagKey<Biome> HAS_PORTAL_RUINS_JUNGLE = create("has_portal_ruins_jungle");
		public static final TagKey<Biome> HAS_PORTAL_RUINS_COLD = create("has_portal_ruins_cold");
		public static final TagKey<Biome> HAS_GOLEM_FORGE = create("has_golem_forge");
		public static final TagKey<Biome> HAS_CURSED_GARDEN = create("has_cursed_garden");
		public static final TagKey<Biome> HAS_STRANGHOUL_DEN = create("has_stranghoul_den");

		private static TagKey<Biome> create(String string) {
			return TagKey.create(Registries.BIOME, EternalStarlight.id(string));
		}
	}

	public static class Enchantments {
		public static final TagKey<Enchantment> GOLEM_FORGE_LOOT = create("golem_forge_loot");
		public static final TagKey<Enchantment> CURSED_GARDEN_LOOT = create("cursed_garden_loot");
		public static final TagKey<Enchantment> PREVENTS_STARFIRE_BIRD_SPAWNS_WHEN_MINING = create("prevents_starfire_bird_spawns_when_mining");
		public static final TagKey<Enchantment> SEEDS_LAUNCHER_EXCLUSIVE = create("exclusive_set/seeds_launcher");

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
		public static final TagKey<MobEffect> DEEPSILVER_ARMOR_CAN_REMOVE = create("deepsilver_armor_can_remove");

		private static TagKey<MobEffect> create(String string) {
			return TagKey.create(Registries.MOB_EFFECT, EternalStarlight.id(string));
		}
	}
}
