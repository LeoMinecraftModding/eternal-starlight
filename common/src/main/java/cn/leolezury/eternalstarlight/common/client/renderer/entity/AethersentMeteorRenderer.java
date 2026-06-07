package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.AethersentMeteorModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.util.ModelPartPose;
import cn.leolezury.eternalstarlight.common.util.ModelSnapshot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class AethersentMeteorRenderer extends EntityRenderer<AethersentMeteor> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/aethersent_meteor.png");
	private static final int SNAPSHOT_LIFESPAN = 5;

	private final AethersentMeteorModel<AethersentMeteor> model;

	public AethersentMeteorRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new AethersentMeteorModel<>(context.bakeLayer(AethersentMeteorModel.LAYER_LOCATION));
	}

	@Override
	public void render(AethersentMeteor entity, float yaw, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
		stack.pushPose();
		float yRot = -Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
		float xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) - 90f;
		float bob = entity.tickCount + partialTicks;

		stack.scale(-1.0F, -1.0F, 1.0F);
		stack.translate(0.0F, -1.5F, 0.0F);

		this.model.prepareMobModel(entity, 0, 0, partialTicks);
		this.model.setupAnim(entity, 0, 0, bob, yRot, xRot);
		RenderType renderType = this.model.renderType(getTextureLocation(entity));
		VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
		this.model.renderToBuffer(stack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

		stack.popPose();

		if (!entity.isRemoved() && entity.getSize() >= 10) {
			Vec3 currentPos = new Vec3(
				Mth.lerp(partialTicks, entity.xo, entity.getX()),
				Mth.lerp(partialTicks, entity.yo, entity.getY()),
				Mth.lerp(partialTicks, entity.zo, entity.getZ())
			);
			float currentTick = entity.tickCount + partialTicks;
			if (entity.trailSnapshots.isEmpty() || currentTick - entity.lastTrailTick > 2) {
				Map<String, ModelPartPose> snapshot = ESModelUtil.saveModelSnapshot(model.allPartNames, model::getAnyDescendantWithName);
				entity.trailSnapshots.addFirst(Pair.of(currentPos, new ModelSnapshot(0, 0, currentTick, snapshot)));
				entity.lastTrailTick = currentTick;
			}
			entity.trailSnapshots.removeIf(p -> currentTick - p.getSecond().timestamp() > SNAPSHOT_LIFESPAN);
			while (entity.trailSnapshots.size() > 32) {
				entity.trailSnapshots.removeLast();
			}
			model.root.getAllParts().forEach(ModelPart::resetPose);
			for (int i = 0; i < entity.trailSnapshots.size(); i++) {
				stack.pushPose();
				Vec3 trailPos = entity.trailSnapshots.get(i).getFirst();
				ModelSnapshot snapshot = entity.trailSnapshots.get(i).getSecond();
				ESModelUtil.loadPoseFromSnapshot(snapshot.poses(), model::getAnyDescendantWithName);
				stack.translate(trailPos.x - currentPos.x, trailPos.y - currentPos.y, trailPos.z - currentPos.z);
				model.alphaFactor = (1 - Mth.clamp(currentTick - snapshot.timestamp(), 0, SNAPSHOT_LIFESPAN) / SNAPSHOT_LIFESPAN) * 0.3F;
				stack.scale(-1.0F, -1.0F, 1.0F);
				stack.translate(0.0F, -1.5F, 0.0F);
				renderType = RenderType.entityTranslucentEmissive(getTextureLocation(entity));
				vertexConsumer = bufferSource.getBuffer(renderType);
				model.renderToBuffer(stack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
				stack.popPose();
			}
			model.alphaFactor = 1;
		}

		super.render(entity, yaw, partialTicks, stack, bufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(AethersentMeteor meteor) {
		return ENTITY_TEXTURE;
	}
}
