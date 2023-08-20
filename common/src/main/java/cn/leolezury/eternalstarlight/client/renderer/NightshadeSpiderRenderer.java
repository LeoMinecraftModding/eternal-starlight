package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.renderer.layer.NightshadeSpiderEyesLayer;
import cn.leolezury.eternalstarlight.entity.monster.NightshadeSpider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


public class NightshadeSpiderRenderer<T extends NightshadeSpider> extends MobRenderer<T, SpiderModel<T>> {
    private static final ResourceLocation NIGHTSHADE_SPIDER_LOCATION = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/nightshade_spider.png");

    public static final ModelLayerLocation NIGHTSHADE_SPIDER = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "nightshade_spider"), "main");

    public NightshadeSpiderRenderer(EntityRendererProvider.Context p_173946_) {
        this(p_173946_, NIGHTSHADE_SPIDER);
        this.shadowRadius *= 0.7F;
    }

    public NightshadeSpiderRenderer(EntityRendererProvider.Context p_174403_, ModelLayerLocation p_174404_) {
        super(p_174403_, new SpiderModel<>(p_174403_.bakeLayer(p_174404_)), 0.8F);
        this.addLayer(new NightshadeSpiderEyesLayer<>(this));
    }

    protected void scale(NightshadeSpider p_113974_, PoseStack p_113975_, float p_113976_) {
        p_113975_.scale(0.7F, 0.7F, 0.7F);
    }

    public ResourceLocation getTextureLocation(T p_113972_) {
        return NIGHTSHADE_SPIDER_LOCATION;
    }
}
