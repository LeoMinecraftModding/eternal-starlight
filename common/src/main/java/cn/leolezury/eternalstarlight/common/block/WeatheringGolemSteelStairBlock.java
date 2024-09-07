package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

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
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		return use(stack, state, level, pos, player);
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
