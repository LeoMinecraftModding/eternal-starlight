package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.util.ESSpellUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractSpell {
    private final Properties properties;
    public Properties spellProperties() {
        return properties;
    }

    public AbstractSpell(Properties properties) {
        this.properties = properties;
    }

    public boolean canCast(LivingEntity entity, boolean checkCrystal) {
        boolean crystalCheck = !checkCrystal || (entity instanceof Player player && hasRightCrystal(player.getInventory()));
        return crystalCheck && ESSpellUtil.getCoolDownFor(entity, this) <= 0 && checkExtraConditions(entity);
    }

    public boolean hasRightCrystal(Inventory inventory) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.is(spellProperties().type().getCrystalsTag())) {
                return true;
            }
        }
        return false;
    }

    public boolean canContinueToCast(LivingEntity entity, int totalTicks) {
        return totalTicks <= properties.preparationTicks() + properties.spellTicks() && checkExtraConditionsToContinue(entity, totalTicks);
    }

    public void start(LivingEntity entity, boolean damageCrystal) {
        if (damageCrystal && entity instanceof Player player) {
            damageCrystal(player);
        }
        onStart(entity);
    }

    public void damageCrystal(Player player) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.is(spellProperties().type().getCrystalsTag())) {
                stack.hurtAndBreak(1, player, (p) -> {});
                return;
            }
        }
    }

    public void stop(LivingEntity entity, int ticks) {
        onStop(entity, ticks);
        ESSpellUtil.setCoolDownFor(entity, this, properties.coolDownTicks());
    }

    public abstract boolean checkExtraConditions(LivingEntity entity);
    public abstract boolean checkExtraConditionsToContinue(LivingEntity entity, int ticks);
    public abstract void onPreparationTick(LivingEntity entity, int ticks);
    public abstract void onSpellTick(LivingEntity entity, int ticks);
    public abstract void onStart(LivingEntity entity);
    public abstract void onStop(LivingEntity entity, int ticks);

    public record Properties(ManaType type, int preparationTicks, int spellTicks, int coolDownTicks) {

    }
}
