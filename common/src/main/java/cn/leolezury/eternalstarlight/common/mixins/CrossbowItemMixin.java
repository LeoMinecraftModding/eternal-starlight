package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
    @Inject(method = "getArrow", at = @At("RETURN"), cancellable = true)
    private static void getArrow(Level level, LivingEntity livingEntity, ItemStack itemStack, ItemStack itemStack2, CallbackInfoReturnable<AbstractArrow> cir) {
        if (itemStack.is(ItemInit.CRYSTAL_CROSSBOW.get())) {
            AbstractArrow abstractArrow = cir.getReturnValue();
            ESUtil.getPersistentData(abstractArrow).putBoolean(EternalStarlight.MOD_ID + ":crystal", true);
            cir.setReturnValue(abstractArrow);
        }
    }
}
