package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.EnergySpark;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class EnergySparkRenderer extends EntityRenderer<EnergySpark> {
	private static final ResourceLocation TEXTURE_LOCATION = EternalStarlight.id("textures/entity/energy_spark.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

	public EnergySparkRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(EnergySpark entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		float scale = (Math.min(60f, entity.getSpawnedTicks() + partialTicks) / 60f) * 0.5f;
		poseStack.translate(0, entity.getBbHeight() / 2, 0);
		poseStack.scale(scale, scale, scale);
		poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		PoseStack.Pose pose = poseStack.last();
		VertexConsumer vertexConsumer = buffer.getBuffer(RENDER_TYPE);
		vertex(vertexConsumer, pose, LightTexture.FULL_BRIGHT, 0.5F * Mth.SQRT_OF_TWO * Mth.sin(Mth.lerp(partialTicks, entity.oSpin, entity.spin)), 0.5F * Mth.SQRT_OF_TWO * Mth.cos(Mth.lerp(partialTicks, entity.oSpin, entity.spin)), 0, 1);
		vertex(vertexConsumer, pose, LightTexture.FULL_BRIGHT, 0.5F * Mth.SQRT_OF_TWO * Mth.sin(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + Mth.DEG_TO_RAD * 90), 0.5F * Mth.SQRT_OF_TWO * Mth.cos(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + Mth.DEG_TO_RAD * 90), 1, 1);
		vertex(vertexConsumer, pose, LightTexture.FULL_BRIGHT, 0.5F * Mth.SQRT_OF_TWO * Mth.sin(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + Mth.DEG_TO_RAD * 180), 0.5F * Mth.SQRT_OF_TWO * Mth.cos(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + Mth.DEG_TO_RAD * 180), 1, 0);
		vertex(vertexConsumer, pose, LightTexture.FULL_BRIGHT, 0.5F * Mth.SQRT_OF_TWO * Mth.sin(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + Mth.DEG_TO_RAD * 270), 0.5F * Mth.SQRT_OF_TWO * Mth.cos(Mth.lerp(partialTicks, entity.oSpin, entity.spin) + Mth.DEG_TO_RAD * 270), 0, 0);
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, float y, float u, float v) {
		consumer.addVertex(pose, x, y, 0.0F)
			.setColor(-1)
			.setUv(u, v)
			.setOverlay(OverlayTexture.NO_OVERLAY)
			.setLight(packedLight)
			.setNormal(pose, 0.0F, 1.0F, 0.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(EnergySpark entity) {
		return TEXTURE_LOCATION;
	}
}
