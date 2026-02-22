package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.LunarSporeModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.LunarSpore;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class LunarSporeRenderer extends EntityRenderer<LunarSpore> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/lunar_spore.png");
	private final LunarSporeModel<LunarSpore> model;

	public LunarSporeRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new LunarSporeModel<>(context.bakeLayer(LunarSporeModel.LAYER_LOCATION));
	}

	@Override
	public boolean shouldRender(LunarSpore entity, Frustum frustum, double d, double e, double f) {
		return true;
	}

	@Override
	public void render(LunarSpore entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
		poseStack.pushPose();
		float yRot = -Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
		float xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 90f;
		float bob = entity.tickCount + partialTicks;

		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0F, -1.5F - entity.getBbHeight() / 2, 0.0F);

		this.model.prepareMobModel(entity, 0, 0, partialTicks);
		this.model.setupAnim(entity, 0, 0, bob, yRot, xRot);
		RenderType renderType = this.model.renderType(getTextureLocation(entity));
		VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
		this.model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);

		poseStack.popPose();

		super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
	}

	@Override
	public ResourceLocation getTextureLocation(LunarSpore entity) {
		return ENTITY_TEXTURE;
	}
}
