package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StarfireBirdAviaryBlock extends StarfireBirdNestBlock {
	public static final MapCodec<StarfireBirdAviaryBlock> CODEC = simpleCodec(StarfireBirdAviaryBlock::new);
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

	public StarfireBirdAviaryBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(defaultBlockState().setValue(OPEN, false));
	}

	@Override
	protected MapCodec<StarfireBirdAviaryBlock> codec() {
		return CODEC;
	}

	@Override
	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return Shapes.block();
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		level.setBlockAndUpdate(pos, state.setValue(OPEN, !state.getValue(OPEN)));
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return true;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(OPEN);
		super.createBlockStateDefinition(builder);
	}
}
