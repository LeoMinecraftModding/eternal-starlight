package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.LunarThornModel;
import cn.leolezury.eternalstarlight.common.entity.attack.LunarThorn;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class LunarThornRenderer extends EntityRenderer<LunarThorn> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/lunar_thorn.png");
	private final LunarThornModel<LunarThorn> model;

	public LunarThornRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new LunarThornModel<>(context.bakeLayer(LunarThornModel.LAYER_LOCATION));
	}

	@Override
	public void render(LunarThorn entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		poseStack.pushPose();
		float yRot = -Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());

		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yRot));
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0F, -1.5F, 0.0F);

		this.model.scale(Math.max(Mth.lerp(partialTicks, entity.oldClientScale, entity.clientScale), 0));
		RenderType renderType = this.model.renderType(getTextureLocation(entity));
		VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
		this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

		poseStack.popPose();
		super.render(entity, yaw, partialTicks, poseStack, bufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(LunarThorn entity) {
		return ENTITY_TEXTURE;
	}
}