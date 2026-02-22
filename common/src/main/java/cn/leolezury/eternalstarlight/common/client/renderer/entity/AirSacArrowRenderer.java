package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.AirSacArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class AirSacArrowRenderer extends ArrowRenderer<AirSacArrow> {
	public static final ResourceLocation ARROW_LOCATION = EternalStarlight.id("textures/entity/air_sac_arrow.png");

	public AirSacArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(AirSacArrow arrow) {
		return ARROW_LOCATION;
	}
}
