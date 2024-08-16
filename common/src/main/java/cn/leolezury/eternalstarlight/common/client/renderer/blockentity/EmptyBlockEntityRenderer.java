package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EmptyBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
	public EmptyBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

	}

	@Override
	public void render(T blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {

	}
}
