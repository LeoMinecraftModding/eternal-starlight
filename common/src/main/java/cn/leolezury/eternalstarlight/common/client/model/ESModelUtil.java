package cn.leolezury.eternalstarlight.common.client.model;

import cn.leolezury.eternalstarlight.common.util.ModelPartPose;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector4f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public class ESModelUtil {
	private static final MultiBufferSource DUMMY_BUFFER = new MultiBufferSource() {
		@Override
		public VertexConsumer getBuffer(RenderType renderType) {
			return new VertexConsumer() {
				@Override
				public VertexConsumer addVertex(float x, float y, float z) {
					return this;
				}

				@Override
				public VertexConsumer setColor(int red, int green, int blue, int alpha) {
					return this;
				}

				@Override
				public VertexConsumer setUv(float u, float v) {
					return this;
				}

				@Override
				public VertexConsumer setUv1(int u, int v) {
					return this;
				}

				@Override
				public VertexConsumer setUv2(int u, int v) {
					return this;
				}

				@Override
				public VertexConsumer setNormal(float normalX, float normalY, float normalZ) {
					return this;
				}
			};
		}
	};

	public static Vec3 getModelPartWorldPosition(Entity entity, float yaw, List<ModelPart> parts) {
		PoseStack stack = new PoseStack();
		stack.translate(entity.getX(), entity.getY(), entity.getZ());
		stack.mulPose(new Quaternionf().rotationY((-yaw + 180.0F) * Mth.DEG_TO_RAD));
		stack.scale(-1, -1, 1);
		stack.translate(0, -1.5f, 0);

		for (ModelPart part : parts) {
			part.translateAndRotate(stack);
		}

		Vector4f vec = new Vector4f(0, 0, 0, 1).mul(stack.last().pose());
		Vec3 pos = new Vec3(vec.x(), vec.y(), vec.z());
		Vec3 subtract = pos.subtract(entity.position());
		return entity.position().add(subtract.scale(entity instanceof LivingEntity living ? living.getScale() : 1));
	}

	public static Optional<Vec3> getThirdPersonPlayerHandPosition(Player player, EntityRenderDispatcher renderDispatcher, float yaw, float partialTick, HumanoidArm arm, Vec3 offset) {
		if (player instanceof AbstractClientPlayer clientPlayer && renderDispatcher.getRenderer(clientPlayer) instanceof PlayerRenderer renderer) {
			PoseStack stack = new PoseStack();
			renderer.render(clientPlayer, yaw, partialTick, stack, DUMMY_BUFFER, LightTexture.FULL_BRIGHT);
			PlayerModel<AbstractClientPlayer> model = renderer.getModel();

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
}
