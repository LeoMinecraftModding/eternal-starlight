package cn.leolezury.eternalstarlight.common.client.particle.effect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class ESSmokeParticle extends SimpleAnimatedParticle {
    private final float rotSpeed;

    protected ESSmokeParticle(ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz, int fromColor, int toColor, SpriteSet spriteSet, float gravity) {
        super(clientLevel, x, y, z, spriteSet, gravity);
        this.xd = dx + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        this.yd = dy + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        this.zd = dz + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        double j = (Math.random() + Math.random() + 1.0) * 0.15000000596046448;
        double k = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
        this.xd = this.xd / k * j * 0.4000000059604645;
        this.yd = this.yd / k * j * 0.4000000059604645 + 0.10000000149011612;
        this.zd = this.zd / k * j * 0.4000000059604645;
        this.rotSpeed = (float) (Math.random() * 0.3f + 0.2f);
        this.lifetime = (int) (Math.random() * 20.0D) + 40;
        this.quadSize *= 10;
        this.alpha = 0.9f;
        this.setColor(fromColor);
        this.setFadeColor(toColor);
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();
        oRoll = roll;
        roll += rotSpeed;
    }

    @Environment(EnvType.CLIENT)
    public static class FlameProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public FlameProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz) {
            return new ESSmokeParticle(clientLevel, x, y, z, dx, dy, dz, 0xfff59d, 0x310a02, this.sprites, 0);
        }
    }
}
