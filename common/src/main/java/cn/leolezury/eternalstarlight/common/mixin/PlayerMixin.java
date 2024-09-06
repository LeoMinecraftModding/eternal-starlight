package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.interfaces.Grappling;
import cn.leolezury.eternalstarlight.common.entity.interfaces.GrapplingOwner;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
import cn.leolezury.eternalstarlight.common.spell.SpellCooldown;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
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

import java.util.ArrayList;
import java.util.Optional;

@Mixin(Player.class)
public abstract class PlayerMixin implements SpellCaster, GrapplingOwner {
	@Unique
	private SpellCastData spellCastData = SpellCastData.getDefault();
	@Unique
	private SpellCastData.SpellSource spellSource = e -> false;
	@Unique
	private ArrayList<SpellCooldown> spellCooldowns = new ArrayList<>();
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

	@Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
	private void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
		Player player = (Player) (Object) this;
		Optional<Tag> cooldowns = SpellCooldown.LIST_CODEC.encodeStart(player.registryAccess().createSerializationContext(NbtOps.INSTANCE), spellCooldowns).resultOrPartial();
		cooldowns.ifPresent(tag -> compoundTag.put(EternalStarlight.ID + ":spell_cooldowns", tag));
	}

	@Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
	private void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
		Player player = (Player) (Object) this;
		Tag tag = compoundTag.get(EternalStarlight.ID + ":spell_cooldowns");
		if (tag != null) {
			this.spellCooldowns = SpellCooldown.LIST_CODEC.parse(player.registryAccess().createSerializationContext(NbtOps.INSTANCE), tag).resultOrPartial().orElse(new ArrayList<>());
		}
	}

	@Override
	public SpellCastData getESSpellData() {
		return spellCastData;
	}

	@Override
	public void setESSpellData(SpellCastData data) {
		this.spellCastData = data;
	}

	@Override
	public SpellCastData.SpellSource getESSpellSource() {
		return spellSource;
	}

	@Override
	public void setESSpellSource(SpellCastData.SpellSource spellSource) {
		this.spellSource = spellSource;
	}

	@Override
	public Entity getESGrappling() {
		return grappling;
	}

	@Override
	public void setESGrappling(Entity grappling) {
		this.grappling = grappling;
	}

	@Override
	public ArrayList<SpellCooldown> getESSpellCooldowns() {
		return spellCooldowns;
	}

	@Override
	public void setESSpellCooldowns(ArrayList<SpellCooldown> cooldowns) {
		this.spellCooldowns = cooldowns;
	}

	@Override
	public void addESSpellCooldown(AbstractSpell spell, int cooldown) {
		if (spellCooldowns.stream().noneMatch(c -> c.getSpell() == spell)) {
			spellCooldowns.add(new SpellCooldown(spell, cooldown));
		} else {
			spellCooldowns.stream().filter(c -> c.getSpell() == spell).findFirst().ifPresent(c -> c.setCooldown(cooldown));
		}
	}
}
