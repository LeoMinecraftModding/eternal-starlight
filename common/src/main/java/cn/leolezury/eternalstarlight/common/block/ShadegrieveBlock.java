package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ShadegrieveBlock extends Block {
	public static final MapCodec<ShadegrieveBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		Codec.BOOL.fieldOf("particles").forGetter((block) -> block.particles),
		propertiesCodec()
	).apply(instance, ShadegrieveBlock::new));
	public static final BooleanProperty TOP = BooleanProperty.create("top");
	private final boolean particles;

	public ShadegrieveBlock(boolean particles, Properties properties) {
		super(properties);
		this.particles = particles;
		this.registerDefaultState(this.stateDefinition.any().setValue(TOP, false));
	}

	@Override
	protected MapCodec<ShadegrieveBlock> codec() {
		return CODEC;
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
			sturdy &= level.getBlockState(blockPos.above()).isFaceSturdy(level, blockPos.above(), dir);
		}
		return state.setValue(TOP, !(level.getBlockState(blockPos.above()).getBlock() instanceof ShadegrieveBlock) && sturdy);
	}

	@Override
	public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		boolean sturdy = true;
		List<Direction> xzDirections = Arrays.stream(Direction.values()).filter(dir -> dir.getAxis() != Direction.Axis.Y).toList();
		for (Direction dir : xzDirections) {
			sturdy &= level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), dir);
		}
		return state.setValue(TOP, !(level.getBlockState(pos.above()).getBlock() instanceof ShadegrieveBlock) && sturdy);
	}

	@Override
	public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
		if (particles && randomSource.nextInt(5) == 0) {
			ParticleUtils.spawnParticlesOnBlockFaces(level, blockPos, ESParticles.SHADEGRIEVE_LEAVES.get(), ConstantInt.of(1));
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TOP);
	}
}
