package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.effect.CrystallineInfectionEffect;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ESMobEffects {
    public static final RegistrationProvider<MobEffect> MOB_EFFECTS = RegistrationProvider.get(Registries.MOB_EFFECT, EternalStarlight.MOD_ID);
    public static final RegistryObject<MobEffect, MobEffect> CRYSTALLINE_INFECTION = MOB_EFFECTS.register("crystalline_infection", () -> new CrystallineInfectionEffect(MobEffectCategory.HARMFUL, 8001164));
    public static void loadClass() {}
}
