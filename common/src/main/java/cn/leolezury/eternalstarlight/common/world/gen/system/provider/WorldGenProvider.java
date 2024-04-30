package cn.leolezury.eternalstarlight.common.world.gen.system.provider;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.world.gen.system.area.WorldArea;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.List;

public class WorldGenProvider {
    public static final Codec<WorldGenProvider> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TransformerWithSeed.CODEC.listOf().fieldOf("biome_transformers").forGetter(o -> o.biomeTransformers),
            TransformerWithSeed.CODEC.listOf().fieldOf("height_transformers").forGetter(o -> o.heightTransformers),
            Codec.INT.fieldOf("max_height").forGetter(o -> o.maxHeight),
            Codec.INT.fieldOf("min_height").forGetter(o -> o.minHeight)
    ).apply(instance, WorldGenProvider::new));

    private final List<TransformerWithSeed> biomeTransformers;
    private final List<TransformerWithSeed> heightTransformers;
    public final int maxHeight;
    public final int minHeight;

    private long seed;
    private int cacheSize = 32;
    public PerlinSimplexNoise[] noises = new PerlinSimplexNoise[3];
    public RegistryAccess registryAccess;
    public Registry<BiomeData> biomeDataRegistry;
    public Registry<DataTransformer> dataTransformerRegistry;
    private final Object2IntLinkedOpenHashMap<ResourceLocation> biomeDataIds = new Object2IntLinkedOpenHashMap<>();
    private final Int2ObjectLinkedOpenHashMap<ResourceLocation> biomeDataLocations = new Int2ObjectLinkedOpenHashMap<>();
    private final Long2ObjectLinkedOpenHashMap<WorldArea> generatedAreas = new Long2ObjectLinkedOpenHashMap<>();

    public WorldGenProvider(List<TransformerWithSeed> biomeTransformers, List<TransformerWithSeed> heightTransformers, int maxHeight, int minHeight) {
        this.biomeTransformers = biomeTransformers;
        this.heightTransformers = heightTransformers;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
    }

    public void setSeed(long seed) {
        this.seed = seed;
        this.noises[0] = new PerlinSimplexNoise(WorldgenRandom.Algorithm.LEGACY.newInstance(seed), List.of(0));
        this.noises[1] = new PerlinSimplexNoise(WorldgenRandom.Algorithm.LEGACY.newInstance(seed * 2 + 5), List.of(0));
        this.noises[2] = new PerlinSimplexNoise(WorldgenRandom.Algorithm.LEGACY.newInstance(seed * 3 + 10), List.of(0));
    }

    public void setRegistryAccess(RegistryAccess access) {
        if (!(access == this.registryAccess)) {
            this.registryAccess = access;
            this.biomeDataRegistry = registryAccess.registryOrThrow(ESRegistries.BIOME_DATA);
            this.dataTransformerRegistry = registryAccess.registryOrThrow(ESRegistries.DATA_TRANSFORMER);
        }
    }

    // used to prevent id changing caused by datapack reloading
    public int getBiomeDataId(BiomeData data) {
        ResourceLocation location = biomeDataRegistry.getKey(data);
        if (biomeDataIds.containsKey(location)) {
            return biomeDataIds.getInt(location);
        } else {
            int id = biomeDataIds.size();
            biomeDataIds.put(location, id);
            biomeDataLocations.put(id, location);
            return id;
        }
    }

    // used to prevent id changing caused by datapack reloading
    public BiomeData getBiomeDataById(int id) {
        return biomeDataRegistry.get(biomeDataLocations.get(id));
    }

    public WorldArea getWorldArea(int x, int z) {
        int areaX = x >> 10;
        int areaZ = z >> 10;
        long areaPos = posAsLong(areaX, areaZ);
        synchronized (generatedAreas) {
            WorldArea area = generatedAreas.get(areaPos);
            if (area != null) {
                return area;
            } else {
                area = new WorldArea(this, areaX, areaZ, 4, seed);
                area.initBiomes();
                for (TransformerWithSeed transformer : biomeTransformers) {
                    area.transformBiomes(transformer.transformer().value(), transformer.seedAddition);
                }
                area.initHeights();
                for (TransformerWithSeed transformer : heightTransformers) {
                    area.transformHeights(transformer.transformer().value(), transformer.seedAddition);
                }
                area.finalizeAll(dataTransformerRegistry);
                generatedAreas.put(areaPos, area);
                while (generatedAreas.size() > cacheSize) {
                    generatedAreas.removeFirst();
                }
            }
            return area;
        }
    }

    public void setCacheSize(int size) {
        this.cacheSize = Math.max(size, 32);
    }

    public static long posAsLong(int i, int j) {
        return (long)i & 4294967295L | ((long)j & 4294967295L) << 32;
    }

    public record TransformerWithSeed(Holder<DataTransformer> transformer, int seedAddition) {
        public static final Codec<TransformerWithSeed> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RegistryFileCodec.create(ESRegistries.DATA_TRANSFORMER, DataTransformer.CODEC).fieldOf("transformer").forGetter(TransformerWithSeed::transformer),
                Codec.INT.fieldOf("seed_addition").forGetter(TransformerWithSeed::seedAddition)
        ).apply(instance, TransformerWithSeed::new));
    }
}
