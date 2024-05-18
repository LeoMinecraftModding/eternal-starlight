package cn.leolezury.eternalstarlight.forge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
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
        super(output, Registries.DAMAGE_TYPE, lookupProvider, EternalStarlight.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(DamageTypeTags.BYPASSES_ARMOR).add(
                ESDamageTypes.ETHER,
                ESDamageTypes.CRYSTALLINE_INFECTION,
                ESDamageTypes.POISON
        );
        tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(
                ESDamageTypes.ETHER,
                ESDamageTypes.CRYSTALLINE_INFECTION,
                ESDamageTypes.POISON
        );
        tag(DamageTypeTags.BYPASSES_SHIELD).add(
                ESDamageTypes.ETHER,
                ESDamageTypes.CRYSTALLINE_INFECTION,
                ESDamageTypes.POISON
        );
        tag(DamageTypeTags.NO_IMPACT).add(
                ESDamageTypes.POISON,
                ESDamageTypes.ENERGIZED_FLAME
        );
        tag(DamageTypeTags.IS_PROJECTILE).add(
                ESDamageTypes.SHATTERED_BLADE
        );
    }
}
