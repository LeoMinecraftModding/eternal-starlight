package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.particle.lightning.LightningParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class ESParticles {
    public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(Registries.PARTICLE_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> STARLIGHT = PARTICLE_TYPES.register("starlight", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> SCARLET_LEAVES = PARTICLE_TYPES.register("scarlet_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> ENERGY = PARTICLE_TYPES.register("energy", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> POISON = PARTICLE_TYPES.register("poison", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, ParticleType<LightningParticleOptions>> LIGHTNING = PARTICLE_TYPES.register("lightning", () -> new ParticleType<>(false, LightningParticleOptions.DESERIALIZER) {
        @Override
        public Codec<LightningParticleOptions> codec() {
            return LightningParticleOptions.CODEC;
        }
    });
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> BLADE_SHOCKWAVE = PARTICLE_TYPES.register("blade_shockwave", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> AMARAMBER_FLAME = PARTICLE_TYPES.register("amaramber_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> FLAME_SMOKE = PARTICLE_TYPES.register("flame_smoke", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> ENERGY_EXPLOSION = PARTICLE_TYPES.register("energy_explosion", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> ENERGIZED_FLAME_SMOKE = PARTICLE_TYPES.register("energized_flame_smoke", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> ENERGIZED_FLAME_SPIT = PARTICLE_TYPES.register("energized_flame_spit", () -> new SimpleParticleType(false));
    public static void loadClass() {}
}
