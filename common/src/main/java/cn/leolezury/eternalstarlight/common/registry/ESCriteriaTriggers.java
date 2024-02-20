package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.Registries;

public class ESCriteriaTriggers {
    public static final RegistrationProvider<CriterionTrigger<?>> TRIGGERS = RegistrationProvider.get(Registries.TRIGGER_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<CriterionTrigger<?>, PlayerTrigger> CHALLENGED_GATEKEEPER = TRIGGERS.register("challenged_gatekeeper", PlayerTrigger::new);
    public static void loadClass() {}
}
