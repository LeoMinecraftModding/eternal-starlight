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

public record ExplosionShockParticleOptions(Vector3f fromColor, Vector3f toColor, float lengthScale, float width, float lifeScale) implements ParticleOptions {
	public static final ExplosionShockParticleOptions BLAST = fromIntColor(new Vector3f(255, 255, 255), new Vector3f(255, 255, 255), 0.3f, 0.06f, 0.5f);
	public static final ExplosionShockParticleOptions AETHERSENT = fromIntColor(new Vector3f(255, 255, 255), new Vector3f(233, 173, 237), 1, 0.06f, 1);
	public static final ExplosionShockParticleOptions CRESCENT_SPEAR = fromIntColor(new Vector3f(161, 223, 255), new Vector3f(124, 164, 213), 1, 0.06f, 1);
	public static final ExplosionShockParticleOptions FROZEN = fromIntColor(new Vector3f(121, 178, 209), new Vector3f(192, 251, 255), 1, 0.06f, 1);
	public static final ExplosionShockParticleOptions CRYSTAL = fromIntColor(new Vector3f(196, 80, 132), new Vector3f(126, 197, 203), 1, 0.06f, 1);
	public static final ExplosionShockParticleOptions FLARE = fromIntColor(new Vector3f(222, 112, 255), new Vector3f(255, 255, 116), 1, 0.06f, 1);
	public static final ExplosionShockParticleOptions DEATH = fromIntColor(new Vector3f(220, 53, 69), new Vector3f(237, 38, 85), 1, 0.06f, 1);
	public static final ExplosionShockParticleOptions ENERGY = fromIntColor(new Vector3f(255, 255, 255), new Vector3f(129, 212, 250), 0.5f, 0.1f, 0.6f);
	public static final ExplosionShockParticleOptions ENERGY_SMALL = fromIntColor(new Vector3f(255, 255, 255), new Vector3f(129, 212, 250), 0.07f, 0.02f, 0.6f);
	public static final ExplosionShockParticleOptions ETHER = fromIntColor(new Vector3f(209, 255, 225), new Vector3f(255, 255, 255), 1, 0.06f, 1);

	public static ExplosionShockParticleOptions fromIntColor(Vector3f fromColor, Vector3f toColor, float lengthScale, float width, float lifeScale) {
		return new ExplosionShockParticleOptions(new Vector3f(fromColor).div(255f), new Vector3f(toColor).div(255f), lengthScale, width, lifeScale);
	}

	public static final MapCodec<ExplosionShockParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		ExtraCodecs.VECTOR3F.fieldOf("from_color").forGetter(ExplosionShockParticleOptions::fromColor),
		ExtraCodecs.VECTOR3F.fieldOf("to_color").forGetter(ExplosionShockParticleOptions::toColor),
		Codec.FLOAT.fieldOf("length_scale").forGetter(ExplosionShockParticleOptions::lengthScale),
		Codec.FLOAT.fieldOf("width").forGetter(ExplosionShockParticleOptions::width),
		Codec.FLOAT.fieldOf("life_scale").forGetter(ExplosionShockParticleOptions::lifeScale)
	).apply(instance, ExplosionShockParticleOptions::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, ExplosionShockParticleOptions> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

	@Override
	public ParticleType<ExplosionShockParticleOptions> getType() {
		return ESParticles.EXPLOSION_SHOCK.get();
	}
}
