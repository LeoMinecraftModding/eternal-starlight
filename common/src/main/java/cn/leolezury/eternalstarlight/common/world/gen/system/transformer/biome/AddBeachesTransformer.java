package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NeighborsRelatedTransformer;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

import java.util.Random;

public class AddBeachesTransformer extends NeighborsRelatedTransformer {
    public static final MapCodec<AddBeachesTransformer> CODEC = RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("beach").xmap(AddBeachesTransformer::new, transformer -> transformer.beach);

    private final Holder<BiomeData> beach;
    private Integer id = null;

    public AddBeachesTransformer(Holder<BiomeData> beach) {
        this.beach = beach;
    }

    @Override
    public int transform(WorldGenProvider provider, Random random, int original, int up, int down, int left, int right) {
        if (id == null) {
            id = provider.getBiomeDataId(beach.value());
        }
        if (provider.getBiomeDataById(original).isOcean()
                && (provider.getBiomeDataById(up).canHaveBeaches()
                || provider.getBiomeDataById(down).canHaveBeaches()
                || provider.getBiomeDataById(left).canHaveBeaches()
                || provider.getBiomeDataById(right).canHaveBeaches())
        ) {
            return id;
        }
        return original;
    }

    @Override
    public DataTransformerType<?> type() {
        return ESDataTransformerTypes.ADD_BEACHES.get();
    }
}
