package cn.leolezury.eternalstarlight.common.item.loot;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

import java.util.Map;

public class ESLootContextParams {
	public static final LootContextParam<Map<ResourceLocation, Integer>> BOSS_CHALLENGE_COUNTS = create("boss_challenge_counts");

	private static <T> LootContextParam<T> create(String id) {
		return new LootContextParam<>(ResourceLocation.withDefaultNamespace(id));
	}
}
