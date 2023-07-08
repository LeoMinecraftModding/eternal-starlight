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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VineRenderer extends EntityRenderer<Vine> {
    ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/vine.png");
    VineModel<Vine> model;

    public VineRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new VineModel<>(context.bakeLayer(VineModel.LAYER_LOCATION));
    }

    @Override
    public void render(Vine p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
        p_114488_.pushPose();
        float scale = p_114485_.getSpawnedTicks() <= 40 ? p_114485_.getSpawnedTicks() / 40f : 1;
        if (p_114485_.getSpawnedTicks() >= 160) {
            scale = (200 - p_114485_.getSpawnedTicks()) / 40f;
        }
        p_114488_.scale(scale, scale, scale);
        VertexConsumer vertexconsumer = p_114489_.getBuffer(this.model.renderType(ENTITY_TEXTURE));
        this.model.renderToBuffer(p_114488_, vertexconsumer, p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_114488_.popPose();
        super.render(p_114485_, p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
    }

    @Override
    public ResourceLocation getTextureLocation(Vine entity) {
        return ENTITY_TEXTURE;
    }
}