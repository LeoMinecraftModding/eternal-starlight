package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.world.level.block.SkullBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(SkullBlockRenderer.class)
public abstract class SkullBlockRendererMixin {
    @Shadow @Mutable @Final private Map<SkullBlock.Type, SkullModelBase> modelByType;

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(BlockEntityRendererProvider.Context context, CallbackInfo ci) {
        HashMap<SkullBlock.Type, SkullModelBase> map = new HashMap<>(modelByType);
        ClientSetupHandlers.registerSkullModels(map::put, context.getModelSet());
        modelByType = map;
    }
}