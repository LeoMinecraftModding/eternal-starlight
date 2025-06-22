package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.StarfireBirdNestBlock;
import cn.leolezury.eternalstarlight.common.block.entity.StarfireBirdNestBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;

import java.util.List;

@Environment(EnvType.CLIENT)
public class StarfireBirdNestRenderer implements BlockEntityRenderer<StarfireBirdNestBlockEntity> {
	private final EntityRenderDispatcher entityRenderer;

	public StarfireBirdNestRenderer(BlockEntityRendererProvider.Context context) {
		this.entityRenderer = context.getEntityRenderer();
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
	}
}
