package cn.leolezury.eternalstarlight.forge.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID)
public class CommonEvents {
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            CommonHandlers.onServerTick(event.getServer());
        }
    }

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
