package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Shadow public abstract ItemStack getItem();

    @Shadow @Nullable private UUID target;

    @Shadow private int pickupDelay;

    @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
    public void playerTouch(Player player, CallbackInfo ci) {
        if (((ItemEntity) (Object) this).level().isClientSide) return;
        if (this.pickupDelay == 0 && (this.target == null || this.target.equals(player.getUUID())) && getItem().is(ESItems.MANA_CRYSTAL_SHARD.get())) {
            ci.cancel();
            ((ItemEntity) (Object) this).discard();
            Inventory inventory = player.getInventory();
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (stack.is(ESTags.Items.MANA_CRYSTALS)) {
                    stack.setDamageValue(stack.getDamageValue() - getItem().getCount());
                    return;
                }
            }
        }
    }
}
