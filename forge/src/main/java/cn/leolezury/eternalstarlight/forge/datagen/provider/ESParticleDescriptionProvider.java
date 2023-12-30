package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.ParticleInit;
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
        spriteSet(ParticleInit.STARLIGHT.get(), loc("glitter"), 6, false);
        spriteSet(ParticleInit.SCARLET_LEAVES.get(), loc("scarlet_leaves"), 5, false);
        sprite(ParticleInit.ENERGY.get(), loc("energy"));
        sprite(ParticleInit.POISON.get(), loc("poison"));
    }

    private ResourceLocation loc(String s) {
        return new ResourceLocation(EternalStarlight.MOD_ID, s);
    }
}
