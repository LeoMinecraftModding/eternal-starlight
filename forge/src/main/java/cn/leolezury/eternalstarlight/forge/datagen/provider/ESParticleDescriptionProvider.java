package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class ESParticleDescriptionProvider extends ParticleDescriptionProvider {
    public ESParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        spriteSet(ESParticles.STARLIGHT.get(), loc("glitter"), 6, false);
        spriteSet(ESParticles.SCARLET_LEAVES.get(), loc("scarlet_leaves"), 5, false);
        sprite(ESParticles.ENERGY.get(), loc("energy"));
        sprite(ESParticles.POISON.get(), loc("poison"));
        spriteSet(ESParticles.BLADE_SHOCKWAVE.get(), mcLoc("sweep"), 8, false);
        sprite(ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), loc("crystallized_moth_sonar"));
        sprite(ESParticles.AMARAMBER_FLAME.get(), loc("amaramber_flame"));
        spriteSet(ESParticles.FLAME_SMOKE.get(), loc("big_smoke"), 12, false);
        spriteSet(ESParticles.ENERGY_EXPLOSION.get(), mcLoc("explosion"), 16, false);
        spriteSet(ESParticles.LAVA_EXPLOSION.get(), mcLoc("explosion"), 16, false);
        spriteSet(ESParticles.ENERGIZED_FLAME_SMOKE.get(), loc("big_smoke"), 12, false);
        spriteSet(ESParticles.ENERGIZED_FLAME_SPIT.get(), loc("big_smoke"), 12, false);
    }

    private ResourceLocation loc(String s) {
        return EternalStarlight.id(s);
    }

    private ResourceLocation mcLoc(String s) {
        return new ResourceLocation(s);
    }
}
