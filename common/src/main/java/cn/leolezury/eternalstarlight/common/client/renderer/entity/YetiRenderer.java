package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.YetiModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Yeti;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class YetiRenderer<T extends Yeti> extends MobRenderer<T, YetiModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/yeti.png");

    public YetiRenderer(EntityRendererProvider.Context context) {
        super(context, new YetiModel<>(context.bakeLayer(YetiModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ENTITY_TEXTURE;
    }
}
