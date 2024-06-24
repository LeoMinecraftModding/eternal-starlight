package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LunarisCactusGelBlock extends SlimeBlock implements ExtendedBlock {
	public LunarisCactusGelBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isSticky(BlockState blockState) {
		return true;
	}

	@Override
	public boolean canStickToEachOther(BlockState blockState, BlockState blockState2) {
		return !blockState2.is(Blocks.SLIME_BLOCK) && !blockState2.is(Blocks.HONEY_BLOCK);
	}
}
