package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.data.ESCrests;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
		if (blockPlaceContext.getPlayer() != null) {
			Direction direction = Direction.NORTH;
			Direction[] directions = Direction.orderedByNearest(blockPlaceContext.getPlayer());
			for (int i = 0; i < 3; i++) {
				Direction dir = directions[i];
				if (dir != Direction.UP && dir != Direction.DOWN) {
					direction = dir;
					break;
				}
			}
			direction = direction.getOpposite();
			return defaultBlockState().setValue(FACING, direction);
		}

		return defaultBlockState();
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		if (!level.isClientSide && player.getItemInHand(interactionHand).is(Items.SHIELD)) {
			if (ESCrestUtil.upgradeCrest(player, ESCrests.BOULDERS_SHIELD)) {
				if (!player.getAbilities().instabuild) {
					player.getItemInHand(interactionHand).shrink(1);
				}
				return ItemInteractionResult.SUCCESS;
			}
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}
}
