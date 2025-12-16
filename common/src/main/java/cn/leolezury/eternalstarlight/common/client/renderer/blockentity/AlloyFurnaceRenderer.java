package cn.leolezury.eternalstarlight.common.client.renderer.blockentity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AlloyFurnaceBlock;
import cn.leolezury.eternalstarlight.common.block.entity.AlloyFurnaceBlockEntity;
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
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class AlloyFurnaceRenderer implements BlockEntityRenderer<AlloyFurnaceBlockEntity> {
	private final AlloyFurnaceModel furnaceModel;

	private static final ResourceLocation FURNACE_TEXTURE = EternalStarlight.id("textures/entity/alloy_furnace.png");
	private static final ResourceLocation LIT_TEXTURE = EternalStarlight.id("textures/entity/alloy_furnace_lit.png");

	public AlloyFurnaceRenderer(BlockEntityRendererProvider.Context context) {
		this.furnaceModel = new AlloyFurnaceModel(context.bakeLayer(AlloyFurnaceModel.LAYER_LOCATION));
	}

	@Override
	public void render(AlloyFurnaceBlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay) {
		BlockState state = blockEntity.getBlockState();
		if (state.getValue(AlloyFurnaceBlock.X_OFFSET) == 0 && state.getValue(AlloyFurnaceBlock.Y_OFFSET) == 0 && state.getValue(AlloyFurnaceBlock.Z_OFFSET) == 1) {
			stack.pushPose();
			stack.scale(-1.0F, -1.0F, 1.0F);
			stack.translate(-0.5F, -1.5F, 0.5F);
			stack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockState().getValue(AlloyFurnaceBlock.FACING).toYRot() + 180));
			this.furnaceModel.renderToBuffer(stack, bufferSource.getBuffer(RenderType.entityTranslucent(FURNACE_TEXTURE)), light, overlay);
			if (blockEntity.isLit()) {
				this.furnaceModel.renderToBuffer(stack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(LIT_TEXTURE)), LightTexture.FULL_BRIGHT, overlay);
			}
			stack.popPose();
		}
	}

	public static final class AlloyFurnaceModel extends Model {
		public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("alloy_furnace"), "main");

		public final ModelPart root;

		public AlloyFurnaceModel(ModelPart root) {
			super(RenderType::entityCutoutNoCull);
			this.root = root.getChild("root");
		}

		public static LayerDefinition createLayer() {
			MeshDefinition meshdefinition = new MeshDefinition();
			PartDefinition partdefinition = meshdefinition.getRoot();

			partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-24.0F, -32.0F, -8.0F, 36.0F, 32.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(0, 64).addBox(12.0F, -28.0F, 2.0F, 12.0F, 28.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(48, 76).addBox(15.0F, -32.0F, 5.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(48, 64).addBox(-1.0F, -38.0F, 5.0F, 22.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 156).addBox(-11.0F, -40.0F, 9.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 120).addBox(-21.0F, -44.0F, -1.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 104).addBox(-22.0F, -48.0F, -2.0F, 12.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 142).addBox(-12.0F, -44.0F, 8.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

			return LayerDefinition.create(meshdefinition, 192, 192);
		}

		@Override
		public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay, int color) {
			this.root.render(stack, consumer, light, overlay, color);
		}
	}

	@Override
	public boolean shouldRenderOffScreen(AlloyFurnaceBlockEntity blockEntity) {
		return true;
	}

	@Override
	public boolean shouldRender(AlloyFurnaceBlockEntity blockEntity, Vec3 vec3) {
		return true;
	}
}
