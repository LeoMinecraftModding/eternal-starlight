package cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.biome.interfaces;

import cn.leolezury.eternalstarlight.common.world.gen.provider.area.BiomesContainer;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.Random;

public interface NoiseDataTransformer extends BiomeDataTransformer {
    @Override
    default int transform(BiomesContainer container, Random random, int x, int z) {
        return transform(container, random, container.getDataSafe(x, z), container.getWorldX(x), container.getWorldZ(z), container.provider.noises[0]);
    }

    int transform(BiomesContainer container, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise);
}
