package cn.leolezury.eternalstarlight.common.client.particle.effect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class BigExplosionParticle extends TextureSheetParticle {
	private final SpriteSet sprites;

	protected BigExplosionParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
		super(level, x, y, z);
		this.gravity = -0.1F;
		this.friction = 0.9F;
		this.sprites = sprites;
		this.xd = xSpeed + (Math.random() * 2.0 - 1.0) * 0.05F;
		this.yd = ySpeed + (Math.random() * 2.0 - 1.0) * 0.05F;
		this.zd = zSpeed + (Math.random() * 2.0 - 1.0) * 0.05F;
		float f = this.random.nextFloat() * 0.3F + 0.7F;
		this.rCol = f;
		this.gCol = f;
		this.bCol = f;
		this.quadSize = 0.7F * (this.random.nextFloat() + 2.0F);
		this.lifetime = (int) (5.0 / (this.random.nextFloat() * 0.8 + 0.2)) + 2;
		this.setSpriteFromAge(sprites);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public int getLightColor(float f) {
		return LightTexture.FULL_BRIGHT;
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.sprites);
	}

	@Environment(EnvType.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new BigExplosionParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
		}
	}
}