package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.misc.TearBombMinecart;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class TearBombMinecartRenderer extends MinecartRenderer<TearBombMinecart> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("tear_bomb_minecart"), "main");
	private final BlockRenderDispatcher blockRenderer;

	public TearBombMinecartRenderer(EntityRendererProvider.Context context) {
		super(context, LAYER_LOCATION);
		this.blockRenderer = context.getBlockRenderDispatcher();
	}

	@Override
	protected void renderMinecartContents(TearBombMinecart entity, float partialTicks, BlockState state, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		int fuse = entity.getFuse();
		if (fuse > -1 && fuse - partialTicks + 1.0F < 10.0F) {
			float f = 1.0F - (fuse - partialTicks + 1.0F) / 10.0F;
			f = Mth.clamp(f, 0.0F, 1.0F);
			f *= f;
			f *= f;
			float f1 = 1.0F + f * 0.3F;
			poseStack.scale(f1, f1, f1);
		}

		TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, state, poseStack, buffer, packedLight, fuse > -1 && fuse / 5 % 2 == 0);
	}
}
