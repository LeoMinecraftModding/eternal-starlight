package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.ShimmerLacewingModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.ShimmerLacewingGlowLayer;
import cn.leolezury.eternalstarlight.common.entity.living.animal.ShimmerLacewing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ShimmerLacewingRenderer<T extends ShimmerLacewing> extends MobRenderer<T, ShimmerLacewingModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/shimmer_lacewing.png");
	private static final ResourceLocation SWAMP_TEXTURE = EternalStarlight.id("textures/entity/shimmer_lacewing_swamp.png");

	public ShimmerLacewingRenderer(EntityRendererProvider.Context context) {
		super(context, new ShimmerLacewingModel<>(context.bakeLayer(ShimmerLacewingModel.LAYER_LOCATION)), 0.3f);
		this.addLayer(new ShimmerLacewingGlowLayer<>(this, context.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return entity.getVariant() == ShimmerLacewing.VARIANT_SWAMP ? SWAMP_TEXTURE : ENTITY_TEXTURE;
	}
}
