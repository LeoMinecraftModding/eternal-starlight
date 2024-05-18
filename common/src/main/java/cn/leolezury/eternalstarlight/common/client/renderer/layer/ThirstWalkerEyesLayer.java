package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.ThirstWalkerModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ThirstWalker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;

@Environment(EnvType.CLIENT)
public class ThirstWalkerEyesLayer<T extends ThirstWalker, M extends ThirstWalkerModel<T>> extends EyesLayer<T, M> {
    private static final RenderType EYES = RenderType.entityTranslucentEmissive(EternalStarlight.id("textures/entity/thirst_walker_eyes.png"));

    public ThirstWalkerEyesLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    public RenderType renderType() {
        return EYES;
    }
}
