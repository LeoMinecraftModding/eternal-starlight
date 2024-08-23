package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.block.DirectionalBudBlock;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.Arrays;
import java.util.Optional;

public class VelvetumossFeature extends Feature<VelvetumossFeature.Configuration> {
	public VelvetumossFeature(Codec<Configuration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Configuration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		Configuration config = context.config();
		BlockPos.MutableBlockPos stonePos = new BlockPos.MutableBlockPos();
		for (int x = -3; x <= 3; x++) {
			for (int y = -2; y <= 2; y++) {
				for (int z = -3; z <= 3; z++) {
					if (ESMathUtil.isPointInEllipsoid(x, y, z, 3 + random.nextInt(3) - 1, 3 + random.nextInt(3) - 1, 3 + random.nextInt(3) - 1)) {
						stonePos.setWithOffset(pos, x, y, z);
						if (level.getBlockState(stonePos).is(ESTags.Blocks.ABYSSLATES)) {
							if (stonePos.distToCenterSqr(stonePos.getCenter()) < 4 && Arrays.stream(Direction.values()).anyMatch(direction -> level.getBlockState(stonePos.relative(direction)).is(Blocks.WATER))) {
								setBlock(level, stonePos, config.moss().defaultBlockState());
							}
							for (Direction direction : Direction.values()) {
								BlockPos growPos = stonePos.relative(direction);
								if (level.getBlockState(growPos).is(Blocks.WATER) && random.nextInt(3) == 0) {
									setBlock(level, growPos, config.villi().defaultBlockState().setValue(DirectionalBudBlock.WATERLOGGED, true).setValue(DirectionalBudBlock.FACING, direction));
								}
							}
							if (config.flower().isPresent() && random.nextInt(10) == 0) {
								BlockPos growPos = stonePos.relative(Direction.UP);
								if (level.getBlockState(growPos).is(Blocks.WATER)) {
									setBlock(level, growPos, config.flower().get().defaultBlockState().setValue(DirectionalBudBlock.WATERLOGGED, true));
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	public record Configuration(Block moss, Block villi, Optional<Block> flower) implements FeatureConfiguration {
		public static final Codec<VelvetumossFeature.Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				BuiltInRegistries.BLOCK.byNameCodec().fieldOf("moss").forGetter(VelvetumossFeature.Configuration::moss),
				BuiltInRegistries.BLOCK.byNameCodec().fieldOf("villi").forGetter(VelvetumossFeature.Configuration::villi),
				BuiltInRegistries.BLOCK.byNameCodec().optionalFieldOf("flower").forGetter(VelvetumossFeature.Configuration::flower))
			.apply(instance, VelvetumossFeature.Configuration::new));
	}
}
