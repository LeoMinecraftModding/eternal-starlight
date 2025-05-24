package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

	@Shadow
	public abstract boolean is(Item item);

	@Inject(method = "inventoryTick", at = @At(value = "RETURN"))
	private void inventoryTick(Level level, Entity entity, int inventorySlot, boolean isCurrentItem, CallbackInfo ci) {
		if (!level.isClientSide && entity.tickCount % 600 == 0 && isDamaged() && is(ESTags.Items.MENDS_NATURALLY)) {
			setDamageValue(Math.max(getDamageValue() - 1, 0));
		}
	}

	@Inject(method = "canBeHurtBy", at = @At(value = "RETURN"), cancellable = true)
	private void canBeHurtBy(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		if (is(ESItems.LOOT_BAG.get()) && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			cir.setReturnValue(false);
		}
	}
}
