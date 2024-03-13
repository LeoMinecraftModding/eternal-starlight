package cn.leolezury.eternalstarlight.forge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ESEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EternalStarlight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(ESTags.EntityTypes.ROBOTIC).add(
                ESEntities.FREEZE.get(),
                ESEntities.STARLIGHT_GOLEM.get()
        );
        tag(EntityTypeTags.SKELETONS).add(
                ESEntities.LONESTAR_SKELETON.get()
        );
        tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(
                ESEntities.ASTRAL_GOLEM.get(),
                ESEntities.CRYSTALLIZED_MOTH.get(),
                ESEntities.SHIMMER_LACEWING.get(),
                ESEntities.GRIMSTONE_GOLEM.get()
        );
        tag(ESTags.EntityTypes.ABYSSAL_FIRE_IMMUNE).add(
                ESEntities.LUMINOFISH.get(),
                ESEntities.LUMINARIS.get()
        );
    }
}
