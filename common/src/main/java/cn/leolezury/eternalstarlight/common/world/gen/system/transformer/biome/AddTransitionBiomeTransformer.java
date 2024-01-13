package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.init.DataTransformerTypeInit;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NeighborsRelatedTransformer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

import java.util.Random;

public class AddTransitionBiomeTransformer extends NeighborsRelatedTransformer {
    public static final Codec<AddTransitionBiomeTransformer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("biome").forGetter(o -> o.biome),
            RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("transition_biome").forGetter(o -> o.transitionBiome)
    ).apply(instance, AddTransitionBiomeTransformer::new));

    private final Holder<BiomeData> transitionBiome;
    private Integer transitionId = null;
    private final Holder<BiomeData> biome;
    private Integer id = null;
    public AddTransitionBiomeTransformer(Holder<BiomeData> biome, Holder<BiomeData> transitionBiome) {
        this.biome = biome;
        this.transitionBiome = transitionBiome;
    }

    @Override
    public int transform(WorldGenProvider provider, Random random, int original, int up, int down, int left, int right) {
        if (transitionId == null) {
            transitionId = provider.biomeDataRegistry.getId(transitionBiome.value());
        }
        if (id == null) {
            id = provider.biomeDataRegistry.getId(biome.value());
        }
        if (original == id && (up != id || down != id || left != id || right != id)) {
            return transitionId;
        }
        return original;
    }

    @Override
    public DataTransformerType<?> type() {
        return DataTransformerTypeInit.ADD_TRANSITION.get();
    }
}
