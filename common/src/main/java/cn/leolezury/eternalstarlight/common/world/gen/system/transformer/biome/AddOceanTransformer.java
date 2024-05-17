package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.Random;

public class AddOceanTransformer extends NoiseDataTransformer {
    public static final MapCodec<AddOceanTransformer> CODEC = RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("ocean").xmap(AddOceanTransformer::new, transformer -> transformer.ocean);

    private final Holder<BiomeData> ocean;
    private Integer id = null;

    public AddOceanTransformer(Holder<BiomeData> ocean) {
        this.ocean = ocean;
    }

    @Override
    public int transform(WorldGenProvider provider, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise) {
        if (id == null) {
            id = provider.getBiomeDataId(ocean.value());
        }
        return noise.getValue(worldX * 0.15, worldZ * 0.15, true) > 0.3 ? id : original;
    }

    @Override
    public DataTransformerType<?> type() {
        return ESDataTransformerTypes.ADD_OCEAN.get();
    }
}
