package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.ESSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlock.class)
public abstract class SignBlockMixin {
    @Inject(method = "newBlockEntity", at = @At(value = "HEAD"), cancellable = true)
    public void es_newBlockEntity(BlockPos blockPos, BlockState blockState, CallbackInfoReturnable<BlockEntity> cir) {
        if (BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).getNamespace().equals(EternalStarlight.MOD_ID)) {
            cir.setReturnValue(new ESSignBlockEntity(blockPos, blockState));
        }
    }
}
