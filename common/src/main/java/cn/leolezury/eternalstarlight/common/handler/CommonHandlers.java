package cn.leolezury.eternalstarlight.common.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.block.fluid.EtherFluid;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.DimensionInit;
import cn.leolezury.eternalstarlight.common.entity.projectile.AetherSentMeteor;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.EnchantmentInit;
import cn.leolezury.eternalstarlight.common.init.MobEffectInit;
import cn.leolezury.eternalstarlight.common.item.armor.AethersentArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.interfaces.TickableArmor;
import cn.leolezury.eternalstarlight.common.network.CancelWeatherPacket;
import cn.leolezury.eternalstarlight.common.network.ESWeatherPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.resource.book.BookManager;
import cn.leolezury.eternalstarlight.common.resource.book.chapter.ChapterManager;
import cn.leolezury.eternalstarlight.common.resource.gatekeeper.TheGatekeeperNameManager;
import cn.leolezury.eternalstarlight.common.util.*;
import cn.leolezury.eternalstarlight.common.weather.Weathers;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

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
        if (serverLevel.dimension() == DimensionInit.STARLIGHT_KEY) {
            WeatherUtil.getOrCreateWeathers(serverLevel);
        }
    }

    public static void onLevelTick(ServerLevel serverLevel) {
        if (serverLevel.dimension() == DimensionInit.STARLIGHT_KEY) {
            Weathers weathers = WeatherUtil.getOrCreateWeathers(serverLevel);
            weathers.tick();
            weathers.getActiveWeather().ifPresentOrElse((weatherInstance -> {
                ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ESWeatherPacket(weatherInstance.getWeather(), weatherInstance.currentDuration, weatherInstance.ticksSinceStarted));
            }), () -> {
                ESPlatform.INSTANCE.sendToAllClients(serverLevel, new CancelWeatherPacket(true));
            });
        }
    }

    public static void onRightClickBlock(Level level, Player player, InteractionHand hand, BlockPos pos) {
        if (!level.isClientSide && player.getItemInHand(hand).is(Items.GLOWSTONE_DUST) && level.getBlockState(pos).is(ESTags.Blocks.PORTAL_FRAME_BLOCKS)) {
            if (level.dimension() == DimensionInit.STARLIGHT_KEY || level.dimension() == Level.OVERWORLD) {
                for (Direction direction : Direction.Plane.VERTICAL) {
                    BlockPos framePos = pos.relative(direction);
                    if (((ESPortalBlock) BlockInit.STARLIGHT_PORTAL.get()).trySpawnPortal(level, framePos)) {
                        level.playSound(player, framePos, SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0F, 1.0F);
                        player.swing(hand);
                    }
                }
            }
        }
    }

    public static float onLivingHurt(LivingEntity entity, DamageSource source, float amount) {
        if (!entity.level().isClientSide) {
            int poisoningLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.POISONING.get(), entity);
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
                livingEntity.setSecondsOnFire(10);
            }
            if (source.is(DamageTypeTags.IS_FIRE)) {
                return amount / 2f;
            }
        }

        if (source.getDirectEntity() instanceof LivingEntity attacker && attacker.getItemInHand(InteractionHand.MAIN_HAND).is(ESTags.Items.THERMAL_SPRINGSTONE_WEAPONS)) {
            entity.setSecondsOnFire(10);
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

        return amount;
    }

    public static void onLivingTick(LivingEntity livingEntity) {
        SpellUtil.ticksSpellCoolDowns(livingEntity);
        List<ItemStack> armors = List.of(livingEntity.getItemBySlot(EquipmentSlot.HEAD), livingEntity.getItemBySlot(EquipmentSlot.CHEST), livingEntity.getItemBySlot(EquipmentSlot.LEGS), livingEntity.getItemBySlot(EquipmentSlot.FEET));
        for (ItemStack armor : armors) {
            if (armor.getItem() instanceof TickableArmor tickableArmor) {
                tickableArmor.tick(livingEntity.level(), livingEntity, armor);
            }
        }
        if (livingEntity.tickCount % 20 == 0) {
            int coolDown = ESUtil.getPersistentData(livingEntity).getInt("MeteorCoolDown");
            if (coolDown > 0) {
                ESUtil.getPersistentData(livingEntity).putInt("MeteorCoolDown", coolDown - 1);
            }
        }
        int inEtherTicks = ESUtil.getPersistentData(livingEntity).getInt("InEtherTicks");
        boolean inEther = BlockUtil.isEntityInBlock(livingEntity, BlockInit.ETHER.get());
        if (!livingEntity.level().isClientSide) {
            if (!inEther && inEtherTicks > 0) {
                ESUtil.getPersistentData(livingEntity).putInt("InEtherTicks", inEtherTicks - 1);
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
            int clientEtherTicks = ESUtil.getPersistentData(livingEntity).getInt("ClientEtherTicks");
            if (!inEther && clientEtherTicks > 0) {
                ESUtil.getPersistentData(livingEntity).putInt("ClientEtherTicks", clientEtherTicks - 1);
            }
        }
        if (livingEntity instanceof Player player) {
            CrestUtil.getCrests(player).forEach(crest -> crest.effect().ifPresent(mobEffectWithLevel ->
                    mobEffectWithLevel.forEach(mobEffect ->
                            player.addEffect(new MobEffectInstance(mobEffect.effect(), 20, mobEffect.level())
                            )
                    )
            ));
        }
    }

    public static void onArrowHit(Projectile projectile, HitResult result) {
        if (projectile.level() instanceof ServerLevel serverLevel) {
            if (ESUtil.getPersistentData(projectile).contains(EternalStarlight.MOD_ID + ":crystal")) {
                if (result.getType() == HitResult.Type.ENTITY && result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
                    int level = 0;
                    if (living.hasEffect(MobEffectInit.CRYSTALLINE_INFECTION.get())) {
                        level += living.getEffect(MobEffectInit.CRYSTALLINE_INFECTION.get()).getAmplifier();
                    }
                    living.addEffect(new MobEffectInstance(MobEffectInit.CRYSTALLINE_INFECTION.get(), 200, level));
                }
            }
            if (ESUtil.getPersistentData(projectile).contains(EternalStarlight.MOD_ID + ":starfall")) {
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
