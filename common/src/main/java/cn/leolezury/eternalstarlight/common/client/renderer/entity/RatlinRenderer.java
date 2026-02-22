package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.RatlinModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Ratlin;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ZombifiedRatlin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RatlinRenderer<T extends Ratlin> extends MobRenderer<T, RatlinModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/ratlin.png");
	private static final ResourceLocation ZOMBIFIED_TEXTURE = EternalStarlight.id("textures/entity/zombified_ratlin.png");

	public RatlinRenderer(EntityRendererProvider.Context context) {
		super(context, new RatlinModel<>(context.bakeLayer(RatlinModel.LAYER_LOCATION)), 0.3f);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return entity instanceof ZombifiedRatlin ? ZOMBIFIED_TEXTURE : ENTITY_TEXTURE;
	}
}
