package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class EnergyBlock extends Block {
	public static final MapCodec<EnergyBlock> CODEC = simpleCodec(EnergyBlock::new);
	public static BooleanProperty LIT = BlockStateProperties.LIT;

	public EnergyBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
	}

	@Override
	protected MapCodec<? extends Block> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIT);
		super.createBlockStateDefinition(builder);
	}

	@Override
	protected void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
		if (blockState.getValue(LIT) && blockHitResult.getType() != HitResult.Type.MISS) {
			level.setBlockAndUpdate(blockHitResult.getBlockPos(), blockState.setValue(LIT, false));
		}
		if (projectile.getOwner() instanceof ServerPlayer serverPlayer) {
			ESCriteriaTriggers.DEACTIVATE_ENERGY_BLOCK.get().trigger(serverPlayer);
		}
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
		if (blockState.getValue(LIT)) {
			level.setBlockAndUpdate(blockPos, blockState.setValue(LIT, false));
			if (player instanceof ServerPlayer serverPlayer) {
				ESCriteriaTriggers.DEACTIVATE_ENERGY_BLOCK.get().trigger(serverPlayer);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	}
}
