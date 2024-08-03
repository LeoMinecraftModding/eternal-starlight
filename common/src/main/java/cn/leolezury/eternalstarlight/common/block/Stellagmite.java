package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public interface Stellagmite {
	Supplier<ImmutableMap<Block, Block>> TO_MOLTEN = Suppliers.memoize(() -> ImmutableMap.of(
		ESBlocks.STELLAGMITE.get(), ESBlocks.MOLTEN_STELLAGMITE.get(),
		ESBlocks.STELLAGMITE_SLAB.get(), ESBlocks.MOLTEN_STELLAGMITE_SLAB.get(),
		ESBlocks.STELLAGMITE_STAIRS.get(), ESBlocks.MOLTEN_STELLAGMITE_STAIRS.get(),
		ESBlocks.STELLAGMITE_WALL.get(), ESBlocks.MOLTEN_STELLAGMITE_WALL.get()
	));

	default ItemInteractionResult use(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand) {
		Block block = blockState.getBlock();
		ImmutableMap<Block, Block> toMolten = TO_MOLTEN.get();
		if (toMolten.containsKey(block) && itemStack.is(ESTags.Items.STELLAGMITE_IGNITERS)) {
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

	default void step(Entity entity) {
		if (!entity.isSteppingCarefully() && entity instanceof LivingEntity) {
			entity.hurt(entity.level().damageSources().hotFloor(), 1.0F);
		}
	}
}
