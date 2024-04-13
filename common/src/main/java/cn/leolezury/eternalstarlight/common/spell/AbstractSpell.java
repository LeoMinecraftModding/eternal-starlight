package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.entity.interfaces.ESLivingEntity;
import cn.leolezury.eternalstarlight.common.entity.misc.ESSynchedEntityData;
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
        if (entity instanceof ESLivingEntity livingEntity) {
            livingEntity.setSynchedData(new ESSynchedEntityData.SynchedData(true, this, 0));
        }
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

    public void tick(LivingEntity entity, int ticks) {
        if (ticks <= spellProperties().preparationTicks()) {
            onPreparationTick(entity, ticks);
        } else if (ticks <= spellProperties().preparationTicks() + spellProperties().spellTicks()) {
            onSpellTick(entity, ticks - spellProperties().preparationTicks());
        }
        if (entity instanceof ESLivingEntity livingEntity) {
            livingEntity.setSynchedData(new ESSynchedEntityData.SynchedData(true, this, ticks));
        }
    }

    public void stop(LivingEntity entity, int ticks) {
        onStop(entity, ticks);
        ESSpellUtil.setCoolDownFor(entity, this, properties.coolDownTicks());
        if (entity instanceof ESLivingEntity livingEntity) {
            livingEntity.setSynchedData(ESSynchedEntityData.SynchedData.getDefault());
        }
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
