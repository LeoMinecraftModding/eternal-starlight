package cn.leolezury.eternalstarlight.common.client.renderer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.item.GlaciteShieldModel;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class ESItemStackRenderer {
	private static GlaciteShieldModel glaciteShieldModel;

	public static void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
		if (stack.is(ESItems.GLACITE_SHIELD.get())) {
			if (glaciteShieldModel == null) {
				glaciteShieldModel = new GlaciteShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(GlaciteShieldModel.LAYER_LOCATION));
			}
			poseStack.pushPose();
			poseStack.scale(1.0F, -1.0F, -1.0F);
			Material material = new Material(Sheets.SHIELD_SHEET, EternalStarlight.id("entity/glacite_shield"));
			VertexConsumer vertexConsumer = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(multiBufferSource, glaciteShieldModel.renderType(material.atlasLocation()), true, stack.hasFoil()));
			glaciteShieldModel.renderToBuffer(poseStack, vertexConsumer, light, overlay);
			poseStack.popPose();
		}
	}
}
