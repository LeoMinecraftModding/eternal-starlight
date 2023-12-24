package cn.leolezury.eternalstarlight.common.client.renderer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.SporeModel;
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

@Environment(EnvType.CLIENT)
public class SporeRenderer extends EntityRenderer<LunarSpore> {
    private static final ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/spore.png");
    private final SporeModel<LunarSpore> model;

    public SporeRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new SporeModel<>(context.bakeLayer(SporeModel.LAYER_LOCATION));
    }

    @Override
    public void render(LunarSpore p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
        VertexConsumer vertexconsumer = p_114489_.getBuffer(this.model.renderType(ENTITY_TEXTURE));
        this.model.renderToBuffer(p_114488_, vertexconsumer, p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        super.render(p_114485_, p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
    }

    @Override
    public ResourceLocation getTextureLocation(LunarSpore entity) {
        return ENTITY_TEXTURE;
    }
}
