package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.data.DamageTypeInit;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {
    @Inject(method = "entityInside", at = @At(value = "HEAD"))
    public void es_entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        if (blockState.is(BlockInit.ETHER.get())) {
            entity.hurt(DamageTypeInit.getDamageSource(level, DamageTypeInit.ETHER), 1);
        }
    }
}
