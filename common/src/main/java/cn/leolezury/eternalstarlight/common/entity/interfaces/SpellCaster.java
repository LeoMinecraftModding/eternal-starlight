package cn.leolezury.eternalstarlight.common.entity.interfaces;

import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
import cn.leolezury.eternalstarlight.common.spell.SpellCooldown;

import java.util.ArrayList;

public interface SpellCaster {
	SpellCastData getESSpellData();

	void setESSpellData(SpellCastData data);

	SpellCastData.SpellSource getESSpellSource();

	void setESSpellSource(SpellCastData.SpellSource source);

	ArrayList<SpellCooldown> getESSpellCooldowns();

	void setESSpellCooldowns(ArrayList<SpellCooldown> cooldowns);

	void addESSpellCooldown(AbstractSpell spell, int cooldown);
}
