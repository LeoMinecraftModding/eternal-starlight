package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.block.ESSkullType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.SkullBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(SkullBlockRenderer.class)
public abstract class SkullBlockRendererMixin {
    @Shadow @Final public static Map<SkullBlock.Type, ResourceLocation> SKIN_BY_TYPE;

    @Inject(method = "getRenderType", at = @At(value = "RETURN"), cancellable = true)
    private static void getRenderType(SkullBlock.Type type, ResolvableProfile resolvableProfile, CallbackInfoReturnable<RenderType> cir) {
        if (type == ESSkullType.TANGLED) {
            cir.setReturnValue(RenderType.entityTranslucent(SKIN_BY_TYPE.get(type)));
        }
    }
}