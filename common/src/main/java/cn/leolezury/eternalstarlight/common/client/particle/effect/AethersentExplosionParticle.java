package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
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
            for (int i = 0; i < 20; i++) {
                Vec3 pos = new Vec3(x, y, z).offsetRandom(random, 5);
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.x, y, pos.z, 0, 0, 0);
            }
            for (int i = 0; i < 25; i++) {
                Vec3 pos = new Vec3(x, y + 2, z).offsetRandom(random, 3);
                level.addParticle(ESSmokeParticleOptions.AETHERSENT, pos.x, pos.y, pos.z, 0, 0, 0);
            }
        }
        super.tick();
        float radius = (float) age / lifetime * 20f;
        for (int angle = 0; angle <= 360; angle += 10) {
            Vec3 basePos = ESMathUtil.rotationToPosition(new Vec3(x, y, z), radius, 0, angle);
            Vec3 pos = basePos.offsetRandom(random, 2);
            level.addParticle(ESExplosionParticleOptions.AETHERSENT, pos.x, pos.y, pos.z, 0, 0, 0);
            pos = basePos.offsetRandom(random, 2);
            level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0, 0, 0);
            pos = basePos.offsetRandom(random, 2);
            level.addParticle(ParticleTypes.LARGE_SMOKE, pos.x, pos.y, pos.z, 0, 0, 0);
            if (random.nextInt(25) == 0) {
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.x, pos.y, pos.z, 0, 0.05, 0);
            }
            pos = basePos.offsetRandom(random, 2);
            level.addParticle(ParticleTypes.WHITE_SMOKE, pos.x, pos.y, pos.z, 0, 0, 0);
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        public Provider(SpriteSet spriteSet) {}

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new AethersentExplosionParticle(clientLevel, d, e, f);
        }
    }
}
