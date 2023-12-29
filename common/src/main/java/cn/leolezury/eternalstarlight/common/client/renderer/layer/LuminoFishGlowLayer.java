package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.LuminoFishModel;
import cn.leolezury.eternalstarlight.common.entity.animal.LuminoFish;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class LuminoFishGlowLayer<T extends LuminoFish, M extends LuminoFishModel<T>> extends EyesLayer<T, M> {
    private static final RenderType GLOW = RenderType.eyes(new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/luminofish_glow.png"));

    public LuminoFishGlowLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return GLOW;
    }
}
