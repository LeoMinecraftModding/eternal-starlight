package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnEggItem.class)
public abstract class SpawnEggItemMixin {
	@Inject(method = "getColor", at = @At("RETURN"), cancellable = true)
	private void getColor(int i, CallbackInfoReturnable<Integer> cir) {
		Item item = (Item) (Object) this;
		if (BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(EternalStarlight.ID)) {
			cir.setReturnValue(0xffffff);
		}
	}
}
