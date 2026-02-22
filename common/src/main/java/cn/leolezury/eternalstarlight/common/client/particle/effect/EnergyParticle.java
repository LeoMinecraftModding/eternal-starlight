package cn.leolezury.eternalstarlight.common.client.particle.effect;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;

public class EnergyParticle extends RisingParticle {
	protected EnergyParticle(ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz) {
		super(clientLevel, x, y, z, dx, dy, dz);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void move(double x, double y, double z) {
		this.setBoundingBox(this.getBoundingBox().move(x, y, z));
		this.setLocationFromBoundingbox();
	}

	@Override
	public float getQuadSize(float f) {
		float progress = (this.age + f) / this.lifetime;
		return this.quadSize * (1.0F - progress * progress * 0.5F);
	}

	@Override
	public int getLightColor(float f) {
		return LightTexture.FULL_BRIGHT;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;

		public Provider(SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
			EnergyParticle particle = new EnergyParticle(level, x, y, z, dx, dy, dz);
			particle.pickSprite(this.sprite);
			return particle;
		}
	}
}
