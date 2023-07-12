package cn.leolezury.eternalstarlight.util;

import cn.leolezury.eternalstarlight.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class SLTags {
    public static class Items {
        public static final TagKey<Item> THERMAL_SPRINGSTONE_WEAPONS = ItemTags.create(new ResourceLocation(EternalStarlight.MOD_ID, "thermal_springstone_weapons"));
    }

    public static class Blocks {
        public static final TagKey<Block> PORTAL_FRAME_BLOCKS = BlockTags.create(new ResourceLocation(EternalStarlight.MOD_ID, "portal_frame_blocks"));
        public static final TagKey<Block> BASE_STONE_STARLIGHT = BlockTags.create(new ResourceLocation(EternalStarlight.MOD_ID, "base_stone_starlight"));
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
        public static final TagKey<Biome> STARLIGHT_FOREST_VARIANT = TagKey.create(Registries.BIOME, new ResourceLocation(EternalStarlight.MOD_ID, "starlight_forest_variant"));
        public static final TagKey<Biome> PERMAFROST_FOREST_VARIANT = TagKey.create(Registries.BIOME, new ResourceLocation(EternalStarlight.MOD_ID, "permafrost_forest_variant"));
        public static final TagKey<Biome> DARK_SWAMP_VARIANT = TagKey.create(Registries.BIOME, new ResourceLocation(EternalStarlight.MOD_ID, "dark_swamp_variant"));
    }
}
