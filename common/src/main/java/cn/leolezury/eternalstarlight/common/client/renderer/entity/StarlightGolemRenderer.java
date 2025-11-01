package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.StarlightGolemModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.StarlightGolemEyesLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.StarlightGolemGlowLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.StarlightGolemHalloweenLayer;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolemChargePhase;
import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class StarlightGolemRenderer<T extends StarlightGolem> extends MobRenderer<T, StarlightGolemModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/starlight_golem/starlight_golem.png");
	private static final ResourceLocation CRACKED_TEXTURE = EternalStarlight.id("textures/entity/starlight_golem/starlight_golem_cracked.png");

	public StarlightGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new StarlightGolemModel<>(context.bakeLayer(StarlightGolemModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new StarlightGolemHalloweenLayer<>(this, context.getModelSet()));
		this.addLayer(new StarlightGolemGlowLayer<>(this, context.getModelSet()));
		this.addLayer(new StarlightGolemEyesLayer<>(this));
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		float deathProgress = Mth.clamp(Mth.lerp(partialTicks, entity.oldDeathAnimationTime, entity.deathAnimationTime) / 110, 0, 1);
		if (deathProgress > 0) {
			poseStack.pushPose();
			poseStack.translate(0, entity.getBbHeight() / 4, 0);
			poseStack.scale(deathProgress + Mth.cos((entity.tickCount + partialTicks) * 4f) * 0.2f * deathProgress, deathProgress + Mth.cos((entity.tickCount + partialTicks) * 4f) * 0.2f * deathProgress, deathProgress + Mth.cos((entity.tickCount + partialTicks) * 4f) * 0.2f * deathProgress);
			poseStack.mulPose(new Quaternionf(this.entityRenderDispatcher.cameraOrientation()).rotateY(Mth.PI));
			PoseStack.Pose pose = poseStack.last();
			VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.dragonRays());
			for (int i = 0; i < 5; i++) {
				vertexConsumer.addVertex(pose, 0, 0, 0).setColor(FastColor.ARGB32.colorFromFloat(Easing.IN_OUT_SINE.interpolate(deathProgress, 0.5F, 1.0F), 1.0F, 1.0F, 1.0F));
				float angle = i * Mth.TWO_PI / 5 + deathProgress * Mth.PI * 1.5f;
				vertexConsumer.addVertex(pose, Mth.sin(angle) * entity.getBbHeight() * 3, Mth.cos(angle) * entity.getBbHeight() * 3, 0).setColor(FastColor.ARGB32.color(0, 64, 106, 125));
				float largerAngle = angle + deathProgress * Mth.TWO_PI / 8;
				vertexConsumer.addVertex(pose, Mth.sin(largerAngle) * entity.getBbHeight() * 3, Mth.cos(largerAngle) * entity.getBbHeight() * 3, 0).setColor(FastColor.ARGB32.color(0, 64, 106, 125));
			}
			poseStack.popPose();
		}
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Override
	protected float getFlipDegrees(T entity) {
		return 0;
	}

	@Override
	protected float getWhiteOverlayProgress(T entity, float partialTicks) {
		float deathProgress = Mth.lerp(partialTicks, entity.oldDeathAnimationTime, entity.deathAnimationTime) / 100;
		if (deathProgress <= 0) return 0;
		return (int) (deathProgress * 45.0) % 2 == 0 ? 0.0F : Mth.clamp(deathProgress, 0.2F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return entity.getPhase() == 0 ? ENTITY_TEXTURE : CRACKED_TEXTURE;
	}

	@Override
	public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
		boolean oCull = entity.noCulling;
		if (entity.getBehaviorState() == StarlightGolemChargePhase.ID) {
			entity.noCulling = true;
		}
		boolean shouldRender = super.shouldRender(entity, frustum, x, y, z);
		entity.noCulling = oCull;
		return shouldRender;
	}
}
