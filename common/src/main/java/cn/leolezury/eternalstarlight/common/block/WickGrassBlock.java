package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WickGrassBlock extends TallSeagrassBlock {
	public static final MapCodec<WickGrassBlock> CODEC = simpleCodec(WickGrassBlock::new);

	public WickGrassBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return new ItemStack(ESItems.WICK_GRASS.get());
	}
}
