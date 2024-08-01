package cn.leolezury.eternalstarlight.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class FallenLogFeature extends Feature<FallenLogFeature.Configuration> {
	public FallenLogFeature(Codec<FallenLogFeature.Configuration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<FallenLogFeature.Configuration> context) {
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos pos = context.origin();
		BlockStateProvider provider = context.config().log();
		Direction direction = random.nextBoolean() ? Direction.WEST : Direction.SOUTH;
		int length = random.nextInt(5, 9);
		for (int i = 0; i < length; i++) {
			BlockPos logPos = pos.relative(direction, i);
			int height = level.getHeight(Heightmap.Types.OCEAN_FLOOR, logPos.getX(), logPos.getZ());
			logPos = new BlockPos(logPos.getX(), height, logPos.getZ());
			BlockState state = level.getBlockState(logPos);
			BlockState placeState = provider.getState(random, logPos);
			if (placeState.hasProperty(BlockStateProperties.AXIS)) {
				placeState = placeState.setValue(BlockStateProperties.AXIS, direction.getAxis());
			}
			if (!state.is(BlockTags.LOGS)) {
				setBlock(level, logPos, placeState);
			}
		}
		return true;
	}

	public record Configuration(BlockStateProvider log) implements FeatureConfiguration {
		public static final Codec<FallenLogFeature.Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockStateProvider.CODEC.fieldOf("log").forGetter(Configuration::log)).apply(instance, Configuration::new));
	}
}

