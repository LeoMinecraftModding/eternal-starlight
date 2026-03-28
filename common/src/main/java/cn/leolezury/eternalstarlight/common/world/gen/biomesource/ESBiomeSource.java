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

	private static final float NOISE_FREQUENCY = 1.5f;
	private static final int HEIGHT_GRID_COARSE = 64;
	private static final int HEIGHT_GRID_FINE = 8;
	private static final int HEIGHT_GRID_LOCAL = 6;
	private static final int CACHE_SIZE = 8192;

	private final Climate.ParameterList<Holder<BiomeData>> climateList;
	private final List<RiverEntry> rivers;

	private long seed = 0;
	private final FastNoise[] noises = new FastNoise[]{
		makeNoise(seed), makeNoise(seed * 2L + 5L), makeNoise(seed * 3L + 10L)
	};

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
			this.biomeCache.clear();
			this.rawHeightCache.clear();
			this.heightCache.clear();
		}
	}

	private FastNoise makeNoise(long seed) {
		FastNoise noise = new FastNoise((int) seed);
		noise.setFrequency(NOISE_FREQUENCY);
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
					double rv = noises[2].getNoise((bx + off) * 0.001f, (bz + off) * 0.001f) * 0.5
						+ noises[2].getNoise((bx + off) * 0.002f, (bz + off) * 0.002f) * 0.3
						+ noises[2].getNoise((bx + off) * 0.0035f, (bz + off) * 0.0035f) * 0.2;

					float core = river.size();
					float shore = river.transitionSize();

					if (rv > -core && rv < core) {
						return river.riverData();
					} else if (shore > core && rv > -shore && rv < shore && river.transitionData().isPresent()) {
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

		int result = computeTwoLevelHeight(blockX, blockZ, sampler);
		synchronized (heightCache) {
			heightCache.putAndMoveToLast(key, result);
			if (heightCache.size() > CACHE_SIZE) heightCache.removeFirst();
		}
		return result;
	}

	private int computeTwoLevelHeight(int blockX, int blockZ, Climate.Sampler sampler) {
		// coarse level
		int cx0 = Math.floorDiv(blockX, HEIGHT_GRID_COARSE) * HEIGHT_GRID_COARSE;
		int cz0 = Math.floorDiv(blockZ, HEIGHT_GRID_COARSE) * HEIGHT_GRID_COARSE;
		int cx1 = cx0 + HEIGHT_GRID_COARSE;
		int cz1 = cz0 + HEIGHT_GRID_COARSE;
		double cfx = smoothstep((blockX - cx0) / (double) HEIGHT_GRID_COARSE);
		double cfz = smoothstep((blockZ - cz0) / (double) HEIGHT_GRID_COARSE);
		double ch00 = getRawHeight(cx0, cz0, sampler);
		double ch10 = getRawHeight(cx1, cz0, sampler);
		double ch01 = getRawHeight(cx0, cz1, sampler);
		double ch11 = getRawHeight(cx1, cz1, sampler);
		double coarseH = ch00 * (1 - cfx) * (1 - cfz)
			+ ch10 * cfx * (1 - cfz)
			+ ch01 * (1 - cfx) * cfz
			+ ch11 * cfx * cfz;

		// fine level
		int fx0 = Math.floorDiv(blockX, HEIGHT_GRID_FINE) * HEIGHT_GRID_FINE;
		int fz0 = Math.floorDiv(blockZ, HEIGHT_GRID_FINE) * HEIGHT_GRID_FINE;
		int fx1 = fx0 + HEIGHT_GRID_FINE;
		int fz1 = fz0 + HEIGHT_GRID_FINE;
		double ffx = smoothstep((blockX - fx0) / (double) HEIGHT_GRID_FINE);
		double ffz = smoothstep((blockZ - fz0) / (double) HEIGHT_GRID_FINE);
		double fh00 = getRawHeight(fx0, fz0, sampler);
		double fh10 = getRawHeight(fx1, fz0, sampler);
		double fh01 = getRawHeight(fx0, fz1, sampler);
		double fh11 = getRawHeight(fx1, fz1, sampler);
		double fineH = fh00 * (1 - ffx) * (1 - ffz)
			+ fh10 * ffx * (1 - ffz)
			+ fh01 * (1 - ffx) * ffz
			+ fh11 * ffx * ffz;

		// local
		int totalCount = 0;
		int total = 0;
		for (int i = -HEIGHT_GRID_LOCAL; i <= HEIGHT_GRID_LOCAL; i++) {
			for (int j = -HEIGHT_GRID_LOCAL; j <= HEIGHT_GRID_LOCAL; j++) {
				if (i * i + j * j <= HEIGHT_GRID_LOCAL * HEIGHT_GRID_LOCAL) {
					total += getRawHeight(blockX + i, blockZ + j, sampler);
					totalCount++;
				}
			}
		}

		double h = (coarseH * 0.7 + fineH * 0.3) * 0.8 + ((double) total / totalCount) * 0.2;
		return (int) Math.round(h);
	}

	private static double smoothstep(double t) {
		return t * t * (3.0 - 2.0 * t);
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
			double n = noises[0].getNoise(bx * 0.004f, bz * 0.004f) * 0.7
				+ noises[1].getNoise(bx * 0.0016f, bz * 0.0016f) * 0.2
				+ noises[0].getNoise(bx * 0.0006f, bz * 0.0006f) * 0.1;
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
