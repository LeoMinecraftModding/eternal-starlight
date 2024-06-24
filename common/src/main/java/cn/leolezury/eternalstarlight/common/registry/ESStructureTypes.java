package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.structure.CursedGardenStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class ESStructureTypes {
	public static final RegistrationProvider<StructureType<?>> STRUCTURE_TYPES = RegistrationProvider.get(Registries.STRUCTURE_TYPE, EternalStarlight.ID);
	public static final RegistryObject<StructureType<?>, StructureType<CursedGardenStructure>> CURSED_GARDEN = STRUCTURE_TYPES.register("cursed_garden", () -> () -> CursedGardenStructure.CODEC);

	public static void loadClass() {
	}
}
