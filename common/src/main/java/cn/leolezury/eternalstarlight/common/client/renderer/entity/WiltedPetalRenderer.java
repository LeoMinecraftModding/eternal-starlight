package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.WiltedPetal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class WiltedPetalRenderer extends EntityRenderer<WiltedPetal> {
	private static final ResourceLocation TEXTURE_LOCATION = EternalStarlight.id("textures/entity/wilted_petal.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

	public WiltedPetalRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(WiltedPetal entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		poseStack.scale(0.6F, 0.6F, 0.6F);
		poseStack.mulPose(new Quaternionf(this.entityRenderDispatcher.cameraOrientation()).rotateZ(Mth.lerp(partialTicks, entity.oSpin, entity.spin)));
		PoseStack.Pose pose = poseStack.last();
		VertexConsumer vertexConsumer = buffer.getBuffer(RENDER_TYPE);
		vertex(vertexConsumer, pose, packedLight, 0.0F, 0, 0, 1);
		vertex(vertexConsumer, pose, packedLight, 1.0F, 0, 1, 1);
		vertex(vertexConsumer, pose, packedLight, 1.0F, 1, 1, 0);
		vertex(vertexConsumer, pose, packedLight, 0.0F, 1, 0, 0);
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, int y, int u, int v) {
		consumer.addVertex(pose, x - 0.5F, y - 0.25F, 0.0F)
			.setColor(-1)
			.setUv(u, v)
			.setOverlay(OverlayTexture.NO_OVERLAY)
			.setLight(packedLight)
			.setNormal(pose, 0.0F, 1.0F, 0.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(WiltedPetal entity) {
		return TEXTURE_LOCATION;
	}
}
