package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.effect.*;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ESMobEffects {
	public static final RegistrationProvider<MobEffect> MOB_EFFECTS = RegistrationProvider.get(Registries.MOB_EFFECT, EternalStarlight.ID);
	public static final RegistryObject<MobEffect, MobEffect> CRYSTAL_INFECTION = MOB_EFFECTS.register("crystal_infection", () -> new CrystalInfectionEffect(MobEffectCategory.HARMFUL, 8001164));
	public static final RegistryObject<MobEffect, MobEffect> DREAM_CATCHER = MOB_EFFECTS.register("dream_catcher", () -> new DreamCatcherEffect(MobEffectCategory.BENEFICIAL, 0x5187c4));
	public static final RegistryObject<MobEffect, MobEffect> STICKY = MOB_EFFECTS.register("sticky", () -> new StickyEffect(MobEffectCategory.BENEFICIAL, 0xffb6af));
	public static final RegistryObject<MobEffect, MobEffect> FLAMMABLE = MOB_EFFECTS.register("flammable", () -> new MobEffect(MobEffectCategory.HARMFUL, 0xd9737c));
	public static final RegistryObject<MobEffect, MobEffect> BRITTLE = MOB_EFFECTS.register("brittle", () -> new MobEffect(MobEffectCategory.HARMFUL, 0x9bf4f4));
	public static final RegistryObject<MobEffect, MobEffect> NUMBNESS = MOB_EFFECTS.register("numbness", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x573a45));
	public static final RegistryObject<MobEffect, MobEffect> TEARY = MOB_EFFECTS.register("teary", () -> new MobEffect(MobEffectCategory.HARMFUL, 0x9fc3c3));
	public static final RegistryObject<MobEffect, MobEffect> STARFIRE = MOB_EFFECTS.register("starfire", () -> new StarfireEffect(MobEffectCategory.HARMFUL, 0xff7d3d));
	public static final RegistryObject<MobEffect, MobEffect> OBLIVION = MOB_EFFECTS.register("oblivion", () -> new OblivionEffect(MobEffectCategory.BENEFICIAL, 0x7a3b8c));

	public static void loadClass() {
	}
}
