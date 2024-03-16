package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.structure.pool.ESSinglePoolElement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;

public class ESStructurePoolElementTypes {
    public static final RegistrationProvider<StructurePoolElementType<?>> STRUCTURE_POOL_ELEMENT_TYPES = RegistrationProvider.get(Registries.STRUCTURE_POOL_ELEMENT, EternalStarlight.MOD_ID);
    public static final RegistryObject<StructurePoolElementType<?>, StructurePoolElementType<ESSinglePoolElement>> SINGLE_POOL = STRUCTURE_POOL_ELEMENT_TYPES.register("single_pool", () -> () -> ESSinglePoolElement.CODEC);
    public static void loadClass() {}
}
