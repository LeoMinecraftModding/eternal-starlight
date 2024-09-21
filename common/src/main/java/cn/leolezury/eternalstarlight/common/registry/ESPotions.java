package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

public class ESPotions {
	public static final RegistrationProvider<Potion> POTIONS = RegistrationProvider.get(Registries.POTION, EternalStarlight.ID);
	public static final RegistryObject<Potion, Potion> HUNGER = POTIONS.register("hunger", () -> new Potion(new MobEffectInstance(MobEffects.HUNGER, 900)));
	public static final RegistryObject<Potion, Potion> LONG_HUNGER = POTIONS.register("long_hunger", () -> new Potion("hunger", new MobEffectInstance(MobEffects.HUNGER, 1800)));
	public static final RegistryObject<Potion, Potion> STRONG_HUNGER = POTIONS.register("strong_hunger", () -> new Potion("hunger", new MobEffectInstance(MobEffects.HUNGER, 450, 1)));

	public static void loadClass() {
	}
}
