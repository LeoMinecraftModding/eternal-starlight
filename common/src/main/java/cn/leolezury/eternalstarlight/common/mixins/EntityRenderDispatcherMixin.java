package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import cn.leolezury.eternalstarlight.common.effect.CrystallineInfectionEffect;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Shadow public abstract <T extends Entity> EntityRenderer<? super T> getRenderer(T entity);

    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    private static void renderShadow(PoseStack poseStack, MultiBufferSource multiBufferSource, Entity entity, float f, float g, LevelReader levelReader, float h, CallbackInfo ci) {
        if (entity instanceof Player && ClientSetupHandlers.renderingFirstPersonPlayer) {
            // cancel the shadow rendering while rendering a first person player model
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private <E extends Entity> void render(E entity, double xOffset, double yOffset, double zOffset, float delta, float yRot, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, CallbackInfo ci) {
        if (entity instanceof LivingEntity living) {
            AttributeInstance speed = living.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speed == null) return;

            AttributeModifier infection = speed.getModifier(CrystallineInfectionEffect.MOVEMENT_SPEED_MODIFIER_UUID);
            if (infection == null) return;
            
            EntityRenderer<? super E> entityRenderer = getRenderer(living);
            
            Vec3 renderOffset = entityRenderer.getRenderOffset(entity, yRot);
            double x = xOffset + renderOffset.x();
            double y = yOffset + renderOffset.y();
            double z = zOffset + renderOffset.z();
            poseStack.pushPose();
            poseStack.translate(x, y, z);

            long seed = (long) (Math.pow(living.getId(), 3) * 12345L);
            RandomSource random = RandomSource.create();
            random.setSeed(seed);
            int numCubes = (int) (living.getBbHeight() / 0.4F) + (int) (infection.getAmount() / 2) + 1;

            for (int i = 0; i < numCubes; i++) {
                poseStack.pushPose();
                float blockX = random.nextFloat() * living.getBbWidth() - living.getBbWidth() / 2f;
                float blockY = random.nextFloat() * living.getBbHeight();
                float blockZ = random.nextFloat() * living.getBbWidth() - living.getBbWidth() / 2f;
                poseStack.translate(blockX, blockY, blockZ);
                poseStack.scale(living.getBbWidth() / 2f, living.getBbWidth() / 2f, living.getBbWidth() / 2f);
                Vec3 center = new Vec3(living.getBbWidth() / 2, living.getBbHeight() / 2, living.getBbWidth() / 2);
                float pitch = ESUtil.positionToPitch(center, new Vec3(blockX, blockY, blockZ));
                float yaw = ESUtil.positionToYaw(center, new Vec3(blockX, blockY, blockZ));
                poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
                poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
                poseStack.translate(-0.5F, -0.5F, -0.5F);
                ESPlatform.INSTANCE.renderBlock(Minecraft.getInstance().getBlockRenderer(), poseStack, multiBufferSource, living.level(), random.nextBoolean() ? BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState() : BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState(), living.blockPosition(), seed);
                poseStack.popPose();
            }
            poseStack.popPose();
        }
    }
}
