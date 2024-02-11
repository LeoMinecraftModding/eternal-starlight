package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.block.entity.ESPortalBlockEntity;
import cn.leolezury.eternalstarlight.common.client.renderer.ESRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Direction;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public class ESPortalRenderer<T extends ESPortalBlockEntity> implements BlockEntityRenderer<T> {
    public ESPortalRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(T portal, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        Matrix4f matrix4f = poseStack.last().pose();
        Direction.Axis axis = portal.getBlockState().getValue(ESPortalBlock.AXIS);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(ESRenderType.portal(TheEndPortalRenderer.END_SKY_LOCATION, TheEndPortalRenderer.END_PORTAL_LOCATION));
        if (axis == Direction.Axis.X) {
            vertexConsumer.vertex(matrix4f, 0, 0, 0.5f).endVertex();
            vertexConsumer.vertex(matrix4f, 0, 1, 0.5f).endVertex();
            vertexConsumer.vertex(matrix4f, 1, 1, 0.5f).endVertex();
            vertexConsumer.vertex(matrix4f, 1, 0, 0.5f).endVertex();

            vertexConsumer.vertex(matrix4f, 1, 0, 0.5f).endVertex();
            vertexConsumer.vertex(matrix4f, 1, 1, 0.5f).endVertex();
            vertexConsumer.vertex(matrix4f, 0, 1, 0.5f).endVertex();
            vertexConsumer.vertex(matrix4f, 0, 0, 0.5f).endVertex();
        } else {
            vertexConsumer.vertex(matrix4f, 0.5f, 0, 0).endVertex();
            vertexConsumer.vertex(matrix4f, 0.5f, 1, 0).endVertex();
            vertexConsumer.vertex(matrix4f, 0.5f, 1, 1).endVertex();
            vertexConsumer.vertex(matrix4f, 0.5f, 0, 1).endVertex();

            vertexConsumer.vertex(matrix4f, 0.5f, 0, 1).endVertex();
            vertexConsumer.vertex(matrix4f, 0.5f, 1, 1).endVertex();
            vertexConsumer.vertex(matrix4f, 0.5f, 1, 0).endVertex();
            vertexConsumer.vertex(matrix4f, 0.5f, 0, 0).endVertex();
        }
    }
}
