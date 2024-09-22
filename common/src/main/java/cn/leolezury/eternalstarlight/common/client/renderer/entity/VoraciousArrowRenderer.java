package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.VoraciousArrow;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class VoraciousArrowRenderer extends ArrowRenderer<VoraciousArrow> {
	public static final ResourceLocation ARROW_LOCATION = EternalStarlight.id("textures/entity/voracious_arrow.png");

	public VoraciousArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(VoraciousArrow arrow) {
		return ARROW_LOCATION;
	}
}
