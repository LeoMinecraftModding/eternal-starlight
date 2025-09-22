package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESAccessoryUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CrescentPendantLayer extends ArmorLikeAccessoryLayer<LivingEntity, HumanoidModel<LivingEntity>, HumanoidModel<LivingEntity>> {
	private static final ResourceLocation TEXTURE = EternalStarlight.id("textures/accessory/crescent_pendant.png");

	public static final ModelLayerLocation INNER_LOCATION = new ModelLayerLocation(EternalStarlight.id("crescent_pendant"), "inner_armor");
	public static final ModelLayerLocation OUTER_LOCATION = new ModelLayerLocation(EternalStarlight.id("crescent_pendant"), "outer_armor");

	public CrescentPendantLayer(RenderLayerParent<LivingEntity, HumanoidModel<LivingEntity>> renderer, HumanoidModel<LivingEntity> innerModel, HumanoidModel<LivingEntity> outerModel) {
		super(renderer, innerModel, outerModel);
	}

	@Override
	protected ResourceLocation getAccessoryTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		return slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST ? TEXTURE : null;
	}

	@Override
	protected boolean shouldRender(LivingEntity entity) {
		return ESAccessoryUtil.getActiveAccessoriesOnArmors(entity).contains(ESItems.CRESCENT_PENDANT.get());
	}
}
