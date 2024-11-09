package cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
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
public class BoarwarfDyerModel extends EntityModel<BoarwarfRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("boarwarf"), "dyer");
	public final ModelPart body;
	public final ModelPart head;

	public BoarwarfDyerModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.head = body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 38).addBox(-5.0F, -10.0F, -3.0F, 10.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void copyPropertiesFrom(BoarwarfModel model) {
		this.body.copyFrom(model.body);
		this.head.copyFrom(model.head);
	}
}
