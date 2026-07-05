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
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3f;

public record SurroundingTrailParticleOptions(Vector3f fromColor, Vector3f toColor, float radius, float rotSpeed, float alpha, int lifetime, int owner) implements ParticleOptions {
	public static SurroundingTrailParticleOptions fromIntColor(Vector3f fromColor, Vector3f toColor, float radius, float rotSpeed, float alpha, int lifetime, int owner) {
		return new SurroundingTrailParticleOptions(new Vector3f(fromColor).div(255f), new Vector3f(toColor).div(255f), radius, rotSpeed, alpha, lifetime, owner);
	}

	public static final MapCodec<SurroundingTrailParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(SurroundingTrailParticleOptions::fromColor),
		ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(SurroundingTrailParticleOptions::toColor),
		Codec.FLOAT.fieldOf("radius").forGetter(SurroundingTrailParticleOptions::radius),
		Codec.FLOAT.fieldOf("rot_speed").forGetter(SurroundingTrailParticleOptions::rotSpeed),
		Codec.FLOAT.fieldOf("alpha").forGetter(SurroundingTrailParticleOptions::alpha),
		Codec.INT.fieldOf("lifetime").forGetter(SurroundingTrailParticleOptions::lifetime),
		Codec.INT.fieldOf("owner").forGetter(SurroundingTrailParticleOptions::owner)
	).apply(instance, SurroundingTrailParticleOptions::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, SurroundingTrailParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

	public static SurroundingTrailParticleOptions magic(Player player) {
		RandomSource random = player.getRandom();
		return fromIntColor(new Vector3f(182, 48, 112), new Vector3f(99, 224, 235), 0.8f + (random.nextFloat() - 0.5f) * 0.2f, (random.nextBoolean() ? -1 : 1) * (float) (15f + (random.nextFloat() - 0.5) * 7f), 0.9f + (random.nextFloat() - 0.5f) * 0.1f, (int) (75 + (random.nextFloat() - 0.5) * 10), player.getId());
	}

	@Override
	public ParticleType<SurroundingTrailParticleOptions> getType() {
		return ESParticles.SURROUNDING_TRAIL.get();
	}
}
