package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.mixins.access.FoliagePlacerTypeAccess;
import cn.leolezury.eternalstarlight.mixins.access.TrunkPlacerTypeAccess;
import cn.leolezury.eternalstarlight.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.world.feature.tree.BranchingTrunkPlacer;
import cn.leolezury.eternalstarlight.world.feature.tree.SpheroidFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class PlacerInit {
//    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, EternalStarlight.MOD_ID);
//    public static final DeferredRegister<FoliagePlacerType<?>> FLOIAGE_PLACERS = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, EternalStarlight.MOD_ID);
    public static final RegistrationProvider<TrunkPlacerType<?>> TRUNK_PLACERS = RegistrationProvider.get(Registries.TRUNK_PLACER_TYPE, EternalStarlight.MOD_ID);
    public static final RegistrationProvider<FoliagePlacerType<?>> FOLIAGE_PLACERS = RegistrationProvider.get(Registries.FOLIAGE_PLACER_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<TrunkPlacerType<BranchingTrunkPlacer>> TRUNK_BRANCHING = TRUNK_PLACERS.register("branching_trunk_placer", () -> TrunkPlacerTypeAccess.create(BranchingTrunkPlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<SpheroidFoliagePlacer>> FOLIAGE_SPHEROID = FOLIAGE_PLACERS.register("spheroid_foliage_placer", () -> FoliagePlacerTypeAccess.create(SpheroidFoliagePlacer.CODEC));
    public static void postRegistry() {}
}