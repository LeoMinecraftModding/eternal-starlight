package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import cn.leolezury.eternalstarlight.common.spell.SpellCooldown;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ESSpellUtil {
	public static int getCooldown(LivingEntity entity, AbstractSpell spell) {
		if (entity instanceof SpellCaster) {
			Optional<SpellCooldown> cooldown = ESDataAttachments.SPELL_COOLDOWNS.getData(entity).stream().filter(c -> c.getSpell() == spell).findFirst();
			if (cooldown.isPresent()) {
				return cooldown.get().getCooldown();
			}
		}
		return 0;
	}

	public static void setCooldown(LivingEntity entity, AbstractSpell spell, int cooldown) {
		if (entity instanceof SpellCaster) {
			List<SpellCooldown> updated = new ArrayList<>(ESDataAttachments.SPELL_COOLDOWNS.getData(entity));
			if (updated.stream().noneMatch(c -> c.getSpell() == spell)) {
				updated.add(new SpellCooldown(spell, cooldown));
			} else {
				updated.stream().filter(c -> c.getSpell() == spell).findFirst().ifPresent(c -> c.setCooldown(cooldown));
			}
			ESDataAttachments.SPELL_COOLDOWNS.setData(entity, updated);
		}
	}

	public static void tickSpells(LivingEntity entity) {
		if (entity instanceof SpellCaster) {
			// cooldown
			List<SpellCooldown> updated = new ArrayList<>(ESDataAttachments.SPELL_COOLDOWNS.getData(entity));
			updated.forEach(SpellCooldown::tick);
			updated.removeIf(c -> c.getCooldown() <= 0);
			ESDataAttachments.SPELL_COOLDOWNS.setData(entity, updated);
			// current spell
			if (!entity.level().isClientSide && ESDataAttachments.SPELL_CAST_DATA.getData(entity).hasSpell()) {
				ESDataAttachments.SPELL_CAST_DATA.setData(entity, ESDataAttachments.SPELL_CAST_DATA.getData(entity).increaseTick());
				AbstractSpell spell = ESDataAttachments.SPELL_CAST_DATA.getData(entity).spell();
				int preparationTicks = spell.spellProperties().preparationTicks();
				int spellTicks = spell.spellProperties().spellTicks();
				int useTicks = ESDataAttachments.SPELL_CAST_DATA.getData(entity).castTicks();
				if (!spell.canContinueToCast(entity, useTicks) || !ESDataAttachments.SPELL_SOURCE.getData(entity).canContinue(entity)) {
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
}
