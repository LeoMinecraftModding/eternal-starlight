package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.system.provider.AbstractWorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NeighborsRelatedTransformer;

import java.util.Random;

public class RandomizeBiomesTransformer implements NeighborsRelatedTransformer {
    @Override
    public int transform(AbstractWorldGenProvider provider, Random random, int original, int up, int down, int left, int right) {
        // we should only randomize the lonely ones
        if (original != up || original != down || original != left || original != right) {
            return chooseRandomly(random, up, down, left, right);
        }
        return original;
    }
}
