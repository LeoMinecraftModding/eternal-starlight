package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.entity.misc.TearBomb;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

// copied from TntRenderer

public class TearBombRenderer extends EntityRenderer<TearBomb> {
	private final BlockRenderDispatcher blockRenderer;

	public TearBombRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.5F;
		this.blockRenderer = context.getBlockRenderDispatcher();
	}

	@Override
	public void render(TearBomb bomb, float yaw, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
		stack.pushPose();
		stack.translate(0.0F, 0.5F, 0.0F);
		int fuse = bomb.getFuse();
		if (fuse - partialTicks + 1.0F < 10.0F) {
			float h = 1.0F - (fuse - partialTicks + 1.0F) / 10.0F;
			h = Mth.clamp(h, 0.0F, 1.0F);
			h *= h;
			h *= h;
			float k = 1.0F + h * 0.3F;
			stack.scale(k, k, k);
		}

		stack.mulPose(Axis.YP.rotationDegrees(-90.0F));
		stack.translate(-0.5F, -0.5F, 0.5F);
		stack.mulPose(Axis.YP.rotationDegrees(90.0F));
		TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, bomb.getBlockState(), stack, bufferSource, packedLight, fuse / 5 % 2 == 0);
		stack.popPose();
		super.render(bomb, yaw, partialTicks, stack, bufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(TearBomb tearBomb) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
