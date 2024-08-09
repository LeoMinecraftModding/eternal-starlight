package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow
	@Nullable
	public LocalPlayer player;

	@Shadow
	@Nullable
	public ClientLevel level;

	@Inject(method = "getSituationalMusic", at = @At(value = "RETURN"), cancellable = true)
	private void getSituationalMusic(CallbackInfoReturnable<Music> cir) {
		if (player != null && player.level().dimension().location().equals(ESDimensions.STARLIGHT_KEY.location()) && (cir.getReturnValue() == Musics.GAME || cir.getReturnValue() == Musics.CREATIVE || cir.getReturnValue() == Musics.UNDER_WATER)) {
			Holder<Biome> biomeHolder = player.level().getBiome(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()));
			if (biomeHolder.isBound()) {
				cir.setReturnValue(biomeHolder.value().getBackgroundMusic().orElse(Musics.GAME));
			}
		}
	}
}
