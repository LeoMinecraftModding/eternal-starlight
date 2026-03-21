package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.AethersentGolemModel;
import cn.leolezury.eternalstarlight.common.entity.living.AethersentGolem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AethersentGolemRenderer<T extends AethersentGolem> extends MobRenderer<T, AethersentGolemModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/aethersent_golem.png");

	public AethersentGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new AethersentGolemModel<>(context.bakeLayer(AethersentGolemModel.LAYER_LOCATION)), 0.3f);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
