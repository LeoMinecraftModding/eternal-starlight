package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.SolarEggBlock;
import cn.leolezury.eternalstarlight.common.block.entity.SolarEggBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.world.level.block.state.BlockState;

public class SolarEggRenderer implements BlockEntityRenderer<SolarEggBlockEntity> {
	private static final ResourceLocation EGG_TEXTURE = EternalStarlight.id("textures/entity/solar_egg.png");

	private final SolarEggModel eggModel;

	public SolarEggRenderer(BlockEntityRendererProvider.Context context) {
		this.eggModel = new SolarEggModel(context.bakeLayer(SolarEggModel.LAYER_LOCATION));
	}

	@Override
	public void render(SolarEggBlockEntity egg, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay) {
		BlockState state = egg.getBlockState();
		if (state.getValue(SolarEggBlock.X_OFFSET) == 1 && state.getValue(SolarEggBlock.Y_OFFSET) == 0 && state.getValue(SolarEggBlock.Z_OFFSET) == 1) {
			stack.pushPose();
			stack.scale(-1.0F, -1.0F, 1.0F);
			stack.translate(-0.5F, -1.5F, 0.5F);
			this.eggModel.renderToBuffer(stack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(EGG_TEXTURE)), light, overlay);
			stack.popPose();
		}
	}

	public static final class SolarEggModel extends Model {
		public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("solar_egg"), "main");

		public final ModelPart root;

		public SolarEggModel(ModelPart root) {
			super(RenderType::entityCutoutNoCull);
			this.root = root.getChild("root");
		}

		public static LayerDefinition createLayer() {
			MeshDefinition meshdefinition = new MeshDefinition();
			PartDefinition partdefinition = meshdefinition.getRoot();

			partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-23.0F, -46.0F, -23.0F, 46.0F, 46.0F, 46.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

			return LayerDefinition.create(meshdefinition, 256, 128);
		}

		@Override
		public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay, int color) {
			this.root.render(stack, consumer, light, overlay, color);
		}
	}

	@Override
	public boolean shouldRenderOffScreen(SolarEggBlockEntity blockEntity) {
		return true;
	}

	@Override
	public int getViewDistance() {
		return 256;
	}
}
