package cn.leolezury.eternalstarlight.neoforge.mixin;

import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileWeaponItem.class)
public abstract class ProjectileWeaponItemMixin {
	@Inject(method = "getDefaultCreativeAmmo", at = @At("RETURN"), cancellable = true, remap = false)
	private void getDefaultCreativeAmmo(Player player, ItemStack projectileWeaponItem, CallbackInfoReturnable<ItemStack> cir) {
		if (projectileWeaponItem.getItem() instanceof SeedsLauncherItem) {
			cir.setReturnValue(Items.WHEAT_SEEDS.getDefaultInstance());
		}
	}
}
