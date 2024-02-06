package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.util.SpellUtil;
import net.minecraft.world.entity.LivingEntity;

public abstract class AbstractSpell {
    private final Properties properties;
    private final ManaType type;
    public Properties spellProperties() {
        return properties;
    }

    public ManaType spellType() {
        return type;
    }

    public AbstractSpell(Properties properties, ManaType type) {
        this.properties = properties;
        this.type = type;
    }

    public boolean canCast(LivingEntity entity) {
        return SpellUtil.getCoolDownFor(entity, this) <= 0 && checkExtraConditions(entity);
    }

    public boolean canContinueToCast(LivingEntity entity, int totalTicks) {
        return totalTicks <= properties.preparationTicks() + properties.spellTicks() && checkExtraConditionsToContinue(entity, totalTicks);
    }

    public void stop(LivingEntity entity, int ticks) {
        onStop(entity, ticks);
        SpellUtil.setCoolDownFor(entity, this, properties.coolDownTicks());
    }

    public abstract boolean checkExtraConditions(LivingEntity entity);
    public abstract boolean checkExtraConditionsToContinue(LivingEntity entity, int ticks);
    public abstract void onPreparationTick(LivingEntity entity, int ticks);
    public abstract void onSpellTick(LivingEntity entity, int ticks);
    public abstract void onStart(LivingEntity entity);
    public abstract void onStop(LivingEntity entity, int ticks);

    public record Properties(int preparationTicks, int spellTicks, int coolDownTicks) {

    }
}
