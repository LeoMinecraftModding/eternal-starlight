package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimator;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	@Final
	private EntityRenderDispatcher entityRenderDispatcher;

	@Shadow
	public abstract void renderItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i);

	@Shadow
	private ItemStack mainHandItem;

	@Shadow
	private ItemStack offHandItem;

	@SuppressWarnings({"ConstantConditions"})
	@Inject(method = "evaluateWhichHandsToRender", at = @At(value = "RETURN"), cancellable = true)
	private static void evaluateWhichHandsToRender(LocalPlayer player, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
		ItemStack itemStack = player.getMainHandItem();
		ItemStack itemStack1 = player.getOffhandItem();
		boolean flag = itemStack.is(ESItems.CRYSTAL_CROSSBOW.get()) || itemStack.is(ESItems.MECHANICAL_CROSSBOW.get()) || itemStack.is(ESItems.MOONRING_BOW.get()) || itemStack.is(ESItems.STARFALL_LONGBOW.get()) || itemStack.is(ESItems.BOW_OF_BLOOD.get());
		boolean flag1 = itemStack1.is(ESItems.CRYSTAL_CROSSBOW.get()) || itemStack.is(ESItems.MECHANICAL_CROSSBOW.get()) || itemStack1.is(ESItems.MOONRING_BOW.get()) || itemStack1.is(ESItems.STARFALL_LONGBOW.get()) || itemStack1.is(ESItems.BOW_OF_BLOOD.get());
		if (flag || flag1) {
			if (player.isUsingItem()) {
				ItemStack itemStack2 = player.getUseItem();
				InteractionHand interactionhand = player.getUsedItemHand();
				if (itemStack2.is(ESItems.CRYSTAL_CROSSBOW.get()) || itemStack2.is(ESItems.MECHANICAL_CROSSBOW.get()) || itemStack2.is(ESItems.MOONRING_BOW.get()) || itemStack2.is(ESItems.STARFALL_LONGBOW.get()) || itemStack2.is(ESItems.BOW_OF_BLOOD.get())) {
					cir.setReturnValue(ItemInHandRenderer.HandRenderSelection.onlyForHand(interactionhand));
				}
			} else {
				cir.setReturnValue(((itemStack.is(ESItems.CRYSTAL_CROSSBOW.get()) || itemStack.is(ESItems.MECHANICAL_CROSSBOW.get())) && CrossbowItem.isCharged(itemStack)) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
			}
		}
	}

	@Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
	private void renderArmWithItem(AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, CallbackInfo ci) {
		PlayerAnimator.renderingFirstPersonPlayer = true;
		boolean renderLeft = false;
		boolean renderRight = false;
		for (Map.Entry<PlayerAnimator.AnimationTrigger, PlayerAnimator.AnimationStateFunction> entry : PlayerAnimator.ANIMATIONS.entrySet()) {
			if (entry.getKey().shouldPlay(abstractClientPlayer)) {
				PlayerAnimator.PlayerAnimationState state = entry.getValue().get(abstractClientPlayer);
				renderLeft = renderLeft || state.renderLeftArm();
				renderRight = renderRight || state.renderRightArm();
				ci.cancel();
			}
		}
		if (renderLeft || renderRight) {
			boolean mainHand = interactionHand == InteractionHand.MAIN_HAND;
			boolean rightMain = abstractClientPlayer.getMainArm() == HumanoidArm.RIGHT;

			poseStack.pushPose();

			boolean mainArm = renderRight && ((mainHand && rightMain) || (!mainHand && !rightMain));
			boolean oppositeArm = renderLeft && ((mainHand && !rightMain) || (!mainHand && rightMain));

			// adjusted from the two-handed map rendering
			float n = Mth.sqrt(h);
			float k = -0.2F * Mth.sin(h * 3.1415927F);
			float l = -0.4F * Mth.sin(n * 3.1415927F);
			poseStack.translate(0.0F, -k / 2.0F, l);
			poseStack.translate(0.0F, 0.04F + i * -1.2F, -0.72F);
			poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));

			if (mainArm) {
				HumanoidArm humanoidArm = abstractClientPlayer.getMainArm();
				renderPlayerArm(poseStack, multiBufferSource, j, mainHand, humanoidArm);
			}
			if (oppositeArm) {
				HumanoidArm humanoidArm = abstractClientPlayer.getMainArm().getOpposite();
				renderPlayerArm(poseStack, multiBufferSource, j, mainHand, humanoidArm);
			}

			poseStack.popPose();
		}
		PlayerAnimator.renderingFirstPersonPlayer = false;
	}

	// vanilla copy (adjusted)
	@Unique
	private void renderPlayerArm(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, boolean mainHand, HumanoidArm humanoidArm) {
		poseStack.pushPose();
		boolean bl = humanoidArm != HumanoidArm.LEFT;
		float h = bl ? 1.0F : -1.0F;
		AbstractClientPlayer abstractClientPlayer = this.minecraft.player;
		poseStack.mulPose(Axis.YP.rotationDegrees(92F));
		poseStack.mulPose(Axis.XP.rotationDegrees(45F));
		// translate hands here
		poseStack.translate(h * -0.02F, -0.6, 0.45F);

		if (bl) {
			renderRightHand(poseStack, multiBufferSource, i, mainHand, abstractClientPlayer);
		} else {
			renderLeftHand(poseStack, multiBufferSource, i, mainHand, abstractClientPlayer);
		}
		poseStack.popPose();
	}

	@Unique
	public void renderRightHand(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, boolean mainHand, AbstractClientPlayer abstractClientPlayer) {
		PlayerRenderer playerRenderer = (PlayerRenderer) entityRenderDispatcher.getRenderer(abstractClientPlayer);
		PlayerModel<AbstractClientPlayer> playerModel = playerRenderer.getModel();
		renderHand(poseStack, multiBufferSource, i, mainHand, false, abstractClientPlayer, playerModel.rightArm, playerModel.rightSleeve);
	}

	@Unique
	public void renderLeftHand(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, boolean mainHand, AbstractClientPlayer abstractClientPlayer) {
		PlayerRenderer playerRenderer = (PlayerRenderer) entityRenderDispatcher.getRenderer(abstractClientPlayer);
		PlayerModel<AbstractClientPlayer> playerModel = playerRenderer.getModel();
		renderHand(poseStack, multiBufferSource, i, mainHand, true, abstractClientPlayer, playerModel.leftArm, playerModel.leftSleeve);
	}

	@Unique
	private void renderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, boolean mainHand, boolean leftHand, AbstractClientPlayer abstractClientPlayer, ModelPart modelPart, ModelPart modelPart2) {
		PlayerRenderer playerRenderer = (PlayerRenderer) entityRenderDispatcher.getRenderer(abstractClientPlayer);
		PlayerModel<AbstractClientPlayer> playerModel = playerRenderer.getModel();
		playerRenderer.setModelProperties(abstractClientPlayer);
		playerModel.attackTime = 0.0F;
		playerModel.crouching = false;
		playerModel.swimAmount = 0.0F;
		playerModel.setupAnim(abstractClientPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		ResourceLocation resourceLocation = abstractClientPlayer.getSkin().texture();
		modelPart.render(poseStack, multiBufferSource.getBuffer(RenderType.entitySolid(resourceLocation)), i, OverlayTexture.NO_OVERLAY);
		modelPart2.render(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(resourceLocation)), i, OverlayTexture.NO_OVERLAY);
		poseStack.pushPose();
		playerModel.translateToHand(leftHand ? HumanoidArm.LEFT : HumanoidArm.RIGHT, poseStack);
		poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		poseStack.translate((float) (leftHand ? -1 : 1) / 16.0F, 0.125F, -0.625F);
		renderItem(Minecraft.getInstance().player, mainHand ? mainHandItem : offHandItem, leftHand ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, leftHand, poseStack, multiBufferSource, i);
		poseStack.popPose();
	}
}

