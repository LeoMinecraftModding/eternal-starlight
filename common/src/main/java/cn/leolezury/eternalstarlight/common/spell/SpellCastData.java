package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import net.minecraft.network.RegistryFriendlyByteBuf;

public record SpellCastData(boolean hasSpell, AbstractSpell spell, int castTicks) {
    public static SpellCastData getDefault() {
        return new SpellCastData(false, ESSpells.GUIDANCE_OF_STARS.get(), 0);
    }

    @Override
    public boolean hasSpell() {
        return hasSpell && spell() != null;
    }

    public static SpellCastData fromNetwork(RegistryFriendlyByteBuf buf) {
        boolean hasSpell = buf.readBoolean();
        AbstractSpell spell = buf.readById(ESSpells.SPELLS.registry()::byId);
        int ticks = buf.readInt();
        return new SpellCastData(hasSpell, spell, ticks);
    }

    public void toNetwork(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(hasSpell());
        buf.writeById(ESSpells.SPELLS.registry()::getId, spell());
        buf.writeInt(castTicks());
    }
}
