package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.TangledHatredModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.TangledHatred;
import cn.leolezury.eternalstarlight.common.util.Chain;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class TangledHatredRenderer extends EntityRenderer<TangledHatred> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/tangled_hatred.png");

	private final TangledHatredModel<TangledHatred> model;

	public TangledHatredRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new TangledHatredModel<>(context.bakeLayer(TangledHatredModel.LAYER_LOCATION));
	}

	@Override
	public boolean shouldRender(TangledHatred entity, Frustum frustum, double d, double e, double f) {
		return true;
	}

	@Override
	public void render(TangledHatred entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(ENTITY_TEXTURE));
		float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
		float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
		float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
		poseStack.pushPose();
		poseStack.translate(-x, -y, -z);
		int numSegments = Math.min(entity.chain.segments().size(), entity.oldChain.segments().size());
		if (numSegments > 0) {
			Chain.Segment last = entity.chain.segments().getLast();
			Chain.Segment oldLast = entity.oldChain.segments().getLast();
			Vec3 pos = ESMathUtil.lerpVec(partialTicks, oldLast.getLowerPosition(), last.getLowerPosition());
			poseStack.translate(x - pos.x, y - pos.y, z - pos.z);
		}
		for (int i = 0; i < numSegments; i++) {
			poseStack.pushPose();
			Chain.Segment segment = entity.chain.segments().get(i);
			Chain.Segment old = entity.oldChain.segments().get(i);
			Vec3 pos = ESMathUtil.lerpVec(partialTicks, old.getLowerPosition(), segment.getLowerPosition());
			float segmentPitch = Mth.lerp(partialTicks, old.getPitch(), segment.getPitch());
			float segmentYaw = Mth.lerp(partialTicks, old.getYaw(), segment.getYaw());
			float scale = ((float) i / numSegments) * 0.5f + 0.5f;
			poseStack.translate(pos.x, pos.y, pos.z);
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			poseStack.translate(0.0F, -1.5F, 0.0F);
			this.model.setRotation(segmentPitch, segmentYaw);
			this.model.scaleXZ(scale);
			this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.pack(OverlayTexture.u(0), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0)));
			poseStack.popPose();
		}
		poseStack.popPose();
		super.render(entity, yaw, partialTicks, poseStack, bufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(TangledHatred entity) {
		return ENTITY_TEXTURE;
	}
}