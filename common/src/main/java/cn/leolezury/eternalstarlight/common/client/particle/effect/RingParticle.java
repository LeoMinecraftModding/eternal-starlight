package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.particle.RingParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Color;
import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.joml.Quaternionf;

public class RingParticle extends SimpleAnimatedParticle {
	private final float scale;
	private final boolean gathering;

	protected RingParticle(ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz, int fromColor, int toColor, float scale, float lifeScale, boolean gathering, SpriteSet spriteSet) {
		super(clientLevel, x, y, z, spriteSet, 0);
		this.xd = dx;
		this.yd = dy;
		this.zd = dz;
		this.quadSize = 1.5f;
		this.lifetime = Math.round(60 * lifeScale);
		this.setColor(fromColor);
		this.setFadeColor(toColor);
		this.scale = scale;
		this.gathering = gathering;
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.CUSTOM;
	}

	@Override
	public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
		if (gathering) {
			alpha = Easing.OUT_CUBIC.interpolate(Math.min((age + partialTicks) / lifetime, 1), 0, 1);
		} else {
			alpha = Easing.OUT_CUBIC.interpolate(Math.min((age + partialTicks) / lifetime, 1), 1, 0);
		}
		Quaternionf rotation = new Quaternionf(camera.rotation());
		VertexConsumer vertexConsumer = ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE_NO_DEPTH);
		this.renderRotatedQuad(vertexConsumer, camera, rotation, partialTicks);
	}

	@Override
	public float getQuadSize(float partialTicks) {
		return this.quadSize * (gathering
			? Easing.IN_OUT_QUAD.interpolate((age + partialTicks) / lifetime, scale, 0)
			: Easing.IN_OUT_QUAD.interpolate((age + partialTicks) / lifetime, 0, scale));
	}

	public static class Provider implements ParticleProvider<RingParticleOptions> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(RingParticleOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
			return new RingParticle(level, x, y, z, dx, dy, dz, Color.rgbd(options.fromColor().x, options.fromColor().y, options.fromColor().z).rgb(), Color.rgbd(options.toColor().x, options.toColor().y, options.toColor().z).rgb(), options.scale(), options.lifeScale(), options.gathering(), this.sprites);
		}
	}
}
