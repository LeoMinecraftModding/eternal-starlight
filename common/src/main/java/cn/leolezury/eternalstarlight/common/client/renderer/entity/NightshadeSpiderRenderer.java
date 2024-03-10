package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.NightshadeSpiderModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.NightshadeSpiderEyesLayer;
import cn.leolezury.eternalstarlight.common.entity.living.monster.NightshadeSpider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class NightshadeSpiderRenderer<T extends NightshadeSpider> extends MobRenderer<T, NightshadeSpiderModel<T>> {
    private static final ResourceLocation NIGHTSHADE_SPIDER_LOCATION = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/nightshade_spider.png");

    public NightshadeSpiderRenderer(EntityRendererProvider.Context context) {
        this(context, NightshadeSpiderModel.LAYER_LOCATION);
        this.shadowRadius *= 0.7F;
    }

    public NightshadeSpiderRenderer(EntityRendererProvider.Context context, ModelLayerLocation location) {
        super(context, new NightshadeSpiderModel<>(context.bakeLayer(location)), 0.8F);
        this.addLayer(new NightshadeSpiderEyesLayer<>(this));
    }

    public ResourceLocation getTextureLocation(T entity) {
        return NIGHTSHADE_SPIDER_LOCATION;
    }
}
