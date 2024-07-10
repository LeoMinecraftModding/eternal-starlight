package cn.leolezury.eternalstarlight.common.entity.interfaces;

import cn.leolezury.eternalstarlight.common.spell.SpellCastData;

public interface SpellCaster {
	SpellCastData getSpellData();

	void setSpellData(SpellCastData data);

	SpellCastData.SpellSource getSpellSource();

	void setSpellSource(SpellCastData.SpellSource source);
}
