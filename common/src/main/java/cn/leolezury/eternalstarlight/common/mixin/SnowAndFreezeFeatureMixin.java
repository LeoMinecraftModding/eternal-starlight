package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SnowAndFreezeFeature.class)
public abstract class SnowAndFreezeFeatureMixin {
	@WrapOperation(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", ordinal = 0))
	private boolean place(WorldGenLevel instance, BlockPos pos, BlockState state, int updateFlags, Operation<Boolean> original) {
		if (instance.getLevel().dimension() == ESDimensions.STARLIGHT_KEY) {
			return original.call(instance, pos, ESBlocks.THIN_ETERNAL_ICE.get().defaultBlockState(), updateFlags);
		} else {
			return original.call(instance, pos, state, updateFlags);
		}
	}
}
