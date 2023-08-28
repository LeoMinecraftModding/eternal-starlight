package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.item.interfaces.Swingable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract ItemStack getItemInHand(InteractionHand interactionHand);

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"))
    private void swing(InteractionHand interactionHand, boolean bl, CallbackInfo ci) {
        if (this.getItemInHand(interactionHand).getItem() instanceof Swingable swingable) {
            swingable.swing(this.getItemInHand(interactionHand), (LivingEntity) ((Object) this));
        }
    }
}
