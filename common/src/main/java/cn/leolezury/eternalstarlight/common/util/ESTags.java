package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ESTags {
    public static class Items {
        private static TagKey<Item> create(String string) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(EternalStarlight.MOD_ID, string));
        }
        public static final TagKey<Item> LUNAR_LOGS = create("lunar_logs");
        public static final TagKey<Item> NORTHLAND_LOGS = create("northland_logs");
        public static final TagKey<Item> STARLIGHT_MANGROVE_LOGS = create("starlight_mangrove_logs");
        public static final TagKey<Item> THERMAL_SPRINGSTONE_WEAPONS = create("thermal_springstone_weapons");
        public static final TagKey<Item> DOOMEDEN_KEYS = create("doomeden_keys");
        // astral sorcery system stuff
        public static final TagKey<Item> RIESTURUS_LOCATORS = create("riesturus_locators");
        public static final TagKey<Item> RINEMIN_LOCATORS = create("rinemin_locators");
        public static final TagKey<Item> ANRLO_LOCATORS = create("anrlo_locators");
        public static final TagKey<Item> CRATEVIRGO_LOCATORS = create("cratevirgo_locators");
        public static final TagKey<Item> LIBRASCRP_LOCATORS = create("librascrp_locators");
        public static final TagKey<Item> OPHUCHAGAR_LOCATORS = create("ophuchagar_locators");
        public static final TagKey<Item> APORNIGAS_LOCATORS = create("aporniqas_locators");
        public static final TagKey<Item> EGASUSICE_LOCATORS = create("egasusice_locators");
        public static final TagKey<Item> STARATHER_LOCATORS = create("starather_locators");
    }

    public static class Blocks {
        private static TagKey<Block> create(String string) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(EternalStarlight.MOD_ID, string));
        }
        public static final TagKey<Block> LUNAR_LOGS = create("lunar_logs");
        public static final TagKey<Block> NORTHLAND_LOGS = create("northland_logs");
        public static final TagKey<Block> STARLIGHT_MANGROVE_LOGS = create("starlight_mangrove_logs");
        public static final TagKey<Block> PORTAL_FRAME_BLOCKS = create("portal_frame_blocks");
        public static final TagKey<Block> BASE_STONE_STARLIGHT = create("base_stone_starlight");
        public static final TagKey<Block> CORAL_PLANTS = create("coral_plants");
        public static final TagKey<Block> CORALS = create("corals");
        public static final TagKey<Block> WALL_CORALS = create("wall_corals");
        public static final TagKey<Block> CORAL_BLOCKS = create("coral_blocks");
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
}
