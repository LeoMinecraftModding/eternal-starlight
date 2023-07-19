package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.entity.misc.AetherSentMeteor;
import cn.leolezury.eternalstarlight.entity.misc.SLFallingBlock;
import cn.leolezury.eternalstarlight.init.BlockInit;
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

public class AetherSentMeteorRenderer extends EntityRenderer<AetherSentMeteor> {
    private final BlockRenderDispatcher dispatcher;

    public AetherSentMeteorRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    public void render(AetherSentMeteor meteor, float yaw, float delta, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
        //TODO: aethersentium block
        BlockState blockstate = BlockInit.SWAMP_SILVER_BLOCK.get().defaultBlockState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = meteor.level();
            if (blockstate != level.getBlockState(meteor.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                stack.pushPose();
                BlockPos blockpos = BlockPos.containing(meteor.getX(), meteor.getBoundingBox().maxY, meteor.getZ());
                float scale = meteor.getSize() / 10f;
                stack.translate(-0.5D * scale, 0.0D, -0.5D * scale);
                stack.scale(scale, scale, scale);
                var model = this.dispatcher.getBlockModel(blockstate);
                for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(), net.minecraftforge.client.model.data.ModelData.EMPTY))
                    this.dispatcher.getModelRenderer().tesselateBlock(level, model, blockstate, blockpos, stack, bufferSource.getBuffer(renderType), false, RandomSource.create(), blockstate.getSeed(meteor.blockPosition()), OverlayTexture.NO_OVERLAY, net.minecraftforge.client.model.data.ModelData.EMPTY, renderType);
                stack.popPose();
                super.render(meteor, yaw, delta, stack, bufferSource, packedLight);
            }
        }
    }

    public ResourceLocation getTextureLocation(AetherSentMeteor meteor) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
