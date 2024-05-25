package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {
    @Shadow @Nullable public abstract Entity getOwner();

    @Inject(method = "getMovementToShoot", at = @At(value = "RETURN"), cancellable = true)
    private void getMovementToShoot(double d, double e, double f, float g, float h, CallbackInfoReturnable<Vec3> cir) {
        if (getOwner() instanceof LivingEntity livingEntity && ((Projectile) (Object) this) instanceof ThrownPotion) {
            double factor = 1;
            if (livingEntity.getAttributes().hasAttribute(ESAttributes.THROWN_POTION_DISTANCE.asHolder())) {
                AttributeInstance distance = livingEntity.getAttribute(ESAttributes.THROWN_POTION_DISTANCE.asHolder());
                if (distance != null) {
                    factor = distance.getValue();
                }
            }
            cir.setReturnValue(cir.getReturnValue().scale(factor));
        }
    }
}
