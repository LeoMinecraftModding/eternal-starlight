package cn.leolezury.eternalstarlight.common.client.renderer.layer.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.BoarwarfModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession.BoarwarfBlacksmithModel;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.state.BoarwarfRenderState;
import cn.leolezury.eternalstarlight.common.registry.ESBoarwarfProfessions;
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
public class BoarwarfBlacksmithLayer extends RenderLayer<BoarwarfRenderState, BoarwarfModel> {
	private static final ResourceLocation TEXTURE = EternalStarlight.id("textures/entity/boarwarf/profession/blacksmith.png");
	private final BoarwarfBlacksmithModel professionModel;

	public BoarwarfBlacksmithLayer(RenderLayerParent<BoarwarfRenderState, BoarwarfModel> parent, EntityModelSet modelSet) {
		super(parent);
		this.professionModel = new BoarwarfBlacksmithModel(modelSet.bakeLayer(BoarwarfBlacksmithModel.LAYER_LOCATION));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, BoarwarfRenderState state, float netHeadYaw, float headPitch) {
		if (!state.isInvisible && state.profession == ESBoarwarfProfessions.BLACKSMITH.get()) {
			this.professionModel.setupAnim(state);
			this.professionModel.copyPropertiesFrom(getParentModel());
			VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
			this.professionModel.renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(state, 0.0F));
		}
	}
}
