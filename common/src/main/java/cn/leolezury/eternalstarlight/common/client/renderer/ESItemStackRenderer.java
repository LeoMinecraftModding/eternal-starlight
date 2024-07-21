package cn.leolezury.eternalstarlight.common.client.renderer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.item.CrescentSpear;
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
	private static GlaciteShieldModel GLACITE_SHIELD_MODEL;
	private static CrescentSpear CRESCENT_SPEAR_MODEL;

	public static void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
		if (stack.is(ESItems.GLACITE_SHIELD.get())) {
			if (GLACITE_SHIELD_MODEL == null) {
				GLACITE_SHIELD_MODEL = new GlaciteShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(GlaciteShieldModel.LAYER_LOCATION));
			}
			poseStack.pushPose();
			poseStack.scale(1.0F, -1.0F, -1.0F);
			Material material = new Material(Sheets.SHIELD_SHEET, EternalStarlight.id("entity/glacite_shield"));
			VertexConsumer vertexConsumer = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(multiBufferSource, GLACITE_SHIELD_MODEL.renderType(material.atlasLocation()), true, stack.hasFoil()));
			GLACITE_SHIELD_MODEL.renderToBuffer(poseStack, vertexConsumer, light, overlay);
			poseStack.popPose();
		}
		if (stack.is(ESItems.CRESCENT_SPEAR.get())) {
			if (CRESCENT_SPEAR_MODEL == null) {
				CRESCENT_SPEAR_MODEL = new CrescentSpear(Minecraft.getInstance().getEntityModels().bakeLayer(CrescentSpear.LAYER_LOCATION));
			}
			poseStack.pushPose();
			poseStack.scale(1.0F, -1.0F, -1.0F);
			VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(multiBufferSource, CRESCENT_SPEAR_MODEL.renderType(CrescentSpear.TEXTURE), false, stack.hasFoil());
			CRESCENT_SPEAR_MODEL.renderToBuffer(poseStack, vertexConsumer, light, overlay);
			poseStack.popPose();
		}
	}
}
