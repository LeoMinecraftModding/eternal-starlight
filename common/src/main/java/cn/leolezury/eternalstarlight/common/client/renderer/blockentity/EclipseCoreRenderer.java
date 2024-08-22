package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.entity.EclipseCoreBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class EclipseCoreRenderer implements BlockEntityRenderer<EclipseCoreBlockEntity> {
	public EclipseCoreRenderer(BlockEntityRendererProvider.Context context) {

	}

	@Override
	public void render(EclipseCoreBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
		VertexConsumer vertexConsumer = ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.ECLIPSE);
		PoseStack.Pose pose = poseStack.last();
		vertexConsumer.addVertex(pose, 33, 0.5f, 33).setColor(1, 1, 1, 1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		vertexConsumer.addVertex(pose, 33, 0.5f, -32).setColor(1, 1, 1, 1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		vertexConsumer.addVertex(pose, -32, 0.5f, -32).setColor(1, 1, 1, 1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		vertexConsumer.addVertex(pose, -32, 0.5f, 33).setColor(1, 1, 1, 1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
	}

	@Override
	public boolean shouldRenderOffScreen(EclipseCoreBlockEntity blockEntity) {
		return true;
	}

	@Override
	public boolean shouldRender(EclipseCoreBlockEntity blockEntity, Vec3 vec3) {
		return true;
	}
}
