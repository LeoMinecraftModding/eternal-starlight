package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.entity.interfaces.ESLivingEntity;
import cn.leolezury.eternalstarlight.common.entity.misc.ESSynchedEntityData;
import cn.leolezury.eternalstarlight.common.item.interfaces.Swingable;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements ESLivingEntity {
    @Shadow public abstract ItemStack getItemInHand(InteractionHand interactionHand);

    @Shadow public abstract boolean isUsingItem();

    @Shadow public abstract ItemStack getUseItem();

    @Unique
    private static final EntityDataAccessor<ESSynchedEntityData.SynchedData> ES_SYNCHED_DATA = SynchedEntityData.defineId(LivingEntity.class, ESSynchedEntityData.SYNCHED_DATA_SERIALIZER);

    @Inject(method = "defineSynchedData", at = @At("RETURN"))
    private void es_defineSynchedData(CallbackInfo ci) {
        ((LivingEntity) ((Object) this)).getEntityData().define(ES_SYNCHED_DATA, ESSynchedEntityData.SynchedData.getDefault());
    }

    @Override
    public ESSynchedEntityData.SynchedData getSynchedData() {
        return ((LivingEntity) ((Object) this)).getEntityData().get(ES_SYNCHED_DATA);
    }

    @Override
    public void setSynchedData(ESSynchedEntityData.SynchedData data) {
        ((LivingEntity) ((Object) this)).getEntityData().set(ES_SYNCHED_DATA, data);
    }

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"))
    private void es_swing(InteractionHand interactionHand, boolean bl, CallbackInfo ci) {
        if (this.getItemInHand(interactionHand).getItem() instanceof Swingable swingable) {
            swingable.swing(this.getItemInHand(interactionHand), (LivingEntity) ((Object) this));
        }
    }

    @Inject(method = "isBlocking", at = @At("RETURN"), cancellable = true)
    private void es_isBlocking(CallbackInfoReturnable<Boolean> cir) {
        if (isUsingItem() && getUseItem().is(ESItems.MOONRING_GREATSWORD.get())) {
            cir.setReturnValue(true);
        }
    }
}
