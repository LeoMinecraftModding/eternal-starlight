package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.NightfallSpiderModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.NightfallSpider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;

@Environment(EnvType.CLIENT)
public class NightfallSpiderEyesLayer<T extends NightfallSpider, M extends NightfallSpiderModel<T>> extends EyesLayer<T, M> {
    private static final RenderType SPIDER_EYES = RenderType.eyes(EternalStarlight.id("textures/entity/nightfall_spider_eyes.png"));

    public NightfallSpiderEyesLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    public RenderType renderType() {
        return SPIDER_EYES;
    }
}
