package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;

@Environment(EnvType.CLIENT)
public class ESExplosionParticle extends SimpleAnimatedParticle {
	protected ESExplosionParticle(ClientLevel level, float lifeMultiplier, double x, double y, double z, int fromColor, int toColor, SpriteSet spriteSet) {
		super(level, x, y, z, spriteSet, 0);
		this.lifetime = (int) ((6 + this.random.nextInt(4)) * lifeMultiplier);
		this.quadSize = 0.8F + this.random.nextFloat() * 0.6F;
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
			return new ESExplosionParticle(level, options.lifeScale(), x, y, z, Color.rgbd(options.fromColor().x / 255f, options.fromColor().y / 255f, options.fromColor().z / 255f).rgb(), Color.rgbd(options.toColor().x / 255f, options.toColor().y / 255f, options.toColor().z / 255f).rgb(), this.sprites);
		}
	}
}
