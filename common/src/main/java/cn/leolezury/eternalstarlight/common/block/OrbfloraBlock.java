package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class OrbfloraBlock extends GrowingPlantHeadBlock implements LiquidBlockContainer {
    public static final MapCodec<OrbfloraBlock> CODEC = simpleCodec(OrbfloraBlock::new);
    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    public static final IntegerProperty ORBFLORA_AGE = IntegerProperty.create("orbflora_age", 0, 2);

    public OrbfloraBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.02);
        this.registerDefaultState(this.stateDefinition.any().setValue(ORBFLORA_AGE, 0));
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return 1;
    }

    protected boolean canGrowInto(BlockState state) {
        return state.is(Blocks.WATER);
    }

    @Override
    protected Block getBodyBlock() {
        return ESBlocks.ORBFLORA_PLANT.get();
    }

    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
        BlockPos growthDest = pos.relative(this.growthDirection);
        if (this.canGrowInto(serverLevel.getBlockState(growthDest))) {
            serverLevel.setBlockAndUpdate(growthDest, this.getGrowIntoState(state, serverLevel.random));
        }
        tryGrowOrbflora(serverLevel, state, pos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return super.isValidBonemealTarget(levelReader, blockPos, blockState) || levelReader.getBlockState(blockPos.above()).isAir();
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        super.randomTick(blockState, serverLevel, blockPos, randomSource);
        tryGrowOrbflora(serverLevel, blockState, blockPos);
    }

    private void tryGrowOrbflora(ServerLevel serverLevel, BlockState state, BlockPos pos) {
        if (serverLevel.getBlockState(pos.above()).isAir() && state.is(this)) {
            int age = state.getValue(ORBFLORA_AGE);
            if (age < 2) {
                state = state.setValue(ORBFLORA_AGE, age + 1);
                serverLevel.setBlockAndUpdate(pos, state);
            } else {
                serverLevel.setBlockAndUpdate(pos.above(), ESBlocks.ORBFLORA_LIGHT.get().defaultBlockState());
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ORBFLORA_AGE);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return false;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 ? super.getStateForPlacement(blockPlaceContext) : null;
    }

    public FluidState getFluidState(BlockState blockState) {
        return Fluids.WATER.getSource(false);
    }
}
