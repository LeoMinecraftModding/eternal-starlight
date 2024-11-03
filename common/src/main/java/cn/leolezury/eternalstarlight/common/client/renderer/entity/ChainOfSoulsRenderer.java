package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.projectile.ChainOfSouls;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class ChainOfSoulsRenderer extends EntityRenderer<ChainOfSouls> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/chain_of_souls.png");

	public ChainOfSoulsRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(ChainOfSouls chain, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
		Player player = chain.getPlayerOwner();
		if (player != null) {
			stack.pushPose();
			float attackAnim = player.getAttackAnim(partialTicks);
			Entity target = chain.level().getEntity(chain.getTargetId());
			Vec3 handPos = getPlayerHandPos(player, Mth.sin(Mth.sqrt(attackAnim) * Mth.PI), partialTicks);
			Vec3 endPos = new Vec3(Mth.lerp(partialTicks, chain.xo, chain.getX()), Mth.lerp(partialTicks, chain.yo, chain.getY()), Mth.lerp(partialTicks, chain.zo, chain.getZ()));
			if (target != null) {
				Vec3 targetPos = new Vec3(Mth.lerp(partialTicks, target.xo, target.getX()), Mth.lerp(partialTicks, target.yo, target.getY()) + target.getBbHeight() / 2, Mth.lerp(partialTicks, target.zo, target.getZ()));
				Vec3 diff = targetPos.subtract(endPos);
				stack.translate(diff.x(), diff.y(), diff.z());
				endPos = targetPos;
			}
			Vec3 offset = handPos.subtract(endPos);
			float length = (float) offset.length();
			float yRot = ESMathUtil.positionToYaw(offset);
			float xRot = ESMathUtil.positionToPitch(offset);
			stack.mulPose(new Quaternionf().rotationX(90 * Mth.DEG_TO_RAD));
			stack.mulPose(new Quaternionf().rotationZ((yRot - 90) * Mth.DEG_TO_RAD));
			stack.mulPose(new Quaternionf().rotationX(-xRot * Mth.DEG_TO_RAD));
			VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(ENTITY_TEXTURE));
			Matrix4f pose = stack.last().pose();

			vertexConsumer.addVertex(pose, -0.2f, 0, 0).setColor(-1).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, 0.2f, 0, 0).setColor(-1).setUv(0.5f, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, 0.2f, length, 0).setColor(-1).setUv(0.5f, length / 0.8f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, -0.2f, length, 0).setColor(-1).setUv(0, length / 0.8f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);

			vertexConsumer.addVertex(pose, 0, 0, -0.2f).setColor(-1).setUv(0.5f, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, 0, 0, 0.2f).setColor(-1).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, 0, length, 0.2f).setColor(-1).setUv(1, length / 0.8f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);
			vertexConsumer.addVertex(pose, 0, length, -0.2f).setColor(-1).setUv(0.5f, length / 0.8f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(ClientHandlers.FULL_BRIGHT).setNormal(0.0F, 1.0F, 0.0F);

			stack.popPose();
		}
		super.render(chain, yaw, partialTicks, stack, buffer, light);
	}

	private Vec3 getPlayerHandPos(Player player, float attackAnim, float partialTicks) {
		int arm = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
		ItemStack itemStack = player.getMainHandItem();
		if (!itemStack.is(ESItems.CHAIN_OF_SOULS.get())) {
			arm = -arm;
		}
		if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
			double fovFactor = 960.0 / (double) this.entityRenderDispatcher.options.fov().get();
			float xRotFactor = player.getViewXRot(partialTicks) / 750f;
			Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float) arm * (0.85f + xRotFactor), -0.1f).scale(fovFactor).yRot(attackAnim * 0.5f).xRot(-attackAnim * 0.7f);
			return player.getEyePosition(partialTicks).add(vec3);
		} else {
			float f = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * (float) (Math.PI / 180.0);
			double d0 = Mth.sin(f);
			double d1 = Mth.cos(f);
			float f1 = player.getScale();
			double d2 = arm * 0.35 * (double) f1;
			double d3 = 0.8 * (double) f1;
			float f2 = player.isCrouching() ? -0.1875F : 0.0F;
			return player.getEyePosition(partialTicks).add(-d1 * d2 - d0 * d3, (double) f2 - 0.45 * (double) f1, -d0 * d2 + d1 * d3);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(ChainOfSouls chain) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
