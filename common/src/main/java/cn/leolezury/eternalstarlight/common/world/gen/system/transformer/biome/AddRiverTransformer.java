package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.init.DataTransformerTypeInit;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NoiseDataTransformer;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.Random;

public class AddRiverTransformer extends NoiseDataTransformer {
    public static final Codec<AddRiverTransformer> CODEC = RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("river").xmap(AddRiverTransformer::new, transformer -> transformer.river).codec();

    private final Holder<BiomeData> river;
    private Integer id = null;

    public AddRiverTransformer(Holder<BiomeData> river) {
        this.river = river;
    }

    @Override
    public int transform(WorldGenProvider provider, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise) {
        if (id == null) {
            id = provider.biomeDataRegistry.getId(river.value());
        }
        if (provider.biomeDataRegistry.byId(original).canHaveRivers()) {
            double noiseVal =
                    noise.getValue(worldX * 0.0025, worldZ * 0.0025, false) * 0.50d
                    + noise.getValue(worldX * 0.0075, worldZ * 0.0075, true) * 0.25d
                    + noise.getValue(worldX * 0.025, worldZ * 0.025, true) * 0.025d;
            if (noiseVal > -0.02 && noiseVal < 0.02) {
                return id;
            }
        }
        return original;
    }

    @Override
    public DataTransformerType<?> type() {
        return DataTransformerTypeInit.ADD_RIVER.get();
    }
}
