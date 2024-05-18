package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.feature.*;
import cn.leolezury.eternalstarlight.common.world.gen.feature.coral.ESCoralClawFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.coral.ESCoralMushroomFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.coral.ESCoralTreeFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.structure.CursedGardenExtraHeightFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.structure.GolemForgeChimneyFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.DeadLunarTreeFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.HugeGlowingMushroomFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ESFeatures {
    public static final RegistrationProvider<Feature<?>> FEATURES = RegistrationProvider.get(Registries.FEATURE, EternalStarlight.ID);
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> ETHER_FLUID_BORDER = FEATURES.register("ether_fluid_border", () -> new EtherFluidBorderFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<ESLakeFeature.Configuration>> LAKE = FEATURES.register("lake", () -> new ESLakeFeature(ESLakeFeature.Configuration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> STONE_SPIKE = FEATURES.register("stone_spike", () -> new StoneSpikeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> GLACITE = FEATURES.register("glacite", () -> new GlaciteFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> HUGE_GLOWING_MUSHROOM = FEATURES.register("huge_glowing_mushroom", () -> new HugeGlowingMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> STARLIGHT_CRYSTAL = FEATURES.register("starlight_crystal", () -> new StarlightCrystalFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<FallenLogFeature.Configuration>> FALLEN_LOG = FEATURES.register("fallen_log", () -> new FallenLogFeature(FallenLogFeature.Configuration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<LeavesPileFeature.Configuration>> LEAVES_PILE = FEATURES.register("leaves_pile", () -> new LeavesPileFeature(LeavesPileFeature.Configuration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> SWAMP_WATER = FEATURES.register("swamp_water", () -> new SwampWaterFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> KELP = FEATURES.register("kelp", () -> new ESKelpFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> ORBFLORA = FEATURES.register("orbflora", () -> new OrbfloraFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> CORAL_CLAW = FEATURES.register("coral_claw", () -> new ESCoralClawFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> CORAL_MUSHROOM = FEATURES.register("coral_mushroom", () -> new ESCoralMushroomFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> CORAL_TREE = FEATURES.register("coral_tree", () -> new ESCoralTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> DEAD_LUNAR_TREE = FEATURES.register("dead_lunar_tree", () -> new DeadLunarTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<AbysslatePatchFeature.Configuration>> ABYSSLATE_PATCH = FEATURES.register("abysslate_patch", () -> new AbysslatePatchFeature(AbysslatePatchFeature.Configuration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> GOLEM_FORGE_CHIMNEY = FEATURES.register("golem_forge_chimney", () -> new GolemForgeChimneyFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> CURSED_GARDEN_EXTRA_HEIGHT = FEATURES.register("cursed_garden_extra_height", () -> new CursedGardenExtraHeightFeature(NoneFeatureConfiguration.CODEC));

    public static void loadClass() {}
}
