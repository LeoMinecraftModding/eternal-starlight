package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.visual.TrailRenderer;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class ParryParticle extends Particle {
	private static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/parry_trail.png");

	private final TrailEffect effect = new TrailEffect(0.05f, 0.8f);

	protected ParryParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
		super(level, x, y, z);
		this.friction = 0.99f;
		this.gravity = 0.2f;
		this.lifetime = 200;
		this.xd = xSpeed;
		this.yd = ySpeed;
		this.zd = zSpeed;
	}

	@Override
	public void tick() {
		super.tick();
		this.effect.update(new Vec3(xo, yo, zo));
		if (onGround) {
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
		TrailRenderer.render(this.effect, ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(RenderType.entityCutoutNoCull(TRAIL_TEXTURE)), stack, TrailEffect.TrailOffsetFunction.FACE_CAMERA, true, 1, 1, 1, 1, LightTexture.FULL_BRIGHT);
		stack.popPose();
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.CUSTOM;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		public Provider(SpriteSet spriteSet) {
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new ParryParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
		}
	}
}
