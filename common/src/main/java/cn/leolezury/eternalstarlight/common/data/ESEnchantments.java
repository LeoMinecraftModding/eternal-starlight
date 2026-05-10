package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.enchantment.effect.Freeze;
import cn.leolezury.eternalstarlight.common.enchantment.effect.IgniteAbyssalFire;
import cn.leolezury.eternalstarlight.common.enchantment.effect.PushTowardsEntity;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESEnchantmentEffectComponents;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import org.apache.commons.lang3.mutable.MutableFloat;

public class ESEnchantments {
	public static final ResourceKey<Enchantment> POISONING = create("poisoning");
	public static final ResourceKey<Enchantment> FEARLESS = create("fearless");
	public static final ResourceKey<Enchantment> SOUL_SNATCHER = create("soul_snatcher");
	public static final ResourceKey<Enchantment> TRACING = create("tracing");
	public static final ResourceKey<Enchantment> TEARING = create("tearing");
	public static final ResourceKey<Enchantment> OVERHEAT = create("overheat");
	public static final ResourceKey<Enchantment> GLACIAL_SOWING = create("glacial_sowing");
	public static final ResourceKey<Enchantment> FERTILE = create("fertile");
	public static final ResourceKey<Enchantment> PRECISION = create("precision");
	public static final ResourceKey<Enchantment> HOMING = create("homing");
	public static final ResourceKey<Enchantment> GATHERING = create("gathering");
	public static final ResourceKey<Enchantment> SWIFT_LASH = create("swift_lash");
	public static final ResourceKey<Enchantment> ABYSSAL_TOUCH = create("abyssal_touch");

	public static void bootstrap(BootstrapContext<Enchantment> context) {
		HolderGetter<Item> items = context.lookup(Registries.ITEM);
		HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
		context.register(POISONING, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 10, 4, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(12, 11), 1, EquipmentSlotGroup.ARMOR))
			.withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.VICTIM, EnchantmentTarget.ATTACKER, new ApplyMobEffect(HolderSet.direct(MobEffects.POISON), LevelBasedValue.constant(2.5F), LevelBasedValue.perLevel(2.5F, 0.5F), LevelBasedValue.constant(0F), LevelBasedValue.perLevel(1F)))
			.build(POISONING.location()));
		context.register(FEARLESS, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE), items.getOrThrow(ItemTags.SWORD_ENCHANTABLE), 10, 2, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(21, 11), 1, EquipmentSlotGroup.MAINHAND))
			.withEffect(EnchantmentEffectComponents.KNOCKBACK, new AddValue(LevelBasedValue.perLevel(0.5F)))
			.withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new PushTowardsEntity(LevelBasedValue.constant(0.1F), LevelBasedValue.perLevel(0.1F, 0.2F)))
			.build(FEARLESS.location()));
		context.register(SOUL_SNATCHER, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ESTags.Items.CHAIN_OF_SOULS_ENCHANTABLE), 10, 3, Enchantment.dynamicCost(25, 25), Enchantment.dynamicCost(75, 25), 1, EquipmentSlotGroup.MAINHAND))
			.withEffect(EnchantmentEffectComponents.DAMAGE, new AddValue(LevelBasedValue.perLevel(0.5F)), LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(ESEntities.CHAIN_OF_SOULS.get())))
			.build(SOUL_SNATCHER.location()));
		context.register(TRACING, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ESTags.Items.CHAIN_OF_SOULS_ENCHANTABLE), items.getOrThrow(ESTags.Items.CHAIN_OF_SOULS_ENCHANTABLE), 5, 2, Enchantment.dynamicCost(20, 11), Enchantment.dynamicCost(30, 11), 1, EquipmentSlotGroup.MAINHAND))
			.withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new ApplyMobEffect(HolderSet.direct(MobEffects.GLOWING), LevelBasedValue.constant(4.5F), LevelBasedValue.perLevel(4.5F, 2.5F), LevelBasedValue.constant(0F), LevelBasedValue.constant(0F)), LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(ESEntities.CHAIN_OF_SOULS.get())))
			.build(TRACING.location()));
		context.register(TEARING, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ESTags.Items.PUNGENCY_FRUIT_SPEAR_ENCHANTABLE), items.getOrThrow(ESTags.Items.PUNGENCY_FRUIT_SPEAR_ENCHANTABLE), 10, 1, Enchantment.dynamicCost(25, 25), Enchantment.dynamicCost(75, 25), 1, EquipmentSlotGroup.MAINHAND))
			.withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new ApplyMobEffect(HolderSet.direct(ESMobEffects.TEARY.asHolder()), LevelBasedValue.constant(2.5F), LevelBasedValue.perLevel(2.5F, 0.5F), LevelBasedValue.constant(0F), LevelBasedValue.constant(0F)), LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(ESEntities.PUNGENCY_FRUIT_SPEAR.get())))
			.withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.DAMAGING_ENTITY, new SpawnParticlesEffect(ESSmokeParticleOptions.PUNGENCY_FRUIT, SpawnParticlesEffect.inBoundingBox(), SpawnParticlesEffect.inBoundingBox(), SpawnParticlesEffect.fixedVelocity(ConstantFloat.of(0)), SpawnParticlesEffect.fixedVelocity(ConstantFloat.of(0)), ConstantFloat.of(0)), LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(ESEntities.PUNGENCY_FRUIT_SPEAR.get())))
			.build(TEARING.location()));
		context.register(OVERHEAT, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ESTags.Items.SEEDS_LAUNCHER_ENCHANTABLE), items.getOrThrow(ESTags.Items.SEEDS_LAUNCHER_ENCHANTABLE), 5, 2, Enchantment.dynamicCost(20, 11), Enchantment.dynamicCost(30, 11), 2, EquipmentSlotGroup.MAINHAND))
			.withEffect(EnchantmentEffectComponents.PROJECTILE_SPAWNED, new Ignite(LevelBasedValue.perLevel(100.0F)))
			.exclusiveWith(enchantments.getOrThrow(ESTags.Enchantments.SEEDS_LAUNCHER_EXCLUSIVE))
			.build(OVERHEAT.location()));
		context.register(GLACIAL_SOWING, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ESTags.Items.SEEDS_LAUNCHER_ENCHANTABLE), items.getOrThrow(ESTags.Items.SEEDS_LAUNCHER_ENCHANTABLE), 5, 2, Enchantment.dynamicCost(20, 11), Enchantment.dynamicCost(30, 11), 2, EquipmentSlotGroup.MAINHAND))
			.withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new Freeze(LevelBasedValue.perLevel(4F)), LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(ESEntities.SHOT_SEEDS.get())))
			.exclusiveWith(enchantments.getOrThrow(ESTags.Enchantments.SEEDS_LAUNCHER_EXCLUSIVE))
			.build(GLACIAL_SOWING.location()));
		context.register(FERTILE, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ESTags.Items.SEEDS_LAUNCHER_ENCHANTABLE), items.getOrThrow(ESTags.Items.SEEDS_LAUNCHER_ENCHANTABLE), 2, 3, Enchantment.dynamicCost(20, 11), Enchantment.dynamicCost(30, 11), 1, EquipmentSlotGroup.MAINHAND))
			.withEffect(EnchantmentEffectComponents.PROJECTILE_COUNT, new AddValue(LevelBasedValue.perLevel(2.0F)))
			.build(FERTILE.location()));
		context.register(PRECISION, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ESTags.Items.BOOMERANG_ENCHANTABLE), items.getOrThrow(ESTags.Items.BOOMERANG_ENCHANTABLE), 10, 5, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(21, 11), 1, EquipmentSlotGroup.MAINHAND))
			.withEffect(ESEnchantmentEffectComponents.BOOMERANG_CRIT_CHANCE.get(), new AddValue(LevelBasedValue.perLevel(0.16F)))
			.build(PRECISION.location()));
		context.register(HOMING, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ESTags.Items.BOOMERANG_ENCHANTABLE), items.getOrThrow(ESTags.Items.BOOMERANG_ENCHANTABLE), 4, 3, Enchantment.dynamicCost(15, 11), Enchantment.dynamicCost(21, 11), 2, EquipmentSlotGroup.MAINHAND))
			.withEffect(ESEnchantmentEffectComponents.BOOMERANG_HOMING_STRENGTH.get(), new AddValue(LevelBasedValue.perLevel(0.1F, 0.75F)))
			.build(HOMING.location()));
		context.register(GATHERING, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ESTags.Items.BOOMERANG_ENCHANTABLE), items.getOrThrow(ESTags.Items.BOOMERANG_ENCHANTABLE), 3, 2, Enchantment.dynamicCost(20, 11), Enchantment.dynamicCost(30, 11), 4, EquipmentSlotGroup.MAINHAND))
			.withEffect(ESEnchantmentEffectComponents.BOOMERANG_PICKUP_RADIUS.get(), new AddValue(LevelBasedValue.perLevel(1.0F, 0.75F)))
			.build(GATHERING.location()));
		context.register(SWIFT_LASH, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ESTags.Items.WHIP_ENCHANTABLE), items.getOrThrow(ESTags.Items.WHIP_ENCHANTABLE), 10, 5, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(21, 11), 1, EquipmentSlotGroup.MAINHAND))
			.withEffect(EnchantmentEffectComponents.ATTRIBUTES, new EnchantmentAttributeEffect(EternalStarlight.id("enchantment.swift_lash"), Attributes.ATTACK_SPEED, LevelBasedValue.perLevel(0.2F), AttributeModifier.Operation.ADD_VALUE))
			.build(SWIFT_LASH.location()));
		context.register(ABYSSAL_TOUCH, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ESTags.Items.WHIP_ENCHANTABLE), items.getOrThrow(ESTags.Items.WHIP_ENCHANTABLE), 6, 2, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(21, 11), 1, EquipmentSlotGroup.MAINHAND))
			.withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new IgniteAbyssalFire(LevelBasedValue.perLevel(5.0F)))
			.build(ABYSSAL_TOUCH.location()));
	}

	public static ResourceKey<Enchantment> create(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, EternalStarlight.id(name));
	}

	public static float modifyBoomerangCritChance(ServerLevel level, ItemStack tool, Entity entity, DamageSource damageSource, float boomerangCritChance) {
		MutableFloat mutablefloat = new MutableFloat(boomerangCritChance);
		EnchantmentHelper.runIterationOnItem(
			tool, (holder, enchantmentLevel) -> modifyBoomerangCritChance(holder.value(), level, enchantmentLevel, tool, entity, damageSource, mutablefloat)
		);
		return mutablefloat.floatValue();
	}

	public static void modifyBoomerangCritChance(Enchantment enchantment, ServerLevel level, int enchantmentLevel, ItemStack tool, Entity entity, DamageSource damageSource, MutableFloat boomerangCritChance) {
		enchantment.modifyDamageFilteredValue(ESEnchantmentEffectComponents.BOOMERANG_CRIT_CHANCE.get(), level, enchantmentLevel, tool, entity, damageSource, boomerangCritChance);
	}

	public static float modifyBoomerangHomingStrength(ServerLevel level, ItemStack tool, float boomerangHomingStrength) {
		MutableFloat mutablefloat = new MutableFloat(boomerangHomingStrength);
		EnchantmentHelper.runIterationOnItem(
			tool, (holder, enchantmentLevel) -> modifyBoomerangHomingStrength(holder.value(), level, enchantmentLevel, tool, mutablefloat)
		);
		return mutablefloat.floatValue();
	}

	public static void modifyBoomerangHomingStrength(Enchantment enchantment, ServerLevel level, int enchantmentLevel, ItemStack tool, MutableFloat boomerangHomingStrength) {
		enchantment.modifyItemFilteredCount(ESEnchantmentEffectComponents.BOOMERANG_HOMING_STRENGTH.get(), level, enchantmentLevel, tool, boomerangHomingStrength);
	}

	public static float modifyBoomerangPickupRadius(ServerLevel level, ItemStack tool, float boomerangPickupRadius) {
		MutableFloat mutablefloat = new MutableFloat(boomerangPickupRadius);
		EnchantmentHelper.runIterationOnItem(
			tool, (holder, enchantmentLevel) -> modifyBoomerangPickupRadius(holder.value(), level, enchantmentLevel, tool, mutablefloat)
		);
		return mutablefloat.floatValue();
	}

	public static void modifyBoomerangPickupRadius(Enchantment enchantment, ServerLevel level, int enchantmentLevel, ItemStack tool, MutableFloat boomerangPickupRadius) {
		enchantment.modifyItemFilteredCount(ESEnchantmentEffectComponents.BOOMERANG_PICKUP_RADIUS.get(), level, enchantmentLevel, tool, boomerangPickupRadius);
	}
}
