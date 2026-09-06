package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.misc.EyeOfSeeking;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

public class EyeOfSeekingRenderer extends EntityRenderer<EyeOfSeeking> {
	private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
	private static final ResourceLocation LINE_TEXTURE = EternalStarlight.id("textures/entity/seeking_eye_line.png");
	private final ItemRenderer itemRenderer;

	public EyeOfSeekingRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(EyeOfSeeking entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		Vec3 posForOwner = entity.getPosition(partialTicks);
		Vec3 pos = entity.getPosition(partialTicks);
		Vec3 ownerPos = null;
		Vec3 targetPosition = entity.getTargetPosition();
		Player owner = entity.getOwner();
		if (owner != null && targetPosition != null) {
			ownerPos = owner.getPosition(partialTicks).add(0, owner.getBbHeight() / 2, 0);
			posForOwner = entity.getPositionForOwner(ownerPos, targetPosition);
		}
		poseStack.pushPose();
		poseStack.translate(posForOwner.x - pos.x, posForOwner.y - pos.y, posForOwner.z - pos.z);
		if (owner != null && ownerPos != null) {
			poseStack.pushPose();
			poseStack.translate(0, entity.getBbHeight() / 2, 0);
			renderLineToOwner(posForOwner.add(0, entity.getBbHeight() / 2, 0), ownerPos, poseStack, buffer);
			poseStack.popPose();
		}
		if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < MIN_CAMERA_DISTANCE_SQUARED)) {
			poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
			this.itemRenderer.renderStatic(entity.getItem(), ItemDisplayContext.GROUND, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
		}
		poseStack.popPose();
		super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
	}

	private void renderLineToOwner(Vec3 eyePos, Vec3 ownerPos, PoseStack poseStack, MultiBufferSource buffer) {
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		Vec3 sight = camera.getPosition().subtract(eyePos);
		Vec3 end = ownerPos.subtract(eyePos).scale(0.6);
		Vec3 sideOffset = end.cross(sight).normalize().scale(1.0 / 36.0);
		PoseStack.Pose pose = poseStack.last();
		VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(LINE_TEXTURE));
		vertexConsumer.addVertex(pose, sideOffset.toVector3f()).setColor(-1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		vertexConsumer.addVertex(pose, sideOffset.scale(-1).toVector3f()).setColor(-1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		vertexConsumer.addVertex(pose, end.add(sideOffset.scale(-1)).toVector3f()).setColor(-1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		vertexConsumer.addVertex(pose, end.add(sideOffset).toVector3f()).setColor(-1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(EyeOfSeeking entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
