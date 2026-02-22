package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.StranghoulModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Stranghoul;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class StranghoulRenderer<T extends Stranghoul> extends HumanoidMobRenderer<T, StranghoulModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/stranghoul.png");

	public StranghoulRenderer(EntityRendererProvider.Context context) {
		super(context, new StranghoulModel<>(context.bakeLayer(StranghoulModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidArmorModel<>(context.bakeLayer(StranghoulModel.INNER_ARMOR_LOCATION)), new HumanoidArmorModel<>(context.bakeLayer(StranghoulModel.OUTER_ARMOR_LOCATION)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
