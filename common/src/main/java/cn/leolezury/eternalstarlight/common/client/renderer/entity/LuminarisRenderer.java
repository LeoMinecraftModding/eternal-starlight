package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.LuminarisModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.LuminarisGlowLayer;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Luminaris;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class LuminarisRenderer<T extends Luminaris> extends MobRenderer<T, LuminarisModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/luminaris.png");

    public LuminarisRenderer(EntityRendererProvider.Context context) {
        super(context, new LuminarisModel<>(context.bakeLayer(LuminarisModel.LAYER_LOCATION)), 0.3f);
        this.addLayer(new LuminarisGlowLayer<>(this));
    }

    @Override
    protected void setupRotations(T living, PoseStack poseStack, float f, float g, float h) {
        super.setupRotations(living, poseStack, f, g, h);
        if (!living.isInWater()) {
            poseStack.translate(0.1F, 0.1F, -0.1F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ENTITY_TEXTURE;
    }
}
