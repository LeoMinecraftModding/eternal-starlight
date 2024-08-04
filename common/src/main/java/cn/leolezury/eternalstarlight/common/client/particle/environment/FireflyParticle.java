package cn.leolezury.eternalstarlight.common.client.particle.environment;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.List;

@Environment(EnvType.CLIENT)
public class FireflyParticle extends TextureSheetParticle {
	private final SpriteSet spriteSet;
	private int ticksOnGround;
	private int ticksSinceMotionChange;

	protected FireflyParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
		super(level, x, y, z, 0, 0, 0);
		this.quadSize = 0.2f + (this.random.nextFloat() * 0.1f);
		this.lifetime = 50 + this.random.nextInt(21);
		this.spriteSet = spriteSet;
		this.setSpriteFromAge(spriteSet);
		checkLight();
	}

	private boolean checkLight() {
		if (this.level.getBrightness(LightLayer.SKY, BlockPos.containing(this.x, this.y, this.z)) < 1) {
			this.remove();
			return false;
		}
		return true;
	}

	@Override
	public void tick() {
		checkLight();
		super.tick();
		this.setSpriteFromAge(this.spriteSet);
		ticksSinceMotionChange++;
		if (ticksSinceMotionChange > 25) {
			Vec3 movement = new Vec3((random.nextBoolean() ? 1 : -1) * random.nextInt(1, 10), (random.nextBoolean() ? 1 : -1) * random.nextInt(1, 10), (random.nextBoolean() ? 1 : -1) * random.nextInt(1, 10)).normalize().scale(new Vec3(xd, yd, zd).length());
			this.xd = movement.x;
			this.yd = movement.y;
			this.zd = movement.z;
			ticksSinceMotionChange = 0;
		}
		ESEntityUtil.RaytraceResult result = ESEntityUtil.raytrace(level, CollisionContext.empty(), new Vec3(x, y, z), new Vec3(x + xd, y + yd, z + zd));
		if (result.blockHitResult() != null) {
			BlockHitResult hitResult = result.blockHitResult();
			switch (hitResult.getDirection().getAxis()) {
				case X -> xd = -xd;
				case Y -> yd = -yd;
				case Z -> zd = -zd;
			}
		}
		if (onGround) {
			ticksOnGround++;
			if (ticksOnGround > 20) {
				remove();
			}
		} else {
			ticksOnGround = 0;
		}
		this.alpha = (float) (0.85f * (1 - Math.abs(age - lifetime / 2) / ((float) lifetime / 2)) + 0.15f * ((Math.sin((double) age / 2) + 1) / 2f));
	}

	@Override
	public void move(double d, double e, double f) {
		double g = d;
		double h = e;
		double i = f;
		if (this.hasPhysics && (d != 0.0 || e != 0.0 || f != 0.0) && d * d + e * e + f * f < 10) {
			Vec3 vec3 = Entity.collideBoundingBox(null, new Vec3(d, e, f), this.getBoundingBox(), this.level, List.of());
			d = vec3.x;
			e = vec3.y;
			f = vec3.z;
		}

		if (d != 0.0 || e != 0.0 || f != 0.0) {
			this.setBoundingBox(this.getBoundingBox().move(d, e, f));
			this.setLocationFromBoundingbox();
		}

		this.onGround = h != e && h < 0.0;
		if (g != d) {
			this.xd = 0.0;
		}

		if (i != f) {
			this.zd = 0.0;
		}
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
		if (checkLight() || age > 3) {
			super.render(vertexConsumer, camera, f);
		}
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public int getLightColor(float partialTicks) {
		return ClientHandlers.FULL_BRIGHT;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel clientLevel, double x, double y, double z, double xs, double ys, double zs) {
			return new FireflyParticle(clientLevel, x, y, z, this.sprites);
		}
	}
}
