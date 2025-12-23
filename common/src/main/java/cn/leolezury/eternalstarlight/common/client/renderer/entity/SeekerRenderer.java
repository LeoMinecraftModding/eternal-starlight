package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.SeekerModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.SeekerGlowLayer;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Seeker;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class SeekerRenderer<T extends Seeker> extends MobRenderer<T, SeekerModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/seeker.png");

	public SeekerRenderer(EntityRendererProvider.Context context) {
		super(context, new SeekerModel<>(context.bakeLayer(SeekerModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new SeekerGlowLayer<>(this));
	}

	@Override
	protected void setupRotations(T entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
		poseStack.mulPose(Axis.YP.rotationDegrees(-entity.getSeekerYRot(partialTick) - 90));
		if (entity.deathTime > 0) {
			float deathProgress = (entity.deathTime + partialTick - 1.0F) / 20.0F * 1.6F;
			deathProgress = Mth.sqrt(deathProgress);
			if (deathProgress > 1.0F) {
				deathProgress = 1.0F;
			}
			poseStack.mulPose(Axis.ZP.rotationDegrees(deathProgress * this.getFlipDegrees(entity)));
		}
		poseStack.translate(0.0F, 0.5F, 0.0F);
		poseStack.mulPose(Axis.XP.rotationDegrees(entity.getSeekerXRot(partialTick) - 90));
		poseStack.translate(0.0F, -0.5F, 0.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
