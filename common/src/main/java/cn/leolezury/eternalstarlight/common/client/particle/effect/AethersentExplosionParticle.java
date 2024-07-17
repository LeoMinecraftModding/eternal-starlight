package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class AethersentExplosionParticle extends NoRenderParticle {
	protected AethersentExplosionParticle(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z);
		this.lifetime = 20;
	}

	@Override
	public void tick() {
		if (age == 0) {
			for (int angle = 0; angle <= 360; angle += 30) {
				Vec3 pos = ESMathUtil.rotationToPosition(new Vec3(x, y, z), 0.2f, 0, angle);
				level.addParticle(ESSmokeParticleOptions.AETHERSENT, true, x, y, z, pos.x - x, pos.y - y, pos.z - z);
			}
			Vec3 basePos = new Vec3(x, y + 2, z);
			for (int i = 0; i < 300; i++) {
				Vec3 pos = ESMathUtil.rotationToPosition(basePos, random.nextFloat() * 3, random.nextFloat() * 360, random.nextFloat() * 360);
				level.addParticle(ESParticles.AETHERSENT_SMOKE.get(), true, x, y, z, pos.x - x, pos.y - y, pos.z - z);
			}
			for (int i = 0; i < 15; i++) {
				Vec3 pos = ESMathUtil.rotationToPosition(new Vec3(x, y, z), 0.5f, random.nextInt(30, 60), random.nextFloat() * 360);
				level.addParticle(ESParticles.SMOKE_TRAIL.get(), true, x, y, z, pos.x - x, pos.y - y, pos.z - z);
			}
		}
		super.tick();
		float radius = (float) age / lifetime * 20f;
		for (int angle = 0; angle <= 360; angle += 10) {
			Vec3 basePos = ESMathUtil.rotationToPosition(new Vec3(x, y, z), radius, 0, angle);
			Vec3 pos = basePos.offsetRandom(random, 2);
			level.addParticle(ESExplosionParticleOptions.AETHERSENT, true, pos.x, pos.y, pos.z, 0, 0, 0);
			pos = basePos.offsetRandom(random, 2);
			level.addParticle(ParticleTypes.SMOKE, true, pos.x, pos.y, pos.z, 0, 0, 0);
			pos = basePos.offsetRandom(random, 2);
			level.addParticle(ParticleTypes.LARGE_SMOKE, true, pos.x, pos.y, pos.z, 0, 0, 0);
			if (random.nextInt(25) == 0) {
				level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.x, pos.y, pos.z, 0, 0.05, 0);
			}
			pos = basePos.offsetRandom(random, 2);
			level.addParticle(ParticleTypes.WHITE_SMOKE, true, pos.x, pos.y, pos.z, 0, 0, 0);
		}
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		public Provider(SpriteSet spriteSet) {
		}

		public Particle createParticle(SimpleParticleType type, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new AethersentExplosionParticle(clientLevel, d, e, f);
		}
	}
}
