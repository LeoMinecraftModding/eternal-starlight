package cn.leolezury.eternalstarlight.common.world.gen.system.provider;

import cn.leolezury.eternalstarlight.common.world.gen.system.area.WorldArea;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.BiomeTransformers;
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

    public void setSeed(long seed) {
        this.seed = seed;
        this.noises[0] = new PerlinSimplexNoise(WorldgenRandom.Algorithm.LEGACY.newInstance(seed), List.of(0));
        this.noises[1] = new PerlinSimplexNoise(WorldgenRandom.Algorithm.LEGACY.newInstance(seed * 2 + 5), List.of(0));
        this.noises[2] = new PerlinSimplexNoise(WorldgenRandom.Algorithm.LEGACY.newInstance(seed * 3 + 10), List.of(0));
    }

    private void fillBiomeMapByTemperature(EnumMap<BiomeData.Temperature, ArrayList<Integer>> map, int[] biomes) {
        for (BiomeData.Temperature temperature : BiomeData.Temperature.values()) {
            map.put(temperature, new ArrayList<>());
        }
        for (int biome : biomes) {
            for (BiomeData.Temperature temperature : BiomeDataRegistry.getBiomeData(biome).temperatures()) {
                map.get(temperature).add(biome);
            }
        }
    }

    public int getRandomBiome(BiomeData.Temperature temperature, boolean isOcean, Random random) {
        ArrayList<Integer> biomeList = (isOcean ? TEMPERATURE_TO_OCEAN_BIOME : TEMPERATURE_TO_LAND_BIOME).get(temperature);
        return biomeList.get(random.nextInt(biomeList.size()));
    }

    abstract void doBiomesTransformation(WorldArea area);
    abstract void doHeightsTransformation(WorldArea area);

    public void enlargeAndProcessBiomes(WorldArea area, long seedAddition) {
        area.transformBiomes(BiomeTransformers.DUPLICATE, seedAddition);
        area.transformBiomes(BiomeTransformers.RANDOMIZE, seedAddition);
        area.transformBiomes(BiomeTransformers.ASSIMILATE, seedAddition);
        area.transformBiomes(BiomeTransformers.ASSIMILATE_LONELY, seedAddition);
    }

    public WorldArea getWorldArea(int chunkX, int chunkZ) {
        int areaX = (int) Math.floor(chunkX / 64d);
        int areaZ = (int) Math.floor(chunkZ / 64d);
        long areaPos = posAsLong(areaX, areaZ);
        synchronized (cache) {
            WorldArea area = cache.get(areaPos);
            if (area != null) {
                return area;
            } else {
                area = new WorldArea(this, areaX, areaZ, 4, seed);
                area.initBiomes();
                doBiomesTransformation(area);
                area.initHeights();
                doHeightsTransformation(area);
                area.finalizeAll();
                cache.put(areaPos, area);
                while (cache.size() > 32) {
                    cache.removeFirst();
                }
            }
            return area;
        }
    }

    public static long posAsLong(int i, int j) {
        return (long)i & 4294967295L | ((long)j & 4294967295L) << 32;
    }
}
