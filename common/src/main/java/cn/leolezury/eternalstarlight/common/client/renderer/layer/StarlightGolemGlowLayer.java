package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.StarlightGolemModel;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.StarlightGolemRenderer;
import cn.leolezury.eternalstarlight.common.entity.boss.golem.StarlightGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class StarlightGolemGlowLayer<T extends StarlightGolem> extends RenderLayer<T, StarlightGolemModel<T>> {
    private static final RenderType GLOW = RenderType.entityTranslucentEmissive(new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/starlight_golem/starlight_golem_glow.png"));
    private static final RenderType GLOW_HALLOWEEN = RenderType.entityTranslucentEmissive(new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/starlight_golem/starlight_golem_glow_halloween.png"));
    private final StarlightGolemModel<T> model;

    public StarlightGolemGlowLayer(RenderLayerParent<T, StarlightGolemModel<T>> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new StarlightGolemModel<>(modelSet.bakeLayer(StarlightGolemModel.LAYER_LOCATION));
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer consumer = bufferSource.getBuffer(StarlightGolemRenderer.isHalloween() ? GLOW_HALLOWEEN : GLOW);
            this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, Math.max(0.0F, Mth.cos(ageInTicks * 0.1f + 3.1415927F)));
        }
    }
}
