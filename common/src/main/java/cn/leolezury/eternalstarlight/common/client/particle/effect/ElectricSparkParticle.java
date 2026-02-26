package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ElectricSparkParticle extends TextureSheetParticle {
	private final Vec3 destPos;
	private final List<Vec3> segments = new ArrayList<>();

	protected ElectricSparkParticle(ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz, SpriteSet spriteSet) {
		super(clientLevel, x, y, z);
		this.stoppedByCollision = true;
		this.lifetime = (int) (this.random.nextFloat() * 7 + 7);
		this.destPos = new Vec3(x + dx, y + dy, z + dz);
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
	public void tick() {
		super.tick();
		Vec3 currentPos = new Vec3(x, y, z);
		if (segments.isEmpty() || age % 3 == 0) {
			segments.clear();
			int numSegments = (int) (this.random.nextFloat() * 3 + 3);
			float segmentLength = (float) (destPos.subtract(currentPos).length() / numSegments);
			Vec3 increment = destPos.subtract(currentPos).scale((double) 1 / numSegments);
			segments.add(currentPos);
			for (int i = 0; i < numSegments; i++) {
				if (i == numSegments - 1) {
					segments.add(currentPos.add(increment.scale((i + 1))));
				} else {
					segments.add(currentPos.add(increment.scale((i + 1))).add(new Vec3(random.nextDouble() - 0.5, random.nextDouble() - 0.5, random.nextDouble() - 0.5).normalize().scale(segmentLength / 2)));
				}
			}
		}
	}

	@Override
	public void render(VertexConsumer consumer, Camera camera, float partialTick) {
		Vec3 camPos = camera.getPosition();
		PoseStack stack = new PoseStack();
		stack.pushPose();
		stack.translate(-camPos.x, -camPos.y, -camPos.z);
		VertexConsumer vertexConsumer = ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE_ADDITIVE_GLOW);
		for (int i = 0; i < segments.size() - 1; i++) {
			Vec3 start = segments.get(i);
			Vec3 end = segments.get(i + 1);
			Vec3 offset = end.subtract(start);
			Vec3 sight = camPos.subtract(start).scale(-1);
			Vec3 sideOffset = offset.cross(sight).normalize().scale(0.03);
			PoseStack.Pose pose = stack.last();
			float u0 = this.getU0();
			float u1 = this.getU1();
			float v0 = this.getV0();
			float v1 = this.getV1();
			vertexConsumer.addVertex(pose, start.add(sideOffset).toVector3f()).setColor(0.6F, 0.6F, 0.9F, 0.75F).setUv(u0, v0).setLight(LightTexture.FULL_BRIGHT);
			vertexConsumer.addVertex(pose, start.add(sideOffset.scale(-1)).toVector3f()).setColor(0.6F, 0.6F, 0.9f, 0.75f).setUv(u0, v1).setLight(LightTexture.FULL_BRIGHT);
			vertexConsumer.addVertex(pose, end.add(sideOffset.scale(-1)).toVector3f()).setColor(0.6F, 0.6F, 0.9f, 0.75f).setUv(u1, v1).setLight(LightTexture.FULL_BRIGHT);
			vertexConsumer.addVertex(pose, end.add(sideOffset).toVector3f()).setColor(0.6F, 0.6F, 0.9f, 0.75f).setUv(u1, v0).setLight(LightTexture.FULL_BRIGHT);
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
			return new ElectricSparkParticle(level, x, y, z, dx, dy, dz, sprites);
		}
	}
}
