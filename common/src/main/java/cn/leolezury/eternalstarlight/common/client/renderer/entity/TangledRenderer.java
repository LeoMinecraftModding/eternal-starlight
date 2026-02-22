package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.TangledModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.TangledGlowLayer;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Tangled;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TangledRenderer<T extends Tangled> extends MobRenderer<T, TangledModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/tangled.png");
	private static final ResourceLocation LUSH_TEXTURE = EternalStarlight.id("textures/entity/tangled_lush.png");
	private static final ResourceLocation BLOOMING_TEXTURE = EternalStarlight.id("textures/entity/tangled_blooming.png");

	public TangledRenderer(EntityRendererProvider.Context context) {
		super(context, new TangledModel<>(context.bakeLayer(TangledModel.LAYER_LOCATION)), 0.3f);
		this.addLayer(new TangledGlowLayer<>(this, context.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return switch (entity.getVariant()) {
			case 1 -> LUSH_TEXTURE;
			case 2 -> BLOOMING_TEXTURE;
			default -> ENTITY_TEXTURE;
		};
	}
}
