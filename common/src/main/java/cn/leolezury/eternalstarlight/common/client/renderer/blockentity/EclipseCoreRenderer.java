package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.entity.AbstractDuskLightBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class EclipseCoreRenderer extends DuskLightRenderer {
	public EclipseCoreRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(AbstractDuskLightBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
		super.render(blockEntity, f, poseStack, multiBufferSource, i, j);
		if (blockEntity.isLit()) {
			VertexConsumer vertexConsumer = ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.ECLIPSE);
			PoseStack.Pose pose = poseStack.last();
			float height = 7.5f;
			vertexConsumer.addVertex(pose, 33, height, 33).setColor(1, 1, 1, 1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, 33, height, -32).setColor(1, 1, 1, 1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, -32, height, -32).setColor(1, 1, 1, 1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, -32, height, 33).setColor(1, 1, 1, 1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		}
	}

	@Override
	public boolean shouldRenderOffScreen(AbstractDuskLightBlockEntity blockEntity) {
		return true;
	}

	@Override
	public boolean shouldRender(AbstractDuskLightBlockEntity blockEntity, Vec3 vec3) {
		return true;
	}
}
