package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.block.AlloyFurnaceBlock;
import cn.leolezury.eternalstarlight.common.block.MechanicalSpawnerBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.chunk.LevelChunk;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin {
	// don't log the annoying error
	@WrapOperation(method = "promotePendingBlockEntity", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", remap = false, ordinal = 1))
	private void logWarning(Logger instance, String string, Object o1, Object o2, Operation<Void> original, @Local BlockState state) {
		if (!(state.getBlock() instanceof MechanicalSpawnerBlock && state.getValue(MechanicalSpawnerBlock.HALF) != DoubleBlockHalf.UPPER)
			&& !(state.getBlock() instanceof AlloyFurnaceBlock && !(state.getValue(AlloyFurnaceBlock.X_OFFSET) == 0 && state.getValue(AlloyFurnaceBlock.Y_OFFSET) == 0 && state.getValue(AlloyFurnaceBlock.Z_OFFSET) == 1))) {
			original.call(instance, string, o1, o2);
		}
	}
}
