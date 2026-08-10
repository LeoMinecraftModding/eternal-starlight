package cn.leolezury.eternalstarlight.common.item.loot;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

import java.util.Optional;
import java.util.Set;

// TODO REGISTER
public record BossChallengeCountCondition(ResourceLocation bossId, Optional<Integer> minInclusive, Optional<Integer> maxInclusive) implements LootItemCondition {
	public static final MapCodec<BossChallengeCountCondition> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		ResourceLocation.CODEC.fieldOf("boss_id").forGetter(BossChallengeCountCondition::bossId),
		Codec.INT.optionalFieldOf("min_inclusive").forGetter(BossChallengeCountCondition::minInclusive),
		Codec.INT.optionalFieldOf("max_inclusive").forGetter(BossChallengeCountCondition::maxInclusive)
	).apply(instance, BossChallengeCountCondition::new));

	@Override
	public LootItemConditionType getType() {
		// TODO FIXME
		return LootItemConditions.ENTITY_PROPERTIES;
	}

	@Override
	public Set<LootContextParam<?>> getReferencedContextParams() {
		// TODO FIXME NEW LOOT PARAM
		return ImmutableSet.of(LootContextParams.ORIGIN, LootContextParams.THIS_ENTITY);
	}

	// TODO LOGIC
	@Override
	public boolean test(LootContext arg) {
		return true;
	}
}
