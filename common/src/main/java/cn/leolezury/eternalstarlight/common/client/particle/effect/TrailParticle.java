package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.trail.Trail;
import cn.leolezury.eternalstarlight.common.client.trail.TrailPoint;
import cn.leolezury.eternalstarlight.common.client.trail.TrailRenderer;
import cn.leolezury.eternalstarlight.common.client.trail.TrailStyles;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

import java.util.function.Function;

public class TrailParticle extends TextureSheetParticle {
	private final Trail trail = new Trail(0.05f, 0.8f);

	protected TrailParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet, Function<Float, Vector4f> colorProfile) {
		super(level, x, y, z);
		this.friction = 0.99f;
		this.gravity = 0.2f;
		this.lifetime = 200;
		this.xd = xSpeed;
		this.yd = ySpeed;
		this.zd = zSpeed;
		this.pickSprite(spriteSet);
		this.trail.setColorProfile(colorProfile);
		this.trail.setWidthProfile(TrailStyles.TAPER);
	}

	@Override
	public void tick() {
		super.tick();
		this.trail.update(new Vec3(xo, yo, zo));
		if (onGround || stoppedByCollision) {
			trail.setLength(Math.max(trail.getLength() - 0.75f, 0));
			if (trail.getLength() <= 0) {
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
		this.trail.setSpriteUv(this.sprite);
		this.trail.prepareRender(TrailPoint.cameraFacing(new Vec3(x, y, z)), partialTicks);
		RenderSystem.disableCull();
		TrailRenderer.render(this.trail, consumer, stack, true, true, LightTexture.FULL_BRIGHT);
		stack.popPose();
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;
		private final Function<Float, Vector4f> colorProfile;

		public Provider(SpriteSet spriteSet, Function<Float, Vector4f> colorProfile) {
			this.sprites = spriteSet;
			this.colorProfile = colorProfile;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new TrailParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites, colorProfile);
		}
	}
}
