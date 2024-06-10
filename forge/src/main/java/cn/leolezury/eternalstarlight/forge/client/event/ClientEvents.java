package cn.leolezury.eternalstarlight.forge.client.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = EternalStarlight.ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        ClientHandlers.onClientTick();
    }

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Vec3 angle = ClientHandlers.computeCameraAngles(new Vec3(event.getPitch(), event.getYaw(), event.getRoll()));
        event.setPitch((float) angle.x);
        event.setYaw((float) angle.y);
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        ClientHandlers.onRenderFog(event.getCamera(), event.getMode());
    }

    @SubscribeEvent
    public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if (ClientHandlers.renderBossBar(event.getGuiGraphics(), event.getBossEvent(), event.getX(), event.getY())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onAfterRenderEntities(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            ClientHandlers.onAfterRenderEntities(event.getLevelRenderer().renderBuffers.bufferSource(), event.getPoseStack(), event.getPartialTick());
        }
    }
}
