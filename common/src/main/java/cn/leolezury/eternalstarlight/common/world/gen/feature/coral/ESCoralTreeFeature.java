package cn.leolezury.eternalstarlight.common.world.gen.feature.coral;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

public class ESCoralTreeFeature extends ESCoralFeature {
	public ESCoralTreeFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	protected boolean placeFeature(LevelAccessor levelAccessor, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
		int i = randomSource.nextInt(3) + 1;

		for (int j = 0; j < i; ++j) {
			if (!this.placeCoralBlock(levelAccessor, randomSource, mutableBlockPos, blockState)) {
				return true;
			}

			mutableBlockPos.move(Direction.UP);
		}

		BlockPos blockPos2 = mutableBlockPos.immutable();
		int k = randomSource.nextInt(3) + 2;
		List<Direction> list = Direction.Plane.HORIZONTAL.shuffledCopy(randomSource);
		List<Direction> list2 = list.subList(0, k);

		for (Direction direction : list2) {
			mutableBlockPos.set(blockPos2);
			mutableBlockPos.move(direction);
			int l = randomSource.nextInt(5) + 2;
			int m = 0;

			for (int n = 0; n < l && this.placeCoralBlock(levelAccessor, randomSource, mutableBlockPos, blockState); ++n) {
				++m;
				mutableBlockPos.move(Direction.UP);
				if (n == 0 || m >= 2 && randomSource.nextFloat() < 0.25F) {
					mutableBlockPos.move(direction);
					m = 0;
				}
			}
		}

		return true;
	}
}
