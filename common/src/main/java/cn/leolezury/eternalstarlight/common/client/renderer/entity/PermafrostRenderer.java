package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.PermafrostModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.Permafrost;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PermafrostRenderer<T extends Permafrost> extends MobRenderer<T, PermafrostModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/permafrost.png");

	public PermafrostRenderer(EntityRendererProvider.Context context) {
		super(context, new PermafrostModel<>(context.bakeLayer(PermafrostModel.LAYER_LOCATION)), 0.3f);
	}

	@Override
	public void render(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
		super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
		entity.smokePos = ESModelUtil.getModelPosition(entity, entity.yBodyRot, List.of(getModel().root(), getModel().lower, getModel().armature));
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
