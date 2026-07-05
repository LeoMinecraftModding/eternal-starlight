package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RippleParticle extends TextureSheetParticle {
	private final SmoothSegmentedValue radius;
	private final SmoothSegmentedValue width;
	private final float red, green, blue;

	protected RippleParticle(ClientLevel level, double x, double y, double z, SmoothSegmentedValue radius, SmoothSegmentedValue width, Vector3f color, int lifetime, SpriteSet spriteSet) {
		super(level, x, y, z);
		this.radius = radius;
		this.width = width;
		this.red = color.x();
		this.green = color.y();
		this.blue = color.z();
		this.lifetime = lifetime;
		this.quadSize = 0;
		this.hasPhysics = false;
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	protected int getLightColor(float f) {
		return LightTexture.FULL_BRIGHT;
	}

	@Override
	public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
		float progress = Mth.clamp((age + partialTicks) / lifetime, 0, 1);
		float currentRadius = radius.calculate(progress);
		float currentWidth = width.calculate(progress);
		if (currentRadius <= 0 || currentWidth <= 0) return;
		float innerRadius = Math.max(0, currentRadius - currentWidth);

		float cx = (float) (Mth.lerp(partialTicks, xo, x) - camera.getPosition().x);
		float cy = (float) (Mth.lerp(partialTicks, yo, y) - camera.getPosition().y);
		float cz = (float) (Mth.lerp(partialTicks, zo, z) - camera.getPosition().z);

		Quaternionf quaternion = new Quaternionf(camera.rotation());

		int segments = Math.max(24, (int) (currentRadius * 48));
		float angleStep = Mth.TWO_PI / segments;
		int packedLight = this.getLightColor(partialTicks);
		float u0 = this.getU0(), u1 = this.getU1(), v0 = this.getV0(), v1 = this.getV1();

		for (int i = 0; i < segments; i++) {
			float a1 = i * angleStep;
			float a2 = (i + 1) * angleStep;

			float cos1 = Mth.cos(a1), sin1 = Mth.sin(a1);
			float cos2 = Mth.cos(a2), sin2 = Mth.sin(a2);

			Vector3f inner1 = new Vector3f(cos1 * innerRadius, sin1 * innerRadius, 0).rotate(quaternion).add(cx, cy, cz);
			Vector3f outer1 = new Vector3f(cos1 * currentRadius, sin1 * currentRadius, 0).rotate(quaternion).add(cx, cy, cz);
			Vector3f outer2 = new Vector3f(cos2 * currentRadius, sin2 * currentRadius, 0).rotate(quaternion).add(cx, cy, cz);
			Vector3f inner2 = new Vector3f(cos2 * innerRadius, sin2 * innerRadius, 0).rotate(quaternion).add(cx, cy, cz);

			consumer.addVertex(inner1).setColor(red, green, blue, 1).setUv(u0, v0).setLight(packedLight);
			consumer.addVertex(outer1).setColor(red, green, blue, 1).setUv(u0, v1).setLight(packedLight);
			consumer.addVertex(outer2).setColor(red, green, blue, 1).setUv(u1, v1).setLight(packedLight);
			consumer.addVertex(inner2).setColor(red, green, blue, 1).setUv(u1, v0).setLight(packedLight);
		}
	}

	public static class Provider implements ParticleProvider<RippleParticleOptions> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(RippleParticleOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
			return new RippleParticle(level, x, y, z, options.radius(), options.width(), options.color(), options.lifetime(), sprites);
		}
	}
}
