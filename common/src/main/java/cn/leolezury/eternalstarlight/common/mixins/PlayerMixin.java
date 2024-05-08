package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin implements SpellCaster {
    @Unique
    private SpellCastData spellCastData = SpellCastData.getDefault();

    @Inject(at = @At(value = "HEAD"), method = "hurtCurrentlyUsedShield")
    private void es_damageShield(float amount, CallbackInfo callBackInfo) {
        Player player = (Player) (Object) this;
        ItemStack useItem = player.getUseItem();
        if (useItem.is(ESItems.MOONRING_GREATSWORD.get())) {
            useItem.hurtAndBreak(Math.max((int) (amount / 5f), 1), player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            player.stopUsingItem();
            player.getCooldowns().addCooldown(useItem.getItem(), 100);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "disableShield", cancellable = true)
    private void es_disableShield(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemStack useItem = player.getUseItem();
        if (useItem.is(ESItems.MOONRING_GREATSWORD.get())) {
            ci.cancel();
        }
    }

    @Override
    public SpellCastData getSpellData() {
        return spellCastData;
    }

    @Override
    public void setSpellData(SpellCastData data) {
        this.spellCastData = data;
    }
}
