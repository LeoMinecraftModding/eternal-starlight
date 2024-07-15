package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.world.level.block.SkullBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(SkullBlockRenderer.class)
public abstract class SkullBlockRendererMixin {
	@Inject(method = "createSkullRenderers", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;put(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMap$Builder;", remap = false, ordinal = 0, shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void createSkullRenderers(EntityModelSet entityModelSet, CallbackInfoReturnable<Map<SkullBlock.Type, SkullModelBase>> cir, ImmutableMap.Builder<SkullBlock.Type, SkullModelBase> builder) {
		ClientSetupHandlers.registerSkullModels(builder::put, entityModelSet);
	}
}