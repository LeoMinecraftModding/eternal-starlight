package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.item.misc.GalacticQuiverItem;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
	@Shadow
	public abstract ItemStack getItem();

	@Shadow
	private int age;

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo ci) {
		ItemEntity itemEntity = ((ItemEntity) (Object) this);
		// max age is 6000
		if (age < 3000 && getItem().get(DataComponents.FOOD) != null && (itemEntity.level().getBlockState(itemEntity.blockPosition()).is(ESTags.Blocks.TOOTH_OF_HUNGER_BLOCKS) || itemEntity.level().getBlockState(itemEntity.getOnPos()).is(ESTags.Blocks.TOOTH_OF_HUNGER_BLOCKS))) {
			age = 3000;
		}
	}

	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		ItemEntity itemEntity = ((ItemEntity) (Object) this);
		if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && ESDataAttachments.IMPORTANT_ITEM.getData(itemEntity)) {
			cir.setReturnValue(false);
		}
	}

	@WrapOperation(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z"))
	public boolean addToInventory(Inventory instance, ItemStack itemStack, Operation<Boolean> original) {
		if (itemStack.is(ItemTags.ARROWS)) {
			boolean arrowSuccess = GalacticQuiverItem.addArrowToInventory(instance, itemStack);
			return arrowSuccess || original.call(instance, itemStack);
		}
		return original.call(instance, itemStack);
	}
}
