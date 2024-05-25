package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class PolishedToxiteBlock extends Block {
    public static final MapCodec<PolishedToxiteBlock> CODEC = simpleCodec(PolishedToxiteBlock::new);

    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);

    public PolishedToxiteBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(PART, Part.MIDDLE));
    }

    @Override
    protected MapCodec<PolishedToxiteBlock> codec() {
        return CODEC;
    }

    @Override
    protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        boolean above = levelAccessor.getBlockState(blockPos.above()).is(this);
        boolean below = levelAccessor.getBlockState(blockPos.below()).is(this);
        if (above && below || (!above && !below)) {
            return blockState.setValue(PART, Part.MIDDLE);
        } else if (above) {
            return blockState.setValue(PART, Part.LOWER);
        } else {
            return blockState.setValue(PART, Part.UPPER);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }

    public enum Part implements StringRepresentable {
        UPPER("upper"),
        MIDDLE("middle"),
        LOWER("lower");

        private final String name;

        Part(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
