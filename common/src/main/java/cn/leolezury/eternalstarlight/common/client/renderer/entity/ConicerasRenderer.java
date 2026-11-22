package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.ConicerasModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Coniceras;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ConicerasRenderer<T extends Coniceras> extends MobRenderer<T, ConicerasModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/coniceras.png");

	public ConicerasRenderer(EntityRendererProvider.Context context) {
		super(context, new ConicerasModel<>(context.bakeLayer(ConicerasModel.LAYER_LOCATION)), 0.4f);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}

	@Override
	protected void setupRotations(T coniceras, PoseStack poseStack, float f, float g, float h, float i) {
		// While charging the body yaw has to follow the synced entity yaw directly: the client side
		// body rotation control only follows yRot while the mob is moving, and Coniceras holds still
		// during the windup.
		float yaw = coniceras.isCharging() ? Mth.rotLerp(h, coniceras.yRotO, coniceras.getYRot()) : g;
		float pitch = Mth.lerp(h, coniceras.xBodyRotO, coniceras.xBodyRot);
		float roll = Mth.lerp(h, coniceras.zBodyRotO, coniceras.zBodyRot);
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yaw));
		poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
		poseStack.mulPose(Axis.YP.rotationDegrees(roll));
	}
}
