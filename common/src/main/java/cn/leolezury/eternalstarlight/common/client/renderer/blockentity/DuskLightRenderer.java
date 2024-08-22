package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.DuskLightBlockEntity;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class DuskLightRenderer implements BlockEntityRenderer<DuskLightBlockEntity> {
	public static final ResourceLocation DUSK_BEAM_TEXTURE = EternalStarlight.id("textures/entity/dusk_beam.png");

	public DuskLightRenderer(BlockEntityRendererProvider.Context context) {

	}

	@Override
	public void render(DuskLightBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		for (Direction direction : Direction.values()) {
			float progress = Mth.lerp(partialTicks, blockEntity.getOldBeamProgresses().getOrDefault(direction, 0), blockEntity.getBeamProgresses().getOrDefault(direction, 0));
			Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
			/*float yaw = camera.getYRot() + 90;
			float pitch = -camera.getXRot();
			Vec3 sight = ESMathUtil.rotationToPosition(1, pitch, yaw);*/
			Vec3 sight = camera.getPosition().subtract(blockEntity.getBlockPos().getCenter());
			Vec3 start = new Vec3(0.5, 0.5, 0.5);
			Vec3 end = start.add(new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ()).scale(progress * DuskLightBlockEntity.MAX_LENGTH));
			Vec3 sideOffset = end.subtract(start).cross(sight).normalize().scale(0.25);
			PoseStack.Pose pose = poseStack.last();
			VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(DUSK_BEAM_TEXTURE));
			vertexConsumer.addVertex(pose, start.add(sideOffset).toVector3f()).setColor(-1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, start.add(sideOffset.scale(-1)).toVector3f()).setColor(-1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, end.add(sideOffset.scale(-1)).toVector3f()).setColor(-1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, end.add(sideOffset).toVector3f()).setColor(-1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		}
	}

	@Override
	public boolean shouldRenderOffScreen(DuskLightBlockEntity blockEntity) {
		return true;
	}

	@Override
	public boolean shouldRender(DuskLightBlockEntity blockEntity, Vec3 vec3) {
		return true;
	}
}
