package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.AmaramberArrow;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class AmaramberArrowRenderer extends ArrowRenderer<AmaramberArrow> {
	public static final ResourceLocation ARROW_LOCATION = EternalStarlight.id("textures/entity/amaramber_arrow.png");

	public AmaramberArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	public ResourceLocation getTextureLocation(AmaramberArrow arrow) {
		return ARROW_LOCATION;
	}
}
