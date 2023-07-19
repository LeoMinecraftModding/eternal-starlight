package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.particle.lightning.LightningParticleOptions;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EternalStarlight.MOD_ID);
    public static final RegistryObject<ParticleType<LightningParticleOptions>> LIGHTNING = PARTICLE_TYPES.register("lightning",() -> new ParticleType<LightningParticleOptions>(false, LightningParticleOptions.DESERIALIZER) {
        @Override
        public Codec<LightningParticleOptions> codec() {
            return LightningParticleOptions.CODEC;
        }
    });
    public static final RegistryObject<SimpleParticleType> STARLIGHT = PARTICLE_TYPES.register("starlight",() -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> POISON = PARTICLE_TYPES.register("poison",() -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ENERGY = PARTICLE_TYPES.register("energy",() -> new SimpleParticleType(false));
}
