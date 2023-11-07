package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.height;

import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformer;
import net.minecraft.util.Mth;

public class FinalizeHeightsTransformer implements DataTransformer {
    @Override
    public int[][] transform(int[][] original, int[][] related, AbstractWorldGenProvider provider, int areaX, int areaZ, int size, long seed, long seedAddition) {
        int[][] transformed = new int[size / 2][size / 2];
        for (int x = 0; x < size / 2; x++) {
            for (int z = 0; z < size / 2; z++) {
                int data = original[x + size / 4][z + size / 4];
                transformed[x][z] = Mth.clamp(data, provider.minHeight, provider.maxHeight);
            }
        }
        return transformed;
    }
}
