package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.attack.TentacleSpike;
import cn.leolezury.eternalstarlight.common.entity.attack.Whip;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class TentacleSpikeItem extends WhipItem {
	public TentacleSpikeItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public Whip createWhip(Level level, Player owner, ItemStack weapon, float damageScale) {
		return new TentacleSpike(level, owner, weapon, damageScale);
	}
}
