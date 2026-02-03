package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.item.misc.GalacticQuiverItem;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
	@Shadow
	public abstract ItemStack getItem();

	@Shadow
	@Nullable
	private UUID target;

	@Shadow
	private int pickupDelay;

	@Shadow
	private int age;

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo ci) {
		ItemEntity itemEntity = ((ItemEntity) (Object) this);
		// max age is 6000
		if (age < 3000 && getItem().get(DataComponents.FOOD) != null && (itemEntity.level().getBlockState(itemEntity.blockPosition()).is(ESTags.Blocks.TOOTH_OF_HUNGER_BLOCKS) || itemEntity.level().getBlockState(itemEntity.blockPosition().below()).is(ESTags.Blocks.TOOTH_OF_HUNGER_BLOCKS))) {
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

	@Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
	public void playerTouch(Player player, CallbackInfo ci) {
		ItemEntity itemEntity = ((ItemEntity) (Object) this);
		if (itemEntity.level().isClientSide) return;
		if (this.pickupDelay == 0 && (this.target == null || this.target.equals(player.getUUID())) && getItem().is(ESItems.MANA_CRYSTAL_SHARD.get())) {
			ci.cancel();
			player.take(itemEntity, getItem().getCount());
			itemEntity.discard();
			Inventory inventory = player.getInventory();
			for (int i = 0; i < inventory.getContainerSize(); i++) {
				ItemStack stack = inventory.getItem(i);
				if (stack.is(ESTags.Items.MANA_CRYSTALS) && stack.isDamaged()) {
					stack.setDamageValue(Math.max(stack.getDamageValue() - getItem().getCount(), 0));
					return;
				}
			}
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
