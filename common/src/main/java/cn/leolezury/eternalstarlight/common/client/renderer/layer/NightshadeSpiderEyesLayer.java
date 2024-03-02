package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.NightshadeSpiderModel;
import cn.leolezury.eternalstarlight.common.entity.monster.NightshadeSpider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class NightshadeSpiderEyesLayer<T extends NightshadeSpider, M extends NightshadeSpiderModel<T>> extends EyesLayer<T, M> {
    private static final RenderType SPIDER_EYES = RenderType.eyes(new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/nightshade_spider_eyes.png"));

    public NightshadeSpiderEyesLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    public RenderType renderType() {
        return SPIDER_EYES;
    }
}
