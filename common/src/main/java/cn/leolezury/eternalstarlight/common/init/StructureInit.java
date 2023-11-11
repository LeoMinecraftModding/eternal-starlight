package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.structure.LargeJigsawStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class StructureInit {
    public static final RegistrationProvider<StructureType<?>> STRUCTURES = RegistrationProvider.get(Registries.STRUCTURE_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<StructureType<LargeJigsawStructure>> LARGE_JIGSAW = STRUCTURES.register("large_jigsaw", () -> () -> LargeJigsawStructure.CODEC);
    public static void loadClass() {}
}
