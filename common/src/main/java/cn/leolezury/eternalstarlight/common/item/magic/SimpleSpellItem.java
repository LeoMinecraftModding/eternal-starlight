package cn.leolezury.eternalstarlight.common.item.magic;

import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class SimpleSpellItem extends Item {
    private final Supplier<? extends AbstractSpell> spellSupplier;

    public SimpleSpellItem(Supplier<? extends AbstractSpell> spellSupplier, Properties properties) {
        super(properties);
        this.spellSupplier = spellSupplier;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.BOW;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int i) {
        AbstractSpell spell = spellSupplier.get();
        int preparationTicks = spell.spellProperties().preparationTicks();
        int spellTicks = spell.spellProperties().spellTicks();
        int useItemTicks = livingEntity.getTicksUsingItem();
        if (!spell.canContinueToCast(livingEntity, useItemTicks)) {
            spell.stop(livingEntity, useItemTicks - preparationTicks);
            livingEntity.stopUsingItem();
            if (livingEntity instanceof Player player) {
                player.getCooldowns().addCooldown(this, spell.spellProperties().coolDownTicks());
            }
            return;
        }
        if (useItemTicks <= preparationTicks + spellTicks) {
            spell.tick(livingEntity, useItemTicks);
        } else {
            spell.stop(livingEntity, useItemTicks - preparationTicks);
            livingEntity.stopUsingItem();
            if (livingEntity instanceof Player player) {
                player.getCooldowns().addCooldown(this, spell.spellProperties().coolDownTicks());
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        AbstractSpell spell = spellSupplier.get();
        spell.stop(livingEntity, getUseDuration(itemStack) - i - spell.spellProperties().preparationTicks());
        if (livingEntity instanceof Player player) {
            player.getCooldowns().addCooldown(this, spell.spellProperties().coolDownTicks());
        }
    }

    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        AbstractSpell spell = spellSupplier.get();
        if (!level.isClientSide && spell.canCast(player, false)) {
            player.startUsingItem(interactionHand);
            itemStack.hurtAndBreak(1, player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            spell.start(player, false);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);
    }
}
