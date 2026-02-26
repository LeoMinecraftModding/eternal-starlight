package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class LunarSlashParticle extends TextureSheetParticle {
	private static final int DIVISION = 72;
	private static final float CELL = (float) 180 / DIVISION;

	private final float yRot, zRot;

	protected LunarSlashParticle(ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz, SpriteSet spriteSet) {
		super(clientLevel, x, y, z);
		this.stoppedByCollision = true;
		this.lifetime = (int) (this.random.nextFloat() * 5 + 10);
		this.yRot = ESMathUtil.positionToYaw(new Vec3(dx, dy, dz)) * Mth.DEG_TO_RAD;
		this.zRot = Mth.TWO_PI * this.random.nextFloat();
		this.pickSprite(spriteSet);
	}

	@Override
	public void move(double d, double e, double f) {
		super.move(d, e, f);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void render(VertexConsumer consumer, Camera camera, float partialTick) {
		float progress = Math.min(age + partialTick, lifetime) / lifetime;
		double currentX = Mth.lerp(partialTick, this.xo, this.x);
		double currentY = Mth.lerp(partialTick, this.yo, this.y);
		double currentZ = Mth.lerp(partialTick, this.zo, this.z);
		Vec3 camPos = camera.getPosition();
		PoseStack stack = new PoseStack();
		stack.pushPose();
		stack.translate(currentX - camPos.x, currentY - camPos.y, currentZ - camPos.z);
		stack.mulPose(new Quaternionf().rotationX(90 * Mth.DEG_TO_RAD));
		stack.mulPose(new Quaternionf().rotationZ(yRot));
		stack.mulPose(new Quaternionf().rotationX(zRot));
		VertexConsumer vertexConsumer = ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE_ADDITIVE_GLOW);
		float lastYawOffset = 0;
		Vec3 lastInnerPos = Vec3.ZERO;
		Vec3 lastOuterPos = Vec3.ZERO;
		for (int i = 0; i < DIVISION / 2; i++) {
			float yawOffset = progress * 270 - i * CELL;
			Vec3 innerPos = ESMathUtil.rotationToPosition(1, 0, -90 + yawOffset);
			Vec3 outerPos = ESMathUtil.rotationToPosition(1 + Mth.sin((yawOffset / 180) * Mth.PI) * 1.25f, 0, -90 + yawOffset);
			if (i != 0 && lastYawOffset > 0 && yawOffset < 180) {
				Vec3 renderLastInnerPos = lastInnerPos;
				Vec3 renderLastOuterPos = lastOuterPos;
				Vec3 renderInnerPos = innerPos;
				Vec3 renderOuterPos = outerPos;
				if (yawOffset < 0) {
					renderInnerPos = ESMathUtil.rotationToPosition(1, 0, -90);
					renderOuterPos = renderInnerPos;
				}
				if (lastYawOffset > 180) {
					renderLastInnerPos = ESMathUtil.rotationToPosition(1, 0, 90);
					renderLastOuterPos = renderLastInnerPos;
				}
				PoseStack.Pose pose = stack.last();
				float u0 = this.getU0();
				float u1 = this.getU1();
				float v0 = this.getV0();
				float v1 = this.getV1();
				vertexConsumer.addVertex(pose, renderLastInnerPos.toVector3f()).setColor(128 / 255f, 156 / 255f, 240 / 255f, 1 - (i - 1) / (DIVISION / 2f)).setUv(u0, v0).setLight(LightTexture.FULL_BRIGHT);
				vertexConsumer.addVertex(pose, renderLastOuterPos.toVector3f()).setColor(128 / 255f, 156 / 255f, 240 / 255f, 1 - (i - 1) / (DIVISION / 2f)).setUv(u0, v1).setLight(LightTexture.FULL_BRIGHT);
				vertexConsumer.addVertex(pose, renderOuterPos.toVector3f()).setColor(128 / 255f, 156 / 255f, 240 / 255f, 1 - i / (DIVISION / 2f)).setUv(u1, v1).setLight(LightTexture.FULL_BRIGHT);
				vertexConsumer.addVertex(pose, renderInnerPos.toVector3f()).setColor(128 / 255f, 156 / 255f, 240 / 255f, 1 - i / (DIVISION / 2f)).setUv(u1, v0).setLight(LightTexture.FULL_BRIGHT);
			}
			lastYawOffset = yawOffset;
			lastInnerPos = innerPos;
			lastOuterPos = outerPos;
		}
		stack.popPose();
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
			return new LunarSlashParticle(level, x, y, z, dx, dy, dz, sprites);
		}
	}
}
