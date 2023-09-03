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
        public static final TagKey<Item> THERMAL_SPRINGSTONE_WEAPONS = create("thermal_springstone_weapons");
        public static final TagKey<Item> RIESTURUS_LOCATE_ITEM = create("riesturus_locate_item");
        public static final TagKey<Item> RINEMIN_LOCATE_ITEM = create("rinemin_locate_item");
        public static final TagKey<Item> ANRLO_LOCATE_ITEM = create("anrlo_locate_item");
        public static final TagKey<Item> CRATEVIRGO_LOCATE_ITEM = create("cratevirgo_locate_item");
        public static final TagKey<Item> LIBRASCRP_LOCATE_ITEM = create("librascrp_locate_item");
        public static final TagKey<Item> OPHUCHAGAR_LOCATE_ITEM = create("ophuchagar_locate_item");
        public static final TagKey<Item> APORNIGAS_LOCATE_ITEM = create("aporniqas_locate_item");
        public static final TagKey<Item> EGASUSICE_LOCATE_ITEM = create("egasusice_locate_item");
        public static final TagKey<Item> STARATHER_LOCATE_ITEM = create("starather_locate_item");
    }

    public static class Blocks {
        private static TagKey<Block> create(String string) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(EternalStarlight.MOD_ID, string));
        }
        public static final TagKey<Block> PORTAL_FRAME_BLOCKS = create("portal_frame_blocks");
        public static final TagKey<Block> BASE_STONE_STARLIGHT = create("base_stone_starlight");
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
