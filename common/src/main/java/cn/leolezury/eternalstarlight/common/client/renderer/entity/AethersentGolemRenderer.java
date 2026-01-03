package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.AethersentGolemModel;
import cn.leolezury.eternalstarlight.common.entity.living.AethersentGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@Environment(EnvType.CLIENT)
public class AethersentGolemRenderer<T extends AethersentGolem> extends MobRenderer<T, AethersentGolemModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/aethersent_golem.png");

	public AethersentGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new AethersentGolemModel<>(context.bakeLayer(AethersentGolemModel.LAYER_LOCATION)), 0.3f);
	}

	@Override
	public void render(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
		super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
		if (entity.shootAnimationState.isStarted() && !entity.shootPosTracked) {
			entity.leftMuzzlePos = ESModelUtil.getModelPartWorldPosition(entity, entity.yBodyRot, List.of(getModel().upper, getModel().body, getModel().leftArm, getModel().leftMuzzle));
			entity.rightMuzzlePos = ESModelUtil.getModelPartWorldPosition(entity, entity.yBodyRot, List.of(getModel().upper, getModel().body, getModel().rightArm, getModel().rightMuzzle));
			entity.shootPosTracked = true;
			entity.shouldAddShootParticle = true;
		}
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
