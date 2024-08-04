package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Color;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;

@Environment(EnvType.CLIENT)
public class ESSmokeParticle extends SimpleAnimatedParticle {
	private final float rotSpeed;

	protected ESSmokeParticle(ClientLevel level, float lifeMultiplier, double x, double y, double z, double dx, double dy, double dz, boolean rise, double movementMultiplier, int fromColor, int toColor, float alpha, SpriteSet spriteSet, float gravity) {
		super(level, x, y, z, spriteSet, gravity);
		this.xd = dx + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
		this.yd = dy + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
		this.zd = dz + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
		double j = (Math.random() + Math.random() + 1.0) * 0.15000000596046448 * movementMultiplier;
		double k = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
		this.xd = this.xd / k * j * 0.4000000059604645;
		this.yd = this.yd / k * j * 0.4000000059604645 + (rise ? 0.10000000149011612 : 0);
		this.zd = this.zd / k * j * 0.4000000059604645;
		this.rotSpeed = (float) (Math.random() * 0.05f + 0.075f);
		this.lifetime = (int) (((int) (Math.random() * 20.0D) + 40) * lifeMultiplier);
		this.quadSize *= 5;
		this.alpha = alpha;
		this.friction = 1f;
		this.setColor(fromColor);
		this.setFadeColor(toColor);
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public void tick() {
		super.tick();
		oRoll = roll;
		roll += rotSpeed;
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
		super.render(ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE), camera, f);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.CUSTOM;
	}

	public static class Provider implements ParticleProvider<ESSmokeParticleOptions> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(ESSmokeParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new ESSmokeParticle(level, options.lifeScale(), x, y, z, xSpeed, ySpeed, zSpeed, options.rise(), options.motionScale(), Color.rgbd(options.fromColor().x / 255f, options.fromColor().y / 255f, options.fromColor().z / 255f).rgb(), Color.rgbd(options.toColor().x / 255f, options.toColor().y / 255f, options.toColor().z / 255f).rgb(), options.alpha(), this.sprites, 0);
		}
	}
}
