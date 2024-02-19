package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownShatteredBlade;
import cn.leolezury.eternalstarlight.common.init.ESItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class ShatteredSwordItem extends SwordItem {
    public ShatteredSwordItem(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }
    
    public static boolean hasBlade(ItemStack itemStack) {
        return !itemStack.getOrCreateTag().getBoolean("NoBlade");
    }

    public static void setHasBlade(ItemStack itemStack, boolean hasBlade) {
        itemStack.getOrCreateTag().putBoolean("NoBlade", !hasBlade);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (hasBlade(itemStack)) {
            ThrownShatteredBlade blade = new ThrownShatteredBlade(level, player);
            blade.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
            if (player.getAbilities().instabuild) {
                blade.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }

            level.addFreshEntity(blade);
            level.playSound(null, blade, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
            if (!player.getAbilities().instabuild) {
                itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(p.getUsedItemHand()));
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
