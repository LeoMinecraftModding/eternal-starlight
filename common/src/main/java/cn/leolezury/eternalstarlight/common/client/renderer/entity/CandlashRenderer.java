package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.CandlashModel;
import cn.leolezury.eternalstarlight.common.entity.attack.Candlash;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class CandlashRenderer extends WhipRenderer<Candlash> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/candlash.png");
	private final CandlashModel<Candlash> model;

	public CandlashRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new CandlashModel<>(context.bakeLayer(CandlashModel.LAYER_LOCATION));
	}

	@Override
	public void renderWhip(Candlash entity, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
		float bob = entity.tickCount + partialTicks;
		this.model.setupAnim(entity, 0, 0, bob, 0, 0);
		RenderType renderType = this.model.renderType(getTextureLocation(entity));
		VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, renderType, false, entity.isFoil());
		this.model.renderToBuffer(stack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);

		if (bob - entity.lastParticleTick > 0.1) {
			entity.tipPositions.offer(getWhipTipPosition(entity, partialTicks));
			entity.lastParticleTick = bob;
		}
	}

	private Vec3 getWhipTipPosition(Candlash entity, float partialTicks) {
		PoseStack stack = new PoseStack();
		stack.translate(entity.getX(), entity.getY(), entity.getZ());
		translateAndRotate(entity, partialTicks, stack);
		this.model.translateToTip(stack);
		Vector4f vec = new Vector4f(0, 0, 0, 1).mul(stack.last().pose());
		return new Vec3(vec.x(), vec.y(), vec.z());
	}

	@Override
	public ResourceLocation getTextureLocation(Candlash entity) {
		return ENTITY_TEXTURE;
	}
}
