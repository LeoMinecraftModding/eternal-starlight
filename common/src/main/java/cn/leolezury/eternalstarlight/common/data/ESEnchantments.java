package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.enchantment.effects.PushTowardsEntity;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.AllOf;
import net.minecraft.world.item.enchantment.effects.ApplyMobEffect;
import net.minecraft.world.item.enchantment.effects.DamageItem;

public class ESEnchantments {
	public static final ResourceKey<Enchantment> POISONING = create("poisoning");
	public static final ResourceKey<Enchantment> FEARLESS = create("fearless");

	public static void bootstrap(BootstrapContext<Enchantment> context) {
		HolderGetter<Item> items = context.lookup(Registries.ITEM);
		context.register(POISONING, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 10, 4, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(12, 11), 1, EquipmentSlotGroup.ARMOR))
			.withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.VICTIM, EnchantmentTarget.ATTACKER, AllOf.entityEffects(new ApplyMobEffect(HolderSet.direct(MobEffects.POISON), LevelBasedValue.constant(2.5F), LevelBasedValue.perLevel(2.5F, 0.5F), LevelBasedValue.constant(0F), LevelBasedValue.perLevel(1F)), new DamageItem(LevelBasedValue.constant(1.0F))))
			.build(POISONING.location()));
		context.register(FEARLESS, Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE), items.getOrThrow(ItemTags.SWORD_ENCHANTABLE), 10, 2, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(21, 11), 1, EquipmentSlotGroup.HAND))
			.withEffect(EnchantmentEffectComponents.KNOCKBACK, new AddValue(LevelBasedValue.perLevel(0.5F)))
			.withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.VICTIM, new PushTowardsEntity(LevelBasedValue.constant(0.4F), LevelBasedValue.perLevel(0.4F, 0.3F)))
			.build(FEARLESS.location()));
	}

	public static ResourceKey<Enchantment> create(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, EternalStarlight.id(name));
	}
}
