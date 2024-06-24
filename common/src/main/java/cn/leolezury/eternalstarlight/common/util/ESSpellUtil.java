package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class ESSpellUtil {
	public static int getCooldownFor(LivingEntity entity, AbstractSpell spell) {
		String id = Objects.requireNonNull(ESSpells.SPELLS.registry().getKey(spell)).toString();
		if (!ESEntityUtil.getPersistentData(entity).contains("SpellCooldowns", Tag.TAG_COMPOUND)) {
			ESEntityUtil.getPersistentData(entity).put("SpellCooldowns", new CompoundTag());
		}
		CompoundTag spellCooldowns = ESEntityUtil.getPersistentData(entity).getCompound("SpellCooldowns");
		return spellCooldowns.getInt(id);
	}

	public static void setCooldownFor(LivingEntity entity, AbstractSpell spell, int cooldown) {
		String id = Objects.requireNonNull(ESSpells.SPELLS.registry().getKey(spell)).toString();
		if (!ESEntityUtil.getPersistentData(entity).contains("SpellCooldowns", Tag.TAG_COMPOUND)) {
			ESEntityUtil.getPersistentData(entity).put("SpellCooldowns", new CompoundTag());
		}
		CompoundTag spellCooldowns = ESEntityUtil.getPersistentData(entity).getCompound("SpellCooldowns");
		spellCooldowns.putInt(id, cooldown);
	}

	public static void tickSpells(LivingEntity entity) {
		// cooldown
		if (!ESEntityUtil.getPersistentData(entity).contains("SpellCooldowns", Tag.TAG_COMPOUND)) {
			ESEntityUtil.getPersistentData(entity).put("SpellCooldowns", new CompoundTag());
		}
		CompoundTag spellCooldowns = ESEntityUtil.getPersistentData(entity).getCompound("SpellCooldowns");
		ESSpells.SPELLS.registry().forEach((spell -> {
			String id = Objects.requireNonNull(ESSpells.SPELLS.registry().getKey(spell)).toString();
			if (spellCooldowns.getInt(id) > 0) {
				spellCooldowns.putInt(id, spellCooldowns.getInt(id) - 1);
			}
		}));
		// current spell
		if (!entity.level().isClientSide && entity instanceof SpellCaster caster && caster.getSpellData().hasSpell()) {
			caster.setSpellData(caster.getSpellData().increaseTick());
			AbstractSpell spell = caster.getSpellData().spell();
			int preparationTicks = spell.spellProperties().preparationTicks();
			int spellTicks = spell.spellProperties().spellTicks();
			int useTicks = caster.getSpellData().castTicks();
			if (!spell.canContinueToCast(entity, useTicks)) {
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
