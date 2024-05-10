package cn.leolezury.eternalstarlight.common.world.gen.biomesource;

import cn.leolezury.eternalstarlight.common.world.gen.system.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.WorldGenProvider;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.stream.Stream;

public class ESBiomeSource extends BiomeSource {
    public static final MapCodec<ESBiomeSource> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            WorldGenProvider.CODEC.fieldOf("worldgen_provider").forGetter(o -> o.provider),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(o -> o.biomeHolderSet)
    ).apply(instance, instance.stable(ESBiomeSource::new)));

    private final WorldGenProvider provider;
    private final HolderSet<Biome> biomeHolderSet;

    public ESBiomeSource(WorldGenProvider provider, HolderSet<Biome> biomeHolderSet) {
        this.provider = provider;
        this.biomeHolderSet = biomeHolderSet;
    }

    public void setSeed(long seed) {
        this.provider.setSeed(seed);
    }

    public void setRegistryAccess(RegistryAccess access) {
        this.provider.setRegistryAccess(access);
    }

    public void setCacheSize(int size) {
        this.provider.setCacheSize(size);
    }

    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return biomeHolderSet.stream();
    }

    public BiomeData getBiomeData(int x, int z) {
        return this.provider.getWorldArea(x, z).getBiomeData(x, z);
    }

    public int getBiome(int x, int z) {
        return this.provider.getWorldArea(x, z).getBiome(x, z);
    }

    public int getHeight(int x, int z) {
        return this.provider.getWorldArea(x, z).getHeight(x, z);
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        return provider.getBiomeDataById(getBiome(x * 4, z * 4)).biome();
    }
}
