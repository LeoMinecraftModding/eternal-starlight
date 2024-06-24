package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.forge.datagen.provider.sub.ESBlockLootSubProvider;
import cn.leolezury.eternalstarlight.forge.datagen.provider.sub.ESBossLootSubProvider;
import cn.leolezury.eternalstarlight.forge.datagen.provider.sub.ESChestLootSubProvider;
import cn.leolezury.eternalstarlight.forge.datagen.provider.sub.ESEntityLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ESLootProvider extends LootTableProvider {
	public ESLootProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
		super(packOutput, Set.of(), List.of(
			new LootTableProvider.SubProviderEntry(ESBlockLootSubProvider::new, LootContextParamSets.BLOCK),
			new LootTableProvider.SubProviderEntry(ESEntityLootSubProvider::new, LootContextParamSets.ENTITY),
			new LootTableProvider.SubProviderEntry(ESChestLootSubProvider::new, LootContextParamSets.CHEST),
			new LootTableProvider.SubProviderEntry(ESBossLootSubProvider::new, LootContextParamSets.EMPTY)
		), provider);
	}
}
