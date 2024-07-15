package cn.leolezury.eternalstarlight.common.crest;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.List;
import java.util.Optional;

public record Crest(ManaType type, int maxLevel, ResourceLocation texture, Optional<AbstractSpell> spell, Optional<List<MobEffectWithLevel>> effects, Optional<List<LevelBasedAttributeModifier>> attributeModifiers) {
	public Crest(ManaType type, int maxLevel, ResourceLocation texture, AbstractSpell spell, List<MobEffectWithLevel> effects, List<LevelBasedAttributeModifier> attributeModifiers) {
		this(type, maxLevel, texture, Optional.ofNullable(spell), Optional.of(effects), Optional.of(attributeModifiers));
	}

	public static final Codec<Crest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ManaType.CODEC.fieldOf("type").forGetter(Crest::type),
		Codec.INT.fieldOf("max_level").forGetter(Crest::maxLevel),
		ResourceLocation.CODEC.fieldOf("texture").forGetter(Crest::texture),
		ESSpells.CODEC.optionalFieldOf("spell").forGetter(Crest::spell),
		MobEffectWithLevel.CODEC.listOf().optionalFieldOf("mob_effects").forGetter(Crest::effects),
		LevelBasedAttributeModifier.CODEC.listOf().optionalFieldOf("attribute_modifiers").forGetter(Crest::attributeModifiers)
	).apply(instance, Crest::new));

	public record MobEffectWithLevel(Holder<MobEffect> effect, int level, int levelAddition) {
		public static final Codec<MobEffectWithLevel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("effect").forGetter(MobEffectWithLevel::effect),
			Codec.INT.fieldOf("level").forGetter(MobEffectWithLevel::level),
			Codec.INT.fieldOf("level_addition").forGetter(MobEffectWithLevel::levelAddition)
		).apply(instance, MobEffectWithLevel::new));
	}

	public record LevelBasedAttributeModifier(Holder<Attribute> attribute, ResourceLocation id, double amount, double amountAddition, AttributeModifier.Operation operation) {
		public static final Codec<LevelBasedAttributeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BuiltInRegistries.ATTRIBUTE.holderByNameCodec().fieldOf("attribute").forGetter(LevelBasedAttributeModifier::attribute),
			ResourceLocation.CODEC.fieldOf("id").forGetter(LevelBasedAttributeModifier::id),
			Codec.DOUBLE.fieldOf("amount").forGetter(LevelBasedAttributeModifier::amount),
			Codec.DOUBLE.fieldOf("amount_addition").forGetter(LevelBasedAttributeModifier::amountAddition),
			AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(LevelBasedAttributeModifier::operation)
		).apply(instance, LevelBasedAttributeModifier::new));

		public AttributeModifier getModifier(int level) {
			return new AttributeModifier(getModifierId(level), amount() + (level - 1) * amountAddition(), operation());
		}

		public ResourceLocation getModifierId(int level) {
			return id().withSuffix("_level_" + level);
		}
	}

	public record Instance(Holder<Crest> crest, int level) {
		public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryFixedCodec.create(ESRegistries.CREST).fieldOf("crest").forGetter(Instance::crest),
			Codec.INT.fieldOf("level").forGetter(Instance::level)
		).apply(instance, Instance::new));

		public static Optional<Instance> of(RegistryAccess access, ResourceKey<Crest> key, int level) {
			Registry<Crest> registry = access.registryOrThrow(ESRegistries.CREST);
			Optional<Holder.Reference<Crest>> holder = registry.getHolder(key);
			return holder.map(ref -> new Instance(ref, Math.min(level, ref.value().maxLevel())));
		}
	}

	public record Set(List<Instance> crests) {
		public static final Codec<Set> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Instance.CODEC.listOf().fieldOf("crests").forGetter(Set::crests)
		).apply(instance, Set::new));
	}
}