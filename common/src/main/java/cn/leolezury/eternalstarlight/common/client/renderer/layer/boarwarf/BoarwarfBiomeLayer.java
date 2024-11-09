package cn.leolezury.eternalstarlight.common.client.renderer.layer.boarwarf;

import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.BoarwarfModel;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.state.BoarwarfRenderState;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.BoarwarfType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

@Environment(EnvType.CLIENT)
public class BoarwarfBiomeLayer extends RenderLayer<BoarwarfRenderState, BoarwarfModel> {
	public BoarwarfBiomeLayer(RenderLayerParent<BoarwarfRenderState, BoarwarfModel> parent) {
		super(parent);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, BoarwarfRenderState state, float netHeadYaw, float headPitch) {
		BoarwarfType type = state.type;
		if (type == null) {
			return;
		}
		if (!state.isInvisible) {
			VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutout(type.texture()));
			getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(state, 0.0F));
		}
	}
}
