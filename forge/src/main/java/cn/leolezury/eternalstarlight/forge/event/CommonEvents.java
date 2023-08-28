package cn.leolezury.eternalstarlight.forge.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID)
public class CommonEvents {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        CommonHandlers.onRightClickBlock(event.getLevel(), event.getEntity(), event.getHand(), event.getPos());
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        CommonHandlers.onLivingHurt(event.getEntity(), event.getSource(), event.getAmount());
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        CommonHandlers.onLivingTick(event.getEntity());
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        CommonHandlers.onArrowHit(event.getProjectile(), event.getRayTraceResult());
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        CommonHandlers.addReloadListeners(event::addListener);
    }
}
