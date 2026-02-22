package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.ChainOfSoulsModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.ChainOfSouls;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.Optional;

public class ChainOfSoulsRenderer extends EntityRenderer<ChainOfSouls> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/chain_of_souls.png");
	private static final ResourceLocation TIP_TEXTURE = EternalStarlight.id("textures/entity/chain_of_souls_tip.png");
	private final ChainOfSoulsModel<ChainOfSouls> model;

	public ChainOfSoulsRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new ChainOfSoulsModel<>(context.bakeLayer(ChainOfSoulsModel.LAYER_LOCATION));
	}

	@Override
	public void render(ChainOfSouls chain, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
		Player player = chain.getPlayerOwner();
		if (player != null) {
			stack.pushPose();
			Entity target = chain.getTarget();
			boolean attachedToBlock = chain.reachedTarget();
			Vec3 handPos = getPlayerHandPos(player, partialTicks);
			Vec3 endPos = new Vec3(Mth.lerp(partialTicks, chain.xo, chain.getX()), Mth.lerp(partialTicks, chain.yo, chain.getY()), Mth.lerp(partialTicks, chain.zo, chain.getZ()));
			if (chain.isValidTarget(target)) {
				Vec3 targetPos = new Vec3(Mth.lerp(partialTicks, target.xo, target.getX()), Mth.lerp(partialTicks, target.yo, target.getY()) + target.getBbHeight() / 2, Mth.lerp(partialTicks, target.zo, target.getZ()));
				Vec3 diff = targetPos.subtract(endPos);
				stack.translate(diff.x(), diff.y(), diff.z());
				endPos = targetPos;
				attachedToBlock = false;
			}
			Vec3 offset = handPos.subtract(endPos);
			float length = (float) offset.length();
			float yRot = ESMathUtil.positionToYaw(offset);
			float xRot = ESMathUtil.positionToPitch(offset);

			if (attachedToBlock) {
				stack.pushPose();
			}

			stack.mulPose(new Quaternionf().rotationX(90 * Mth.DEG_TO_RAD));
			stack.mulPose(new Quaternionf().rotationZ((yRot - 90) * Mth.DEG_TO_RAD));
			stack.mulPose(new Quaternionf().rotationX(-xRot * Mth.DEG_TO_RAD));
			VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(ENTITY_TEXTURE));
			Matrix4f pose = stack.last().pose();

			vertexConsumer.addVertex(pose, -0.5f, 0, 0).setColor(-1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, 0.5f, 0, 0).setColor(-1).setUv(0.5f, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, 0.5f, length, 0).setColor(-1).setUv(0.5f, length / 2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, -0.5f, length, 0).setColor(-1).setUv(0, length / 2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);

			vertexConsumer.addVertex(pose, 0, 0, -0.5f).setColor(-1).setUv(0.5f, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, 0, 0, 0.5f).setColor(-1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, 0, length, 0.5f).setColor(-1).setUv(1, length / 2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, 0, length, -0.5f).setColor(-1).setUv(0.5f, length / 2).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);

			if (attachedToBlock) {
				stack.popPose();
				stack.mulPose(chain.getTipDirection().getOpposite().getRotation());
			}

			stack.scale(-1.0F, 1.0F, 1.0F);
			stack.translate(0.0F, -1.5F, 0.0F);

			RenderType renderType = this.model.renderType(getTextureLocation(chain));
			vertexConsumer = buffer.getBuffer(renderType);
			this.model.renderToBuffer(stack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);

			stack.popPose();
		}
		super.render(chain, yaw, partialTicks, stack, buffer, light);
	}

	private Vec3 getPlayerHandPos(Player player, float partialTicks) {
		int arm = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
		ItemStack mainHandItem = player.getMainHandItem();
		if (!mainHandItem.is(ESItems.CHAIN_OF_SOULS.get())) {
			arm = -arm;
		}
		if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
			double fovFactor = 960.0 / (double) this.entityRenderDispatcher.options.fov().get();
			Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float) arm * 1.25f, -1.25f).scale(fovFactor);
			return player.getEyePosition(partialTicks).add(vec3);
		} else {
			Optional<Vec3> handPos = ESModelUtil.getThirdPersonPlayerHandPosition(player, entityRenderDispatcher, Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot), partialTicks, mainHandItem.is(ESItems.CHAIN_OF_SOULS.get()) ? player.getMainArm() : player.getMainArm().getOpposite(), new Vec3(0, 0.6, -0.15));
			if (handPos.isPresent()) {
				return handPos.get();
			}
			float yaw = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * (float) (Math.PI / 180.0);
			double sin = Mth.sin(yaw);
			double cos = Mth.cos(yaw);
			float playerScale = player.getScale();
			double sideOffset = arm * 0.35 * (double) playerScale;
			double forwardOffset = 0.35 * (double) playerScale;
			float crouchingFactor = player.isCrouching() ? -0.1875F : 0.0F;
			return player.getEyePosition(partialTicks).add(-cos * sideOffset - sin * forwardOffset, (double) crouchingFactor - 0.85 * (double) playerScale, -sin * sideOffset + cos * forwardOffset);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(ChainOfSouls chain) {
		return TIP_TEXTURE;
	}
}
