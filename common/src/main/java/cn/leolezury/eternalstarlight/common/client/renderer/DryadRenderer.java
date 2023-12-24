package cn.leolezury.eternalstarlight.common.client.renderer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.DryadModel;
import cn.leolezury.eternalstarlight.common.entity.animal.Dryad;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class DryadRenderer<T extends Dryad> extends MobRenderer<T, DryadModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/dryad.png");
    private static final ResourceLocation LUSH_ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/lush_dryad.png");

    public DryadRenderer(EntityRendererProvider.Context context) {
        super(context, new DryadModel<>(context.bakeLayer(DryadModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return entity.hasLeaves() ? LUSH_ENTITY_TEXTURE : ENTITY_TEXTURE;
    }
}
