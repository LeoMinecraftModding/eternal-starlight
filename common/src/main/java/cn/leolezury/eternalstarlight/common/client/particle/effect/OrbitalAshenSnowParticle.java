package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class OrbitalAshenSnowParticle extends BaseAshSmokeParticle {
	private final double cx, cz;
	private final float maxRadius, rotSpeed;
	private float radius, oRadius, angle, oAngle;

	protected OrbitalAshenSnowParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
		super(level, x, y, z, 1f, 1f, 1f, xSpeed, ySpeed, zSpeed, 1.25f, sprites, 1, 20, 0.0125f, true);
		this.rCol = this.gCol = this.bCol = 1;
		this.lifetime *= 4;
		this.cx = x;
		this.cz = z;
		this.oAngle = this.angle = (random.nextFloat() * 90 + 90) * (random.nextBoolean() ? 1 : -1);
		this.maxRadius = random.nextFloat() * 3 + 2;
		this.rotSpeed = (random.nextFloat() * 5 + 3) * (random.nextBoolean() ? 1 : -1);
	}

	@Override
	public void tick() {
		super.tick();
		oAngle = angle;
		angle += rotSpeed;
		oRadius = radius;
		radius += 0.25f;
		if (radius > maxRadius) {
			radius = maxRadius;
		}
		if (!onGround) {
			Vec3 pos = ESMathUtil.rotationToPosition(new Vec3(cx, 0, cz), radius, 0, angle);
			setPos(pos.x, y, pos.z);
		}
	}

	@Override
	public void render(VertexConsumer consumer, Camera camera, float partialTick) {
		if (!onGround) {
			Vec3 pos = ESMathUtil.rotationToPosition(new Vec3(cx, 0, cz), Mth.lerp(partialTick, oRadius, radius), 0, Mth.lerp(partialTick, oAngle, angle));
			this.x = pos.x;
			this.z = pos.z;
		}
		super.render(consumer, camera, partialTick);
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xs, double ys, double zs) {
			return new OrbitalAshenSnowParticle(level, x, y, z, xs, ys, zs, this.sprites);
		}
	}
}
