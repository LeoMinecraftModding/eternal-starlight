package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.client.model.entity.EntModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Ent;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EntRenderer<T extends Ent> extends MobRenderer<T, EntModel<T>> {
	public EntRenderer(EntityRendererProvider.Context context) {
		super(context, new EntModel<>(context.bakeLayer(EntModel.LAYER_LOCATION)), 0.15f);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return entity.getVariant().value().textureFull();
	}
}
