package cn.leolezury.eternalstarlight.common.particle;

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

public record ESExplosionParticleOptions(Vector3f fromColor, Vector3f toColor) implements ParticleOptions {
	public static final ESExplosionParticleOptions ENERGY = new ESExplosionParticleOptions(new Vector3f(255, 255, 255), new Vector3f(129, 212, 250));
	public static final ESExplosionParticleOptions LAVA = new ESExplosionParticleOptions(new Vector3f(217, 168, 74), new Vector3f(174, 76, 18));
	public static final ESExplosionParticleOptions AETHERSENT = new ESExplosionParticleOptions(new Vector3f(255, 255, 255), new Vector3f(233, 173, 237));
	public static final ESExplosionParticleOptions LUNAR = new ESExplosionParticleOptions(new Vector3f(66, 66, 115), new Vector3f(32, 32, 64));

	public static final MapCodec<ESExplosionParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(ESExplosionParticleOptions::fromColor),
		ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(ESExplosionParticleOptions::toColor)
	).apply(instance, ESExplosionParticleOptions::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, ESExplosionParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

	@Override
	public ParticleType<ESExplosionParticleOptions> getType() {
		return ESParticles.EXPLOSION.get();
	}
}
