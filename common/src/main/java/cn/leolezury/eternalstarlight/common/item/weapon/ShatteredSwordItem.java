package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownShatteredBlade;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class ShatteredSwordItem extends SwordItem {
    public ShatteredSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    public static boolean hasBlade(ItemStack itemStack) {
        return itemStack.getOrDefault(ESDataComponents.HAS_BLADE.get(), true);
    }

    public static void setHasBlade(ItemStack itemStack, boolean hasBlade) {
        itemStack.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.HAS_BLADE.get(), hasBlade).build());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (hasBlade(itemStack)) {
            ThrownShatteredBlade blade = new ThrownShatteredBlade(level, player, itemStack);
            blade.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
            if (player.getAbilities().instabuild) {
                blade.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }

            level.addFreshEntity(blade);
            level.playSound(null, blade.blockPosition(), SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS);
            if (!player.getAbilities().instabuild) {
                itemStack.hurtAndBreak(1, player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                setHasBlade(itemStack, false);
            }
            return InteractionResultHolder.success(itemStack);
        } else {
            if (player.getAbilities().instabuild) {
                setHasBlade(itemStack, true);
                return InteractionResultHolder.consume(itemStack);
            } else {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack item = player.getInventory().getItem(i);
                    if (item.is(ESItems.SHATTERED_SWORD_BLADE.get())) {
                        setHasBlade(itemStack, true);
                        item.shrink(1);
                        return InteractionResultHolder.consume(itemStack);
                    }
                }
            }
        }
        return InteractionResultHolder.fail(itemStack);
    }
}
