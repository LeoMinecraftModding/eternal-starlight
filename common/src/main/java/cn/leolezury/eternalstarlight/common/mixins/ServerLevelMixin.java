package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.util.ESWeatherUtil;
import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Inject(method = "getChunkSource()Lnet/minecraft/server/level/ServerChunkCache;", at = @At("RETURN"))
    private void getChunkSource(CallbackInfoReturnable<ServerChunkCache> cir) {
        if (cir.getReturnValue() != null && cir.getReturnValue().getGenerator() instanceof ESChunkGenerator generator && generator.getBiomeSource() instanceof ESBiomeSource source) {
            source.setRegistryAccess(((ServerLevel) (Object) this).registryAccess());
        }
    }

    @Inject(method = "tickPrecipitation", at = @At("RETURN"))
    private void tickPrecipitation(BlockPos blockPos, CallbackInfo ci) {
        if (((ServerLevel) (Object) this).dimension() == ESDimensions.STARLIGHT_KEY) {
            ESWeatherUtil.getOrCreateWeathers(((ServerLevel) (Object) this)).getActiveWeather().ifPresent((instance) -> {
                instance.getWeather().tickBlock(((ServerLevel) (Object) this), instance.ticksSinceStarted, blockPos);
            });
        }
    }
}
