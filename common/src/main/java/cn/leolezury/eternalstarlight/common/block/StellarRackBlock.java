package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.StellarRackBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class StellarRackBlock extends BaseEntityBlock {
	public static final MapCodec<StellarRackBlock> CODEC = simpleCodec(StellarRackBlock::new);
	protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

	public StellarRackBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		if (level.getBlockEntity(blockPos) instanceof StellarRackBlockEntity entity) {
			ItemStack stack = player.getItemInHand(interactionHand);
			if (entity.anyEmpty() && !stack.isEmpty()) {
				if (!level.isClientSide && entity.placeItem(stack.copyWithCount(1))) {
					stack.consume(1, player);
					return ItemInteractionResult.SUCCESS;
				}
				return ItemInteractionResult.CONSUME;
			}
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	protected void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		if (!blockState.is(blockState2.getBlock())) {
			BlockEntity blockEntity = level.getBlockEntity(blockPos);
			if (blockEntity instanceof StellarRackBlockEntity entity) {
				Containers.dropContents(level, blockPos, entity.getItems());
			}
			super.onRemove(blockState, level, blockPos, blockState2, bl);
		}
	}

	@Override
	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	protected RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new StellarRackBlockEntity(blockPos, blockState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, ESBlockEntities.STELLAR_RACK.get(), StellarRackBlockEntity::tick);
	}
}
