package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.model.StarlightGolemModel;
import cn.leolezury.eternalstarlight.client.renderer.layer.StarlightGolemGlowLayer;
import cn.leolezury.eternalstarlight.entity.boss.StarlightGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class StarlightGolemRenderer<T extends StarlightGolem> extends MobRenderer<T, StarlightGolemModel<T>> {
    ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/starlight_golem.png");

    public StarlightGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new StarlightGolemModel<>(context.bakeLayer(StarlightGolemModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new StarlightGolemGlowLayer<>(this));
    }

    @Override
    public void render(T entity, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, delta, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected float getFlipDegrees(T p_115337_) {
        return 0;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ENTITY_TEXTURE;
    }
}
