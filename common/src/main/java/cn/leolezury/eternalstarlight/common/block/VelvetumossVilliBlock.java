package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class VelvetumossVilliBlock extends DirectionalBudBlock implements BonemealableBlock {
	public static final MapCodec<VelvetumossVilliBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("full_block").forGetter((block) -> block.fullBlock),
		propertiesCodec()
	).apply(instance, VelvetumossVilliBlock::new));
	public static final IntegerProperty AGE = BlockStateProperties.AGE_25;

	private final Holder<Block> fullBlock;

	public VelvetumossVilliBlock(Holder<Block> fullBlock, Properties properties) {
		super(properties);
		this.fullBlock = fullBlock;
		this.registerDefaultState(defaultBlockState().setValue(AGE, 0));
	}

	@Override
	protected MapCodec<? extends DirectionalBudBlock> codec() {
		return CODEC;
	}

	@Override
	protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (blockState.getValue(AGE) == 25) {
			serverLevel.setBlockAndUpdate(blockPos, fullBlock.value().defaultBlockState());
		} else if (randomSource.nextInt(5) == 0) {
			serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(AGE, Math.min(blockState.getValue(AGE) + randomSource.nextInt(3), 25)));
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		if (blockState.getValue(AGE) == 25) {
			serverLevel.setBlockAndUpdate(blockPos, fullBlock.value().defaultBlockState());
		} else {
			serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(AGE, Math.min(blockState.getValue(AGE) + randomSource.nextInt(5) + 1, 25)));
		}
	}
}
