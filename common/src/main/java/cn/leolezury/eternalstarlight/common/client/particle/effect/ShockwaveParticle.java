package cn.leolezury.eternalstarlight.common.client.particle.effect;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class ShockwaveParticle extends SimpleAnimatedParticle {
	private final float rot;
	private final float pitch;

	protected ShockwaveParticle(ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz, SpriteSet spriteSet) {
		super(clientLevel, x, y, z, spriteSet, 0);
		this.friction = 1F;
		this.xd = dx;
		this.yd = dy;
		this.zd = dz;
		this.quadSize = this.random.nextFloat() * this.random.nextFloat() * 1.0F + 1.0F;
		this.lifetime = (int) (16.0 / (this.random.nextFloat() * 0.8 + 0.2)) + 2;
		this.rot = (float) Mth.atan2(-dx, -dz);
		this.pitch = (float) Mth.atan2(-dy, Math.sqrt(dx * dx + dz * dz));
		this.setFadeColor(15916745);
		this.setSpriteFromAge(spriteSet);
	}

	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public void tick() {
		super.tick();
		if (this.onGround || this.xd == 0.0 || this.zd == 0.0) {
			this.remove();
		}
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
		// float f = Mth.sin(((float)this.age + partialTicks - 6.2831855F) * 0.05F) * 2.0F;
		Quaternionf quaternionf = new Quaternionf();
		quaternionf.rotateY(rot).rotateX(-(pitch + 1.5707964F));
		this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
		quaternionf = new Quaternionf();
		quaternionf.rotateY(-3.1415927F + rot).rotateX(pitch + 1.5707964F);
		this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
			return new ShockwaveParticle(level, x, y, z, dx, dy, dz, this.sprites);
		}
	}
}
