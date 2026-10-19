package cn.leolezury.eternalstarlight.common.client.sound;

import cn.leolezury.eternalstarlight.common.entity.living.animal.Coniceras;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

@Environment(EnvType.CLIENT)
public class DrillSoundInstance extends AbstractTickableSoundInstance {
	private static final float VOLUME_STEP = 0.05F;

	private final Coniceras mob;

	public DrillSoundInstance(Coniceras mob) {
		super(ESSoundEvents.CONICERAS_DRILL.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
		this.mob = mob;
		this.looping = true;
		this.volume = 0.0F;
		this.x = mob.getX();
		this.y = mob.getY();
		this.z = mob.getZ();
	}

	public static void playFor(Coniceras mob) {
		if (!mob.isSilent()) {
			Minecraft.getInstance().getSoundManager().play(new DrillSoundInstance(mob));
		}
	}

	@Override
	public void tick() {
		if (this.mob.isRemoved() || this.mob.level() != Minecraft.getInstance().level) {
			this.stop();
			return;
		}

		if (this.mob.isCharging()) {
			this.x = this.mob.getX();
			this.y = this.mob.getY();
			this.z = this.mob.getZ();
			this.volume = Math.min(1.0F, this.volume + VOLUME_STEP);
		} else {
			this.volume = Math.max(0.0F, this.volume - VOLUME_STEP);
			if (this.volume <= 0.0F) {
				this.stop();
			}
		}
	}

	@Override
	public boolean canStartSilent() {
		return true;
	}
}
