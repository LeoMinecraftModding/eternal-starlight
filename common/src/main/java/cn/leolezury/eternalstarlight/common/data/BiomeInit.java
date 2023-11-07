package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.SoundEventInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class BiomeInit {
    public static final ResourceKey<Biome> STARLIGHT_FOREST = create("starlight_forest");
    public static final ResourceKey<Biome> STARLIGHT_DENSE_FOREST = create("starlight_dense_forest");
    public static final ResourceKey<Biome> STARLIGHT_PERMAFROST_FOREST = create("starlight_permafrost_forest");
    public static final ResourceKey<Biome> DARK_SWAMP = create("dark_swamp");
    public static final ResourceKey<Biome> SHIMMER_RIVER = create("shimmer_river");
    public static final ResourceKey<Biome> STARLIT_SEA = create("starlit_sea");
    public static final ResourceKey<Biome> WARM_SHORE = create("warm_shore");

    public static final Music MUSIC_DEFAULT = new Music(SoundEventInit.MUSIC_DIMENSION_SL.asHolder(), 1200, 12000, true);
    public static final Music MUSIC_FOREST = new Music(SoundEventInit.MUSIC_BIOME_STARLIGHT_FOREST.asHolder(), 1200, 12000, true);
    public static final Music MUSIC_SWAMP = new Music(SoundEventInit.MUSIC_BIOME_DARK_SWAMP.asHolder(), 1200, 12000, true);
    public static final Music MUSIC_PERMAFROST_FOREST = new Music(SoundEventInit.MUSIC_BIOME_STARLIGHT_PERMAFROST_FOREST.asHolder(), 1200, 12000, true);

    public static void bootstrap(BootstapContext<Biome> context) {
        // W. I. P.
    }

    public static Biome.BiomeBuilder baseBiomeBuilder(BiomeSpecialEffects.Builder specialEffects, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder genSettings) {
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5F)
                .downfall(0.5F)
                .specialEffects(specialEffects.build())
                .mobSpawnSettings(mobSpawnSettings.build())
                .generationSettings(genSettings.build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE);
    }

    public static BiomeSpecialEffects.Builder baseEffectsBuilder() {
        return new BiomeSpecialEffects.Builder()
                .fogColor(3091031)
                .foliageColorOverride(3091031)
                .grassColorOverride(3091031)
                .waterColor(6187416)
                .waterFogColor(6187416)
                .skyColor(5658761)
                .backgroundMusic(MUSIC_DEFAULT);
    }

    public static ResourceKey<Biome> create(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}
