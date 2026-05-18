package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.entity.attack.Whip;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.util.Optional;

public abstract class WhipRenderer<T extends Whip> extends EntityRenderer<T> {
	public WhipRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(T entity, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
		stack.pushPose();
		translateAndRotate(entity, partialTicks, stack);
		renderWhip(entity, partialTicks, stack, buffer, light);
		stack.popPose();
		super.render(entity, yaw, partialTicks, stack, buffer, light);
	}

	public void translateAndRotate(T entity, float partialTicks, PoseStack stack) {
		Player player = entity.getPlayerOwner();
		if (player != null) {
			Vec3 handPos = getPlayerHandPos(player, partialTicks);
			Vec3 pos = new Vec3(
				Mth.lerp(partialTicks, entity.xo, entity.getX()),
				Mth.lerp(partialTicks, entity.yo, entity.getY()),
				Mth.lerp(partialTicks, entity.zo, entity.getZ())
			);
			stack.translate(handPos.x - pos.x, handPos.y - pos.y, handPos.z - pos.z);
			stack.mulPose(new Quaternionf().rotationX(90 * Mth.DEG_TO_RAD));
			stack.mulPose(new Quaternionf().rotationZ(Mth.lerp(partialTicks, player.yHeadRotO, player.yHeadRot) * Mth.DEG_TO_RAD));
			stack.mulPose(new Quaternionf().rotationX(player.getViewXRot(partialTicks) * Mth.DEG_TO_RAD));
			stack.scale(-1.0F, -1.0F, 1.0F);
			stack.translate(0.0F, -1.5F, 0.0F);
		}
	}

	public abstract void renderWhip(T entity, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light);

	private Vec3 getPlayerHandPos(Player player, float partialTicks) {
		int arm = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
		if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
			double fovFactor = 960.0 / (double) this.entityRenderDispatcher.options.fov().get();
			Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float) arm * 1.25f, -1.25f).scale(fovFactor);
			return player.getEyePosition(partialTicks).add(vec3);
		} else {
			Optional<Vec3> handPos = ESModelUtil.getThirdPersonPlayerHandPosition(player, entityRenderDispatcher, Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot), partialTicks, player.getMainArm(), new Vec3(0, 0.6, 0));
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
	public ResourceLocation getTextureLocation(T entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
