package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(at = @At(value = "HEAD"), method = "hurtCurrentlyUsedShield")
    private void es_damageShield(float amount, CallbackInfo callBackInfo) {
        Player player = (Player) (Object) this;
        ItemStack useItem = player.getUseItem();
        if (CommonSetupHandlers.SHIELDS.stream().anyMatch(itemSupplier -> useItem.is(itemSupplier.get()))) {
            useItem.hurtAndBreak(Math.max((int) (amount / 5f), 1), player, (p) -> {
                p.broadcastBreakEvent(p.getUsedItemHand());
            });
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "disableShield")
    private void es_disableShield(boolean sprinting, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemStack useItem = player.getUseItem();
        if (CommonSetupHandlers.SHIELDS.stream().anyMatch(itemSupplier -> useItem.is(itemSupplier.get()))) {
            float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(player) * 0.05F;
            if (sprinting) {
                f += 0.75F;
            }

            if (player.getRandom().nextFloat() < f) {
                player.getCooldowns().addCooldown(useItem.getItem(), 100);
                player.stopUsingItem();
                player.level().broadcastEntityEvent(player, (byte)30);
            }
        }
    }
}
