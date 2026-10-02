package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.trail.Trail;
import cn.leolezury.eternalstarlight.common.client.trail.TrailPoint;
import cn.leolezury.eternalstarlight.common.client.trail.TrailRenderer;
import cn.leolezury.eternalstarlight.common.client.trail.TrailStyles;
import cn.leolezury.eternalstarlight.common.particle.OrbitalTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.EasingCurve;
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
import org.joml.Vector4f;

import java.util.function.Function;

public class OrbitalTrailParticle extends TextureSheetParticle {
	private final Trail trail;
	private final Vec3 center, axis;
	private final EasingCurve radius, speed, length;
	private final float red, green, blue;
	private final boolean reverseSpeed;
	private float angle;

	protected OrbitalTrailParticle(ClientLevel level, double x, double y, double z, Vector3f axis, EasingCurve radius, EasingCurve speed, float width, EasingCurve length, Vector3f color, int lifetime, SpriteSet spriteSet, Function<Float, Vector4f> colorProfile) {
		super(level, x, y, z);
		this.trail = new Trail(width, length.calculate(0));
		this.trail.setColorProfile(TrailStyles.tint(colorProfile, new Vector4f(color, 1)));
		this.trail.setWidthProfile(TrailStyles.TAPER);
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
			this.trail.update(TrailPoint.cameraFacing(new Vec3(xo, yo, zo)).color(red, green, blue, 1));
			this.trail.setLength(length.calculate(progress));
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

	public static class Provider implements ParticleProvider<OrbitalTrailParticleOptions> {
		private final SpriteSet sprites;
		private final Function<Float, Vector4f> colorProfile;

		public Provider(SpriteSet spriteSet, Function<Float, Vector4f> colorProfile) {
			this.sprites = spriteSet;
			this.colorProfile = colorProfile;
		}

		@Override
		public Particle createParticle(OrbitalTrailParticleOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
			return new OrbitalTrailParticle(level, x, y, z, options.axis(), options.radius(), options.speed(), options.width(), options.length(), options.color(), options.lifetime(), sprites, colorProfile);
		}
	}
}
