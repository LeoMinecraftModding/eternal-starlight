package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.util.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class AethersentSmokeParticle extends TextureSheetParticle {
	private final Color fromColor = Color.rgbi(163, 76, 186).blend(Color.WHITE, 0.4);
	private final Color fadeColor = Color.rgbi(69, 18, 82);
	private final double initialX, initialY, initialZ;
	private final double targetX, targetY, targetZ;

	protected AethersentSmokeParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, SpriteSet spriteSet) {
		super(clientLevel, d, e, f, g, h, i);
		this.lifetime = 100;
		this.initialX = d;
		this.initialY = e;
		this.initialZ = f;
		this.targetX = d + g;
		this.targetY = e + h;
		this.targetZ = f + i;
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		} else {
			if (age > 10) {
				setPos(targetX, targetY, targetZ);
			} else {
				double progress = age / 10d;
				progress = Math.pow(progress, 1.5);
				setPos(Mth.lerp(progress, initialX, targetX), Mth.lerp(progress, initialY, targetY), Mth.lerp(progress, initialZ, targetZ));
			}
		}

		Color currentColor;
		if (age < 30) {
			currentColor = fromColor;
		} else if (age < 60) {
			double progress = (age - 30d) / 30d;
			currentColor = fromColor.blend(fadeColor, Math.pow(progress, 1.5));
		} else {
			currentColor = fadeColor;
		}
		rCol = currentColor.rf();
		gCol = currentColor.gf();
		bCol = currentColor.bf();
	}

	@Override
	public float getQuadSize(float f) {
		return (age > 60 ? (float) (Math.pow((lifetime - age) / 40d, 1.5) * this.quadSize) : this.quadSize) * 2.5f;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_LIT;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz) {
			return new AethersentSmokeParticle(clientLevel, x, y, z, dx, dy, dz, this.sprites);
		}
	}
}
