package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.SimpleMapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class AbyssFireBlock extends BaseFireBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<AbyssFireBlock> CODEC = simpleCodec(AbyssFireBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public AbyssFireBlock(Properties properties) {
        super(properties, 3.0F);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BaseFireBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean canBurn(BlockState blockState) {
        return true;
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return canSurviveOnBlock(levelReader.getBlockState(blockPos.below()));
    }

    public static boolean canSurviveOnBlock(BlockState blockState) {
        return blockState.is(ESTags.Blocks.ABYSS_BURNER);
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (this.canSurvive(blockState, levelAccessor, blockPos)) {
            if (blockState.getValue(WATERLOGGED)) {
                return this.stateDefinition.any().setValue(WATERLOGGED, true);
            } else {
                return this.defaultBlockState();
            }
        }
        return Blocks.AIR.defaultBlockState();
    }

    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!entity.getType().is(ESTags.EnityTypes.IMMUNE_ABYSS_FIRE)) {
            int ticks = ESEntityUtil.getPersistentData(entity).getInt("InAbyssFireTicks");
            ESEntityUtil.getPersistentData(entity).putInt("InAbyssFireTicks", ticks + 1);
            if (ticks == 30) {
                ESEntityUtil.getPersistentData(entity).putInt("InAbyssFireTicks", 0);
                entity.hurt(level.damageSources().inFire(), 3.0F);
            }
        }
    }
}
