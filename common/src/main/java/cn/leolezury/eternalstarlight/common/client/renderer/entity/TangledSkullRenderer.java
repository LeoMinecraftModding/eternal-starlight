package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.TangledSkullModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.TangledSkull;
import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class TangledSkullRenderer<T extends TangledSkull> extends MobRenderer<T, TangledSkullModel<T>> {
	public static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/tangled_skull.png");

	public TangledSkullRenderer(EntityRendererProvider.Context context) {
		super(context, new TangledSkullModel<>(context.bakeLayer(TangledSkullModel.LAYER_LOCATION)), 0.3f);
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
		if (entity.isShot()) {
			Vec3 currentPos = new Vec3(
				Mth.lerp(partialTicks, entity.xo, entity.getX()),
				Mth.lerp(partialTicks, entity.yo, entity.getY()),
				Mth.lerp(partialTicks, entity.zo, entity.getZ())
			);
			if (entity.trailPositions.isEmpty() || entity.trailPositions.getFirst().distanceTo(currentPos) > 0.1) {
				entity.trailPositions.addFirst(currentPos);
			}
			while (entity.trailPositions.size() > 5) {
				entity.trailPositions.removeLast();
			}
			for (int i = 0; i < entity.trailPositions.size(); i++) {
				getModel().alphaFactor = 1 - ((float) i / entity.trailPositions.size());
				poseStack.pushPose();
				Vec3 trailPos = entity.trailPositions.get(i);
				poseStack.translate(trailPos.x - currentPos.x, trailPos.y - currentPos.y, trailPos.z - currentPos.z);
				super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
				poseStack.popPose();
			}
			getModel().alphaFactor = 1;
		}
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
			y = Easing.OUT_ELASTIC.calculate(Math.min((entity.tickCount + f) / 30, 1));
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
