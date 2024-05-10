package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NoiseDataTransformer;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.Random;

public class AddTheAbyssTransformer extends NoiseDataTransformer {
    public static final MapCodec<AddTheAbyssTransformer> CODEC = RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("the_abyss").xmap(AddTheAbyssTransformer::new, transformer -> transformer.abyss);

    private final Holder<BiomeData> abyss;
    private Integer id = null;

    public AddTheAbyssTransformer(Holder<BiomeData> abyss) {
        this.abyss = abyss;
    }

    @Override
    public int transform(WorldGenProvider provider, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise) {
        if (id == null) {
            id = provider.getBiomeDataId(abyss.value());
        }
        if (provider.getBiomeDataById(original).isOcean()) {
            double noiseVal =
                    noise.getValue(worldX * 0.0025, worldZ * 0.0025, false) * 0.50d
                    + noise.getValue(worldX * 0.0075, worldZ * 0.0075, true) * 0.25d
                    + noise.getValue(worldX * 0.025, worldZ * 0.025, true) * 0.025d;
            if (noiseVal > -0.04 && noiseVal < 0.04) {
                return id;
            }
        }
        return original;
    }

    @Override
    public DataTransformerType<?> type() {
        return ESDataTransformerTypes.ADD_THE_ABYSS.get();
    }
}
