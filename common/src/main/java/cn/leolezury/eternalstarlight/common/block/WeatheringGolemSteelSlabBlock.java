package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringGolemSteelSlabBlock extends SlabBlock implements WeatheringGolemSteel {
    public static final MapCodec<WeatheringGolemSteelSlabBlock> CODEC = simpleCodec(WeatheringGolemSteelSlabBlock::new);

    public WeatheringGolemSteelSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends SlabBlock> codec() {
        return CODEC;
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        this.changeOverTime(blockState, serverLevel, blockPos, randomSource);
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return !isOxidized();
    }
}
