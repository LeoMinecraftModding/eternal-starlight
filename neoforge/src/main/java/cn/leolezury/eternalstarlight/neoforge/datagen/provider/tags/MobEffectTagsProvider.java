package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public abstract class MobEffectTagsProvider extends IntrinsicHolderTagsProvider<MobEffect> {
	public MobEffectTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, Registries.MOB_EFFECT, lookupProvider, (effect) -> {
			return BuiltInRegistries.MOB_EFFECT.getResourceKey(effect).orElseThrow();
		}, modId, existingFileHelper);
	}
}