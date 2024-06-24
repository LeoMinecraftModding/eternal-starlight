package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TorreyaVinesBlock extends GrowingPlantHeadBlock {
	public static final MapCodec<TorreyaVinesBlock> CODEC = simpleCodec(TorreyaVinesBlock::new);
	public static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

	public TorreyaVinesBlock(Properties properties) {
		super(properties, Direction.DOWN, SHAPE, false, 0.1D);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}

	@Override
	protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
		return CODEC;
	}

	protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
		return 1;
	}

	protected boolean canGrowInto(BlockState state) {
		return state.isAir();
	}

	@Override
	public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		BlockPos blockPos2 = blockPos.relative(this.growthDirection.getOpposite());
		BlockState blockState2 = levelReader.getBlockState(blockPos2);
		if (!this.canAttachTo(blockState2)) {
			return false;
		} else {
			return blockState2.is(this.getHeadBlock()) || blockState2.is(this.getBodyBlock()) || blockState2.is(BlockTags.LEAVES) || blockState2.isFaceSturdy(levelReader, blockPos2, this.growthDirection);
		}
	}

	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		BlockState state = levelAccessor.getBlockState(blockPos.above());
		if (state.is(ESBlocks.TORREYA_VINES_PLANT.get()) && !levelAccessor.getBlockState(blockPos.above(2)).is(ESBlocks.TORREYA_VINES_PLANT.get()) && !state.getValue(TorreyaVinesPlantBlock.TOP)) {
			state = state.setValue(TorreyaVinesPlantBlock.TOP, true);
			levelAccessor.setBlock(blockPos.above(), state, 2);
		}
		return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
	}

	protected Block getBodyBlock() {
		return ESBlocks.TORREYA_VINES_PLANT.get();
	}

	public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos pos, BlockState state) {
		return new ItemStack(ESItems.TORREYA_VINES.get());
	}
}
