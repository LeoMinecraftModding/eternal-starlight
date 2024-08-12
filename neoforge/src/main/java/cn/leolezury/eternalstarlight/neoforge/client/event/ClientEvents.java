package cn.leolezury.eternalstarlight.neoforge.client.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = EternalStarlight.ID, value = Dist.CLIENT)
public class ClientEvents {
	@SubscribeEvent
	private static void onClientTick(ClientTickEvent.Post event) {
		ClientHandlers.onClientTick();
	}

	@SubscribeEvent
	private static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
		Vec3 angle = ClientHandlers.computeCameraAngles(new Vec3(event.getPitch(), event.getYaw(), event.getRoll()));
		event.setPitch((float) angle.x);
		event.setYaw((float) angle.y);
	}

	@SubscribeEvent
	private static void onComputeFovModifier(ComputeFovModifierEvent event) {
		ClientHandlers.modifyFov(event.getFovModifier()).ifPresent(d -> event.setNewFovModifier((float) d));
	}

	@SubscribeEvent
	private static void onRenderFog(ViewportEvent.RenderFog event) {
		ClientHandlers.onRenderFog(event.getCamera(), event.getMode());
	}

	@SubscribeEvent
	private static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
		if (ClientHandlers.renderBossBar(event.getGuiGraphics(), event.getBossEvent(), event.getX(), event.getY())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	private static void onAfterRenderEntities(RenderLevelStageEvent event) {
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
			ClientHandlers.onAfterRenderEntities(event.getLevelRenderer().renderBuffers.bufferSource(), event.getPoseStack(), event.getPartialTick().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()));
		}
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
			ClientHandlers.onAfterRenderParticles();
		}
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
			ClientHandlers.onAfterRenderWeather();
		}
	}
}
