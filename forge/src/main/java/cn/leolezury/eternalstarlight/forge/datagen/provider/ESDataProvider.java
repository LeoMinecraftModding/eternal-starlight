package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ESDataProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_CARVER, ConfiguredWorldCarverInit::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatureInit::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatureInit::bootstrap)
            .add(Registries.BIOME, BiomeInit::bootstrap)
            .add(Registries.NOISE_SETTINGS, DimensionInit::bootstrapNoiseSettings)
            .add(Registries.DIMENSION_TYPE, DimensionInit::bootstrapDimType)
            .add(Registries.LEVEL_STEM, DimensionInit::bootstrapLevelStem)
            .add(Registries.DAMAGE_TYPE, DamageTypeInit::bootstrap)
            .add(Registries.TRIM_MATERIAL, TrimMaterialInit::bootstrap)
            // custom
            .add(ESRegistries.BIOME_DATA, BiomeDataInit::bootstrap)
            .add(ESRegistries.DATA_TRANSFORMER, DataTransformerInit::bootstrap)
            .add(ESRegistries.BOARWARF_TYPE, BoarwarfTypeInit::bootstrap)
            .add(ESRegistries.ASTRAL_GOLEM_MATERIAL, AstralGolemMaterialInit::bootstrap)
            .add(ESRegistries.CREST, CrestInit::bootstrap);

    public ESDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(EternalStarlight.MOD_ID, "minecraft"));
    }
}