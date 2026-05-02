package cn.leolezury.eternalstarlight.common.particle;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

public record GeyserParticleOptions(ParticleType<GeyserParticleOptions> type, Vector3f color, int strength) implements ParticleOptions {
	public static GeyserParticleOptions getAbyssalGeyser(int strength) {
		return fromIntColor(ESParticles.GEYSER.get(), new Vector3f(55, 36, 55), strength);
	}

	public static GeyserParticleOptions fromIntColor(ParticleType<GeyserParticleOptions> type, Vector3f color, int strength) {
		return new GeyserParticleOptions(type, new Vector3f(color).div(255f), strength);
	}

	public static MapCodec<GeyserParticleOptions> codec(final ParticleType<GeyserParticleOptions> type) {
		return RecordCodecBuilder.mapCodec(
			i -> i.group(
					ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(o -> o.color),
					ExtraCodecs.POSITIVE_INT.fieldOf("strength").forGetter(o -> o.strength)
				)
				.apply(i, (color, strength) -> new GeyserParticleOptions(type, color, strength))
		);
	}

	public static StreamCodec<? super ByteBuf, GeyserParticleOptions> streamCodec(final ParticleType<GeyserParticleOptions> type) {
		return StreamCodec.composite(
			ByteBufCodecs.VECTOR3F,
			o -> o.color,
			ByteBufCodecs.INT,
			o -> o.strength,
			(color, strength) -> new GeyserParticleOptions(type, color, strength)
		);
	}

	@Override
	public ParticleType<GeyserParticleOptions> getType() {
		return this.type;
	}
}
