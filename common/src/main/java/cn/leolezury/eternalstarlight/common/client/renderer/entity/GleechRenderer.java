package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.GleechModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Gleech;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class GleechRenderer<T extends Gleech> extends MobRenderer<T, GleechModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/gleech.png");

    public GleechRenderer(EntityRendererProvider.Context context) {
        super(context, new GleechModel<>(context.bakeLayer(GleechModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ENTITY_TEXTURE;
    }
}
