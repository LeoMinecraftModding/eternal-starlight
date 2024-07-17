package cn.leolezury.eternalstarlight.common.client.particle.advanced;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

@Environment(EnvType.CLIENT)
public class AdvancedParticle extends TextureSheetParticle implements ParticleOperator {
	protected SpriteSet sprites;
	private final AdvancedParticleOptions options;

	public AdvancedParticle(ClientLevel clientLevel, double x, double y, double z, SpriteSet spriteSet, AdvancedParticleOptions options) {
		super(clientLevel, x, y, z);
		this.sprites = spriteSet;
		this.options = options;
		this.options.operateSpawn(this);
		this.setSpriteFromAge(sprites);
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(sprites);
		this.options.operateTick(this);
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
		this.options.operateRender(this);
		super.render(ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(this.options.getRenderType()), camera, f);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.CUSTOM;
	}

	@Override
	public void remove() {
		this.options.operateRemove(this);
		super.remove();
	}

	@Override
	public Vec3 getSpeed() {
		return new Vec3(xd, yd, zd);
	}

	@Override
	public void setSpeed(Vec3 speed) {
		xd = speed.x;
		yd = speed.y;
		zd = speed.z;
	}

	@Override
	public float getQuadSize() {
		return quadSize;
	}

	@Override
	public void setQuadSize(float quadSize) {
		this.quadSize = quadSize;
	}

	@Override
	public float getAge() {
		return age + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public Vector4f getColor() {
		return new Vector4f(rCol, gCol, bCol, alpha);
	}

	@Override
	public void setColor(Vector4f color) {
		rCol = color.x;
		gCol = color.y;
		bCol = color.z;
		alpha = color.w;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(SimpleParticleType type, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new AdvancedParticle(clientLevel, d, e, f, spriteSet, null);
		}
	}
}
