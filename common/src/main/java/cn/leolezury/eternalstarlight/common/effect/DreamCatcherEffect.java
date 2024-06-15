package cn.leolezury.eternalstarlight.common.effect;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DreamCatcherEffect extends MobEffect {
    public static final double ARMOR_ADDITION = 5D;

    public DreamCatcherEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
        this.addAttributeModifier(Attributes.ARMOR, EternalStarlight.id("armor.dream_catcher"), ARMOR_ADDITION, AttributeModifier.Operation.ADD_VALUE);
    }
}
