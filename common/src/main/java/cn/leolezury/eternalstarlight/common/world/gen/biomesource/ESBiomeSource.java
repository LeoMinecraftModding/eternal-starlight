package cn.leolezury.eternalstarlight.common.world.gen.biomesource;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.util.FastNoise;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ESBiomeSource extends BiomeSource {
	public static final MapCodec<ESBiomeSource> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		Climate.ParameterList.codec(RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("biome_data")).fieldOf("biome_data_parameters").forGetter(o -> o.climateList),
		RiverEntry.CODEC.listOf().fieldOf("rivers").forGetter(o -> o.rivers)
	).apply(instance, instance.stable(ESBiomeSource::new)));

	private static final float NOISE_FREQUENCY = 0.005f;
	private static final float RIVER_NOISE_FREQUENCY = 0.001f;
	private static final float RIVER_WARP_NOISE_FREQUENCY = 0.002f;
	private static final int HEIGHT_GRID_SIZE = 10;
	private static final int CACHE_SIZE = 8192;

	private final Climate.ParameterList<Holder<BiomeData>> climateList;
	private final List<RiverEntry> rivers;

	private long seed = 0;
	private final FastNoise[] noises = new FastNoise[]{
		makeNoise(seed), makeNoise(seed * 2L + 5L), makeNoise(seed * 3L + 10L)
	};
	private final FastNoise riverNoise = makeRiverNoise(seed);
	private final FastNoise riverWarpNoise = makeRiverWarpNoise(seed * 2L + 5L);

	private final Long2ObjectLinkedOpenHashMap<Holder<BiomeData>> biomeCache =
		new Long2ObjectLinkedOpenHashMap<>(CACHE_SIZE + 1, 0.75f);

	private final Long2ObjectLinkedOpenHashMap<Integer> rawHeightCache =
		new Long2ObjectLinkedOpenHashMap<>(CACHE_SIZE + 1, 0.75f);

	private final Long2ObjectLinkedOpenHashMap<Integer> heightCache =
		new Long2ObjectLinkedOpenHashMap<>(CACHE_SIZE + 1, 0.75f);

	public ESBiomeSource(Climate.ParameterList<Holder<BiomeData>> climateList, List<RiverEntry> rivers) {
		this.climateList = climateList;
		this.rivers = rivers;
	}

	public void setSeed(long seed) {
		if (this.seed != seed) {
			this.seed = seed;
			this.noises[0].setSeed((int) seed);
			this.noises[1].setSeed((int) (seed * 2L + 5L));
			this.noises[2].setSeed((int) (seed * 3L + 10L));
			this.riverNoise.setSeed((int) seed);
			this.riverWarpNoise.setSeed((int) (seed * 2L + 5L));
			this.biomeCache.clear();
			this.rawHeightCache.clear();
			this.heightCache.clear();
		}
	}

	private FastNoise makeNoise(long seed) {
		FastNoise noise = new FastNoise((int) seed);
		noise.setFrequency(NOISE_FREQUENCY);
		noise.setFractalType(FastNoise.FractalType.FBM);
		return noise;
	}

	private FastNoise makeRiverNoise(long seed) {
		FastNoise noise = new FastNoise((int) seed);
		noise.setFrequency(RIVER_NOISE_FREQUENCY);
		noise.setFractalType(FastNoise.FractalType.FBM);
		noise.setDomainWarpAmp(80.0f);
		return noise;
	}

	private FastNoise makeRiverWarpNoise(long seed) {
		FastNoise noise = new FastNoise((int) seed);
		noise.setFrequency(RIVER_WARP_NOISE_FREQUENCY);
		return noise;
	}

	@Override
	protected MapCodec<? extends BiomeSource> codec() {
		return CODEC;
	}

	@Override
	protected Stream<Holder<Biome>> collectPossibleBiomes() {
		List<Holder<Biome>> transitions = this.rivers.stream().map(r -> r.transitionData().map(t -> t.value().biome())).filter(Optional::isPresent).map(Optional::get).toList();
		return Stream.concat(
			this.climateList.values().stream().map(p -> p.getSecond().value().biome()),
			Stream.concat(
				this.rivers.stream().map(r -> r.riverData().value().biome()),
				transitions.stream()
			)
		);
	}

	public Holder<BiomeData> getBiomeData(int blockX, int blockY, int blockZ, Climate.Sampler sampler) {
		int gx = blockX >> 2;
		int gy = blockY >> 2;
		int gz = blockZ >> 2;
		long key = posAsLong(gx, gz);

		if (blockY == ESDimensions.SEA_LEVEL) {
			synchronized (biomeCache) {
				Holder<BiomeData> cached = biomeCache.get(key);
				if (cached != null) return cached;
			}
		}

		Holder<BiomeData> result = computeBiomeData(gx << 2, gy << 2, gz << 2, sampler);

		if (blockY == ESDimensions.SEA_LEVEL) {
			synchronized (biomeCache) {
				biomeCache.putAndMoveToLast(key, result);
				if (biomeCache.size() > CACHE_SIZE) biomeCache.removeFirst();
			}
		}

		return result;
	}

	private Holder<BiomeData> computeBiomeData(int bx, int by, int bz, Climate.Sampler sampler) {
		Climate.TargetPoint target = sampler.sample(bx >> 2, by >> 2, bz >> 2);

		Holder<BiomeData> base = climateList.findValue(target);
		BiomeData value = base.value();

		if (value.hasRivers() && !rivers.isEmpty()) {
			for (RiverEntry river : rivers) {
				boolean isOcean = value.isOcean();
				if (!(isOcean && !river.canGenerateInOcean()) && !(!isOcean && river.canGenerateInOceanOnly())) {
					int off = river.offset();
					int riverX = bx + off;
					int riverZ = bz + off;

					riverNoise.domainWarp(new FastNoise.Vector2(riverX, riverZ));

					float noiseValue = riverNoise.getNoise(riverX, riverZ);

					float delta = 1.0f;
					float nx1 = riverNoise.getNoise(riverX + delta, riverZ);
					float nx2 = riverNoise.getNoise(riverX - delta, riverZ);
					float nz1 = riverNoise.getNoise(riverX, riverZ + delta);
					float nz2 = riverNoise.getNoise(riverX, riverZ - delta);
					float gradX = (nx1 - nx2) / (2 * delta);
					float gradZ = (nz1 - nz2) / (2 * delta);

					float gradLength = (float) Math.sqrt(gradX * gradX + gradZ * gradZ);
					if (gradLength < 0.0001f) gradLength = 0.0001f;

					double riverValue = Math.abs(noiseValue) / gradLength;

					if (riverValue < river.size()) {
						return river.riverData();
					} else if (riverValue < river.transitionSize() && river.transitionData().isPresent()) {
						return river.transitionData().get();
					}
				}
			}
		}

		return base;
	}

	public int getHeight(int blockX, int blockZ, Climate.Sampler sampler) {
		long key = posAsLong(blockX, blockZ);
		synchronized (heightCache) {
			Integer cached = heightCache.get(key);
			if (cached != null) return cached;
		}

		int result = computeHeight(blockX, blockZ, sampler);
		synchronized (heightCache) {
			heightCache.putAndMoveToLast(key, result);
			if (heightCache.size() > CACHE_SIZE) heightCache.removeFirst();
		}
		return result;
	}

	private int computeHeight(int blockX, int blockZ, Climate.Sampler sampler) {
		float totalWeight = 0;
		float totalHeight = 0;
		for (int i = -HEIGHT_GRID_SIZE; i <= HEIGHT_GRID_SIZE; i++) {
			for (int j = -HEIGHT_GRID_SIZE; j <= HEIGHT_GRID_SIZE; j++) {
				if (i * i + j * j < HEIGHT_GRID_SIZE * HEIGHT_GRID_SIZE) {
					totalHeight += getRawHeight(blockX + i, blockZ + j, sampler) * (HEIGHT_GRID_SIZE - Mth.sqrt((float) (i * i + j * j)));
					totalWeight += (HEIGHT_GRID_SIZE - Mth.sqrt((float) (i * i + j * j)));
				}
			}
		}
		return Math.round(totalHeight / totalWeight);
	}

	private int getRawHeight(int bx, int bz, Climate.Sampler sampler) {
		long key = posAsLong(bx, bz);
		synchronized (rawHeightCache) {
			Integer cached = rawHeightCache.get(key);
			if (cached != null) return cached;
		}

		int h = computeRawHeight(bx, bz, sampler);
		synchronized (rawHeightCache) {
			rawHeightCache.putAndMoveToLast(key, h);
			if (rawHeightCache.size() > CACHE_SIZE) rawHeightCache.removeFirst();
		}
		return h;
	}

	private int computeRawHeight(int bx, int bz, Climate.Sampler sampler) {
		BiomeData data = getBiomeData(bx, ESDimensions.SEA_LEVEL, bz, sampler).value();
		int base = data.height();
		int variance = data.variance();
		if (variance > 0) {
			float n = noises[0].getNoise(bx, bz) * 0.7f
				+ noises[1].getNoise(bx * 0.4f, bz * 0.4f) * 0.2f
				+ noises[0].getNoise(bx * 0.15f, bz * 0.15f) * 0.1f;
			base += (int) (n * variance);
		}
		return base;
	}

	public static long posAsLong(int x, int z) {
		return ((long) x << 32) | (z & 0xffffffffL);
	}

	@Override
	public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
		return getBiomeData(x << 2, y << 2, z << 2, sampler).value().biome();
	}

	public record RiverEntry(
		Holder<BiomeData> riverData,
		float size,
		Optional<Holder<BiomeData>> transitionData,
		float transitionSize,
		int offset,
		boolean canGenerateInOcean,
		boolean canGenerateInOceanOnly
	) {
		public static final Codec<RiverEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC)
				.fieldOf("river").forGetter(RiverEntry::riverData),
			Codec.FLOAT.fieldOf("size").forGetter(RiverEntry::size),
			RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC)
				.optionalFieldOf("transition").forGetter(RiverEntry::transitionData),
			Codec.FLOAT.optionalFieldOf("transition_size", 0f).forGetter(RiverEntry::transitionSize),
			Codec.INT.fieldOf("offset").forGetter(RiverEntry::offset),
			Codec.BOOL.optionalFieldOf("can_generate_in_ocean", false).forGetter(RiverEntry::canGenerateInOcean),
			Codec.BOOL.optionalFieldOf("can_generate_in_ocean_only", false).forGetter(RiverEntry::canGenerateInOceanOnly)
		).apply(instance, RiverEntry::new));
	}
}
