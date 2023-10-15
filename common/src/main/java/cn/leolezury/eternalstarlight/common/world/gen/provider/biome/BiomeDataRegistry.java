package cn.leolezury.eternalstarlight.common.world.gen.provider.biome;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BiomeDataRegistry {
    private static final Map<ResourceLocation, BiomeData> LOCATION_TO_DATA = new HashMap<>();
    private static final Map<Integer, ResourceLocation> ID_TO_LOCATION = new HashMap<>();
    private static final Map<ResourceLocation, Integer> LOCATION_TO_ID = new HashMap<>();
    private static int currentId = 0;

    public static BiomeData getBiomeData(ResourceLocation location) {
        return LOCATION_TO_DATA.get(location);
    }
    public static BiomeData getBiomeData(int id) {
        return LOCATION_TO_DATA.get(getBiomeLocation(id));
    }
    public static ResourceLocation getBiomeLocation(int id) {
        return ID_TO_LOCATION.get(id);
    }
    public static int getBiomeId(ResourceLocation location) {
        return LOCATION_TO_ID.get(location);
    }

    public static final int STARLIGHT_FOREST = register(new BiomeData.Builder(new ResourceLocation(EternalStarlight.MOD_ID, "starlight_forest"), 70, 15).withTemperatures(BiomeData.Temperature.NEUTRAL, BiomeData.Temperature.HOT, BiomeData.Temperature.COLD).build());
    public static final int STARLIGHT_DENSE_FOREST = register(new BiomeData.Builder(new ResourceLocation(EternalStarlight.MOD_ID, "starlight_dense_forest"), 70, 15).withTemperatures(BiomeData.Temperature.NEUTRAL, BiomeData.Temperature.HOT, BiomeData.Temperature.COLD).build());
    public static final int DARK_SWAMP = register(new BiomeData.Builder(new ResourceLocation(EternalStarlight.MOD_ID, "dark_swamp"), 60, 5).withTemperatures(BiomeData.Temperature.NEUTRAL, BiomeData.Temperature.HOT, BiomeData.Temperature.HOT_EXTREME).build());
    public static final int STARLIGHT_PERMAFROST_FOREST = register(new BiomeData.Builder(new ResourceLocation(EternalStarlight.MOD_ID, "starlight_permafrost_forest"), 110, 50).withTemperatures(BiomeData.Temperature.COLD, BiomeData.Temperature.COLD_EXTREME).hasBeaches(false).hasRivers(false).build());
    public static final int SHIMMER_RIVER = register(new BiomeData.Builder(new ResourceLocation(EternalStarlight.MOD_ID, "shimmer_river"), 30, 5).build());
    public static final int SHIMMER_RIVER_TRANSITION = register(new BiomeData.Builder(new ResourceLocation(EternalStarlight.MOD_ID, "shimmer_river_transition"), new ResourceLocation(EternalStarlight.MOD_ID, "shimmer_river"), 40, 3).withMaxValleyDepth(2).build());
    public static final int STARLIT_SEA = register(new BiomeData.Builder(new ResourceLocation(EternalStarlight.MOD_ID, "starlit_sea"), 40, 5).fullRangeTemperatures().isOcean(true).build());
    public static final int WARM_SHORE = register(new BiomeData.Builder(new ResourceLocation(EternalStarlight.MOD_ID, "warm_shore"), 60, 5).withMaxValleyDepth(0).build());

    private static int register(BiomeData data) {
        LOCATION_TO_DATA.put(data.name(), data);
        ID_TO_LOCATION.put(currentId, data.name());
        LOCATION_TO_ID.put(data.name(), currentId);
        currentId++;
        return currentId - 1;
    }
}
