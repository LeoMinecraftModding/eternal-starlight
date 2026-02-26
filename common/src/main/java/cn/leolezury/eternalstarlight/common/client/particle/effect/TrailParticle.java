package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.visual.TrailRenderer;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
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

public class TrailParticle extends TextureSheetParticle {
	private final TrailEffect effect = new TrailEffect(0.05f, 0.8f);

	protected TrailParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
		super(level, x, y, z);
		this.friction = 0.99f;
		this.gravity = 0.2f;
		this.lifetime = 200;
		this.xd = xSpeed;
		this.yd = ySpeed;
		this.zd = zSpeed;
		this.pickSprite(spriteSet);
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
		RenderSystem.disableCull();
		TrailRenderer.render(this.effect, consumer, stack, TrailEffect.TrailOffsetFunction.FACE_CAMERA, true, true, 1, 1, 1, 1, getU0(), getU1(), getV0(), getV1(), LightTexture.FULL_BRIGHT);
		stack.popPose();
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new TrailParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
		}
	}
}
