package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class AethersentArrowRenderer extends ArrowRenderer<AethersentArrow> {
	public static final ResourceLocation ARROW_LOCATION = EternalStarlight.id("textures/entity/aethersent_arrow.png");

	public AethersentArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(AethersentArrow arrow) {
		return ARROW_LOCATION;
	}
}
