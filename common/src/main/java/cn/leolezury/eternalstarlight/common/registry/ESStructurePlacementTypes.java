package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.structure.placement.LandmarkStructurePlacement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

public class ESStructurePlacementTypes {
	public static final RegistrationProvider<StructurePlacementType<?>> STRUCTURE_PLACEMENT_TYPES = RegistrationProvider.get(Registries.STRUCTURE_PLACEMENT, EternalStarlight.ID);
	public static final RegistryObject<StructurePlacementType<?>, StructurePlacementType<LandmarkStructurePlacement>> LANDMARK = STRUCTURE_PLACEMENT_TYPES.register("landmark", () -> () -> LandmarkStructurePlacement.CODEC);

	public static void loadClass() {
	}
}
