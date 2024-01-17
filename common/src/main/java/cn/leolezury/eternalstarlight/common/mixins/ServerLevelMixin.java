package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Inject(method = "getChunkSource()Lnet/minecraft/server/level/ServerChunkCache;", at = @At("RETURN"))
    private void es_getChunkSource(CallbackInfoReturnable<ServerChunkCache> cir) {
        if (cir.getReturnValue() != null && cir.getReturnValue().getGenerator() instanceof ESChunkGenerator generator && generator.getBiomeSource() instanceof ESBiomeSource source) {
            source.setRegistryAccess(((ServerLevel) (Object) this).registryAccess());
        }
    }
}
