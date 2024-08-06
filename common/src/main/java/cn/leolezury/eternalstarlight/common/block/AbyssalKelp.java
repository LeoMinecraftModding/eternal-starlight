package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface AbyssalKelp {
	BooleanProperty BERRIES = BlockStateProperties.BERRIES;

	static InteractionResult use(BlockState state, Level level, BlockPos pos) {
		if (state.getValue(BERRIES)) {
			Block.popResource(level, pos, new ItemStack(ESItems.ABYSSAL_FRUIT.get(), 1));
			float f = Mth.randomBetween(level.random, 0.8F, 1.2F);
			level.playSound(null, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, f);
			level.setBlock(pos, state.setValue(BERRIES, false), 2);
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return InteractionResult.PASS;
		}
	}
}
