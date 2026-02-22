package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.AuroraDeerModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.AuroraDeer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class AuroraDeerSnowLayer<T extends AuroraDeer> extends RenderLayer<T, AuroraDeerModel<T>> {
	private static final RenderType SNOW = RenderType.entityCutout(EternalStarlight.id("textures/entity/aurora_deer_snow.png"));

	public AuroraDeerSnowLayer(RenderLayerParent<T, AuroraDeerModel<T>> parent) {
		super(parent);
	}

	@Override
	public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.hasSnow()) {
			this.getParentModel().renderToBuffer(stack, buffer.getBuffer(SNOW), packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
		}
	}
}
