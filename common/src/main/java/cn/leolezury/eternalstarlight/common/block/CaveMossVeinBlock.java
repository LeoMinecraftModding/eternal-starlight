package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class CaveMossVeinBlock extends SimpleMultifaceBlock {
	public static final MapCodec<CaveMossVeinBlock> CODEC = simpleCodec(CaveMossVeinBlock::new);

	public CaveMossVeinBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<CaveMossVeinBlock> codec() {
		return CODEC;
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		return ESItems.CAVE_MOSS.get().getDefaultInstance();
	}
}
