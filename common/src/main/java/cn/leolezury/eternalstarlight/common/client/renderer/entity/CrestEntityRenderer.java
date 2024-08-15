package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.misc.CrestEntity;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CrestEntityRenderer extends EntityRenderer<CrestEntity> {
	public CrestEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(CrestEntity entity) {
		return null;
	}

	@Override
	public void render(CrestEntity entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		EternalStarlight.getClientHelper().spawnManaCrystalItemParticles(entity.getCrestSelf().type(), entity.position());
	}
}
