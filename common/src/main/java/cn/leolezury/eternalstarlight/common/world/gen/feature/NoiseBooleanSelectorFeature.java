package cn.leolezury.eternalstarlight.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.stream.Stream;

public class NoiseBooleanSelectorFeature extends Feature<NoiseBooleanSelectorFeature.Configuration> {
	public NoiseBooleanSelectorFeature(Codec<Configuration> codec) {
		super(codec);
	}

	@SuppressWarnings("removal")
	@Override
	public boolean place(FeaturePlaceContext<Configuration> context) {
		RandomSource random = context.random();
		Configuration config = context.config();
		WorldGenLevel level = context.level();
		ChunkGenerator chunkGenerator = context.chunkGenerator();
		BlockPos origin = context.origin();
		double noiseValue = Biome.BIOME_INFO_NOISE.getValue(origin.getX() * config.noiseScale(), origin.getZ() * config.noiseScale(), false);
		return (noiseValue > config.noiseLevel() ? config.featureTrue() : config.featureFalse()).value().place(level, chunkGenerator, random, origin);
	}

	public record Configuration(double noiseScale, double noiseLevel, Holder<PlacedFeature> featureTrue, Holder<PlacedFeature> featureFalse) implements FeatureConfiguration {
		public static final Codec<Configuration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.DOUBLE.fieldOf("noise_scale").forGetter(c -> c.noiseScale),
			Codec.DOUBLE.fieldOf("noise_level").forGetter(c -> c.noiseLevel),
			PlacedFeature.CODEC.fieldOf("feature_true").forGetter(c -> c.featureTrue),
			PlacedFeature.CODEC.fieldOf("feature_false").forGetter(c -> c.featureFalse)
		).apply(instance, Configuration::new));

		@Override
		public Stream<ConfiguredFeature<?, ?>> getFeatures() {
			return Stream.concat(this.featureTrue.value().getFeatures(), this.featureFalse.value().getFeatures());
		}
	}
}
