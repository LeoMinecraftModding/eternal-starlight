package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.TwilightGazeModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.TwilightGazeEyesLayer;
import cn.leolezury.eternalstarlight.common.entity.living.animal.TwilightGaze;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class TwilightGazeRenderer<T extends TwilightGaze> extends MobRenderer<T, TwilightGazeModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/twilight_gaze.png");

    public TwilightGazeRenderer(EntityRendererProvider.Context context) {
        super(context, new TwilightGazeModel<>(context.bakeLayer(TwilightGazeModel.LAYER_LOCATION)), 0.3f);
        this.addLayer(new TwilightGazeEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ENTITY_TEXTURE;
    }
}
