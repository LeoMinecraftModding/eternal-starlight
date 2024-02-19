package cn.leolezury.eternalstarlight.forge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESBiomeTagsProvider extends BiomeTagsProvider {
    public ESBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EternalStarlight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        tag(ESTags.Biomes.HAS_GOLEM_FORGE).add(
                ESBiomes.STARLIGHT_FOREST,
                ESBiomes.STARLIGHT_DENSE_FOREST,
                ESBiomes.STARLIGHT_PERMAFROST_FOREST
        );
    }
}