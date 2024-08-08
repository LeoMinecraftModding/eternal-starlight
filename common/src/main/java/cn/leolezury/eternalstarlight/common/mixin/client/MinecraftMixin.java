package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.MusicManager;
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

import java.util.Comparator;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow
	@Nullable
	public LocalPlayer player;

	@Shadow
	@Nullable
	public ClientLevel level;

	@Shadow
	public abstract MusicManager getMusicManager();

	@Inject(method = "getSituationalMusic", at = @At(value = "RETURN"), cancellable = true)
	private void getSituationalMusic(CallbackInfoReturnable<Music> cir) {
		if (player != null && player.level().dimension() == ESDimensions.STARLIGHT_KEY && (cir.getReturnValue() == Musics.GAME || cir.getReturnValue() == Musics.CREATIVE || cir.getReturnValue() == Musics.UNDER_WATER)) {
			Holder<Biome> biomeHolder = player.level().getBiome(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()));
			if (biomeHolder.isBound()) {
				cir.setReturnValue(biomeHolder.value().getBackgroundMusic().orElse(Musics.GAME));
			}
		}
		if (player != null && player.tickCount % 15 == 0 && level != null) {
			List<ESBoss> bosses = level.getEntitiesOfClass(ESBoss.class, player.getBoundingBox().inflate(50));
			bosses.sort(Comparator.comparingDouble(b -> b.distanceTo(player)));
			for (ESBoss boss : bosses) {
				if (boss.shouldPlayBossMusic()) {
					cir.setReturnValue(boss.getBossMusic());
					return;
				}
			}
			for (Music music : CommonHandlers.POSSIBLE_BOSS_MUSICS) {
				if (getMusicManager().isPlayingMusic(music)) {
					getMusicManager().stopPlaying(music);
				}
			}
		}
	}
}
