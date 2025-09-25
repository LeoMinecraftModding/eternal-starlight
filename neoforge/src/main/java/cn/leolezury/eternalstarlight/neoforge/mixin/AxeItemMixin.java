package cn.leolezury.eternalstarlight.neoforge.mixin;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESAccessoryUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {
	@WrapOperation(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/AxeItem;evaluateNewBlockState(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/item/context/UseOnContext;)Ljava/util/Optional;", remap = false))
	private Optional<BlockState> evaluateNewBlockState(AxeItem instance, Level level, BlockPos pos, Player player, BlockState state, UseOnContext context, Operation<Optional<BlockState>> original) {
		if (ESAccessoryUtil.getAccessories(context.getItemInHand()).contains(ESItems.BATTLEAXE_PENDANT.get())) {
			return Optional.empty();
		} else {
			return original.call(instance, level, pos, player, state, context);
		}
	}
}
