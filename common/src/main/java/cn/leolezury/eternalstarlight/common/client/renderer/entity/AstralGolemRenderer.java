package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.AstralGolemModel;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.state.AstralGolemRenderState;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.AstralGolemArmorLayer;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolemMaterial;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class AstralGolemRenderer extends HumanoidMobRenderer<AstralGolem, AstralGolemRenderState, AstralGolemModel> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/boarwarf/golem/astral_golem_iron.png");

	public AstralGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new AstralGolemModel(context.bakeLayer(AstralGolemModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new AstralGolemArmorLayer<>(this, new HumanoidArmorModel<>(context.bakeLayer(AstralGolemModel.INNER_ARMOR_LOCATION)), new HumanoidArmorModel<>(context.bakeLayer(AstralGolemModel.OUTER_ARMOR_LOCATION)), context.getEquipmentRenderer()));
	}

	@Override
	public AstralGolemRenderState createRenderState() {
		return new AstralGolemRenderState();
	}

	@Override
	public void extractRenderState(AstralGolem mob, AstralGolemRenderState state, float partialTicks) {
		super.extractRenderState(mob, state, partialTicks);
		state.material = mob.getMaterial();
		state.attackAnimationTick = mob.getAttackAnimationTick(partialTicks);
		state.blocking = mob.isGolemBlocking();
	}

	@Override
	protected int getModelTint(AstralGolemRenderState state) {
		return state.material != null ? state.material.tintColor() : -1;
	}

	@Override
	public ResourceLocation getTextureLocation(AstralGolemRenderState state) {
		AstralGolemMaterial material = state.material;
		return material == null ? ENTITY_TEXTURE : material.texture().withSuffix(".png");
	}
}
