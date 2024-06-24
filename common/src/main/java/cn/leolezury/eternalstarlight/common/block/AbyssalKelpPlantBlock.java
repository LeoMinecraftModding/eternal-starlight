package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;

public class AbyssalKelpPlantBlock extends GrowingPlantBodyBlock implements LiquidBlockContainer, AbyssalKelp {
	public static final MapCodec<AbyssalKelpPlantBlock> CODEC = simpleCodec(AbyssalKelpPlantBlock::new);

	public AbyssalKelpPlantBlock(Properties properties) {
		super(properties, Direction.UP, Shapes.block(), true);
		this.registerDefaultState(this.stateDefinition.any().setValue(BERRIES, false));
	}

	@Override
	protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
		return CODEC;
	}

	@Override
	protected GrowingPlantHeadBlock getHeadBlock() {
		return ESBlocks.ABYSSAL_KELP.get();
	}

	protected BlockState updateHeadAfterConvertedFromBody(BlockState state, BlockState blockState) {
		return blockState.setValue(BERRIES, state.getValue(BERRIES));
	}

	public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos pos, BlockState state) {
		return new ItemStack(ESItems.ABYSSAL_FRUIT.get());
	}

	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		return AbyssalKelp.use(state, level, pos);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BERRIES);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return !blockState.getValue(BERRIES);
	}

	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
		return true;
	}

	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		serverLevel.setBlock(blockPos, blockState.setValue(BERRIES, Boolean.valueOf(true)), 2);
	}

	public FluidState getFluidState(BlockState blockState) {
		return Fluids.WATER.getSource(false);
	}

	public boolean canAttachTo(BlockState blockState) {
		return this.getHeadBlock().canAttachTo(blockState);
	}

	public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
		return false;
	}

	public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
		return false;
	}
}
