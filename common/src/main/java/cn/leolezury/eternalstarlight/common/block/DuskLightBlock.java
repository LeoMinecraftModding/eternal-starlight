package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.DuskLightBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class DuskLightBlock extends BaseEntityBlock {
	public static final MapCodec<DuskLightBlock> CODEC = simpleCodec(DuskLightBlock::new);

	public DuskLightBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<DuskLightBlock> codec() {
		return CODEC;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
		if (!player.isCrouching() && level.getBlockEntity(blockPos) instanceof DuskLightBlockEntity entity) {
			if (!level.isClientSide) {
				entity.setFacing();
				return InteractionResult.SUCCESS;
			}
			return InteractionResult.PASS;
		}
		return InteractionResult.PASS;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new DuskLightBlockEntity(blockPos, blockState);
	}

	@Override
	protected RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return createTickerHelper(blockEntityType, ESBlockEntities.DUSK_LIGHT.get(), DuskLightBlockEntity::tick);
	}
}
