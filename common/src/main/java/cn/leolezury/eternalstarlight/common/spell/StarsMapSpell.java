package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class StarsMapSpell extends AbstractSpell {
    public StarsMapSpell(Properties properties) {
        super(properties, ManaType.BLAZE);
    }

    @Override
    public boolean checkExtraConditions(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean checkExtraConditionsToContinue(LivingEntity entity, int ticks) {
        return false;
    }

    @Override
    public void onPreparationTick(LivingEntity entity, int ticks) {

    }

    @Override
    public void onSpellTick(LivingEntity entity, int ticks) {
        if (entity instanceof Player player && entity.level().isClientSide) {

        }
    }

    @Override
    public void onStart(LivingEntity entity) {
        if (entity instanceof Player && entity.level().isClientSide) {
            ESUtil.getPersistentData(entity).putBoolean("UseStarsMap", true);
        }
    }

    @Override
    public void onStop(LivingEntity entity, int ticks) {
        if (entity instanceof Player && entity.level().isClientSide) {
            ESUtil.getPersistentData(entity).putBoolean("UseStarsMap", false);
        }
    }
}
