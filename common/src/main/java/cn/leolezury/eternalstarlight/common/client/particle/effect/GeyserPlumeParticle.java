package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.GeyserParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.jetbrains.annotations.Nullable;

public class GeyserPlumeParticle extends TextureSheetParticle {
	private final SpriteSet sprites;
	private final double startY;
	private final double maxY;
	private final float initialPropulsion;
	private final float horizontalSprayX;
	private final float horizontalSprayZ;
	private final float minSize;
	private final float maxSize;
	private boolean done;

	private GeyserPlumeParticle(
		final ClientLevel level,
		final double x,
		final double y,
		final double z,
		final double xa,
		final double ya,
		final double za,
		final GeyserParticleOptions options,
		final SpriteSet sprites
	) {
		super(level, x, y, z, xa, ya, za);
		int plumeHeight = 5 * Math.max(1, options.strength());
		this.hasPhysics = true;
		this.speedUpWhenYMotionIsBlocked = true;
		this.lifetime = plumeHeight * 5;
		this.yd = 0.0;
		this.startY = y;
		this.maxY = this.startY + plumeHeight - 1.0;
		this.horizontalSprayX = (level.getRandom().nextFloat() - 0.5F) * 0.2F;
		this.horizontalSprayZ = (level.getRandom().nextFloat() - 0.5F) * 0.2F;
		this.friction = 1.0F;
		this.initialPropulsion = (options.strength() == 1 ? 1.5F : 1.0F) * plumeHeight * 1.45F;
		this.gravity = -this.initialPropulsion;
		float initiallyRandomizedSize = this.quadSize * 0.75F;
		this.minSize = initiallyRandomizedSize * (2.0F + plumeHeight / 8.0F);
		this.maxSize = initiallyRandomizedSize * (3.0F + plumeHeight / 8.0F);
		this.quadSize = this.minSize;
		this.setColor(options.color().x(), options.color().y(), options.color().z());
		this.sprites = sprites;
		this.setSpriteFromAge(sprites);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.done && (this.yd < 0.0 || this.y > this.maxY || this.y == this.yo)) {
			this.lifetime = Math.min(this.lifetime, this.age + 5);
			this.friction = 0.0F;
			this.done = true;
		}

		double yProgressLinear = Math.clamp((this.y - this.startY) / (this.maxY - this.startY), 0.0, 1.0);
		double yProgressExponential = Math.pow(yProgressLinear, 3.0);
		this.gravity = this.initialPropulsion * (float) yProgressExponential * 0.12F;
		this.xd = yProgressLinear * this.horizontalSprayX;
		this.zd = yProgressLinear * this.horizontalSprayZ;
		this.setSpriteFromAge(this.sprites);
		this.quadSize = this.minSize + (float) (yProgressLinear * (this.maxSize - this.minSize));
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public static class Provider implements ParticleProvider<GeyserParticleOptions> {
		private final SpriteSet sprites;

		public Provider(final SpriteSet sprites) {
			this.sprites = sprites;
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
			double randomX = x + (level.random.nextFloat() - 0.5F) * 0.2F;
			double randomY = y + level.random.nextFloat();
			double randomZ = z + (level.random.nextFloat() - 0.5F) * 0.2F;
			return new GeyserPlumeParticle(level, randomX, randomY, randomZ, xAux, yAux, zAux, options, this.sprites);
		}
	}
}
