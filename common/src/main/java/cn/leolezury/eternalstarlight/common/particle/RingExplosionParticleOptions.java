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

public record RingExplosionParticleOptions(Vector3f fromColor, Vector3f toColor, float scale) implements ParticleOptions {
	public static final RingExplosionParticleOptions LUNAR = new RingExplosionParticleOptions(new Vector3f(66, 66, 115), new Vector3f(32, 32, 64), 10);
	public static final RingExplosionParticleOptions SOUL = new RingExplosionParticleOptions(new Vector3f(96, 245, 250), new Vector3f(131, 140, 141), 3);

	public static final MapCodec<RingExplosionParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(RingExplosionParticleOptions::fromColor),
		ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(RingExplosionParticleOptions::toColor),
		Codec.FLOAT.fieldOf("scale").forGetter(RingExplosionParticleOptions::scale)
	).apply(instance, RingExplosionParticleOptions::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, RingExplosionParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

	public ParticleType<RingExplosionParticleOptions> getType() {
		return ESParticles.RING_EXPLOSION.get();
	}
}
