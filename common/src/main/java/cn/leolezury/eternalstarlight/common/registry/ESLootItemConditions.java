package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.loot.BossChallengeCountCondition;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class ESLootItemConditions {
	public static final RegistrationProvider<LootItemConditionType> LOOT_ITEM_CONDITIONS = RegistrationProvider.get(Registries.LOOT_CONDITION_TYPE, EternalStarlight.ID);
	public static final RegistryObject<LootItemConditionType, LootItemConditionType> BOSS_CHALLENGE_COUNT = LOOT_ITEM_CONDITIONS.register("boss_challenge_count", () -> new LootItemConditionType(BossChallengeCountCondition.CODEC));

	public static void loadClass() {
	}
}
