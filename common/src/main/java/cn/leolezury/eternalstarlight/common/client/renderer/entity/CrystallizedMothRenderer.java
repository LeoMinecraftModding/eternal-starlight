package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.CrystallizedMothModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.CrystallizedMothGlowLayer;
import cn.leolezury.eternalstarlight.common.entity.living.animal.CrystallizedMoth;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class CrystallizedMothRenderer<T extends CrystallizedMoth> extends MobRenderer<T, CrystallizedMothModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/crystallized_moth.png");

	public CrystallizedMothRenderer(EntityRendererProvider.Context context) {
		super(context, new CrystallizedMothModel<>(context.bakeLayer(CrystallizedMothModel.LAYER_LOCATION)), 0.3f);
		this.addLayer(new CrystallizedMothGlowLayer<>(this, context.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
