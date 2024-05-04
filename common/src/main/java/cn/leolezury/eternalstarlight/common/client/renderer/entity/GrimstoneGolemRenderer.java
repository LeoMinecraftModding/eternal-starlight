package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.GrimstoneGolemModel;
import cn.leolezury.eternalstarlight.common.entity.living.GrimstoneGolem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class GrimstoneGolemRenderer<T extends GrimstoneGolem> extends MobRenderer<T, GrimstoneGolemModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/grimstone_golem.png");

    public GrimstoneGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new GrimstoneGolemModel<>(context.bakeLayer(GrimstoneGolemModel.LAYER_LOCATION)), 0.3f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ENTITY_TEXTURE;
    }
}
