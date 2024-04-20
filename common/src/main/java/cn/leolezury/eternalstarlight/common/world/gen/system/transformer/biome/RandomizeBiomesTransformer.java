package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.registry.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NeighborsRelatedTransformer;
import com.mojang.serialization.MapCodec;

import java.util.Random;

public class RandomizeBiomesTransformer extends NeighborsRelatedTransformer {
    public static final MapCodec<RandomizeBiomesTransformer> CODEC = MapCodec.unit(RandomizeBiomesTransformer::new);

    @Override
    public int transform(WorldGenProvider provider, Random random, int original, int up, int down, int left, int right) {
        // we should only randomize the lonely ones
        if (original != up || original != down || original != left || original != right) {
            return chooseRandomly(random, up, down, left, right);
        }
        return original;
    }

    @Override
    public DataTransformerType<?> type() {
        return ESDataTransformerTypes.RANDOMIZE.get();
    }
}
