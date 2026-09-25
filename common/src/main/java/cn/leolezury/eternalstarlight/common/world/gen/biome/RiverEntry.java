package cn.leolezury.eternalstarlight.common.world.gen.biome;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.List;
import java.util.Optional;

/**
 * A river carved out of the climate table by a distance field, so {@code size} is the half width of the river in blocks
 * and rivers keep the same width everywhere instead of widening wherever the noise happens to change slowly.
 */
public record RiverEntry(Holder<BiomeData> river, float size, Optional<Holder<BiomeData>> transition, float transitionSize, int offset, boolean oceanOnly) {
	public static final Codec<RiverEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC)
			.fieldOf("river").forGetter(RiverEntry::river),
		Codec.FLOAT.fieldOf("size").forGetter(RiverEntry::size),
		RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC)
			.optionalFieldOf("transition").forGetter(RiverEntry::transition),
		Codec.FLOAT.optionalFieldOf("transition_size", 0f).forGetter(RiverEntry::transitionSize),
		Codec.INT.fieldOf("offset").forGetter(RiverEntry::offset),
		Codec.BOOL.optionalFieldOf("ocean_only", false).forGetter(RiverEntry::oceanOnly)
	).apply(instance, RiverEntry::new));

	/**
	 * Distance to every centreline at a column, independent of y so callers can cache one column of values.
	 */
	public static float[] values(List<RiverEntry> rivers, DensityFunction riverValue, int x, int y, int z) {
		float[] values = new float[rivers.size()];
		for (int i = 0; i < rivers.size(); i++) {
			int offset = rivers.get(i).offset();
			values[i] = (float) riverValue.compute(new DensityFunction.SinglePointContext(x + offset, y, z + offset));
		}
		return values;
	}

	public static Holder<BiomeData> resolve(Holder<BiomeData> base, List<RiverEntry> rivers, float[] values) {
		if (rivers.isEmpty() || !base.value().hasRivers()) {
			return base;
		}
		boolean ocean = base.value().isOcean();
		for (int i = 0; i < rivers.size(); i++) {
			RiverEntry entry = rivers.get(i);
			if (ocean != entry.oceanOnly()) {
				continue;
			}
			if (values[i] < entry.size()) {
				return entry.river();
			}
			if (entry.transition().isPresent() && values[i] < entry.transitionSize()) {
				return entry.transition().get();
			}
		}
		return base;
	}

	/**
	 * One shot variant: entries on the other side of the ocean split are skipped before their noise is evaluated.
	 */
	public static Holder<BiomeData> resolve(Holder<BiomeData> base, List<RiverEntry> rivers, DensityFunction riverValue, int x, int y, int z) {
		if (rivers.isEmpty() || !base.value().hasRivers()) {
			return base;
		}
		boolean ocean = base.value().isOcean();
		for (RiverEntry entry : rivers) {
			if (ocean != entry.oceanOnly()) {
				continue;
			}
			float value = (float) riverValue.compute(new DensityFunction.SinglePointContext(x + entry.offset(), y, z + entry.offset()));
			if (value < entry.size()) {
				return entry.river();
			}
			if (entry.transition().isPresent() && value < entry.transitionSize()) {
				return entry.transition().get();
			}
		}
		return base;
	}
}
