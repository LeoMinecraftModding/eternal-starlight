package cn.leolezury.eternalstarlight.common.world.gen.biomesource;

import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.ESWorldGenProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ESBiomeSource extends BiomeSource {
    public static final Codec<ESBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(o -> o.biomeHolderSet)
    ).apply(instance, instance.stable(ESBiomeSource::new)));

    public final Map<ResourceLocation, Holder<Biome>> biomeMap = new HashMap<>();
    private final HolderSet<Biome> biomeHolderSet;
    private ESWorldGenProvider provider;

    public ESBiomeSource(HolderSet<Biome> biomeHolderSet) {
        this.biomeHolderSet = biomeHolderSet;
        this.biomeMap.putAll(biomeHolderSet.stream().collect(Collectors.toMap(biomeHolder -> biomeHolder.unwrapKey().orElseThrow().location(), Function.identity())));
    }

    public void setSeed(long seed) {
        this.provider.setSeed(seed);
    }

    public void setHeights(int maxHeight, int minHeight) {
        this.provider = new ESWorldGenProvider(maxHeight, minHeight);
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return biomeMap.values().stream();
    }

    public int getBiome(int x, int z) {
        ChunkPos pos = new ChunkPos(x >> 4, z >> 4);
        return this.provider.getWorldArea(pos.x, pos.z).getBiome(x, z);
    }

    public int getHeight(int x, int z) {
        ChunkPos pos = new ChunkPos(x >> 4, z >> 4);
        return this.provider.getWorldArea(pos.x, pos.z).getHeight(x, z);
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        return biomeMap.get(BiomeDataRegistry.getBiomeData(getBiome(x * 4, z * 4)).biome());
    }
}
