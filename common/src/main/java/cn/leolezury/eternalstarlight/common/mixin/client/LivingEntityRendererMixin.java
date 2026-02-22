package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
	@Shadow
	public abstract M getModel();

	@Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isSpectator()Z"))
	private void renderOverlay(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
		if (entity instanceof AbstractClientPlayer && entity.level().getEntity(ESDataAttachments.HUSK_OWNER_ID.getData(entity)) instanceof Player) {
			VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutout(entity instanceof AbstractClientPlayer player && player.getSkin().model() == PlayerSkin.Model.SLIM ? EternalStarlight.id("textures/entity/tangled_husk_slim.png") : EternalStarlight.id("textures/entity/tangled_husk.png")));
			getModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
		}
	}

	@WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
	private void renderToBuffer(M instance, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color, Operation<Void> original, @Local(ordinal = 0, argsOnly = true) T entity) {
		if (entity instanceof AbstractClientPlayer && entity.level().getEntity(ESDataAttachments.HUSK_OWNER_ID.getData(entity)) instanceof Player) {
			int alpha = FastColor.ARGB32.alpha(color);
			int red = FastColor.ARGB32.red(color);
			int green = FastColor.ARGB32.green(color);
			int blue = FastColor.ARGB32.blue(color);
			original.call(instance, poseStack, vertexConsumer, packedLight, packedOverlay, FastColor.ARGB32.color(alpha / 2, red / 2, green / 2, blue));
		} else {
			original.call(instance, poseStack, vertexConsumer, packedLight, packedOverlay, color);
		}
	}

	@Inject(method = "getRenderType", at = @At("RETURN"), cancellable = true)
	private void getRenderType(T livingEntity, boolean bodyVisible, boolean translucent, boolean glowing, CallbackInfoReturnable<RenderType> cir) {
		if (livingEntity instanceof AbstractClientPlayer && livingEntity.level().getEntity(ESDataAttachments.HUSK_OWNER_ID.getData(livingEntity)) instanceof Player) {
			if (!translucent && bodyVisible) {
				cir.setReturnValue(RenderType.entityTranslucent(((LivingEntityRenderer<T, M>) (Object) this).getTextureLocation(livingEntity)));
			}
		}
	}

	@Inject(method = "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("RETURN"), cancellable = true)
	private void shouldShowName(T livingEntity, CallbackInfoReturnable<Boolean> cir) {
		if (livingEntity.level().getEntity(ESDataAttachments.HUSK_OWNER_ID.getData(livingEntity)) instanceof Player) {
			cir.setReturnValue(false);
		}
	}
}
