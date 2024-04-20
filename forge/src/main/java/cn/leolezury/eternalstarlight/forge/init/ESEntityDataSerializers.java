package cn.leolezury.eternalstarlight.forge.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.misc.ESSynchedEntityData;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ESEntityDataSerializers {
    public static final RegistrationProvider<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = RegistrationProvider.get(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS.key(), EternalStarlight.MOD_ID);
    public static final RegistryObject<EntityDataSerializer<?>, EntityDataSerializer<ESSynchedEntityData.SynchedData>> SYNCHED_DATA = ENTITY_DATA_SERIALIZERS.register("synched_data", () -> ESSynchedEntityData.SYNCHED_DATA_SERIALIZER);

    public static void loadClass() {}
}
