package cn.leolezury.eternalstarlight.client.renderer.layer;

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
    public AstralGolemArmorLayer(RenderLayerParent parent, A inner, A outer, ModelManager mgr) {
        super(parent, inner, outer, mgr);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource source, int p_117098_, T model, float p_117100_, float p_117101_, float p_117102_, float p_117103_, float p_117104_, float p_117105_) {
        this.renderArmorPiece(stack, source, model, EquipmentSlot.CHEST, p_117098_, this.getArmorModel(EquipmentSlot.CHEST));
        this.renderArmorPiece(stack, source, model, EquipmentSlot.HEAD, p_117098_, this.getArmorModel(EquipmentSlot.HEAD));
    }
}
