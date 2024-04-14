package cn.leolezury.eternalstarlight.common.client.particle.lightning;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

public class LightningParticleOptions implements ParticleOptions {
    protected final Vector3f color;

    public Vector3f getColor() {
        return this.color;
    }

    public static final MapCodec<LightningParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(LightningParticleOptions::getColor)).apply(instance, LightningParticleOptions::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, LightningParticleOptions> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VECTOR3F, LightningParticleOptions::getColor, LightningParticleOptions::new);

    public LightningParticleOptions(Vector3f vector3f) {
        this.color = vector3f;
    }

    public ParticleType<LightningParticleOptions> getType() {
        return ESParticles.LIGHTNING.get();
    }
}
