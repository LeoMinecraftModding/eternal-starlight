package cn.leolezury.eternalstarlight.common.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.fluid.EtherFluid;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.item.armor.AethersentArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.GlaciteArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.interfaces.TickableArmor;
import cn.leolezury.eternalstarlight.common.network.CancelWeatherPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateSpellDataPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateWeatherPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.resource.gatekeeper.TheGatekeeperNameManager;
import cn.leolezury.eternalstarlight.common.util.*;
import cn.leolezury.eternalstarlight.common.weather.Weathers;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Locale;

public class CommonHandlers {
    private static TheGatekeeperNameManager gatekeeperNameManager;

    public static String getGatekeeperName() {
        return gatekeeperNameManager.getTheGatekeeperName();
    }

    private static int ticksSinceLastUpdate = 0;

    private static final AttributeModifier AMARAMBER_BONUS = new AttributeModifier(EternalStarlight.id("armor.amaramber_bonus"), 7, AttributeModifier.Operation.ADD_VALUE);

    private static final AttributeModifier DAGGER_OF_HUNGER_BONUS = new AttributeModifier(EternalStarlight.id("weapon.dagger_of_hunger.bonus"), 1, AttributeModifier.Operation.ADD_VALUE);
    private static final AttributeModifier DAGGER_OF_HUNGER_SPEED_BONUS = new AttributeModifier(EternalStarlight.id("weapon.dagger_of_hunger.speed_bonus"), 0.5, AttributeModifier.Operation.ADD_VALUE);
    private static final AttributeModifier DAGGER_OF_HUNGER_PENALTY = new AttributeModifier(EternalStarlight.id("weapon.dagger_of_hunger.penalty"), -1, AttributeModifier.Operation.ADD_VALUE);
    private static final AttributeModifier DAGGER_OF_HUNGER_SPEED_PENALTY = new AttributeModifier(EternalStarlight.id("weapon.dagger_of_hunger.speed_penalty"), -0.5, AttributeModifier.Operation.ADD_VALUE);

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
                        ESPlatform.INSTANCE.sendToClient(player, new UpdateSpellDataPacket(tracked.entity.getId(), caster.getSpellData()));
                    });
                    if (tracked.entity instanceof ServerPlayer player) {
                        ESPlatform.INSTANCE.sendToClient(player, new UpdateSpellDataPacket(tracked.entity.getId(), caster.getSpellData()));
                    }
                }
            });
        }
    }

    public static void onLevelLoad(ServerLevel serverLevel) {
        if (serverLevel.dimension() == ESDimensions.STARLIGHT_KEY) {
            ESWeatherUtil.getOrCreateWeathers(serverLevel);
        }
    }

    public static void onLevelTick(ServerLevel serverLevel) {
        if (serverLevel.dimension() == ESDimensions.STARLIGHT_KEY) {
            Weathers weathers = ESWeatherUtil.getOrCreateWeathers(serverLevel);
            weathers.tick();
            weathers.getActiveWeather().ifPresentOrElse((weatherInstance -> {
                ESPlatform.INSTANCE.sendToAllClients(serverLevel, new UpdateWeatherPacket(weatherInstance.getWeather(), weatherInstance.currentDuration, weatherInstance.ticksSinceStarted));
            }), () -> {
                ESPlatform.INSTANCE.sendToAllClients(serverLevel, new CancelWeatherPacket(true));
            });
        }
    }

    public static float onLivingHurt(LivingEntity entity, DamageSource source, float amount) {
        if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThermalSpringStoneArmorItem
                || entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ThermalSpringStoneArmorItem
                || entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ThermalSpringStoneArmorItem
                || entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ThermalSpringStoneArmorItem
        ) {
            if (source.getDirectEntity() instanceof LivingEntity livingEntity) {
                livingEntity.setRemainingFireTicks(livingEntity.getRemainingFireTicks() + 200);
            }
            if (source.is(DamageTypeTags.IS_FIRE)) {
                return amount / 2f;
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

        return amount;
    }

    public static void onLivingTick(LivingEntity livingEntity) {
        ESSpellUtil.tickSpells(livingEntity);
        if (livingEntity instanceof Player player) {
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
            armorAttribute.removeModifier(AMARAMBER_BONUS.id());
            if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(ESItems.AMARAMBER_HELMET.get())
                    && livingEntity.getItemBySlot(EquipmentSlot.CHEST).is(ESItems.AMARAMBER_CHESTPLATE.get())
                    && livingEntity.getItemBySlot(EquipmentSlot.LEGS).isEmpty()
                    && livingEntity.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
                armorAttribute.addPermanentModifier(AMARAMBER_BONUS);
            }
        }
        AttributeInstance damageAttribute = livingEntity.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
        if (damageAttribute != null) {
            damageAttribute.removeModifier(DAGGER_OF_HUNGER_BONUS.id());
            damageAttribute.removeModifier(DAGGER_OF_HUNGER_PENALTY.id());
            if (livingEntity.getMainHandItem().is(ESItems.DAGGER_OF_HUNGER.get())) {
                int state = Math.min(2, (int) ((livingEntity.getMainHandItem().getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0f) + 1f) * 1.5f));
                if (state == 0) {
                    damageAttribute.addPermanentModifier(DAGGER_OF_HUNGER_PENALTY);
                }
                if (state == 2) {
                    damageAttribute.addPermanentModifier(DAGGER_OF_HUNGER_BONUS);
                }
            }
        }
        AttributeInstance attackSpeedAttribute = livingEntity.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
        if (attackSpeedAttribute != null) {
            attackSpeedAttribute.removeModifier(DAGGER_OF_HUNGER_SPEED_BONUS.id());
            attackSpeedAttribute.removeModifier(DAGGER_OF_HUNGER_SPEED_PENALTY.id());
            if (livingEntity.getMainHandItem().is(ESItems.DAGGER_OF_HUNGER.get())) {
                int state = Math.min(2, (int) ((livingEntity.getMainHandItem().getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0f) + 1f) * 1.5f));
                if (state == 0) {
                    attackSpeedAttribute.addPermanentModifier(DAGGER_OF_HUNGER_SPEED_PENALTY);
                }
                if (state == 2) {
                    attackSpeedAttribute.addPermanentModifier(DAGGER_OF_HUNGER_SPEED_BONUS);
                }
            }
        }
        if (livingEntity.tickCount % 20 == 0) {
            int cooldown = ESEntityUtil.getPersistentData(livingEntity).getInt("MeteorCooldown");
            if (cooldown > 0) {
                ESEntityUtil.getPersistentData(livingEntity).putInt("MeteorCooldown", cooldown - 1);
            }
        }
        int inEtherTicks = ESEntityUtil.getPersistentData(livingEntity).getInt("InEtherTicks");
        boolean inEther = ESBlockUtil.isEntityInBlock(livingEntity, ESBlocks.ETHER.get());
        if (!livingEntity.level().isClientSide) {
            if (!inEther && inEtherTicks > 0) {
                ESEntityUtil.getPersistentData(livingEntity).putInt("InEtherTicks", inEtherTicks - 1);
            }
            AttributeInstance armorInstance = livingEntity.getAttributes().getInstance(Attributes.ARMOR);
            if (inEtherTicks <= 0 && armorInstance != null) {
                armorInstance.removeModifier(EtherFluid.ARMOR_MODIFIER_ID);
            }
            if (livingEntity.tickCount % 20 == 0 && inEtherTicks > 0 && armorInstance != null) {
                armorInstance.removeModifier(EtherFluid.ARMOR_MODIFIER_ID);
                armorInstance.addPermanentModifier(EtherFluid.armorModifier((float) -inEtherTicks / 100));
            }
        } else {
            int clientEtherTicks = ESEntityUtil.getPersistentData(livingEntity).getInt("ClientEtherTicks");
            if (!inEther && clientEtherTicks > 0) {
                ESEntityUtil.getPersistentData(livingEntity).putInt("ClientEtherTicks", clientEtherTicks - 1);
            }
        }
    }

    public static void onBlockBroken(Player player, BlockPos pos, BlockState state) {
        if (state.is(BlockTags.LEAVES) && player.level().dimension() == ESDimensions.STARLIGHT_KEY) {
            float chance = player.getName().getString().toLowerCase(Locale.ROOT).contains("nuttar") ? (ESEntityUtil.getPersistentData(player).getBoolean("ObtainedBlossomOfStars") ? 2.5f : 25f) : 0.0025f;
            if (player.getRandom().nextFloat() < chance / 100f) {
                ESEntityUtil.getPersistentData(player).putBoolean("ObtainedBlossomOfStars", true);
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
            if (ESEntityUtil.getPersistentData(projectile).contains(EternalStarlight.ID + ":crystal")) {
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
            if (ESEntityUtil.getPersistentData(projectile).contains(EternalStarlight.ID + ":starfall")) {
                Vec3 location = result.getLocation();
                AethersentMeteor.createMeteorShower(serverLevel, projectile.getOwner() instanceof LivingEntity livingEntity ? livingEntity : null, result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity ? livingEntity : null, location.x, location.y, location.z, 200, false);
            }
        }
    }

    public static void onCompleteAdvancement(Player player, AdvancementHolder advancement) {
        if (player instanceof ServerPlayer serverPlayer && advancement.id().equals(EternalStarlight.id("enter_starlight"))) {
            ESBookUtil.unlockFor(serverPlayer, EternalStarlight.id("enter_starlight"));
        }
    }

    public interface AddReloadListenerStrategy {
        void add(PreparableReloadListener listener);
    }

    public static void addReloadListeners(AddReloadListenerStrategy strategy) {
        gatekeeperNameManager = ESPlatform.INSTANCE.createGatekeeperNameManager();
        strategy.add(gatekeeperNameManager);
    }
}
