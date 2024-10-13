package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class AstralGolemArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
	public AstralGolemArmorLayer(RenderLayerParent<T, M> parent, A inner, A outer, ModelManager mgr) {
		super(parent, inner, outer, mgr);
	}

	@Override
	public void render(PoseStack stack, MultiBufferSource source, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
		this.renderArmorPiece(stack, source, livingEntity, EquipmentSlot.CHEST, i, this.getArmorModel(EquipmentSlot.CHEST));
		this.renderArmorPiece(stack, source, livingEntity, EquipmentSlot.HEAD, i, this.getArmorModel(EquipmentSlot.HEAD));
	}
}
