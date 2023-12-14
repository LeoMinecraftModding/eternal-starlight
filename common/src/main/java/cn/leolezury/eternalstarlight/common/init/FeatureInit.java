package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.feature.*;
import cn.leolezury.eternalstarlight.common.world.gen.feature.coral.ESCoralClawFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.coral.ESCoralMushroomFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.coral.ESCoralTreeFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.structure.CursedGardenExtraHeightFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.DeadLunarTreeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FeatureInit {
    public static final RegistrationProvider<Feature<?>> FEATURES = RegistrationProvider.get(Registries.FEATURE, EternalStarlight.MOD_ID);
    public static final RegistryObject<Feature<?>, Feature<ESLakeFeature.Configuration>> LAKE = FEATURES.register("lake", () -> new ESLakeFeature(ESLakeFeature.Configuration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> STONE_SPIKE = FEATURES.register("stone_spike", () -> new StoneSpikeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> STARLIGHT_CRYSTAL = FEATURES.register("starlight_crystal", () -> new StarlightCrystalFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<FallenLogFeature.Configuration>> FALLEN_LOG = FEATURES.register("fallen_log", () -> new FallenLogFeature(FallenLogFeature.Configuration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> SWAMP_WATER = FEATURES.register("swamp_water", () -> new SwampWaterFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> KELP = FEATURES.register("kelp", () -> new ESKelpFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> CORAL_CLAW = FEATURES.register("coral_claw", () -> new ESCoralClawFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> CORAL_MUSHROOM = FEATURES.register("coral_mushroom", () -> new ESCoralMushroomFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> CORAL_TREE = FEATURES.register("coral_tree", () -> new ESCoralTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> DEAD_LUNAR_TREE = FEATURES.register("dead_lunar_tree", () -> new DeadLunarTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> CURSED_GARDEN_EXTRA_HEIGHT = FEATURES.register("cursed_garden_extra_height", () -> new CursedGardenExtraHeightFeature(NoneFeatureConfiguration.CODEC));
    public static void loadClass() {}
}
