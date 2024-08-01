package cn.leolezury.eternalstarlight.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class ESFeature<FC extends FeatureConfiguration> extends Feature<FC> {
	public ESFeature(Codec<FC> codec) {
		super(codec);
	}

	protected boolean isValidBlock(BlockState state, List<TagKey<Block>> tags) {
		for (TagKey<Block> tag : tags) {
			if (state.is(tag)) {
				return true;
			}
		}
		return false;
	}

	protected boolean setBlockIfEmpty(WorldGenLevel level, BlockPos pos, BlockState state) {
		if (level.isEmptyBlock(pos)) {
			setBlock(level, pos, state);
			return true;
		}
		return false;
	}

	protected boolean setBlockIfEmpty(WorldGenLevel level, BlockPos pos, BlockState state, boolean replaceFluid, List<TagKey<Block>> ignored) {
		if (level.isEmptyBlock(pos) || isValidBlock(level.getBlockState(pos), ignored) || (replaceFluid && !level.getFluidState(pos).isEmpty() && level.getBlockState(pos).is(level.getFluidState(pos).createLegacyBlock().getBlock()))) {
			setBlock(level, pos, state);
			return true;
		}
		return false;
	}

	protected BlockPos getChunkCoordinate(BlockPos origin) {
		return new BlockPos((origin.getX() / 16) * 16, origin.getY(), (origin.getZ() / 16) * 16);
	}
}
