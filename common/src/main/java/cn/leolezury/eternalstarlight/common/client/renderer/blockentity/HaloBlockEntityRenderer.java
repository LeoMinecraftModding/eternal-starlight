package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.entity.HaloBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;

public class HaloBlockEntityRenderer implements BlockEntityRenderer<HaloBlockEntity> {
	public HaloBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		super();
	}

	@Override
	public void render(HaloBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
		var mainVertex = ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.HALO);
		var pose = poseStack.last();
		poseStack.pushPose();
		mainVertex.addVertex(pose, 33, 0.5f, 33).setColor(1, 1, 1, 1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		mainVertex.addVertex(pose, 33, 0.5f, -32).setColor(1, 1, 1, 1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		mainVertex.addVertex(pose, -32, 0.5f, -32).setColor(1, 1, 1, 1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		mainVertex.addVertex(pose, -32, 0.5f, 33).setColor(1, 1, 1, 1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		poseStack.popPose();
	}

	@Override
	public boolean shouldRenderOffScreen(HaloBlockEntity blockEntity) {
		return true;
	}

	@Override
	public boolean shouldRender(HaloBlockEntity blockEntity, Vec3 vec3) {
		return true;
	}
}
