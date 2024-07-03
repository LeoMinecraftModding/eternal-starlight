package cn.leolezury.eternalstarlight.forge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESEnchantments;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESEnchantmentTagsProvider extends EnchantmentTagsProvider {
	public ESEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		tag(ESTags.Enchantments.GOLEM_FORGE_LOOT).add(
			ESEnchantments.FEARLESS
		);
		tag(ESTags.Enchantments.CURSED_GARDEN_LOOT).add(
			ESEnchantments.FEARLESS,
			ESEnchantments.POISONING,
			ESEnchantments.SOUL_SNATCHER
		);
		tag(EnchantmentTags.ARMOR_EXCLUSIVE).add(
			ESEnchantments.POISONING
		);
		tag(EnchantmentTags.TREASURE).add(
			ESEnchantments.POISONING,
			ESEnchantments.FEARLESS,
			ESEnchantments.SOUL_SNATCHER
		);
	}
}
