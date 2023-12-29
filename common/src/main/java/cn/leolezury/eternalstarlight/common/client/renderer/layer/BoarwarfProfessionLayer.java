package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.BoarwarfModel;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.BoarwarfVariants;
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

@Environment(EnvType.CLIENT)
public class BoarwarfProfessionLayer<T extends Boarwarf> extends RenderLayer<T, BoarwarfModel<T>> {
    private final BoarwarfModel<T> model;

    public BoarwarfProfessionLayer(RenderLayerParent<T, BoarwarfModel<T>> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new BoarwarfModel<>(modelSet.bakeLayer(BoarwarfModel.LAYER_LOCATION));
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.getProfessionVariant() < 0 || entityIn.getProfessionVariant() >= BoarwarfVariants.BoarwarfProfessionVariants.VARIANT_NUM) {
            return;
        }
        ResourceLocation TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/boarwarf/profession/" + entityIn.getProfessionVariant() + ".png");
        if (entityIn.getProfessionVariant() >= 0 && !entityIn.isInvisible()) {
            getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutout(TEXTURE));
            this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlayCoords(entityIn, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
