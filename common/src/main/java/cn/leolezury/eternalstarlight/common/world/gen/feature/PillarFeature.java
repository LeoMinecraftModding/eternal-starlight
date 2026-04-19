package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.world.gen.valuemap.CylinderProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.RotatedProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.ValueMapGenerator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PillarFeature extends ESFeature<PillarFeature.Configuration> {
	public PillarFeature(Codec<PillarFeature.Configuration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<PillarFeature.Configuration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		RandomSource random = context.random();
		Configuration config = context.config();
		ChunkGenerator chunkGenerator = context.chunkGenerator();
		List<BlockPos> positions = new ArrayList<>();
		ValueMapGenerator.place(new RotatedProvider(new CylinderProvider(config.radius().sample(random), config.height().sample(random)), config.rotation().sample(random), random.nextFloat() * 360), (pos, value) -> {
			setBlockIfEmpty(level, pos.offset(origin), config.pillar().getState(random, pos.offset(origin)));
			positions.add(pos.offset(origin));
			for (Direction direction : Direction.values()) {
				if (random.nextFloat() < 0.05) {
					setBlockIfEmpty(level, pos.offset(origin).relative(direction), config.pillar().getState(random, pos.offset(origin).relative(direction)));
					positions.add(pos.offset(origin).relative(direction));
				}
			}
		});
		positions.stream().filter(pos -> Arrays.stream(Direction.values()).anyMatch(dir -> !positions.contains(pos.relative(dir)))).forEach(pos -> {
			for (Direction direction : Direction.values()) {
				if (level.isEmptyBlock(pos.relative(direction))) {
					config.decorations().forEach(decoration -> {
						if (random.nextFloat() < config.decorationChance()) {
							decoration.value().place(level, chunkGenerator, random, pos.relative(direction));
						}
					});
				}
			}
		});
		return true;
	}

	public record Configuration(BlockStateProvider pillar, IntProvider height, IntProvider radius, IntProvider rotation, HolderSet<PlacedFeature> decorations, float decorationChance) implements FeatureConfiguration {
		public static final Codec<PillarFeature.Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BlockStateProvider.CODEC.fieldOf("pillar").forGetter(PillarFeature.Configuration::pillar),
			IntProvider.CODEC.fieldOf("height").forGetter(PillarFeature.Configuration::height),
			IntProvider.CODEC.fieldOf("radius").forGetter(PillarFeature.Configuration::radius),
			IntProvider.CODEC.fieldOf("rotation").forGetter(PillarFeature.Configuration::rotation),
			PlacedFeature.LIST_CODEC.fieldOf("decorations").forGetter(PillarFeature.Configuration::decorations),
			Codec.floatRange(0, 1).fieldOf("decoration_chance").forGetter(PillarFeature.Configuration::decorationChance)
		).apply(instance, PillarFeature.Configuration::new));

		@Override
		public Stream<ConfiguredFeature<?, ?>> getFeatures() {
			return decorations.stream().flatMap(placedFeature -> placedFeature.value().getFeatures());
		}
	}
}
