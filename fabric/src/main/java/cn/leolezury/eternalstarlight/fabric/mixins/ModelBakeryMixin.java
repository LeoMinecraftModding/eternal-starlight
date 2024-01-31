package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {
    @Inject(method = "getBakedTopLevelModels", at = @At(value = "RETURN"))
    private void es_getBakedTopLevelModels(CallbackInfoReturnable<Map<ResourceLocation, BakedModel>> cir) {
        // idk if it works
        ClientSetupHandlers.modifyBakingResult(cir.getReturnValue());
    }
}
