package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.init.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NeighborsRelatedTransformer;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

import java.util.Random;

public class AddBeachesTransformer extends NeighborsRelatedTransformer {
    public static final Codec<AddBeachesTransformer> CODEC = RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("beach").xmap(AddBeachesTransformer::new, transformer -> transformer.beach).codec();

    private final Holder<BiomeData> beach;
    private Integer id = null;

    public AddBeachesTransformer(Holder<BiomeData> beach) {
        this.beach = beach;
    }

    @Override
    public int transform(WorldGenProvider provider, Random random, int original, int up, int down, int left, int right) {
        if (id == null) {
            id = provider.biomeDataRegistry.getId(beach.value());
        }
        if (provider.biomeDataRegistry.byId(original).isOcean()
                && (provider.biomeDataRegistry.byId(up).canHaveBeaches()
                || provider.biomeDataRegistry.byId(down).canHaveBeaches()
                || provider.biomeDataRegistry.byId(left).canHaveBeaches()
                || provider.biomeDataRegistry.byId(right).canHaveBeaches())
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
