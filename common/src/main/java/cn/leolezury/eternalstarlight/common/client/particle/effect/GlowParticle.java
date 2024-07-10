package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.particle.GlowParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Color;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class GlowParticle extends TextureSheetParticle {
	protected SpriteSet sprites;
	private final float rotSpeed;
	private float fadeR;
	private float fadeG;
	private float fadeB;

	protected GlowParticle(ClientLevel level, float lifeMultiplier, double x, double y, double z, double dx, double dy, double dz, int fromColor, int toColor, float alpha, SpriteSet spriteSet) {
		super(level, x, y, z, dx, dy, dz);
		this.xd = dx;
		this.yd = dy;
		this.zd = dz;
		this.rotSpeed = (float) (Math.random() * 0.05f + 0.075f);
		this.lifetime = (int) (((int) (Math.random() * 20.0D) + 40) * lifeMultiplier);
		this.alpha = alpha;
		this.setColor(fromColor);
		this.setFadeColor(toColor);
		this.setSpriteFromAge(spriteSet);
		this.sprites = spriteSet;
	}

	public void setColor(int i) {
		float f = (float) ((i & 16711680) >> 16) / 255.0F;
		float g = (float) ((i & '\uff00') >> 8) / 255.0F;
		float h = (float) ((i & 255)) / 255.0F;
		this.setColor(f, g, h);
	}

	public void setFadeColor(int i) {
		this.fadeR = (float) ((i & 16711680) >> 16) / 255.0F;
		this.fadeG = (float) ((i & '\uff00') >> 8) / 255.0F;
		this.fadeB = (float) ((i & 255)) / 255.0F;
	}

	@Override
	public void tick() {
		super.tick();
		oRoll = roll;
		roll += rotSpeed;
		this.setSpriteFromAge(sprites);
		if (this.age > this.lifetime / 2) {
			this.rCol += (this.fadeR - this.rCol) * 0.2F;
			this.gCol += (this.fadeG - this.gCol) * 0.2F;
			this.bCol += (this.fadeB - this.bCol) * 0.2F;
		}
	}

	@Override
	public int getLightColor(float f) {
		return 15728880;
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
		float a = alpha;
		float progress = Math.min(age + f, lifetime) / lifetime;
		this.alpha = Mth.lerp((float) Math.pow((Math.abs(progress - 0.5) * 2), 5), alpha, 0);
		super.render(ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE), camera, f);
		this.alpha = a;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.CUSTOM;
	}

	public static class Provider implements ParticleProvider<GlowParticleOptions> {
		private final SpriteSet sprites;

		public Provider(SpriteSet spriteSet) {
			this.sprites = spriteSet;
		}

		public Particle createParticle(GlowParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new GlowParticle(level, options.lifeScale(), x, y, z, xSpeed, ySpeed, zSpeed, Color.rgbd(options.fromColor().x / 255f, options.fromColor().y / 255f, options.fromColor().z / 255f).rgb(), Color.rgbd(options.toColor().x / 255f, options.toColor().y / 255f, options.toColor().z / 255f).rgb(), options.alpha(), this.sprites);
		}
	}
}
