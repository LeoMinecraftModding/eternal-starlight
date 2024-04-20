package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.entity.misc.ESSynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityDataSerializers.class)
public abstract class EntityDataSerializersMixin {
    @Shadow
    public static void registerSerializer(EntityDataSerializer<?> entityDataSerializer) {
    }

    static {
        registerSerializer(ESSynchedEntityData.SYNCHED_DATA_SERIALIZER);
    }
}
