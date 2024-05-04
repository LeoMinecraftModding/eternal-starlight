package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.EntModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Ent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class EntRenderer<T extends Ent> extends MobRenderer<T, EntModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/ent/ent.png");
    private static final ResourceLocation LUSH_ENTITY_TEXTURE = EternalStarlight.id("textures/entity/ent/lush_ent.png");

    public EntRenderer(EntityRendererProvider.Context context) {
        super(context, new EntModel<>(context.bakeLayer(EntModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return entity.hasLeaves() ? LUSH_ENTITY_TEXTURE : ENTITY_TEXTURE;
    }
}
