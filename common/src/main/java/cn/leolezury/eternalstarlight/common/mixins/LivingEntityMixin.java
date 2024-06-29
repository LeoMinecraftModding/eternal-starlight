package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.item.interfaces.Swingable;
import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow
	public abstract ItemStack getItemInHand(InteractionHand interactionHand);

	@Shadow
	public abstract boolean isUsingItem();

	@Shadow
	public abstract ItemStack getUseItem();

	@Shadow
	public abstract Collection<MobEffectInstance> getActiveEffects();

	@Shadow
	public abstract boolean hasEffect(Holder<MobEffect> holder);

	@Shadow
	public abstract boolean removeEffect(Holder<MobEffect> holder);

	@Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"))
	private void swing(InteractionHand interactionHand, boolean bl, CallbackInfo ci) {
		if (this.getItemInHand(interactionHand).getItem() instanceof Swingable swingable) {
			swingable.swing(this.getItemInHand(interactionHand), (LivingEntity) ((Object) this));
		}
	}

	@Inject(method = "isBlocking", at = @At("RETURN"), cancellable = true)
	private void isBlocking(CallbackInfoReturnable<Boolean> cir) {
		if (isUsingItem() && getUseItem().is(ESItems.MOONRING_GREATSWORD.get())) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "createLivingAttributes", at = @At("RETURN"), cancellable = true)
	private static void createLivingAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.setReturnValue(cir.getReturnValue().add(ESAttributes.THROWN_POTION_DISTANCE.asHolder()).add(ESAttributes.ETHER_RESISTANCE.asHolder()));
	}

	@Inject(method = "eat*", at = @At("HEAD"))
	private void eat(Level level, ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {
		if (itemStack.is(ESItems.LUNARIS_CACTUS_GEL.get())) {
			List<Holder<MobEffect>> effectsToRemove = new ArrayList<>();
			for (MobEffectInstance effectInstance : getActiveEffects()) {
				if (!effectInstance.getEffect().value().isBeneficial()) {
					effectsToRemove.add(effectInstance.getEffect());
				}
			}
			for (Holder<MobEffect> effect : effectsToRemove) {
				if (hasEffect(effect)) {
					removeEffect(effect);
				}
			}
		}
	}
}
