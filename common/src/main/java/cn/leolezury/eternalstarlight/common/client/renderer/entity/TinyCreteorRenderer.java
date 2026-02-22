package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.TinyCreteorModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.TinyCreteorPowerLayer;
import cn.leolezury.eternalstarlight.common.entity.living.monster.TinyCreteor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TinyCreteorRenderer<T extends TinyCreteor> extends MobRenderer<T, TinyCreteorModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/tiny_creteor.png");

	public TinyCreteorRenderer(EntityRendererProvider.Context context) {
		super(context, new TinyCreteorModel<>(context.bakeLayer(TinyCreteorModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new TinyCreteorPowerLayer<>(this, context.getModelSet()));
	}

	@Override
	protected void scale(T entity, PoseStack poseStack, float f) {
		float swell = entity.getSwellProgress(f);
		float factor = 1.0F + Mth.sin(swell * 200.0F) * swell * 0.05F;
		swell = Mth.clamp(swell, 0.0F, 1.0F);
		swell *= swell;
		swell *= swell;
		float xz = (1.0F + swell * 0.4F) * factor;
		float y = (1.0F + swell * 0.1F) / factor;
		poseStack.scale(xz, y, xz);
	}

	@Override
	protected float getWhiteOverlayProgress(T entity, float f) {
		float swell = entity.getSwellProgress(f);
		return (int) (swell * 40.0F) % 2 == 0 ? 0.0F : Mth.clamp(swell, 0.05F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
