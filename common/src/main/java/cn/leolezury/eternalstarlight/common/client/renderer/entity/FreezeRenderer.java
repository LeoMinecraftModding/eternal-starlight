package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.FreezeModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Freeze;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class FreezeRenderer<T extends Freeze> extends MobRenderer<T, FreezeModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/freeze.png");
    private static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/trail.png");

    public FreezeRenderer(EntityRendererProvider.Context context) {
        super(context, new FreezeModel<>(context.bakeLayer(FreezeModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public void render(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
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
    public ResourceLocation getTextureLocation(T entity) {
        return ENTITY_TEXTURE;
    }
}
