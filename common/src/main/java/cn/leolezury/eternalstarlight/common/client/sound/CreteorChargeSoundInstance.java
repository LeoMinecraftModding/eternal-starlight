package cn.leolezury.eternalstarlight.common.client.sound;

import cn.leolezury.eternalstarlight.common.entity.living.monster.Creteor;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;

public class CreteorChargeSoundInstance extends AbstractTickableSoundInstance {
	private final Creteor mob;

	public CreteorChargeSoundInstance(Creteor mob) {
		super(ESSoundEvents.CRETEOR_CHARGE.get(), mob.getSoundSource(), SoundInstance.createUnseededRandom());
		this.mob = mob;
		this.attenuation = SoundInstance.Attenuation.LINEAR;
		this.x = mob.getX();
		this.y = mob.getY();
		this.z = mob.getZ();
	}

	public static void playFor(Creteor mob) {
		Minecraft.getInstance().getSoundManager().play(new CreteorChargeSoundInstance(mob));
	}

	@Override
	public boolean canPlaySound() {
		return !this.mob.isSilent();
	}

	@Override
	public void tick() {
		if (this.mob.isRemoved() || this.mob.isDeadOrDying() || this.mob.level() != Minecraft.getInstance().level) {
			this.stop();
			return;
		}
		this.x = this.mob.getX();
		this.y = this.mob.getY();
		this.z = this.mob.getZ();
	}
}
