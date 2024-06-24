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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class BerriesVinesBlock extends GrowingPlantHeadBlock implements BonemealableBlock, BerriesVines {
	public static final MapCodec<BerriesVinesBlock> CODEC = simpleCodec(BerriesVinesBlock::new);

	public BerriesVinesBlock(BlockBehaviour.Properties properties) {
		super(properties, Direction.DOWN, SHAPE, false, 0.1D);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(BERRIES, false));
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

	protected Block getBodyBlock() {
		return ESBlocks.BERRIES_VINES_PLANT.get();
	}

	protected BlockState updateBodyAfterConvertedFromHead(BlockState state, BlockState blockState) {
		return blockState.setValue(BERRIES, state.getValue(BERRIES));
	}

	protected BlockState getGrowIntoState(BlockState state, RandomSource randomSource) {
		return super.getGrowIntoState(state, randomSource).setValue(BERRIES, Boolean.valueOf(randomSource.nextFloat() < 0.11F));
	}

	public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos pos, BlockState state) {
		return new ItemStack(ESItems.LUNAR_BERRIES.get());
	}

	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		return BerriesVines.use(state, level, pos);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BERRIES);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return !blockState.getValue(BERRIES);
	}

	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
		return true;
	}

	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
		serverLevel.setBlock(pos, state.setValue(BERRIES, Boolean.valueOf(true)), 2);
	}
}
