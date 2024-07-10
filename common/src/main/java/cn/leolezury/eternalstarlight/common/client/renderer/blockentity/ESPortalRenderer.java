package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.block.entity.ESPortalBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;

@Environment(EnvType.CLIENT)
public class ESPortalRenderer<T extends ESPortalBlockEntity> implements BlockEntityRenderer<T> {
	public ESPortalRenderer(BlockEntityRendererProvider.Context context) {

	}

	@Override
	public void render(T portal, float f, PoseStack stack, MultiBufferSource multiBufferSource, int i, int j) {
		if (portal.getBlockState().getValue(ESPortalBlock.CENTER)) {
			VertexConsumer vertexConsumer = ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PORTAL);
			PoseStack.Pose pose = stack.last();
			float radius = 0.6f * portal.getBlockState().getValue(ESPortalBlock.SIZE) * (Math.min(portal.getClientSideTickCount() + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true), 60f) / 60f);
			if (portal.getBlockState().getValue(ESPortalBlock.AXIS) == Direction.Axis.X) {
				vertexConsumer.addVertex(pose, -radius, -radius, 0.5f).setColor(1, 1, 1, 1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(pose, 0.0F, 1.0F, 0.0F);
				vertexConsumer.addVertex(pose, -radius, 1 + radius, 0.5f).setColor(1, 1, 1, 1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(pose, 0.0F, 1.0F, 0.0F);
				vertexConsumer.addVertex(pose, 1 + radius, 1 + radius, 0.5f).setColor(1, 1, 1, 1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(pose, 0.0F, 1.0F, 0.0F);
				vertexConsumer.addVertex(pose, 1 + radius, -radius, 0.5f).setColor(1, 1, 1, 1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(pose, 0.0F, 1.0F, 0.0F);
			} else {
				vertexConsumer.addVertex(pose, 0.5f, -radius, -radius).setColor(1, 1, 1, 1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(pose, 0.0F, 1.0F, 0.0F);
				vertexConsumer.addVertex(pose, 0.5f, 1 + radius, -radius).setColor(1, 1, 1, 1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(pose, 0.0F, 1.0F, 0.0F);
				vertexConsumer.addVertex(pose, 0.5f, 1 + radius, 1 + radius).setColor(1, 1, 1, 1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(pose, 0.0F, 1.0F, 0.0F);
				vertexConsumer.addVertex(pose, 0.5f, -radius, 1 + radius).setColor(1, 1, 1, 1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(pose, 0.0F, 1.0F, 0.0F);
			}
		}
	}
}
