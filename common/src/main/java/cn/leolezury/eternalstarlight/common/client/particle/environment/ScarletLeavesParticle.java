package cn.leolezury.eternalstarlight.common.client.particle.environment;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class ScarletLeavesParticle extends TextureSheetParticle {
	private float rotSpeed;
	private final float particleRandom;
	private final float spinAcceleration;

	public ScarletLeavesParticle(ClientLevel clientLevel, double x, double y, double z, SpriteSet spriteSet) {
		super(clientLevel, x, y, z);
		this.setSprite(spriteSet.get(this.random.nextInt(12), 12));
		this.rotSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
		this.particleRandom = this.random.nextFloat();
		this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
		this.lifetime = 300;
		this.gravity = 7.5E-4F;
		float size = 0.05F + 0.025F * this.random.nextFloat();
		this.quadSize = size;
		this.setSize(size, size);
		this.friction = 1.0F;
	}

	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.lifetime-- <= 0) {
			this.remove();
		}

		if (!this.removed) {
			float f = (float) (300 - this.lifetime);
			float g = Math.min(f / 300.0F, 1.0F);
			double d = Math.cos(Math.toRadians(this.particleRandom * 60.0F)) * 2.0 * Math.pow(g, 1.25);
			double e = Math.sin(Math.toRadians(this.particleRandom * 60.0F)) * 2.0 * Math.pow(g, 1.25);
			this.xd += d * 0.0024999999441206455;
			this.zd += e * 0.0024999999441206455;
			this.yd -= this.gravity;
			this.rotSpeed += this.spinAcceleration / 20.0F;
			this.oRoll = this.roll;
			this.roll += this.rotSpeed / 20.0F;
			this.move(this.xd, this.yd, this.zd);
			if (this.onGround || this.lifetime < 299 && (this.xd == 0.0 || this.zd == 0.0)) {
				this.remove();
			}

			if (!this.removed) {
				this.xd *= this.friction;
				this.yd *= this.friction;
				this.zd *= this.friction;
			}
		}
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xs, double ys, double zs) {
			return new ScarletLeavesParticle(clientLevel, x, y, z, this.sprites);
		}
	}
}
