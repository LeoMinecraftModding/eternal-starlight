package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.entity.attack.CrystalCluster;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;

public class CrystalClusterRenderer extends EntityRenderer<CrystalCluster> {
	private final BlockRenderDispatcher dispatcher;

	public CrystalClusterRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.2F;
		this.dispatcher = context.getBlockRenderDispatcher();
	}

	@Override
	public void render(CrystalCluster cluster, float yaw, float delta, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
		BlockState state = cluster.clientCrystalType == 1 ? ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState() : ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState();
		if (state.getRenderShape() == RenderShape.MODEL) {
			Level level = cluster.level();
			if (state != level.getBlockState(cluster.blockPosition()) && state.getRenderShape() != RenderShape.INVISIBLE) {
				stack.pushPose();
				BlockPos pos = BlockPos.containing(cluster.getX(), cluster.getBoundingBox().maxY, cluster.getZ());
				float scale = Math.max(Mth.lerp(delta, cluster.oldClientScale, cluster.clientScale), 0);
				stack.mulPose(new Quaternionf().rotateY(-yaw * Mth.DEG_TO_RAD));
				stack.translate(-0.5 * scale, 0.0, -0.5 * scale);
				stack.scale(scale, scale, scale);
				ESClientPlatform.INSTANCE.renderBlock(dispatcher, stack, bufferSource, level, state, pos, state.getSeed(cluster.blockPosition()));
				stack.popPose();
				super.render(cluster, yaw, delta, stack, bufferSource, packedLight);
			}
		}
	}

	@Override
	public ResourceLocation getTextureLocation(CrystalCluster cluster) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
