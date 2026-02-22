package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.PermafrostSpitModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.PermafrostSpit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PermafrostSpitRenderer extends EntityRenderer<PermafrostSpit> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/permafrost_spit.png");
	private static final ResourceLocation SMALL_TEXTURE = EternalStarlight.id("textures/entity/permafrost_spit_small.png");

	private final PermafrostSpitModel<PermafrostSpit> model;
	private final PermafrostSpitModel<PermafrostSpit> smallModel;

	public PermafrostSpitRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new PermafrostSpitModel<>(context.bakeLayer(PermafrostSpitModel.LAYER_LOCATION));
		smallModel = new PermafrostSpitModel<>(context.bakeLayer(PermafrostSpitModel.SMALL_LAYER_LOCATION));
	}

	@Override
	public void render(PermafrostSpit entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
		poseStack.pushPose();
		float yRot = -Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
		float xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) - 90f;
		float bob = entity.tickCount + partialTicks;

		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0F, -1.5F - entity.getBbHeight() / 2, 0.0F);

		PermafrostSpitModel<PermafrostSpit> spitModel = entity.isSmall() ? smallModel : model;

		spitModel.prepareMobModel(entity, 0, 0, partialTicks);
		spitModel.setupAnim(entity, 0, 0, bob, yRot, xRot);
		RenderType renderType = spitModel.renderType(getTextureLocation(entity));
		VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
		spitModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);

		poseStack.popPose();

		super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
		super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
	}

	@Override
	public ResourceLocation getTextureLocation(PermafrostSpit entity) {
		return entity.isSmall() ? SMALL_TEXTURE : ENTITY_TEXTURE;
	}
}
