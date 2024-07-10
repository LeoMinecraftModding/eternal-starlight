package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.entity.interfaces.Grappling;
import cn.leolezury.eternalstarlight.common.entity.interfaces.GrapplingOwner;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin implements SpellCaster, GrapplingOwner {
	@Unique
	private SpellCastData spellCastData = SpellCastData.getDefault();
	@Unique
	private SpellCastData.SpellSource spellSource = e -> true;
	@Unique
	private Entity grappling;

	@Inject(method = "hurtCurrentlyUsedShield", at = @At(value = "HEAD"))
	private void damageShield(float amount, CallbackInfo callBackInfo) {
		Player player = (Player) (Object) this;
		ItemStack useItem = player.getUseItem();
		if (useItem.is(ESItems.MOONRING_GREATSWORD.get())) {
			useItem.hurtAndBreak(Math.max((int) (amount / 5f), 1), player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
			player.stopUsingItem();
			player.getCooldowns().addCooldown(useItem.getItem(), 100);
		}
	}

	@Inject(method = "disableShield", at = @At(value = "HEAD"), cancellable = true)
	private void disableShield(CallbackInfo ci) {
		Player player = (Player) (Object) this;
		ItemStack useItem = player.getUseItem();
		if (useItem.is(ESItems.MOONRING_GREATSWORD.get())) {
			ci.cancel();
		}
	}

	@Inject(method = "aiStep", at = @At(value = "TAIL"))
	private void aiStep(CallbackInfo ci) {
		Player player = (Player) (Object) this;
		if (this.grappling != null && (this.grappling instanceof Grappling grappling1 && grappling1.reachedTarget())) {
			player.resetFallDistance();
			if (player.isControlledByLocalInstance()) {
				float length = grappling1.length();
				double d = this.grappling.position().subtract(player.getEyePosition()).length();
				if (d > (double) length) {
					double e = d / (double) length * 0.1;
					boolean crouch = player.isCrouching();
					boolean shouldJump = this.grappling.getY() + 0.01 >= player.getY() && !crouch && player.onGround() && player.tickCount % 20 == 0;
					player.addDeltaMovement(this.grappling.position().subtract(player.getEyePosition()).scale(1.0 / d).multiply(e, e * 1.1, e).scale(crouch ? 0.6 : (player.onGround() ? 1.8 : 1)).add(0, shouldJump ? 1.1 : 0, 0));
					player.hurtMarked = true;
				}
			}
		}
	}

	@Override
	public SpellCastData getSpellData() {
		return spellCastData;
	}

	@Override
	public void setSpellData(SpellCastData data) {
		this.spellCastData = data;
	}

	@Override
	public SpellCastData.SpellSource getSpellSource() {
		return spellSource;
	}

	@Override
	public void setSpellSource(SpellCastData.SpellSource spellSource) {
		this.spellSource = spellSource;
	}

	@Override
	public Entity getGrappling() {
		return grappling;
	}

	@Override
	public void setGrappling(Entity grappling) {
		this.grappling = grappling;
	}
}
