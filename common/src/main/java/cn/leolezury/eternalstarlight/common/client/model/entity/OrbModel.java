package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3f;

public class OrbModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("orb"), "main");
	public final ModelPart root;
	public final ModelPart layer1;
	public final ModelPart layer2;
	public final ModelPart layer3;

	public OrbModel(ModelPart root) {
		this.root = root.getChild("root");
		this.layer1 = this.root.getChild("layer1");
		this.layer2 = this.root.getChild("layer2");
		this.layer3 = this.root.getChild("layer3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		root.addOrReplaceChild("layer1", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		root.addOrReplaceChild("layer2", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-6.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		root.addOrReplaceChild("layer3", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-8.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root.getAllParts().forEach(ModelPart::resetPose);
		this.root.xRot = Mth.sin(ageInTicks * 0.05f + 7 + entity.getId() * 7) * Mth.TWO_PI;
		this.root.yRot = Mth.sin(ageInTicks * 0.03f + 13 + entity.getId() * 7) * Mth.TWO_PI;
		this.layer1.offsetScale(new Vector3f(Mth.sin(ageInTicks * 0.05f + 19 + entity.getId() * 7) * (0.6f / 4f)));
		this.layer2.offsetScale(new Vector3f(Mth.sin(ageInTicks * 0.06f + 23 + entity.getId() * 7) * (0.6f / 6f)));
		this.layer3.offsetScale(new Vector3f(Mth.sin(ageInTicks * 0.07f + 31 + entity.getId() * 7) * (0.6f / 8f)));
	}

	@Override
	public RenderType renderType(ResourceLocation resourceLocation) {
		return ESRenderType.entityCutoutGlow(resourceLocation);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}
