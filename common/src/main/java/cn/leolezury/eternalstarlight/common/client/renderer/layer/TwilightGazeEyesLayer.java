package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.TwilightGazeModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.TwilightGaze;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;

@Environment(EnvType.CLIENT)
public class TwilightGazeEyesLayer<T extends TwilightGaze, M extends TwilightGazeModel<T>> extends EyesLayer<T, M> {
    private static final RenderType EYES = RenderType.eyes(EternalStarlight.id("textures/entity/twilight_gaze_eyes.png"));

    public TwilightGazeEyesLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    public RenderType renderType() {
        return EYES;
    }
}
