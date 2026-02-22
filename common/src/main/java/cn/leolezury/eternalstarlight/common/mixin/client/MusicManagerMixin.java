package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public abstract class MusicManagerMixin {
	@Shadow
	public abstract void stopPlaying();

	@Inject(method = "tick", at = @At(value = "TAIL"))
	private void tick(CallbackInfo ci) {
		if (ESClientHandler.bossMusicInstance != null) {
			stopPlaying();
		}
	}
}
