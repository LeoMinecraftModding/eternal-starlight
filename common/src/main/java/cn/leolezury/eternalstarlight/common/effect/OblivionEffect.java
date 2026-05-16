package cn.leolezury.eternalstarlight.common.effect;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class OblivionEffect extends MobEffect {
	public static final double SPEED_BONUS = 0.2;
	public static final double GRAVITY_REDUCTION = -0.2;
	public static final double FOLLOW_RANGE_REDUCTION = -0.2;

	public OblivionEffect(MobEffectCategory category, int color) {
		super(category, color);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, EternalStarlight.id("effect.oblivion.speed"), SPEED_BONUS, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.GRAVITY, EternalStarlight.id("effect.oblivion.gravity"), GRAVITY_REDUCTION, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ESAttributes.ENEMY_FOLLOW_RANGE_MULTIPLIER.asHolder(), EternalStarlight.id("effect.oblivion.follow_range"), FOLLOW_RANGE_REDUCTION, AttributeModifier.Operation.ADD_VALUE);
	}
}
