package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.StarfireBirdNestBlock;
import cn.leolezury.eternalstarlight.common.block.entity.StarfireBirdNestBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class StarfireBirdNestRenderer implements BlockEntityRenderer<StarfireBirdNestBlockEntity> {
	private final EntityRenderDispatcher entityRenderer;
	private final ItemRenderer itemRenderer;

	public StarfireBirdNestRenderer(BlockEntityRendererProvider.Context context) {
		this.entityRenderer = context.getEntityRenderer();
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(StarfireBirdNestBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		List<StarfireBirdNestBlockEntity.BirdData> adults = blockEntity.getAdults();
		int adultCount = adults.size();
		float slightOffset = 0.001F;
		float singleOffset = adultCount == 1 ? 0 : 0.4F / (adultCount - 1);
		float offset = adultCount == 1 ? 0 : -0.2F;
		for (StarfireBirdNestBlockEntity.BirdData adult : adults) {
			if (blockEntity.getLevel() != null) {
				Entity entity = adult.getOrCreateEntityInstance(blockEntity.getLevel(), blockEntity.getBlockPos());
				if (entity != null) {
					poseStack.pushPose();
					poseStack.translate(0.5F + slightOffset, 0.025F + slightOffset, 0.5F + slightOffset);
					poseStack.mulPose(Axis.YP.rotationDegrees(-blockEntity.getBlockState().getValue(StarfireBirdNestBlock.FACING).toYRot()));
					poseStack.translate(offset, 0.0F, 0.0F);
					entityRenderer.render(entity, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);
					poseStack.popPose();
				}
			}
			slightOffset += 0.001F;
			offset += singleOffset;
		}
		List<StarfireBirdNestBlockEntity.BirdData> babies = blockEntity.getBabies();
		int babyCount = blockEntity.getBabies().size();
		slightOffset = 0.001F;
		singleOffset = babyCount == 1 ? 0 : 0.4F / (babyCount - 1);
		offset = babyCount == 1 ? 0 : -0.2F;
		for (StarfireBirdNestBlockEntity.BirdData baby : babies) {
			if (blockEntity.getLevel() != null) {
				Entity entity = baby.getOrCreateEntityInstance(blockEntity.getLevel(), blockEntity.getBlockPos());
				if (entity != null) {
					poseStack.pushPose();
					poseStack.translate(0.5F + slightOffset, 0.125F + slightOffset, 0.5F + slightOffset);
					poseStack.mulPose(Axis.YP.rotationDegrees(-blockEntity.getBlockState().getValue(StarfireBirdNestBlock.FACING).toYRot()));
					poseStack.translate(offset, 0.0F, -0.125F);
					entityRenderer.render(entity, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);
					poseStack.popPose();
				}
			}
			slightOffset += 0.001F;
			offset += singleOffset;
		}
		long seed = blockEntity.getBlockPos().asLong();
		List<ItemStack> seeds = blockEntity.getItems().stream().filter(stack -> !stack.isEmpty()).toList();
		slightOffset = 0.001F;
		float seedsOffset = blockEntity.getBlockState().getBlock() instanceof StarfireBirdNestBlock block ? block.getSeedsRenderOffset() : 0;
		for (int i = 0; i < seeds.size(); i++) {
			ItemStack stack = seeds.get(i);
			if (!stack.isEmpty()) {
				poseStack.pushPose();
				poseStack.translate(0.5F + slightOffset, seedsOffset + slightOffset, 0.5F + slightOffset);
				poseStack.mulPose(Axis.YP.rotationDegrees(i * 30.0F));
				poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
				poseStack.scale(0.375F, 0.375F, 0.375F);
				this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), (int) (i + seed));
				poseStack.popPose();
			}
			slightOffset += 0.001F;
			seedsOffset += 0.375F * 0.0625F;
		}
	}
}
