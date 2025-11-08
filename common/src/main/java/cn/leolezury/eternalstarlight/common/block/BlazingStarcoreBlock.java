package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class BlazingStarcoreBlock extends Block {
	public static final MapCodec<BlazingStarcoreBlock> CODEC = simpleCodec(BlazingStarcoreBlock::new);

	public BlazingStarcoreBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<BlazingStarcoreBlock> codec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		return state == null ? null : (isAffectedByFluid(state, context.getLevel(), context.getClickedPos(), FluidTags.WATER) ? ESBlocks.STARCORE_BLOCK.get().defaultBlockState() : state);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		BlockState newState = super.updateShape(state, direction, neighborState, level, pos, neighborPos);
		return isAffectedByFluid(newState, level, pos, FluidTags.WATER) ? ESBlocks.STARCORE_BLOCK.get().defaultBlockState() : newState;
	}

	private boolean isAffectedByFluid(BlockState state, LevelAccessor level, BlockPos pos, TagKey<Fluid> fluid) {
		return Arrays.stream(Direction.values()).anyMatch(direction -> level.getFluidState(pos.relative(direction)).is(fluid)) || state.getFluidState().is(fluid);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		if (itemStack.is(ItemTags.PICKAXES)) {
			level.destroyBlock(blockPos, false);
			level.setBlockAndUpdate(blockPos, ESBlocks.STARCORE_LIGHT.get().defaultBlockState());
			itemStack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemStack));
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
	}
}
