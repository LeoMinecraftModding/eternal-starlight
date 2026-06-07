package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.feature.*;
import cn.leolezury.eternalstarlight.common.world.gen.feature.coral.ESCoralClawFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.coral.ESCoralMushroomFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.coral.ESCoralTreeFeature;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class ESFeatures {
	public static final RegistrationProvider<Feature<?>> FEATURES = RegistrationProvider.get(Registries.FEATURE, EternalStarlight.ID);
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> FINAL_MODIFICATION = FEATURES.register("final_modification", () -> new FinalModificationFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<ESLakeFeature.Configuration>> LAKE = FEATURES.register("lake", () -> new ESLakeFeature(ESLakeFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> GLACITE = FEATURES.register("glacite", () -> new GlaciteFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> ICICLE = FEATURES.register("icicle", () -> new IcicleFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<HugeMarimoldFeature.Configuration>> HUGE_MARIMOLD = FEATURES.register("huge_marimold", () -> new HugeMarimoldFeature(HugeMarimoldFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> HUGE_GLOWING_MUSHROOM = FEATURES.register("huge_glowing_mushroom", () -> new HugeGlowingMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> HUGE_SHINING_MUSHROOM = FEATURES.register("huge_shining_mushroom", () -> new HugeShiningMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> BOULDERSHROOM = FEATURES.register("bouldershroom", () -> new BouldershroomFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> STARLIGHT_CRYSTAL = FEATURES.register("starlight_crystal", () -> new StarlightCrystalFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<SpikeFeature.Configuration>> SPIKE = FEATURES.register("spike", () -> new SpikeFeature(SpikeFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<PillarFeature.Configuration>> PILLAR = FEATURES.register("pillar", () -> new PillarFeature(PillarFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> STELLAGMITE = FEATURES.register("stellagmite", () -> new StellagmiteFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<SpeleothemFeature.Configuration>> SPELEOTHEM = FEATURES.register("speleothem", () -> new SpeleothemFeature(SpeleothemFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<FallenLogFeature.Configuration>> FALLEN_LOG = FEATURES.register("fallen_log", () -> new FallenLogFeature(FallenLogFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<LeavesPileFeature.Configuration>> LEAVES_PILE = FEATURES.register("leaves_pile", () -> new LeavesPileFeature(LeavesPileFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> ASHEN_SNOW = FEATURES.register("ashen_snow", () -> new AshenSnowFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> SWAMP_WATER = FEATURES.register("swamp_water", () -> new SwampWaterFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<WaterPlantFeature.Configuration>> WATER_PLANT = FEATURES.register("water_plant", () -> new WaterPlantFeature(WaterPlantFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> ORBFLORA = FEATURES.register("orbflora", () -> new OrbfloraFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<SimpleBlockConfiguration>> UNDERWATER_SIMPLE_BLOCK = FEATURES.register("underwater_simple_block", () -> new UnderwaterSimpleBlockFeature(SimpleBlockConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> CORAL_CLAW = FEATURES.register("coral_claw", () -> new ESCoralClawFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> CORAL_MUSHROOM = FEATURES.register("coral_mushroom", () -> new ESCoralMushroomFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> CORAL_TREE = FEATURES.register("coral_tree", () -> new ESCoralTreeFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> DEAD_LUNAR_TREE = FEATURES.register("dead_lunar_tree", () -> new DeadLunarTreeFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<JinglestemFeature.Configuration>> JINGLESTEM = FEATURES.register("jinglestem", () -> new JinglestemFeature(JinglestemFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<CradlewoodFeature.Configuration>> CRADLEWOOD = FEATURES.register("cradlewood", () -> new CradlewoodFeature(CradlewoodFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<AbysslatePatchFeature.Configuration>> ABYSSLATE_PATCH = FEATURES.register("abysslate_patch", () -> new AbysslatePatchFeature(AbysslatePatchFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> ABYSSAL_CAVE = FEATURES.register("abyssal_cave", () -> new AbyssalCaveFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<VelvetumossFeature.Configuration>> VELVETUMOSS = FEATURES.register("velvetumoss", () -> new VelvetumossFeature(VelvetumossFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<BlockPatchFeature.Configuration>> BLOCK_PATCH = FEATURES.register("block_patch", () -> new BlockPatchFeature(BlockPatchFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<NoiseBooleanSelectorFeature.Configuration>> NOISE_BOOLEAN_SELECTOR = FEATURES.register("noise_boolean_selector", () -> new NoiseBooleanSelectorFeature(NoiseBooleanSelectorFeature.Configuration.CODEC));
	public static final RegistryObject<Feature<?>, Feature<SkyIslandFeature.Configuration>> SKY_ISLAND = FEATURES.register("sky_island", () -> new SkyIslandFeature(SkyIslandFeature.Configuration.CODEC));

	public static void loadClass() {
	}
}
