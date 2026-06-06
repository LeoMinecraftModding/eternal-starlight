package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.GeyserBaseParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.joml.Vector3f;

// Backported from 26.1
public class GeyserBaseParticle extends BaseAshSmokeParticle {
	private GeyserBaseParticle(
		final ClientLevel level,
		final double x,
		final double y,
		final double z,
		final double xAux,
		final double yAux,
		final double zAux,
		final Vector3f color,
		final int strength,
		final float burstImpulseBase,
		final SpriteSet sprites
	) {
		super(level, x, y, z, burstImpulseBase + 0.25F * strength, burstImpulseBase + 0.25F * strength, burstImpulseBase + 0.25F * strength, xAux, yAux, zAux, 3.0F + 0.125F * strength, sprites, 0.0F, 0, 0.0F, true);
		this.friction = 0.725F;
		this.setColor(color.x(), color.y(), color.z());
		this.yd = Math.abs(this.yd);
		float lifetimeFactor = 0.8F + 0.2F * level.getRandom().nextFloat();
		this.lifetime = (int) (25.0F * lifetimeFactor);
	}

	public static class Provider implements ParticleProvider<GeyserBaseParticleOptions> {
		private final SpriteSet sprites;

		public Provider(final SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(
			final GeyserBaseParticleOptions options,
			final ClientLevel level,
			final double x,
			final double y,
			final double z,
			final double xAux,
			final double yAux,
			final double zAux
		) {
			double randomX = x + (level.random.nextFloat() - 0.5F) * 0.5F;
			double randomY = y + (level.random.nextFloat() - 0.5F) * 0.5F + 0.2F;
			double randomZ = z + (level.random.nextFloat() - 0.5F) * 0.5F;
			return new GeyserBaseParticle(level, randomX, randomY, randomZ, xAux, yAux, zAux, options.color(), options.strength(), options.burstImpulseBase(), this.sprites);
		}
	}
}
