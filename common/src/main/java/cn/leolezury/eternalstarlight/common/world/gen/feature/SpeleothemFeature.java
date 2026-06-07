package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.block.SpeleothemBlock;
import cn.leolezury.eternalstarlight.common.block.SpeleothemThickness;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.Optional;

public class SpeleothemFeature extends Feature<SpeleothemFeature.Configuration> {
	public SpeleothemFeature(Codec<Configuration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Configuration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		RandomSource random = context.random();
		Configuration config = context.config();
		Optional<Direction> tipDirection = getTipDirection(level, origin, random);
		if (tipDirection.isEmpty()) {
			return false;
		}
		createPatch(level, random, origin.relative(tipDirection.get().getOpposite()), config);
		int maxLength = 0;
		for (int i = 0; i < 4; i++) {
			if (level.getBlockState(origin.relative(tipDirection.get(), i)).isAir()) {
				maxLength = i + 1;
			} else {
				break;
			}
		}
		if (maxLength > 0) {
			buildSpike(level, origin, tipDirection.get(), random, config, random.nextInt(maxLength) + 1);
		}
		return true;
	}

	private static Optional<Direction> getTipDirection(WorldGenLevel level, BlockPos pos, RandomSource random) {
		BlockState above = level.getBlockState(pos.above());
		BlockState below = level.getBlockState(pos.below());
		boolean canPlaceAbove = above.is(ESTags.Blocks.SPELEOTHEM_BASE_BLOCKS) || above.isFaceSturdy(level, pos.above(), Direction.DOWN);
		boolean canPlaceBelow = below.is(ESTags.Blocks.SPELEOTHEM_BASE_BLOCKS) || below.isFaceSturdy(level, pos.below(), Direction.UP);
		if (canPlaceAbove && canPlaceBelow) {
			return Optional.of(random.nextBoolean() ? Direction.DOWN : Direction.UP);
		} else if (canPlaceAbove) {
			return Optional.of(Direction.DOWN);
		} else if (canPlaceBelow) {
			return Optional.of(Direction.UP);
		}
		return Optional.empty();
	}

	private static void createPatch(WorldGenLevel level, RandomSource random, BlockPos pos, Configuration config) {
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (!(random.nextFloat() > config.chanceOfDirectionalSpread)) {
				BlockPos pos1 = pos.relative(direction);
				if (!(random.nextFloat() > config.chanceOfSpreadRadius2)) {
					BlockPos pos2 = pos1.relative(Direction.getRandom(random));
					if (!(random.nextFloat() > config.chanceOfSpreadRadius3)) {
						BlockPos pos3 = pos2.relative(Direction.getRandom(random));
						setSpikeIfPossible(level, pos3, random, config);
					}
					setSpikeIfPossible(level, pos2, random, config);
				}
				setSpikeIfPossible(level, pos1, random, config);
			}
		}
	}

	private static void setSpikeIfPossible(WorldGenLevel level, BlockPos pos, RandomSource random, Configuration config) {
		Optional<Direction> tipDir = getTipDirection(level, pos, random);
		if (tipDir.isPresent()) {
			Direction dir = tipDir.get();
			int maxLength = 0;
			for (int i = 0; i < 4; i++) {
				if (level.getBlockState(pos.relative(dir, i)).isAir()) {
					maxLength = i + 1;
				} else {
					break;
				}
			}
			if (maxLength > 0) {
				buildSpike(level, pos, dir, random, config, random.nextInt(maxLength) + 1);
			}
		}
	}

	private static void buildSpike(WorldGenLevel level, BlockPos startPos, Direction tipDirection, RandomSource random, Configuration config, int height) {
		BlockPos.MutableBlockPos pos = startPos.mutable();
		for (int i = 0; i < height; i++) {
			SpeleothemThickness thickness;
			if (height >= 3) {
				if (i == 0) {
					thickness = SpeleothemThickness.BASE;
				} else if (i < height - 2) {
					thickness = SpeleothemThickness.MIDDLE;
				} else if (i == height - 2) {
					thickness = SpeleothemThickness.FRUSTUM;
				} else {
					thickness = SpeleothemThickness.TIP;
				}
			} else if (height == 2) {
				thickness = i == 0 ? SpeleothemThickness.FRUSTUM : SpeleothemThickness.TIP;
			} else {
				thickness = SpeleothemThickness.TIP;
			}
			BlockState spikeState = config.speleothem.getState(random, pos)
				.setValue(SpeleothemBlock.TIP_DIRECTION, tipDirection)
				.setValue(SpeleothemBlock.THICKNESS, thickness);
			level.setBlock(pos, spikeState, Block.UPDATE_ALL);
			pos.move(tipDirection);
		}
	}

	public record Configuration(BlockStateProvider speleothem, float chanceOfDirectionalSpread, float chanceOfSpreadRadius2, float chanceOfSpreadRadius3) implements FeatureConfiguration {
		public static final Codec<Configuration> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				BlockStateProvider.CODEC.fieldOf("speleothem").forGetter(Configuration::speleothem),
				Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread").orElse(0.7F).forGetter(Configuration::chanceOfDirectionalSpread),
				Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2").orElse(0.5F).forGetter(Configuration::chanceOfSpreadRadius2),
				Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3").orElse(0.5F).forGetter(Configuration::chanceOfSpreadRadius3)
			).apply(instance, Configuration::new)
		);
	}
}
