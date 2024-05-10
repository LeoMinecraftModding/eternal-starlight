package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.registry.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformer;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import com.mojang.serialization.MapCodec;

public class FinalizeBiomesTransformer extends DataTransformer {
    public static final MapCodec<FinalizeBiomesTransformer> CODEC = MapCodec.unit(FinalizeBiomesTransformer::new);

    @Override
    public int[][] transform(int[][] original, int[][] related, WorldGenProvider provider, int areaX, int areaZ, int size, long seed, long seedAddition) {
        int[][] transformed = new int[size / 2][size / 2];
        for (int x = 0; x < size / 2; x++) {
            for (int z = 0; z < size / 2; z++) {
                int data = original[x + size / 4][z + size / 4];
                transformed[x][z] = data;
            }
        }
        return transformed;
    }

    @Override
    public DataTransformerType<?> type() {
        return ESDataTransformerTypes.FINALIZE_BIOMES.get();
    }
}
