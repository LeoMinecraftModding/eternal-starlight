package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.OrbModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.BouncyStar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BouncyStarRenderer extends EntityRenderer<BouncyStar> {
	private static final ResourceLocation SUN_TEXTURE = EternalStarlight.id("textures/entity/solar_creeper/sun.png");

	private final OrbModel<BouncyStar> model;

	public BouncyStarRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new OrbModel<>(context.bakeLayer(OrbModel.LAYER_LOCATION));
	}

	@Override
	public void render(BouncyStar entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		float bob = entity.tickCount + partialTicks;

		poseStack.translate(0.0F, entity.getBbHeight() / 2, 0.0F);
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0F, -1.5F, 0.0F);

		this.model.prepareMobModel(entity, 0, 0, partialTicks);
		this.model.setupAnim(entity, 0, 0, bob, 0, 0);
		this.model.renderToBuffer(poseStack, buffer.getBuffer(model.renderType(getTextureLocation(entity))), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();

		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(BouncyStar entity) {
		return SUN_TEXTURE;
	}
}
