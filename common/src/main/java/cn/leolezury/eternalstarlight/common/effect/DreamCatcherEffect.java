package cn.leolezury.eternalstarlight.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class DreamCatcherEffect extends MobEffect {
    public static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("CE8DBC1A-EE5F-54F7-9DF6-F7F1EE4915A9");
    public static final double ARMOR_ADDITION = 5D;

    public DreamCatcherEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
        this.addAttributeModifier(Attributes.ARMOR, ARMOR_MODIFIER_UUID.toString(), ARMOR_ADDITION, AttributeModifier.Operation.ADDITION);
    }
}
