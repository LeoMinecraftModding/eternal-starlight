package cn.leolezury.eternalstarlight.common.particle;

import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public record RippleParticleOptions(ParticleType<RippleParticleOptions> type, SmoothSegmentedValue radius, SmoothSegmentedValue width, Vector3f color, int lifetime) implements ParticleOptions {
	public static final Vector3f PURPLE = new Vector3f(222f / 255f, 112f / 255f, 1);
	public static final Vector3f GOLD = new Vector3f(1, 1, 116f / 255f);
	public static final Vector3f ORANGE = new Vector3f(1, 213 / 255f, 74 / 255f);
	public static final Vector3f WHITE = new Vector3f(1, 1, 1);
	public static final Vector3f LIGHT_BLUE = new Vector3f(178 / 255f, 210 / 255f, 247 / 255f);
	public static final Vector3f DEEP_BLUE = new Vector3f(56 / 255f, 122 / 255f, 203 / 255f);

	public static void addFlareExplosionRippleParticles(ServerLevel level, double x, double y, double z, RandomSource random, float scale, float lifeScale, Vector3f flareColorA, Vector3f flareColorB) {
		RippleParticleOptions whiteRipple = new RippleParticleOptions(ESParticles.RIPPLE.get(),
			SmoothSegmentedValue.of(Easing.OUT_QUAD, 0, 0.5f * scale, 0.25f).add(Easing.OUT_QUAD, 0.5f * scale, 0.6f * scale, 0.75f),
			SmoothSegmentedValue.of(Easing.IDENTITY, 0.3f * scale, 0.3f * scale, 0.3f).add(Easing.OUT_QUAD, 0.3f * scale, 0, 0.7f),
			WHITE, Math.round(12 * lifeScale));
		ESPlatform.INSTANCE.sendToAllClients(level, new ParticlePacket(whiteRipple, x + (random.nextFloat() - random.nextFloat()) * 0.15F, y + (random.nextFloat() - random.nextFloat()) * 0.15F, z + (random.nextFloat() - random.nextFloat()) * 0.15F, 0, 0, 0));

		RippleParticleOptions rippleA = new RippleParticleOptions(ESParticles.RIPPLE.get(),
			SmoothSegmentedValue.of(Easing.OUT_QUAD, 0, 1.1f * scale, 0.3f).add(Easing.OUT_QUAD, 1.1f * scale, 2.1f * scale, 0.7f),
			SmoothSegmentedValue.of(Easing.IDENTITY, 0.4f * scale, 0.5f * scale, 0.4f).add(Easing.OUT_QUAD, 0.5f * scale, 0, 0.6f),
			flareColorA, Math.round(14 * lifeScale));
		ESPlatform.INSTANCE.sendToAllClients(level, new ParticlePacket(rippleA, x + (random.nextFloat() - random.nextFloat()) * 0.15F, y + (random.nextFloat() - random.nextFloat()) * 0.15F, z + (random.nextFloat() - random.nextFloat()) * 0.15F, 0, 0, 0));

		RippleParticleOptions rippleB = new RippleParticleOptions(ESParticles.RIPPLE.get(),
			SmoothSegmentedValue.of(Easing.OUT_QUAD, 0, 1.1f * scale, 0.2f).add(Easing.OUT_QUAD, 1.1f * scale, 2.1f * scale, 0.8f),
			SmoothSegmentedValue.of(Easing.IDENTITY, 0.2f * scale, 0.3f * scale, 0.3f).add(Easing.OUT_QUAD, 0.3f * scale, 0, 0.7f),
			flareColorB, Math.round(17 * lifeScale));
		ESPlatform.INSTANCE.sendToAllClients(level, new ParticlePacket(rippleB, x + (random.nextFloat() - random.nextFloat()) * 0.15F, y + (random.nextFloat() - random.nextFloat()) * 0.15F, z + (random.nextFloat() - random.nextFloat()) * 0.15F, 0, 0, 0));

		for (int i = 0; i < 12; i++) {
			Vec3 speed = new Vec3((random.nextFloat() - random.nextFloat()) * 0.1F, (random.nextFloat() - random.nextFloat()) * 0.1F, (random.nextFloat() - random.nextFloat()) * 0.1F).normalize().scale(scale);
			ESPlatform.INSTANCE.sendToAllClients(level, new ParticlePacket(new ExplosionShockParticleOptions(flareColorA, flareColorB, 1, 0.06f, lifeScale), x + speed.x * 0.6, y + speed.y * 0.6, z + speed.z * 0.6, speed.x, speed.y, speed.z));
		}
	}

	public static void addFlareExplosionRippleParticles(ServerLevel level, double x, double y, double z, RandomSource random, float scale, Vector3f flareColorA, Vector3f flareColorB) {
		addFlareExplosionRippleParticles(level, x, y, z, random, scale, 1, flareColorA, flareColorB);
	}

	public static void addFlareExplosionRippleParticles(ServerLevel level, double x, double y, double z, RandomSource random) {
		addFlareExplosionRippleParticles(level, x, y, z, random, 1, PURPLE, GOLD);
	}

	public static void addBlueExplosionRippleParticles(ServerLevel level, double x, double y, double z, RandomSource random) {
		addFlareExplosionRippleParticles(level, x, y, z, random, 1.2f, DEEP_BLUE, LIGHT_BLUE);
	}

	public static MapCodec<RippleParticleOptions> codec(ParticleType<RippleParticleOptions> type) {
		return RecordCodecBuilder.mapCodec((instance) -> instance.group(
			SmoothSegmentedValue.CODEC.fieldOf("radius").forGetter(RippleParticleOptions::radius),
			SmoothSegmentedValue.CODEC.fieldOf("width").forGetter(RippleParticleOptions::width),
			ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(RippleParticleOptions::color),
			Codec.INT.fieldOf("lifetime").forGetter(RippleParticleOptions::lifetime)
		).apply(instance, (radius, width, color, lifetime) -> new RippleParticleOptions(type, radius, width, color, lifetime)));
	}

	public static StreamCodec<RegistryFriendlyByteBuf, RippleParticleOptions> streamCodec(ParticleType<RippleParticleOptions> type) {
		return ByteBufCodecs.fromCodecWithRegistries(codec(type).codec());
	}

	@Override
	public ParticleType<RippleParticleOptions> getType() {
		return type();
	}
}
