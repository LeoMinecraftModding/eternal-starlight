package cn.leolezury.eternalstarlight.common.item.loot;

import cn.leolezury.eternalstarlight.common.registry.ESLootItemConditions;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public record BossChallengeCountCondition(ResourceLocation bossId, Optional<Integer> minInclusive, Optional<Integer> maxInclusive) implements LootItemCondition {
	public static final MapCodec<BossChallengeCountCondition> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		ResourceLocation.CODEC.fieldOf("boss_id").forGetter(BossChallengeCountCondition::bossId),
		Codec.INT.optionalFieldOf("min_inclusive").forGetter(BossChallengeCountCondition::minInclusive),
		Codec.INT.optionalFieldOf("max_inclusive").forGetter(BossChallengeCountCondition::maxInclusive)
	).apply(instance, BossChallengeCountCondition::new));

	@Override
	public LootItemConditionType getType() {
		return ESLootItemConditions.BOSS_CHALLENGE_COUNT.get();
	}

	@Override
	public Set<LootContextParam<?>> getReferencedContextParams() {
		return ImmutableSet.of(ESLootContextParams.BOSS_CHALLENGE_COUNTS);
	}

	@Override
	public boolean test(LootContext context) {
		Map<ResourceLocation, Integer> counts = context.getParamOrNull(ESLootContextParams.BOSS_CHALLENGE_COUNTS);
		if (counts != null) {
			int count = counts.getOrDefault(bossId, 0);
			boolean result = true;
			if (minInclusive.isPresent()) {
				result = count >= minInclusive.get();
			}
			if (maxInclusive.isPresent()) {
				result = result && count <= maxInclusive.get();
			}
			return result;
		}
		return false;
	}

	public static LootItemCondition.Builder min(ResourceLocation bossId, int min) {
		return () -> new BossChallengeCountCondition(bossId, Optional.of(min), Optional.empty());
	}

	public static LootItemCondition.Builder max(ResourceLocation bossId, int max) {
		return () -> new BossChallengeCountCondition(bossId, Optional.empty(), Optional.of(max));
	}

	public static LootItemCondition.Builder range(ResourceLocation bossId, int min, int max) {
		return () -> new BossChallengeCountCondition(bossId, Optional.of(min), Optional.of(max));
	}
}
