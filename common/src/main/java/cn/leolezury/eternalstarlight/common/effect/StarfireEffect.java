package cn.leolezury.eternalstarlight.common.effect;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;

public class StarfireEffect extends MobEffect {
	public StarfireEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public ParticleOptions createParticleOptions(MobEffectInstance instance) {
		return ESParticles.STARFIRE.get();
	}
}
