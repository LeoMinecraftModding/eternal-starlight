package cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.BiomesContainer;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.biome.interfaces.NeighborsRelatedTransformer;

import java.util.Random;

public class RandomizeBiomesTransformer implements NeighborsRelatedTransformer {
    public RandomizeBiomesTransformer(){}

    @Override
    public int transform(BiomesContainer container, Random random, int original, int up, int down, int left, int right) {
        // we should only randomize the lonely ones
        if (original != up || original != down || original != left || original != right) {
            return chooseRandomly(random, up, down, left, right);
        }
        return original;
    }
}
