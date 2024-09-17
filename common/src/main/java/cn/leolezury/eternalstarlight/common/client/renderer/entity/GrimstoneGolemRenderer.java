package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.GrimstoneGolemModel;
import cn.leolezury.eternalstarlight.common.entity.living.GrimstoneGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class GrimstoneGolemRenderer<T extends GrimstoneGolem> extends MobRenderer<T, GrimstoneGolemModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/grimstone_golem.png");
	private final ItemRenderer itemRenderer;

	public GrimstoneGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new GrimstoneGolemModel<>(context.bakeLayer(GrimstoneGolemModel.LAYER_LOCATION)), 0.3f);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		float scale = entity.getScale();
		poseStack.scale(scale, scale, scale);
		ItemStack itemStack = entity.getMainHandItem();
		BakedModel bakedModel = this.itemRenderer.getModel(itemStack, entity.level(), entity, entity.getId());
		poseStack.mulPose(Axis.YP.rotationDegrees(270 - Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot)));
		poseStack.translate(0.5, 0.5 + Math.sin((entity.tickCount + partialTicks) * 0.1) * 0.1, 0);
		poseStack.mulPose(Axis.YP.rotationDegrees((entity.tickCount + partialTicks) * 8));
		itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, bakedModel);
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
