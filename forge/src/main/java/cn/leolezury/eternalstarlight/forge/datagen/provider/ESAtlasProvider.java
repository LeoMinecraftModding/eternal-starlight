package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ESAtlasProvider extends SpriteSourceProvider {
    public ESAtlasProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
    }

    @Override
    protected void gather() {
        this.atlas(SHIELD_PATTERNS_ATLAS).addSource(new SingleFile(EternalStarlight.id("entity/glacite_shield"), Optional.empty()));
    }
}
