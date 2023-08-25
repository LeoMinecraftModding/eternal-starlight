package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.world.structure.LargeJigsawStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class StructureInit {
    public static final RegistrationProvider<StructureType<?>> STRUCTURES = RegistrationProvider.get(Registries.STRUCTURE_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<StructureType<LargeJigsawStructure>> LARGE_JIGSAW = STRUCTURES.register("large_jigsaw", () -> () -> LargeJigsawStructure.CODEC);
    public static void postRegistry() {}
}
