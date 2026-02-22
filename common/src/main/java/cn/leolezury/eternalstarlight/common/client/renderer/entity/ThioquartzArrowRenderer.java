package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThioquartzArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ThioquartzArrowRenderer extends ArrowRenderer<ThioquartzArrow> {
	public static final ResourceLocation ARROW_LOCATION = EternalStarlight.id("textures/entity/thioquartz_arrow.png");

	public ThioquartzArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(ThioquartzArrow arrow) {
		return ARROW_LOCATION;
	}
}
