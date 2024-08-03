package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class StellagmiteSlabBlock extends SlabBlock implements Stellagmite {
	public static final MapCodec<StellagmiteSlabBlock> CODEC = simpleCodec(StellagmiteSlabBlock::new);

	public StellagmiteSlabBlock(Properties properties) {
		super(properties);
	}

	@Override
	public MapCodec<? extends SlabBlock> codec() {
		return CODEC;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		return use(itemStack, blockState, level, blockPos, player, interactionHand);
	}

	@Override
	public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
		step(entity);
	}
}
