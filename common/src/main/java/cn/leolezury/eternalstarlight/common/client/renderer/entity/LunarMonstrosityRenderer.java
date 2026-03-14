package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.LunarMonstrosityModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.List;

public class LunarMonstrosityRenderer<T extends LunarMonstrosity> extends MobRenderer<T, LunarMonstrosityModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/lunar_monstrosity.png");

	public LunarMonstrosityRenderer(EntityRendererProvider.Context context) {
		super(context, new LunarMonstrosityModel<>(context.bakeLayer(LunarMonstrosityModel.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public void render(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
		super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
		entity.headPos = ESModelUtil.getModelPartWorldPosition(entity, Mth.lerp(partialTicks, entity.yBodyRotO, entity.yBodyRot), List.of(getModel().root(), getModel().stemAll, getModel().stemMiddle, getModel().stemTop, getModel().head));
	}

	@Override
	protected float getFlipDegrees(T livingEntity) {
		return 0;
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
