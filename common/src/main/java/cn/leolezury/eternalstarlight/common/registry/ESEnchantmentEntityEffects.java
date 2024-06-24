package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.enchantment.effects.PushTowardsEntity;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class ESEnchantmentEntityEffects {
	public static final RegistrationProvider<MapCodec<? extends EnchantmentEntityEffect>> ENCHANTMENT_ENTITY_EFFECTS = RegistrationProvider.get(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, EternalStarlight.ID);
	public static final RegistryObject<MapCodec<? extends EnchantmentEntityEffect>, MapCodec<PushTowardsEntity>> PUSH_TOWARDS_ENTITY = ENCHANTMENT_ENTITY_EFFECTS.register("push_towards_entity", () -> PushTowardsEntity.CODEC);

	public static void loadClass() {
	}
}
