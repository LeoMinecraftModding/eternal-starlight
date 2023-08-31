package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.item.interfaces.Swingable;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract ItemStack getItemInHand(InteractionHand interactionHand);

    @Shadow public abstract boolean isUsingItem();

    @Shadow public abstract ItemStack getUseItem();

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"))
    private void swing(InteractionHand interactionHand, boolean bl, CallbackInfo ci) {
        if (this.getItemInHand(interactionHand).getItem() instanceof Swingable swingable) {
            swingable.swing(this.getItemInHand(interactionHand), (LivingEntity) ((Object) this));
        }
    }

    @Inject(method = "isBlocking", at = @At("RETURN"), cancellable = true)
    private void isBlocking(CallbackInfoReturnable<Boolean> cir) {
        if (isUsingItem() && getUseItem().is(ItemInit.MOONRING_GREATSWORD.get())) {
            cir.setReturnValue(true);
        }
    }
}
