package cn.leolezury.eternalstarlight.common.world.gen.biomesource;

import cn.leolezury.eternalstarlight.common.data.ESBiomeClimate;
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
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.RandomState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ESBiomeSource extends BiomeSource {
	public static final MapCodec<ESBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		ESBiomeClimate.HOLDER_CODEC.fieldOf("surface_climate").forGetter(o -> o.surfaceClimate),
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

	public void bindNoise(RandomState randomState) {
		NoiseBinding binding = this.noiseBinding;
		if (binding != null && binding.source() == randomState) {
			return;
		}
		AtomicInteger wired = new AtomicInteger();
		DensityFunction riverValue = wireNoise(this.riverValue.value(), randomState, wired);
		if (wired.get() == 0) {
			// nothing was rewired, so every distance would read zero and turn every column into a river: rail them off
			this.noiseBinding = new NoiseBinding(randomState, DensityFunctions.constant(64.0));
			return;
		}
		this.noiseBinding = new NoiseBinding(randomState, riverValue);
	}

	private static DensityFunction wireNoise(DensityFunction function, RandomState randomState, AtomicInteger wired) {
		return function.mapAll(new DensityFunction.Visitor() {
			@Override
			public DensityFunction apply(DensityFunction visited) {
				return visited;
			}

			@Override
			public DensityFunction.NoiseHolder visitNoise(DensityFunction.NoiseHolder noiseHolder) {
				return noiseHolder.noiseData().unwrapKey()
					.map(key -> {
						wired.incrementAndGet();
						return new DensityFunction.NoiseHolder(noiseHolder.noiseData(), randomState.getOrCreateNoise(key));
					})
					.orElse(noiseHolder);
			}
		});
	}

	private record NoiseBinding(RandomState source, DensityFunction riverValue) {
	}

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
		NoiseBinding binding = this.noiseBinding;
		DensityFunction riverValue = binding == null ? this.riverValue.value() : binding.riverValue();
		return RiverEntry.resolve(base, this.rivers, this.riverColumn.get().values(this.rivers, binding, riverValue, blockX, blockY, blockZ));
	}

	@Override
	public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
		return getBiomeData(x << 2, y << 2, z << 2, sampler).value().biome();
	}

	private static final class RiverColumn {
		private static final int COLUMNS = 16;
		private final NoiseBinding[] bindings = new NoiseBinding[COLUMNS];
		private final int[] xs = new int[COLUMNS];
		private final int[] zs = new int[COLUMNS];
		private final float[][] values = new float[COLUMNS][];

		private float[] values(List<RiverEntry> rivers, NoiseBinding binding, DensityFunction riverValue, int x, int y, int z) {
			int index = ((x >> 2) & 3) | (((z >> 2) & 3) << 2);
			if (this.bindings[index] != binding || this.xs[index] != x || this.zs[index] != z) {
				this.bindings[index] = binding;
				this.xs[index] = x;
				this.zs[index] = z;
				this.values[index] = RiverEntry.values(rivers, riverValue, x, y, z);
			}
			return this.values[index];
		}
	}
}
