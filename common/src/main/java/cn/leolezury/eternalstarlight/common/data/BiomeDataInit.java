package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeData;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BiomeDataInit {
    public static final ResourceKey<BiomeData> STARLIGHT_FOREST = create("starlight_forest");
    public static final ResourceKey<BiomeData> STARLIGHT_DENSE_FOREST = create("starlight_dense_forest");
    public static final ResourceKey<BiomeData> STARLIGHT_PERMAFROST_FOREST = create("starlight_permafrost_forest");
    public static final ResourceKey<BiomeData> DARK_SWAMP = create("dark_swamp");
    public static final ResourceKey<BiomeData> SCARLET_FOREST = create("scarlet_forest");
    public static final ResourceKey<BiomeData> CRYSTALLIZED_DESERT = create("crystallized_desert");
    public static final ResourceKey<BiomeData> SHIMMER_RIVER = create("shimmer_river");
    public static final ResourceKey<BiomeData> SHIMMER_RIVER_TRANSITION = create("shimmer_river_transition");
    public static final ResourceKey<BiomeData> STARLIT_SEA = create("starlit_sea");
    public static final ResourceKey<BiomeData> THE_ABYSS = create("the_abyss");
    public static final ResourceKey<BiomeData> THE_ABYSS_TRANSITION = create("the_abyss_transition");
    public static final ResourceKey<BiomeData> WARM_SHORE = create("warm_shore");

    public static void bootstrap(BootstapContext<BiomeData> context) {
        HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
        context.register(STARLIGHT_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.STARLIGHT_FOREST), 70, 15).withTemperatures(BiomeData.Temperature.NEUTRAL, BiomeData.Temperature.HOT, BiomeData.Temperature.COLD).build());
        context.register(STARLIGHT_DENSE_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.STARLIGHT_DENSE_FOREST), 70, 15).withTemperatures(BiomeData.Temperature.NEUTRAL, BiomeData.Temperature.HOT, BiomeData.Temperature.COLD).build());
        context.register(DARK_SWAMP, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.DARK_SWAMP), 60, 5).withTemperatures(BiomeData.Temperature.NEUTRAL, BiomeData.Temperature.HOT).build());
        context.register(STARLIGHT_PERMAFROST_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.STARLIGHT_PERMAFROST_FOREST), 110, 50).withTemperatures(BiomeData.Temperature.COLD, BiomeData.Temperature.COLD_EXTREME).hasBeaches(false).hasRivers(false).build());
        context.register(SCARLET_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.SCARLET_FOREST), 70, 15).withTemperatures(BiomeData.Temperature.NEUTRAL, BiomeData.Temperature.HOT).build());
        context.register(CRYSTALLIZED_DESERT, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.CRYSTALLIZED_DESERT), 60, 10).withTemperatures(BiomeData.Temperature.HOT, BiomeData.Temperature.HOT_EXTREME).hasRivers(false).build());
        context.register(SHIMMER_RIVER, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.SHIMMER_RIVER), 30, 5).build());
        context.register(SHIMMER_RIVER_TRANSITION, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.SHIMMER_RIVER), 40, 3).build());
        context.register(STARLIT_SEA, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.STARLIT_SEA), 30, 5).fullRangeTemperatures().isOcean(true).build());
        context.register(THE_ABYSS, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.THE_ABYSS), -55, 5).fullRangeTemperatures().isOcean(true).build());
        context.register(THE_ABYSS_TRANSITION, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.THE_ABYSS), -20, 3).isOcean(true).build());
        context.register(WARM_SHORE, new BiomeData.Builder(biomeHolderGetter.getOrThrow(BiomeInit.WARM_SHORE), 60, 5).build());
    }

    public static ResourceKey<BiomeData> create(String name) {
        return ResourceKey.create(ESRegistries.BIOME_DATA, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}
