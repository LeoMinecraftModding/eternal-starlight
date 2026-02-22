package cn.leolezury.eternalstarlight.neoforge.item.combat;

import cn.leolezury.eternalstarlight.common.item.combat.HammerItem;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.function.Supplier;

public class NeoHammerItem extends HammerItem {
	public NeoHammerItem(Tier tier, Supplier<ParticleOptions> smashParticle, Holder<SoundEvent> smashSound, Properties properties) {
		super(tier, smashParticle, smashSound, properties);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return super.canPerformAction(stack, ability) || ItemAbilities.DEFAULT_PICKAXE_ACTIONS.contains(ability);
	}
}
