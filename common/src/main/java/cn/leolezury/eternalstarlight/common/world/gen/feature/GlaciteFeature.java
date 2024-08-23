package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;

public class GlaciteFeature extends ESFeature<NoneFeatureConfiguration> {
	public GlaciteFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		BlockPos toPos = pos.offset(random.nextInt(13, 16) * (random.nextBoolean() ? -1 : 1), random.nextInt(10, 16) * (random.nextBoolean() ? -1 : 1), random.nextInt(13, 16) * (random.nextBoolean() ? -1 : 1));
		BlockHitResult result = level.clip(new ClipContext(pos.getCenter(), toPos.getCenter(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));
		if (result.getType() != HitResult.Type.MISS) {
			toPos = result.getBlockPos();
		} else {
			return false;
		}
		if (pos.distSqr(toPos) < 12 * 12) {
			return false;
		}
		BlockPos.MutableBlockPos placePos = new BlockPos.MutableBlockPos();
		for (int x = Math.min(pos.getX(), toPos.getX()); x <= Math.max(pos.getX(), toPos.getX()); x++) {
			for (int y = Math.min(pos.getY(), toPos.getY()); y <= Math.max(pos.getY(), toPos.getY()); y++) {
				for (int z = Math.min(pos.getZ(), toPos.getZ()); z <= Math.max(pos.getZ(), toPos.getZ()); z++) {
					if (ESMathUtil.distSqrBetweenLineAndDot(pos.getX(), pos.getY(), pos.getZ(), toPos.getX(), toPos.getY(), toPos.getZ(), x, y, z) < 2) {
						placePos.set(x, y, z);
						setBlockIfEmpty(level, placePos, ESBlocks.GLACITE.get().defaultBlockState());
						for (Direction direction : Direction.values()) {
							if (random.nextInt(5) == 0) {
								setBlockIfEmpty(level, placePos.relative(direction), ESBlocks.GLACITE.get().defaultBlockState());
							}
						}
					}
				}
			}
		}
		return true;
	}
}
