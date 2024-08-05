package cn.leolezury.eternalstarlight.common.client.particle.environment;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class FireflyParticle extends TextureSheetParticle {
	private final SpriteSet spriteSet;
	private int ticksSinceMotionChange;

	protected FireflyParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
		super(level, x, y, z, 0, 0, 0);
		this.quadSize = 0.2f + (this.random.nextFloat() * 0.1f);
		this.lifetime = 50 + this.random.nextInt(21);
		this.spriteSet = spriteSet;
		this.setSpriteFromAge(spriteSet);
		this.setAlpha();
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.spriteSet);
		ticksSinceMotionChange++;
		if (ticksSinceMotionChange > 40) {
			Vec3 movement = new Vec3((random.nextBoolean() ? 1 : -1) * random.nextInt(1, 10), (random.nextBoolean() ? 1 : -1) * random.nextInt(1, 10), (random.nextBoolean() ? 1 : -1) * random.nextInt(1, 10)).normalize().scale(new Vec3(xd, yd, zd).length());
			this.xd = movement.x;
			this.yd = movement.y;
			this.zd = movement.z;
			ticksSinceMotionChange = 0;
		}
		setAlpha();
	}

	private void setAlpha() {
		this.alpha = (float) (0.85f * (1 - Math.abs(age - lifetime / 2) / ((float) lifetime / 2)) + 0.15f * ((Math.sin((double) age / 2) + 1) / 2f));
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
