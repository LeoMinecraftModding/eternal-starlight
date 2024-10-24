package cn.leolezury.eternalstarlight.common.entity.living.goal;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class BoarwarfTradeWithPlayerGoal extends Goal {
	private final Boarwarf villager;

	public BoarwarfTradeWithPlayerGoal(Boarwarf villager) {
		this.villager = villager;
		this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if (!this.villager.isAlive()) {
			return false;
		} else if (this.villager.isInWater()) {
			return false;
		} else if (!this.villager.onGround()) {
			return false;
		} else if (this.villager.hurtMarked) {
			return false;
		} else {
			Player player = this.villager.getTradingPlayer();
			if (player == null) {
				return false;
			} else if (this.villager.distanceToSqr(player) > 16.0D) {
				return false;
			} else return Boarwarf.getBoarwarfCredit(player) >= -30;
		}
	}

	@Override
	public void start() {
		this.villager.getNavigation().stop();
	}

	@Override
	public void stop() {
		this.villager.setTradingPlayer(null);
	}
}
