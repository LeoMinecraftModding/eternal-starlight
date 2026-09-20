package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.SpiralArrowModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.SpiralArrow;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SpiralArrowRenderer extends EntityRenderer<SpiralArrow> {
	private static final ResourceLocation TEXTURE_LOCATION = EternalStarlight.id("textures/entity/spiral_arrow.png");
	private final SpiralArrowModel<SpiralArrow> model;

	public SpiralArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new SpiralArrowModel<>(context.bakeLayer(SpiralArrowModel.LAYER_LOCATION));
	}

	@Override
	public void render(SpiralArrow entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		if (entity.isAttached()) return;
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		float shake = (float) entity.shakeTime - partialTicks;
		if (shake > 0.0F) {
			poseStack.mulPose(Axis.ZP.rotationDegrees(-Mth.sin(shake * 3.0F) * shake));
		}
		poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
		poseStack.scale(0.8F, 0.8F, 0.8F);
		poseStack.translate(0.35F, -1.45F, 0.0F);
		VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(TEXTURE_LOCATION));
		this.model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTicks, entity.getYRot(), entity.getXRot());
		this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(SpiralArrow entity) {
		return TEXTURE_LOCATION;
	}
}
