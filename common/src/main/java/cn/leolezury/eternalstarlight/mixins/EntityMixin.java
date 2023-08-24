package cn.leolezury.eternalstarlight.mixins;

import cn.leolezury.eternalstarlight.entity.interfaces.PersistentDataHolder;
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
    private CompoundTag persistentData;

    @Override
    public CompoundTag getPersistentData() {
        if (persistentData == null) {
            persistentData = new CompoundTag();
        }
        return persistentData;
    }

    @Inject(method = "save", at = @At("HEAD"))
    private void save(CompoundTag compoundTag, CallbackInfoReturnable<Boolean> info) {
        compoundTag.put("ESData", persistentData);
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void load(CompoundTag compoundTag, CallbackInfo info) {
        if (compoundTag != null && compoundTag.contains("ESData", 10)) {
            persistentData = compoundTag.getCompound("ESData");
        }
    }
}
