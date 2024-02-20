package cn.leolezury.eternalstarlight.common.world.gen.system.transformer;

import cn.leolezury.eternalstarlight.common.registry.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.WorldGenProvider;
import com.mojang.serialization.Codec;

import java.util.Random;

public abstract class DataTransformer {
    public static final Codec<DataTransformer> CODEC = ESDataTransformerTypes.CODEC.dispatch("transformer_type", DataTransformer::type, DataTransformerType::codec);

    public abstract DataTransformerType<?> type();

    public abstract int[][] transform(int[][] original, int[][] related, WorldGenProvider provider, int areaX, int areaZ, int size, long seed, long seedAddition);

    protected int getDataSafe(int[][] data, int x, int z, int size) {
        int safeX = x < 0 ? 0 : Math.min(x, size - 1);
        int safeZ = z < 0 ? 0 : Math.min(z, size - 1);
        return data[safeX][safeZ];
    }

    protected int getWorldCoord(int coord, int areaCoord, int size) {
        return areaCoord * (size / 2) + coord;
    }

    protected Random getRandomForPos(int x, int z, int areaX, int areaZ, int size, long seedWithAddition) {
        return new Random(seedWithAddition * WorldGenProvider.posAsLong(getWorldCoord(x, areaX, size), getWorldCoord(z, areaZ, size)));
    }
}
