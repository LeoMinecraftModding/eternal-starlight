package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.client.renderer.ESRenderType;
import cn.leolezury.eternalstarlight.common.entity.attack.beam.AbstractLaserBeam;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public abstract class AbstractLaserBeamRenderer<T extends AbstractLaserBeam> extends EntityRenderer<T> {
    public AbstractLaserBeamRenderer(EntityRendererProvider.Context context) {
        super(context);
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
    public void render(T laserBeam, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
        double targetX = Mth.lerp(partialTicks, laserBeam.prevCollidePosX, laserBeam.collidePosX);
        double targetY = Mth.lerp(partialTicks, laserBeam.prevCollidePosY, laserBeam.collidePosY);
        double targetZ = Mth.lerp(partialTicks, laserBeam.prevCollidePosZ, laserBeam.collidePosZ);

        double posX = Mth.lerp(partialTicks, laserBeam.xo, laserBeam.getX());
        double posY = Mth.lerp(partialTicks, laserBeam.yo, laserBeam.getY());
        double posZ = Mth.lerp(partialTicks, laserBeam.zo, laserBeam.getZ());

        float yaw = Mth.lerp(partialTicks, laserBeam.prevYaw, laserBeam.renderYaw);
        float pitch = Mth.lerp(partialTicks, laserBeam.prevPitch, laserBeam.renderPitch);

        float length = (float) Math.sqrt(Math.pow(targetX - posX, 2) + Math.pow(targetY - posY, 2) + Math.pow(targetZ - posZ, 2));

        int frame = Mth.floor((laserBeam.appearTimer - 1 + partialTicks) * 2);
        if (frame < 0) {
            frame = 6;
        }

        // debug
        frame = 5;

        VertexConsumer consumer = bufferSource.getBuffer(ESRenderType.laserBeam(getTextureLocation(laserBeam)));
        VertexConsumer glowConsumer = bufferSource.getBuffer(ESRenderType.glow(getTextureLocation(laserBeam)));

        // render beam start
        renderQuad(frame, this.entityRenderDispatcher.cameraOrientation(), stack, glowConsumer, packedLight);

        // render beam
        renderBeam(length, 180f / (float) Math.PI * yaw, 180f / (float) Math.PI * pitch, frame, stack, consumer, packedLight);

        // render beam end
        stack.pushPose();
        stack.translate(targetX - posX, targetY - posY, targetZ - posZ);
        renderQuad(frame, this.entityRenderDispatcher.cameraOrientation(), stack, glowConsumer, packedLight);
        if (laserBeam.blockSide != null) {
            renderBlockHit(frame, laserBeam.blockSide, stack, glowConsumer, packedLight);
        }
        stack.popPose();
    }

    private void renderBlockHit(int frame, Direction hitDirection, PoseStack stack, VertexConsumer consumer, int packedLight) {
        stack.pushPose();
        Quaternionf sideRotation = hitDirection.getRotation().mul((new Quaternionf()).rotationX(90.0F * (float) Math.PI / 180f));
        renderQuad(frame, sideRotation, stack, consumer, packedLight);
        stack.popPose();
    }

    private void renderQuad(int frame, Quaternionf rotation, PoseStack stack, VertexConsumer consumer, int packedLight) {
        stack.pushPose();
        stack.mulPose(rotation);

        float textureX = 0 + 16F / getTextureWidth() * frame;
        float textureY = 0;
        float textureEndX = textureX + 16F / getTextureWidth();
        float textureEndY = textureY + 16F / getTextureHeight();

        PoseStack.Pose pose = stack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        vertex(matrix4f, matrix3f, consumer, -getStartRadius(), -getStartRadius(), 0, textureX, textureY, 1, packedLight);
        vertex(matrix4f, matrix3f, consumer, -getStartRadius(), getStartRadius(), 0, textureX, textureEndY, 1, packedLight);
        vertex(matrix4f, matrix3f, consumer, getStartRadius(), getStartRadius(), 0, textureEndX, textureEndY, 1, packedLight);
        vertex(matrix4f, matrix3f, consumer, getStartRadius(), -getStartRadius(), 0, textureEndX, textureY, 1, packedLight);
        stack.popPose();
    }

    private void renderBeamPart(float length, int frame, PoseStack stack, VertexConsumer consumer, int packedLight) {
        float textureX = 0;
        float textureY = 16 / getTextureHeight() + 1 / getTextureHeight() * frame;
        float textureEndX = textureX + 20 / getTextureWidth();
        float textureEndY = textureY + 1 / getTextureHeight();

        PoseStack.Pose pose = stack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        vertex(matrix4f, matrix3f, consumer, -getBeamRadius(), 0, 0, textureX, textureY, 1, packedLight);
        vertex(matrix4f, matrix3f, consumer, -getBeamRadius(), length, 0, textureX, textureEndY, 1, packedLight);
        vertex(matrix4f, matrix3f, consumer, getBeamRadius(), length, 0, textureEndX, textureEndY, 1, packedLight);
        vertex(matrix4f, matrix3f, consumer, getBeamRadius(), 0, 0, textureEndX, textureY, 1, packedLight);
    }

    private void renderBeam(float length, float yaw, float pitch, int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose((new Quaternionf()).rotationX(90.0F * (float) Math.PI / 180f));
        matrixStackIn.mulPose((new Quaternionf()).rotationZ((yaw - 90.0F) * (float) Math.PI / 180f));
        matrixStackIn.mulPose((new Quaternionf()).rotationX(-pitch * (float) Math.PI / 180f));

        float angle = Minecraft.getInstance().gameRenderer.getMainCamera().getXRot();

        // debug
        matrixStackIn.pushPose();
        matrixStackIn.mulPose((new Quaternionf()).rotationY((angle) * (float) Math.PI / 180f));

        renderBeamPart(length, frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();

        /*for (int i = 0; i < 6; i++) {
            matrixStackIn.pushPose();
            matrixStackIn.mulPose((new Quaternionf()).rotationY((angle + i * 30) * (float) Math.PI / 180f));

            renderBeamPart(length, frame, matrixStackIn, builder, packedLightIn);
            matrixStackIn.popPose();
        }*/

        matrixStackIn.popPose();
    }

    public void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer consumer, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLight) {
        consumer.vertex(matrix4f, offsetX, offsetY, offsetZ).color(1, 1, 1, 1 * alpha).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }
}
