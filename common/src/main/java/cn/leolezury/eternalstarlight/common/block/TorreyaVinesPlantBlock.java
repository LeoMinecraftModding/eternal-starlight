package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class TorreyaVinesPlantBlock extends GrowingPlantBodyBlock {
    public static final MapCodec<TorreyaVinesPlantBlock> CODEC = simpleCodec(TorreyaVinesPlantBlock::new);
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    
    public TorreyaVinesPlantBlock(Properties properties) {
        super(properties, Direction.DOWN, TorreyaVinesBlock.SHAPE, false);
        this.registerDefaultState(this.stateDefinition.any().setValue(TOP, false));
    }

    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return null;
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) ESBlocks.TORREYA_VINES.get();
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.relative(this.growthDirection.getOpposite());
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        if (!this.canAttachTo(blockState2)) {
            return false;
        } else {
            return blockState2.is(this.getHeadBlock()) || blockState2.is(this.getBodyBlock()) || blockState2.is(BlockTags.LEAVES) || blockState2.isFaceSturdy(levelReader, blockPos2, this.growthDirection);
        }
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        BlockState state = super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
        if (!levelAccessor.getBlockState(blockPos.above()).is(this) && state.hasProperty(TOP)) {
            state = state.setValue(TOP, true);
        }
        return state;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(ESItems.TORREYA_VINES.get());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TOP);
    }
}
