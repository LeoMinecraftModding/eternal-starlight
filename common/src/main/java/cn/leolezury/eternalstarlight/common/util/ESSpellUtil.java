package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import cn.leolezury.eternalstarlight.common.spell.SpellCooldown;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;

public class ESSpellUtil {
	public static int getCooldownFor(LivingEntity entity, AbstractSpell spell) {
		if (entity instanceof SpellCaster caster) {
			Optional<SpellCooldown> cooldown = caster.getESSpellCooldowns().stream().filter(c -> c.getSpell() == spell).findFirst();
			if (cooldown.isPresent()) {
				return cooldown.get().getCooldown();
			}
		}
		return 0;
	}

	public static void setCooldownFor(LivingEntity entity, AbstractSpell spell, int cooldown) {
		if (entity instanceof SpellCaster caster) {
			caster.addESSpellCooldown(spell, cooldown);
		}
	}

	public static void tickSpells(LivingEntity entity) {
		// cooldown
		if (entity instanceof SpellCaster caster) {
			caster.getESSpellCooldowns().forEach(SpellCooldown::tick);
			caster.getESSpellCooldowns().removeIf(c -> c.getCooldown() <= 0);
		}
		// current spell
		if (!entity.level().isClientSide && entity instanceof SpellCaster caster && caster.getESSpellData().hasSpell()) {
			caster.setESSpellData(caster.getESSpellData().increaseTick());
			AbstractSpell spell = caster.getESSpellData().spell();
			int preparationTicks = spell.spellProperties().preparationTicks();
			int spellTicks = spell.spellProperties().spellTicks();
			int useTicks = caster.getESSpellData().castTicks();
			if (!spell.canContinueToCast(entity, useTicks) || !caster.getESSpellSource().canContinue(entity)) {
				spell.stop(entity, useTicks - preparationTicks);
			}
			if (useTicks <= preparationTicks + spellTicks) {
				spell.tick(entity, useTicks);
			} else {
				spell.stop(entity, useTicks - preparationTicks);
			}
		}
	}
}
