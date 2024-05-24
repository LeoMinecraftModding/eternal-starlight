package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.FrozenTubeModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.FrozenTube;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class FrozenTubeRenderer extends EntityRenderer<FrozenTube> {
    private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/freeze.png");
    private static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/trail.png");
    private final FrozenTubeModel<FrozenTube> model;

    public FrozenTubeRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new FrozenTubeModel<>(context.bakeLayer(FrozenTubeModel.LAYER_LOCATION));
    }

    @Override
    public void render(FrozenTube entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        poseStack.pushPose();
        float yRot = -Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
        float xRot = -Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 90f;
        float bob = entity.tickCount + partialTicks;

        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yRot));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(0.0F, -1.5F, 0.0F);

        this.model.prepareMobModel(entity, 0, 0, partialTicks);
        this.model.setupAnim(entity, 0, 0, bob, 0, xRot);
        RenderType renderType = this.model.renderType(getTextureLocation(entity));
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
        this.model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();

        VertexConsumer trailConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(TRAIL_TEXTURE));
        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
        poseStack.pushPose();
        poseStack.translate(-x, -y, -z);
        entity.trailEffect.createCurrentPoint(new Vec3(x, y, z).add(0, entity.getBbHeight() / 2, 0), new Vec3(x, y, z).subtract(new Vec3(entity.xOld, entity.yOld, entity.zOld)));
        entity.trailEffect.render(trailConsumer, poseStack, partialTicks, 160 / 255f, 164 / 255f, 195 / 255f, 0.5f, light);
        entity.trailEffect.removeNearest();
        poseStack.popPose();

        super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    @Override
    public ResourceLocation getTextureLocation(FrozenTube entity) {
        return ENTITY_TEXTURE;
    }
}
