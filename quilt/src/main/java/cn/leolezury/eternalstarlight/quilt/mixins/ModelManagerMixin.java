package cn.leolezury.eternalstarlight.quilt.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.AtlasSet;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(ModelManager.class)
public abstract class ModelManagerMixin {
    @Inject(method = "loadModels", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", args = "ldc=dispatch", shift = At.Shift.BEFORE))
    public void loadModels(ProfilerFiller profilerFiller, Map<ResourceLocation, AtlasSet.StitchResult> map, ModelBakery modelBakery, CallbackInfoReturnable<ModelManager.ReloadState> cir) {
        ClientSetupHandlers.modifyBakingResult(modelBakery.getBakedTopLevelModels());
    }
}
