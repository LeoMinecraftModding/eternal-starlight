package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.data.ESCrests;
import cn.leolezury.eternalstarlight.common.entity.misc.CrestEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class EnchantedGrimstoneBricksBlock extends HorizontalDirectionalBlock {
	public static final MapCodec<EnchantedGrimstoneBricksBlock> CODEC = simpleCodec(EnchantedGrimstoneBricksBlock::new);

	public EnchantedGrimstoneBricksBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
		return CODEC;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		return defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
		player.displayClientMessage(Component.translatable("message.eternal_starlight.enchanted_grimstone_pre"), true);
		player.displayClientMessage(Component.translatable("message.eternal_starlight.enchanted_grimstone_pre"), true);
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
		player.displayClientMessage(Component.translatable("message.eternal_starlight.enchanted_grimstone_post"), true);
		CrestEntity crest = new CrestEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ESCrests.BOULDERS_SHIELD);
		level.addFreshEntity(crest);
		return super.playerWillDestroy(level, blockPos, blockState, player);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}
}
