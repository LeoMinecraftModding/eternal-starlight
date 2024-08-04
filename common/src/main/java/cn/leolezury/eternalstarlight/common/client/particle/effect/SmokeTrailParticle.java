package cn.leolezury.eternalstarlight.common.client.particle.effect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class SmokeTrailParticle extends NoRenderParticle {
	protected SmokeTrailParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
		super(clientLevel, d, e, f, g, h, i);
		this.friction = 0.99f;
		this.gravity = 0.3f;
		this.lifetime = 200;
		this.xd *= 3;
		this.yd *= 2.25;
		this.zd *= 3;
	}

	@Override
	public void tick() {
		super.tick();
		for (int i = 0; i < 2; i++) {
			level.addParticle(ParticleTypes.SMOKE, true, x + (random.nextFloat() - 0.5) * 0.15, y + (random.nextFloat() - 0.5) * 0.15, z + (random.nextFloat() - 0.5) * 0.15, 0, 0, 0);
			level.addParticle(ParticleTypes.LARGE_SMOKE, true, x + (random.nextFloat() - 0.5) * 0.15, y + (random.nextFloat() - 0.5) * 0.15, z + (random.nextFloat() - 0.5) * 0.15, 0, 0, 0);
		}
		if (this.onGround) {
			remove();
		}
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		public Provider(SpriteSet spriteSet) {
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new SmokeTrailParticle(clientLevel, d, e, f, g, h, i);
		}
	}
}
