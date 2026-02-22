package cn.leolezury.eternalstarlight.common.client.renderer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.LootChestBlockEntity;
import cn.leolezury.eternalstarlight.common.client.model.item.*;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ESItemStackRenderer {
	private static final Supplier<LootChestBlockEntity> LOOT_CHEST = Suppliers.memoize(() -> new LootChestBlockEntity(BlockPos.ZERO, ESBlocks.LOOT_CHEST.get().defaultBlockState()));

	private static GlaciteShieldModel GLACITE_SHIELD_MODEL;
	private static FlowglazeShieldModel FLOWGLAZE_SHIELD_MODEL;
	private static MalariteSpearModel MALARITE_SPEAR_MODEL;
	private static PungencyFruitSpearModel PUNGENCY_FRUIT_SPEAR_MODEL;
	private static CrescentSpearModel CRESCENT_SPEAR_MODEL;

	public static void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
		if (stack.getItem() instanceof BlockItem blockItem) {
			BlockState state = blockItem.getBlock().defaultBlockState();
			BlockEntity blockEntity = null;
			if (state.is(ESBlocks.LOOT_CHEST.get())) {
				blockEntity = LOOT_CHEST.get();
			}
			if (blockEntity != null) {
				Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity, poseStack, bufferSource, light, overlay);
			}
		}
		if (stack.is(ESItems.GLACITE_SHIELD.get())) {
			if (GLACITE_SHIELD_MODEL == null) {
				GLACITE_SHIELD_MODEL = new GlaciteShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(GlaciteShieldModel.LAYER_LOCATION));
			}
			poseStack.pushPose();
			poseStack.scale(1.0F, -1.0F, -1.0F);
			Material material = new Material(Sheets.SHIELD_SHEET, EternalStarlight.id("entity/shields/glacite_shield"));
			VertexConsumer vertexConsumer = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(bufferSource, GLACITE_SHIELD_MODEL.renderType(material.atlasLocation()), true, stack.hasFoil()));
			GLACITE_SHIELD_MODEL.renderToBuffer(poseStack, vertexConsumer, light, overlay);
			poseStack.popPose();
		}
		if (stack.is(ESItems.FLOWGLAZE_SHIELD.get())) {
			if (FLOWGLAZE_SHIELD_MODEL == null) {
				FLOWGLAZE_SHIELD_MODEL = new FlowglazeShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(FlowglazeShieldModel.LAYER_LOCATION));
			}
			poseStack.pushPose();
			poseStack.scale(1.0F, -1.0F, -1.0F);
			Material material = new Material(Sheets.SHIELD_SHEET, EternalStarlight.id("entity/shields/flowglaze_shield"));
			VertexConsumer vertexConsumer = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(bufferSource, FLOWGLAZE_SHIELD_MODEL.renderType(material.atlasLocation()), true, stack.hasFoil()));
			FLOWGLAZE_SHIELD_MODEL.renderToBuffer(poseStack, vertexConsumer, light, overlay);
			poseStack.popPose();
		}
		if (stack.is(ESItems.MALARITE_SPEAR.get())) {
			if (MALARITE_SPEAR_MODEL == null) {
				MALARITE_SPEAR_MODEL = new MalariteSpearModel(Minecraft.getInstance().getEntityModels().bakeLayer(MalariteSpearModel.LAYER_LOCATION));
			}
			poseStack.pushPose();
			poseStack.scale(1.0F, -1.0F, -1.0F);
			VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(bufferSource, MALARITE_SPEAR_MODEL.renderType(MalariteSpearModel.TEXTURE), false, stack.hasFoil());
			MALARITE_SPEAR_MODEL.renderToBuffer(poseStack, vertexConsumer, light, overlay);
			poseStack.popPose();
		}
		if (stack.is(ESItems.PUNGENCY_FRUIT_SPEAR.get())) {
			if (PUNGENCY_FRUIT_SPEAR_MODEL == null) {
				PUNGENCY_FRUIT_SPEAR_MODEL = new PungencyFruitSpearModel(Minecraft.getInstance().getEntityModels().bakeLayer(PungencyFruitSpearModel.LAYER_LOCATION));
			}
			poseStack.pushPose();
			poseStack.scale(1.0F, -1.0F, -1.0F);
			VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(bufferSource, PUNGENCY_FRUIT_SPEAR_MODEL.renderType(PungencyFruitSpearModel.TEXTURE), false, stack.hasFoil());
			PUNGENCY_FRUIT_SPEAR_MODEL.renderToBuffer(poseStack, vertexConsumer, light, overlay);
			poseStack.popPose();
		}
		if (stack.is(ESItems.CRESCENT_SPEAR.get())) {
			if (CRESCENT_SPEAR_MODEL == null) {
				CRESCENT_SPEAR_MODEL = new CrescentSpearModel(Minecraft.getInstance().getEntityModels().bakeLayer(CrescentSpearModel.LAYER_LOCATION));
			}
			poseStack.pushPose();
			poseStack.scale(1.0F, -1.0F, -1.0F);
			VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(bufferSource, CRESCENT_SPEAR_MODEL.renderType(CrescentSpearModel.TEXTURE), false, stack.hasFoil());
			CRESCENT_SPEAR_MODEL.renderToBuffer(poseStack, vertexConsumer, light, overlay);
			poseStack.popPose();
		}
	}
}
