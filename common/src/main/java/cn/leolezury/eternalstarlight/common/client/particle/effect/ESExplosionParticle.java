package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Color;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;

public class ESExplosionParticle extends SimpleAnimatedParticle {
    protected ESExplosionParticle(ClientLevel level, double d, double e, double f, int fromColor, int toColor, SpriteSet spriteSet, float g) {
        super(level, d, e, f, spriteSet, g);
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

    public static class Provider implements ParticleProvider<ESExplosionParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(ESExplosionParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ESExplosionParticle(level, x, y, z, Color.rgbd(options.fromColor().x / 255f, options.fromColor().y / 255f, options.fromColor().z / 255f).rgb(), Color.rgbd(options.toColor().x / 255f, options.toColor().y / 255f, options.toColor().z / 255f).rgb(), this.sprites, 0);
        }
    }
}
