package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Color;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;

public class ESExplosionParticle extends SimpleAnimatedParticle {
	protected ESExplosionParticle(ClientLevel level, float lifeMultiplier, double x, double y, double z, int fromColor, int toColor, SpriteSet spriteSet) {
		super(level, x, y, z, spriteSet, 0);
		this.lifetime = (int) ((6 + this.random.nextInt(4)) * lifeMultiplier);
		this.quadSize = 0.6F + this.random.nextFloat() * 0.4F;
		this.setColor(fromColor);
		this.setFadeColor(toColor);
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public int getLightColor(float f) {
		return LightTexture.FULL_BRIGHT;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_LIT;
	}

	public static class Provider implements ParticleProvider<ESExplosionParticleOptions> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(ESExplosionParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new ESExplosionParticle(level, options.lifeScale(), x, y, z, Color.rgbd(options.fromColor().x, options.fromColor().y, options.fromColor().z).rgb(), Color.rgbd(options.toColor().x, options.toColor().y, options.toColor().z).rgb(), this.sprites);
		}
	}
}
