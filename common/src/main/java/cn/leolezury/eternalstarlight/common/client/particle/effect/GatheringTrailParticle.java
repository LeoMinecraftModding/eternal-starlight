package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.visual.TrailRenderer;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
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

public class GatheringTrailParticle extends TextureSheetParticle {
	private final TrailEffect effect;
	private final Vec3 destPos;
	private final float pitchSpeed, yawSpeed, radiusSpeed;
	private float pitch, yaw, radius;

	protected GatheringTrailParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, float trailWidth, float trailLength, float speedScale, float rotSpeedScale, SpriteSet spriteSet) {
		super(level, x, y, z);
		this.lifetime = 200;
		this.effect = new TrailEffect(trailWidth, trailLength);
		this.destPos = new Vec3(x + dx, y + dy, z + dz);
		this.pitch = ESMathUtil.positionToPitch(-dx, -dy, -dz);
		this.yaw = ESMathUtil.positionToYaw(-dx, -dz);
		this.radius = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		this.pitchSpeed = (random.nextBoolean() ? 1 : -1) * (2 + random.nextFloat() * 3) * rotSpeedScale;
		this.yawSpeed = (random.nextBoolean() ? 1 : -1) * (2 + random.nextFloat() * 3) * rotSpeedScale;
		this.radiusSpeed = radius / 20 * (0.8f + random.nextFloat() * 0.4f) * speedScale;
		this.pickSprite(spriteSet);
	}

	@Override
	public void tick() {
		this.pitch += pitchSpeed;
		this.yaw += yawSpeed;
		this.radius -= radiusSpeed;
		if (this.radius < 0) {
			this.radius = 0;
		}
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		Vec3 pos = ESMathUtil.rotationToPosition(destPos, radius, pitch, yaw);
		this.x = pos.x();
		this.y = pos.y();
		this.z = pos.z();
		if (this.age > 0) {
			this.effect.update(new Vec3(xo, yo, zo));
		}
		if (this.age++ >= this.lifetime) {
			this.remove();
		}
		if (radius <= 0) {
			effect.setLength(Math.max(effect.getLength() - 0.75f, 0));
			if (effect.getLength() <= 0) {
				remove();
			}
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
		TrailRenderer.render(this.effect, consumer, stack, TrailEffect.TrailOffsetFunction.FACE_CAMERA, true, true, 1, 1, 1, 1, getU0(), getU1(), getV0(), getV1(), LightTexture.FULL_BRIGHT);
		stack.popPose();
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public static class Provider implements ParticleProvider<GatheringTrailParticleOptions> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(GatheringTrailParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new GatheringTrailParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, options.trailWidth(), options.trailLength(), options.speedScale(), options.rotSpeedScale(), sprites);
		}
	}
}
