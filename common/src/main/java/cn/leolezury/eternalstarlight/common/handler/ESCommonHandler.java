package cn.leolezury.eternalstarlight.common.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.fluid.EtherFluid;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.data.ESPaintingVariants;
import cn.leolezury.eternalstarlight.common.entity.attack.CrystalCluster;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.entity.projectile.EnergySpark;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownStarfire;
import cn.leolezury.eternalstarlight.common.entity.projectile.WiltedPetal;
import cn.leolezury.eternalstarlight.common.item.armor.GlaciteArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringstoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.combat.DualWieldingSwordItem;
import cn.leolezury.eternalstarlight.common.item.combat.HammerItem;
import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherAmmoType;
import cn.leolezury.eternalstarlight.common.item.component.Accessory;
import cn.leolezury.eternalstarlight.common.item.interfaces.SwingAttackWeapon;
import cn.leolezury.eternalstarlight.common.item.interfaces.TickableArmor;
import cn.leolezury.eternalstarlight.common.item.misc.ManaCrystalItem;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.network.SimpleActionPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateWeatherPacket;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.*;
import cn.leolezury.eternalstarlight.common.resource.gatekeeper.TheGatekeeperNameManager;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.*;
import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import cn.leolezury.eternalstarlight.common.weather.WeatherInstance;
import cn.leolezury.eternalstarlight.common.weather.Weathers;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class ESCommonHandler {
	public static final String STARFIRE_ARROW = EternalStarlight.ID + ":starfire";
	public static final String FLOWGLAZE_ARROW = EternalStarlight.ID + ":flowglaze";
	public static final String CRYSTAL_ARROW = EternalStarlight.ID + ":crystal";
	public static final String MECHANICAL_ARROW = EternalStarlight.ID + ":mechanical";
	public static final String STARFALL_ARROW = EternalStarlight.ID + ":starfall";
	public static final String WILTED_ARROW = EternalStarlight.ID + ":wilted";
	public static TheGatekeeperNameManager gatekeeperNames;
	private static Weathers starlightWeathers;
	private static AbstractWeather lastWeather;

	public static String getGatekeeperName() {
		return gatekeeperNames.getTheGatekeeperName();
	}

	public static Optional<WeatherInstance> getActiveWeather() {
		if (starlightWeathers == null) {
			return Optional.empty();
		}
		return starlightWeathers.getActiveWeather();
	}

	private static final AttributeModifier AMARAMBER_BONUS = new AttributeModifier(EternalStarlight.id("armor.amaramber_bonus"), 7, AttributeModifier.Operation.ADD_VALUE);

	public static void onServerTick(MinecraftServer server) {

	}

	public static void onLevelLoad(ServerLevel serverLevel) {
		if (serverLevel.dimension() == ESDimensions.STARLIGHT_KEY) {
			starlightWeathers = ESWeatherUtil.getOrCreateWeathers(serverLevel);
		}
	}

	public static void onLevelTick(ServerLevel serverLevel) {
		if (serverLevel.dimension() == ESDimensions.STARLIGHT_KEY && starlightWeathers != null) {
			starlightWeathers.tick();
			long gameTime = serverLevel.getGameTime();
			starlightWeathers.getActiveWeather().ifPresentOrElse((weatherInstance -> {
				if (weatherInstance.getWeather() != lastWeather || gameTime % 200 == 0) {
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new UpdateWeatherPacket(weatherInstance.getWeather()));
					lastWeather = weatherInstance.getWeather();
				}
				if (gameTime % 80 == 0) {
					for (ServerPlayer player : serverLevel.players()) {
						if (serverLevel.canSeeSky(BlockPos.containing(player.getEyePosition()))) {
							ESCriteriaTriggers.WITNESS_WEATHER.get().trigger(player);
						}
					}
				}
			}), () -> {
				if (lastWeather != null || gameTime % 200 == 0) {
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new SimpleActionPacket(SimpleActionPacket.S2C_CLEAR_WEATHER));
					lastWeather = null;
				}
			});
		}
	}

	public static void onPlayerJoin(Player player) {
		if (ESConfig.INSTANCE.startWithGuidebook && !player.level().isClientSide && !ESDataAttachments.RECEIVED_GUIDEBOOK.getData(player)) {
			ESDataAttachments.RECEIVED_GUIDEBOOK.setData(player, true);
			ESEntityUtil.givePlayerItem(player, ESItems.BOOK.get().getDefaultInstance());
		}
	}

	public static void onItemTooltip(Player player, TooltipFlag flags, ItemStack itemStack, List<Component> tooltip, Item.TooltipContext context) {
		HolderLookup.Provider lookup = context.registries();
		Accessory accessory = itemStack.get(ESDataComponents.ACCESSORY.get());
		// copied from ItemStack#addAttributeTooltips
		if (accessory != null) {
			if ((accessory.attributeModifiers().showInTooltip() && !accessory.attributeModifiers().modifiers().isEmpty()) || !accessory.extraDescription().isEmpty()) {
				tooltip.add(CommonComponents.EMPTY);
				tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".accessory_combined").withStyle(ChatFormatting.GRAY));
			}
			if (accessory.attributeModifiers().showInTooltip()) {
				for (EquipmentSlotGroup slotGroup : EquipmentSlotGroup.values()) {
					accessory.attributeModifiers().forEach(slotGroup, (holder, modifier) -> itemStack.addModifierTooltip(tooltip::add, player, holder, modifier));
				}
			}
			for (Component desc : accessory.extraDescription()) {
				tooltip.add(Component.literal(" ").append(desc));
			}
			tooltip.add(CommonComponents.EMPTY);
			tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".accessory_combination_target").withStyle(ChatFormatting.GRAY));
			tooltip.add(Component.literal(" ").append(accessory.combinationTargetDescription()));
		}
		if (itemStack.has(ESDataComponents.ACCESSORIES.get())) {
			List<ItemStack> accessories = itemStack.getOrDefault(ESDataComponents.ACCESSORIES.get(), new ArrayList<>());
			if (!accessories.isEmpty()) {
				tooltip.add(CommonComponents.EMPTY);
				tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".accessories").withStyle(ChatFormatting.GRAY));
				accessories.forEach(accessoryStack -> {
					Accessory data = accessoryStack.get(ESDataComponents.ACCESSORY.get());
					MutableComponent name = Component.literal(" ").append(accessoryStack.getHoverName());
					if (data != null && data.nameStyle().isPresent()) {
						name.withStyle(data.nameStyle().get());
					}
					tooltip.add(name);
					if (data != null) {
						for (Component desc : data.extraDescription()) {
							tooltip.add(Component.literal(" ").append(desc));
						}
					}
				});
			}
		}
		int accessorySlotCount = itemStack.getOrDefault(ESDataComponents.ACCESSORY_SLOT_COUNT.get(), 1);
		if (accessorySlotCount > 1) {
			tooltip.add(CommonComponents.EMPTY);
			tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".accessory_slot_count", accessorySlotCount).withStyle(ChatFormatting.BLUE));
		}
		if (itemStack.is(ESTags.Items.FLOWGLAZE_WEAPONS)) {
			tooltip.add(CommonComponents.EMPTY);
			tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".flowglaze_weapon").withColor(0x8ed6b0));
			tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".flowglaze_tool").withColor(0x8ed6b0));
		}
		if (itemStack.is(ESItems.FLOWGLAZE_BOW.get())) {
			tooltip.add(CommonComponents.EMPTY);
			tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".flowglaze_bow").withColor(0x8ed6b0));
		}
		if (itemStack.is(ESItems.FLOWGLAZE_SHIELD.get())) {
			tooltip.add(CommonComponents.EMPTY);
			tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".flowglaze_shield").withColor(0x8ed6b0));
		}
		if (player != null && lookup != null && itemStack.is(ESTags.Items.SEEDS_LAUNCHER_AMMO) && player.getInventory().contains(stack -> stack.is(ESItems.SEEDS_LAUNCHER.get()))) {
			tooltip.add(CommonComponents.EMPTY);
			SeedsLauncherAmmoType type = SeedsLauncherAmmoType.getAmmoType(lookup, itemStack.getItem()).value();
			tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".seeds_launcher.ammo").withStyle(ChatFormatting.GRAY));
			String damage = Math.round((type.damageMultiplier() - 1) * 100) + "%";
			if (!damage.startsWith("-")) {
				damage = "+" + damage;
			}
			String speed = Math.round((type.speedMultiplier() - 1) * 100) + "%";
			if (!speed.startsWith("-")) {
				speed = "+" + speed;
			}
			if (!damage.equals("+0%")) {
				tooltip.add(Component.literal(" ").append(Component.translatable("tooltip." + EternalStarlight.ID + ".seeds_launcher.damage_multiplier", damage).withStyle(damage.startsWith("-") ? ChatFormatting.RED : ChatFormatting.BLUE)));
			}
			if (!speed.equals("+0%")) {
				tooltip.add(Component.literal(" ").append(Component.translatable("tooltip." + EternalStarlight.ID + ".seeds_launcher.speed_multiplier", speed).withStyle(speed.startsWith("-") ? ChatFormatting.RED : ChatFormatting.BLUE)));
			}
			tooltip.add(Component.literal(" ").append(Component.translatable("tooltip." + EternalStarlight.ID + ".seeds_launcher.cooldown", type.cooldown()).withStyle(ChatFormatting.DARK_GREEN)));
		}
		if (itemStack.is(ESItems.UNDERMINER.get())) {
			tooltip.add(CommonComponents.EMPTY);
			tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".underminer").withColor(0x47adc4));
		}
	}

	public static boolean onAllowLivingHurt(LivingEntity entity, DamageSource source, float amount) {
		if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ESItems.UNREALIUM_HELMET.get()) && source.is(DamageTypes.IN_WALL)) {
			return false;
		}
		if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ESItems.UNREALIUM_CHESTPLATE.get()) && source.is(DamageTypes.CRAMMING)) {
			return false;
		}
		return true;
	}

	public static float onModifyLivingActualHurtDamage(LivingEntity entity, DamageSource source, float amount) {
		float modified = amount;
		Set<Item> activeAccessories = ESAccessoryUtil.getActiveAccessoriesOnArmors(entity);
		if (activeAccessories.contains(ESItems.CRESCENT_PENDANT.get()) && !source.is(ESTags.DamageTypes.BYPASSES_CRESCENT_PENDANT) && modified > entity.getMaxHealth() * 0.75f) {
			modified = entity.getMaxHealth() * 0.75f;
		}
		if (entity.hasEffect(ESMobEffects.NUMBNESS.asHolder())) {
			ESDataAttachments.NUMBNESS_DAMAGE.setData(entity, ESDataAttachments.NUMBNESS_DAMAGE.getData(entity) + modified * 0.75f);
			modified *= 0.25f;
		}
		if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return Math.max(amount, modified);
		} else {
			return modified;
		}
	}

	public static float onModifyLivingHurtDamage(LivingEntity entity, DamageSource source, float amount) {
		float modified = amount;
		Entity sourceEntity = source.getEntity();
		if (sourceEntity != null) {
			if (sourceEntity.getType() == ESEntities.THE_GATEKEEPER.get() && entity instanceof ServerPlayer serverPlayer && TheGatekeeper.isPlayerPermitted(serverPlayer)) {
				modified *= (1 + Mth.clamp(ESDataAttachments.GATEKEEPER_CHALLENGE_COUNT.getData(entity), 0, 40) * 0.05f);
			}
			if (sourceEntity.getType() == ESEntities.STARLIGHT_GOLEM.get()) {
				modified *= (float) ESConfig.INSTANCE.mobsConfig.starlightGolem.attackDamageScale();
			}
			if (sourceEntity.getType() == ESEntities.LUNAR_MONSTROSITY.get()) {
				modified *= (float) ESConfig.INSTANCE.mobsConfig.lunarMonstrosity.attackDamageScale();
			}
		}
		if (source.is(DamageTypeTags.IS_FIRE)) {
			if (entity.hasEffect(ESMobEffects.FLAMMABLE.asHolder())) {
				MobEffectInstance instance = entity.getEffect(ESMobEffects.FLAMMABLE.asHolder());
				if (instance != null) {
					modified *= instance.getAmplifier() + 2;
				}
			}
			AttributeInstance resistance = entity.getAttribute(ESAttributes.FIRE_RESISTANCE.asHolder());
			if (resistance != null) {
				modified *= (1 - (float) resistance.getValue());
			}
		}
		if (source.is(DamageTypeTags.IS_FREEZING)) {
			if (entity.hasEffect(ESMobEffects.BRITTLE.asHolder())) {
				MobEffectInstance instance = entity.getEffect(ESMobEffects.BRITTLE.asHolder());
				if (instance != null) {
					modified *= instance.getAmplifier() + 2;
				}
			}
		}
		if (source.getDirectEntity() instanceof LivingEntity attacker
			&& attacker.getWeaponItem().is(ESTags.Items.FLOWGLAZE_WEAPONS)
			&& entity == ESDataAttachments.CONCENTRATED_TARGET.getData(attacker)
			&& attacker.getWeaponItem() == ESDataAttachments.CONCENTRATED_WEAPON.getData(attacker)
			&& ESDataAttachments.CONCENTRATION_LEVEL.getData(attacker) >= 4
		) {
			modified *= 1.25f;
		}
		if (source.getDirectEntity() instanceof LivingEntity attacker && ESAccessoryUtil.getAccessories(attacker.getWeaponItem()).contains(ESItems.WARHAMMER_PENDANT.get())) {
			modified *= Math.min(1 + (float) ESDataAttachments.MOVEMENT.getData(attacker).length() * 1.5f, 2);
		}
		if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return Math.max(amount, modified);
		} else {
			return modified;
		}
	}

	public static void onPostLivingHurt(LivingEntity entity, DamageSource source, float amount) {
		if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThermalSpringstoneArmorItem
			|| entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ThermalSpringstoneArmorItem
			|| entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ThermalSpringstoneArmorItem
			|| entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ThermalSpringstoneArmorItem
		) {
			if (source.getDirectEntity() instanceof LivingEntity livingEntity) {
				livingEntity.setRemainingFireTicks(livingEntity.getRemainingFireTicks() + 200);
			}
		}

		if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.getWeaponItem().is(ESTags.Items.THERMAL_SPRINGSTONE_WEAPONS)) {
			entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 200);
		}

		if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof GlaciteArmorItem
			|| entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof GlaciteArmorItem
			|| entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof GlaciteArmorItem
			|| entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof GlaciteArmorItem
		) {
			if (source.getDirectEntity() instanceof LivingEntity livingEntity) {
				livingEntity.setTicksFrozen(Math.min(livingEntity.getTicksFrozen() + 80, 300));
			}
		}

		if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.getWeaponItem().is(ESTags.Items.GLACITE_WEAPONS) && entity.canFreeze()) {
			entity.setTicksFrozen(Math.min(entity.getTicksFrozen() + 80, 300));
		}

		if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.getWeaponItem().is(ESTags.Items.MALARITE_WEAPONS)) {
			entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
		}

		if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.getWeaponItem().is(ESTags.Items.PUNGENCY_FRUIT_WEAPONS)) {
			entity.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 1));
			entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120));
		}

		if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.getWeaponItem().is(ESTags.Items.STARFIRE_WEAPONS)) {
			entity.addEffect(new MobEffectInstance(ESMobEffects.STARFIRE.asHolder(), 60));
			if (attacker.level() instanceof ServerLevel serverLevel) {
				ThrownStarfire.createExplosionParticles(serverLevel, entity.position().add(0, entity.getBbHeight() / 2, 0), 5, 0.25);
			}
			attacker.level().playSound(null, attacker.blockPosition(), ESSoundEvents.STARFIRE_WHOOSH.get(), attacker.getSoundSource());
		}

		if (source.getDirectEntity() instanceof LivingEntity attacker && !(attacker instanceof Player)) {
			handleFlowglazeWeaponAttack(attacker, entity);
		}

		AttributeInstance meteorChance = entity.getAttribute(ESAttributes.METEOR_COUNTERATTACK_CHANCE.asHolder());
		if (meteorChance != null && entity.getRandom().nextDouble() < meteorChance.getValue()) {
			if (source.getEntity() instanceof LivingEntity livingEntity && livingEntity.level() instanceof ServerLevel serverLevel) {
				Vec3 location = livingEntity.position();
				AethersentMeteor.createMeteorShower(serverLevel, entity, livingEntity, location.x, location.y, location.z, 30, 120);
			}
		}

		if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.getWeaponItem().is(ESItems.PETAL_SCYTHE.get())) {
			for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(2.5))) {
				if (living != attacker) {
					living.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 1));
				}
			}
			if (attacker.level() instanceof ServerLevel serverLevel) {
				Vec3 vec3 = entity.position().add(0, entity.getBbHeight() / 2, 0);
				serverLevel.sendParticles(ESSmokeParticleOptions.LUNAR_ATTACK, vec3.x, vec3.y, vec3.z, 10, 1.5 * (serverLevel.getRandom().nextFloat() - 0.5), 1.5 * (serverLevel.getRandom().nextFloat() - 0.5), 1.5 * (serverLevel.getRandom().nextFloat() - 0.5), 0.1 * (serverLevel.getRandom().nextFloat() - 0.5));
			}
		}

		if (entity.hasEffect(ESMobEffects.STARFIRE.asHolder()) && !source.is(ESDamageTypes.STARFIRE)) {
			if (entity.level() instanceof ServerLevel serverLevel) {
				ThrownStarfire.createExplosionParticles(serverLevel, entity.position().add(0, entity.getBbHeight() / 2, 0), 6, 0.75);
			}
			for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3))) {
				if (ESEntityUtil.shouldHarm(source.getEntity(), living)) {
					living.hurt(ESDamageTypes.getIndirectEntityDamageSource(entity.level(), ESDamageTypes.STARFIRE, source.getDirectEntity(), source.getEntity()), amount / 3);
				}
			}
		}

		if (ESAccessoryUtil.getActiveAccessoriesOnArmors(entity).contains(ESItems.BUTTERFLY_WINGS_AMULET.get()) && source.getEntity() instanceof LivingEntity attacker) {
			int inEtherTicks = ESDataAttachments.IN_ETHER_TICKS.getData(attacker);
			if (inEtherTicks < 600) {
				ESDataAttachments.IN_ETHER_TICKS.setData(attacker, Math.min(inEtherTicks + 200, 600));
			}
		}

		if (source.getEntity() instanceof LivingEntity attacker && ESAccessoryUtil.getActiveAccessoriesOnArmors(attacker).contains(ESItems.BUTTERFLY_WINGS_AMULET.get())) {
			int inEtherTicks = ESDataAttachments.IN_ETHER_TICKS.getData(entity);
			if (inEtherTicks < 600) {
				ESDataAttachments.IN_ETHER_TICKS.setData(entity, Math.min(inEtherTicks + 200, 600));
			}
		}

		if (entity instanceof Player player && player.level().getBiome(player.blockPosition()).is(ESBiomes.THE_ABYSS) && player.isEyeInFluid(FluidTags.WATER) && player.getAirSupply() > 0) {
			player.setAirSupply(Math.max(player.getAirSupply() - 30, 0));
		}

		if (source.getDirectEntity() instanceof Player player) {
			if (player.getRandom().nextInt(15) == 0) {
				Inventory inventory = player.getInventory();
				boolean hasCrystals = false;
				for (int i = 0; i < inventory.getContainerSize(); i++) {
					if (inventory.getItem(i).is(ESTags.Items.MANA_CRYSTALS)) {
						hasCrystals = true;
					}
				}
				if (hasCrystals) {
					ItemEntity itemEntity = new ItemEntity(player.level(), entity.getX(), entity.getY(), entity.getZ(), ESItems.MANA_CRYSTAL_SHARD.get().getDefaultInstance());
					player.level().addFreshEntity(itemEntity);
				}
			}
		}
	}

	public static int onModifyPostAttackInvulnerabilityTicks(LivingEntity entity, DamageSource source, float amount, int ticks) {
		if (source.isDirect() && source.getDirectEntity() != null) {
			ItemStack weapon = source.getDirectEntity().getWeaponItem();
			if (weapon != null && weapon.getItem() instanceof DualWieldingSwordItem) {
				return Math.min(15, ticks);
			}
		}
		return ticks;
	}

	public static float onLivingHeal(LivingEntity entity, float amount) {
		float modified = amount;
		AttributeInstance healMultiplier = entity.getAttribute(ESAttributes.HEAL_MULTIPLIER.asHolder());
		if (healMultiplier != null) {
			modified *= (float) healMultiplier.getValue();
		}
		return modified;
	}

	public static void handleFlowglazeWeaponAttack(LivingEntity attacker, LivingEntity entity) {
		if (attacker.getWeaponItem().is(ESTags.Items.FLOWGLAZE_WEAPONS)) {
			ItemStack stack = attacker.getWeaponItem();
			if (entity == ESDataAttachments.CONCENTRATED_TARGET.getData(attacker) && stack == ESDataAttachments.CONCENTRATED_WEAPON.getData(attacker)) {
				ESDataAttachments.LAST_CONCENTRATED_ATTACK_TIME.setData(attacker, attacker.tickCount);
				ESDataAttachments.CONCENTRATION_LEVEL.setData(attacker, Math.min(ESDataAttachments.CONCENTRATION_LEVEL.getData(attacker) + 1, 4));
			} else {
				ESDataAttachments.CONCENTRATED_TARGET.setData(attacker, entity);
				ESDataAttachments.CONCENTRATED_WEAPON.setData(attacker, stack);
				ESDataAttachments.LAST_CONCENTRATED_ATTACK_TIME.setData(attacker, attacker.tickCount);
				ESDataAttachments.CONCENTRATION_LEVEL.setData(attacker, 0);
			}
		}
	}

	public static boolean onAllowLivingDeath(LivingEntity entity, DamageSource source) {
		if (entity instanceof Player player && source.getEntity() instanceof TheGatekeeper gatekeeper && gatekeeper.isStandardFight()) {
			gatekeeper.abortFight();
			player.setHealth(Math.max(player.getHealth(), player.getMaxHealth() * 0.1f));
			player.invulnerableTime = 200;
			if (entity.level() instanceof ServerLevel serverLevel) {
				RandomSource random = serverLevel.getRandom();
				for (int i = 0; i <= 25; i++) {
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.DEATH, player.getX() + (random.nextFloat() - 0.5f) * player.getBbWidth() * 3, player.getY(), player.getZ() + (random.nextFloat() - 0.5f) * player.getBbWidth() * 3, 0, 1, 0));
				}
			}
			return false;
		}
		return true;
	}

	public static void onLivingDeath(LivingEntity entity, DamageSource source) {
		if (entity.hasEffect(ESMobEffects.STARFIRE.asHolder())) {
			for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3))) {
				if (living != entity && ESEntityUtil.shouldHarm(source.getEntity(), living)) {
					MobEffectInstance instance = entity.getEffect(ESMobEffects.STARFIRE.asHolder());
					if (instance != null) {
						living.addEffect(new MobEffectInstance(ESMobEffects.STARFIRE.asHolder(), Math.max(instance.getDuration() / 2, 20)));
					}
				}
			}
		}
		if (source.getEntity() instanceof ServerPlayer player && entity.getType().is(ESTags.EntityTypes.AFFECTS_PROGRESSION)) {
			ESBookUtil.unlock(player, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).withPrefix("entity_killed_"));
		}
	}

	// returns the MULTIPLIER of the visibility multiplier
	public static double onLivingVisibility(LivingEntity entity, Entity lookingEntity, double modifier) {
		AttributeInstance followRangeMultiplier = entity.getAttribute(ESAttributes.ENEMY_FOLLOW_RANGE_MULTIPLIER.asHolder());
		if (followRangeMultiplier != null) {
			return followRangeMultiplier.getValue();
		}
		return 1;
	}

	public static LivingEntity onLivingChangeTarget(LivingEntity entity, LivingEntity newTarget) {
		if (newTarget != null && entity.hasEffect(ESMobEffects.TEARY.asHolder())) {
			int tearyTicks = ESDataAttachments.TEARY_TICKS.getData(entity);
			if (tearyTicks <= ESConfig.INSTANCE.mobMaxTearyTicks) {
				return null;
			}
		}
		return newTarget;
	}

	public static int onLivingDecreaseAirSupply(LivingEntity entity) {
		if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ESItems.AIR_SAC_MASK.asHolder())) {
			if (entity.isSwimming()) {
				return entity.getRandom().nextBoolean() ? -1 : 0;
			} else if (ESDataAttachments.MOVEMENT.getData(entity).multiply(1, 0, 1).length() < 0.01) {
				return 1;
			}
		}
		return 0;
	}

	public static void onEntityTick(Entity entity) {
		Level level = entity.level();
		if (entity instanceof ItemEntity item) {
			if (!level.isClientSide) {
				if (item.tickCount % 100 == 0 && ESBlockUtil.isEntityInBlock(item, ESBlocks.ETHER.get())) {
					ItemStack content = item.getItem();
					if (content.is(ConventionalTags.Items.MUSIC_DISCS) && !content.is(ESItems.MUSIC_DISC_SPIRIT.get()) && !content.is(ESItems.MUSIC_DISC_ETHER_RAIN.get())) {
						item.setItem(level.getRandom().nextBoolean() ? ESItems.MUSIC_DISC_SPIRIT.get().getDefaultInstance() : ESItems.MUSIC_DISC_ETHER_RAIN.get().getDefaultInstance());
						item.addDeltaMovement(new Vec3(0, 0.75, 0));
						level.playSound(null, item.blockPosition(), ESSoundEvents.ETHER_TRANSFORM.get(), SoundSource.BLOCKS, 1f, 1f);
					} else if (content.is(ESItems.STARLIT_PAINTING.get())) {
						CustomData data = content.get(DataComponents.ENTITY_DATA);
						if (data != null) {
							Holder<PaintingVariant> variant = data.read(level.registryAccess().createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC).getOrThrow();
							CustomData newData = null;
							if (variant.is(ESPaintingVariants.ENERGIZED)) {
								newData = data.update(level.registryAccess().createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC, level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getHolderOrThrow(ESPaintingVariants.ENERGIZED_SPECIAL)).getOrThrow();
							} else if (variant.is(ESPaintingVariants.ABSOLUTE_ZERO)) {
								newData = data.update(level.registryAccess().createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC, level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getHolderOrThrow(ESPaintingVariants.ABSOLUTE_ZERO_SPECIAL)).getOrThrow();
							} else if (variant.is(ESPaintingVariants.MONSTROUS)) {
								newData = data.update(level.registryAccess().createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC, level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getHolderOrThrow(ESPaintingVariants.MONSTROUS_SPECIAL)).getOrThrow();
							}
							if (newData != null) {
								ItemStack copy = content.copy();
								copy.set(DataComponents.ENTITY_DATA, newData);
								item.setItem(copy);
								item.addDeltaMovement(new Vec3(0, 0.75, 0));
								level.playSound(null, item.blockPosition(), ESSoundEvents.ETHER_TRANSFORM.get(), SoundSource.BLOCKS, 1f, 1f);
							}
						}
					} else if (content.is(ESTags.Items.ACCESSORIES)) {
						item.setItem(ESItems.BUTTERFLY_WINGS_AMULET.get().getDefaultInstance());
						item.addDeltaMovement(new Vec3(0, 0.75, 0));
						level.playSound(null, item.blockPosition(), ESSoundEvents.ETHER_TRANSFORM.get(), SoundSource.BLOCKS, 1f, 1f);
					}
				}
			} else {
				if ((item.getItem().is(ESTags.Items.MANA_CRYSTALS) || item.getItem().is(ESItems.MANA_CRYSTAL_SHARD.get()))) {
					EternalStarlight.getClientHelper().spawnManaCrystalItemParticles(item.getItem().getItem() instanceof ManaCrystalItem crystalItem ? crystalItem.getManaType() : ManaType.LUNAR, item.position().add(0, item.getBbHeight() / 2, 0));
				}
			}
		}
		if (!level.isClientSide) {
			int abyssalFireTicks = ESDataAttachments.ABYSSAL_FIRE_TICKS.getData(entity);
			if (abyssalFireTicks > 0) {
				ESDataAttachments.ABYSSAL_FIRE_TICKS.setData(entity, abyssalFireTicks - 1);
				if (!entity.getType().is(ESTags.EntityTypes.ABYSSAL_FIRE_IMMUNE) && entity.tickCount % 30 == 0) {
					int oldInvulnerableTime = entity.invulnerableTime;
					entity.invulnerableTime = 0;
					entity.hurt(level.damageSources().onFire(), 3.0F);
					entity.invulnerableTime = oldInvulnerableTime;
				}
			}
		}
		if (!level.isClientSide && entity instanceof AbstractArrow arrow) {
			ItemStack pickupOrigin = arrow.getPickupItemStackOrigin();
			if (pickupOrigin != null && pickupOrigin.has(ESDataComponents.QUIVER_ARROW.get())) {
				pickupOrigin.remove(ESDataComponents.QUIVER_ARROW.get());
			}
			ItemStack weaponItem = arrow.getWeaponItem();
			if (weaponItem != null && weaponItem.is(ESItems.UNREALIUM_CROSSBOW.get())) {
				arrow.setPierceLevel(Byte.MAX_VALUE);
			}
			if (!arrow.inGround) {
				if (ESDataAttachments.ARROW_TYPE.getData(arrow).equals(FLOWGLAZE_ARROW)) {
					float previousExtra = ESDataAttachments.FLOWGLAZE_ARROW_EXTRA_BASE_DAMAGE.getData(arrow);
					if (previousExtra < 3) {
						arrow.setBaseDamage(arrow.getBaseDamage() + 0.1);
						ESDataAttachments.FLOWGLAZE_ARROW_EXTRA_BASE_DAMAGE.setData(arrow, previousExtra + 0.1f);
					}
				}
				if (ESDataAttachments.ARROW_TYPE.getData(arrow).equals(WILTED_ARROW)) {
					List<LivingEntity> affected = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(5));
					affected.removeIf(e -> !ESEntityUtil.shouldHarm(arrow.getOwner(), e));
					for (LivingEntity living : affected) {
						living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80));
						living.addEffect(new MobEffectInstance(MobEffects.WITHER, entity.isInWater() ? 300 : 160));
					}
					if (arrow.tickCount % 4 == 0) {
						for (int i = 0; i < 3; i++) {
							WiltedPetal petal = arrow.getOwner() instanceof LivingEntity living ? new WiltedPetal(level, living) : new WiltedPetal(ESEntities.WILTED_PETAL.get(), level);
							petal.setPos(entity.position());
							Vec3 movement = new Vec3(entity.getRandom().nextFloat() - 0.5, entity.getRandom().nextFloat() - 0.5, entity.getRandom().nextFloat() - 0.5);
							if (affected.size() > i) {
								LivingEntity target = affected.get(i);
								movement = target.position().add(0, target.getBbHeight() / 2, 0).subtract(entity.position());
							}
							petal.shoot(movement.x, movement.y, movement.z, 0.8f, 0.2f);
							level.addFreshEntity(petal);
						}
					}
					if (level instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, FastColor.ARGB32.color(96, 0x90003b)), entity.getX(), entity.getY(), entity.getZ(), 6, 2, 2, 2, 0.2);
					}
				}
			}
		}
		if (entity instanceof LivingEntity livingEntity) {
			ESSpellUtil.tickSpells(livingEntity);
			SpecialItemCooldown.tick(livingEntity);
			if (livingEntity instanceof Player player && !level.isClientSide) {
				ESDataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER.setData(player, ESDataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER.getData(player) + 1);
				if (!ItemStack.matches(ESDataAttachments.LAST_OFFHAND_ITEM.getData(player), player.getOffhandItem())) {
					if (!ItemStack.isSameItem(ESDataAttachments.LAST_OFFHAND_ITEM.getData(player), player.getOffhandItem())) {
						ESDataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER.setData(player, 0);
					}
					ESDataAttachments.LAST_OFFHAND_ITEM.setData(player, player.getOffhandItem().copy());
				}
				ESCrestUtil.tickCrests(player);
				if (player.hasEffect(ESMobEffects.OBLIVION.asHolder())) {
					BlockPos.MutableBlockPos testPos = new BlockPos.MutableBlockPos();

					for (int i = 0; i < 8; i++) {
						double xo = player.getX() + ((i >> 0) % 2 - 0.5F) * player.getBbWidth() * 0.8F;
						double yo = player.getEyeY() + ((i >> 1) % 2 - 0.5F) * 0.1F * player.getScale();
						double zo = player.getZ() + ((i >> 2) % 2 - 0.5F) * player.getBbWidth() * 0.8F;
						testPos.set(xo, yo, zo);
						BlockState testState = player.level().getBlockState(testPos);
						if (testState.getRenderShape() != RenderShape.INVISIBLE && testState.isViewBlocking(player.level(), testPos)) {
							player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40));
						}
					}
				}
				if (level.getBiome(player.blockPosition()).is(ESBiomes.THE_ABYSS) && player.isEyeInFluid(FluidTags.WATER) && player.getY() < 0) {
					int maxAir = Math.max((int) Math.round((player.getMaxAirSupply() + player.getY() * 3) / 30) * 30 - 15, 0);
					if (player.getAirSupply() > maxAir) {
						player.setAirSupply(maxAir);
					}
				}
				if (ESAccessoryUtil.getActiveAccessoriesOnArmors(player).contains(ESItems.PEARL_NECKLACE.get()) && !player.isEyeInFluid(FluidTags.WATER)) {
					player.setAirSupply(player.getMaxAirSupply());
				}
				Inventory inventory = player.getInventory();
				for (int i = 0; i < inventory.getContainerSize(); i++) {
					if (inventory.getItem(i).has(ESDataComponents.QUIVER_ARROW.get())) {
						inventory.getItem(i).remove(ESDataComponents.QUIVER_ARROW.get());
					}
				}
				if (player.getMainHandItem().is(ESItems.GRAVITY_PICKAXE.get()) || player.getOffhandItem().is(ESItems.GRAVITY_PICKAXE.get())) {
					for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(5))) {
						itemEntity.playerTouch(player);
					}
				}
				if (player instanceof ServerPlayer serverPlayer) {
					ServerPlayerGameMode gameMode = serverPlayer.gameMode;
					ServerLevel serverLevel = serverPlayer.serverLevel();
					if (gameMode.isDestroyingBlock && serverPlayer.getMainHandItem().is(ESTags.Items.FLOWGLAZE_WEAPONS)) {
						BlockPos oldTarget = ESDataAttachments.FLOWGLAZE_DESTROY_BLOCK_TARGET.getData(serverPlayer);
						if (oldTarget != null && !oldTarget.equals(gameMode.destroyPos)) {
							ESDataAttachments.FLOWGLAZE_DESTROY_BLOCK_TICKS.setData(serverPlayer, 0);
						}
						ESDataAttachments.FLOWGLAZE_DESTROY_BLOCK_TARGET.setData(serverPlayer, gameMode.destroyPos);
						ESDataAttachments.FLOWGLAZE_DESTROY_BLOCK_TICKS.setData(serverPlayer, ESDataAttachments.FLOWGLAZE_DESTROY_BLOCK_TICKS.getData(serverPlayer) + 1);
						BlockState destroyState = serverLevel.getBlockState(gameMode.destroyPos);
						if (ESDataAttachments.FLOWGLAZE_DESTROY_BLOCK_TICKS.getData(serverPlayer) >= 100 && destroyState.getDestroyProgress(serverPlayer, serverLevel, gameMode.destroyPos) > 0 && (!destroyState.requiresCorrectToolForDrops() || serverPlayer.getMainHandItem().isCorrectToolForDrops(destroyState))) {
							int id = Block.getId(destroyState);
							gameMode.destroyBlock(gameMode.destroyPos);
							for (int i = 0; i < serverLevel.players().size(); i++) {
								serverLevel.players().get(i).connection.send(new ClientboundLevelEventPacket(2001, gameMode.destroyPos, id, false));
							}
						}
					} else if (ESDataAttachments.FLOWGLAZE_DESTROY_BLOCK_TARGET.hasData(serverPlayer) || ESDataAttachments.FLOWGLAZE_DESTROY_BLOCK_TICKS.hasData(serverPlayer)) {
						ESDataAttachments.FLOWGLAZE_DESTROY_BLOCK_TARGET.removeData(serverPlayer);
						ESDataAttachments.FLOWGLAZE_DESTROY_BLOCK_TICKS.removeData(serverPlayer);
					}
				}
			}
			if (ESDataAttachments.CONCENTRATION_LEVEL.getData(livingEntity) > 0
				&& (livingEntity.tickCount - ESDataAttachments.LAST_CONCENTRATED_ATTACK_TIME.getData(livingEntity) > 100 || livingEntity.getWeaponItem() != ESDataAttachments.CONCENTRATED_WEAPON.getData(livingEntity))) {
				ESDataAttachments.CONCENTRATED_TARGET.removeData(livingEntity);
				ESDataAttachments.CONCENTRATED_WEAPON.removeData(livingEntity);
				ESDataAttachments.LAST_CONCENTRATED_ATTACK_TIME.removeData(livingEntity);
				ESDataAttachments.CONCENTRATION_LEVEL.removeData(livingEntity);
			}
			List<ItemStack> armors = List.of(livingEntity.getItemBySlot(EquipmentSlot.HEAD), livingEntity.getItemBySlot(EquipmentSlot.CHEST), livingEntity.getItemBySlot(EquipmentSlot.LEGS), livingEntity.getItemBySlot(EquipmentSlot.FEET));
			for (ItemStack armor : armors) {
				if (armor.getItem() instanceof TickableArmor tickableArmor) {
					tickableArmor.tick(level, livingEntity, armor);
				}
			}
			AttributeInstance armorAttribute = livingEntity.getAttributes().getInstance(Attributes.ARMOR);
			if (armorAttribute != null) {
				if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(ESItems.AMARAMBER_MASK.get())
					&& livingEntity.getItemBySlot(EquipmentSlot.CHEST).is(ESItems.AMARAMBER_CHESTPLATE.get())
					&& livingEntity.getItemBySlot(EquipmentSlot.LEGS).isEmpty()
					&& livingEntity.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
					if (!armorAttribute.hasModifier(AMARAMBER_BONUS.id())) {
						armorAttribute.addPermanentModifier(AMARAMBER_BONUS);
					}
				} else if (armorAttribute.hasModifier(AMARAMBER_BONUS.id())) {
					armorAttribute.removeModifier(AMARAMBER_BONUS.id());
				}
			}
			int inEtherTicks = ESDataAttachments.IN_ETHER_TICKS.getData(entity);
			AttributeInstance armorInstance = livingEntity.getAttributes().getInstance(Attributes.ARMOR);
			boolean inEther = ESDataAttachments.IN_ETHER.getData(entity);
			if (!level.isClientSide) {
				int meteorCooldown = ESDataAttachments.METEOR_COOLDOWN.getData(entity);
				if (meteorCooldown > 0) {
					ESDataAttachments.METEOR_COOLDOWN.setData(entity, meteorCooldown - 1);
				}
				int hireCooldown = ESDataAttachments.STRANGHOUL_HIRING_COOLDOWN.getData(entity);
				if (hireCooldown > 0) {
					ESDataAttachments.STRANGHOUL_HIRING_COOLDOWN.setData(entity, hireCooldown - 1);
				}
				if (livingEntity.hasEffect(ESMobEffects.TEARY.asHolder()) && level instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(ParticleTypes.FALLING_WATER, livingEntity.getX() + livingEntity.getBbWidth() * (livingEntity.getRandom().nextFloat() - 0.5), livingEntity.getEyeY(), livingEntity.getZ() + livingEntity.getBbWidth() * (livingEntity.getRandom().nextFloat() - 0.5), 3, 0, 0, 0, 0);
				}
				if (!livingEntity.getType().is(ESTags.EntityTypes.TEARY_IMMUNE) && livingEntity.hasEffect(ESMobEffects.TEARY.asHolder())) {
					int tearyTicks = ESDataAttachments.TEARY_TICKS.getData(entity);
					if (tearyTicks <= ESConfig.INSTANCE.mobMaxTearyTicks) {
						if (livingEntity instanceof Mob mob && mob.getTarget() != null) {
							mob.setTarget(null);
							mob.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
							mob.getNavigation().stop();
							mob.setLastHurtByMob(null);
						}
						ESDataAttachments.TEARY_TICKS.setData(entity, tearyTicks + 1);
					}
				}
				if (inEther) {
					float factor = 0;
					AttributeInstance resistance = livingEntity.getAttribute(ESAttributes.ETHER_RESISTANCE.asHolder());
					if (resistance != null) {
						factor = 1 - (float) resistance.getValue();
					}
					if (armorInstance != null && armorInstance.getValue() <= 0) {
						if (entity.hurt(ESDamageTypes.getDamageSource(level, ESDamageTypes.ETHER), 0.3f + 0.6f * factor) && level instanceof ServerLevel serverLevel) {
							for (int i = 0; i < 5; i++) {
								serverLevel.sendParticles(ESParticles.STARLIGHT.get(), entity.getX() + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(), entity.getY() + entity.getBbHeight() / 2d + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbHeight(), entity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(), 20, 0.1, 0.1, 0.1, 0);
							}
						}
					}
					if ((armorInstance == null || armorInstance.getValue() > 0 || inEtherTicks < 140) && livingEntity.getRandom().nextFloat() <= factor) {
						ESDataAttachments.IN_ETHER_TICKS.setData(entity, inEtherTicks + 1);
					}
					ESDataAttachments.IN_ETHER.setData(entity, false);
				}
				if (!inEther && inEtherTicks > 0) {
					ESDataAttachments.IN_ETHER_TICKS.setData(entity, inEtherTicks - 1);
				}
				if (inEtherTicks <= 0 && armorInstance != null) {
					armorInstance.removeModifier(EtherFluid.ARMOR_MODIFIER_ID);
				}
				if (livingEntity.tickCount % 20 == 0 && inEtherTicks > 0 && armorInstance != null) {
					armorInstance.removeModifier(EtherFluid.ARMOR_MODIFIER_ID);
					armorInstance.addPermanentModifier(EtherFluid.armorModifier((float) -inEtherTicks / 100));
				}
			}
		}
	}

	public static void onCriticalHit(Player player, Entity target, float attackStrength) {
		if (player.getWeaponItem().is(ESTags.Items.HAMMERS) && player.getWeaponItem().getItem() instanceof HammerItem hammerItem && attackStrength > 0.9f) {
			hammerItem.performCriticalAttack(player, target);
		}
	}

	public static void onBlockBroken(Player player, BlockPos pos, BlockState state) {
		if (state.is(BlockTags.LEAVES) && player.level().dimension() == ESDimensions.STARLIGHT_KEY) {
			float chance = player.getName().getString().toLowerCase(Locale.ROOT).contains("nuttar") ? (ESDataAttachments.OBTAINED_BLOSSOM_OF_STARS.getData(player) ? 2.5f : 25f) : 0.0025f;
			if (player.getRandom().nextFloat() < chance / 100f) {
				ESDataAttachments.OBTAINED_BLOSSOM_OF_STARS.setData(player, true);
				if (!player.getInventory().add(ESItems.BLOSSOM_OF_STARS.get().getDefaultInstance())) {
					player.spawnAtLocation(ESItems.BLOSSOM_OF_STARS.get());
				}
			}
		}
	}

	public static float onBlockBreakSpeed(Player player, BlockState state, float speed) {
		if (player.getMainHandItem().is(ESItems.UNDERMINER.get())) {
			int min = player.level().getMinBuildHeight();
			int max = player.level().getMaxBuildHeight();
			double y = Mth.clamp(player.getY(), min, max);
			float modifier = (float) (2 - 1.75 * (y - min) / (max - min));
			return speed * modifier;
		}
		return speed;
	}

	public static void onShieldBlock(LivingEntity blocker, DamageSource source) {
		if (blocker.getUseItem().is(ESItems.GLACITE_SHIELD.get()) && source.getDirectEntity() instanceof LivingEntity entity && entity.canFreeze()) {
			entity.setTicksFrozen(Math.min(entity.getTicksFrozen() + 100, 300));
		}
	}

	public static void onProjectileImpact(Projectile projectile, HitResult result) {
		if (projectile.level() instanceof ServerLevel serverLevel) {
			if (ESDataAttachments.ARROW_TYPE.getData(projectile).equals(STARFIRE_ARROW)) {
				if (result.getType() == HitResult.Type.BLOCK) {
					ESDataAttachments.ARROW_TYPE.setData(projectile, "");
				}
				ThrownStarfire.createExplosionParticles(serverLevel, projectile.position(), 10, 0.25);
				for (LivingEntity living : projectile.level().getEntitiesOfClass(LivingEntity.class, projectile.getBoundingBox().inflate(3))) {
					if (ESEntityUtil.shouldHarm(projectile.getOwner(), living)) {
						living.addEffect(new MobEffectInstance(ESMobEffects.STARFIRE.asHolder(), 200));
					}
				}
			}
			if (ESDataAttachments.ARROW_TYPE.getData(projectile).equals(CRYSTAL_ARROW)) {
				if (result.getType() == HitResult.Type.BLOCK) {
					ESDataAttachments.ARROW_TYPE.setData(projectile, "");
				}
				for (int i = 0; i < 5; i++) {
					Vec3 pos = projectile.position().offsetRandom(projectile.getRandom(), 4);
					BlockPos startPos = BlockPos.containing(pos);
					int currentDiff = 0;
					while (!serverLevel.getBlockState(startPos).isAir() && currentDiff < 40) {
						startPos = startPos.above();
						currentDiff++;
					}
					if (serverLevel.getBlockState(startPos).isAir()) {
						BlockHitResult toGround = serverLevel.clip(new ClipContext(startPos.getCenter(), startPos.getCenter().subtract(0, 128, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, projectile));
						if (toGround.getType() != HitResult.Type.MISS) {
							CrystalCluster cluster = new CrystalCluster(ESEntities.CRYSTAL_CLUSTER.get(), serverLevel);
							cluster.setPos(toGround.getLocation());
							if (projectile.getOwner() instanceof LivingEntity owner) {
								cluster.setOwner(owner);
							}
							cluster.setYRot(Mth.wrapDegrees(projectile.getRandom().nextFloat() * 360));
							serverLevel.addFreshEntity(cluster);
						}
					}
				}
				if (result.getType() == HitResult.Type.ENTITY && result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
					int level = 0;
					if (living.hasEffect(ESMobEffects.CRYSTAL_INFECTION.asHolder())) {
						MobEffectInstance instance = living.getEffect(ESMobEffects.CRYSTAL_INFECTION.asHolder());
						if (instance != null) {
							level = Math.min(instance.getAmplifier() + 1, 4);
						}
					}
					living.addEffect(new MobEffectInstance(ESMobEffects.CRYSTAL_INFECTION.asHolder(), 200, level));
				}
			}
			if (ESDataAttachments.ARROW_TYPE.getData(projectile).equals(MECHANICAL_ARROW) && result.getType() == HitResult.Type.ENTITY && result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
				ESDataAttachments.ARROW_TYPE.setData(projectile, "");
				Entity owner = projectile.getOwner();
				ItemStack weapon = projectile.getWeaponItem();
				if (owner instanceof LivingEntity attacker && weapon != null && !SpecialItemCooldown.isOnCooldown(owner, weapon.getItem())) {
					for (int i = 0; i < owner.getRandom().nextInt(5, 8); i++) {
						EnergySpark spark = new EnergySpark(serverLevel, attacker);
						spark.setPos(living.position().add(0, living.getBbHeight() / 2, 0));
						spark.setTarget(living);
						Vec3 movement = new Vec3(owner.getRandom().nextFloat() - 0.5, owner.getRandom().nextFloat() - 0.5, owner.getRandom().nextFloat() - 0.5);
						spark.shoot(movement.x, movement.y, movement.z, 0.1f, 0.2f);
						serverLevel.addFreshEntity(spark);
					}
					SpecialItemCooldown.setCooldown(owner, weapon.getItem(), 75);
				}
			}
			if (ESDataAttachments.ARROW_TYPE.getData(projectile).equals(STARFALL_ARROW) && projectile.getOwner() instanceof LivingEntity owner) {
				ESDataAttachments.ARROW_TYPE.setData(projectile, "");
				Vec3 location = result.getLocation();
				AethersentMeteor.createMeteorShower(serverLevel, owner, result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity ? livingEntity : null, location.x, location.y, location.z, 30, 60);
			}
		}
	}

	public static void onCompleteAdvancement(Player player, AdvancementHolder advancement) {
		if (player instanceof ServerPlayer serverPlayer) {
			ESBookUtil.unlock(serverPlayer, advancement.id().withPrefix("advancement_"));
		}
	}

	public static boolean onVanillaGameEvent(Level level, Holder<GameEvent> vanillaEvent, Vec3 position, GameEvent.Context context) {
		if (context.sourceEntity() instanceof LivingEntity living) {
			if ((living.getItemBySlot(EquipmentSlot.HEAD).is(ESItems.UNREALIUM_HELMET.get()) && (vanillaEvent.is(GameEvent.EAT) || vanillaEvent.is(GameEvent.ITEM_INTERACT_START) || vanillaEvent.is(GameEvent.ITEM_INTERACT_FINISH)))
				|| (living.getItemBySlot(EquipmentSlot.CHEST).is(ESItems.UNREALIUM_CHESTPLATE.get()) && vanillaEvent.is(GameEvent.ENTITY_DAMAGE))
				|| (living.getItemBySlot(EquipmentSlot.LEGS).is(ESItems.UNREALIUM_LEGGINGS.get()) && (vanillaEvent.is(GameEvent.HIT_GROUND) || vanillaEvent.is(GameEvent.SPLASH)))
				|| (living.getItemBySlot(EquipmentSlot.FEET).is(ESItems.UNREALIUM_BOOTS.get()) && (vanillaEvent.is(GameEvent.STEP) || vanillaEvent.is(GameEvent.HIT_GROUND)))) {
				return false;
			}
		}
		return true;
	}

	public static void onClientToServerSimpleAction(ServerPlayer player, String id) {
		switch (id) {
			case SimpleActionPacket.C2S_SWING_ATTACK -> {
				ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
				if (stack.getItem() instanceof SwingAttackWeapon weapon) {
					weapon.performSwingAttack(stack, player);
				}
			}
			case SimpleActionPacket.C2S_SWITCH_CREST -> {
				List<Crest.Instance> crests = ESCrestUtil.getOwnedCrests(player);
				ItemStack mainHand = player.getMainHandItem();
				ItemStack offHand = player.getOffhandItem();
				ItemStack spellItem;
				Holder<Crest> component = null;
				Holder<Crest> nextCrest = null;
				if (mainHand.has(ESDataComponents.CURRENT_CREST.get())) {
					component = mainHand.get(ESDataComponents.CURRENT_CREST.get());
					spellItem = mainHand;
				} else if (offHand.has(ESDataComponents.CURRENT_CREST.get())) {
					component = offHand.get(ESDataComponents.CURRENT_CREST.get());
					spellItem = offHand;
				} else if (mainHand.is(ESItems.ORB_OF_PROPHECY.get())) {
					spellItem = mainHand;
				} else if (offHand.is(ESItems.ORB_OF_PROPHECY.get())) {
					spellItem = offHand;
				} else {
					spellItem = null;
				}
				if (component != null) {
					find:
					for (int i = 0; i < crests.size(); i++) {
						if (crests.get(i).crest().is(component) && i < crests.size() - 1) {
							for (int j = i + 1; j < crests.size(); j++) {
								if (crests.get(j).crest().value().getSpell().isPresent()) {
									nextCrest = crests.get(j).crest();
									break find;
								}
							}
						}
					}
				} else {
					for (Crest.Instance instance : crests) {
						if (instance.crest().value().getSpell().isPresent()) {
							nextCrest = instance.crest();
							break;
						}
					}
				}
				if (spellItem != null) {
					if (nextCrest != null && nextCrest.isBound()) {
						spellItem.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.CURRENT_CREST.get(), nextCrest).build());
					} else {
						spellItem.remove(ESDataComponents.CURRENT_CREST.get());
					}
				}
			}
		}
	}
}
