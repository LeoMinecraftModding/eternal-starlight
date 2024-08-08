package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(MusicManager.class)
public abstract class MusicManagerMixin {
	@Shadow
	public abstract void stopPlaying(Music music);

	@Inject(method = "tick", at = @At(value = "TAIL"))
	private void tick(CallbackInfo ci) {
		LocalPlayer player = Minecraft.getInstance().player;
		ClientLevel level = Minecraft.getInstance().level;
		if (player != null && player.tickCount % 15 == 0 && level != null) {
			List<ESBoss> bosses = level.getEntitiesOfClass(ESBoss.class, player.getBoundingBox().inflate(50));
			bosses.sort(Comparator.comparingDouble(b -> b.distanceTo(player)));
			List<Music> possibleMusics = new ArrayList<>();
			for (ESBoss boss : bosses) {
				if (boss.shouldPlayBossMusic()) {
					possibleMusics.add(boss.getBossMusic());
				}
			}
			for (Music music : CommonHandlers.POSSIBLE_BOSS_MUSICS) {
				if (possibleMusics.stream().noneMatch(m -> m.getEvent().value().getLocation().equals(music.getEvent().value().getLocation()))) {
					stopPlaying(music);
				}
			}
		}
	}
}
