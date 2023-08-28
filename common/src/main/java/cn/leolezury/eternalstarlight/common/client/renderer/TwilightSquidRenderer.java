package cn.leolezury.eternalstarlight.common.client.renderer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.TwilightSquidModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.TwilightSquidEyesLayer;
import cn.leolezury.eternalstarlight.common.entity.animal.TwilightSquid;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class TwilightSquidRenderer<T extends TwilightSquid> extends MobRenderer<T, TwilightSquidModel<T>> {
    ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/twilight_squid.png");

    public TwilightSquidRenderer(EntityRendererProvider.Context context) {
        super(context, new TwilightSquidModel<>(context.bakeLayer(TwilightSquidModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new TwilightSquidEyesLayer<>(this));
    }

    @Override
    protected void scale(T entity, PoseStack poseStack, float f) {
        float scale = 1f;
        if (entity.tickCount % 20 < 10) {
            scale = ((20 - entity.tickCount % 20) / 15f + 20f) / 20f;
        }
        if (entity.tickCount % 20 >= 10) {
            scale = ((entity.tickCount % 20) / 15f + 20f) / 20f;
        }
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ENTITY_TEXTURE;
    }
}
