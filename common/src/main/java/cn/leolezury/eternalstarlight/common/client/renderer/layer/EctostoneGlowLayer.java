package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.client.model.entity.EctostoneModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone.Ectostone;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class EctostoneGlowLayer<T extends Ectostone, M extends EctostoneModel<T>> extends RenderLayer<T, M> {
	public EctostoneGlowLayer(RenderLayerParent<T, M> parent) {
		super(parent);
	}

	@Override
	public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight,
					   @NotNull T entity, float limbSwing, float limbSwingAmount, float partialTicks,
					   float ageInTicks, float netHeadYaw, float headPitch) {
		float time = entity.tickCount + partialTicks;
		float wave = Mth.cos(time * 0.025F) + 1.0F;
		float breathing = 0.35F + 0.65F * wave * 0.5F;

		float alpha;
		if (entity.getDormancyState() == Ectostone.DORMANCY_STATE_FALLING_ASLEEP) {
			float fade = 1.0F - Mth.clamp(entity.getBehaviorTicks() + partialTicks / 25.0F, 0.0F, 1.0F);
			alpha = breathing * fade;
		} else if (entity.getDormancyState() == Ectostone.DORMANCY_STATE_AWAKENING) {
			alpha = breathing * Mth.clamp(entity.getBehaviorTicks() + partialTicks / 40.0F, 0.0F, 1.0F);
		} else if (entity.isDormancy()) {
			alpha = 0.0F;
		} else {
			alpha = breathing;
		}

		if (entity.deathTime > 0) {
			alpha *= 1.0F - Math.min(entity.deathTime / 35.0F, 1.0F);
		}

		VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(
			entity.getVariant().value().glowTextureFull()));

		this.getParentModel().renderToBuffer(poseStack, consumer,
			LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
			Mth.ceil(Mth.clamp(alpha, 0.0F, 1.0F) * 255.0F) << 24 | 0xFFFFFF);
	}
}