package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HangingFantagrassPlantBlock extends GrowingPlantBodyBlock {
	public static final MapCodec<HangingFantagrassPlantBlock> CODEC = simpleCodec(HangingFantagrassPlantBlock::new);

	public HangingFantagrassPlantBlock(Properties properties) {
		super(properties, Direction.DOWN, HangingFantagrassBlock.SHAPE, false);
	}

	@Override
	protected MapCodec<HangingFantagrassPlantBlock> codec() {
		return CODEC;
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return ESItems.HANGING_FANTAGRASS.get().getDefaultInstance();
	}

	@Override
	protected GrowingPlantHeadBlock getHeadBlock() {
		return ESBlocks.HANGING_FANTAGRASS.get();
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos attachPos = pos.relative(this.growthDirection.getOpposite());
		BlockState attachState = level.getBlockState(attachPos);
		if (!this.canAttachTo(attachState)) {
			return false;
		} else {
			return attachState.is(this.getHeadBlock()) || attachState.is(this.getBodyBlock()) || attachState.is(BlockTags.LEAVES) || attachState.isFaceSturdy(level, attachPos, this.growthDirection);
		}
	}
}
