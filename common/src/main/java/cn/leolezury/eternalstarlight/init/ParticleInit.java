package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.particle.lightning.LightningParticleOptions;
import cn.leolezury.eternalstarlight.mixins.access.SimpleParticleTypeAccess;
import cn.leolezury.eternalstarlight.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.util.register.RegistryObject;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class ParticleInit {
    public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(Registries.PARTICLE_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<ParticleType<LightningParticleOptions>> LIGHTNING = PARTICLE_TYPES.register("lightning",() -> new ParticleType<LightningParticleOptions>(false, LightningParticleOptions.DESERIALIZER) {
        @Override
        public Codec<LightningParticleOptions> codec() {
            return LightningParticleOptions.CODEC;
        }
    });
    public static final RegistryObject<SimpleParticleType> STARLIGHT = PARTICLE_TYPES.register("starlight",() -> SimpleParticleTypeAccess.create(false));
    public static final RegistryObject<SimpleParticleType> POISON = PARTICLE_TYPES.register("poison",() -> SimpleParticleTypeAccess.create(false));
    public static final RegistryObject<SimpleParticleType> ENERGY = PARTICLE_TYPES.register("energy",() -> SimpleParticleTypeAccess.create(false));
    public static void postRegistry() {}
}
