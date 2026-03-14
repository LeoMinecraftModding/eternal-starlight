package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.critereon.WitnessWeatherTrigger;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.Registries;

public class ESCriteriaTriggers {
	public static final RegistrationProvider<CriterionTrigger<?>> TRIGGERS = RegistrationProvider.get(Registries.TRIGGER_TYPE, EternalStarlight.ID);
	public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> CHALLENGED_GATEKEEPER = TRIGGERS.register("challenged_gatekeeper", PlayerTrigger::new);
	public static final RegistryObject<CriterionTrigger<?>, WitnessWeatherTrigger> WITNESS_WEATHER = TRIGGERS.register("witness_weather", WitnessWeatherTrigger::new);
	public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> THROW_GLEECH_EGG = TRIGGERS.register("throw_gleech_egg", PlayerTrigger::new);
	public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> DEACTIVATE_ENERGY_BLOCK = TRIGGERS.register("deactivate_energy_block", PlayerTrigger::new);
	public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> FREEZE_STARLIGHT_GOLEM = TRIGGERS.register("freeze_starlight_golem", PlayerTrigger::new);
	public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> CHAIN_TANGLED_SKULL_EXPLOSION = TRIGGERS.register("chain_tangled_skull_explosion", PlayerTrigger::new);
	public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> SATURATE_DAGGER_OF_HUNGER = TRIGGERS.register("saturate_dagger_of_hunger", PlayerTrigger::new);
	public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> IGNITE_TEAR_BOMB = TRIGGERS.register("ignite_tear_bomb", PlayerTrigger::new);
	public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> WITNESS_STRANGHOUL_HUNT = TRIGGERS.register("witness_stranghoul_hunt", PlayerTrigger::new);
	public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> HIRE_STRANGHOUL = TRIGGERS.register("hire_stranghoul", PlayerTrigger::new);
	public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> HAMMER_CRITICAL_HIT = TRIGGERS.register("hammer_critical_hit", PlayerTrigger::new);
	public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> PUT_SEEDS_INTO_STARFIRE_BIRD_NEST = TRIGGERS.register("put_seeds_into_starfire_bird_nest", PlayerTrigger::new);

	public static void loadClass() {
	}
}
