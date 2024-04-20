package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.registry.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformer;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import com.mojang.serialization.MapCodec;

public class DuplicateSizeTransformer extends DataTransformer {
    public static final MapCodec<DuplicateSizeTransformer> CODEC = MapCodec.unit(DuplicateSizeTransformer::new);

    @Override
    public int[][] transform(int[][] original, int[][] related, WorldGenProvider provider, int areaX, int areaZ, int size, long seed, long seedAddition) {
        int newSize = size * 2;
        int[][] transformed = new int[newSize][newSize];
        for (int x = 0; x < newSize; x++) {
            for (int z = 0; z < newSize; z++) {
                transformed[x][z] = original[x / 2][z / 2];
            }
        }
        return transformed;
    }

    @Override
    public DataTransformerType<?> type() {
        return ESDataTransformerTypes.DUPLICATE.get();
    }
}
