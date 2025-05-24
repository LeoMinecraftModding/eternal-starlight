package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.client.model.entity.DarkSwampWitchModel;
import cn.leolezury.eternalstarlight.common.entity.interfaces.StarlightWitch;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WitchRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Witch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(WitchRenderer.class)
public abstract class WitchRendererMixin {
	@Unique
	private WitchModel<Witch> vanillaModel;
	@Unique
	private WitchModel<Witch> darkSwampModel;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(EntityRendererProvider.Context context, CallbackInfo ci) {
		vanillaModel = ((WitchRenderer) (Object) this).getModel();
		darkSwampModel = new WitchModel<>(context.bakeLayer(DarkSwampWitchModel.LAYER_LOCATION));
	}

	@Inject(method = "render(Lnet/minecraft/world/entity/monster/Witch;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
	private void render(Witch witch, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
		if (witch instanceof StarlightWitch starlightWitch && Objects.equals(starlightWitch.getWitchType(), "dark_swamp")) {
			WitchRenderer renderer = (WitchRenderer) (Object) this;
			renderer.model = darkSwampModel;
		}
	}

	@Inject(method = "render(Lnet/minecraft/world/entity/monster/Witch;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("RETURN"))
	private void renderReturn(Witch witch, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
		WitchRenderer renderer = (WitchRenderer) (Object) this;
		if (renderer.getModel() != vanillaModel) {
			renderer.model = vanillaModel;
		}
	}

	@Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/monster/Witch;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
	private void getTextureLocation(Witch witch, CallbackInfoReturnable<ResourceLocation> cir) {
		if (witch instanceof StarlightWitch starlightWitch && Objects.equals(starlightWitch.getWitchType(), "dark_swamp")) {
			cir.setReturnValue(DarkSwampWitchModel.ENTITY_TEXTURE);
		}
	}
}
