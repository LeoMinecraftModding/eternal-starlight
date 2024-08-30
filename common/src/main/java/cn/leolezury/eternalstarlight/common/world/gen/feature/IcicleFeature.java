package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.block.IcicleBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class IcicleFeature extends Feature<NoneFeatureConfiguration> {
	public IcicleFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		boolean aboveSturdy = level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN);
		boolean belowSturdy = level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
		if (!aboveSturdy && !belowSturdy) {
			return false;
		}
		Direction tipDirection = aboveSturdy && belowSturdy ? (random.nextBoolean() ? Direction.UP : Direction.DOWN) : (aboveSturdy ? Direction.DOWN : Direction.UP);
		int maxLength = 0;
		for (int i = 0; i < 4; i++) {
			if (level.getBlockState(pos.relative(tipDirection, i)).isAir()) {
				maxLength = i + 1;
			} else {
				break;
			}
		}
		if (maxLength == 0) {
			return false;
		}
		switch (random.nextInt(maxLength)) {
			case 0 -> setBlock(level, pos, ESBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.TIP_DIRECTION, tipDirection));
			case 1 -> {
				setBlock(level, pos, ESBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.THICKNESS, IcicleBlock.IcicleThickness.TOP).setValue(IcicleBlock.TIP_DIRECTION, tipDirection));
				setBlock(level, pos.relative(tipDirection), ESBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.TIP_DIRECTION, tipDirection));
			}
			case 2 -> {
				setBlock(level, pos, ESBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.THICKNESS, IcicleBlock.IcicleThickness.BASE).setValue(IcicleBlock.TIP_DIRECTION, tipDirection));
				setBlock(level, pos.relative(tipDirection), ESBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.THICKNESS, IcicleBlock.IcicleThickness.TOP).setValue(IcicleBlock.TIP_DIRECTION, tipDirection));
				setBlock(level, pos.relative(tipDirection, 2), ESBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.TIP_DIRECTION, tipDirection));
			}
			case 3 -> {
				setBlock(level, pos, ESBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.THICKNESS, IcicleBlock.IcicleThickness.BASE).setValue(IcicleBlock.TIP_DIRECTION, tipDirection));
				setBlock(level, pos.relative(tipDirection), ESBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.THICKNESS, IcicleBlock.IcicleThickness.MIDDLE).setValue(IcicleBlock.TIP_DIRECTION, tipDirection));
				setBlock(level, pos.relative(tipDirection, 2), ESBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.THICKNESS, IcicleBlock.IcicleThickness.TOP).setValue(IcicleBlock.TIP_DIRECTION, tipDirection));
				setBlock(level, pos.relative(tipDirection, 3), ESBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.TIP_DIRECTION, tipDirection));
			}
			default -> {
				return false;
			}
		}
		return true;
	}
}
