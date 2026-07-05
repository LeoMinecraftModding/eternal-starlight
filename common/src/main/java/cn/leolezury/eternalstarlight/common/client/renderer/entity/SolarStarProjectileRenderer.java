package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.SolarStarProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

public class SolarStarProjectileRenderer extends EntityRenderer<SolarStarProjectile> {
	private static final ResourceLocation TEXTURE_LOCATION = EternalStarlight.id("textures/entity/solar_creeper/star.png");
	private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(TEXTURE_LOCATION);

	public SolarStarProjectileRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(SolarStarProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		poseStack.translate(0.0F, entity.getBbHeight() / 2, 0.0F);
		poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());

		float age = entity.tickCount + partialTicks;

		float factor = Mth.sin(age * 0.1f + entity.getId()) * 0.5f + 0.5f;
		int r = Mth.lerpInt(factor, 135, 255);
		int g = Mth.lerpInt(factor, 206, 215);
		int b = Mth.lerpInt(factor, 250, 0);
		int color = FastColor.ARGB32.color(255, r, g, b);

		PoseStack.Pose pose;

		VertexConsumer consumer = buffer.getBuffer(RENDER_TYPE);

		poseStack.translate(0.0F, 0.0F, 0.001F);
		pose = poseStack.last();
		renderDiamond(consumer, pose, 1.8F, 0.1F, color);
		poseStack.translate(0.0F, 0.0F, 0.001F);
		pose = poseStack.last();
		renderDiamond(consumer, pose, 0.1F, 0.9F, color);

		float pulseZRot = age * (entity.getId() % 2 == 0 ? 1.5f : -1.5f) + entity.getId() * 2;
		poseStack.mulPose(Axis.ZP.rotationDegrees(pulseZRot));
		float pulseScale = 1.2F + 0.3F * Mth.sin(age * 2.1F);
		poseStack.scale(pulseScale, pulseScale, 1.0F);

		poseStack.translate(0.0F, 0.0F, 0.001F);
		pose = poseStack.last();
		renderDiamond(consumer, pose, 0.9F, 0.05F, color);
		poseStack.translate(0.0F, 0.0F, 0.001F);
		pose = poseStack.last();
		renderDiamond(consumer, pose, 0.05F, 0.45F, color);

		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	private static void renderDiamond(VertexConsumer consumer, PoseStack.Pose pose, float xSize, float ySize, int color) {
		consumer.addVertex(pose, xSize, 0.0F, 0.0F).setColor(color).setUv(0.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
		consumer.addVertex(pose, 0.0F, ySize, 0.0F).setColor(color).setUv(1.0F, 0.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
		consumer.addVertex(pose, -xSize, 0.0F, 0.0F).setColor(color).setUv(1.0F, 1.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
		consumer.addVertex(pose, 0.0F, -ySize, 0.0F).setColor(color).setUv(0.0F, 1.0F).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(SolarStarProjectile entity) {
		return TEXTURE_LOCATION;
	}
}
