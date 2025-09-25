package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESAccessoryUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DiggerItem.class)
public abstract class DiggerItemMixin {
	@WrapOperation(method = "postHurtEnemy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V"))
	private void hurtAndBreak(ItemStack instance, int damage, LivingEntity livingEntity, EquipmentSlot slot, Operation<Void> original) {
		if (ESAccessoryUtil.getAccessories(instance).contains(ESItems.BATTLEAXE_PENDANT.get())) {
			original.call(instance, 1, livingEntity, slot);
		} else {
			original.call(instance, damage, livingEntity, slot);
		}
	}
}
