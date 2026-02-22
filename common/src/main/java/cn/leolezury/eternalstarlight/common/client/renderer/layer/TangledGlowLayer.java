package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.TangledModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Tangled;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class TangledGlowLayer<T extends Tangled> extends RenderLayer<T, TangledModel<T>> {
	private static final RenderType GLOW = RenderType.entityTranslucentEmissive(EternalStarlight.id("textures/entity/tangled_glow.png"));
	private final TangledModel<T> model;

	public TangledGlowLayer(RenderLayerParent<T, TangledModel<T>> parent, EntityModelSet modelSet) {
		super(parent);
		this.model = new TangledModel<>(modelSet.bakeLayer(TangledModel.LAYER_LOCATION));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entity.isInvisible()) {
			getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
			this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			this.model.renderToBuffer(poseStack, bufferSource.getBuffer(GLOW), packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), -1);
		}
	}
}
