package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.BranchingTrunkPlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.MegaBranchingTrunkPlacer;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.SpheroidFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class PlacerInit {
//    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, EternalStarlight.MOD_ID);
//    public static final DeferredRegister<FoliagePlacerType<?>> FLOIAGE_PLACERS = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, EternalStarlight.MOD_ID);
    public static final RegistrationProvider<TrunkPlacerType<?>> TRUNK_PLACERS = RegistrationProvider.get(Registries.TRUNK_PLACER_TYPE, EternalStarlight.MOD_ID);
    public static final RegistrationProvider<FoliagePlacerType<?>> FOLIAGE_PLACERS = RegistrationProvider.get(Registries.FOLIAGE_PLACER_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<TrunkPlacerType<BranchingTrunkPlacer>> TRUNK_BRANCHING = TRUNK_PLACERS.register("branching_trunk_placer", () -> new TrunkPlacerType(BranchingTrunkPlacer.CODEC));
    public static final RegistryObject<TrunkPlacerType<MegaBranchingTrunkPlacer>> MEGA_TRUNK_BRANCHING = TRUNK_PLACERS.register("mega_branching_trunk_placer", () -> new TrunkPlacerType(MegaBranchingTrunkPlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<SpheroidFoliagePlacer>> FOLIAGE_SPHEROID = FOLIAGE_PLACERS.register("spheroid_foliage_placer", () -> new FoliagePlacerType(SpheroidFoliagePlacer.CODEC));
    public static void postRegistry() {}
}