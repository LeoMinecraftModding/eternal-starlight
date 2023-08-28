package cn.leolezury.eternalstarlight.common.client.renderer;

import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
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
public class ESFallingBlockRenderer extends EntityRenderer<ESFallingBlock> {
    private final BlockRenderDispatcher dispatcher;

    public ESFallingBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    public void render(ESFallingBlock block, float yaw, float delta, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
        BlockState blockstate = block.getBlockState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = block.level();
            if (blockstate != level.getBlockState(block.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                stack.pushPose();
                BlockPos blockpos = BlockPos.containing(block.getX(), block.getBoundingBox().maxY, block.getZ());
                stack.translate(-0.5D, 0.0D, -0.5D);
                ESPlatform.INSTANCE.renderBlock(dispatcher, stack, bufferSource, level, blockstate, blockpos, blockstate.getSeed(block.getStartPos()));
                stack.popPose();
                super.render(block, yaw, delta, stack, bufferSource, packedLight);
            }
        }
    }

    public ResourceLocation getTextureLocation(ESFallingBlock block) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
