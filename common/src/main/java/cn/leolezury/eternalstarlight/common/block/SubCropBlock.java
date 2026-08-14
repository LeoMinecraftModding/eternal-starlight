package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.CropUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SubCropBlock extends BasicCropBlock {
	private final int maxHeight;
	private final ResourceKey<Block> origin;

	public SubCropBlock(Properties properties, int maxHeight, ResourceKey<Block> origin, CropUtil.SubCropParam param) {
		super(properties, param);
		this.maxHeight = maxHeight;
		this.origin = origin;
	}

	@Override
	protected int detectBoxBottomModifier() {
		return this.maxHeight + 1;
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return blockState.is(BuiltInRegistries.BLOCK.get(this.origin));
	}
}
