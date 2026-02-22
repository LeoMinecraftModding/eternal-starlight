package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import com.mojang.blaze3d.vertex.PoseStack;
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

public class ESFallingBlockRenderer extends EntityRenderer<ESFallingBlock> {
	private final BlockRenderDispatcher dispatcher;

	public ESFallingBlockRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.dispatcher = context.getBlockRenderDispatcher();
	}

	@Override
	public void render(ESFallingBlock block, float yaw, float delta, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
		BlockState state = block.getBlockState();
		if (state == null) return;
		if (state.getRenderShape() == RenderShape.MODEL) {
			Level level = block.level();
			if (state.getRenderShape() != RenderShape.INVISIBLE) {
				stack.pushPose();
				BlockPos pos = BlockPos.containing(block.getX(), block.getBoundingBox().maxY, block.getZ());
				stack.translate(-0.5, 0.0, -0.5);
				ESClientPlatform.INSTANCE.renderBlock(dispatcher, stack, bufferSource, level, state, pos, state.getSeed(block.getStartPos()));
				stack.popPose();
				super.render(block, yaw, delta, stack, bufferSource, packedLight);
			}
		}
	}

	@Override
	public ResourceLocation getTextureLocation(ESFallingBlock block) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
