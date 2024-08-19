package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.DuskLightBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class DuskLightBlockEntityRenderer implements BlockEntityRenderer<DuskLightBlockEntity> {
	public static final ResourceLocation DUSK_BEAM = EternalStarlight.id("textures/misc/dusk_beam.png");
	public DuskLightBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		super();
	}

	@Override
	public void render(DuskLightBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
		if (blockEntity.lit) {
			Vec3 pOffset = new Vec3(0, 0, 0);
			float xOffset = 0.5f;
			float yOffset = 0.5f;
			float zOffset = 0.5f;
			float xRot = 0;
			float zRot = 0;
			float yRot = 0;
			switch (blockEntity.facing) {
				case 0: {
					pOffset = new Vec3(0.375, 0, 0);
					yRot = 90;
					xRot = 90;
					break;
				}
				case 1: {
					pOffset = new Vec3(0, 0, 0.375);
					zRot = 180;
					break;
				}
				case 2: {
					pOffset = new Vec3(-0.375, 0, 0);
					yRot = -90;
					break;
				}
				case 3: {
					pOffset = new Vec3(0, 0, -0.375);
					yRot = 180;
					break;
				}
				case 4: {
					pOffset = new Vec3(0, 0.375, 0);
					xRot = -90;
					break;
				}
				case 5: {
					pOffset = new Vec3(0, -0.375, 0);
					xRot = 90;
					break;
				}
				default: {}
			}
			EternalStarlight.getClientHelper().spawnManaCrystalItemParticles(ManaType.BLAZE, blockEntity.getBlockPos().getCenter().add(pOffset));

			var consumer = ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.duskRay(DUSK_BEAM));

			poseStack.translate(xOffset, yOffset, zOffset);
			poseStack.mulPose(new Quaternionf().rotateX(xRot * Mth.DEG_TO_RAD));
			poseStack.mulPose(new Quaternionf().rotateZ(zRot * Mth.DEG_TO_RAD));
			poseStack.mulPose(new Quaternionf().rotateY(yRot * Mth.DEG_TO_RAD));

			if (blockEntity.distance > 0) {
				renderBeamPart(blockEntity.distance, blockEntity.ticks, poseStack, consumer);
			}
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

	private void renderBeamPart(float length, float tickCount, PoseStack stack, VertexConsumer consumer) {
		PoseStack.Pose pose = stack.last();
		float xOffset = (tickCount * 0.2f) % 16;
		vertex(pose, consumer, -0.25f, 0, 0, -xOffset, 0, 1, ClientHandlers.FULL_BRIGHT);
		vertex(pose, consumer, -0.25f, 0, length + 0.5f, 1 - xOffset, 0, 1, ClientHandlers.FULL_BRIGHT);
		vertex(pose, consumer, 0.25f, 0, length + 0.5f, 1 - xOffset, 1, 1, ClientHandlers.FULL_BRIGHT);
		vertex(pose, consumer, 0.25f, 0, 0, -xOffset, 1, 1, ClientHandlers.FULL_BRIGHT);
		vertex(pose, consumer, 0, -0.25f, 0, -xOffset, 0, 1, ClientHandlers.FULL_BRIGHT);
		vertex(pose, consumer, 0, -0.25f, length + 0.5f, 1 - xOffset, 0, 1, ClientHandlers.FULL_BRIGHT);
		vertex(pose, consumer, 0, 0.25f, length + 0.5f, 1 - xOffset, 1, 1, ClientHandlers.FULL_BRIGHT);
		vertex(pose, consumer, 0, 0.25f, 0, -xOffset, 1, 1, ClientHandlers.FULL_BRIGHT);
	}

	public void vertex(PoseStack.Pose pose, VertexConsumer consumer, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLight) {
		consumer.addVertex(pose, offsetX, offsetY, offsetZ).setColor(1, 1, 1, 1 * alpha).setUv(textureX, textureY).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, 0.0F, 1.0F, 0.0F);
	}
}
