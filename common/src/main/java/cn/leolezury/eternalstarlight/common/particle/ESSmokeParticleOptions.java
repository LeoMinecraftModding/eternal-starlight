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

public record ESSmokeParticleOptions(Vector3f fromColor, Vector3f toColor, float alpha, float lifeScale, float motionScale, boolean rise) implements ParticleOptions {
	public static final ESSmokeParticleOptions FLAME = fromIntColor(new Vector3f(255, 147, 25), new Vector3f(49, 10, 2), 1, 1, 1, true);
	public static final ESSmokeParticleOptions AETHERSENT = fromIntColor(new Vector3f(115, 51, 153), new Vector3f(46, 14, 64), 1, 3, 2.5f, false);
	public static final ESSmokeParticleOptions LUNAR = fromIntColor(new Vector3f(66, 66, 115), new Vector3f(32, 32, 64), 1, 3, 0f, false);
	public static final ESSmokeParticleOptions LUNAR_SHORT = fromIntColor(new Vector3f(66, 66, 115), new Vector3f(32, 32, 64), 1, 0.8f, 0f, false);
	public static final ESSmokeParticleOptions LUNAR_BREATH = fromIntColor(new Vector3f(66, 66, 115), new Vector3f(32, 32, 64), 1, 0.6f, 4f, false);
	public static final ESSmokeParticleOptions LUNAR_ATTACK = fromIntColor(new Vector3f(66, 66, 115), new Vector3f(32, 32, 64), 0.5f, 0.6f, 0.4f, false);
	public static final ESSmokeParticleOptions PUNGENCY_FRUIT = fromIntColor(new Vector3f(87, 58, 69), new Vector3f(179, 116, 116), 1, 1, 0.3f, true);

	public static ESSmokeParticleOptions fromIntColor(Vector3f fromColor, Vector3f toColor, float alpha, float lifeScale, float motionScale, boolean rise) {
		return new ESSmokeParticleOptions(new Vector3f(fromColor).div(255f), new Vector3f(toColor).div(255f), alpha, lifeScale, motionScale, rise);
	}

	public static final MapCodec<ESSmokeParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(ESSmokeParticleOptions::fromColor),
		ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(ESSmokeParticleOptions::toColor),
		Codec.FLOAT.fieldOf("alpha").forGetter(ESSmokeParticleOptions::alpha),
		Codec.FLOAT.fieldOf("life_scale").forGetter(ESSmokeParticleOptions::lifeScale),
		Codec.FLOAT.fieldOf("motion_scale").forGetter(ESSmokeParticleOptions::motionScale),
		Codec.BOOL.fieldOf("rise").forGetter(ESSmokeParticleOptions::rise)
	).apply(instance, ESSmokeParticleOptions::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, ESSmokeParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

	@Override
	public ParticleType<ESSmokeParticleOptions> getType() {
		return ESParticles.SMOKE.get();
	}
}
