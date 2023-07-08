package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BiomeInit {
    public static final ResourceKey<Biome> FROST_FOREST_KEY = ResourceKey.create(Registries.BIOME, new ResourceLocation(EternalStarlight.MOD_ID, "starlight_permafrost_forest"));
}
