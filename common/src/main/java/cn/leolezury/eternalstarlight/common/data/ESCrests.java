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

	public static void bootstrap(BootstrapContext<Crest> context) {
		context.register(BOULDERS_SHIELD, new Crest(ManaType.TERRA, 2, EternalStarlight.id("textures/crest/boulders_shield.png"), null, List.of(new Crest.MobEffectWithLevel(MobEffects.DAMAGE_RESISTANCE, 0, 1)), List.of(new Crest.LevelBasedAttributeModifier(Attributes.MOVEMENT_SPEED, EternalStarlight.id("crest.boulders_shield.speed"), -0.007, -0.005, AttributeModifier.Operation.ADD_VALUE))));
		context.register(GUIDANCE_OF_STARS, new Crest(ManaType.LUNAR, 1, EternalStarlight.id("textures/crest/guidance_of_stars.png"), Optional.ofNullable(ESSpells.GUIDANCE_OF_STARS.get()), Optional.empty(), Optional.empty()));
		context.register(BLAZING_BEAM, new Crest(ManaType.BLAZE, 3, EternalStarlight.id("textures/crest/blazing_beam.png"), Optional.ofNullable(ESSpells.LASER_BEAM.get()), Optional.empty(), Optional.empty()));
	}

	public static ResourceKey<Crest> create(String name) {
		return ResourceKey.create(ESRegistries.CREST, EternalStarlight.id(name));
	}
}
