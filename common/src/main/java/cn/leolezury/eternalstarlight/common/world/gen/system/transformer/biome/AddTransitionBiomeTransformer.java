package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NeighborsRelatedTransformer;

import java.util.Random;

public class AddTransitionBiomeTransformer implements NeighborsRelatedTransformer {
    private final int transitionBiome;
    private final int biome;

    public AddTransitionBiomeTransformer(int biome, int transitionBiome) {
        this.biome = biome;
        this.transitionBiome = transitionBiome;
    }

    @Override
    public int transform(AbstractWorldGenProvider provider, Random random, int original, int up, int down, int left, int right) {
        if (original == biome && (up != biome || down != biome || left != biome || right != biome)) {
            return transitionBiome;
        }
        return original;
    }
}
