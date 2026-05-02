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

public record RingParticleOptions(Vector3f fromColor, Vector3f toColor, float scale, float lifeScale, boolean gathering) implements ParticleOptions {
	public static RingParticleOptions fromIntColor(Vector3f fromColor, Vector3f toColor, float scale, float lifeScale, boolean gathering) {
		return new RingParticleOptions(new Vector3f(fromColor).div(255f), new Vector3f(toColor).div(255f), scale, lifeScale, gathering);
	}

	public static RingParticleOptions getFlare(float lifeScale) {
		return fromIntColor(new Vector3f(222, 112, 255), new Vector3f(255, 255, 116), 2, lifeScale, true);
	}

	public static final MapCodec<RingParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(RingParticleOptions::fromColor),
		ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(RingParticleOptions::toColor),
		Codec.FLOAT.fieldOf("scale").forGetter(RingParticleOptions::scale),
		Codec.FLOAT.fieldOf("life_scale").forGetter(RingParticleOptions::lifeScale),
		Codec.BOOL.fieldOf("gathering").forGetter(RingParticleOptions::gathering)
	).apply(instance, RingParticleOptions::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, RingParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

	@Override
	public ParticleType<RingParticleOptions> getType() {
		return ESParticles.RING.get();
	}
}
