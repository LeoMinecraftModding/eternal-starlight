package cn.leolezury.eternalstarlight.common.client.particle.environment;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class FireflyParticle extends TextureSheetParticle {
	private final SpriteSet spriteSet;
	private int ticksSinceMotionChange;

	protected FireflyParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
		super(level, x, y, z, 0, 0, 0);
		this.quadSize = 0.1f + (this.random.nextFloat() * 0.1f);
		this.lifetime = 50 + this.random.nextInt(21);
		this.spriteSet = spriteSet;
		this.setSpriteFromAge(spriteSet);
		setSpeed();
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.spriteSet);
		ticksSinceMotionChange++;
		if (ticksSinceMotionChange > 40) {
			setSpeed();
			ticksSinceMotionChange = 0;
		}
	}

	private void setSpeed() {
		Vec3 movement = new Vec3((random.nextBoolean() ? 1 : -1) * random.nextInt(1, 10), (random.nextBoolean() ? 1 : -1) * random.nextInt(1, 10), (random.nextBoolean() ? 1 : -1) * random.nextInt(1, 10)).normalize().scale(0.03);
		this.xd = movement.x;
		this.yd = movement.y;
		this.zd = movement.z;
	}

	@Override
	public void move(double xd, double yd, double zd) {
		this.setBoundingBox(this.getBoundingBox().move(xd, yd, zd));
		this.setLocationFromBoundingbox();
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
		super.render(vertexConsumer, camera, f);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public int getLightColor(float partialTicks) {
		float g = (this.age + partialTicks) / this.lifetime;
		g = Mth.clamp(g, 0.0F, 1.0F);
		int i = super.getLightColor(partialTicks);
		int j = i & 255;
		int k = i >> 16 & 255;
		j += (int) (g * 15.0F * 16.0F);
		if (j > 240) {
			j = 240;
		}
		return j | k << 16;
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
