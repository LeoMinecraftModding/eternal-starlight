package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ESShortBushOnStoneBlock extends BushBlock {
    public static final MapCodec<ESShortBushOnStoneBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.INT.fieldOf("height").forGetter((block) -> block.height),
            propertiesCodec()
    ).apply(instance, ESShortBushOnStoneBlock::new));
    private final int height;

    public ESShortBushOnStoneBlock(Properties properties) {
        this(3, properties);
    }

    public ESShortBushOnStoneBlock(int height, Properties properties) {
        super(properties);
        this.height = height;
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(ESTags.Blocks.BASE_STONE_STARLIGHT);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return Block.box(2.0D, 0.0D, 2.0D, 14.0D, height, 14.0D);
    }
}
