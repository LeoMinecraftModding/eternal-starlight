package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.ShadowSnailModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.ShadowSnail;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShadowSnailRenderer<T extends ShadowSnail> extends MobRenderer<T, ShadowSnailModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/shadow_snail.png");

	public ShadowSnailRenderer(EntityRendererProvider.Context context) {
		super(context, new ShadowSnailModel<>(context.bakeLayer(ShadowSnailModel.LAYER_LOCATION)), 0.1f);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
