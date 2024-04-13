package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.block.entity.ESPortalBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public class ESPortalRenderer<T extends ESPortalBlockEntity> implements BlockEntityRenderer<T> {
    public ESPortalRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(T portal, float f, PoseStack stack, MultiBufferSource multiBufferSource, int i, int j) {
        if (portal.getBlockState().getValue(ESPortalBlock.CENTER)) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(ESRenderType.portal());
            Matrix4f matrix4f = stack.last().pose();
            Matrix3f matrix3f = stack.last().normal();
            float radius = 0.6f * portal.getBlockState().getValue(ESPortalBlock.SIZE) * (Math.min(portal.getClientSideTickCount() + Minecraft.getInstance().getFrameTime(), 60f) / 60f);
            if (portal.getBlockState().getValue(ESPortalBlock.AXIS) == Direction.Axis.X) {
                vertexConsumer.vertex(matrix4f, -radius, -radius, 0.5f).color(1, 1, 1, 1).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                vertexConsumer.vertex(matrix4f, -radius, 1 + radius, 0.5f).color(1, 1, 1, 1).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                vertexConsumer.vertex(matrix4f, 1 + radius, 1 + radius, 0.5f).color(1, 1, 1, 1).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                vertexConsumer.vertex(matrix4f, 1 + radius, -radius, 0.5f).color(1, 1, 1, 1).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            } else {
                vertexConsumer.vertex(matrix4f, 0.5f, -radius, -radius).color(1, 1, 1, 1).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                vertexConsumer.vertex(matrix4f, 0.5f, 1 + radius, -radius).color(1, 1, 1, 1).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                vertexConsumer.vertex(matrix4f, 0.5f, 1 + radius, 1 + radius).color(1, 1, 1, 1).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                vertexConsumer.vertex(matrix4f, 0.5f, -radius, 1 + radius).color(1, 1, 1, 1).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            }
        }
    }
}
