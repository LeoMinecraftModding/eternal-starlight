package cn.leolezury.eternalstarlight.fabric.mixin.client;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ModelManager.class)
public abstract class ModelManagerMixin {
	@Inject(method = "apply", at = @At(value = "FIELD", target = "Lnet/minecraft/client/resources/model/ModelManager;missingModel:Lnet/minecraft/client/resources/model/BakedModel;", shift = At.Shift.AFTER))
	private void apply(ModelManager.ReloadState reloadState, ProfilerFiller profilerFiller, CallbackInfo ci) {
		ClientSetupHandlers.modifyBakingResult(reloadState.modelBakery().getBakedTopLevelModels());
	}
}
