package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NoiseDataTransformer;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.Random;

public class AddTheAbyssTransformer implements NoiseDataTransformer {
    private final int abyss;

    public AddTheAbyssTransformer(int abyss) {
        this.abyss = abyss;
    }

    @Override
    public int transform(AbstractWorldGenProvider provider, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise) {
        if (BiomeDataRegistry.getBiomeData(original).isOcean()) {
            double noiseVal =
                    noise.getValue(worldX * 0.0025, worldZ * 0.0025, false) * 0.50d
                    + noise.getValue(worldX * 0.0075, worldZ * 0.0075, true) * 0.25d
                    + noise.getValue(worldX * 0.025, worldZ * 0.025, true) * 0.025d;
            if (noiseVal > -0.02 && noiseVal < 0.02) {
                return this.abyss;
            }
        }
        return original;
    }
}
