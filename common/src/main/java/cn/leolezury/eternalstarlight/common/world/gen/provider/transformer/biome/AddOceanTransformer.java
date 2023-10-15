package cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.provider.area.BiomesContainer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.biome.interfaces.NoiseDataTransformer;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.Random;

public class AddOceanTransformer implements NoiseDataTransformer {
    private final int ocean;

    public AddOceanTransformer(int ocean) {
        this.ocean = ocean;
    }

    @Override
    public int transform(BiomesContainer container, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise) {
        return noise.getValue(worldX * 0.1, worldZ * 0.1, true) > 0.3 ? this.ocean : original;
    }
}
