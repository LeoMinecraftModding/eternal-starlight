package cn.leolezury.eternalstarlight.client.renderer.layer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.model.TwilightSquidModel;
import cn.leolezury.eternalstarlight.entity.animal.TwilightSquid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class TwilightSquidEyesLayer<T extends TwilightSquid, M extends TwilightSquidModel<T>> extends EyesLayer<T, M> {
    private static final RenderType SQUID_EYES = RenderType.eyes(new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/twilight_squid_eyes.png"));

    public TwilightSquidEyesLayer(RenderLayerParent<T, M> p_117507_) {
        super(p_117507_);
    }

    public RenderType renderType() {
        return SQUID_EYES;
    }
}
