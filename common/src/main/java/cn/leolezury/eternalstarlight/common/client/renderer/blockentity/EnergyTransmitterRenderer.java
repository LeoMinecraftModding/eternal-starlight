package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.EnergyTransmitterBlock;
import cn.leolezury.eternalstarlight.common.block.entity.EnergyTransmitterBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EnergyTransmitterRenderer<T extends EnergyTransmitterBlockEntity> implements BlockEntityRenderer<T> {
	public static final ResourceLocation ENERGY_TRANSMITTER_TEXTURE = EternalStarlight.id("textures/entity/energy_transmitter.png");

	public EnergyTransmitterRenderer(BlockEntityRendererProvider.Context context) {

	}

	@Override
	public void render(T blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		Vec3i inputOffset = blockEntity.getInputOffset();
		BlockState state = blockEntity.getBlockState();
		BlockState inputState = blockEntity.getInputState();
		Direction inputFacing = inputState.hasProperty(EnergyTransmitterBlock.FACING) ? inputState.getValue(EnergyTransmitterBlock.FACING) : Direction.UP;
		int receiverPower = state.hasProperty(EnergyTransmitterBlock.POWER) ? state.getValue(EnergyTransmitterBlock.POWER) : 0;
		if (!inputOffset.equals(Vec3i.ZERO) && receiverPower > 0) {
			Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
			Vec3 sight = camera.getPosition().subtract(blockEntity.getBlockPos().getCenter());
			Vec3 start = new Vec3(0.5, 0.5, 0.5).add(new Vec3(state.getValue(EnergyTransmitterBlock.FACING).step()).scale(-0.125));
			Vec3 end = Vec3.atCenterOf(inputOffset).add(new Vec3(inputFacing.step()).scale(-0.125));
			Vec3 sideOffset = end.subtract(start).cross(sight).normalize().scale(1.0 / 32.0);
			PoseStack.Pose pose = poseStack.last();
			VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(ENERGY_TRANSMITTER_TEXTURE));
			vertexConsumer.addVertex(pose, start.add(sideOffset).toVector3f()).setColor(-1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, start.add(sideOffset.scale(-1)).toVector3f()).setColor(-1).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, end.add(sideOffset.scale(-1)).toVector3f()).setColor(-1).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, end.add(sideOffset).toVector3f()).setColor(-1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(pose, 0.0F, 1.0F, 0.0F);
		}
	}

	@Override
	public boolean shouldRenderOffScreen(T blockEntity) {
		return true;
	}

	@Override
	public int getViewDistance() {
		return 256;
	}
}
