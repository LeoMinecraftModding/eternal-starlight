package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.OrbModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.OrbitalPlanet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class OrbitalPlanetRenderer extends EntityRenderer<OrbitalPlanet> {
	private static final ResourceLocation[] PLANET_TEXTURES = new ResourceLocation[]{
		EternalStarlight.id("textures/entity/solar_creeper/planet_0.png"),
		EternalStarlight.id("textures/entity/solar_creeper/planet_1.png"),
		EternalStarlight.id("textures/entity/solar_creeper/planet_2.png"),
		EternalStarlight.id("textures/entity/solar_creeper/planet_3.png"),
		EternalStarlight.id("textures/entity/solar_creeper/planet_4.png"),
		EternalStarlight.id("textures/entity/solar_creeper/planet_5.png"),
		EternalStarlight.id("textures/entity/solar_creeper/planet_6.png"),
		EternalStarlight.id("textures/entity/solar_creeper/planet_7.png")
	};

	private final OrbModel<OrbitalPlanet> model;

	public OrbitalPlanetRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new OrbModel<>(context.bakeLayer(OrbModel.LAYER_LOCATION));
	}

	@Override
	public void render(OrbitalPlanet entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0F, -1.5F - entity.getBbHeight() / 2, 0.0F);
		float bob = entity.tickCount + partialTicks;
		this.model.prepareMobModel(entity, 0, 0, partialTicks);
		this.model.setupAnim(entity, 0, 0, bob, 0, 0);
		this.model.renderToBuffer(poseStack, buffer.getBuffer(this.model.renderType(getTextureLocation(entity))), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(OrbitalPlanet entity) {
		return PLANET_TEXTURES[Math.floorMod(entity.getId(), PLANET_TEXTURES.length)];
	}
}
