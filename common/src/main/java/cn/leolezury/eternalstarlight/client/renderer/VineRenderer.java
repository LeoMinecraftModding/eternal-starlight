package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.model.VineModel;
import cn.leolezury.eternalstarlight.entity.attack.Vine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


public class VineRenderer extends EntityRenderer<Vine> {
    ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/vine.png");
    VineModel<Vine> model;

    public VineRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new VineModel<>(context.bakeLayer(VineModel.LAYER_LOCATION));
    }

    @Override
    public void render(Vine entity, float f1, float f2, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
        stack.pushPose();
        float scale = entity.getSpawnedTicks() <= 40 ? entity.getSpawnedTicks() / 40f : 1;
        if (entity.getSpawnedTicks() >= 160) {
            scale = (200 - entity.getSpawnedTicks()) / 40f;
        }
        stack.scale(scale, scale, scale);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(ENTITY_TEXTURE));
        this.model.renderToBuffer(stack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        stack.popPose();
        super.render(entity, f1, f2, stack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Vine entity) {
        return ENTITY_TEXTURE;
    }
}