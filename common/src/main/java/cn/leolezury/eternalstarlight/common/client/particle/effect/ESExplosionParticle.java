package cn.leolezury.eternalstarlight.common.client.particle.effect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class ESExplosionParticle extends SimpleAnimatedParticle {
    protected ESExplosionParticle(ClientLevel clientLevel, double d, double e, double f, int fromColor, int toColor, SpriteSet spriteSet, float g) {
        super(clientLevel, d, e, f, spriteSet, g);
        this.lifetime = 6 + this.random.nextInt(4);
        this.quadSize = (float) ((8f + 2f * Math.random()) * quadSize);
        this.setColor(fromColor);
        this.setFadeColor(toColor);
        this.setSpriteFromAge(spriteSet);
    }

    public int getLightColor(float f) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Environment(EnvType.CLIENT)
    public static class EnergyProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public EnergyProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new ESExplosionParticle(clientLevel, d, e, f, 0xffffff, 0x81d4fa, this.sprites, 0);
        }
    }
    @Environment(EnvType.CLIENT)
    public static class LavaProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public LavaProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new ESExplosionParticle(clientLevel, d, e, f, 0xd9a84a, 0xae4c12, this.sprites, 0);
        }
    }
}
