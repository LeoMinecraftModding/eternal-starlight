package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.ESHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// todo don't use mixin if mojang fixed the MapCodec<SomeBlock> problem
@Mixin(CeilingHangingSignBlock.class)
public abstract class CeilingHangingSignBlockMixin {
    @Inject(method = "newBlockEntity", at = @At(value = "HEAD"), cancellable = true)
    public void es_newBlockEntity(BlockPos blockPos, BlockState blockState, CallbackInfoReturnable<BlockEntity> cir) {
        if (BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).getNamespace().equals(EternalStarlight.MOD_ID)) {
            cir.setReturnValue(new ESHangingSignBlockEntity(blockPos, blockState));
        }
    }
}
