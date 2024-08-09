package cn.leolezury.eternalstarlight.common.client.particle.environment;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@Environment(EnvType.CLIENT)
public class MeteorParticle extends Particle {
	private static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/trail.png");
	private final TrailEffect effect = new TrailEffect(0.8f, 15);

	protected MeteorParticle(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z);
		this.xd = 0;
		this.yd = -2;
		this.zd = 0;
		this.lifetime = 400;
	}

	@Override
	public void tick() {
		super.tick();
		level.addParticle(ESExplosionParticleOptions.AETHERSENT, true, x, y, z, 0, 0, 0);
		this.effect.update(new Vec3(xo, yo, zo), new Vec3(x - xo, y - yo, z - zo));
		if (onGround) {
			effect.setLength(Math.max(effect.getLength() - 0.75f, 0));
			if (effect.getLength() <= 0) {
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
		this.effect.prepareRender(new Vec3(x, y, z), new Vec3(this.x, this.y, this.z).subtract(new Vec3(this.xo, this.yo, this.zo)), partialTicks);
		List<TrailEffect.TrailPoint> adjustedVertical = this.effect.getVerticalRenderPoints().stream().map(point -> {
			Vec3 center = point.center();
			float width = point.width() / 2;
			if (Minecraft.getInstance().getCameraEntity() != null) {
				float yRot = Minecraft.getInstance().getCameraEntity().getYHeadRot() + 90;
				Vec3 upper = ESMathUtil.rotationToPosition(center, width, 0, yRot + 90);
				Vec3 lower = ESMathUtil.rotationToPosition(center, width, 0, yRot - 90);
				return new TrailEffect.TrailPoint(upper, lower);
			}
			return point;
		}).toList();
		this.effect.getVerticalRenderPoints().clear();
		this.effect.getVerticalRenderPoints().addAll(adjustedVertical);
		this.effect.render(ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.entityTranslucentGlow(TRAIL_TEXTURE)), stack, true, 144 / 255f, 94 / 255f, 168 / 255f, 1f, ClientHandlers.FULL_BRIGHT);
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
			return new MeteorParticle(level, x, y, z);
		}
	}
}
