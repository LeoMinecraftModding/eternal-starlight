package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.LunarSporeModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.LunarSpore;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class LunarSporeRenderer extends EntityRenderer<LunarSpore> {
    private static final ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/spore.png");
    private final LunarSporeModel<LunarSpore> model;

    public LunarSporeRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new LunarSporeModel<>(context.bakeLayer(LunarSporeModel.LAYER_LOCATION));
    }

    @Override
    public void render(LunarSpore entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(ENTITY_TEXTURE));
        float yRot = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
        float xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        this.model.setupAnim(yRot, xRot);
        this.model.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    @Override
    public ResourceLocation getTextureLocation(LunarSpore entity) {
        return ENTITY_TEXTURE;
    }
}
