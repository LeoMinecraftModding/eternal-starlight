package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.particle.OrbitalTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Color;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class OrbitalTrailParticle extends Particle {
	private static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/trail.png");

	private final TrailEffect effect = new TrailEffect(0.3f, 20);
	private final int fromColor, toColor;
	private final float radius, rotSpeed;
	private final Entity owner;
	private double cx, cy, cz;
	private float yaw;

	protected OrbitalTrailParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int lifetime, int owner, int fromColor, int toColor, float radius, float rotSpeed, float alpha) {
		super(level, x, y, z);
		this.lifetime = lifetime;
		this.xd = xSpeed;
		this.yd = ySpeed;
		this.zd = zSpeed;
		this.fromColor = fromColor;
		this.toColor = toColor;
		this.radius = radius;
		this.rotSpeed = rotSpeed;
		this.alpha = alpha;
		this.cx = x;
		this.cy = y;
		this.cz = z;
		this.owner = level.getEntity(owner);
	}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		this.cx += xd;
		this.cy += yd;
		this.cz += zd;
		if (this.owner != null) {
			this.cx = owner.getX();
			this.cz = owner.getZ();
		}
		this.yaw += rotSpeed;
		this.yaw = Mth.wrapDegrees(yaw);
		Vec3 pos = ESMathUtil.rotationToPosition(new Vec3(cx, cy, cz), radius, 0, yaw);
		this.x = pos.x();
		this.y = pos.y();
		this.z = pos.z();
		this.effect.update(new Vec3(xo, yo, zo), new Vec3(x - xo, y - yo, z - zo));
		if (this.age++ >= this.lifetime) {
			this.remove();
		}
	}

	@Override
	public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
		float progress = Math.min(age + partialTicks, lifetime) / lifetime;
		Color color = Color.rgbi((int) Mth.lerp(progress, Color.rgb(fromColor).r(), Color.rgb(toColor).r()), (int) Mth.lerp(progress, Color.rgb(fromColor).g(), Color.rgb(toColor).g()), (int) Mth.lerp(progress, Color.rgb(fromColor).b(), Color.rgb(toColor).b()));
		float a = Mth.lerp((float) (Math.abs(progress - 0.5) * 2), alpha, 0);
		PoseStack stack = new PoseStack();
		float x = (float) Mth.lerp(partialTicks, this.xo, this.x);
		float y = (float) Mth.lerp(partialTicks, this.yo, this.y);
		float z = (float) Mth.lerp(partialTicks, this.zo, this.z);
		stack.pushPose();
		stack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
		this.effect.prepareRender(new Vec3(x, y, z), new Vec3(this.x, this.y, this.z).subtract(new Vec3(this.xo, this.yo, this.zo)), partialTicks);
		this.effect.render(ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.entityTranslucentGlow(TRAIL_TEXTURE)), stack, true, color.rf(), color.gf(), color.bf(), a, ClientHandlers.FULL_BRIGHT);
		stack.popPose();
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.CUSTOM;
	}


	public static class Provider implements ParticleProvider<OrbitalTrailParticleOptions> {
		public Provider(SpriteSet spriteSet) {
		}

		@Override
		public Particle createParticle(OrbitalTrailParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new OrbitalTrailParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, options.lifetime(), options.owner(), Color.rgbd(options.fromColor().x / 255f, options.fromColor().y / 255f, options.fromColor().z / 255f).rgb(), Color.rgbd(options.toColor().x / 255f, options.toColor().y / 255f, options.toColor().z / 255f).rgb(), options.radius(), options.rotSpeed(), options.alpha());
		}
	}
}
