package cn.leolezury.eternalstarlight.forge.client.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;


@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Vec3 angle = ClientHandlers.computeCameraAngles(new Vec3(event.getPitch(), event.getYaw(), event.getRoll()));
        event.setPitch((float) angle.x);
        event.setYaw((float) angle.y);
        event.setRoll((float) angle.z);
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        ClientHandlers.onRenderFog(event.getCamera());
    }

    @SubscribeEvent
    public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if (ClientHandlers.renderBossBar(event.getGuiGraphics(), event.getBossEvent(), event.getX(), event.getY())) {
            event.setCanceled(true);
        }
    }
}
