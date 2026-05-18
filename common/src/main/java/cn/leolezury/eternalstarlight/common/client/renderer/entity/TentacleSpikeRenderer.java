package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.TentacleSpikeModel;
import cn.leolezury.eternalstarlight.common.entity.attack.TentacleSpike;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class TentacleSpikeRenderer extends WhipRenderer<TentacleSpike> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/tentacle_spike.png");
	private static final ResourceLocation GLOW_TEXTURE = EternalStarlight.id("textures/entity/tentacle_spike_glow.png");
	private final TentacleSpikeModel<TentacleSpike> model;

	public TentacleSpikeRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new TentacleSpikeModel<>(context.bakeLayer(TentacleSpikeModel.LAYER_LOCATION));
	}

	@Override
	public void renderWhip(TentacleSpike entity, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
		float bob = entity.tickCount + partialTicks;
		this.model.setupAnim(entity, 0, 0, bob, 0, 0);
		RenderType renderType = this.model.renderType(getTextureLocation(entity));
		VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, renderType, false, entity.isFoil());
		this.model.renderToBuffer(stack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
		renderType = RenderType.eyes(GLOW_TEXTURE);
		vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, renderType, false, entity.isFoil());
		this.model.renderToBuffer(stack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
	}

	@Override
	public ResourceLocation getTextureLocation(TentacleSpike entity) {
		return ENTITY_TEXTURE;
	}
}
