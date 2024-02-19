package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height;

import cn.leolezury.eternalstarlight.common.init.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.IterationWithCullTransformer;
import com.mojang.serialization.Codec;

import java.util.Random;

public class SmoothHeightsTransformer extends IterationWithCullTransformer {
    public static final Codec<SmoothHeightsTransformer> CODEC = Codec.INT.fieldOf("size").xmap(SmoothHeightsTransformer::new, transformer -> transformer.size).codec();

    private final int size;

    public SmoothHeightsTransformer(int size) {
        this.size = size;
    }

    @Override
    public int transform(int[][] original, int[][] related, WorldGenProvider provider, Random random, int x, int z, int areaX, int areaZ, int size) {
        int totalCount = 0;
        int total = 0;
        for (int i = -this.size; i <= this.size; i++) {
            for (int j = -this.size; j <= this.size; j++) {
                total += getDataSafe(original, x + i, z + j, size);
                totalCount++;
            }
        }
        return total / totalCount;
    }

    @Override
    public DataTransformerType<?> type() {
        return ESDataTransformerTypes.SMOOTH_HEIGHTS.get();
    }
}
