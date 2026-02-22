package cn.leolezury.eternalstarlight.common.client.particle.effect;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class PungencyFruitSmokeParticle extends BaseAshSmokeParticle {
	protected PungencyFruitSmokeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float quadSizeMultiplier, SpriteSet sprites) {
		super(level, x, y, z, 0.1F, 0.1F, 0.1F, xSpeed, ySpeed, zSpeed, quadSizeMultiplier, sprites, 0.3F, 8, -0.1F, true);
		this.rCol = 179 / 255f;
		this.gCol = 116 / 255f;
		this.bCol = 116 / 255f;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new PungencyFruitSmokeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 1.0F, this.sprites);
		}
	}
}
