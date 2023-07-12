package cn.leolezury.eternalstarlight.event.server;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.block.SLPortalBlock;
import cn.leolezury.eternalstarlight.init.BlockInit;
import cn.leolezury.eternalstarlight.init.DimensionInit;
import cn.leolezury.eternalstarlight.init.EnchantmentInit;
import cn.leolezury.eternalstarlight.init.ItemInit;
import cn.leolezury.eternalstarlight.item.armor.SwampSilverArmorItem;
import cn.leolezury.eternalstarlight.item.armor.ThermalSpringStoneArmorItem;
import cn.leolezury.eternalstarlight.manager.TheGatekeeperNameManager;
import cn.leolezury.eternalstarlight.util.SLTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID)
public class ServerEvents {
    private static TheGatekeeperNameManager gatekeeperNameManager;

    public static String getGatekeeperName() {
        return gatekeeperNameManager.getTheGatekeeperName();
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getLevel().isClientSide && event.getItemStack().is(Items.GLOWSTONE_DUST) && event.getLevel().getBlockState(event.getPos()).is(SLTags.Blocks.PORTAL_FRAME_BLOCKS)) {
            Player player = event.getEntity();
            Level level = event.getLevel();
            if (level.dimension() == DimensionInit.STARLIGHT_KEY || level.dimension() == Level.OVERWORLD) {
                for (Direction direction : Direction.Plane.VERTICAL) {
                    BlockPos framePos = event.getPos().relative(direction);
                    if (((SLPortalBlock) BlockInit.STARLIGHT_PORTAL.get()).trySpawnPortal(level, framePos)) {
                        level.playSound(player, framePos, SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0F, 1.0F);
                        player.swing(event.getHand());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().level().isClientSide) {
            int poisoningLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.POISONING.get(), event.getEntity());
            if (poisoningLevel > 0 && event.getSource().getEntity() instanceof LivingEntity entity) {
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * poisoningLevel, poisoningLevel - 1));
            }
        }
        if (event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThermalSpringStoneArmorItem
                || event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ThermalSpringStoneArmorItem
                || event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ThermalSpringStoneArmorItem
                || event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ThermalSpringStoneArmorItem
        ) {
            if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
                livingEntity.setSecondsOnFire(10);
            }
            if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
                event.setAmount(event.getAmount() / 2);
            }
        }

        if (event.getSource().getDirectEntity() instanceof LivingEntity attacker && attacker.getItemInHand(InteractionHand.MAIN_HAND).is(SLTags.Items.THERMAL_SPRINGSTONE_WEAPONS)) {
            event.getEntity().setSecondsOnFire(10);
        }
    }

    @SubscribeEvent
    public static void onAddMobEffect(MobEffectEvent.Applicable event) {
        if (event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SwampSilverArmorItem
                && event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SwampSilverArmorItem
                && event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SwampSilverArmorItem
                && event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SwampSilverArmorItem
        ) {
            if (!event.getEffectInstance().getEffect().isBeneficial()) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        gatekeeperNameManager = new TheGatekeeperNameManager();
        event.addListener(gatekeeperNameManager);
    }
}
