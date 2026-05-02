package cn.leolezury.eternalstarlight.common.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

public record GeyserBaseParticleOptions(ParticleType<GeyserBaseParticleOptions> type, Vector3f color, int strength, float burstImpulseBase) implements ParticleOptions {
	public static MapCodec<GeyserBaseParticleOptions> codec(final ParticleType<GeyserBaseParticleOptions> type) {
		return RecordCodecBuilder.mapCodec(
			i -> i.group(
					ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(o -> o.color),
					ExtraCodecs.POSITIVE_INT.fieldOf("strength").forGetter(o -> o.strength),
					Codec.FLOAT.fieldOf("burst_impulse_base").forGetter(o -> o.burstImpulseBase)
				)
				.apply(i, (color, strength, burstImpulseBase) -> new GeyserBaseParticleOptions(type, color, strength, burstImpulseBase))
		);
	}

	public static StreamCodec<? super ByteBuf, GeyserBaseParticleOptions> streamCodec(final ParticleType<GeyserBaseParticleOptions> type) {
		return StreamCodec.composite(
			ByteBufCodecs.VECTOR3F,
			o -> o.color,
			ByteBufCodecs.INT,
			o -> o.strength,
			ByteBufCodecs.FLOAT,
			o -> o.burstImpulseBase,
			(color, strength, burstImpulseBase) -> new GeyserBaseParticleOptions(type, color, strength, burstImpulseBase)
		);
	}

	@Override
	public ParticleType<GeyserBaseParticleOptions> getType() {
		return this.type;
	}
}
