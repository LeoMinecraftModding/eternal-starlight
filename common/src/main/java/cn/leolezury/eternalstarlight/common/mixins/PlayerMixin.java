package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(at = @At(value = "HEAD"), method = "hurtCurrentlyUsedShield")
    private void es_damageShield(float amount, CallbackInfo callBackInfo) {
        Player player = (Player) (Object) this;
        ItemStack useItem = player.getUseItem();
        if (useItem.is(ItemInit.MOONRING_GREATSWORD.get())) {
            useItem.hurtAndBreak(Math.max((int) (amount / 5f), 1), player, (p) -> {
                p.broadcastBreakEvent(p.getUsedItemHand());
            });
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "disableShield", cancellable = true)
    private void es_disableShield(boolean sprinting, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemStack useItem = player.getUseItem();
        if (useItem.is(ItemInit.MOONRING_GREATSWORD.get())) {
            ci.cancel();
        }
    }
}
