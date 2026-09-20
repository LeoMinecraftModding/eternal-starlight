package cn.leolezury.eternalstarlight.common.particle;

import cn.leolezury.eternalstarlight.common.util.EasingCurve;
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

public record OrbitalTrailParticleOptions(ParticleType<OrbitalTrailParticleOptions> type, Vector3f axis, EasingCurve radius, EasingCurve speed, float width, EasingCurve length, Vector3f color, int lifetime) implements ParticleOptions {
	public static MapCodec<OrbitalTrailParticleOptions> codec(ParticleType<OrbitalTrailParticleOptions> type) {
		return RecordCodecBuilder.mapCodec((instance) -> instance.group(
			ExtraCodecs.VECTOR3F.fieldOf("axis").forGetter(OrbitalTrailParticleOptions::axis),
			EasingCurve.CODEC.fieldOf("radius").forGetter(OrbitalTrailParticleOptions::radius),
			EasingCurve.CODEC.fieldOf("speed").forGetter(OrbitalTrailParticleOptions::speed),
			Codec.FLOAT.fieldOf("width").forGetter(OrbitalTrailParticleOptions::width),
			EasingCurve.CODEC.fieldOf("length").forGetter(OrbitalTrailParticleOptions::length),
			ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(OrbitalTrailParticleOptions::color),
			Codec.INT.fieldOf("lifetime").forGetter(OrbitalTrailParticleOptions::lifetime)
		).apply(instance, (axis, radius, speed, width, length, color, lifetime) -> new OrbitalTrailParticleOptions(type, axis, radius, speed, width, length, color, lifetime)));
	}

	public static StreamCodec<RegistryFriendlyByteBuf, OrbitalTrailParticleOptions> streamCodec(ParticleType<OrbitalTrailParticleOptions> type) {
		return ByteBufCodecs.fromCodecWithRegistries(codec(type).codec());
	}

	@Override
	public ParticleType<OrbitalTrailParticleOptions> getType() {
		return type();
	}
}
