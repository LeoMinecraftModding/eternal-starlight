package cn.leolezury.eternalstarlight.common.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.fluid.EtherFluid;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.data.ESPaintingVariants;
import cn.leolezury.eternalstarlight.common.entity.interfaces.StarlightWitch;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownStarfire;
import cn.leolezury.eternalstarlight.common.entity.projectile.WiltedPetal;
import cn.leolezury.eternalstarlight.common.item.armor.AethersentArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.GlaciteArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringstoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.combat.HammerItem;
import cn.leolezury.eternalstarlight.common.item.component.CurrentCrestComponent;
import cn.leolezury.eternalstarlight.common.item.interfaces.TickableArmor;
import cn.leolezury.eternalstarlight.common.item.misc.ManaCrystalItem;
import cn.leolezury.eternalstarlight.common.network.NoParametersPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateWeatherPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateWitchTypePacket;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.*;
import cn.leolezury.eternalstarlight.common.resource.gatekeeper.TheGatekeeperNameManager;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.*;
import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import cn.leolezury.eternalstarlight.common.weather.WeatherInstance;
import cn.leolezury.eternalstarlight.common.weather.Weathers;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class CommonHandlers {
	public static final String TAG_IN_ETHER = "in_ether";
	public static final String TAG_IN_ETHER_TICKS = "in_ether_ticks";
	public static final String TAG_CLIENT_IN_ETHER_TICKS = "client_in_ether_ticks";
	private static final String TAG_OBTAINED_BLOSSOM_OF_STARS = "obtained_blossom_of_stars";
	public static final String TAG_CRYSTAL_ARROW = EternalStarlight.ID + ":crystal";
	public static final String TAG_STARFALL_ARROW = EternalStarlight.ID + ":starfall";
	public static final String TAG_WILTED_ARROW = EternalStarlight.ID + ":wilted";
	public static final String TAG_IN_ABYSSAL_FIRE_TICKS = "in_abyssal_fire_ticks";
	public static final String TAG_NUMBNESS_DAMAGE = "numbness_damage";
	private static final String TAG_TEARY_TICKS = "teary_ticks";
	private static TheGatekeeperNameManager gatekeeperNames;
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

	private static int ticksSinceLastUpdate = 0;

	private static final AttributeModifier AMARAMBER_BONUS = new AttributeModifier(EternalStarlight.id("armor.amaramber_bonus"), 7, AttributeModifier.Operation.ADD_VALUE);

	public static void onServerTick(MinecraftServer server) {
		ticksSinceLastUpdate++;
		if (ticksSinceLastUpdate >= 20) {
			for (ServerLevel level : server.getAllLevels()) {
				if (level.getChunkSource().getGenerator().getBiomeSource() instanceof ESBiomeSource source) {
					source.setCacheSize(level.players().size() * 8);
				}
			}
			ticksSinceLastUpdate = 0;
		}
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
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new NoParametersPacket("cancel_weather"));
					lastWeather = null;
				}
			});
		}
	}

	public static void onItemTooltip(TooltipFlag flags, ItemStack itemStack, List<Component> tooltip, Item.TooltipContext context) {
		if (itemStack.is(ESTags.Items.FLOWGLAZE_WEAPONS)) {
			tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".flowglaze_weapon").withStyle(Style.EMPTY.withColor(0x8ed6b0)));
			tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".flowglaze_tool").withStyle(Style.EMPTY.withColor(0x8ed6b0)));
		}
		if (itemStack.is(ESItems.FLOWGLAZE_SHIELD.get())) {
			tooltip.add(Component.translatable("tooltip." + EternalStarlight.ID + ".flowglaze_shield").withStyle(Style.EMPTY.withColor(0x8ed6b0)));
		}
	}

	public static float onModifyLivingHurtDamage(LivingEntity entity, DamageSource source, float amount) {
		float modified = amount;
		Entity sourceEntity = source.getEntity();
		if (sourceEntity != null) {
			if (sourceEntity.getType() == ESEntities.STARLIGHT_GOLEM.get()) {
				modified *= (float) ESConfig.INSTANCE.mobsConfig.starlightGolem.attackDamageScale();
			}
			if (sourceEntity.getType() == ESEntities.LUNAR_MONSTROSITY.get()) {
				modified *= (float) ESConfig.INSTANCE.mobsConfig.lunarMonstrosity.attackDamageScale();
			}
			if (sourceEntity.getType() == ESEntities.TANGLED_HATRED.get()) {
				modified *= (float) ESConfig.INSTANCE.mobsConfig.tangledHatred.attackDamageScale();
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
		if (entity.hasEffect(ESMobEffects.NUMBNESS.asHolder())) {
			CompoundTag tag = ESEntityUtil.getPersistentData(entity);
			tag.putFloat(TAG_NUMBNESS_DAMAGE, tag.getFloat(TAG_NUMBNESS_DAMAGE) + modified * 0.75f);
			modified *= 0.25f;
		}
		if (source.getDirectEntity() instanceof LivingEntity attacker
			&& attacker.getWeaponItem().is(ESTags.Items.FLOWGLAZE_WEAPONS)
			&& entity == ESDataAttachments.CONCENTRATED_TARGET.getData(attacker)
			&& attacker.getWeaponItem() == ESDataAttachments.CONCENTRATED_WEAPON.getData(attacker)
			&& ESDataAttachments.CONCENTRATION_LEVEL.getData(attacker) >= 4
		) {
			modified *= 1.25f;
		}
		return modified;
	}

	public static void onPostLivingHurt(LivingEntity entity, DamageSource source, float amount) {
		if (amount > 0) {
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

			if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof AethersentArmorItem
				&& entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof AethersentArmorItem
				&& entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof AethersentArmorItem
				&& entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof AethersentArmorItem
			) {
				if (source.getEntity() instanceof LivingEntity livingEntity && livingEntity.level() instanceof ServerLevel serverLevel) {
					Vec3 location = livingEntity.position();
					AethersentMeteor.createMeteorShower(serverLevel, entity, livingEntity, location.x, location.y, location.z, 200, true);
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
					// serverLevel.sendParticles(ESParticles.SHADEGRIEVE_LEAVES.get(), vec3.x, vec3.y, vec3.z, 15, entity.getBbWidth() * 1.25 * (serverLevel.getRandom().nextFloat() - 0.5), entity.getBbHeight() * 0.75 * (serverLevel.getRandom().nextFloat() - 0.5), entity.getBbWidth() * 1.25 * (serverLevel.getRandom().nextFloat() - 0.5), 0.1 * (serverLevel.getRandom().nextFloat() - 0.5));
				}
			}

			if (entity.hasEffect(ESMobEffects.STARFIRE.asHolder()) && !source.is(ESDamageTypes.STARFIRE)) {
				if (entity.level() instanceof ServerLevel serverLevel) {
					ThrownStarfire.createExplosionParticles(serverLevel, entity.position().add(0, entity.getBbHeight() / 2, 0), 6, 0.75);
				}
				for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3))) {
					if (living != entity && living != source.getDirectEntity()) {
						living.hurt(ESDamageTypes.getIndirectEntityDamageSource(entity.level(), ESDamageTypes.STARFIRE, source.getDirectEntity(), source.getEntity()), amount / 3);
					}
				}
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

	public static void onLivingDeath(LivingEntity entity, DamageSource source) {
		if (entity.hasEffect(ESMobEffects.STARFIRE.asHolder())) {
			for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3))) {
				if (living != entity && living != source.getDirectEntity()) {
					MobEffectInstance instance = entity.getEffect(ESMobEffects.STARFIRE.asHolder());
					if (instance != null) {
						living.addEffect(new MobEffectInstance(ESMobEffects.STARFIRE.asHolder(), Math.max(instance.getDuration() / 2, 20)));
					}
				}
			}
		}
	}

	public static LivingEntity onLivingChangeTarget(LivingEntity entity, LivingEntity newTarget) {
		if (newTarget != null && entity.hasEffect(ESMobEffects.TEARY.asHolder())) {
			CompoundTag persistentData = ESEntityUtil.getPersistentData(entity);
			int tearyTicks = persistentData.getInt(TAG_TEARY_TICKS);
			if (tearyTicks <= ESConfig.INSTANCE.mobMaxTearyTicks) {
				return null;
			}
		}
		return newTarget;
	}

	public static void onEntityTick(Entity entity) {
		Level level = entity.level();
		if (entity instanceof ItemEntity item) {
			if (!item.level().isClientSide) {
				if (item.tickCount % 100 == 0 && ESBlockUtil.isEntityInBlock(item, ESBlocks.ETHER.get())) {
					ItemStack content = item.getItem();
					if (content.is(ConventionalTags.Items.MUSIC_DISCS) && !content.is(ESItems.MUSIC_DISC_SPIRIT.get())) {
						item.setItem(ESItems.MUSIC_DISC_SPIRIT.get().getDefaultInstance());
						item.addDeltaMovement(new Vec3(0, 0.25, 0));
						level.playSound(null, item.blockPosition(), ESSoundEvents.ETHER_TRANSFORM.get(), SoundSource.BLOCKS, 1f, 1f);
					} else if (content.is(ESItems.STARLIT_PAINTING.get())) {
						CustomData data = content.get(DataComponents.ENTITY_DATA);
						if (data != null) {
							Holder<PaintingVariant> variant = data.read(level.registryAccess().createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC).getOrThrow();
							CustomData newData = null;
							if (variant.is(ESPaintingVariants.ENERGIZED)) {
								newData = data.update(level.registryAccess().createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC, level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getHolderOrThrow(ESPaintingVariants.ENERGIZED_SPECIAL)).getOrThrow();
							} else if (variant.is(ESPaintingVariants.MONSTROUS)) {
								newData = data.update(level.registryAccess().createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC, level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getHolderOrThrow(ESPaintingVariants.MONSTROUS_SPECIAL)).getOrThrow();
							}
							if (newData != null) {
								ItemStack copy = content.copy();
								copy.set(DataComponents.ENTITY_DATA, newData);
								item.setItem(copy);
								item.addDeltaMovement(new Vec3(0, 0.25, 0));
							}
						}
						level.playSound(null, item.blockPosition(), ESSoundEvents.ETHER_TRANSFORM.get(), SoundSource.BLOCKS, 1f, 1f);
					}
				}
			} else {
				if ((item.getItem().is(ESTags.Items.MANA_CRYSTALS) || item.getItem().getItem() == ESItems.MANA_CRYSTAL_SHARD.get())) {
					EternalStarlight.getClientHelper().spawnManaCrystalItemParticles(item.getItem().getItem() instanceof ManaCrystalItem crystalItem ? crystalItem.getManaType() : ManaType.LUNAR, item.position().add(0, item.getBbHeight() / 2, 0));
				}
			}
		}
		if (entity instanceof StarlightWitch witch && witch.isWitchTypeDirty()) {
			if (entity.level() instanceof ServerLevel serverLevel) {
				ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, new UpdateWitchTypePacket(entity.getId(), witch.getWitchType()));
				witch.setWitchTypeDirty(false);
			}
		}
		CompoundTag persistentData = ESEntityUtil.getPersistentData(entity);
		int inAbyssalFireTicks = persistentData.getInt(TAG_IN_ABYSSAL_FIRE_TICKS);
		persistentData.putInt(TAG_IN_ABYSSAL_FIRE_TICKS, Math.max(inAbyssalFireTicks - 1, 0));
		if (!level.isClientSide && entity instanceof AbstractArrow arrow && persistentData.getBoolean(TAG_WILTED_ARROW) && !arrow.inGround) {
			List<LivingEntity> affected = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(5));
			affected.removeIf(e -> arrow.getOwner() == e);
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
		if (entity instanceof LivingEntity livingEntity) {
			ESSpellUtil.tickSpells(livingEntity);
			if (livingEntity instanceof Player player && !livingEntity.level().isClientSide) {
				ESCrestUtil.tickCrests(player);
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
					tickableArmor.tick(livingEntity.level(), livingEntity, armor);
				}
			}
			boolean armorChanged = false;
			for (EquipmentSlot slot : List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)) {
				if (livingEntity.equipmentHasChanged(livingEntity.getLastArmorItem(slot), livingEntity.getItemBySlot(slot))) {
					armorChanged = true;
					break;
				}
			}
			if (armorChanged) {
				AttributeInstance armorAttribute = livingEntity.getAttributes().getInstance(Attributes.ARMOR);
				if (armorAttribute != null) {
					if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(ESItems.AMARAMBER_HELMET.get())
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
			}
			if (livingEntity.tickCount % 20 == 0) {
				int cooldown = persistentData.getInt(AethersentMeteor.TAG_METEOR_COOLDOWN);
				if (cooldown > 0) {
					persistentData.putInt(AethersentMeteor.TAG_METEOR_COOLDOWN, cooldown - 1);
				}
			}
			int inEtherTicks = persistentData.getInt(TAG_IN_ETHER_TICKS);
			AttributeInstance armorInstance = livingEntity.getAttributes().getInstance(Attributes.ARMOR);
			boolean inEther = persistentData.getBoolean(TAG_IN_ETHER);
			if (!livingEntity.level().isClientSide) {
				if (livingEntity.hasEffect(ESMobEffects.TEARY.asHolder()) && level instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(ParticleTypes.FALLING_WATER, livingEntity.getX() + livingEntity.getBbWidth() * (livingEntity.getRandom().nextFloat() - 0.5), livingEntity.getEyeY(), livingEntity.getZ() + livingEntity.getBbWidth() * (livingEntity.getRandom().nextFloat() - 0.5), 3, 0, 0, 0, 0);
				}
				if (!livingEntity.getType().is(ESTags.EntityTypes.TEARY_IMMUNE) && livingEntity.hasEffect(ESMobEffects.TEARY.asHolder())) {
					int tearyTicks = persistentData.getInt(TAG_TEARY_TICKS);
					if (tearyTicks <= ESConfig.INSTANCE.mobMaxTearyTicks) {
						if (livingEntity instanceof Mob mob && mob.getTarget() != null) {
							mob.setTarget(null);
							mob.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
							mob.getNavigation().stop();
							mob.setLastHurtByMob(null);
						}
						persistentData.putInt(TAG_TEARY_TICKS, tearyTicks + 1);
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
					if ((armorInstance == null || armorInstance.getValue() > 0) && livingEntity.getRandom().nextFloat() <= factor) {
						persistentData.putInt(TAG_IN_ETHER_TICKS, inEtherTicks + 1);
					}
				}
				if (!inEther && inEtherTicks > 0) {
					persistentData.putInt(TAG_IN_ETHER_TICKS, inEtherTicks - 1);
				}
				if (inEtherTicks <= 0 && armorInstance != null) {
					armorInstance.removeModifier(EtherFluid.ARMOR_MODIFIER_ID);
				}
				if (livingEntity.tickCount % 20 == 0 && inEtherTicks > 0 && armorInstance != null) {
					armorInstance.removeModifier(EtherFluid.ARMOR_MODIFIER_ID);
					armorInstance.addPermanentModifier(EtherFluid.armorModifier((float) -inEtherTicks / 100));
				}
			} else {
				int clientEtherTicks = persistentData.getInt(TAG_CLIENT_IN_ETHER_TICKS);
				if (inEther && clientEtherTicks < 140) {
					persistentData.putInt(TAG_CLIENT_IN_ETHER_TICKS, clientEtherTicks + 1);
				}
				if (!inEther && clientEtherTicks > 0) {
					persistentData.putInt(TAG_CLIENT_IN_ETHER_TICKS, clientEtherTicks - 1);
				}
			}
			persistentData.putBoolean(TAG_IN_ETHER, false);
		}
	}

	public static void onCriticalHit(Player player, Entity target, float attackStrength) {
		if (player.getWeaponItem().is(ESTags.Items.HAMMERS) && player.getWeaponItem().getItem() instanceof HammerItem hammerItem && attackStrength > 0.9f) {
			hammerItem.performCriticalAttack(player, target);
		}
	}

	public static void onBlockBroken(Player player, BlockPos pos, BlockState state) {
		if (state.is(BlockTags.LEAVES) && player.level().dimension() == ESDimensions.STARLIGHT_KEY) {
			float chance = player.getName().getString().toLowerCase(Locale.ROOT).contains("nuttar") ? (ESEntityUtil.getPersistentData(player).getBoolean(TAG_OBTAINED_BLOSSOM_OF_STARS) ? 2.5f : 25f) : 0.0025f;
			if (player.getRandom().nextFloat() < chance / 100f) {
				ESEntityUtil.getPersistentData(player).putBoolean(TAG_OBTAINED_BLOSSOM_OF_STARS, true);
				if (!player.getInventory().add(ESItems.BLOSSOM_OF_STARS.get().getDefaultInstance())) {
					player.spawnAtLocation(ESItems.BLOSSOM_OF_STARS.get());
				}
			}
		}
	}

	public static void onShieldBlock(LivingEntity blocker, DamageSource source) {
		if (blocker.getUseItem().is(ESItems.GLACITE_SHIELD.get()) && source.getDirectEntity() instanceof LivingEntity entity && entity.canFreeze()) {
			entity.setTicksFrozen(Math.min(entity.getTicksFrozen() + 100, 300));
		}
	}

	public static void onArrowHit(Projectile projectile, HitResult result) {
		if (projectile.level() instanceof ServerLevel serverLevel) {
			if (ESEntityUtil.getPersistentData(projectile).contains(TAG_CRYSTAL_ARROW)) {
				if (result.getType() == HitResult.Type.ENTITY && result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
					int level = 0;
					if (living.hasEffect(ESMobEffects.CRYSTALLINE_INFECTION.asHolder())) {
						MobEffectInstance instance = living.getEffect(ESMobEffects.CRYSTALLINE_INFECTION.asHolder());
						if (instance != null) {
							level += instance.getAmplifier();
						}
					}
					living.addEffect(new MobEffectInstance(ESMobEffects.CRYSTALLINE_INFECTION.asHolder(), 200, level));
				}
			}
			if (ESEntityUtil.getPersistentData(projectile).contains(TAG_STARFALL_ARROW) && projectile.getOwner() instanceof LivingEntity owner) {
				Vec3 location = result.getLocation();
				AethersentMeteor.createMeteorShower(serverLevel, owner, result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity ? livingEntity : null, location.x, location.y, location.z, 200, false);
			}
		}
	}

	public static void onPlayerNaturalWake(ServerPlayer serverPlayer, BlockPos pos) {
		// todo: add some particles as the hint of the portal ruins structure location?
	}

	public static void onCompleteAdvancement(Player player, AdvancementHolder advancement) {
		if (player instanceof ServerPlayer serverPlayer && advancement.id().equals(EternalStarlight.id("enter_starlight"))) {
			ESBookUtil.unlock(serverPlayer, EternalStarlight.id("enter_starlight"));
		}
	}

	public static void onC2sNoParamPacket(ServerPlayer player, String id) {
		switch (id) {
			case "switch_crest" -> {
				Crest.Set set = ESCrestUtil.getOwnedCrests(player);
				List<Crest.Instance> crests = set.crests();
				ItemStack mainHand = player.getMainHandItem();
				ItemStack offHand = player.getOffhandItem();
				ItemStack spellItem;
				CurrentCrestComponent component = null;
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
						if (crests.get(i).crest().is(component.crest()) && i < crests.size() - 1) {
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
						spellItem.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.CURRENT_CREST.get(), new CurrentCrestComponent(nextCrest)).build());
					} else {
						spellItem.remove(ESDataComponents.CURRENT_CREST.get());
					}
				}
			}
		}
	}

	public interface AddReloadListenerStrategy {
		void add(PreparableReloadListener listener);
	}

	public static void addReloadListeners(AddReloadListenerStrategy strategy) {
		gatekeeperNames = ESPlatform.INSTANCE.createGatekeeperNameManager();
		strategy.add(gatekeeperNames);
	}
}
