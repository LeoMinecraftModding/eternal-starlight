package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ESAttributes {
	public static final RegistrationProvider<Attribute> ATTRIBUTES = RegistrationProvider.get(Registries.ATTRIBUTE, EternalStarlight.ID);
	public static final RegistryObject<Attribute, RangedAttribute> THROWN_POTION_DISTANCE = ATTRIBUTES.register("generic.thrown_potion_distance", () -> new RangedAttribute("attribute.name." + EternalStarlight.ID + ".generic.thrown_potion_distance", 1, 0, 1024));
	public static final RegistryObject<Attribute, RangedAttribute> ETHER_RESISTANCE = ATTRIBUTES.register("generic.ether_resistance", () -> new RangedAttribute("attribute.name." + EternalStarlight.ID + ".generic.ether_resistance", 0, 0, 1));
	public static final RegistryObject<Attribute, RangedAttribute> FIRE_RESISTANCE = ATTRIBUTES.register("generic.fire_resistance", () -> new RangedAttribute("attribute.name." + EternalStarlight.ID + ".generic.fire_resistance", 0, 0, 1));
	public static final RegistryObject<Attribute, RangedAttribute> METEOR_COUNTERATTACK_CHANCE = ATTRIBUTES.register("generic.meteor_counterattack_chance", () -> new RangedAttribute("attribute.name." + EternalStarlight.ID + ".generic.meteor_counterattack_chance", 0, 0, 1));
	public static final RegistryObject<Attribute, RangedAttribute> HEAL_MULTIPLIER = ATTRIBUTES.register("generic.heal_multiplier", () -> new RangedAttribute("attribute.name." + EternalStarlight.ID + ".generic.heal_multiplier", 1, 0, 1024));
	public static final RegistryObject<Attribute, Attribute> ENEMY_FOLLOW_RANGE_MULTIPLIER = ATTRIBUTES.register("generic.enemy_follow_range_multiplier", () -> new RangedAttribute("attribute.name." + EternalStarlight.ID + ".generic.enemy_follow_range_multiplier", 1, 0, 1024).setSentiment(Attribute.Sentiment.NEGATIVE));
	public static final RegistryObject<Attribute, Attribute> FOG_VISION = ATTRIBUTES.register("player.fog_vision", () -> new RangedAttribute("attribute.name." + EternalStarlight.ID + ".player.fog_vision", 0, 0, 1024).setSyncable(true));

	public static void loadClass() {
	}
}
