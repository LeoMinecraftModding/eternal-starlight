package cn.leolezury.eternalstarlight.common.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.fluid.EtherFluid;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.item.armor.AethersentArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.GlaciteArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringstoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.component.CurrentCrestComponent;
import cn.leolezury.eternalstarlight.common.item.interfaces.TickableArmor;
import cn.leolezury.eternalstarlight.common.item.misc.ManaCrystalItem;
import cn.leolezury.eternalstarlight.common.network.NoParametersPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateSpellDataPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateWeatherPacket;
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class CommonHandlers {
	private static final String TAG_IN_ETHER_TICKS = "in_ether_ticks";
	public static final String TAG_CLIENT_IN_ETHER_TICKS = "client_in_ether_ticks";
	private static final String TAG_OBTAINED_BLOSSOM_OF_STARS = "obtained_blossom_of_stars";
	public static final String TAG_CRYSTAL_ARROW = EternalStarlight.ID + ":crystal";
	public static final String TAG_STARFALL_ARROW = EternalStarlight.ID + ":starfall";
	public static final String TAG_IN_ABYSSAL_FIRE_TICKS = "in_abyssal_fire_ticks";
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
		for (ServerLevel level : server.getAllLevels()) {
			level.getChunkSource().chunkMap.entityMap.forEach((i, tracked) -> {
				if (tracked.entity instanceof SpellCaster caster) {
					tracked.seenBy.forEach(connection -> {
						ServerPlayer player = connection.getPlayer();
						ESPlatform.INSTANCE.sendToClient(player, new UpdateSpellDataPacket(tracked.entity.getId(), caster.getESSpellData()));
					});
					if (tracked.entity instanceof ServerPlayer player) {
						ESPlatform.INSTANCE.sendToClient(player, new UpdateSpellDataPacket(tracked.entity.getId(), caster.getESSpellData()));
					}
				}
			});
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

	public static float onModifyLivingHurtDamage(LivingEntity entity, DamageSource source, float amount) {
		if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThermalSpringstoneArmorItem
			|| entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ThermalSpringstoneArmorItem
			|| entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ThermalSpringstoneArmorItem
			|| entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ThermalSpringstoneArmorItem
		) {
			if (source.is(DamageTypeTags.IS_FIRE)) {
				return amount / 2f;
			}
		}
		return amount;
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

			if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.getItemInHand(InteractionHand.MAIN_HAND).is(ESTags.Items.THERMAL_SPRINGSTONE_WEAPONS)) {
				entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 200);
			}

			if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof GlaciteArmorItem
				|| entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof GlaciteArmorItem
				|| entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof GlaciteArmorItem
				|| entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof GlaciteArmorItem
			) {
				if (source.getDirectEntity() instanceof LivingEntity livingEntity) {
					livingEntity.setTicksFrozen(livingEntity.getTicksFrozen() + 80);
				}
			}

			if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.getItemInHand(InteractionHand.MAIN_HAND).is(ESTags.Items.GLACITE_WEAPONS) && entity.canFreeze()) {
				entity.setTicksFrozen(entity.getTicksFrozen() + 80);
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

	public static void onEntityTick(Entity entity) {
		Level level = entity.level();
		if (entity instanceof ItemEntity item) {
			if (!item.level().isClientSide) {
				if (item.tickCount % 100 == 0 && item.getItem().is(ESTags.Items.MUSIC_DISCS) && !item.getItem().is(ESItems.MUSIC_DISC_SPIRIT.get()) && ESBlockUtil.isEntityInBlock(item, ESBlocks.ETHER.get())) {
					item.setItem(ESItems.MUSIC_DISC_SPIRIT.get().getDefaultInstance());
					item.addDeltaMovement(new Vec3(0, 0.25, 0));
				}
			} else {
				if ((item.getItem().is(ESTags.Items.MANA_CRYSTALS) || item.getItem().getItem() == ESItems.MANA_CRYSTAL_SHARD.get())) {
					EternalStarlight.getClientHelper().spawnManaCrystalItemParticles(item.getItem().getItem() instanceof ManaCrystalItem crystalItem ? crystalItem.getManaType() : ManaType.LUNAR, item.position().add(0, item.getBbHeight() / 2, 0));
				}
			}
		}
		CompoundTag persistentData = ESEntityUtil.getPersistentData(entity);
		int inAbyssalFireTicks = persistentData.getInt(TAG_IN_ABYSSAL_FIRE_TICKS);
		persistentData.putInt(TAG_IN_ABYSSAL_FIRE_TICKS, Math.max(inAbyssalFireTicks - 1, 0));
		if (entity instanceof LivingEntity livingEntity) {
			ESSpellUtil.tickSpells(livingEntity);
			if (livingEntity instanceof Player player && !livingEntity.level().isClientSide) {
				ESCrestUtil.tickCrests(player);
			}
			List<ItemStack> armors = List.of(livingEntity.getItemBySlot(EquipmentSlot.HEAD), livingEntity.getItemBySlot(EquipmentSlot.CHEST), livingEntity.getItemBySlot(EquipmentSlot.LEGS), livingEntity.getItemBySlot(EquipmentSlot.FEET));
			for (ItemStack armor : armors) {
				if (armor.getItem() instanceof TickableArmor tickableArmor) {
					tickableArmor.tick(livingEntity.level(), livingEntity, armor);
				}
			}
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
			if (livingEntity.tickCount % 20 == 0) {
				int cooldown = persistentData.getInt(AethersentMeteor.TAG_METEOR_COOLDOWN);
				if (cooldown > 0) {
					persistentData.putInt(AethersentMeteor.TAG_METEOR_COOLDOWN, cooldown - 1);
				}
			}
			int inEtherTicks = persistentData.getInt(TAG_IN_ETHER_TICKS);
			AttributeInstance armorInstance = livingEntity.getAttributes().getInstance(Attributes.ARMOR);
			boolean inEther = ESBlockUtil.isEntityInBlock(livingEntity, ESBlocks.ETHER.get());
			if (!livingEntity.level().isClientSide) {
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
			entity.setTicksFrozen(entity.getTicksFrozen() + 100);
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
			ESBookUtil.unlockFor(serverPlayer, EternalStarlight.id("enter_starlight"));
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
								if (crests.get(j).crest().value().spell().isPresent()) {
									nextCrest = crests.get(j).crest();
									break find;
								}
							}
						}
					}
				} else {
					for (Crest.Instance instance : crests) {
						if (instance.crest().value().spell().isPresent()) {
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
