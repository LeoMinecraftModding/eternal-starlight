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
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

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
	public void extractRenderState(AstralGolem entity, AstralGolemRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		Optional<Holder.Reference<AstralGolemMaterial>> ref = entity.getMaterial();
		if (ref.isPresent() && ref.get().isBound()) {
			state.material = ref.get().value();
		}
		state.attackAnimationTick = entity.getAttackAnimationTick(partialTicks);
		state.isBlocking = entity.isGolemBlocking();
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
