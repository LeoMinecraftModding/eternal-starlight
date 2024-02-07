package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;

public class ESTags {
    public static class Items {
        private static TagKey<Item> create(String string) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(EternalStarlight.MOD_ID, string));
        }
        public static final TagKey<Item> LUNAR_LOGS = create("lunar_logs");
        public static final TagKey<Item> NORTHLAND_LOGS = create("northland_logs");
        public static final TagKey<Item> STARLIGHT_MANGROVE_LOGS = create("starlight_mangrove_logs");
        public static final TagKey<Item> SCARLET_LOGS = create("scarlet_logs");
        public static final TagKey<Item> TORREYA_LOGS = create("torreya_logs");
        public static final TagKey<Item> YETI_FUR = create("yeti_fur");
        public static final TagKey<Item> YETI_FUR_CARPETS = create("yeti_fur_carpets");
        public static final TagKey<Item> THERMAL_SPRINGSTONE_WEAPONS = create("thermal_springstone_weapons");
        public static final TagKey<Item> DOOMEDEN_KEYS = create("doomeden_keys");
        public static final TagKey<Item> MANA_CRYSTAL_INGREDIENTS = create("mana_crystal_ingredients");
        public static final TagKey<Item> MANA_CRYSTALS = create("mana_crystals");
        public static final TagKey<Item> TERRA_CRYSTALS = create("terra_crystals");
        public static final TagKey<Item> WIND_CRYSTALS = create("wind_crystals");
        public static final TagKey<Item> WATER_CRYSTALS = create("water_crystals");
        public static final TagKey<Item> LUNAR_CRYSTALS = create("lunar_crystals");
        public static final TagKey<Item> BLAZE_CRYSTALS = create("blaze_crystals");
        public static final TagKey<Item> LIGHT_CRYSTALS = create("light_crystals");
    }

    public static class Blocks {
        private static TagKey<Block> create(String string) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(EternalStarlight.MOD_ID, string));
        }
        public static final TagKey<Block> LUNAR_LOGS = create("lunar_logs");
        public static final TagKey<Block> NORTHLAND_LOGS = create("northland_logs");
        public static final TagKey<Block> STARLIGHT_MANGROVE_LOGS = create("starlight_mangrove_logs");
        public static final TagKey<Block> SCARLET_LOGS = create("scarlet_logs");
        public static final TagKey<Block> TORREYA_LOGS = create("torreya_logs");
        public static final TagKey<Block> PORTAL_FRAME_BLOCKS = create("portal_frame_blocks");
        public static final TagKey<Block> BASE_STONE_STARLIGHT = create("base_stone_starlight");
        public static final TagKey<Block> CORAL_PLANTS = create("coral_plants");
        public static final TagKey<Block> CORALS = create("corals");
        public static final TagKey<Block> WALL_CORALS = create("wall_corals");
        public static final TagKey<Block> CORAL_BLOCKS = create("coral_blocks");
        public static final TagKey<Block> YETI_FUR = create("yeti_fur");
        public static final TagKey<Block> YETI_FUR_CARPETS = create("yeti_fur_carpets");
    }

    public static class Structures {
        private static TagKey<Structure> create(String p_215896_) {
            return TagKey.create(Registries.STRUCTURE, new ResourceLocation(EternalStarlight.MOD_ID, p_215896_));
        }
        public static final TagKey<Structure> BOSS_STRUCTURES = create("boss_structures");
        public static final TagKey<Structure> GOLEM_FORGE = create("golem_forge");
        public static final TagKey<Structure> CURSED_GARDEN = create("cursed_garden");
    }

    public static class Biomes {
        private static TagKey<Biome> create(String string) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(EternalStarlight.MOD_ID, string));
        }
        public static final TagKey<Biome> STARLIGHT_FOREST_VARIANT = create("starlight_forest_variant");
        public static final TagKey<Biome> PERMAFROST_FOREST_VARIANT = create("permafrost_forest_variant");
        public static final TagKey<Biome> DARK_SWAMP_VARIANT = create("dark_swamp_variant");
    }

    public static class Fluids {
        private static TagKey<Fluid> create(String string) {
            return TagKey.create(Registries.FLUID, new ResourceLocation(EternalStarlight.MOD_ID, string));
        }
        public static final TagKey<Fluid> ETHER = create("ether");
    }
}
