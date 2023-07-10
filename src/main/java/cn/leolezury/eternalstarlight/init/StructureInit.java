package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.world.structure.LargeJigsawStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class StructureInit {
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, EternalStarlight.MOD_ID);

    public static final RegistryObject<StructureType<LargeJigsawStructure>> LARGE_JIGSAW = STRUCTURES.register("large_jigsaw", () -> () -> LargeJigsawStructure.CODEC);
}
