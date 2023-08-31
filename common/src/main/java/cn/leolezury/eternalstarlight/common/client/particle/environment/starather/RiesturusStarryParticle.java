package cn.leolezury.eternalstarlight.common.client.particle.environment.starather;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)

public class RiesturusStarryParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected RiesturusStarryParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, SpriteSet spriteSet) {
        super(clientLevel, d, e, f);
        this.gravity = 0.225F;
        this.friction = 1.0F;
        this.sprites = spriteSet;
        this.xd = g + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.yd = h + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.zd = i + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.quadSize = 0.1F * (this.random.nextFloat() * this.random.nextFloat() * 1.0F + 1.0F);
        this.lifetime = (int)(16.0 / ((double)this.random.nextFloat() * 0.8 + 0.2)) + 2;
        this.setSpriteFromAge(spriteSet);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        this.xd *= 0.949999988079071;
        this.yd *= 0.8999999761581421;
        this.zd *= 0.949999988079071;
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            RiesturusStarryParticle riesturusStarryParticle = new RiesturusStarryParticle(clientLevel, d, e, f, g, h, i, this.sprites);
            riesturusStarryParticle.setColor(0.923F, 0.964F, 0.999F);
            return riesturusStarryParticle;
        }
    }
}
