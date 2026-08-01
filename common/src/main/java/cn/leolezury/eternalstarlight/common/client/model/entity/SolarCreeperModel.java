package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class SolarCreeperModel<T extends SolarCreeper> extends AnimatedEntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("solar_creeper"), "main");
	public final ModelPart root;
	public final ModelPart body;
	public final ModelPart head;
	public final ModelPart leg1;
	public final ModelPart leg2;
	public final ModelPart leg3;
	public final ModelPart leg4;
	public final List<String> allPartNames;

	public float alphaFactor = 1;
	public float shatterProgress = 0;

	public SolarCreeperModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.head = this.body.getChild("head");
		this.leg1 = this.root.getChild("leg1");
		this.leg2 = this.root.getChild("leg2");
		this.leg3 = this.root.getChild("leg3");
		this.leg4 = this.root.getChild("leg4");
		this.allPartNames = Stream.concat(Stream.of("root"), ESModelUtil.getAllPartNames(this.root)).toList();
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 44).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -22.0F, -11.0F, 28.0F, 22.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		root.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 68).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 9.0F, -7.0F));

		root.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 68).mirror().addBox(-5.0F, -1.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 9.0F, -7.0F));

		root.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 68).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 9.0F, 7.0F));

		root.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 68).mirror().addBox(-5.0F, -1.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 9.0F, 7.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
		head.xRot = headPitch * Mth.DEG_TO_RAD;
		if (shatterProgress > 0 && entity.getBehaviorTicks() >= 3) {
			applyShatter(entity.clientPartOffsets, entity.clientPartRotations, shatterProgress);
		}
	}

	@Override
	public ModelPart root() {
		return root;
	}

	public void applyShatter(Map<String, Vector3f> offsets, Map<String, Vector3f> rotations, float shatterProgress) {
		for (String name : allPartNames) {
			ModelPart part = getPartByName(name);
			if (part != null) {
				Vector3f offset = offsets.getOrDefault(name, new Vector3f());
				Vector3f rotation = rotations.getOrDefault(name, new Vector3f());
				part.x += offset.x() * shatterProgress;
				part.y += offset.y() * shatterProgress;
				part.z += offset.z() * shatterProgress;
				part.xRot += rotation.x() * shatterProgress;
				part.yRot += rotation.y() * shatterProgress;
				part.zRot += rotation.z() * shatterProgress;
			}
		}
	}

	private ModelPart getPartByName(String name) {
		if (name.equals("root")) return root;
		return findChild(root, name);
	}

	private static ModelPart findChild(ModelPart parent, String name) {
		for (Map.Entry<String, ModelPart> entry : parent.children.entrySet()) {
			if (entry.getKey().equals(name)) return entry.getValue();
			ModelPart found = findChild(entry.getValue(), name);
			if (found != null) return found;
		}
		return null;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root().render(poseStack, vertexConsumer, packedLight, packedOverlay, FastColor.ARGB32.color(Math.round(FastColor.ARGB32.alpha(color) * alphaFactor), FastColor.ARGB32.red(color), FastColor.ARGB32.green(color), FastColor.ARGB32.blue(color)));
	}
}
