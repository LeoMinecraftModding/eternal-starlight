package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.AuroraDeerModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.AuroraDeer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class AuroraDeerRenderer<T extends AuroraDeer> extends MobRenderer<T, AuroraDeerModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/aurora_deer.png");

	public AuroraDeerRenderer(EntityRendererProvider.Context context) {
		super(context, new AuroraDeerModel<>(context.bakeLayer(AuroraDeerModel.LAYER_LOCATION)), 0.8f);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
