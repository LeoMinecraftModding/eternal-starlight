package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.PermafrostModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.Permafrost;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class PermafrostRenderer<T extends Permafrost> extends MobRenderer<T, PermafrostModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/permafrost.png");

	public PermafrostRenderer(EntityRendererProvider.Context context) {
		super(context, new PermafrostModel<>(context.bakeLayer(PermafrostModel.LAYER_LOCATION)), 0.3f);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
