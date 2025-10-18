package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.particle.*;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class ESParticles {
	public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(Registries.PARTICLE_TYPE, EternalStarlight.ID);
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> STARLIGHT = PARTICLE_TYPES.register("starlight", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> STARDUST = PARTICLE_TYPES.register("stardust", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> FIREFLY = PARTICLE_TYPES.register("firefly", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> SCARLET_LEAVES = PARTICLE_TYPES.register("scarlet_leaves", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> CRADLEWOOD_LEAVES = PARTICLE_TYPES.register("cradlewood_leaves", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> SHADEGRIEVE_LEAVES = PARTICLE_TYPES.register("shadegrieve_leaves", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> SPIRAL_KELP_LEAVES = PARTICLE_TYPES.register("spiral_kelp_leaves", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> FALLING_RED_CRYSTAL_MOSS = PARTICLE_TYPES.register("falling_red_crystal_moss", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> FALLING_BLUE_CRYSTAL_MOSS = PARTICLE_TYPES.register("falling_blue_crystal_moss", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> ENERGY = PARTICLE_TYPES.register("energy", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> ELECTRIC_SPARK = PARTICLE_TYPES.register("electric_spark", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> LUNAR_SLASH = PARTICLE_TYPES.register("lunar_slash", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> BLADE_SHOCKWAVE = PARTICLE_TYPES.register("blade_shockwave", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> CRYSTALLIZED_MOTH_SONAR = PARTICLE_TYPES.register("crystallized_moth_sonar", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> AMARAMBER_FLAME = PARTICLE_TYPES.register("amaramber_flame", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, ParticleType<ESExplosionParticleOptions>> EXPLOSION = PARTICLE_TYPES.register("explosion", () -> new ParticleType<>(false) {
		@Override
		public MapCodec<ESExplosionParticleOptions> codec() {
			return ESExplosionParticleOptions.CODEC;
		}

		@Override
		public StreamCodec<? super RegistryFriendlyByteBuf, ESExplosionParticleOptions> streamCodec() {
			return ESExplosionParticleOptions.STREAM_CODEC;
		}
	});
	public static final RegistryObject<ParticleType<?>, ParticleType<ESSmokeParticleOptions>> SMOKE = PARTICLE_TYPES.register("smoke", () -> new ParticleType<>(false) {
		@Override
		public MapCodec<ESSmokeParticleOptions> codec() {
			return ESSmokeParticleOptions.CODEC;
		}

		@Override
		public StreamCodec<? super RegistryFriendlyByteBuf, ESSmokeParticleOptions> streamCodec() {
			return ESSmokeParticleOptions.STREAM_CODEC;
		}
	});
	public static final RegistryObject<ParticleType<?>, ParticleType<RingExplosionParticleOptions>> RING_EXPLOSION = PARTICLE_TYPES.register("ring_explosion", () -> new ParticleType<>(false) {
		@Override
		public MapCodec<RingExplosionParticleOptions> codec() {
			return RingExplosionParticleOptions.CODEC;
		}

		@Override
		public StreamCodec<? super RegistryFriendlyByteBuf, RingExplosionParticleOptions> streamCodec() {
			return RingExplosionParticleOptions.STREAM_CODEC;
		}
	});
	public static final RegistryObject<ParticleType<?>, ParticleType<OrbitalTrailParticleOptions>> ORBITAL_TRAIL = PARTICLE_TYPES.register("orbital_trail", () -> new ParticleType<>(false) {
		@Override
		public MapCodec<OrbitalTrailParticleOptions> codec() {
			return OrbitalTrailParticleOptions.CODEC;
		}

		@Override
		public StreamCodec<? super RegistryFriendlyByteBuf, OrbitalTrailParticleOptions> streamCodec() {
			return OrbitalTrailParticleOptions.STREAM_CODEC;
		}
	});
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> METEOR = PARTICLE_TYPES.register("meteor", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, ParticleType<ESGlowParticleOptions>> GLOW = PARTICLE_TYPES.register("glow", () -> new ParticleType<>(false) {
		@Override
		public MapCodec<ESGlowParticleOptions> codec() {
			return ESGlowParticleOptions.CODEC;
		}

		@Override
		public StreamCodec<? super RegistryFriendlyByteBuf, ESGlowParticleOptions> streamCodec() {
			return ESGlowParticleOptions.STREAM_CODEC;
		}
	});
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> AETHERSENT_SMOKE = PARTICLE_TYPES.register("aethersent_smoke", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> SMOKE_TRAIL = PARTICLE_TYPES.register("smoke_trail", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> AETHERSENT_EXPLOSION = PARTICLE_TYPES.register("aethersent_explosion", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> ASHEN_SNOW = PARTICLE_TYPES.register("ashen_snow", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> ORBITAL_ASHEN_SNOW = PARTICLE_TYPES.register("orbital_ashen_snow", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, ParticleType<ExplosionShockParticleOptions>> EXPLOSION_SHOCK = PARTICLE_TYPES.register("explosion_shock", () -> new ParticleType<>(false) {
		@Override
		public MapCodec<ExplosionShockParticleOptions> codec() {
			return ExplosionShockParticleOptions.CODEC;
		}

		@Override
		public StreamCodec<? super RegistryFriendlyByteBuf, ExplosionShockParticleOptions> streamCodec() {
			return ExplosionShockParticleOptions.STREAM_CODEC;
		}
	});
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> ROOKFISH_INK = PARTICLE_TYPES.register("rookfish_ink", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> AMARAMBER_WAX_ON = PARTICLE_TYPES.register("amaramber_wax_on", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> DRIPPING_MUD = PARTICLE_TYPES.register("dripping_mud", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> FALLING_MUD = PARTICLE_TYPES.register("falling_mud", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> LANDING_MUD = PARTICLE_TYPES.register("landing_mud", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> ALLIED = PARTICLE_TYPES.register("allied", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> PUNGENCY_FRUIT_SMOKE = PARTICLE_TYPES.register("pungency_fruit_smoke", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> STARFIRE = PARTICLE_TYPES.register("starfire", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> STARFIRE_EXPLOSION = PARTICLE_TYPES.register("starfire_explosion", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> STARFIRE_EXPLOSION_SMALL = PARTICLE_TYPES.register("starfire_explosion_small", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> BIG_EXPLOSION = PARTICLE_TYPES.register("big_explosion", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> ADVANCED_GLOW = PARTICLE_TYPES.register("advanced_glow", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<?>, SimpleParticleType> SHINE = PARTICLE_TYPES.register("shine", () -> new SimpleParticleType(false));

	public static void loadClass() {
	}
}
