package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.HashSet;
import java.util.Set;

public class AbyssalCaveFeature extends Feature<NoneFeatureConfiguration> {
	public AbyssalCaveFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		BlockPos.MutableBlockPos placePos = new BlockPos.MutableBlockPos();
		int xSize = 8 + random.nextInt(5) - 2;
		int ySize = 8 + random.nextInt(5) - 2;
		int zSize = 8 + random.nextInt(5) - 2;
		Set<BlockPos> hollowPositions = new HashSet<>();
		for (int x = -xSize; x <= xSize; x++) {
			for (int y = -ySize; y <= ySize; y++) {
				for (int z = -zSize; z <= zSize; z++) {
					if (ESMathUtil.isPointInEllipsoid(x, y, z, xSize, ySize, zSize)) {
						placePos.setWithOffset(pos, x, y, z);
						BlockState state = level.getBlockState(placePos);
						if (state.is(ESTags.Blocks.ABYSSAL_CAVE_REPLACEABLES) || state.is(ESTags.Blocks.STARLIGHT_CARVER_REPLACEABLES) || state.getBlock() == Blocks.WATER || (state.canBeReplaced() && !state.isAir())) {
							hollowPositions.add(placePos.immutable());
						} else {
							return false;
						}
					}
				}
			}
		}
		hollowPositions.forEach(blockPos -> setBlock(level, blockPos, Blocks.WATER.defaultBlockState()));
		return true;
	}
}
