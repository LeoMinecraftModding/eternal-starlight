package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.LuminofishModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.LuminofishGlowLayer;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Luminofish;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LuminofishRenderer<T extends Luminofish> extends MobRenderer<T, LuminofishModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/luminofish.png");

	public LuminofishRenderer(EntityRendererProvider.Context context) {
		super(context, new LuminofishModel<>(context.bakeLayer(LuminofishModel.LAYER_LOCATION)), 0.3f);
		this.addLayer(new LuminofishGlowLayer<>(this));
	}

	@Override
	protected void setupRotations(T livingEntity, PoseStack poseStack, float f, float g, float h, float i) {
		super.setupRotations(livingEntity, poseStack, f, g, h, i);
		if (!livingEntity.isInWater() && !livingEntity.isFirstTick()) {
			poseStack.translate(0.1F, 0.1F, -0.1F);
			poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
