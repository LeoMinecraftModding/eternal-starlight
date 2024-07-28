package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {
	@Unique
	private ItemStack armorStack;

	@Inject(method = "renderArmorPiece", at = @At("HEAD"))
	private void renderArmorPiece(PoseStack poseStack, MultiBufferSource multiBufferSource, T livingEntity, EquipmentSlot equipmentSlot, int i, A humanoidModel, CallbackInfo ci) {
		armorStack = livingEntity.getItemBySlot(equipmentSlot);
	}

	@Inject(method = "renderTrim", at = @At("HEAD"), cancellable = true)
	private void renderTrim(Holder<ArmorMaterial> holder, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, ArmorTrim armorTrim, A humanoidModel, boolean bl, CallbackInfo ci) {
		if (BuiltInRegistries.ITEM.getKey(armorStack.getItem()).getNamespace().equals(EternalStarlight.ID) && !armorStack.is(ESTags.Items.TRIMMABLE_ARMOR)) {
			ci.cancel();
		}
	}
}
