package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.LunarVineModel;
import cn.leolezury.eternalstarlight.common.entity.attack.LunarVine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class LunarVineRenderer extends EntityRenderer<LunarVine> {
    private static final ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/vine.png");
    LunarVineModel<LunarVine> model;

    public LunarVineRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new LunarVineModel<>(context.bakeLayer(LunarVineModel.LAYER_LOCATION));
    }

    @Override
    public void render(LunarVine entity, float yaw, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
        stack.pushPose();
        float scale = entity.getSpawnedTicks() <= 40 ? entity.getSpawnedTicks() / 40f : 1;
        if (entity.getSpawnedTicks() >= 160) {
            scale = (200 - entity.getSpawnedTicks()) / 40f;
        }
        stack.scale(scale, scale, scale);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(ENTITY_TEXTURE));
        this.model.renderToBuffer(stack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        stack.popPose();
        super.render(entity, yaw, partialTicks, stack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(LunarVine entity) {
        return ENTITY_TEXTURE;
    }
}