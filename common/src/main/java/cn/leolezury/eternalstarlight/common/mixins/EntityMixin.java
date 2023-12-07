package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.entity.interfaces.PersistentDataHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements PersistentDataHolder {
    @Unique
    private CompoundTag esPersistentData;

    @Override
    public CompoundTag esGetPersistentData() {
        if (esPersistentData == null) {
            esPersistentData = new CompoundTag();
        }
        return esPersistentData;
    }

    @Inject(method = "save", at = @At("HEAD"))
    private void es_save(CompoundTag compoundTag, CallbackInfoReturnable<Boolean> info) {
        if (esPersistentData != null && compoundTag != null) {
            compoundTag.put("ESData", esPersistentData.copy());
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void es_load(CompoundTag compoundTag, CallbackInfo info) {
        if (compoundTag != null && compoundTag.contains("ESData", 10)) {
            esPersistentData = compoundTag.getCompound("ESData");
        }
    }
}
