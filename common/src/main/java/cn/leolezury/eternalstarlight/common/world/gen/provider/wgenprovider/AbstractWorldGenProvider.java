package cn.leolezury.eternalstarlight.common.world.gen.provider.wgenprovider;

import cn.leolezury.eternalstarlight.common.world.gen.provider.area.BiomesContainer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.area.HeightsContainer;
import cn.leolezury.eternalstarlight.common.world.gen.provider.area.WorldArea;
import cn.leolezury.eternalstarlight.common.world.gen.provider.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.provider.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.provider.transformer.BiomeTransformers;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public abstract class AbstractWorldGenProvider {
    public final int maxHeight;
    public final int minHeight;
    public long seed;
    public void setSeed(long seed) {
        this.seed = seed;
        this.noises[0] = new PerlinSimplexNoise(WorldgenRandom.Algorithm.LEGACY.newInstance(seed), List.of(0));
        this.noises[1] = new PerlinSimplexNoise(WorldgenRandom.Algorithm.LEGACY.newInstance(seed * 2 + 5), List.of(0));
        this.noises[2] = new PerlinSimplexNoise(WorldgenRandom.Algorithm.LEGACY.newInstance(seed * 3 + 10), List.of(0));
    }
    public PerlinSimplexNoise[] noises = new PerlinSimplexNoise[3];
    private final EnumMap<BiomeData.Temperature, ArrayList<Integer>> TEMPERATURE_TO_LAND_BIOME = new EnumMap<>(BiomeData.Temperature.class);
    private final EnumMap<BiomeData.Temperature, ArrayList<Integer>> TEMPERATURE_TO_OCEAN_BIOME = new EnumMap<>(BiomeData.Temperature.class);
    abstract int[] getLandBiomes();
    abstract int[] getOceanBiomes();
    private final Long2ObjectLinkedOpenHashMap<WorldArea> cache = new Long2ObjectLinkedOpenHashMap<>();

    public AbstractWorldGenProvider(int maxHeight, int minHeight) {
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        fillBiomeMapByTemperature(TEMPERATURE_TO_LAND_BIOME, getLandBiomes());
        fillBiomeMapByTemperature(TEMPERATURE_TO_OCEAN_BIOME, getOceanBiomes());
    }

    private void fillBiomeMapByTemperature(EnumMap<BiomeData.Temperature, ArrayList<Integer>> map, int[] biomes) {
        for (BiomeData.Temperature temperature : BiomeData.Temperature.values()) {
            map.put(temperature, new ArrayList<>());
        }
        for (int i : biomes) {
            for (BiomeData.Temperature temperature : BiomeDataRegistry.getBiomeData(i).temperatures()) {
                map.get(temperature).add(i);
            }
        }
    }

    public int getRandomBiome(BiomeData.Temperature temperature, boolean isOcean, Random random) {
        ArrayList<Integer> biomeList = (isOcean ? TEMPERATURE_TO_OCEAN_BIOME : TEMPERATURE_TO_LAND_BIOME).get(temperature);
        return biomeList.get(random.nextInt(biomeList.size()));
    }

    abstract void doBiomesTransformation(BiomesContainer container);
    abstract void doHeightsTransformation(HeightsContainer container);

    public void doubleAndProcessEdges(BiomesContainer container, long seedAddition) {
        container.doubleSize();
        container.processData(BiomeTransformers.RANDOMIZE, seedAddition);
        container.processData(BiomeTransformers.ASSIMILATE, seedAddition);
        container.processData(BiomeTransformers.ASSIMILATE_LONELY, seedAddition);
    }

    public WorldArea getWorldArea(int chunkX, int chunkZ) {
        int areaX = (int) Math.floor(chunkX / 64d);
        int areaZ = (int) Math.floor(chunkZ / 64d);
        long areaPos = posAsLong(areaX, areaZ);
        WorldArea area;
        if ((area = cache.get(areaPos)) != null) {
            return area;
        } else {
            BiomesContainer biomesContainer = new BiomesContainer(this, seed, areaX, areaZ, 4);
            doBiomesTransformation(biomesContainer);
            HeightsContainer heightsContainer = new HeightsContainer(biomesContainer);
            doHeightsTransformation(heightsContainer);
            area = new WorldArea(biomesContainer, heightsContainer);
            cache.put(areaPos, area);
            if (cache.size() > 32) {
                cache.removeFirst();
            }
        }
        return area;
    }

    public static long posAsLong(int i, int j) {
        return (long)i & 4294967295L | ((long)j & 4294967295L) << 32;
    }
}
