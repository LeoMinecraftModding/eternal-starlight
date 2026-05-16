package cn.leolezury.eternalstarlight.common.effect;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class StickyEffect extends MobEffect {
	public static final double SPEED_PENALTY = -0.06;

	public StickyEffect(MobEffectCategory category, int color) {
		super(category, color);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, EternalStarlight.id("effect.sticky"), SPEED_PENALTY, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
}
