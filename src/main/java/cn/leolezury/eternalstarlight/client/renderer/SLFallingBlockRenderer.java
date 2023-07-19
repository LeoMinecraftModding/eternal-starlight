package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.entity.misc.SLFallingBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SLFallingBlockRenderer extends EntityRenderer<SLFallingBlock> {
    private final BlockRenderDispatcher dispatcher;

    public SLFallingBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    public void render(SLFallingBlock block, float yaw, float delta, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
        BlockState blockstate = block.getBlockState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = block.level();
            if (blockstate != level.getBlockState(block.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                stack.pushPose();
                BlockPos blockpos = BlockPos.containing(block.getX(), block.getBoundingBox().maxY, block.getZ());
                stack.translate(-0.5D, 0.0D, -0.5D);
                var model = this.dispatcher.getBlockModel(blockstate);
                for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(block.getStartPos())), net.minecraftforge.client.model.data.ModelData.EMPTY))
                    this.dispatcher.getModelRenderer().tesselateBlock(level, model, blockstate, blockpos, stack, bufferSource.getBuffer(renderType), false, RandomSource.create(), blockstate.getSeed(block.getStartPos()), OverlayTexture.NO_OVERLAY, net.minecraftforge.client.model.data.ModelData.EMPTY, renderType);
                stack.popPose();
                super.render(block, yaw, delta, stack, bufferSource, packedLight);
            }
        }
    }

    public ResourceLocation getTextureLocation(SLFallingBlock block) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
