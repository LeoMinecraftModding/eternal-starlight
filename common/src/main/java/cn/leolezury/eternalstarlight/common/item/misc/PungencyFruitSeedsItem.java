package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PungencyFruitSeedsItem extends ItemNameBlockItem {
	public PungencyFruitSeedsItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	protected boolean canPlace(BlockPlaceContext context, BlockState state) {
		return super.canPlace(context, state) && ESBlocks.PUNGENCY_FRUIT_VINES.get().canPlaceSeeds(context.getLevel().getBlockState(context.getClickedPos().below()), context.getLevel(), context.getClickedPos().below());
	}
}
