package cn.leolezury.eternalstarlight.common.particle;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

public record ESSmokeParticleOptions(Vector3f fromColor, Vector3f toColor, float alpha, boolean rise, float lifeScale, float motionScale) implements ParticleOptions {
    public static final ESSmokeParticleOptions FLAME = new ESSmokeParticleOptions(new Vector3f(255, 147, 25), new Vector3f(49, 10, 2), 1, true, 1, 1);
    public static final ESSmokeParticleOptions ENERGIZED_FLAME = new ESSmokeParticleOptions(new Vector3f(207, 255, 255), new Vector3f(49, 10, 2), 0.3f, true, 1.2f, 1.8f);
    public static final ESSmokeParticleOptions AETHERSENT = new ESSmokeParticleOptions(new Vector3f(115, 51, 153), new Vector3f(46, 14, 64), 0.5f, false, 3, 2.5f);
    public static final ESSmokeParticleOptions LUNAR = new ESSmokeParticleOptions(new Vector3f(66, 66, 115), new Vector3f(32, 32, 64), 0.4f, false, 3, 0f);
    public static final ESSmokeParticleOptions LUNAR_SHORT = new ESSmokeParticleOptions(new Vector3f(66, 66, 115), new Vector3f(32, 32, 64), 0.4f, false, 0.9f, 0f);

    public static final MapCodec<ESSmokeParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(ESSmokeParticleOptions::fromColor),
            ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(ESSmokeParticleOptions::toColor),
            Codec.FLOAT.fieldOf("alpha").forGetter(ESSmokeParticleOptions::alpha),
            Codec.BOOL.fieldOf("rise").forGetter(ESSmokeParticleOptions::rise),
            Codec.FLOAT.fieldOf("life_scale").forGetter(ESSmokeParticleOptions::lifeScale),
            Codec.FLOAT.fieldOf("motion_scale").forGetter(ESSmokeParticleOptions::motionScale)
    ).apply(instance, ESSmokeParticleOptions::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ESSmokeParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

    public ParticleType<ESSmokeParticleOptions> getType() {
        return ESParticles.SMOKE.get();
    }
}
