package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.world.feature.tree.BranchingTrunkPlacer;
import cn.leolezury.eternalstarlight.world.feature.tree.SpheroidFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PlacerInit {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, EternalStarlight.MOD_ID);
    public static final DeferredRegister<FoliagePlacerType<?>> FLOIAGE_PLACERS = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<TrunkPlacerType<BranchingTrunkPlacer>> TRUNK_BRANCHING = TRUNK_PLACERS.register("branching_trunk_placer", () -> new TrunkPlacerType<>(BranchingTrunkPlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<SpheroidFoliagePlacer>> FOLIAGE_SPHEROID = FLOIAGE_PLACERS.register("spheroid_foliage_placer", () -> new FoliagePlacerType<>(SpheroidFoliagePlacer.CODEC));
}