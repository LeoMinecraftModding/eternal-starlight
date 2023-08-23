package cn.leolezury.eternalstarlight.datagen.provider;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.datagen.generator.ConfiguredFeatureGenerator;
import cn.leolezury.eternalstarlight.datagen.generator.DamageTypeGenerator;
import cn.leolezury.eternalstarlight.datagen.generator.PlacedFeatureGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SLDataProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatureGenerator::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatureGenerator::bootstrap)
            .add(Registries.DAMAGE_TYPE, DamageTypeGenerator::bootstrap);

    public SLDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(EternalStarlight.MOD_ID));
    }
}