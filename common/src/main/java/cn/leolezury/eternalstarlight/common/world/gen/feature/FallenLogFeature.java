package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class FallenLogFeature extends ESFeature<FallenLogFeature.Configuration> {
	public FallenLogFeature(Codec<FallenLogFeature.Configuration> codec) {
		super(codec);
	}

	protected void placeLog(WorldGenLevel worldGenLevel, BlockPos pos, BlockStateProvider provider, int length, boolean eastWest) {
		RandomSource randomsource = worldGenLevel.getRandom();
		for (int i = 0; i < length; i++) {
			for (; !worldGenLevel.getBlockState(pos).is(BlockTags.DIRT) && !worldGenLevel.getBlockState(pos).is(BlockTags.SNOW) && !worldGenLevel.getBlockState(pos).is(ESTags.Blocks.BASE_STONE_STARLIGHT) && pos.getY() > worldGenLevel.getMinBuildHeight() + 2; pos = pos.below()) {
			}
			pos = pos.above();
			if (pos.getY() <= worldGenLevel.getMinBuildHeight() + 2) {
				return;
			}
			setBlockIfEmpty(worldGenLevel, pos, provider.getState(randomsource, pos).setValue(BlockStateProperties.AXIS, eastWest ? Direction.Axis.X : Direction.Axis.Z));
			if (worldGenLevel.getBlockState(pos).is(BlockTags.LOGS)) {
				if (randomsource.nextBoolean()) {
					setBlockIfEmpty(worldGenLevel, pos.offset(eastWest ? 0 : 1, 0, eastWest ? 1 : 0), (randomsource.nextBoolean() ? Blocks.VINE : Blocks.GLOW_LICHEN).defaultBlockState().setValue(eastWest ? BlockStateProperties.NORTH : BlockStateProperties.WEST, true));
				}
				if (randomsource.nextBoolean()) {
					setBlockIfEmpty(worldGenLevel, pos.offset(eastWest ? 0 : -1, 0, eastWest ? -1 : 0), (randomsource.nextBoolean() ? Blocks.VINE : Blocks.GLOW_LICHEN).defaultBlockState().setValue(eastWest ? BlockStateProperties.SOUTH : BlockStateProperties.EAST, true));
				}
			}
		}
	}

	public boolean place(FeaturePlaceContext<FallenLogFeature.Configuration> context) {
		RandomSource randomsource = context.random();
		BlockPos pos = context.origin();
		BlockStateProvider provider = context.config().log();
		boolean eastWest = randomsource.nextBoolean();
		placeLog(context.level(), pos, provider, randomsource.nextInt(5) + 4, eastWest);
		return true;
	}

	public record Configuration(BlockStateProvider log) implements FeatureConfiguration {
		public static final Codec<FallenLogFeature.Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockStateProvider.CODEC.fieldOf("log").forGetter(Configuration::log)).apply(instance, Configuration::new));
	}
}

