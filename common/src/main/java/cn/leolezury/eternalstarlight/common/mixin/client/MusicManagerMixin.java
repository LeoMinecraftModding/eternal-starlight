package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MusicManager.class)
public abstract class MusicManagerMixin {
	@Shadow
	public abstract void stopPlaying();

	@Inject(method = "tick", at = @At(value = "TAIL"))
	private void tick(CallbackInfo ci) {
		if (ClientHandlers.BOSS_MUSIC_INSTANCE != null) {
			stopPlaying();
		}
	}
}
