package cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.BoarwarfModel;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.state.BoarwarfRenderState;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class BoarwarfChefModel extends EntityModel<BoarwarfRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("boarwarf"), "chef");
	public final ModelPart body;
	public final ModelPart head;

	public BoarwarfChefModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.head = body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 35).addBox(-5.0F, -10.0F, -3.0F, 10.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -15.0F, -6.0F, 12.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
			.texOffs(0, 19).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void copyPropertiesFrom(BoarwarfModel model) {
		this.body.copyFrom(model.body);
		this.head.copyFrom(model.head);
	}
}
