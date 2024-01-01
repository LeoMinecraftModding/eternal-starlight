package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.data.DimensionInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow @Nullable public LocalPlayer player;

    @Inject(at = @At(value = "RETURN"), method = "getSituationalMusic", cancellable = true)
    private void es_getSituationalMusic(CallbackInfoReturnable<Music> cir) {
        if (player != null && player.level().dimension() == DimensionInit.STARLIGHT_KEY && (cir.getReturnValue() == Musics.GAME || cir.getReturnValue() == Musics.CREATIVE || cir.getReturnValue() == Musics.UNDER_WATER)) {
            Holder<Biome> biomeHolder = player.level().getBiome(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()));
            if (biomeHolder.isBound()) {
                cir.setReturnValue(biomeHolder.value().getBackgroundMusic().orElse(Musics.GAME));
            }
        }
    }
}
