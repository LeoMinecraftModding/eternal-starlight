package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage.ScarletFoliagePlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage.SpheroidFoliagePlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.foliage.TorreyaFoliagePlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.trunk.BranchingTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class ESPlacers {
    public static final RegistrationProvider<TrunkPlacerType<?>> TRUNK_PLACERS = RegistrationProvider.get(Registries.TRUNK_PLACER_TYPE, EternalStarlight.MOD_ID);
    public static final RegistrationProvider<FoliagePlacerType<?>> FOLIAGE_PLACERS = RegistrationProvider.get(Registries.FOLIAGE_PLACER_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<TrunkPlacerType<?>, TrunkPlacerType<BranchingTrunkPlacer>> TRUNK_BRANCHING = TRUNK_PLACERS.register("branching", () -> new TrunkPlacerType<>(BranchingTrunkPlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<?>, FoliagePlacerType<SpheroidFoliagePlacer>> FOLIAGE_SPHEROID = FOLIAGE_PLACERS.register("spheroid", () -> new FoliagePlacerType<>(SpheroidFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<?>, FoliagePlacerType<ScarletFoliagePlacer>> FOLIAGE_SCARLET = FOLIAGE_PLACERS.register("scarlet", () -> new FoliagePlacerType<>(ScarletFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<?>, FoliagePlacerType<TorreyaFoliagePlacer>> FOLIAGE_TORREYA = FOLIAGE_PLACERS.register("torreya", () -> new FoliagePlacerType<>(TorreyaFoliagePlacer.CODEC));
    public static void loadClass() {}
}