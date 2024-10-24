package cn.leolezury.eternalstarlight.neoforge.datagen;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.neoforge.datagen.provider.*;
import cn.leolezury.eternalstarlight.neoforge.datagen.provider.advancement.ESAdvancementProvider;
import cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot.ESLootProvider;
import cn.leolezury.eternalstarlight.neoforge.datagen.provider.model.ESBlockStateProvider;
import cn.leolezury.eternalstarlight.neoforge.datagen.provider.model.ESItemModelProvider;
import cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = EternalStarlight.ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		generator.addProvider(event.includeClient(), new ESBlockStateProvider(output, helper));
		generator.addProvider(event.includeClient(), new ESItemModelProvider(output, helper));
		generator.addProvider(event.includeClient(), new ESAtlasProvider(output, lookupProvider, helper));
		generator.addProvider(event.includeClient(), new ESParticleDescriptionProvider(output, helper));
		generator.addProvider(event.includeClient(), new ESSoundProvider(output, helper));

		ESBlockTagsProvider blockTagsProvider = new ESBlockTagsProvider(output, lookupProvider, helper);
		generator.addProvider(event.includeServer(), blockTagsProvider);
		generator.addProvider(event.includeServer(), new ESItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), helper));
		generator.addProvider(event.includeServer(), new ESEntityTypeTagsProvider(output, lookupProvider, helper));
		generator.addProvider(event.includeServer(), new ESFluidTagsProvider(output, lookupProvider, helper));
		generator.addProvider(event.includeServer(), new ESMobEffectTagsProvider(output, lookupProvider, helper));

		DatapackBuiltinEntriesProvider registryProvider = new ESRegistryProvider(output, lookupProvider);
		CompletableFuture<HolderLookup.Provider> lookup = registryProvider.getRegistryProvider();
		generator.addProvider(event.includeServer(), registryProvider);
		generator.addProvider(event.includeServer(), new ESDamageTypeTagsProvider(output, lookup, helper));
		generator.addProvider(event.includeServer(), new ESBiomeTagsProvider(output, lookup, helper));
		generator.addProvider(event.includeServer(), new ESStructureTagsProvider(output, lookup, helper));
		generator.addProvider(event.includeServer(), new ESEnchantmentTagsProvider(output, lookup, helper));
		generator.addProvider(event.includeServer(), new ESCrestTagsProvider(output, lookup, helper));
		generator.addProvider(event.includeServer(), new ESPaintingVariantTagsProvider(output, lookup, helper));

		generator.addProvider(event.includeServer(), new ESLootProvider(output, lookup));
		generator.addProvider(event.includeServer(), new ESAdvancementProvider(output, lookup, helper));
		generator.addProvider(event.includeServer(), new ESRecipeProvider(output, lookupProvider));
	}
}
