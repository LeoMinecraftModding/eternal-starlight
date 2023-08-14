package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.world.structure.placement.SLRandomSpreadStructurePlacement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
public class StructurePlacementTypeInit {
    public static final RegistrationProvider<StructurePlacementType<?>> STRUCTURE_PLACEMENT_TYPES = RegistrationProvider.get(Registries.STRUCTURE_PLACEMENT, EternalStarlight.MOD_ID);

    public static final RegistryObject<StructurePlacementType<SLRandomSpreadStructurePlacement>> SL_RANDOM_SPREAD = STRUCTURE_PLACEMENT_TYPES.register("sl_random_spread", () -> () -> SLRandomSpreadStructurePlacement.CODEC);
}
