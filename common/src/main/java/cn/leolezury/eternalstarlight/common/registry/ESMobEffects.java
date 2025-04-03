package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.effect.CrystallineInfectionEffect;
import cn.leolezury.eternalstarlight.common.effect.DreamCatcherEffect;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ESMobEffects {
	public static final RegistrationProvider<MobEffect> MOB_EFFECTS = RegistrationProvider.get(Registries.MOB_EFFECT, EternalStarlight.ID);
	public static final RegistryObject<MobEffect, MobEffect> CRYSTALLINE_INFECTION = MOB_EFFECTS.register("crystalline_infection", () -> new CrystallineInfectionEffect(MobEffectCategory.HARMFUL, 8001164));
	public static final RegistryObject<MobEffect, MobEffect> DREAM_CATCHER = MOB_EFFECTS.register("dream_catcher", () -> new DreamCatcherEffect(MobEffectCategory.BENEFICIAL, 0x5187c4));
	public static final RegistryObject<MobEffect, MobEffect> STICKY = MOB_EFFECTS.register("sticky", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xffb6af).addAttributeModifier(Attributes.MOVEMENT_SPEED, EternalStarlight.id("effect.sticky"), -0.06, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
	public static final RegistryObject<MobEffect, MobEffect> FLAMMABLE = MOB_EFFECTS.register("flammable", () -> new MobEffect(MobEffectCategory.HARMFUL, 0xd9737c));
	public static final RegistryObject<MobEffect, MobEffect> NUMBNESS = MOB_EFFECTS.register("numbness", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x573a45));
	public static final RegistryObject<MobEffect, MobEffect> TEARY = MOB_EFFECTS.register("teary", () -> new MobEffect(MobEffectCategory.HARMFUL, 0x9fc3c3));

	public static void loadClass() {
	}
}
