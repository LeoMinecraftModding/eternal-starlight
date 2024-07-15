package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.LunarVineBlock;
import cn.leolezury.eternalstarlight.common.block.entity.LunarVineBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class LunarVineRenderer implements BlockEntityRenderer<LunarVineBlockEntity> {
	private final VineModel vineModel;
	private final FlowerModel flowerModel;

	private static final ResourceLocation VINE_TEXTURE = EternalStarlight.id("textures/entity/block_lunar_vine.png");
	private static final ResourceLocation FLOWER_TEXTURE = EternalStarlight.id("textures/entity/block_lunar_vine_flower.png");

	public LunarVineRenderer(BlockEntityRendererProvider.Context context) {
		this.vineModel = new VineModel(context.bakeLayer(VineModel.LAYER_LOCATION));
		this.flowerModel = new FlowerModel(context.bakeLayer(FlowerModel.LAYER_LOCATION));
	}

	@Override
	public void render(LunarVineBlockEntity vine, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay) {
		stack.pushPose();
		stack.scale(-1.0F, -1.0F, 1.0F);
		stack.translate(-0.5F, -1.5F, 0.5F);
		stack.mulPose(Axis.YP.rotationDegrees(vine.getBlockState().getValue(LunarVineBlock.FACING).toYRot()));
		if (vine.getLevel() != null) {
			BlockPos attachPos = vine.getBlockPos().relative(vine.getBlockState().getValue(LunarVineBlock.FACING));
			boolean upperSturdy = vine.getLevel().getBlockState(attachPos.above()).isFaceSturdy(vine.getLevel(), attachPos.above(), vine.getBlockState().getValue(LunarVineBlock.FACING).getOpposite());
			boolean lowerSturdy = vine.getLevel().getBlockState(attachPos.below()).isFaceSturdy(vine.getLevel(), attachPos.below(), vine.getBlockState().getValue(LunarVineBlock.FACING).getOpposite());
			this.vineModel.applyVisibility(upperSturdy && vine.getLevel().isEmptyBlock(vine.getBlockPos().above()), lowerSturdy && vine.getLevel().isEmptyBlock(vine.getBlockPos().below()));
		}
		this.vineModel.renderToBuffer(stack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(VINE_TEXTURE)), light, overlay);
		for (LunarVineBlockEntity.Flower flower : vine.getOrCreateFlowers()) {
			stack.pushPose();
			stack.translate(flower.pos().x, flower.pos().y, flower.pos().z);
			this.flowerModel.root.xRot = flower.xRot() * Mth.DEG_TO_RAD;
			this.flowerModel.root.yRot = flower.yRot() * Mth.DEG_TO_RAD;
			this.flowerModel.renderToBuffer(stack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(FLOWER_TEXTURE)), light, overlay);
			stack.popPose();
		}
		stack.popPose();
	}

	public static final class VineModel extends Model {
		public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("block_lunar_vine"), "main");

		public final ModelPart root;
		public final ModelPart upperVine;
		public final ModelPart lowerVine;

		public VineModel(ModelPart root) {
			super(RenderType::entityCutoutNoCull);
			this.root = root.getChild("root");
			this.upperVine = root.getChild("root").getChild("upper_vine");
			this.lowerVine = root.getChild("root").getChild("lower_vine");
		}

		public static LayerDefinition createLayer() {
			MeshDefinition meshdefinition = new MeshDefinition();
			PartDefinition partdefinition = meshdefinition.getRoot();

			PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

			root.addOrReplaceChild("vine1", CubeListBuilder.create().texOffs(0, 21).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 8.0F, -0.2618F, 0.0F, 0.0F));

			root.addOrReplaceChild("vine2", CubeListBuilder.create().texOffs(0, 14).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, 8.0F, -0.2618F, 0.0F, 0.0F));

			root.addOrReplaceChild("vine3", CubeListBuilder.create().texOffs(0, 7).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 8.0F, -0.2618F, 0.0F, 0.0F));

			root.addOrReplaceChild("vine4", CubeListBuilder.create().texOffs(32, 0).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 8.0F, -0.0436F, 0.0F, 0.0F));

			root.addOrReplaceChild("upper_vine", CubeListBuilder.create().texOffs(0, 28).addBox(-8.0F, -3.0F, 0.0F, 16.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, 8.0F, -0.0436F, 0.0F, 0.0F));

			root.addOrReplaceChild("lower_vine", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, -0.0436F, 0.0F, 0.0F));

			return LayerDefinition.create(meshdefinition, 64, 64);
		}

		public void applyVisibility(boolean upper, boolean lower) {
			this.upperVine.visible = upper;
			this.lowerVine.visible = lower;
		}

		public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay, int color) {
			this.root.render(stack, consumer, light, overlay, color);
		}
	}

	public static final class FlowerModel extends Model {
		public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("block_lunar_vine_flower"), "main");

		public final ModelPart root;

		public FlowerModel(ModelPart root) {
			super(RenderType::entityCutoutNoCull);
			this.root = root.getChild("root");
		}

		public static LayerDefinition createLayer() {
			MeshDefinition meshdefinition = new MeshDefinition();
			PartDefinition partdefinition = meshdefinition.getRoot();

			PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

			PartDefinition stem = root.addOrReplaceChild("stem", CubeListBuilder.create().texOffs(4, 2).addBox(0.0F, -5.0F, -0.5F, 0.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

			stem.addOrReplaceChild("stem_rotated", CubeListBuilder.create().texOffs(4, 2).addBox(0.0F, -5.0F, -0.5F, 0.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

			PartDefinition petal1 = root.addOrReplaceChild("petal1", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -6.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

			petal1.addOrReplaceChild("upper_petal", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

			PartDefinition petal2_rotated = root.addOrReplaceChild("petal2_rotated", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

			PartDefinition petal2 = petal2_rotated.addOrReplaceChild("petal2", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

			petal2.addOrReplaceChild("upper_petal2", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

			PartDefinition petal3_rotated = root.addOrReplaceChild("petal3_rotated", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

			PartDefinition petal3 = petal3_rotated.addOrReplaceChild("petal3", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

			petal3.addOrReplaceChild("upper_petal3", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

			PartDefinition petal4_rotated = root.addOrReplaceChild("petal4_rotated", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

			PartDefinition petal4 = petal4_rotated.addOrReplaceChild("petal4", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

			petal4.addOrReplaceChild("upper_petal4", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

			return LayerDefinition.create(meshdefinition, 16, 16);
		}

		public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay, int color) {
			this.root.render(stack, consumer, light, overlay, color);
		}
	}
}
