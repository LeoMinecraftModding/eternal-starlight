package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.GeyserBaseParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.GeyserParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.Nullable;

public class GeyserEruptionParticle extends NoRenderParticle {
	private final int strength;
	private final double xa;
	private final double ya;
	private final double za;
	private final GeyserParticleOptions plumeParticle;
	private final GeyserBaseParticleOptions baseParticle;
	private final GeyserBaseParticleOptions poofParticle;

	protected GeyserEruptionParticle(
		final ClientLevel level,
		final double x,
		final double y,
		final double z,
		final double xAux,
		final double yAux,
		final double zAux,
		final GeyserParticleOptions options
	) {
		super(level, x, y, z);
		this.xa = xAux;
		this.ya = yAux;
		this.za = zAux;
		this.strength = options.strength();
		this.lifetime = 20;
		this.plumeParticle = new GeyserParticleOptions(ESParticles.GEYSER_PLUME.get(), options.color(), this.strength);
		this.baseParticle = new GeyserBaseParticleOptions(ESParticles.GEYSER_BASE.get(), options.color(), this.strength, 1.5F);
		this.poofParticle = new GeyserBaseParticleOptions(ESParticles.GEYSER_POOF.get(), options.color(), this.strength, 2.0F);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age % 2 == 0) {
			for (int i = 0; i < 2; i++) {
				this.level.addParticle(this.baseParticle, this.x, this.y, this.z, this.xa, this.ya, this.za);
			}
		}

		for (int i = 0; i < this.strength + 2; i++) {
			this.level.addParticle(this.plumeParticle, this.x, this.y, this.z, this.xa, this.ya, this.za);
		}

		if (this.age % 10 == 0) {
			for (int i = 0; i < 20; i++) {
				this.level.addParticle(this.poofParticle, this.x, this.y, this.z, this.xa, this.ya, this.za);
			}
		}
	}

	public static class Provider implements ParticleProvider<GeyserParticleOptions> {
		public Provider(final SpriteSet sprites) {
		}

		@Nullable
		@Override
		public Particle createParticle(
			final GeyserParticleOptions options,
			final ClientLevel level,
			final double x,
			final double y,
			final double z,
			final double xAux,
			final double yAux,
			final double zAux
		) {
			return new GeyserEruptionParticle(level, x, y, z, xAux, yAux, zAux, options);
		}
	}
}
