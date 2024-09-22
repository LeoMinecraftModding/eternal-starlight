package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.component.DataComponents;
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
}
