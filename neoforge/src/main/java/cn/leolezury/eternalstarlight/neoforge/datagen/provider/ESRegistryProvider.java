package cn.leolezury.eternalstarlight.neoforge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ESRegistryProvider extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
		.add(Registries.ENCHANTMENT, ESEnchantments::bootstrap)
		.add(Registries.CONFIGURED_CARVER, ESConfiguredWorldCarvers::bootstrap)
		.add(Registries.CONFIGURED_FEATURE, ESConfiguredFeatures::bootstrap)
		.add(Registries.PLACED_FEATURE, ESPlacedFeatures::bootstrap)
		.add(Registries.BIOME, ESBiomes::bootstrap)
		.add(Registries.NOISE_SETTINGS, ESDimensions::bootstrapNoiseSettings)
		.add(Registries.DIMENSION_TYPE, ESDimensions::bootstrapDimType)
		.add(Registries.LEVEL_STEM, ESDimensions::bootstrapLevelStem)
		.add(Registries.PROCESSOR_LIST, ESTemplatePools::bootstrapProcessors)
		.add(Registries.TEMPLATE_POOL, ESTemplatePools::bootstrap)
		.add(Registries.STRUCTURE, ESStructures::bootstrap)
		.add(Registries.STRUCTURE_SET, ESStructures::bootstrapSets)
		.add(Registries.DAMAGE_TYPE, ESDamageTypes::bootstrap)
		.add(Registries.TRIM_MATERIAL, ESTrimMaterials::bootstrap)
		.add(Registries.TRIM_PATTERN, ESTrimPatterns::bootstrap)
		// custom
		.add(ESRegistries.BIOME_DATA, ESBiomeData::bootstrap)
		.add(ESRegistries.DATA_TRANSFORMER, ESDataTransformers::bootstrap)
		.add(ESRegistries.BOARWARF_TYPE, ESBoarwarfTypes::bootstrap)
		.add(ESRegistries.ASTRAL_GOLEM_MATERIAL, ESAstralGolemMaterials::bootstrap)
		.add(ESRegistries.CREST, ESCrests::bootstrap);

	public ESRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, BUILDER, Set.of(EternalStarlight.ID, "minecraft"));
	}
}