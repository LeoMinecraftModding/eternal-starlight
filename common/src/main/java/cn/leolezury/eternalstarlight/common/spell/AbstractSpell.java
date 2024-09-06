package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.util.ESSpellUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public abstract class AbstractSpell {
	private final Properties properties;

	public Properties spellProperties() {
		return properties;
	}

	public AbstractSpell(Properties properties) {
		this.properties = properties;
	}

	public boolean canCast(LivingEntity entity, boolean checkCrystal) {
		boolean crystalCheck = !checkCrystal || (entity instanceof Player player && (player.hasInfiniteMaterials() || hasNeededCrystal(player.getInventory())));
		return crystalCheck && ESSpellUtil.getCooldownFor(entity, this) <= 0 && checkExtraConditions(entity);
	}

	public boolean hasNeededCrystal(Inventory inventory) {
		for (int i = 0; i < inventory.getContainerSize(); i++) {
			ItemStack stack = inventory.getItem(i);
			if (spellProperties().types().stream().anyMatch(t -> stack.is(t.getCrystalsTag()))) {
				return true;
			}
		}
		return false;
	}

	public boolean canContinueToCast(LivingEntity entity, int totalTicks) {
		return totalTicks <= properties.preparationTicks() + properties.spellTicks() && checkExtraConditionsToContinue(entity, totalTicks);
	}

	public void start(LivingEntity entity, boolean damageCrystal) {
		start(entity, 1, damageCrystal);
	}

	public void start(LivingEntity entity, int strength, boolean damageCrystal) {
		if (damageCrystal && entity instanceof Player player && !player.hasInfiniteMaterials()) {
			damageCrystal(player);
		}
		onStart(entity);
		if (!entity.level().isClientSide && entity instanceof SpellCaster caster) {
			caster.setESSpellData(new SpellCastData(true, this, strength, 0));
		}
	}

	public void damageCrystal(Player player) {
		Inventory inventory = player.getInventory();
		for (int i = 0; i < inventory.getContainerSize(); i++) {
			ItemStack stack = inventory.getItem(i);
			if (spellProperties().types().stream().anyMatch(t -> stack.is(t.getCrystalsTag()))) {
				stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
				return;
			}
		}
	}

	public void tick(LivingEntity entity, int ticks) {
		if (ticks <= spellProperties().preparationTicks()) {
			onPreparationTick(entity, ticks);
		} else if (ticks <= spellProperties().preparationTicks() + spellProperties().spellTicks()) {
			onSpellTick(entity, ticks - spellProperties().preparationTicks());
		}
		if (ticks % 15 == 0 && entity instanceof Player player) {
			damageCrystal(player);
		}
	}

	public void stop(LivingEntity entity, int ticks) {
		onStop(entity, ticks);
		ESSpellUtil.setCooldownFor(entity, this, properties.cooldownTicks());
		if (!entity.level().isClientSide && entity instanceof SpellCaster caster) {
			caster.setESSpellData(SpellCastData.getDefault());
		}
	}

	public abstract boolean checkExtraConditions(LivingEntity entity);

	public abstract boolean checkExtraConditionsToContinue(LivingEntity entity, int ticks);

	public abstract void onPreparationTick(LivingEntity entity, int ticks);

	public abstract void onSpellTick(LivingEntity entity, int ticks);

	public abstract void onStart(LivingEntity entity);

	public abstract void onStop(LivingEntity entity, int ticks);

	public record Properties(List<ManaType> types, int preparationTicks, int spellTicks, int cooldownTicks) {

	}
}
