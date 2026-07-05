package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.visual.TrailRenderer;
import cn.leolezury.eternalstarlight.common.particle.OrbitalTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class OrbitalTrailParticle extends TextureSheetParticle {
	private final TrailEffect effect;
	private final Vec3 center, axis;
	private final SmoothSegmentedValue radius, speed, length;
	private final float red, green, blue;
	private final boolean reverseSpeed;
	private float angle;

	protected OrbitalTrailParticle(ClientLevel level, double x, double y, double z, Vector3f axis, SmoothSegmentedValue radius, SmoothSegmentedValue speed, float width, SmoothSegmentedValue length, Vector3f color, int lifetime, SpriteSet spriteSet) {
		super(level, x, y, z);
		this.effect = new TrailEffect(width, length.calculate(0));
		this.center = new Vec3(x, y, z);
		this.axis = new Vec3(axis);
		this.radius = radius;
		this.speed = speed;
		this.angle = random.nextFloat() * 360;
		this.reverseSpeed = random.nextBoolean();
		this.length = length;
		this.red = color.x();
		this.green = color.y();
		this.blue = color.z();
		this.lifetime = lifetime;
		this.hasPhysics = false;
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public void tick() {
		float progress = (float) age / lifetime;
		this.angle += (reverseSpeed ? -1 : 1) * speed.calculate(progress);
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		Vec3 pos = ESMathUtil.rotateAroundAxis(center, axis, angle, radius.calculate(progress));
		this.x = pos.x();
		this.y = pos.y();
		this.z = pos.z();
		if (this.age > 0) {
			this.effect.update(new Vec3(xo, yo, zo));
			this.effect.setLength(length.calculate(progress));
		}
		if (this.age++ >= this.lifetime) {
			this.remove();
		}
	}

	@Override
	public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
		PoseStack stack = new PoseStack();
		float x = (float) Mth.lerp(partialTicks, this.xo, this.x);
		float y = (float) Mth.lerp(partialTicks, this.yo, this.y);
		float z = (float) Mth.lerp(partialTicks, this.zo, this.z);
		stack.pushPose();
		stack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
		this.effect.prepareRender(new Vec3(x, y, z), partialTicks);
		RenderSystem.disableCull();
		TrailRenderer.render(this.effect, consumer, stack, TrailEffect.TrailOffsetFunction.FACE_CAMERA, true, true, red, green, blue, 1, getU0(), getU1(), getV0(), getV1(), LightTexture.FULL_BRIGHT);
		stack.popPose();
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public static class Provider implements ParticleProvider<OrbitalTrailParticleOptions> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(OrbitalTrailParticleOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
			return new OrbitalTrailParticle(level, x, y, z, options.axis(), options.radius(), options.speed(), options.width(), options.length(), options.color(), options.lifetime(), sprites);
		}
	}
}
