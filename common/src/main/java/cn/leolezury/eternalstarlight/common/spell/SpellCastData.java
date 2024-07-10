package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public record SpellCastData(boolean hasSpell, AbstractSpell spell, int strength, int castTicks) {
	public static SpellCastData getDefault() {
		return new SpellCastData(false, ESSpells.GUIDANCE_OF_STARS.get(), 0, 0);
	}

	@Override
	public boolean hasSpell() {
		return hasSpell && spell() != null;
	}

	public SpellCastData increaseTick() {
		return new SpellCastData(hasSpell(), spell(), strength(), castTicks() + 1);
	}

	public static SpellCastData fromNetwork(RegistryFriendlyByteBuf buf) {
		boolean hasSpell = buf.readBoolean();
		AbstractSpell spell = buf.readById(ESSpells.SPELLS.registry()::byId);
		int strength = buf.readInt();
		int ticks = buf.readInt();
		return new SpellCastData(hasSpell, spell, strength, ticks);
	}

	public void toNetwork(RegistryFriendlyByteBuf buf) {
		buf.writeBoolean(hasSpell());
		buf.writeById(ESSpells.SPELLS.registry()::getId, spell());
		buf.writeInt(strength());
		buf.writeInt(castTicks());
	}

	public record ItemSpellSource(Item item, InteractionHand hand) implements SpellSource {
		@Override
		public boolean canContinue(LivingEntity living) {
			return living.isUsingItem() && living.getUseItem().is(item()) && living.getUsedItemHand() == hand();
		}
	}

	public interface SpellSource {
		boolean canContinue(LivingEntity living);
	}
}
