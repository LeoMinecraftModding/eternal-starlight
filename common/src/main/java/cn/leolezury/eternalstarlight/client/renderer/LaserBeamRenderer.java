package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.entity.attack.beam.AbstractLaserBeam;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class LaserBeamRenderer<T extends AbstractLaserBeam> extends EntityRenderer<T> {
    public LaserBeamRenderer(EntityRendererProvider.Context mgr) {
        super(mgr);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null;
    }
    
    public float getTextureWidth() {
        return 256;
    }
    
    public float getTextureHeight() {
        return 32;
    }

    public float getStartRadius() {
        return 1.3f;
    }

    public float getBeamRadius() {
        return 1;
    }

    @Override
    public void render(T laserBeam, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        double collidePosX = laserBeam.prevCollidePosX + (laserBeam.collidePosX - laserBeam.prevCollidePosX) * delta;
        double collidePosY = laserBeam.prevCollidePosY + (laserBeam.collidePosY - laserBeam.prevCollidePosY) * delta;
        double collidePosZ = laserBeam.prevCollidePosZ + (laserBeam.collidePosZ - laserBeam.prevCollidePosZ) * delta;
        double posX = laserBeam.xo + (laserBeam.getX() - laserBeam.xo) * delta;
        double posY = laserBeam.yo + (laserBeam.getY() - laserBeam.yo) * delta;
        double posZ = laserBeam.zo + (laserBeam.getZ() - laserBeam.zo) * delta;
        float yaw = laserBeam.prevYaw + (laserBeam.renderYaw - laserBeam.prevYaw) * delta;
        float pitch = laserBeam.prevPitch + (laserBeam.renderPitch - laserBeam.prevPitch) * delta;

        float length = (float) Math.sqrt(Math.pow(collidePosX - posX, 2) + Math.pow(collidePosY - posY, 2) + Math.pow(collidePosZ - posZ, 2));
        int frame = Mth.floor((laserBeam.appear.getTimer() - 1 + delta) * 2);
        if (frame < 0) {
            frame = 6;
        }
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(SLRenderType.glow(getTextureLocation(laserBeam)));

        renderStart(frame, matrixStackIn, ivertexbuilder, packedLightIn);
        renderBeam(length, 180f / (float) Math.PI * yaw, 180f / (float) Math.PI * pitch, frame, matrixStackIn, ivertexbuilder, packedLightIn);

        matrixStackIn.pushPose();
        matrixStackIn.translate(collidePosX - posX, collidePosY - posY, collidePosZ - posZ);
        renderEnd(frame, laserBeam.blockSide, matrixStackIn, ivertexbuilder, packedLightIn);
        matrixStackIn.popPose();
    }

    private void renderFlatQuad(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        float minU = 0 + 16F / getTextureWidth() * frame;
        float minV = 0;
        float maxU = minU + 16F / getTextureWidth();
        float maxV = minV + 16F / getTextureHeight();
        PoseStack.Pose matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        drawVertex(matrix4f, matrix3f, builder, -getStartRadius(), -getStartRadius(), 0, minU, minV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, -getStartRadius(), getStartRadius(), 0, minU, maxV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, getStartRadius(), getStartRadius(), 0, maxU, maxV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, getStartRadius(), -getStartRadius(), 0, maxU, minV, 1, packedLightIn);
    }

    private void renderStart(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        matrixStackIn.pushPose();
        Quaternionf quat = this.entityRenderDispatcher.cameraOrientation();
        matrixStackIn.mulPose(quat);
        renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
    }

    private void renderEnd(int frame, Direction side, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        matrixStackIn.pushPose();
        Quaternionf quat = this.entityRenderDispatcher.cameraOrientation();
        matrixStackIn.mulPose(quat);
        renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
        if (side == null) {
            return;
        }
        matrixStackIn.pushPose();
        Quaternionf sideQuat = side.getRotation();
        sideQuat.mul((new Quaternionf()).rotationX(90.0F * (float) Math.PI / 180f));
        matrixStackIn.mulPose(sideQuat);
        matrixStackIn.translate(0, 0, -0.01f);
        renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
    }

    private void drawBeam(float length, int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        float minU = 0;
        float minV = 16 / getTextureHeight() + 1 / getTextureHeight() * frame;
        float maxU = minU + 20 / getTextureWidth();
        float maxV = minV + 1 / getTextureHeight();
        PoseStack.Pose matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        float offset = 0;
        drawVertex(matrix4f, matrix3f, builder, -getBeamRadius(), offset, 0, minU, minV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, -getBeamRadius(), length, 0, minU, maxV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, getBeamRadius(), length, 0, maxU, maxV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, getBeamRadius(), offset, 0, maxU, minV, 1, packedLightIn);
    }

    private void renderBeam(float length, float yaw, float pitch, int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose((new Quaternionf()).rotationX(90.0F * (float) Math.PI / 180f));
        matrixStackIn.mulPose((new Quaternionf()).rotationZ((yaw - 90.0F) * (float) Math.PI / 180f));
        matrixStackIn.mulPose((new Quaternionf()).rotationX(-pitch * (float) Math.PI / 180f));
        matrixStackIn.pushPose();
        matrixStackIn.mulPose((new Quaternionf()).rotationY(Minecraft.getInstance().gameRenderer.getMainCamera().getXRot() + 90.0F));
        drawBeam(length, frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
        
        matrixStackIn.pushPose();
        matrixStackIn.mulPose((new Quaternionf()).rotationY((-Minecraft.getInstance().gameRenderer.getMainCamera().getXRot() - 90.0F) * (float) Math.PI / 180f));
        drawBeam(length, frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
        matrixStackIn.popPose();
    }

    public void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLightIn) {
        vertexBuilder.vertex(matrix, offsetX, offsetY, offsetZ).color(1, 1, 1, 1 * alpha).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
    }
}
