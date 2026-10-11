package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
	@WrapOperation(method = "findRespawnPositionAndUseSpawnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;"))
	private ServerLevel modifyRespawnDimension(MinecraftServer instance, Operation<ServerLevel> original) {
		if (ESConfig.INSTANCE.respawnInEternalStarlight) {
			return instance.getLevel(ESDimensions.STARLIGHT_KEY);
		}
		return original.call(instance);
	}

	@WrapOperation(method = "swing", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;resetAttackStrengthTicker()V"))
	private void keepMainHandAttackStrengthOnOffhandSwing(ServerPlayer instance, Operation<Void> original, @Local(argsOnly = true, ordinal = 0) InteractionHand hand) {
		if (hand == InteractionHand.OFF_HAND && instance.getOffhandItem().is(ESItems.DAGGER_OF_HUNGER.get())) {
			return;
		}
		original.call(instance);
	}
}
