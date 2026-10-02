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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

public class EyeOfSeekingRenderer extends EntityRenderer<EyeOfSeeking> {
	private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
	private static final ResourceLocation LINE_TEXTURE = EternalStarlight.id("textures/entity/blank.png");
	private static final int[] LINE_BLOCK_COLORS = {0xBAE3EA, 0xA368AD, 0x5E3F7A, 0x220B3D};
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
		// a trapezoid from the vertices instead of the old tapering texture, still split into four color blocks
		int segments = LINE_BLOCK_COLORS.length;
		for (int i = 0; i < segments; i++) {
			float fromProgress = (float) i / segments;
			float toProgress = (float) (i + 1) / segments;
			Vec3 from = end.scale(fromProgress);
			Vec3 to = end.scale(toProgress);
			Vec3 fromOffset = sideOffset.scale(lineWidth(fromProgress));
			Vec3 toOffset = sideOffset.scale(lineWidth(toProgress));
			int color = LINE_BLOCK_COLORS[i];
			float red = ((color >> 16) & 0xFF) / 255f;
			float green = ((color >> 8) & 0xFF) / 255f;
			float blue = (color & 0xFF) / 255f;
			addLineVertex(pose, vertexConsumer, from.add(fromOffset), red, green, blue, 0, 0);
			addLineVertex(pose, vertexConsumer, from.subtract(fromOffset), red, green, blue, 0, 1);
			addLineVertex(pose, vertexConsumer, to.subtract(toOffset), red, green, blue, 1, 1);
			addLineVertex(pose, vertexConsumer, to.add(toOffset), red, green, blue, 1, 0);
		}
	}

	private static float lineWidth(float progress) {
		return Mth.lerp(Mth.clamp(progress, 0f, 1f), 1f, 1f / 16f);
	}

	private static void addLineVertex(PoseStack.Pose pose, VertexConsumer vertexConsumer, Vec3 pos, float red, float green, float blue, float u, float v) {
		vertexConsumer.addVertex(pose, pos.toVector3f()).setColor(red, green, blue, 1).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(EyeOfSeeking entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
