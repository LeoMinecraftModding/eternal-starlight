package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.effect.CrystallineInfectionEffect;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Unique
    private final static Material ABYSS_FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(EternalStarlight.MOD_ID,"block/abyss_fire_0"));
    @Unique
    private final static Material ABYSS_FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(EternalStarlight.MOD_ID,"block/abyss_fire_1"));
    @Shadow public abstract <T extends Entity> EntityRenderer<? super T> getRenderer(T entity);

    @Inject(method = "render", at = @At("RETURN"))
    private <E extends Entity> void es_render(E entity, double xOffset, double yOffset, double zOffset, float delta, float yRot, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, CallbackInfo ci) {
        if (entity instanceof LivingEntity living) {
            AttributeInstance armor = living.getAttribute(Attributes.ARMOR);
            if (armor == null) return;

            AttributeModifier infection = armor.getModifier(CrystallineInfectionEffect.ARMOR_MODIFIER_UUID);
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
                float pitch = ESMathUtil.positionToPitch(center, new Vec3(blockX, blockY, blockZ));
                float yaw = ESMathUtil.positionToYaw(center, new Vec3(blockX, blockY, blockZ));
                poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
                poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
                poseStack.translate(-0.5F, -0.5F, -0.5F);
                ESPlatform.INSTANCE.renderBlock(Minecraft.getInstance().getBlockRenderer(), poseStack, multiBufferSource, living.level(), random.nextBoolean() ? ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState() : ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState(), living.blockPosition(), seed);
                poseStack.popPose();
            }
            poseStack.popPose();
        }
    }

    @ModifyVariable(method = "renderFlame", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0), ordinal = 0)
    private TextureAtlasSprite modifyFlame0(TextureAtlasSprite value, PoseStack poseStack, MultiBufferSource multiBufferSource, Entity entity, Quaternionf quaternionf) {
        if (Minecraft.getInstance().player != null && ESBlockUtil.isEntityInBlock(Minecraft.getInstance().player, ESBlocks.ABYSS_FIRE.get())) {
            return ABYSS_FIRE_0.sprite();
        }
        return value;
    }

    @ModifyVariable(method = "renderFlame", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 1), ordinal = 1)
    private TextureAtlasSprite modifyFlame1(TextureAtlasSprite value, PoseStack poseStack, MultiBufferSource multiBufferSource, Entity entity, Quaternionf quaternionf) {
        if (Minecraft.getInstance().player != null && ESBlockUtil.isEntityInBlock(Minecraft.getInstance().player, ESBlocks.ABYSS_FIRE.get())) {
            return ABYSS_FIRE_1.sprite();
        }
        return value;
    }
}
