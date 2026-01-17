package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.model.entity.BallLightningModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.BallLightning;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class BallLightningRenderer extends EntityRenderer<BallLightning> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/ball_lightning.png");
	private static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/trail.png");

	private final BallLightningModel<BallLightning> model;

	public BallLightningRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new BallLightningModel<>(context.bakeLayer(BallLightningModel.LAYER_LOCATION));
	}

	@Override
	public void render(BallLightning entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
		poseStack.pushPose();
		float yRot = -Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
		float xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) - 90f;
		float bob = entity.tickCount + partialTicks;

		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0F, -1.5F, 0.0F);

		this.model.prepareMobModel(entity, 0, 0, partialTicks);
		this.model.setupAnim(entity, 0, 0, bob, yRot, xRot);
		RenderType renderType = RenderType.eyes(getTextureLocation(entity));
		VertexConsumer vertexConsumer = ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(renderType);
		this.model.inner.visible = true;
		this.model.outer.visible = false;
		this.model.renderToBuffer(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		this.model.inner.visible = false;
		this.model.outer.visible = true;
		this.model.renderToBuffer(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.color(127, 30, 50, 50));

		poseStack.popPose();

		Entity target = entity.level().getEntity(entity.getTargetId());
		if (target != null) {
			Vec3 pos = new Vec3(
				Mth.lerp(partialTicks, entity.xo, entity.getX()),
				Mth.lerp(partialTicks, entity.yo, entity.getY()),
				Mth.lerp(partialTicks, entity.zo, entity.getZ())
			);
			Vec3 startPos = new Vec3(
				Mth.lerp(partialTicks, entity.xo, entity.getX()),
				Mth.lerp(partialTicks, entity.yo, entity.getY()) + entity.getBbHeight() / 2,
				Mth.lerp(partialTicks, entity.zo, entity.getZ())
			);
			Vec3 destPos = new Vec3(
				Mth.lerp(partialTicks, target.xo, target.getX()),
				Mth.lerp(partialTicks, target.yo, target.getY()) + target.getBbHeight() / 2,
				Mth.lerp(partialTicks, target.zo, target.getZ())
			);
			List<Vec3> segments = new ArrayList<>();
			int numSegments = Math.round(((float) startPos.distanceTo(destPos) / 1.25f) * (entity.getRandom().nextFloat() * 0.5f + 1));
			float segmentLength = (float) (destPos.subtract(startPos).length() / numSegments);
			Vec3 increment = destPos.subtract(startPos).scale((double) 1 / numSegments);
			segments.add(startPos);
			for (int i = 0; i < numSegments; i++) {
				if (i == numSegments - 1) {
					segments.add(startPos.add(increment.scale((i + 1))));
				} else {
					segments.add(startPos.add(increment.scale((i + 1))).add(new Vec3(entity.getRandom().nextDouble() - 0.5, entity.getRandom().nextDouble() - 0.5, entity.getRandom().nextDouble() - 0.5).normalize().scale(segmentLength / 6)));
				}
			}
			// add a full connection
			segments.add(startPos);
			Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
			vertexConsumer = ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.entityTranslucentNoDepth(TRAIL_TEXTURE));
			for (int i = 0; i < segments.size() - 1; i++) {
				Vec3 start = segments.get(i);
				Vec3 end = segments.get(i + 1);
				Vec3 offset = end.subtract(start);
				Vec3 sight = camPos.subtract(start).scale(-1);
				Vec3 sideOffset = offset.cross(sight).normalize().scale(i == segments.size() - 2 ? 0.035 : 0.03);
				PoseStack.Pose pose = poseStack.last();
				vertexConsumer.addVertex(pose, start.subtract(pos).add(sideOffset).toVector3f()).setColor(0.6F, 1, 1, 1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				vertexConsumer.addVertex(pose, start.subtract(pos).add(sideOffset.scale(-1)).toVector3f()).setColor(0.6F, 1, 1, 1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				vertexConsumer.addVertex(pose, end.subtract(pos).add(sideOffset.scale(-1)).toVector3f()).setColor(0.6F, 1, 1, 1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
				vertexConsumer.addVertex(pose, end.subtract(pos).add(sideOffset).toVector3f()).setColor(0.6F, 1, 1, 1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
			}
		}

		super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
	}

	@Override
	public ResourceLocation getTextureLocation(BallLightning entity) {
		return ENTITY_TEXTURE;
	}
}
