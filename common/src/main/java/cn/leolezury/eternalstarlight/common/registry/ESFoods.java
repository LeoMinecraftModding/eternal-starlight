package cn.leolezury.eternalstarlight.common.registry;

import com.google.common.base.Suppliers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ESFoods {
	public static final Supplier<FoodProperties> JINGLESTEM_CRISP = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(4).saturationModifier(0.8F).build());
	public static final Supplier<FoodProperties> JINGLESTEM_SANDWICH = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(7).saturationModifier(0.8F).build());
	public static final Supplier<FoodProperties> LUNAR_BERRIES = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(3).saturationModifier(0.8F).build());
	public static final Supplier<FoodProperties> BOULDERSHROOM_STEW = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).effect(new MobEffectInstance(ESMobEffects.STICKY.asHolder(), 2400, 0), 0.8f).usingConvertsTo(Items.BOWL).build());
	public static final Supplier<FoodProperties> LUNARIS_CACTUS_FRUIT = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).effect(new MobEffectInstance(MobEffects.GLOWING, 600, 0), 0.8F).effect(new MobEffectInstance(MobEffects.POISON, 200, 0), 0.8F).build());
	public static final Supplier<FoodProperties> LUNARIS_CACTUS_GEL = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(3).saturationModifier(0.1f).build());
	public static final Supplier<FoodProperties> ABYSSAL_FRUIT = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(4).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.GLOWING, 600, 0), 0.3F).effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 400, 0), 0.15F).alwaysEdible().build());
	public static final Supplier<FoodProperties> VELVETUMOSS_BALL = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 400, 0), 0.2F).build());
	public static final Supplier<FoodProperties> CRINOA_BALL = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build());
	public static final Supplier<FoodProperties> CRINOA_PORRIDGE = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).usingConvertsTo(Items.BOWL).build());
	public static final Supplier<FoodProperties> POPPED_NOCTURNAL_MILLET_BUCKET = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(5).saturationModifier(0.8F).fast().build());
	public static final Supplier<FoodProperties> ROASTED_FORGOTTEN_NOCTURNAL_MILLET = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(10).saturationModifier(0.8F).effect(new MobEffectInstance(ESMobEffects.OBLIVION.asHolder(), 300, 0), 1f).alwaysEdible().build());
	public static final Supplier<FoodProperties> PUNGENCY_FRUIT = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(4).saturationModifier(0.6F).effect(new MobEffectInstance(MobEffects.CONFUSION, 120, 0), 0.8F).build());
	public static final Supplier<FoodProperties> PUNGENCY_STEW = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).effect(new MobEffectInstance(MobEffects.CONFUSION, 120, 0), 0.8F).usingConvertsTo(Items.BOWL).build());
	public static final Supplier<FoodProperties> SILVER_PUNGENCY_FRUIT = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(7).saturationModifier(0.6F).effect(new MobEffectInstance(MobEffects.CONFUSION, 120, 0), 0.8F).effect(new MobEffectInstance(ESMobEffects.NUMBNESS.asHolder(), 1200, 0), 1F).alwaysEdible().build());
	public static final Supplier<FoodProperties> ROTTEN_FLESH_JERKY = Suppliers.memoize(() -> new FoodProperties(5, 0.8F, false, 2.5F, Optional.empty(), List.of()));
	public static final Supplier<FoodProperties> ROOKFISH = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(3).saturationModifier(0.1F).build());
	public static final Supplier<FoodProperties> COOKED_ROOKFISH = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(7).saturationModifier(0.3F).build());
	public static final Supplier<FoodProperties> ROOKFISH_SKEWER = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).usingConvertsTo(Items.STICK).build());
	public static final Supplier<FoodProperties> LUMINOFISH = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build());
	public static final Supplier<FoodProperties> COOKED_LUMINOFISH = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build());
	public static final Supplier<FoodProperties> LUMINARIS = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build());
	public static final Supplier<FoodProperties> COOKED_LUMINARIS = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build());
	public static final Supplier<FoodProperties> AURORA_DEER_STEAK = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build());
	public static final Supplier<FoodProperties> COOKED_AURORA_DEER_STEAK = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build());
	public static final Supplier<FoodProperties> RATLIN_MEAT = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build());
	public static final Supplier<FoodProperties> COOKED_RATLIN_MEAT = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build());
	public static final Supplier<FoodProperties> SHADOW_SNAIL_MEAT = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).effect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0), 0.3F).build());
	public static final Supplier<FoodProperties> COOKED_SHADOW_SNAIL_MEAT = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(5).saturationModifier(0.6F).build());
	public static final Supplier<FoodProperties> SHADOW_SNAIL_PIE = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(9).saturationModifier(0.8F).build());
	public static final Supplier<FoodProperties> SHADOW_ESCARGOT = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(10).saturationModifier(0.8F).usingConvertsTo(ESItems.SHADOW_SNAIL_SHELL.get()).build());
	public static final Supplier<FoodProperties> SEEKER_TENTACLE = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(4).saturationModifier(0.1F).fast().build());
	public static final Supplier<FoodProperties> FUNGUS = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(6).saturationModifier(1.2F).build());
	public static final Supplier<FoodProperties> DOOMEDEN_CARRION = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(4).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.8F).build());
	public static final Supplier<FoodProperties> ROTTEN_HAM = Suppliers.memoize(() -> new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).build());
}