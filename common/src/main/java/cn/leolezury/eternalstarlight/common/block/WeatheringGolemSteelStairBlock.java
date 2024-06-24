package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringGolemSteelStairBlock extends StairBlock implements WeatheringGolemSteel {
	public static final MapCodec<WeatheringGolemSteelStairBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
		return instance.group(BlockState.CODEC.fieldOf("base_state").forGetter((stairBlock) -> {
			return stairBlock.baseState;
		}), propertiesCodec()).apply(instance, WeatheringGolemSteelStairBlock::new);
	});

	public WeatheringGolemSteelStairBlock(BlockState blockState, Properties properties) {
		super(blockState, properties);
	}

	@Override
	public MapCodec<? extends StairBlock> codec() {
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
