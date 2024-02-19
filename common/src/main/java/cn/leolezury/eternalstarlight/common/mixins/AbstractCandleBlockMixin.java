package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.init.ESBlocks;
import cn.leolezury.eternalstarlight.common.init.ESParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractCandleBlock.class)
public abstract class AbstractCandleBlockMixin {
    @Shadow protected abstract Iterable<Vec3> getParticleOffsets(BlockState blockState);

    @Shadow @Final public static BooleanProperty LIT;

    @Inject(method = "animateTick", at = @At("HEAD"), cancellable = true)
    private void es_animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        if ((Object) this == ESBlocks.AMARAMBER_CANDLE.get()) {
            if (blockState.getValue(LIT)) {
                this.getParticleOffsets(blockState).forEach((vec3) -> {
                    Vec3 pos = vec3.add(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    float f = randomSource.nextFloat();
                    if (f < 0.3F) {
                        level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
                        if (f < 0.17F) {
                            level.playLocalSound(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + randomSource.nextFloat(), randomSource.nextFloat() * 0.7F + 0.3F, false);
                        }
                    }

                    level.addParticle(ESParticles.AMARAMBER_FLAME.get(), pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
                });
            }
            ci.cancel();
        }
    }
}
