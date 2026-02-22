package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.model.entity.StarlightGolemModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class StarlightGolemHalloweenLayer<T extends StarlightGolem> extends RenderLayer<T, StarlightGolemModel<T>> {
	private static final RenderType HALLOWEEN = RenderType.entityCutoutNoCull(EternalStarlight.id("textures/entity/starlight_golem/starlight_golem_halloween.png"));
	private final StarlightGolemModel<T> model;

	public StarlightGolemHalloweenLayer(RenderLayerParent<T, StarlightGolemModel<T>> parent, EntityModelSet modelSet) {
		super(parent);
		this.model = new StarlightGolemModel<>(modelSet.bakeLayer(StarlightGolemModel.LAYER_LOCATION));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entity.isInvisible() && ESClientHandler.isHalloween) {
			getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
			this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			VertexConsumer consumer = bufferSource.getBuffer(HALLOWEEN);
			this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), -1);
		}
	}
}
