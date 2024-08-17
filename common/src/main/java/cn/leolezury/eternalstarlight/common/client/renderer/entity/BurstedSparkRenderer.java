package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.entity.projectile.BurstedSpark;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BurstedSparkRenderer<T extends BurstedSpark> extends EntityRenderer<T> {
	private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
	private final float scale;
	private final boolean fullBright;

	public BurstedSparkRenderer(EntityRendererProvider.Context context, float f, boolean bl) {
		super(context);
		this.scale = f;
		this.fullBright = bl;
	}

	public BurstedSparkRenderer(EntityRendererProvider.Context context) {
		this(context, 1.0F, false);
	}

	protected int getBlockLightLevel(T entity, BlockPos blockPos) {
		return this.fullBright ? 15 : super.getBlockLightLevel(entity, blockPos);
	}

	public void render(T entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
		if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25)) {
			poseStack.pushPose();
			poseStack.scale(this.scale, this.scale, this.scale);
			poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
			poseStack.popPose();
			super.render(entity, f, g, poseStack, multiBufferSource, i);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
