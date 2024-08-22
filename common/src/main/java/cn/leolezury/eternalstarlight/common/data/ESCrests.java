package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;
import java.util.Optional;

public class ESCrests {
	public static final ResourceKey<Crest> BOULDERS_SHIELD = create("boulders_shield");
	public static final ResourceKey<Crest> GUIDANCE_OF_STARS = create("guidance_of_stars");
	public static final ResourceKey<Crest> BLAZING_BEAM = create("blazing_beam");
	public static final ResourceKey<Crest> BURST_SPARK = create("burst_spark");
	public static final ResourceKey<Crest> FLAMING_AFTERSHOCK = create("flaming_aftershock");
	public static final ResourceKey<Crest> FLAMING_ARC = create("flaming_arc");
	public static final ResourceKey<Crest> FLAMING_RING = create("flaming_ring");
	public static final ResourceKey<Crest> MERGED_FIREBALL = create("merged_fireball");
	public static final ResourceKey<Crest> SURROUNDING_FIREBALLS = create("surrounding_fireballs");
	public static final ResourceKey<Crest> FROZEN_FOG = create("frozen_fog");
	public static final ResourceKey<Crest> ICY_SPIKES = create("icy_spikes");

	public static void bootstrap(BootstrapContext<Crest> context) {
		context.register(BOULDERS_SHIELD, new Crest(
				ManaType.TERRA,
				2,
				EternalStarlight.id("textures/crest/boulders_shield.png"),
				null,
				List.of(new Crest.MobEffectWithLevel(MobEffects.DAMAGE_RESISTANCE, 0, 1)),
				List.of(new Crest.LevelBasedAttributeModifier(
					Attributes.MOVEMENT_SPEED,
					EternalStarlight.id("crest.boulders_shield.speed"),
					-0.007,
					-0.005,
					AttributeModifier.Operation.ADD_VALUE)
				)
			)
		);
		context.register(GUIDANCE_OF_STARS, new Crest(
			ManaType.LUNAR,
			1,
			EternalStarlight.id("textures/crest/guidance_of_stars.png"),
			Optional.ofNullable(ESSpells.GUIDANCE_OF_STARS.get()),
			Optional.empty(),
			Optional.empty()
		));
		context.register(BLAZING_BEAM, new Crest(
			ManaType.BLAZE,
			3,
			EternalStarlight.id("textures/crest/blazing_beam.png"),
			Optional.ofNullable(ESSpells.LASER_BEAM.get()),
			Optional.empty(),
			Optional.empty()
		));
		context.register(BURST_SPARK, new Crest(
			ManaType.BLAZE,
			3,
			EternalStarlight.id("textures/crest/burst_spark.png"),
			Optional.ofNullable(ESSpells.BURST_SPARK.get()),
			Optional.empty(),
			Optional.empty()
		));
		context.register(FLAMING_AFTERSHOCK, new Crest(
			ManaType.BLAZE,
			3,
			EternalStarlight.id("textures/crest/flame_aftershock.png"),
			Optional.ofNullable(ESSpells.FLAMING_AFTERSHOCK.get()),
			Optional.empty(),
			Optional.empty()
		));
		context.register(FLAMING_ARC, new Crest(
			ManaType.BLAZE,
			3,
			EternalStarlight.id("textures/crest/flame_arc.png"),
			Optional.ofNullable(ESSpells.FLAMING_ARC.get()),
			Optional.empty(),
			Optional.empty()
		));
		context.register(FLAMING_RING, new Crest(
			ManaType.BLAZE,
			3,
			EternalStarlight.id("textures/crest/flame_ring.png"),
			Optional.ofNullable(ESSpells.FLAMING_RING.get()),
			Optional.empty(),
			Optional.empty()
		));
		context.register(MERGED_FIREBALL, new Crest(
			ManaType.BLAZE,
			3,
			EternalStarlight.id("textures/crest/merged_fireball.png"),
			Optional.ofNullable(ESSpells.MERGED_FIREBALL.get()),
			Optional.empty(),
			Optional.empty()
		));
		context.register(SURROUNDING_FIREBALLS, new Crest(
			ManaType.BLAZE,
			3,
			EternalStarlight.id("textures/crest/surrounded_fireballs.png"),
			Optional.ofNullable(ESSpells.SURROUNDING_FIREBALLS.get()),
			Optional.empty(),
			Optional.empty()
		));
		context.register(FROZEN_FOG, new Crest(
			ManaType.WATER,
			3,
			EternalStarlight.id("textures/crest/frozen_fog.png"),
			Optional.ofNullable(ESSpells.FROZEN_FOG.get()),
			Optional.empty(),
			Optional.empty()
		));
		context.register(ICY_SPIKES, new Crest(
			ManaType.WATER,
			3,
			EternalStarlight.id("textures/crest/icy_spikes.png"),
			Optional.ofNullable(ESSpells.ICY_SPIKES.get()),
			Optional.empty(),
			Optional.empty()
		));
	}

	public static ResourceKey<Crest> create(String name) {
		return ResourceKey.create(ESRegistries.CREST, EternalStarlight.id(name));
	}
}
