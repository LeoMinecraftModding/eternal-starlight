package cn.leolezury.eternalstarlight.client.renderer.layer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NightshadeSpiderEyesLayer<T extends Entity, M extends SpiderModel<T>> extends EyesLayer<T, M> {
    private static final RenderType SPIDER_EYES = RenderType.eyes(new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/nightshade_spider_eyes.png"));

    public NightshadeSpiderEyesLayer(RenderLayerParent<T, M> p_117507_) {
        super(p_117507_);
    }

    public RenderType renderType() {
        return SPIDER_EYES;
    }
}
