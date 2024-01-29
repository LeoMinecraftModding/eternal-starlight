package cn.leolezury.eternalstarlight.common.effect;

import cn.leolezury.eternalstarlight.common.data.DamageTypeInit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class CrystallineInfectionEffect extends MobEffect {
    public static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("CE8DBC1A-EE2F-54F6-8DF6-F7F1EE4915A9");
    public static final double ARMOR_ADDITION = -5D;

    public CrystallineInfectionEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
        this.addAttributeModifier(Attributes.ARMOR, ARMOR_MODIFIER_UUID.toString(), ARMOR_ADDITION, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        living.hurt(DamageTypeInit.getDamageSource(living.level(), DamageTypeInit.CRYSTALLINE_INFECTION), (amplifier + 1));
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int k = 25 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }
}
