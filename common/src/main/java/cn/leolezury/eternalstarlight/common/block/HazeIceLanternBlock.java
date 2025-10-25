package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HazeIceLanternBlock extends LanternBlock {
	public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 6, 13);
	public static final VoxelShape HANGING_SHAPE = Block.box(3, 4, 3, 13, 10, 13);

	public static final MapCodec<HazeIceLanternBlock> CODEC = simpleCodec(HazeIceLanternBlock::new);

	public HazeIceLanternBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(HANGING) ? HANGING_SHAPE : SHAPE;
	}
}
