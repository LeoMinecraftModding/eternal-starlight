package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.util.Easing;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;

public class StarfireExplosionParticle extends SimpleAnimatedParticle {
	protected StarfireExplosionParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, float scale, SpriteSet spriteSet) {
		super(level, x, y, z, spriteSet, 0.25f);
		this.setParticleSpeed(dx, dy, dz);
		double rand = (Math.random() + Math.random() + 1) * 0.15;
		this.xd = this.xd * rand * 4;
		this.yd = this.yd * rand * 4 + 0.1;
		this.zd = this.zd * rand * 4;
		this.friction = 0.99f;
		this.quadSize *= scale;
		this.lifetime *= 2;
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public float getQuadSize(float f) {
		return Easing.IN_CUBIC.interpolate((age + f) / lifetime, quadSize, 0);
	}

	@Override
	public int getLightColor(float f) {
		return LightTexture.FULL_BRIGHT;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new StarfireExplosionParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 0.25f, sprites);
		}
	}

	public static class SmallProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public SmallProvider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new StarfireExplosionParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 0.125f, sprites);
		}
	}
}
