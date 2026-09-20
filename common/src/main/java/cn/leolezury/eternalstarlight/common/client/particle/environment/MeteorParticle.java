package cn.leolezury.eternalstarlight.common.client.particle.environment;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.trail.Trail;
import cn.leolezury.eternalstarlight.common.client.trail.TrailPoint;
import cn.leolezury.eternalstarlight.common.client.trail.TrailRenderer;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class MeteorParticle extends TextureSheetParticle {
	private final Trail trail = new Trail(0.8f, 15);

	protected MeteorParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
		super(level, x, y, z);
		this.xd = 0;
		this.yd = -2;
		this.zd = 0;
		this.lifetime = 400;
		this.pickSprite(spriteSet);
	}

	@Override
	public void tick() {
		super.tick();
		level.addParticle(ESExplosionParticleOptions.AETHERSENT, true, x, y, z, 0, 0, 0);
		this.trail.update(TrailPoint.cameraFacing(new Vec3(xo, yo, zo)).color(144 / 255f, 94 / 255f, 168 / 255f, 2f));
		if (onGround || stoppedByCollision) {
			trail.setLength(Math.max(trail.getLength() - 0.75f, 0));
			if (trail.getLength() <= 0) {
				remove();
			}
		}
		this.xd = 0;
		this.yd = -2;
		this.zd = 0;
	}

	@Override
	public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
		PoseStack stack = new PoseStack();
		float x = (float) Mth.lerp(partialTicks, this.xo, this.x);
		float y = (float) Mth.lerp(partialTicks, this.yo, this.y);
		float z = (float) Mth.lerp(partialTicks, this.zo, this.z);
		stack.pushPose();
		stack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
		this.trail.setUv(getU0(), getU1(), getV0(), getV1());
		this.trail.prepareRender(TrailPoint.cameraFacing(new Vec3(x, y, z)), partialTicks);
		TrailRenderer.render(this.trail, ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE_ADDITIVE_GLOW), stack, true, false, LightTexture.FULL_BRIGHT);
		stack.popPose();
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.CUSTOM;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new MeteorParticle(level, x, y, z, sprites);
		}
	}
}
