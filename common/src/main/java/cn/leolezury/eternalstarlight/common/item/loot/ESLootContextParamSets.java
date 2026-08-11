package cn.leolezury.eternalstarlight.common.item.loot;

import net.minecraft.Util;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class ESLootContextParamSets {
	public static final LootContextParamSet BOSS = Util.make(() -> new LootContextParamSet.Builder()
		.required(LootContextParams.THIS_ENTITY)
		.required(LootContextParams.ORIGIN)
		.required(ESLootContextParams.BOSS_CHALLENGE_COUNTS)
		.build());
}
