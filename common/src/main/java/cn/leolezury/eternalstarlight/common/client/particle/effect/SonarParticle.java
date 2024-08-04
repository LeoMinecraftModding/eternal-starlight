package cn.leolezury.eternalstarlight.common.client.particle.effect;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class SonarParticle extends SimpleAnimatedParticle {
	private final float rot;
	private final float pitch;

	protected SonarParticle(ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz, SpriteSet spriteSet) {
		super(clientLevel, x, y, z, spriteSet, 0);
		this.friction = 1F;
		this.xd = dx;
		this.yd = dy;
		this.zd = dz;
		this.quadSize = this.random.nextFloat() * this.random.nextFloat() * 2.0F + 2.0F;
		this.lifetime = (int) (100f + this.random.nextFloat());
		this.rot = (float) Mth.atan2(-dx, -dz);
		this.pitch = (float) Mth.atan2(-dy, Math.sqrt(dx * dx + dz * dz));
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.onGround || this.xd == 0.0 || this.zd == 0.0) {
			this.remove();
		}
		alpha = 1f - (float) age / lifetime;
	}

	@Override
	public float getQuadSize(float partialTicks) {
		return this.quadSize * Mth.clamp(((float) this.age + partialTicks) * 1.5F / (float) this.lifetime, 0.0F, 1.0F) * 1.5F;
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
		// float f = Mth.sin(((float)this.age + partialTicks - 6.2831855F) * 0.05F) * 2.0F;
		Quaternionf quaternionf = new Quaternionf();
		quaternionf.rotateY(rot).rotateX(-pitch);
		this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
		quaternionf = new Quaternionf();
		quaternionf.rotateY(-3.1415927F + rot).rotateX(pitch);
		this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
			return new SonarParticle(level, x, y, z, dx, dy, dz, this.sprites);
		}
	}
}
