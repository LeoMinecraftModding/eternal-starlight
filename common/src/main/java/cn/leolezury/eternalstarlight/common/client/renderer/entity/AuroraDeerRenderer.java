package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.AuroraDeerModel;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.state.AuroraDeerRenderState;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.AuroraDeerSnowLayer;
import cn.leolezury.eternalstarlight.common.entity.living.animal.AuroraDeer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class AuroraDeerRenderer extends MobRenderer<AuroraDeer, AuroraDeerRenderState, AuroraDeerModel> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/aurora_deer.png");

	public AuroraDeerRenderer(EntityRendererProvider.Context context) {
		super(context, new AuroraDeerModel(context.bakeLayer(AuroraDeerModel.LAYER_LOCATION)), 0.8f);
		this.addLayer(new AuroraDeerSnowLayer<>(this));
	}

	@Override
	public AuroraDeerRenderState createRenderState() {
		return new AuroraDeerRenderState();
	}

	@Override
	public void extractRenderState(AuroraDeer entity, AuroraDeerRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.idleAnimationState.copyFrom(entity.idleAnimationState);
		state.hasLeftHorn = entity.hasLeftHorn();
		state.hasRightHorn = entity.hasRightHorn();
	}

	@Override
	public ResourceLocation getTextureLocation(AuroraDeerRenderState state) {
		return ENTITY_TEXTURE;
	}
}
