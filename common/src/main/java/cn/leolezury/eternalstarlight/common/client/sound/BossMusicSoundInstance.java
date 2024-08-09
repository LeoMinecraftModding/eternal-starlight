package cn.leolezury.eternalstarlight.common.client.sound;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
		this.x = (float) this.boss.getX();
		this.y = (float) this.boss.getY();
		this.z = (float) this.boss.getZ();
		this.looping = true;
		this.delay = 0;
		this.volume = 1f;
	}

	public boolean sameBoss(ESBoss boss1) {
		return boss1.getId() == boss.getId();
	}

	@Override
	public void tick() {
		if (!this.boss.isAlive()) {
			this.stop();
			if (canPlaySound()) {
				ClientHandlers.BOSS_MUSIC_INSTANCE = null;
			}
		}
	}

	@Override
	public boolean canPlaySound() {
		return ClientHandlers.BOSS_MUSIC_INSTANCE == this;
	}
}