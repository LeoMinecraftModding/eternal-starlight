package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.entity.EclipseCoreBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class EclipseCoreRenderer extends DuskLightRenderer<EclipseCoreBlockEntity> {
	public EclipseCoreRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(EclipseCoreBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
		super.render(blockEntity, f, poseStack, multiBufferSource, i, j);
		VertexConsumer vertexConsumer = ESClientHandler.AFTER_LEVEL_BUFFER_SOURCE.getBuffer(ESRenderType.ECLIPSE);
		PoseStack.Pose pose = poseStack.last();
		float height = 0.5f + 7 * blockEntity.getEclipseProgress(f);
		float size = 32 * blockEntity.getEclipseProgress(f);
		vertexConsumer.addVertex(pose, size + 1, height, size + 1).setColor(1, 1, 1, 1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		vertexConsumer.addVertex(pose, size + 1, height, -size).setColor(1, 1, 1, 1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		vertexConsumer.addVertex(pose, -size, height, -size).setColor(1, 1, 1, 1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		vertexConsumer.addVertex(pose, -size, height, size + 1).setColor(1, 1, 1, 1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
	}

	@Override
	public boolean shouldRenderOffScreen(EclipseCoreBlockEntity blockEntity) {
		return true;
	}

	@Override
	public int getViewDistance() {
		return 256;
	}
}
