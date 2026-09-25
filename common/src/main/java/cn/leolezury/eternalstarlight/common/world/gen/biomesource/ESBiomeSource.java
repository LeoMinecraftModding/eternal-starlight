package cn.leolezury.eternalstarlight.common.world.gen.biomesource;

import cn.leolezury.eternalstarlight.common.data.ESSurfaceClimate;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.biome.RiverEntry;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.RandomState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ESBiomeSource extends BiomeSource {
	public static final MapCodec<ESBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		ESSurfaceClimate.HOLDER_CODEC.fieldOf("surface_climate").forGetter(o -> o.surfaceClimate),
		DensityFunction.CODEC.fieldOf("river_value").forGetter(o -> o.riverValue),
		RiverEntry.CODEC.listOf().fieldOf("rivers").forGetter(o -> o.rivers)
	).apply(instance, instance.stable(ESBiomeSource::new)));

	private final Holder<Climate.ParameterList<Holder<BiomeData>>> surfaceClimate;
	private final Holder<DensityFunction> riverValue;
	private final List<RiverEntry> rivers;
	private final ThreadLocal<RiverColumn> riverColumn = ThreadLocal.withInitial(RiverColumn::new);
	private volatile Map<ResourceKey<Biome>, Holder<Block>> fluids;
	private volatile NoiseBinding noiseBinding;

	public ESBiomeSource(Holder<Climate.ParameterList<Holder<BiomeData>>> surfaceClimate, Holder<DensityFunction> riverValue, List<RiverEntry> rivers) {
		this.surfaceClimate = surfaceClimate;
		this.riverValue = riverValue;
		this.rivers = rivers;
	}

	/**
	 * The biome source has to agree with the terrain on where the rivers run, so the field is rebuilt around the random
	 * state's noise cache. The registry copy still has unwired noise holders, which read as a constant zero.
	 */
	public void bindNoise(RandomState randomState) {
		NoiseBinding binding = this.noiseBinding;
		if (binding != null && binding.source() == randomState) {
			return;
		}
		DensityFunction riverValue = wireNoise(this.riverValue.value(), randomState);
		if (riverValue.minValue() == 0.0 && riverValue.maxValue() == 0.0) {
			// a zero field would turn every column into a river
			return;
		}
		this.noiseBinding = new NoiseBinding(randomState, riverValue);
	}

	/**
	 * Rewires the noise holders only: the graph holds no BlendedNoise, which would need re-seeding like RandomState does.
	 */
	private static DensityFunction wireNoise(DensityFunction function, RandomState randomState) {
		return function.mapAll(new DensityFunction.Visitor() {
			@Override
			public DensityFunction apply(DensityFunction visited) {
				return visited;
			}

			@Override
			public DensityFunction.NoiseHolder visitNoise(DensityFunction.NoiseHolder noiseHolder) {
				return noiseHolder.noiseData().unwrapKey()
					.map(key -> new DensityFunction.NoiseHolder(noiseHolder.noiseData(), randomState.getOrCreateNoise(key)))
					.orElse(noiseHolder);
			}
		});
	}

	private DensityFunction riverValue() {
		NoiseBinding binding = this.noiseBinding;
		return binding == null ? this.riverValue.value() : binding.riverValue();
	}

	private record NoiseBinding(RandomState source, DensityFunction riverValue) {
	}

	/**
	 * Fluid the biome wants above its floor, or null when it has none. Keyed by resource key because holders coming
	 * back out of a chunk are not guaranteed to be the instances the table handed out.
	 */
	public Holder<Block> fluidFor(Holder<Biome> biome) {
		if (this.fluids == null) {
			Map<ResourceKey<Biome>, Holder<Block>> built = new HashMap<>();
			for (Pair<Climate.ParameterPoint, Holder<BiomeData>> pair : this.surfaceClimate.value().values()) {
				putFluid(built, pair.getSecond());
			}
			for (RiverEntry river : this.rivers) {
				putFluid(built, river.river());
				if (river.transition().isPresent()) {
					putFluid(built, river.transition().get());
				}
			}
			this.fluids = built;
		}
		return biome.unwrapKey().map(key -> this.fluids.get(key)).orElse(null);
	}

	private static void putFluid(Map<ResourceKey<Biome>, Holder<Block>> map, Holder<BiomeData> data) {
		data.value().biome().unwrapKey().ifPresent(key -> map.put(key, data.value().fluidBlock()));
	}

	@Override
	protected MapCodec<? extends BiomeSource> codec() {
		return CODEC;
	}

	@Override
	protected Stream<Holder<Biome>> collectPossibleBiomes() {
		List<Holder<Biome>> transitions = this.rivers.stream()
			.map(river -> river.transition().map(transition -> transition.value().biome()))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.toList();
		return Stream.concat(
			this.surfaceClimate.value().values().stream().map(pair -> pair.getSecond().value().biome()),
			Stream.concat(this.rivers.stream().map(river -> river.river().value().biome()), transitions.stream())
		);
	}

	public Holder<BiomeData> getBiomeData(int blockX, int blockY, int blockZ, Climate.Sampler sampler) {
		Holder<BiomeData> base = this.surfaceClimate.value().findValue(sampler.sample(blockX >> 2, blockY >> 2, blockZ >> 2));
		if (this.rivers.isEmpty() || !base.value().hasRivers()) {
			return base;
		}
		return RiverEntry.resolve(base, this.rivers, this.riverColumn.get().values(this.rivers, this.riverValue(), blockX, blockY, blockZ));
	}

	@Override
	public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
		return getBiomeData(x << 2, y << 2, z << 2, sampler).value().biome();
	}

	/**
	 * River widths do not depend on y, so one column of values per thread is enough.
	 */
	private static final class RiverColumn {
		private int x = Integer.MIN_VALUE;
		private int z = Integer.MIN_VALUE;
		private float[] values = new float[0];

		private float[] values(List<RiverEntry> rivers, DensityFunction riverValue, int x, int y, int z) {
			if (this.x != x || this.z != z || this.values.length != rivers.size()) {
				this.x = x;
				this.z = z;
				this.values = RiverEntry.values(rivers, riverValue, x, y, z);
			}
			return this.values;
		}
	}
}
