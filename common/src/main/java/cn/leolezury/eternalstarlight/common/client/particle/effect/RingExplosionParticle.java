package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Color;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class RingExplosionParticle extends SimpleAnimatedParticle {
	private final float scale;

	protected RingExplosionParticle(ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz, int fromColor, int toColor, float scale, SpriteSet spriteSet) {
		super(clientLevel, x, y, z, spriteSet, 0);
		this.xd = dx;
		this.yd = dy;
		this.zd = dz;
		this.quadSize = 1.5f;
		this.lifetime = 60;
		this.setColor(fromColor);
		this.setFadeColor(toColor);
		this.scale = scale;
		this.setSpriteFromAge(spriteSet);
	}

	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	public void tick() {
		super.tick();
		alpha = ESMathUtil.easeOutQuintInterpolation((float) age / lifetime, 1, 0.1f);
	}

	@Override
	public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
		Quaternionf quaternionf = new Quaternionf();
		quaternionf.rotateX(Mth.PI / 2);
		VertexConsumer vertexConsumer = ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.particle());
		this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
		quaternionf = new Quaternionf();
		quaternionf.rotateY(-3.1415927F).rotateX(-Mth.PI / 2);
		this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
	}

	public float getQuadSize(float partialTicks) {
		return this.quadSize * ESMathUtil.easeOutQuintInterpolation((age + partialTicks) / lifetime, 1, scale);
	}

	public static class Provider implements ParticleProvider<RingExplosionParticleOptions> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		public Particle createParticle(RingExplosionParticleOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
			return new RingExplosionParticle(level, x, y, z, dx, dy, dz, Color.rgbd(options.fromColor().x / 255f, options.fromColor().y / 255f, options.fromColor().z / 255f).rgb(), Color.rgbd(options.toColor().x / 255f, options.toColor().y / 255f, options.toColor().z / 255f).rgb(), options.scale(), this.sprites);
		}
	}
}
