package cn.leolezury.eternalstarlight.forge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.DamageTypeInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ESDamageTypeTagsProvider extends TagsProvider<DamageType> {
    public ESDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, EternalStarlight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(DamageTypeTags.BYPASSES_ARMOR).add(
                DamageTypeInit.ETHER,
                DamageTypeInit.CRYSTALLINE_INFECTION,
                DamageTypeInit.POISON
        );
        tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(
                DamageTypeInit.ETHER,
                DamageTypeInit.CRYSTALLINE_INFECTION,
                DamageTypeInit.POISON
        );
        tag(DamageTypeTags.BYPASSES_SHIELD).add(
                DamageTypeInit.ETHER,
                DamageTypeInit.CRYSTALLINE_INFECTION,
                DamageTypeInit.POISON
        );
        tag(DamageTypeTags.NO_IMPACT).add(
                DamageTypeInit.POISON
        );
        tag(DamageTypeTags.IS_PROJECTILE).add(
                DamageTypeInit.SHATTERED_BLADE
        );
    }
}
