package cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.provider.area.BiomesContainer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.provider.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.biome.interfaces.NoiseDataTransformer;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.Random;

public class AddBiomesTransformer implements NoiseDataTransformer {
    private final double xzScale;

    public AddBiomesTransformer(double xzScale) {
        this.xzScale = xzScale;
    }

    @Override
    public int transform(BiomesContainer container, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise) {
        BiomeData.Temperature temperature;
        double noiseVal = noise.getValue(worldX * xzScale, worldZ * xzScale, false);
        if (noiseVal >= 0.6) {
            temperature = BiomeData.Temperature.HOT_EXTREME;
        } else if (noiseVal >= 0.2) {
            temperature = BiomeData.Temperature.HOT;
        } else if (noiseVal >= -0.2) {
            temperature = BiomeData.Temperature.NEUTRAL;
        } else if (noiseVal >= -0.6) {
            temperature = BiomeData.Temperature.COLD;
        } else {
            temperature = BiomeData.Temperature.COLD_EXTREME;
        }
        return container.provider.getRandomBiome(temperature, BiomeDataRegistry.getBiomeData(original).isOcean(), random);
    }
}
