package cn.leolezury.eternalstarlight.common.world.gen.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public record BiomeData(Holder<Biome> biome,
						Holder<Block> fluidBlock,
						int height, int variance,
						boolean hasRivers, boolean isOcean) {
	public static final Codec<BiomeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Biome.CODEC.fieldOf("biome").forGetter(BiomeData::biome),
		BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("fluid").forGetter(BiomeData::fluidBlock),
		Codec.INT.fieldOf("height").forGetter(BiomeData::height),
		Codec.INT.fieldOf("variance").forGetter(BiomeData::variance),
		Codec.BOOL.fieldOf("has_rivers").forGetter(BiomeData::hasRivers),
		Codec.BOOL.fieldOf("is_ocean").forGetter(BiomeData::isOcean)
	).apply(instance, BiomeData::new));

	public static class Builder {
		private final Holder<Biome> biome;
		private Holder<Block> fluidBlock = Blocks.WATER.builtInRegistryHolder();
		private final int height;
		private final int variance;
		private boolean hasRivers = true;
		private boolean isOcean = false;

		public Builder(Holder<Biome> biome, int height, int variance) {
			this.biome = biome;
			this.height = height;
			this.variance = variance;
		}

		public Builder withFluid(Holder<Block> fluid) {
			this.fluidBlock = fluid;
			return this;
		}

		public Builder hasRivers(boolean hasRivers) {
			this.hasRivers = hasRivers;
			return this;
		}

		public Builder isOcean(boolean isOcean) {
			this.isOcean = isOcean;
			return this;
		}

		public BiomeData build() {
			return new BiomeData(biome, fluidBlock, height, variance, hasRivers, isOcean);
		}
	}
}
