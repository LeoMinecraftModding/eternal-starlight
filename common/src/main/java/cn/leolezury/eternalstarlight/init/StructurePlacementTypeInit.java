package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.world.structure.placement.ESRandomSpreadStructurePlacement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
public class StructurePlacementTypeInit {
    public static final RegistrationProvider<StructurePlacementType<?>> STRUCTURE_PLACEMENT_TYPES = RegistrationProvider.get(Registries.STRUCTURE_PLACEMENT, EternalStarlight.MOD_ID);
    public static final RegistryObject<StructurePlacementType<ESRandomSpreadStructurePlacement>> ES_RANDOM_SPREAD = STRUCTURE_PLACEMENT_TYPES.register("es_random_spread", () -> () -> ESRandomSpreadStructurePlacement.CODEC);
    public static void postRegistry() {}
}
