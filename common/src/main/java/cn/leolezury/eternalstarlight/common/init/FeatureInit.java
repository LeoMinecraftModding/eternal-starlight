package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.feature.*;
import cn.leolezury.eternalstarlight.common.world.feature.structure.CursedGardenExtraHeightFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FeatureInit {
    public static final RegistrationProvider<Feature<?>> FEATURES = RegistrationProvider.get(Registries.FEATURE, EternalStarlight.MOD_ID);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CURSED_GARDEN_EXTRA_HEIGHT = FEATURES.register("cursed_garden_extra_height", () -> new CursedGardenExtraHeightFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> STONE_SPIKE = FEATURES.register("stone_spike", () -> new StoneSpikeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> STARLIGHT_CRYSTAL = FEATURES.register("starlight_crystal", () -> new StarlightCrystalFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<FallenLogFeature.Configuration>> FALLEN_LOG = FEATURES.register("fallen_log", () -> new FallenLogFeature(FallenLogFeature.Configuration.CODEC));
    public static final RegistryObject<Feature<BetterLakeFeature.Configuration>> BETTER_LAKE = FEATURES.register("better_lake", () -> new BetterLakeFeature(BetterLakeFeature.Configuration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SWAMP_WATER = FEATURES.register("swamp_water", () -> new SwampWaterFeature(NoneFeatureConfiguration.CODEC));
    public static void postRegistry() {}
}
