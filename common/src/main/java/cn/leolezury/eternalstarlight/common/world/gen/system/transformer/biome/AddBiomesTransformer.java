package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.provider.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome.interfaces.NoiseDataTransformer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class AddBiomesTransformer extends NoiseDataTransformer {
    public static final MapCodec<AddBiomesTransformer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.DOUBLE.fieldOf("xz_scale").forGetter(o -> o.xzScale),
            RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).listOf().fieldOf("land_biomes").forGetter(o -> o.landBiomes),
            RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).listOf().fieldOf("ocean_biomes").forGetter(o -> o.oceanBiomes)
    ).apply(instance, AddBiomesTransformer::new));

    private boolean init = false;
    private final double xzScale;
    private final List<Holder<BiomeData>> landBiomes;
    private final List<Holder<BiomeData>> oceanBiomes;
    private final EnumMap<BiomeData.Temperature, ArrayList<Integer>> TEMPERATURE_TO_LAND_BIOME = new EnumMap<>(BiomeData.Temperature.class);
    private final EnumMap<BiomeData.Temperature, ArrayList<Integer>> TEMPERATURE_TO_OCEAN_BIOME = new EnumMap<>(BiomeData.Temperature.class);

    public AddBiomesTransformer(double xzScale, List<Holder<BiomeData>> landBiomes, List<Holder<BiomeData>> oceanBiomes) {
        this.xzScale = xzScale;
        this.landBiomes = landBiomes;
        this.oceanBiomes = oceanBiomes;
    }

    private void initBiomes(Registry<BiomeData> registry, EnumMap<BiomeData.Temperature, ArrayList<Integer>> map, List<Holder<BiomeData>> biomes) {
        for (BiomeData.Temperature temperature : BiomeData.Temperature.values()) {
            map.put(temperature, new ArrayList<>());
        }
        for (Holder<BiomeData> biome : biomes) {
            for (BiomeData.Temperature temperature : biome.value().temperatures()) {
                map.get(temperature).add(registry.getId(biome.value()));
            }
        }
    }

    private int getRandomBiome(Registry<BiomeData> registry, BiomeData.Temperature temperature, boolean isOcean, Random random) {
        ArrayList<Integer> biomeList = (isOcean ? TEMPERATURE_TO_OCEAN_BIOME : TEMPERATURE_TO_LAND_BIOME).get(temperature);
        return biomeList.get(random.nextInt(biomeList.size()));
    }

    @Override
    public int transform(WorldGenProvider provider, Random random, int original, int worldX, int worldZ, PerlinSimplexNoise noise) {
        if (!init) {
            initBiomes(provider.biomeDataRegistry, TEMPERATURE_TO_LAND_BIOME, landBiomes);
            initBiomes(provider.biomeDataRegistry, TEMPERATURE_TO_OCEAN_BIOME, oceanBiomes);
            init = true;
        }
        BiomeData.Temperature temperature;
        double noiseVal = noise.getValue(worldX * xzScale, worldZ * xzScale, false);
        if (noiseVal >= 0.6) {
            temperature = BiomeData.Temperature.HOT_EXTREME;
        } else if (noiseVal >= 0.2) {
            temperature = BiomeData.Temperature.HOT;
        } else if (noiseVal >= -0.2) {
            temperature = BiomeData.Temperature.NEUTRAL;
        } else if (noiseVal >= -0.6) {
            temperature = BiomeData.Temperature.COLD;
        } else {
            temperature = BiomeData.Temperature.COLD_EXTREME;
        }
        return getRandomBiome(provider.biomeDataRegistry, temperature, provider.biomeDataRegistry.byId(original).isOcean(), random);
    }

    @Override
    public DataTransformerType<?> type() {
        return ESDataTransformerTypes.ADD_BIOMES.get();
    }
}
