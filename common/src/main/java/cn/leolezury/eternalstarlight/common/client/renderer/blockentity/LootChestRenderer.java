package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.LootChestBlock;
import cn.leolezury.eternalstarlight.common.block.entity.LootChestBlockEntity;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.LootChestAnimation;
import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

public class LootChestRenderer implements BlockEntityRenderer<LootChestBlockEntity> {
	private final LootChestModel chestModel;

	private static final ResourceLocation CHEST_TEXTURE = EternalStarlight.id("textures/entity/loot_chest/loot_chest.png");
	private static final ResourceLocation CHEST_OUTLINE_TEXTURE = EternalStarlight.id("textures/entity/loot_chest/loot_chest_outline.png");

	public LootChestRenderer(BlockEntityRendererProvider.Context context) {
		this.chestModel = new LootChestModel(context.bakeLayer(LootChestModel.LAYER_LOCATION));
	}

	@Override
	public void render(LootChestBlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay) {
		stack.pushPose();
		stack.scale(-1.0F, -1.0F, 1.0F);
		stack.translate(-0.5F, -1.5F, 0.5F);
		stack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockState().getValue(LootChestBlock.FACING).toYRot() + 180));
		this.chestModel.root().getAllParts().forEach(ModelPart::resetPose);
		this.chestModel.animate(blockEntity.openAnimationState, LootChestAnimation.OPEN, blockEntity.clientTickCount + partialTicks);
		this.chestModel.animate(blockEntity.closeAnimationState, LootChestAnimation.CLOSE, blockEntity.clientTickCount + partialTicks);
		if (blockEntity.isEjecting() && !blockEntity.openAnimationState.isStarted() && !blockEntity.closeAnimationState.isStarted()) {
			this.chestModel.lid.xRot = -Mth.HALF_PI;
		}
		this.chestModel.renderToBuffer(stack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(CHEST_TEXTURE)), light, overlay, FastColor.ARGB32.opaque(blockEntity.getColor()));
		this.chestModel.renderToBuffer(stack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(CHEST_OUTLINE_TEXTURE)), light, overlay, FastColor.ARGB32.opaque(blockEntity.getOutlineColor()));
		stack.popPose();
		float flashAnimation = Math.max((blockEntity.clientTickCount + partialTicks) - blockEntity.flashStartTickCount, 0);
		VertexConsumer consumer = bufferSource.getBuffer(ESRenderType.DRAGON_RAYS_QUADS);
		PoseStack.Pose pose = stack.last();
		if (flashAnimation < 10) {
			float flashProgress = Easing.IN_OUT_SINE.calculate(1 - Math.abs(flashAnimation - 5) / 5);
			float flashDistFromCenter = Mth.lerp(flashProgress, flashAnimation < 5 ? 0.2f : 0.25f, 0.15f);
			float flashHeight = 0.6f * flashProgress;

			int baseColor = FastColor.ARGB32.color(Math.round(Mth.lerp(flashProgress, 0.5F, 1.0F) * 255), blockEntity.rareFlash ? blockEntity.getRareFlashColor() : blockEntity.getFlashColor());
			int topColor = FastColor.ARGB32.color(0, blockEntity.rareFlash ? blockEntity.getRareFlashColor() : blockEntity.getFlashColor());

			consumer.addVertex(pose, 0.5f - flashDistFromCenter, 0.5625f, 0.5f - flashDistFromCenter).setColor(baseColor);
			consumer.addVertex(pose, 0.5f - flashDistFromCenter, 0.5625f, 0.5f + flashDistFromCenter).setColor(baseColor);
			consumer.addVertex(pose, 0.5f - flashDistFromCenter, 0.5625f + flashHeight, 0.5f + flashDistFromCenter).setColor(topColor);
			consumer.addVertex(pose, 0.5f - flashDistFromCenter, 0.5625f + flashHeight, 0.5f - flashDistFromCenter).setColor(topColor);

			consumer.addVertex(pose, 0.5f - flashDistFromCenter, 0.5625f, 0.5f - flashDistFromCenter).setColor(baseColor);
			consumer.addVertex(pose, 0.5f + flashDistFromCenter, 0.5625f, 0.5f - flashDistFromCenter).setColor(baseColor);
			consumer.addVertex(pose, 0.5f + flashDistFromCenter, 0.5625f + flashHeight, 0.5f - flashDistFromCenter).setColor(topColor);
			consumer.addVertex(pose, 0.5f - flashDistFromCenter, 0.5625f + flashHeight, 0.5f - flashDistFromCenter).setColor(topColor);

			consumer.addVertex(pose, 0.5f + flashDistFromCenter, 0.5625f, 0.5f - flashDistFromCenter).setColor(baseColor);
			consumer.addVertex(pose, 0.5f + flashDistFromCenter, 0.5625f, 0.5f + flashDistFromCenter).setColor(baseColor);
			consumer.addVertex(pose, 0.5f + flashDistFromCenter, 0.5625f + flashHeight, 0.5f + flashDistFromCenter).setColor(topColor);
			consumer.addVertex(pose, 0.5f + flashDistFromCenter, 0.5625f + flashHeight, 0.5f - flashDistFromCenter).setColor(topColor);

			consumer.addVertex(pose, 0.5f - flashDistFromCenter, 0.5625f, 0.5f + flashDistFromCenter).setColor(baseColor);
			consumer.addVertex(pose, 0.5f + flashDistFromCenter, 0.5625f, 0.5f + flashDistFromCenter).setColor(baseColor);
			consumer.addVertex(pose, 0.5f + flashDistFromCenter, 0.5625f + flashHeight, 0.5f + flashDistFromCenter).setColor(topColor);
			consumer.addVertex(pose, 0.5f - flashDistFromCenter, 0.5625f + flashHeight, 0.5f + flashDistFromCenter).setColor(topColor);
		}
	}

	public static final class LootChestModel extends Model implements AnimatedModel {
		public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("loot_chest"), "main");

		public final ModelPart root;
		public final ModelPart lid;

		public LootChestModel(ModelPart root) {
			super(RenderType::entityCutoutNoCull);
			this.root = root.getChild("root");
			this.lid = this.root.getChild("lid");
		}

		public static LayerDefinition createLayer() {
			MeshDefinition meshdefinition = new MeshDefinition();
			PartDefinition partdefinition = meshdefinition.getRoot();

			PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 21).addBox(-7.0F, -9.0F, -15.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(0, 43).addBox(-8.0F, -10.0F, -16.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 43).mirror().addBox(5.0F, -10.0F, -16.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 43).addBox(-8.0F, -10.0F, -3.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 43).mirror().addBox(5.0F, -10.0F, -3.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 8.0F));

			root.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.0F, -14.0F, 14.0F, 7.0F, 14.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -8.0F, -1.0F));

			return LayerDefinition.create(meshdefinition, 64, 64);
		}

		@Override
		public ModelPart root() {
			return root;
		}

		@Override
		public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay, int color) {
			this.root.render(stack, consumer, light, overlay, color);
		}
	}

	@Override
	public boolean shouldRenderOffScreen(LootChestBlockEntity blockEntity) {
		return true;
	}

	@Override
	public int getViewDistance() {
		return 256;
	}
}
