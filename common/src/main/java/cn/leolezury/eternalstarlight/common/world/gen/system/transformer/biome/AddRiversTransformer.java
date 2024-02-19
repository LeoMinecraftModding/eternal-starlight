package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.init.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NoiseDataTransformer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.List;
import java.util.Random;

public class AddRiversTransformer extends NoiseDataTransformer {
    public static final Codec<AddRiversTransformer> CODEC = RiverWithOffset.CODEC.listOf().fieldOf("rivers").xmap(AddRiversTransformer::new, transformer -> transformer.rivers).codec();

    private final List<RiverWithOffset> rivers;
    private final IntArrayList idList = new IntArrayList();

    public AddRiversTransformer(List<RiverWithOffset> rivers) {
        this.rivers = rivers;
    }

    @Override
    public int transform(WorldGenProvider provider, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise) {
        if (idList.isEmpty()) {
            for (RiverWithOffset river : rivers) {
                idList.add(provider.biomeDataRegistry.getId(river.river().value()));
            }
        }
        if (provider.biomeDataRegistry.byId(original).canHaveRivers()) {
            for (int i = 0; i < rivers.size(); i++) {
                RiverWithOffset river = rivers.get(i);
                double noiseVal =
                        noise.getValue((worldX + river.offset()) * 0.0025, (worldZ + river.offset()) * 0.0025, false) * 0.50d
                                + noise.getValue((worldX + river.offset()) * 0.0075, (worldZ + river.offset()) * 0.0075, true) * 0.25d
                                + noise.getValue((worldX + river.offset()) * 0.025, (worldZ + river.offset()) * 0.025, true) * 0.025d;
                if (noiseVal > -river.size() && noiseVal < river.size()) {
                    return idList.getInt(i);
                }
            }
        }
        return original;
    }

    @Override
    public DataTransformerType<?> type() {
        return ESDataTransformerTypes.ADD_RIVERS.get();
    }

    public record RiverWithOffset(Holder<BiomeData> river, float size, int offset) {
        public static final Codec<RiverWithOffset> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("river").forGetter(RiverWithOffset::river),
                Codec.FLOAT.fieldOf("size").forGetter(RiverWithOffset::size),
                Codec.INT.fieldOf("offset").forGetter(RiverWithOffset::offset)
        ).apply(instance, RiverWithOffset::new));
    }
}
