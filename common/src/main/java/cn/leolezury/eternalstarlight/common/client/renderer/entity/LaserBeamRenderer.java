package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.attack.ray.RayAttack;
import cn.leolezury.eternalstarlight.common.entity.interfaces.RayAttackUser;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public abstract class LaserBeamRenderer<T extends RayAttack> extends EntityRenderer<T> {
	public LaserBeamRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	public float getTextureWidth() {
		return 32;
	}

	public float getBeamRadius() {
		return 1;
	}

	private boolean playerCast = false;

	@Override
	public void render(T laserBeam, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
		if (laserBeam.tickCount < 5) return;

		double entityX = Mth.lerp(partialTicks, laserBeam.xo, laserBeam.getX());
		double entityY = Mth.lerp(partialTicks, laserBeam.yo, laserBeam.getY());
		double entityZ = Mth.lerp(partialTicks, laserBeam.zo, laserBeam.getZ());

		stack.pushPose();
		if (laserBeam.getCaster().isPresent()) {
			Entity caster = laserBeam.getCaster().get();
			double posX = Mth.lerp(partialTicks, caster.xo, caster.getX());
			double posY = Mth.lerp(partialTicks, caster.yo, caster.getY());
			double posZ = Mth.lerp(partialTicks, caster.zo, caster.getZ());
			Vec3 pos = laserBeam.getPositionForCaster(caster, new Vec3(posX, posY, posZ));
			posX = pos.x;
			posY = pos.y;
			posZ = pos.z;
			stack.translate(posX - entityX, posY - entityY, posZ - entityZ);
		}
		playerCast = Minecraft.getInstance().options.getCameraType().isFirstPerson() && laserBeam.getCaster().isPresent() && Minecraft.getInstance().player != null && laserBeam.getCaster().get().getUUID().equals(Minecraft.getInstance().player.getUUID());

		if (playerCast && Minecraft.getInstance().getCameraEntity() != null) {
			Vec3 offset = ESMathUtil.rotationToPosition(0.5f, -Minecraft.getInstance().getCameraEntity().getXRot() - 90, Minecraft.getInstance().getCameraEntity().getYHeadRot() + 90);
			stack.translate(offset.x, offset.y, offset.z);
		}

		float yaw = Mth.lerp(partialTicks, laserBeam.prevYaw, laserBeam.getYaw()) * Mth.DEG_TO_RAD;
		float pitch = Mth.lerp(partialTicks, laserBeam.prevPitch, laserBeam.getPitch()) * Mth.DEG_TO_RAD;

		if (laserBeam.getCaster().isPresent()
			&& ((laserBeam.getCaster().get() instanceof RayAttackUser user && user.isRayFollowingHeadRotation())
			|| !(laserBeam.getCaster().get() instanceof RayAttackUser))
			&& laserBeam.getCaster().get() instanceof LivingEntity living) {
			yaw = (living.getViewYRot(partialTicks) + 90) * Mth.DEG_TO_RAD;
			pitch = -living.getViewXRot(partialTicks) * Mth.DEG_TO_RAD;
		}

		float length = laserBeam.getLength();

		VertexConsumer consumer = ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.entityGlow(getTextureLocation(laserBeam)));

		renderBeam(length, yaw, pitch, laserBeam.tickCount + partialTicks, stack, consumer, packedLight);

		stack.popPose();
	}

	private void renderBeamPart(float length, float tickCount, PoseStack stack, VertexConsumer consumer, int packedLight) {
		PoseStack.Pose pose = stack.last();
		float factor = (float) Math.sin(tickCount);
		float xOffset = (tickCount * 0.2f) % getTextureWidth();
		vertex(pose, consumer, -getBeamRadius() * 0.8f - factor * getBeamRadius() * 0.2f, 0, 0, -xOffset, 0, 1, packedLight);
		vertex(pose, consumer, -getBeamRadius() * 0.8f - factor * getBeamRadius() * 0.2f, length, 0, 1 - xOffset, 0, 1, packedLight);
		vertex(pose, consumer, getBeamRadius() * 0.8f + factor * getBeamRadius() * 0.2f, length, 0, 1 - xOffset, 1, 1, packedLight);
		vertex(pose, consumer, getBeamRadius() * 0.8f + factor * getBeamRadius() * 0.2f, 0, 0, -xOffset, 1, 1, packedLight);
	}

	private void renderBeam(float length, float yaw, float pitch, float tickCount, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight) {
		poseStack.pushPose();
		poseStack.mulPose(new Quaternionf().rotationX(90 * Mth.DEG_TO_RAD));
		poseStack.mulPose(new Quaternionf().rotationZ(yaw - 90 * Mth.DEG_TO_RAD));
		poseStack.mulPose(new Quaternionf().rotationX(-pitch));

		poseStack.pushPose();
		if (!playerCast) {
			poseStack.mulPose(new Quaternionf().rotationY(tickCount * Mth.DEG_TO_RAD));
		}
		renderBeamPart(length, tickCount, poseStack, vertexConsumer, packedLight);
		poseStack.popPose();

		if (!playerCast) {
			for (int i = 1; i < 3; i++) {
				poseStack.pushPose();
				poseStack.mulPose(new Quaternionf().rotationY((i * 30) * Mth.DEG_TO_RAD));

				renderBeamPart(length, tickCount, poseStack, vertexConsumer, packedLight);
				poseStack.popPose();
			}
		}

		poseStack.popPose();
	}

	public void vertex(PoseStack.Pose pose, VertexConsumer consumer, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLight) {
		consumer.addVertex(pose, offsetX, offsetY, offsetZ).setColor(1, 1, 1, 1 * alpha).setUv(textureX, textureY).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, 0.0F, 1.0F, 0.0F);
	}
}
