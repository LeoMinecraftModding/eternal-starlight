package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.RatlinModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Ratlin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RatlinRenderer<T extends Ratlin> extends MobRenderer<T, RatlinModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/ratlin.png");

    public RatlinRenderer(EntityRendererProvider.Context context) {
        super(context, new RatlinModel<>(context.bakeLayer(RatlinModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ENTITY_TEXTURE;
    }
}
