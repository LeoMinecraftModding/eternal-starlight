package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BouldershroomRootsBlock extends GrowingPlantHeadBlock {
	public static final MapCodec<BouldershroomRootsBlock> CODEC = simpleCodec(BouldershroomRootsBlock::new);
	public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public BouldershroomRootsBlock(Properties properties) {
		super(properties, Direction.DOWN, SHAPE, false, 0.02);
	}

	@Override
	protected MapCodec<BouldershroomRootsBlock> codec() {
		return CODEC;
	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
		return 1;
	}

	@Override
	protected boolean canGrowInto(BlockState state) {
		return state.isAir();
	}

	@Override
	protected Block getBodyBlock() {
		return ESBlocks.BOULDERSHROOM_ROOTS_PLANT.get();
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return false;
	}
}
