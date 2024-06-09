package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class AethersentMeteorRenderer extends EntityRenderer<AethersentMeteor> {
    private final BlockRenderDispatcher dispatcher;

    public AethersentMeteorRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    public void render(AethersentMeteor meteor, float yaw, float delta, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
        BlockState blockstate = ESBlocks.RAW_AETHERSENT_BLOCK.get().defaultBlockState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = meteor.level();
            if (blockstate != level.getBlockState(meteor.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                stack.pushPose();
                BlockPos blockpos = BlockPos.containing(meteor.getX(), meteor.getBoundingBox().maxY, meteor.getZ());
                float scale = meteor.getSize() / 10f;
                stack.translate(-0.5D * scale, 0.0D, -0.5D * scale);
                stack.scale(scale, scale, scale);
                ESPlatform.INSTANCE.renderBlock(dispatcher, stack, bufferSource, level, blockstate, blockpos, blockstate.getSeed(meteor.blockPosition()));
                stack.popPose();
                super.render(meteor, yaw, delta, stack, bufferSource, packedLight);
            }
        }
    }

    public ResourceLocation getTextureLocation(AethersentMeteor meteor) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
