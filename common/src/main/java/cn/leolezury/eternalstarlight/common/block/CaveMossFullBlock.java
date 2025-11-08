package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.data.ESConfiguredFeatures;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CaveMossFullBlock extends Block implements BonemealableBlock {
	public static final MapCodec<CaveMossFullBlock> CODEC = simpleCodec(CaveMossFullBlock::new);
	public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");

	@Override
	public MapCodec<CaveMossFullBlock> codec() {
		return CODEC;
	}

	public CaveMossFullBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(BOTTOM, false));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state == null) return null;
		boolean sturdy = true;
		Level level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		List<Direction> xzDirections = Arrays.stream(Direction.values()).filter(dir -> dir.getAxis() != Direction.Axis.Y).toList();
		for (Direction dir : xzDirections) {
			sturdy &= level.getBlockState(blockPos.below()).isFaceSturdy(level, blockPos.below(), dir);
		}
		return state.setValue(BOTTOM, !level.getBlockState(blockPos.below()).is(this) && sturdy);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos blockPos, BlockPos blockPos2) {
		boolean sturdy = true;
		List<Direction> xzDirections = Arrays.stream(Direction.values()).filter(dir -> dir.getAxis() != Direction.Axis.Y).toList();
		for (Direction dir : xzDirections) {
			sturdy &= level.getBlockState(blockPos.below()).isFaceSturdy(level, blockPos.below(), dir);
		}
		return state.setValue(BOTTOM, !level.getBlockState(blockPos.below()).is(this) && sturdy);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return levelReader.getBlockState(blockPos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		serverLevel.registryAccess().registry(Registries.CONFIGURED_FEATURE).flatMap((registry) -> registry.getHolder(ESConfiguredFeatures.CAVE_MOSS_PATCH_BONEMEAL)).ifPresent((reference) -> reference.value().place(serverLevel, serverLevel.getChunkSource().getGenerator(), randomSource, blockPos.above()));
	}

	@Override
	public Type getType() {
		return Type.NEIGHBOR_SPREADER;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BOTTOM);
	}
}
