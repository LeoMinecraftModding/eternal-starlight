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
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;

import java.util.List;

public record GlowParticleOptions(Vector3f fromColor, Vector3f toColor, float alpha, float lifeScale) implements ParticleOptions {
	public static final GlowParticleOptions GLOW = new GlowParticleOptions(new Vector3f(255, 255, 255), new Vector3f(255, 255, 255), 0.8f, 1.2f);
	private static final List<Vector3f> SEEK_COLORS = List.of(
		new Vector3f(49, 177, 204),
		new Vector3f(89, 47, 108),
		new Vector3f(22, 7, 78),
		new Vector3f(209, 107, 187),
		new Vector3f(107, 194, 209),
		new Vector3f(67, 113, 145),
		new Vector3f(107, 101, 155)
	);

	public static final MapCodec<GlowParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(GlowParticleOptions::fromColor),
		ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(GlowParticleOptions::toColor),
		Codec.FLOAT.fieldOf("alpha").forGetter(GlowParticleOptions::alpha),
		Codec.FLOAT.fieldOf("life_scale").forGetter(GlowParticleOptions::lifeScale)
	).apply(instance, GlowParticleOptions::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, GlowParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

	public static GlowParticleOptions getSeek(RandomSource random, boolean lowTransparency, boolean extendedLife) {
		return new GlowParticleOptions(SEEK_COLORS.get(random.nextInt(SEEK_COLORS.size())), SEEK_COLORS.get(random.nextInt(SEEK_COLORS.size())), lowTransparency ? 1 : 0.5f + (random.nextFloat() - 0.5f) * 0.2f, extendedLife ? 1.5f : 0.4f);
	}

	public ParticleType<GlowParticleOptions> getType() {
		return ESParticles.GLOW.get();
	}
}
