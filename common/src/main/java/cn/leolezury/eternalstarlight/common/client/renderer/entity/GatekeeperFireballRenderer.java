package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.entity.projectile.GatekeeperFireball;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemDisplayContext;

@Environment(EnvType.CLIENT)
public class GatekeeperFireballRenderer extends ThrownItemRenderer<GatekeeperFireball> {
	private final ItemRenderer itemRenderer;

	public GatekeeperFireballRenderer(EntityRendererProvider.Context context) {
		super(context, 3.0f, true);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(GatekeeperFireball entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25)) {
			poseStack.pushPose();
			float scale = (Math.min(60f, entity.getSpawnedTicks() + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true)) / 60f) * 3f;
			poseStack.scale(scale, scale, scale);
			poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
			this.itemRenderer.renderStatic(((ItemSupplier) entity).getItem(), ItemDisplayContext.GROUND, i, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, entity.level(), entity.getId());
			poseStack.popPose();
		}
	}
}
