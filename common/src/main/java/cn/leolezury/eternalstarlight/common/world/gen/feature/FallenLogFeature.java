package cn.leolezury.eternalstarlight.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
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
		Configuration config = context.config();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos originPos = context.origin();
		Direction direction = Direction.from2DDataValue(random.nextInt(4));
		Direction.Axis axis = direction.getAxis();

		int length = random.nextInt(6, 9);
		boolean canPlace = true;
		for (int i = 0; i < length; i++) {
			BlockPos relativePos = originPos.relative(direction, i);
			BlockState relativeState = level.getBlockState(relativePos);
			boolean canBeReplaced = relativeState.canBeReplaced() && !(relativeState.getBlock() instanceof LiquidBlock) && level.getBlockState(relativePos.above()).isAir();
			canPlace = canBeReplaced && level.getBlockState(relativePos.below()).is(BlockTags.DIRT);
		}

		if (canPlace) {
			for (int i = 0; i < length; ++i) {
				BlockPos relativePos = originPos.relative(direction, i);
				BlockState logState = config.log().getState(random, relativePos).setValue(RotatedPillarBlock.AXIS, axis);
				setBlock(level, relativePos, logState);
			}
			return true;
		}

		return false;
	}

	public record Configuration(BlockStateProvider log) implements FeatureConfiguration {
		public static final Codec<FallenLogFeature.Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockStateProvider.CODEC.fieldOf("log").forGetter(Configuration::log)).apply(instance, Configuration::new));
	}
}

