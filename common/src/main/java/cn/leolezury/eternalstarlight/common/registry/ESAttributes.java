package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ESAttributes {
    public static final RegistrationProvider<Attribute> ATTRIBUTES = RegistrationProvider.get(Registries.ATTRIBUTE, EternalStarlight.ID);
    public static final RegistryObject<Attribute, RangedAttribute> THROWN_POTION_DISTANCE = ATTRIBUTES.register("generic.thrown_potion_distance", () -> new RangedAttribute("attribute.name.eternal_starlight.generic.thrown_potion_distance", 1, 0, 5));
    public static final RegistryObject<Attribute, RangedAttribute> ETHER_RESISTANCE = ATTRIBUTES.register("generic.ether_resistance", () -> new RangedAttribute("attribute.name.eternal_starlight.generic.ether_resistance", 0, 0, 1));

    public static void loadClass() {}
}
