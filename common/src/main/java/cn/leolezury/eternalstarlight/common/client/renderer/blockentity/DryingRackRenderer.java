package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.DryingRackBlock;
import cn.leolezury.eternalstarlight.common.block.entity.DryingRackBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class DryingRackRenderer implements BlockEntityRenderer<DryingRackBlockEntity> {
	private final ItemRenderer itemRenderer;

	public DryingRackRenderer(BlockEntityRendererProvider.Context context) {
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(DryingRackBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		poseStack.pushPose();
		poseStack.translate(0.5F, 0.75F, 0.5F);
		poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockState().getValue(DryingRackBlock.FACING).toYRot()));
		poseStack.translate(0, 0, 0.01F);
		poseStack.scale(0.6F, 0.6F, 0.6F);
		this.itemRenderer.renderStatic(blockEntity.getItem(), ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), (int) blockEntity.getBlockPos().asLong());
		poseStack.popPose();
	}
}
