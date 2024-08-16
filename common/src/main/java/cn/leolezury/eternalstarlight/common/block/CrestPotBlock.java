package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.CrestPotBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrestPotBlock extends BaseEntityBlock {
	public static final MapCodec<CrestPotBlock> CODEC = simpleCodec(CrestPotBlock::new);

	public CrestPotBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new CrestPotBlockEntity(blockPos, blockState);
	}

	protected RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}
}
