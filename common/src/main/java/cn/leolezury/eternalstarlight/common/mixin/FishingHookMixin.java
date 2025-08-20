package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {
	@WrapOperation(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;"))
	public ObjectArrayList<ItemStack> getFishingLoot(LootTable instance, LootParams params, Operation<ObjectArrayList<ItemStack>> original) {
		FishingHook hook = (FishingHook) (Object) this;
		if (hook.level().dimension().location().equals(ESDimensions.STARLIGHT_KEY.location())) {
			MinecraftServer server = hook.level().getServer();
			if (server != null) {
				LootTable lootTable = server.reloadableRegistries().getLootTable(ESLootTables.GAMEPLAY_FISHING);
				return lootTable.getRandomItems(params);
			}
		}
		return original.call(instance, params);
	}
}
