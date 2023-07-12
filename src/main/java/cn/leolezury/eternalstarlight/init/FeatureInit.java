package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.world.feature.CursedGardenExtraHeightFeature;
import cn.leolezury.eternalstarlight.world.feature.FallenLogFeature;
import cn.leolezury.eternalstarlight.world.feature.StarlightCrystalFeature;
import cn.leolezury.eternalstarlight.world.feature.StoneSpikeFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FeatureInit {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, EternalStarlight.MOD_ID);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CURSED_GARDEN_EXTRA_HEIGHT = FEATURES.register("cursed_garden_extra_height", () -> new CursedGardenExtraHeightFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> STONE_SPIKE = FEATURES.register("stone_spike", () -> new StoneSpikeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> STARLIGHT_CRYSTAL = FEATURES.register("starlight_crystal", () -> new StarlightCrystalFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<FallenLogFeature.Configuration>> FALLEN_LOG = FEATURES.register("fallen_log", () -> new FallenLogFeature(FallenLogFeature.Configuration.CODEC));
}
