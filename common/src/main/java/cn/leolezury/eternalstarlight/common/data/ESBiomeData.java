package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeData;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ESBiomeData {
    public static final ResourceKey<BiomeData> STARLIGHT_FOREST = create("starlight_forest");
    public static final ResourceKey<BiomeData> STARLIGHT_DENSE_FOREST = create("starlight_dense_forest");
    public static final ResourceKey<BiomeData> STARLIGHT_PERMAFROST_FOREST = create("starlight_permafrost_forest");
    public static final ResourceKey<BiomeData> DARK_SWAMP = create("dark_swamp");
    public static final ResourceKey<BiomeData> SCARLET_FOREST = create("scarlet_forest");
    public static final ResourceKey<BiomeData> TORREYA_FOREST = create("torreya_forest");
    public static final ResourceKey<BiomeData> CRYSTALLIZED_DESERT = create("crystallized_desert");
    public static final ResourceKey<BiomeData> SHIMMER_RIVER = create("shimmer_river");
    public static final ResourceKey<BiomeData> ETHER_RIVER = create("ether_river");
    public static final ResourceKey<BiomeData> SHIMMER_RIVER_TRANSITION = create("shimmer_river_transition");
    public static final ResourceKey<BiomeData> STARLIT_SEA = create("starlit_sea");
    public static final ResourceKey<BiomeData> THE_ABYSS = create("the_abyss");
    public static final ResourceKey<BiomeData> THE_ABYSS_TRANSITION = create("the_abyss_transition");
    public static final ResourceKey<BiomeData> WARM_SHORE = create("warm_shore");

    public static void bootstrap(BootstrapContext<BiomeData> context) {
        HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
        context.register(STARLIGHT_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_FOREST), 61, 10).withTemperatures(BiomeData.Temperature.NEUTRAL, BiomeData.Temperature.HOT, BiomeData.Temperature.COLD).build());
        context.register(STARLIGHT_DENSE_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_DENSE_FOREST), 61, 10).withTemperatures(BiomeData.Temperature.NEUTRAL, BiomeData.Temperature.HOT, BiomeData.Temperature.COLD).build());
        context.register(DARK_SWAMP, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.DARK_SWAMP), 61, 5).withTemperatures(BiomeData.Temperature.NEUTRAL, BiomeData.Temperature.HOT).build());
        context.register(STARLIGHT_PERMAFROST_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_PERMAFROST_FOREST), 110, 55).withTemperatures(BiomeData.Temperature.COLD, BiomeData.Temperature.COLD_EXTREME).hasBeaches(false).hasRivers(false).build());
        context.register(SCARLET_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SCARLET_FOREST), 63, 12).withTemperatures(BiomeData.Temperature.NEUTRAL, BiomeData.Temperature.HOT).build());
        context.register(TORREYA_FOREST, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.TORREYA_FOREST), 63, 12).build());
        context.register(CRYSTALLIZED_DESERT, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.CRYSTALLIZED_DESERT), 60, 9).withTemperatures(BiomeData.Temperature.HOT, BiomeData.Temperature.HOT_EXTREME).hasRivers(false).build());
        context.register(SHIMMER_RIVER, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SHIMMER_RIVER), 40, 3).build());
        context.register(SHIMMER_RIVER_TRANSITION, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.SHIMMER_RIVER), 45, 3).build());
        context.register(ETHER_RIVER, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.ETHER_RIVER), 45, 3).withFluid(ESBlocks.ETHER.get()).build());
        context.register(STARLIT_SEA, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.STARLIT_SEA), 30, 5).fullRangeTemperatures().isOcean(true).build());
        context.register(THE_ABYSS, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.THE_ABYSS), -55, 5).isOcean(true).build());
        context.register(THE_ABYSS_TRANSITION, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.THE_ABYSS), -40, 3).isOcean(true).build());
        context.register(WARM_SHORE, new BiomeData.Builder(biomeHolderGetter.getOrThrow(ESBiomes.WARM_SHORE), 55, 4).build());
    }

    public static ResourceKey<BiomeData> create(String name) {
        return ResourceKey.create(ESRegistries.BIOME_DATA, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}
