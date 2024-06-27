package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.TangledSkullModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.TangledSkull;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class TangledSkullRenderer<T extends TangledSkull> extends MobRenderer<T, TangledSkullModel<T>> {
	public static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/tangled_skull.png");

	public TangledSkullRenderer(EntityRendererProvider.Context context) {
		super(context, new TangledSkullModel<>(context.bakeLayer(TangledSkullModel.LAYER_LOCATION)), 0.3f);
	}

	@Override
	protected void scale(T entity, PoseStack poseStack, float f) {
		float progress = Math.min((entity.skullDeathTime + f) / 80, 1);
		float swell = progress * 8;
		float factor = 1.0F + Mth.sin(swell * 100.0F) * swell * 0.01F;
		swell = progress;
		swell = Mth.clamp(swell, 0.0F, 1.0F);
		swell *= swell;
		swell *= swell;
		float xz = (1.0F + swell * 0.4F) * factor;
		float y = (1.0F + swell * 0.1F) / factor;
		if (entity.isShotFromMonstrosity()) {
			y = ESMathUtil.easeOutElastic(Math.min((entity.tickCount + f) / 30, 1));
		}
		poseStack.scale(xz, y, xz);
	}

	@Override
	protected float getFlipDegrees(T entity) {
		return 0;
	}

	@Override
	protected float getWhiteOverlayProgress(T entity, float f) {
		float g = Math.min((entity.skullDeathTime + f) / 80, 1);
		return (int) (g * 20.0F) % 2 == 0 ? 0.0F : Mth.clamp(g, 0.5F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return ENTITY_TEXTURE;
	}
}
