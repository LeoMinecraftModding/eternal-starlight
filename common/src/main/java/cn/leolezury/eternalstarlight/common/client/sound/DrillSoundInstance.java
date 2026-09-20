package cn.leolezury.eternalstarlight.common.client.sound;

import cn.leolezury.eternalstarlight.common.entity.living.animal.Coniceras;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class DrillSoundInstance extends AbstractTickableSoundInstance {
	protected final PathfinderMob mob;

	public DrillSoundInstance(SoundEvent soundEvent, PathfinderMob mob, Vec3 pos) {
		super(soundEvent, SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
		this.mob = mob;
		this.x = pos.x;
		this.y = pos.y;
		this.z = pos.z;
		this.looping = true;
		this.delay = 0;
		this.volume = 0.0F;
		this.pitch = 1.0F;
		this.attenuation = Attenuation.LINEAR;
	}

	@Override
	public void tick() {
		if (mob == null || mob.isRemoved()) {
			this.stop();
			return;
		}

		boolean isDrill = mob.getEntityData().get(Coniceras.CHARGING);
		if (isDrill) {
			this.volume = Math.min(1.0F, this.volume + 0.05F);
		} else {
			this.volume = Math.max(0.0F, this.volume - 0.05F);
		}
	}

	@Override
	public boolean canStartSilent() {
		return true;
	}
}
