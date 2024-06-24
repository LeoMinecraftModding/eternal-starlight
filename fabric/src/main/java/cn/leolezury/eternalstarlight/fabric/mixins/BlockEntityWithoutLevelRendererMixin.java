package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.SkullBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public abstract class BlockEntityWithoutLevelRendererMixin {
	@Shadow
	private Map<SkullBlock.Type, SkullModelBase> skullModels;

	@Shadow
	@Final
	private EntityModelSet entityModelSet;

	@Inject(method = "onResourceManagerReload", at = @At(value = "RETURN"))
	private void onResourceManagerReload(ResourceManager resourceManager, CallbackInfo ci) {
		HashMap<SkullBlock.Type, SkullModelBase> map = new HashMap<>(skullModels);
		ClientSetupHandlers.registerSkullModels(map::put, entityModelSet);
		skullModels = map;
	}
}
