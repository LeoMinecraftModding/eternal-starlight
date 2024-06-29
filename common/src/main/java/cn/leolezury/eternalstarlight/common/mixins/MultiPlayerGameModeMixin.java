package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {
	@Inject(method = "performUseItemOn", at = @At(value = "HEAD"), cancellable = true)
	private void performUseItemOn(LocalPlayer localPlayer, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
		if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
			cir.setReturnValue(InteractionResult.SUCCESS);
			cir.cancel();
		}
	}

	@Inject(method = "useItem", at = @At(value = "HEAD"), cancellable = true)
	private void useItem(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
		if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
			cir.setReturnValue(InteractionResult.PASS);
			cir.cancel();
		}
	}

	@Inject(method = "attack", at = @At(value = "HEAD"), cancellable = true)
	private void attack(Player player, Entity entity, CallbackInfo ci) {
		if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
			ci.cancel();
		}
	}

	@Inject(method = "interact", at = @At(value = "HEAD"), cancellable = true)
	private void interact(Player player, Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
		if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
			cir.setReturnValue(InteractionResult.PASS);
			cir.cancel();
		}
	}

	@Inject(method = "startDestroyBlock", at = @At(value = "HEAD"), cancellable = true)
	private void startDestroyBlock(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}

	@Inject(method = "continueDestroyBlock", at = @At(value = "HEAD"), cancellable = true)
	private void continueDestroyBlock(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}

	@Inject(method = "destroyBlock", at = @At(value = "HEAD"), cancellable = true)
	private void destroyBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
		if (Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
