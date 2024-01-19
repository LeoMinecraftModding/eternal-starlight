package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CaveMossPlantBlock extends GrowingPlantBodyBlock {
    public static final MapCodec<CaveMossPlantBlock> CODEC = simpleCodec(CaveMossPlantBlock::new);

    public CaveMossPlantBlock(Properties properties) {
        super(properties, Direction.DOWN, CaveMossBlock.SHAPE, false);
    }

    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return CODEC;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return ItemInit.CAVE_MOSS.get().getDefaultInstance();
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BlockInit.CAVE_MOSS.get();
    }
}
