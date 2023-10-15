package cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.provider.area.BiomesContainer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.biome.interfaces.NoiseDataTransformer;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.Random;

public class AddRiverTransformer implements NoiseDataTransformer {
    private final int river;

    public AddRiverTransformer(int river) {
        this.river = river;
    }

    @Override
    public int transform(BiomesContainer container, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise) {
        if (BiomeDataRegistry.getBiomeData(original).canHaveRivers()) {
            double noiseVal =
                    noise.getValue(worldX * 0.0025, worldZ * 0.0025, false) * 0.50d
                    + noise.getValue(worldX * 0.0075, worldZ * 0.0075, true) * 0.25d
                    + noise.getValue(worldX * 0.025, worldZ * 0.025, true) * 0.025d;
            if (noiseVal > -0.02 && noiseVal < 0.02) {
                return this.river;
            }
        }
        return original;
    }
}
