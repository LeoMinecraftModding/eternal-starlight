package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public interface Stellagmite {
	Supplier<ImmutableMap<Block, Block>> TO_MOLTEN = Suppliers.memoize(() -> ImmutableMap.of(
		ESBlocks.STELLAGMITE.get(), ESBlocks.MOLTEN_STELLAGMITE.get(),
		ESBlocks.STELLAGMITE_SLAB.get(), ESBlocks.MOLTEN_STELLAGMITE_SLAB.get(),
		ESBlocks.STELLAGMITE_STAIRS.get(), ESBlocks.MOLTEN_STELLAGMITE_STAIRS.get(),
		ESBlocks.STELLAGMITE_WALL.get(), ESBlocks.MOLTEN_STELLAGMITE_WALL.get()
	));

	@Nullable
	default BlockState getPlacementState(BlockPlaceContext context, BlockState state) {
		return state == null ? null : (isAffectedByFluid(state, context.getLevel(), context.getClickedPos(), FluidTags.LAVA) ? asMolten(state) : (
			isAffectedByFluid(state, context.getLevel(), context.getClickedPos(), FluidTags.WATER) ?
				asCooled(state) : state
		));
	}

	default ItemInteractionResult use(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand) {
		Block block = blockState.getBlock();
		ImmutableMap<Block, Block> toMolten = TO_MOLTEN.get();
		if (toMolten.containsKey(block) && itemStack.is(ESTags.Items.STELLAGMITE_IGNITERS) && (isAffectedByFluid(blockState, level, blockPos, FluidTags.LAVA) || !isAffectedByFluid(blockState, level, blockPos, FluidTags.WATER))) {
			SoundEvent soundEvent = itemStack.isDamageableItem() ? SoundEvents.FLINTANDSTEEL_USE : SoundEvents.FIRECHARGE_USE;
			level.playSound(player, blockPos.getX(), blockPos.getY(), blockPos.getZ(), soundEvent, player.getSoundSource(), 1.0F, player.getRandom().nextFloat() * 0.4F + 0.8F);
			if (!itemStack.isDamageableItem()) {
				itemStack.consume(1, player);
			} else {
				itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
			}
			Block moltenBlock = toMolten.get(block);
			if (moltenBlock != null) {
				level.setBlockAndUpdate(blockPos, moltenBlock.withPropertiesOf(blockState));
				return ItemInteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	default BlockState updateShape(BlockState state, LevelAccessor level, BlockPos pos) {
		return isAffectedByFluid(state, level, pos, FluidTags.LAVA) ? asMolten(state) : (
			isAffectedByFluid(state, level, pos, FluidTags.WATER) ?
				asCooled(state) : state
		);
	}

	default void step(Entity entity) {
		if (this instanceof Block block && TO_MOLTEN.get().containsValue(block) && !entity.isSteppingCarefully() && entity instanceof LivingEntity) {
			entity.hurt(entity.level().damageSources().hotFloor(), 1.0F);
		}
	}

	default boolean isAffectedByFluid(BlockState state, LevelAccessor level, BlockPos pos, TagKey<Fluid> fluid) {
		return Arrays.stream(Direction.values()).anyMatch(direction -> level.getFluidState(pos.relative(direction)).is(fluid)) || state.getFluidState().is(fluid);
	}

	default BlockState asMolten(BlockState state) {
		Block molten = TO_MOLTEN.get().get(state.getBlock());
		return molten != null ? molten.withPropertiesOf(state) : state;
	}

	default BlockState asCooled(BlockState state) {
		Optional<Block> cooled = TO_MOLTEN.get().entrySet().stream().filter(e -> e.getValue() == state.getBlock()).findFirst().map(Map.Entry::getKey);
		return cooled.map(block -> block.withPropertiesOf(state)).orElse(state);
	}
}
