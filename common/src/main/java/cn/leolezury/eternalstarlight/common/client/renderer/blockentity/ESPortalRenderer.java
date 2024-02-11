package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.block.entity.ESPortalBlockEntity;
import cn.leolezury.eternalstarlight.common.client.renderer.ESRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Direction;
import org.joml.Matrix4f;

public class ESPortalRenderer<T extends ESPortalBlockEntity> implements BlockEntityRenderer<T> {
    public ESPortalRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(T portalEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        Matrix4f matrix4f = poseStack.last().pose();
        this.renderCube(portalEntity, matrix4f, multiBufferSource.getBuffer(ESRenderType.portal(TheEndPortalRenderer.END_SKY_LOCATION, TheEndPortalRenderer.END_PORTAL_LOCATION)));
    }

    private void renderCube(T portalEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer) {
        float f = 0.375F;
        float g = 0.75F;
        this.renderFace(portalEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
        this.renderFace(portalEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
        this.renderFace(portalEntity, matrix4f, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
        this.renderFace(portalEntity, matrix4f, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
        this.renderFace(portalEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
        this.renderFace(portalEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, g, g, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
    }

    private void renderFace(T portalEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, float k, float l, float m, Direction direction) {
        if (direction.getAxis() == Direction.Axis.Y && portalEntity.getBlockState().getBlock() instanceof ESPortalBlock) {
            vertexConsumer.vertex(matrix4f, f, h, j).endVertex();
            vertexConsumer.vertex(matrix4f, g, h, k).endVertex();
            vertexConsumer.vertex(matrix4f, g, i, l).endVertex();
            vertexConsumer.vertex(matrix4f, f, i, m).endVertex();
        }

    }
}
