package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.entity.misc.TearBomb;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntBlock.class)
public abstract class TntBlockMixin {
	@Inject(method = "explode(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
	private static void explode(Level level, BlockPos blockPos, LivingEntity livingEntity, CallbackInfo ci) {
		if (level.getBlockState(blockPos).is(ESBlocks.TEAR_BOMB.get())) {
			if (livingEntity instanceof ServerPlayer serverPlayer) {
				ESCriteriaTriggers.IGNITE_TEAR_BOMB.get().trigger(serverPlayer);
			}
			ci.cancel();
			if (!level.isClientSide) {
				TearBomb bomb = new TearBomb(level, blockPos.getX() + 0.5F, blockPos.getY(), blockPos.getZ() + 0.5F, livingEntity);
				level.addFreshEntity(bomb);
				level.playSound(null, bomb.getX(), bomb.getY(), bomb.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
				level.gameEvent(livingEntity, GameEvent.PRIME_FUSE, blockPos);
			}
		}
	}
}
