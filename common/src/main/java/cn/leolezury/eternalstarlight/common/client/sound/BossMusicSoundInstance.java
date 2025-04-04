package cn.leolezury.eternalstarlight.common.client.sound;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

@Environment(EnvType.CLIENT)
public class BossMusicSoundInstance extends AbstractTickableSoundInstance {
	protected final ESBoss boss;

	public BossMusicSoundInstance(SoundEvent soundEvent, ESBoss boss) {
		super(soundEvent, SoundSource.MUSIC, SoundInstance.createUnseededRandom());
		this.boss = boss;
		this.looping = true;
		this.delay = 0;
		this.volume = 1f;
	}

	public ESBoss getBoss() {
		return boss;
	}

	@Override
	public void tick() {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null) {
			this.x = player.getX();
			this.y = player.getY();
			this.z = player.getZ();
		}
		if (!this.boss.isAlive()) {
			this.stop();
			if (canPlaySound()) {
				ClientHandlers.bossMusicInstance = null;
			}
		}
	}

	@Override
	public boolean canPlaySound() {
		return ClientHandlers.bossMusicInstance == this;
	}
}