package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherItem;
import cn.leolezury.eternalstarlight.common.network.SimpleActionPacket;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow
	@Nullable
	public LocalPlayer player;

	@Shadow
	@Nullable
	public ClientLevel level;

	@Inject(method = "getSituationalMusic", at = @At("RETURN"), cancellable = true)
	private void getSituationalMusic(CallbackInfoReturnable<Music> cir) {
		if (player != null && player.level().dimension().location().equals(ESDimensions.STARLIGHT_KEY.location()) && (cir.getReturnValue() == Musics.GAME || cir.getReturnValue() == Musics.CREATIVE || cir.getReturnValue() == Musics.UNDER_WATER)) {
			Holder<Biome> biomeHolder = player.level().getBiome(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()));
			if (biomeHolder.isBound()) {
				cir.setReturnValue(biomeHolder.value().getBackgroundMusic().orElse(Musics.GAME));
			}
		}
	}

	@WrapOperation(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;itemUsed(Lnet/minecraft/world/InteractionHand;)V"))
	private void itemUsed(ItemInHandRenderer instance, InteractionHand hand, Operation<Void> original) {
		if (player == null || !(player.getItemInHand(hand).getItem() instanceof SeedsLauncherItem)) {
			original.call(instance, hand);
		} else {
			ESClientHandler.oldSeedsLauncherAnimTicks = 5;
			ESClientHandler.seedsLauncherAnimTicks = 5;
		}
	}

	@WrapOperation(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;"))
	private HitResult.Type getHitResultType(HitResult instance, Operation<HitResult.Type> original) {
		if (player != null) {
			ESClientPlatform.INSTANCE.sendToServer(new SimpleActionPacket(SimpleActionPacket.C2S_SWING_ATTACK));
			if (player.getMainHandItem().is(ESTags.Items.WHIPS)) {
				return HitResult.Type.MISS;
			}
		}
		return original.call(instance);
	}
}
