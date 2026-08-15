package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.util.CropUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class CropBranchBlock extends BasicCropBlock {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

	public CropBranchBlock(Properties properties, CropUtil.CropParam param) {
		super(properties, param);

		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0).setValue(WITHERED, false).setValue(WATERLOGGED, false).setValue(ETHERLOGGED, false).setValue(AXIS, Direction.Axis.Y));
	}

	protected BlockState rotate(BlockState blockState, Rotation rotation) {
		return rotatePillar(blockState, rotation);
	}

	public static BlockState rotatePillar(BlockState blockState, Rotation rotation) {
		switch (rotation) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (blockState.getValue(AXIS)) {
					case X -> {
						return blockState.setValue(AXIS, Direction.Axis.Z);
					}
					case Z -> {
						return blockState.setValue(AXIS, Direction.Axis.X);
					}
					default -> {
						return blockState;
					}
				}
			default:
				return blockState;
		}
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}
}
