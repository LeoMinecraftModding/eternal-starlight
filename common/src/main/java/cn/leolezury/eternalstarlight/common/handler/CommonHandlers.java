package cn.leolezury.eternalstarlight.common.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.fluid.EtherFluid;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.entity.projectile.AetherSentMeteor;
import cn.leolezury.eternalstarlight.common.item.armor.AethersentArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.GlaciteArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.interfaces.TickableArmor;
import cn.leolezury.eternalstarlight.common.network.CancelWeatherPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateWeatherPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEnchantments;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.resource.book.BookManager;
import cn.leolezury.eternalstarlight.common.resource.book.chapter.ChapterManager;
import cn.leolezury.eternalstarlight.common.resource.gatekeeper.TheGatekeeperNameManager;
import cn.leolezury.eternalstarlight.common.util.*;
import cn.leolezury.eternalstarlight.common.weather.Weathers;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class CommonHandlers {
    private static TheGatekeeperNameManager gatekeeperNameManager;
    private static BookManager bookManager;
    private static ChapterManager chapterManager;

    public static String getGatekeeperName() {
        return gatekeeperNameManager.getTheGatekeeperName();
    }
    public static BookManager getBookManager() {
        return bookManager;
    }
    public static ChapterManager getChapterManager() {
        return chapterManager;
    }

    private static int ticksSinceLastUpdate = 0;

    private static final AttributeModifier AMARAMBER_BONUS = new AttributeModifier(UUID.fromString("915CFA7C-C624-495F-0193-604728718B6B"), "Amaramber Armor Bonus", 7, AttributeModifier.Operation.ADD_VALUE);

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
        if (!entity.level().isClientSide) {
            int poisoningLevel = EnchantmentHelper.getEnchantmentLevel(ESEnchantments.POISONING.get(), entity);
            if (poisoningLevel > 0 && source.getEntity() instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * poisoningLevel, poisoningLevel - 1));
            }
        }
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
                AetherSentMeteor.createMeteorShower(serverLevel, entity, livingEntity, location.x, location.y, location.z, 200, true);
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
        ESSpellUtil.ticksSpellCoolDowns(livingEntity);
        if (livingEntity instanceof Player player) {
            ESCrestUtil.tickCrests(player);
        }
        List<ItemStack> armors = List.of(livingEntity.getItemBySlot(EquipmentSlot.HEAD), livingEntity.getItemBySlot(EquipmentSlot.CHEST), livingEntity.getItemBySlot(EquipmentSlot.LEGS), livingEntity.getItemBySlot(EquipmentSlot.FEET));
        for (ItemStack armor : armors) {
            if (armor.getItem() instanceof TickableArmor tickableArmor) {
                tickableArmor.tick(livingEntity.level(), livingEntity, armor);
            }
        }
        AttributeInstance instance = livingEntity.getAttributes().getInstance(Attributes.ARMOR);
        if (instance != null) {
            instance.removeModifier(AMARAMBER_BONUS.id());
            if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(ESItems.AMARAMBER_HELMET.get())
                    && livingEntity.getItemBySlot(EquipmentSlot.CHEST).is(ESItems.AMARAMBER_CHESTPLATE.get())
                    && livingEntity.getItemBySlot(EquipmentSlot.LEGS).isEmpty()
                    && livingEntity.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
                instance.addPermanentModifier(AMARAMBER_BONUS);
            }
        }
        if (livingEntity.tickCount % 20 == 0) {
            int coolDown = ESEntityUtil.getPersistentData(livingEntity).getInt("MeteorCoolDown");
            if (coolDown > 0) {
                ESEntityUtil.getPersistentData(livingEntity).putInt("MeteorCoolDown", coolDown - 1);
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
                armorInstance.removeModifier(EtherFluid.ARMOR_MODIFIER_UUID);
            }
            if (livingEntity.tickCount % 20 == 0 && inEtherTicks > 0 && armorInstance != null) {
                armorInstance.removeModifier(EtherFluid.ARMOR_MODIFIER_UUID);
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
            if (ESEntityUtil.getPersistentData(projectile).contains(EternalStarlight.MOD_ID + ":crystal")) {
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
            if (ESEntityUtil.getPersistentData(projectile).contains(EternalStarlight.MOD_ID + ":starfall")) {
                Vec3 location = result.getLocation();
                AetherSentMeteor.createMeteorShower(serverLevel, projectile.getOwner() instanceof LivingEntity livingEntity ? livingEntity : null, result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity ? livingEntity : null, location.x, location.y, location.z, 200, false);
            }
        }
    }

    public interface AddReloadListenerStrategy {
        void add(PreparableReloadListener listener);
    }

    public static void addReloadListeners(AddReloadListenerStrategy strategy) {
        bookManager = ESPlatform.INSTANCE.createBookManager();
        chapterManager = ESPlatform.INSTANCE.createChapterManager();
        gatekeeperNameManager = ESPlatform.INSTANCE.createGatekeeperNameManager();
        strategy.add(bookManager);
        strategy.add(chapterManager);
        strategy.add(gatekeeperNameManager);
    }
}
