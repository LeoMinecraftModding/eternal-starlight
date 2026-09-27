package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.client.model.entity.EctostoneModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.EctostoneGlowLayer;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone.Ectostone;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EctostoneRenderer<T extends Ectostone> extends MobRenderer<T, EctostoneModel<T>> {
	public EctostoneRenderer(EntityRendererProvider.Context context) {
		super(context, new EctostoneModel<>(context.bakeLayer(EctostoneModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new EctostoneGlowLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return entity.getVariant().value().textureFull();
	}
}
