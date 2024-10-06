package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.CrystallizedMothModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.CrystallizedMoth;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class CrystallizedMothGlowLayer<T extends CrystallizedMoth> extends RenderLayer<T, CrystallizedMothModel<T>> {
	private static final RenderType GLOW = RenderType.entityTranslucentEmissive(EternalStarlight.id("textures/entity/crystallized_moth_glow.png"));
	private final CrystallizedMothModel<T> model;

	public CrystallizedMothGlowLayer(RenderLayerParent<T, CrystallizedMothModel<T>> parent, EntityModelSet modelSet) {
		super(parent);
		this.model = new CrystallizedMothModel<>(modelSet.bakeLayer(CrystallizedMothModel.LAYER_LOCATION));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entity.isInvisible()) {
			getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
			this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			this.model.renderToBuffer(poseStack, bufferSource.getBuffer(GLOW), packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), FastColor.ARGB32.color((int) (Math.max(0.0F, Mth.cos(ageInTicks * 0.01f + 3.1415927F)) * 255), 255, 255, 255));
		}
	}
}
