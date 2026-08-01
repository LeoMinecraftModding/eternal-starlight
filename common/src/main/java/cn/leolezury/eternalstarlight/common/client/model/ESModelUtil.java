package cn.leolezury.eternalstarlight.common.client.model;

import cn.leolezury.eternalstarlight.common.util.ModelPartPose;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ESModelUtil {
	public static Vec3 getModelPartWorldPosition(Entity entity, Vec3 pos, float yaw, List<ModelPart> parts) {
		return pos.add(getModelPartOffsetPosition(entity, yaw, parts));
	}

	public static Vec3 getModelPartOffsetPosition(Entity entity, float yaw, List<ModelPart> parts) {
		PoseStack stack = new PoseStack();
		stack.mulPose(Axis.YP.rotationDegrees(180.0F - yaw));
		stack.scale(-1, -1, 1);
		stack.translate(0, -1.5f, 0);

		for (ModelPart part : parts) {
			part.translateAndRotate(stack);
		}

		Vector4f vec = new Vector4f(0, 0, 0, 1).mul(stack.last().pose());
		Vec3 pos = new Vec3(vec.x(), vec.y(), vec.z());
		return pos.scale(entity instanceof LivingEntity living ? living.getScale() : 1);
	}

	public static Optional<Vec3> getThirdPersonPlayerHandPosition(Player player, EntityRenderDispatcher renderDispatcher, float yaw, float partialTick, HumanoidArm arm, Vec3 offset) {
		if (player instanceof AbstractClientPlayer clientPlayer && renderDispatcher.getRenderer(clientPlayer) instanceof PlayerRenderer renderer) {
			PoseStack stack = new PoseStack();
			renderer.setModelProperties(clientPlayer);
			PlayerModel<AbstractClientPlayer> model = renderer.getModel();

			// animate the player model
			// copied from LivingEntityRenderer
			model.attackTime = clientPlayer.getAttackAnim(partialTick);
			boolean shouldSit = clientPlayer.isPassenger() && clientPlayer.getVehicle() != null;
			model.riding = shouldSit;
			float yBodyRot = Mth.rotLerp(partialTick, clientPlayer.yBodyRotO, clientPlayer.yBodyRot);
			float yHeadRot = Mth.rotLerp(partialTick, clientPlayer.yHeadRotO, clientPlayer.yHeadRot);
			float rotDiff = yHeadRot - yBodyRot;
			if (shouldSit && clientPlayer.getVehicle() instanceof LivingEntity livingentity) {
				yBodyRot = Mth.rotLerp(partialTick, livingentity.yBodyRotO, livingentity.yBodyRot);
				rotDiff = yHeadRot - yBodyRot;
				float diff = Mth.wrapDegrees(rotDiff);
				if (diff < -85.0F) {
					diff = -85.0F;
				}
				if (diff >= 85.0F) {
					diff = 85.0F;
				}
				yBodyRot = yHeadRot - diff;
				if (diff * diff > 2500.0F) {
					yBodyRot += diff * 0.2F;
				}
				rotDiff = yHeadRot - yBodyRot;
			}
			float xRot = Mth.lerp(partialTick, clientPlayer.xRotO, clientPlayer.getXRot());
			if (LivingEntityRenderer.isEntityUpsideDown(clientPlayer)) {
				xRot *= -1.0F;
				rotDiff *= -1.0F;
			}
			rotDiff = Mth.wrapDegrees(rotDiff);
			float age = clientPlayer.tickCount + partialTick;
			float walkSpeed = 0.0F;
			float walkPos = 0.0F;
			if (!shouldSit && clientPlayer.isAlive()) {
				walkSpeed = clientPlayer.walkAnimation.speed(partialTick);
				walkPos = clientPlayer.walkAnimation.position(partialTick);
				if (walkSpeed > 1.0F) {
					walkSpeed = 1.0F;
				}
			}
			model.prepareMobModel(clientPlayer, walkPos, walkSpeed, partialTick);
			model.setupAnim(clientPlayer, walkPos, walkSpeed, age, rotDiff, xRot);

			stack.translate(
				Mth.lerp(partialTick, player.xo, player.getX()),
				Mth.lerp(partialTick, player.yo, player.getY()),
				Mth.lerp(partialTick, player.zo, player.getZ())
			);
			stack.mulPose(new Quaternionf().rotationY((-yaw + 180.0F) * Mth.DEG_TO_RAD));
			stack.scale(-1, -1, 1);
			renderer.scale(clientPlayer, stack, partialTick);
			stack.translate(0, -1.5f, 0);
			model.translateToHand(arm, stack);

			Vector4f vec = new Vector4f((float) offset.x(), (float) offset.y(), (float) offset.z(), 1).mul(stack.last().pose());
			Vec3 pos = new Vec3(vec.x(), vec.y(), vec.z());
			Vec3 subtract = pos.subtract(player.position());
			return Optional.of(player.position().add(subtract.scale(player.getScale())));
		}
		return Optional.empty();
	}

	public static Stream<String> getAllPartNames(ModelPart root) {
		return Stream.concat(
			root.children.keySet().stream(),
			root.children.values().stream().flatMap(ESModelUtil::getAllPartNames)
		);
	}

	public static Map<String, ModelPartPose> saveModelSnapshot(List<String> allPartNames, Function<String, Optional<ModelPart>> getter) {
		Map<String, ModelPartPose> snapshot = new HashMap<>();
		for (String name : allPartNames) {
			getter.apply(name).ifPresent(part ->
				snapshot.put(name, new ModelPartPose(
					part.x, part.y, part.z,
					part.xRot, part.yRot, part.zRot,
					part.xScale, part.yScale, part.zScale,
					part.visible
				)));
		}
		return snapshot;
	}

	public static void loadPoseFromSnapshot(Map<String, ModelPartPose> snapshot, Function<String, Optional<ModelPart>> getter) {
		snapshot.forEach((name, pose) ->
			getter.apply(name).ifPresent(part -> {
				part.x = pose.x();
				part.y = pose.y();
				part.z = pose.z();
				part.xRot = pose.xRot();
				part.yRot = pose.yRot();
				part.zRot = pose.zRot();
				part.xScale = pose.xScale();
				part.yScale = pose.yScale();
				part.zScale = pose.zScale();
				part.visible = pose.visible();
			}));
	}

	public static void renderOutlineModelPart(ModelPart part, PoseStack poseStack, VertexConsumer vertexConsumer, float expansion, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		part.translateAndRotate(poseStack);
		for (ModelPart.Cube cube : part.cubes) {
			for (ModelPart.Polygon polygon : cube.polygons) {
				Vector3f normal = polygon.normal;
				ModelPart.Vertex[] vertices = polygon.vertices;
				if (vertices.length != 4) continue;
				int color = FastColor.ARGB32.color((int) (alpha * 255), (int) (red * 255), (int) (green * 255), (int) (blue * 255));
				float minX = Float.MAX_VALUE, maxX = Float.MIN_VALUE;
				float minY = Float.MAX_VALUE, maxY = Float.MIN_VALUE;
				float minZ = Float.MAX_VALUE, maxZ = Float.MIN_VALUE;
				for (ModelPart.Vertex v : vertices) {
					float vx = v.pos.x(), vy = v.pos.y(), vz = v.pos.z();
					if (vx < minX) minX = vx;
					if (vx > maxX) maxX = vx;
					if (vy < minY) minY = vy;
					if (vy > maxY) maxY = vy;
					if (vz < minZ) minZ = vz;
					if (vz > maxZ) maxZ = vz;
				}
				for (int i = 3; i >= 0; i--) {
					ModelPart.Vertex v = vertices[i];
					float vx = v.pos.x(), vy = v.pos.y(), vz = v.pos.z();
					float ox = vx / 16f + normal.x() * expansion;
					float oy = vy / 16f + normal.y() * expansion;
					float oz = vz / 16f + normal.z() * expansion;
					if (normal.x() == 0 && minX != maxX) {
						ox += (vx == minX ? -expansion : expansion);
					}
					if (normal.y() == 0 && minY != maxY) {
						oy += (vy == minY ? -expansion : expansion);
					}
					if (normal.z() == 0 && minZ != maxZ) {
						oz += (vz == minZ ? -expansion : expansion);
					}
					vertexConsumer.addVertex(poseStack.last(), ox, oy, oz)
						.setColor(color)
						.setUv(v.u, v.v)
						.setOverlay(OverlayTexture.NO_OVERLAY)
						.setLight(LightTexture.FULL_BRIGHT)
						.setNormal(poseStack.last(), normal.x(), normal.y(), normal.z());
				}
			}
		}
		for (ModelPart child : part.children.values()) {
			renderOutlineModelPart(child, poseStack, vertexConsumer, expansion, red, green, blue, alpha);
		}
		poseStack.popPose();
	}
}
