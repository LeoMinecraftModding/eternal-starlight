package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.ESCampfireBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// todo don't use mixin if mojang fixed the MapCodec<SomeBlock> problem
@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
    @Inject(method = "newBlockEntity", at = @At(value = "HEAD"), cancellable = true)
    public void es_newBlockEntity(BlockPos blockPos, BlockState blockState, CallbackInfoReturnable<BlockEntity> cir) {
        if (BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).getNamespace().equals(EternalStarlight.MOD_ID)) {
            cir.setReturnValue(new ESCampfireBlockEntity(blockPos, blockState));
        }
    }

    @Inject(method = "getTicker", at = @At(value = "HEAD"), cancellable = true)
    public <T extends CampfireBlockEntity> void es_getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType, CallbackInfoReturnable<BlockEntityTicker<T>> cir) {
        if (BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).getNamespace().equals(EternalStarlight.MOD_ID) && blockEntityType == ESBlockEntities.CAMPFIRE.get()) {
            if (level.isClientSide) {
                cir.setReturnValue(blockState.getValue(BlockStateProperties.LIT) ? CampfireBlockEntity::particleTick : null);
            } else {
                cir.setReturnValue(ESCampfireBlockEntity::serverTick);
            }
        }
    }
}
