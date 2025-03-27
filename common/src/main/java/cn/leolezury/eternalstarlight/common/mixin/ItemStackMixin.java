package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Shadow
	public abstract boolean is(TagKey<Item> arg);

	@Shadow
	public abstract void setDamageValue(int i);

	@Shadow
	public abstract int getDamageValue();

	@Shadow
	public abstract boolean isDamaged();

	@Inject(method = "inventoryTick", at = @At(value = "RETURN"))
	private void inventoryTick(Level level, Entity entity, int inventorySlot, boolean isCurrentItem, CallbackInfo ci) {
		if (!level.isClientSide && entity.tickCount % 600 == 0 && isDamaged() && is(ESTags.Items.MENDS_NATURALLY)) {
			setDamageValue(Math.max(getDamageValue() - 1, 0));
		}
	}
}
