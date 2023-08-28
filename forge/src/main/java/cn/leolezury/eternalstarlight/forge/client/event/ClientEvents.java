package cn.leolezury.eternalstarlight.forge.client.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


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
