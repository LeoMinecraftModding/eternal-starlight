package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESEnchantments;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESEnchantmentTagsProvider extends EnchantmentTagsProvider {
	public ESEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) {
		tag(ESTags.Enchantments.GOLEM_FORGE_LOOT)
			.add(
				ESEnchantments.FEARLESS,
				ESEnchantments.PRECISION,
				ESEnchantments.HOMING,
				ESEnchantments.GATHERING
			);
		tag(ESTags.Enchantments.CURSED_GARDEN_LOOT)
			.add(
				ESEnchantments.FEARLESS,
				ESEnchantments.POISONING,
				ESEnchantments.SOUL_SNATCHER,
				ESEnchantments.TRACING
			);
		tag(ESTags.Enchantments.PREVENTS_STARFIRE_BIRD_SPAWNS_WHEN_MINING)
			.add(
				Enchantments.SILK_TOUCH
			);
		tag(ESTags.Enchantments.SEEDS_LAUNCHER_EXCLUSIVE)
			.add(
				ESEnchantments.OVERHEAT,
				ESEnchantments.GLACIAL_SOWING
			);
		tag(EnchantmentTags.TOOLTIP_ORDER)
			.add(
				ESEnchantments.FEARLESS,
				ESEnchantments.POISONING,
				ESEnchantments.SOUL_SNATCHER,
				ESEnchantments.TRACING,
				ESEnchantments.TEARING,
				ESEnchantments.OVERHEAT,
				ESEnchantments.GLACIAL_SOWING,
				ESEnchantments.FERTILE,
				ESEnchantments.PRECISION,
				ESEnchantments.HOMING,
				ESEnchantments.GATHERING,
				ESEnchantments.SWIFT_LASH,
				ESEnchantments.ABYSSAL_TOUCH
			);
		tag(EnchantmentTags.TREASURE)
			.add(
				ESEnchantments.POISONING,
				ESEnchantments.FEARLESS,
				ESEnchantments.SOUL_SNATCHER
			);
		tag(EnchantmentTags.NON_TREASURE)
			.add(
				ESEnchantments.TRACING,
				ESEnchantments.TEARING,
				ESEnchantments.OVERHEAT,
				ESEnchantments.GLACIAL_SOWING,
				ESEnchantments.FERTILE,
				ESEnchantments.PRECISION,
				ESEnchantments.HOMING,
				ESEnchantments.GATHERING,
				ESEnchantments.SWIFT_LASH,
				ESEnchantments.ABYSSAL_TOUCH
			);
	}
}
