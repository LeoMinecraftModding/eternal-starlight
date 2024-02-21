package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class ESSpellUtil {
    public static int getCoolDownFor(LivingEntity entity, AbstractSpell spell) {
        String id = Objects.requireNonNull(ESSpells.SPELLS.registry().getKey(spell)).toString();
        if (!ESEntityUtil.getPersistentData(entity).contains("SpellCoolDowns", Tag.TAG_COMPOUND)) {
            ESEntityUtil.getPersistentData(entity).put("SpellCoolDowns", new CompoundTag());
        }
        CompoundTag spellCoolDowns = ESEntityUtil.getPersistentData(entity).getCompound("SpellCoolDowns");
        return spellCoolDowns.getInt(id);
    }

    public static void setCoolDownFor(LivingEntity entity, AbstractSpell spell, int coolDown) {
        String id = Objects.requireNonNull(ESSpells.SPELLS.registry().getKey(spell)).toString();
        if (!ESEntityUtil.getPersistentData(entity).contains("SpellCoolDowns", Tag.TAG_COMPOUND)) {
            ESEntityUtil.getPersistentData(entity).put("SpellCoolDowns", new CompoundTag());
        }
        CompoundTag spellCoolDowns = ESEntityUtil.getPersistentData(entity).getCompound("SpellCoolDowns");
        spellCoolDowns.putInt(id, coolDown);
    }

    public static void ticksSpellCoolDowns(LivingEntity entity) {
        if (!ESEntityUtil.getPersistentData(entity).contains("SpellCoolDowns", Tag.TAG_COMPOUND)) {
            ESEntityUtil.getPersistentData(entity).put("SpellCoolDowns", new CompoundTag());
        }
        CompoundTag spellCoolDowns = ESEntityUtil.getPersistentData(entity).getCompound("SpellCoolDowns");
        ESSpells.SPELLS.registry().forEach((spell -> {
            String id = Objects.requireNonNull(ESSpells.SPELLS.registry().getKey(spell)).toString();
            if (spellCoolDowns.getInt(id) > 0) {
                spellCoolDowns.putInt(id, spellCoolDowns.getInt(id) - 1);
            }
        }));
    }
}
